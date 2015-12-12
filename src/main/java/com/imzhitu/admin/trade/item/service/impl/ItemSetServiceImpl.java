package com.imzhitu.admin.trade.item.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.util.StringUtil;
import com.hts.web.operations.pojo.RecommendItemBulletin;
import com.hts.web.operations.pojo.SeckillBulletin;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.OpMsgBulletin;
import com.imzhitu.admin.common.service.KeyGenService;
import com.imzhitu.admin.common.util.AdminLoginUtil;
import com.imzhitu.admin.op.mapper.OpMsgBulletinMapper;
import com.imzhitu.admin.privileges.service.AdminService;
import com.imzhitu.admin.trade.item.dao.ItemCache;
import com.imzhitu.admin.trade.item.dao.ItemSetCache;
import com.imzhitu.admin.trade.item.dto.ItemSetDTO;
import com.imzhitu.admin.trade.item.mapper.ItemAndSetRelationMapper;
import com.imzhitu.admin.trade.item.mapper.ItemSeckillMapper;
import com.imzhitu.admin.trade.item.mapper.ItemSetMapper;
import com.imzhitu.admin.trade.item.pojo.Item;
import com.imzhitu.admin.trade.item.pojo.ItemSet;
import com.imzhitu.admin.trade.item.service.ItemSetService;

/**
 * 商品集合业务操作实现类
 * 
 * @author zhangbo	2015年12月8日
 *
 */
@Service
public class ItemSetServiceImpl implements ItemSetService {
	
	/**
	 * 查询redis缓存中的限时秒杀，与前台对应的标记位
	 * @author zhangbo	2015年12月9日
	 */
	private static final Integer seckillCacheFlag = 1;
	
	/**
	 * 查询redis缓存中的推荐商品，与前台对应的标记位
	 * @author zhangbo	2015年12月9日
	 */
	private static final Integer recommendItemCacheFlag = 2;
	
	@Autowired
	private ItemSetMapper itemSetMapper;
	
	@Autowired
	private OpMsgBulletinMapper msgBulletinMapper;
	
	@Autowired
	private ItemSetCache itemSetCache;
	
	@Autowired
	private KeyGenService keyGenService;
	
	@Autowired
	private ItemAndSetRelationMapper itemAndSetRelationMapper;
	
	@Autowired
	private ItemCache itemCache;
	
	@Autowired
	private ItemSeckillMapper itemSeckillMapper;
	
	@Autowired
	private AdminService adminService;
	
	/**
	 * 客户端商品公告缓存
	 * @author zhangbo	2015年12月9日
	 */
	@Autowired
	private com.hts.web.operations.dao.ItemBulletinCache itemBulletinCache;

	@Override
	public void addAwardActivityByBullentin(String ids) throws Exception {
		// 根据勾选的公告id，查询将要操作的公告集合
		Integer[] bullentinIds = StringUtil.convertStringToIds(ids);
		List<OpMsgBulletin> bulletinlist = msgBulletinMapper.queryMsgBulletinByIds(bullentinIds);
		
		// 定义存储到缓存的有奖活动集合，由于是更新供客户端调用的redis，所以定义为web端对象
		List<com.hts.web.operations.pojo.AwardActivityBulletin> awardActivityList = new ArrayList<com.hts.web.operations.pojo.AwardActivityBulletin>();
		
		for (OpMsgBulletin bulletin : bulletinlist) {
			// 转换对象
			com.hts.web.operations.pojo.AwardActivityBulletin awardActivity = new com.hts.web.operations.pojo.AwardActivityBulletin();
			awardActivity.setId(bulletin.getId());
			awardActivity.setBulletinName(bulletin.getBulletinName());
			awardActivity.setBulletinPath(bulletin.getBulletinPath());
			awardActivity.setBulletinThumb(bulletin.getBulletinThumb());
			awardActivity.setBulletinType(bulletin.getBulletinType());
			awardActivity.setLink(bulletin.getLink());
			
			awardActivityList.add(awardActivity);
		}
		itemSetCache.updateAwardActivity(awardActivityList);
	}
	
	@Override
	public void buildItemSetList(Integer page, Integer rows, Integer cacheFlag, Map<String, Object> jsonMap) throws Exception {
		// 定义返回前台商品集合列表
		List<ItemSetDTO> rtnList = new ArrayList<ItemSetDTO>();
		// 定义商品集合总数
		Integer total = 0;
		
		// 若是查询正在展示的商品集合，则从redis缓存中获取，1为正在展示的限时秒杀，2为正在展示的推荐商品，其他情况为查询全部数据
		if ( cacheFlag == seckillCacheFlag ) {
			List<SeckillBulletin> seckillList = itemBulletinCache.querySeckill(new RowSelection(1,0));
			for (SeckillBulletin seckill : seckillList) {
				ItemSetDTO dto = new ItemSetDTO();
				dto.setId(seckill.getId());
				dto.setDescription(seckill.getBulletinName());
				dto.setPath(seckill.getBulletinPath());
				dto.setThumb(seckill.getBulletinThumb());
				dto.setType(seckill.getBulletinType());
				dto.setLink(seckill.getLink());
				dto.setDeadline(new Date(seckill.getDeadline()));
				rtnList.add(dto);
			}
			total = seckillList.size();
		} else if ( cacheFlag == recommendItemCacheFlag ) {
			List<RecommendItemBulletin> rItemList = itemBulletinCache.queryRecommendItem(new RowSelection(1,0));
			for (RecommendItemBulletin rItem : rItemList) {
				ItemSetDTO dto = new ItemSetDTO();
				dto.setId(rItem.getId());
				dto.setDescription(rItem.getBulletinName());
				dto.setPath(rItem.getBulletinPath());
				dto.setThumb(rItem.getBulletinThumb());
				dto.setType(rItem.getBulletinType());
				dto.setLink(rItem.getLink());
				rtnList.add(dto);
			}
			total = rItemList.size();
		} else {
			Integer fristRow = (page-1) * rows;
			Integer limit = rows;
			List<ItemSet> itemSetList = itemSetMapper.queryItemSetList(fristRow, limit);
			for (ItemSet itemSet : itemSetList) {
				ItemSetDTO dto = new ItemSetDTO();
				dto.setId(itemSet.getId());
				dto.setDescription(itemSet.getDescription());
				dto.setPath(itemSet.getPath());
				dto.setThumb(itemSet.getThumb());
				dto.setType(itemSet.getType());
				dto.setLink(itemSet.getLink());
				dto.setOperator(adminService.getAdminUserNameById(itemSet.getOperator()));
				dto.setCreateTime(itemSet.getCreateTime());
				dto.setModifyTime(itemSet.getModifyTime());
				dto.setDeadline(itemSetCache.getSeckillTemp().get(itemSet.getId()));
				rtnList.add(dto);
			}
			total = itemSetMapper.queryItemSetTotal();
		}
		
		jsonMap.put(OptResult.JSON_KEY_ROWS, rtnList);
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		
	}
	
	@Override
	public void addItemSet(String description, String path, String thumb) throws Exception {
		// 采用公告流水序号
		Integer  serial = keyGenService.generateId(Admin.KEYGEN_ITEM_SET_SERIAL);
		// 链接类型为网页链接，则设置为1，是以公告处类型为标准设定
		Integer type = 1;
		Integer id = itemSetMapper.insert(description, path, thumb, type , AdminLoginUtil.getCurrentLoginId(), serial);
		updateItemSet(id, null, null, null, null, handleLink(id));
	}
	
	@Override
	public void updateItemSet(Integer id, String description, String path, String thumb) throws Exception {
		// 链接类型为网页链接，则设置为1，是以公告处类型为标准设定
		Integer type = 1;
		updateItemSet(id, description, path, thumb, type, handleLink(id));
	}
	
	/**
	 * 更新商品集合
	 * 
	 * @param id
	 * @param description
	 * @param path
	 * @param thumb
	 * @param type
	 * @param link
	 * @throws Exception
	 * @author zhangbo	2015年12月12日
	 */
	private void updateItemSet(Integer id, String description, String path, String thumb, Integer type, String link) throws Exception {
		itemSetMapper.update(id, description, path, thumb, type, AdminLoginUtil.getCurrentLoginId());
	}
	
	/**
	 * 处理链接内容
	 * 
	 * @param id	商品集合id
	 * @return
	 * @author zhangbo	2015年12月12日
	 */
	private String handleLink(Integer id) {
		// 链接内容目前为固定写死，不由人工控制
		return "http://imzhitu.com/item/itemset.html?itemSetId=" + id;
	}
	
	@Override
	public void batchDelete(Integer[] idArray) throws Exception {
		for (Integer id : idArray) {
			itemSetMapper.deleteById(id);
		}
	}
	
	@Override
	public void addSeckillToTemp(Integer id, Date deadline) throws Exception {
		itemSetCache.addSeckillTemp(id, deadline);
		// 当添加到秒杀商品集合时，刷新序号，使其排到最顶层
		itemSetMapper.updateSerial(id, Admin.KEYGEN_ITEM_SET_SERIAL);
	}

	@Override
	public void cancelSeckillFromTemp(Integer id) throws Exception {
		itemSetCache.deleteFromSeckillTempById(id);
	}

	@Override
	public void refreshItemSetCache() throws Exception {
		refreshSeckillCache();
		refreshRecommendItemCache();
	}

	/**
	 * 刷新限时秒杀商品集合redis缓存
	 * 
	 * @author zhangbo	2015年12月12日
	 */
	private void refreshSeckillCache() {
		// 定义web秒杀商品集合列表
		List<com.hts.web.operations.pojo.SeckillBulletin> seckillList = new ArrayList<com.hts.web.operations.pojo.SeckillBulletin>();
		
		// 得到秒杀临时存储中的itemSetId与截止时间Map
		Map<Integer, Date> seckillTemp = itemSetCache.getSeckillTemp();
		
		// 循环处理商品集合id
		for (Integer ItemSetId : seckillTemp.keySet()) {
			// 定义秒杀对象公告
			com.hts.web.operations.pojo.SeckillBulletin seckill = new com.hts.web.operations.pojo.SeckillBulletin();
			
			// 查询商品集合对象，然后转换成web端存储的秒杀公告
			ItemSet itemSet = itemSetMapper.getItemSetById(ItemSetId);
			
			// 转换对应属性
			seckill.setId(itemSet.getId());
			seckill.setBulletinName(itemSet.getDescription());
			seckill.setBulletinPath(itemSet.getPath());
			seckill.setBulletinThumb(itemSet.getThumb());
			seckill.setBulletinType(itemSet.getType());
			seckill.setLink(itemSet.getLink());
			seckill.setDeadline(seckillTemp.get(ItemSetId).getTime());	// 限时秒杀要设置截止日期
			seckill.setSerial(itemSet.getSerial());
			seckillList.add(seckill);
			
			// 刷新此商品集合id，其下对应的商品列表redis集合
			List<Item> itemList = itemAndSetRelationMapper.queryItemListBySetId(ItemSetId);
			itemCache.updateItemListBySetId(ItemSetId, itemListToWebItemList(itemList, seckillTemp.get(ItemSetId)));
			
			// 将查询出的商品列表设置为秒杀商品
			for (Item item : itemList) {
				// id与deadline确定唯一性，重复插入会报错，不影响整体循环
				try {
					itemSeckillMapper.insert(item.getId(), seckillTemp.get(ItemSetId));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// 更新限时秒杀集合
		itemSetCache.updateSeckill(seckillList);
	}
	
	/**
	 * 刷新推荐商品集合redis缓存
	 * 
	 * @author zhangbo	2015年12月12日
	 */
	private void refreshRecommendItemCache() {
		// 由于业务确定，只刷新商品集合列表中的前10条数据到redis中
		List<ItemSet> itemSetList = itemSetMapper.queryItemSetListNotInIds(getSeckillTempIds() , 0, 10);
		
		// 定义web推荐商品集合列表
		List<com.hts.web.operations.pojo.RecommendItemBulletin> recommendItemList = new ArrayList<com.hts.web.operations.pojo.RecommendItemBulletin>();
		
		// 循环处理商品集合id
		for (ItemSet itemSet : itemSetList) {
			// 定义推荐商品对象公告
			com.hts.web.operations.pojo.RecommendItemBulletin recommendItem = new com.hts.web.operations.pojo.RecommendItemBulletin();
			
			// 转换对应属性
			recommendItem.setId(itemSet.getId());
			recommendItem.setBulletinName(itemSet.getDescription());
			recommendItem.setBulletinPath(itemSet.getPath());
			recommendItem.setBulletinThumb(itemSet.getThumb());
			recommendItem.setBulletinType(itemSet.getType());
			recommendItem.setLink(itemSet.getLink());
			recommendItem.setSerial(itemSet.getSerial());
			recommendItemList.add(recommendItem);
			
			// 刷新此商品集合id，其下对应的商品列表redis集合
			List<Item> itemList = itemAndSetRelationMapper.queryItemListBySetId(itemSet.getId());
			itemCache.updateItemListBySetId(itemSet.getId(), itemListToWebItemList(itemList, null));
		}
		// 更新限时秒杀集合
		itemSetCache.updateRecommendItem(recommendItemList);
	}

	/**
	 * 商品列表转换成web端使用的存储在redis缓存中的item对象
	 * 
	 * @param itemList	要转换的商品列表
	 * @param deadline	若传递为null，则不为秒杀商品，若为秒杀商品，必须传递截止日期
	 * @return
	 * @author zhangbo	2015年12月11日
	 */
	private List<com.hts.web.trade.item.dto.ItemDTO> itemListToWebItemList(List<Item> itemList, Date deadline) {
		// 定义web端的item集合
		List<com.hts.web.trade.item.dto.ItemDTO> rtnList = new ArrayList<com.hts.web.trade.item.dto.ItemDTO>();
		if ( itemList != null && itemList.size() != 0 ) {
			for (Item item : itemList) {
				com.hts.web.trade.item.dto.ItemDTO dto = new com.hts.web.trade.item.dto.ItemDTO();
				dto.setId(item.getId());
				dto.setName(item.getName());
				dto.setSummary(item.getSummary());
				dto.setDescription(item.getDescription());
				dto.setWorldId(item.getWorldId());
				dto.setImgPath(item.getImgPath());
				dto.setImgThumb(item.getImgThumb());
				dto.setPrice(item.getPrice());
				dto.setSale(item.getSale());
				dto.setSales(item.getSales());
				dto.setStock(item.getStock());
				dto.setItemId(item.getItemId());
				dto.setItemType(item.getItemType());
				dto.setLike(item.getLike());
				dto.setLink(item.getLink());
				dto.setDeadline(deadline);
			}
		}
		return rtnList;
	}
	
	/**
	 * 获取秒杀商品集合ids
	 * 
	 * @return
	 * @author zhangbo	2015年12月12日
	 */
	private Integer[] getSeckillTempIds() {
		// 获取秒杀商品集合临时存储中的id 
		Set<Integer> seckillKeySet = itemSetCache.getSeckillTemp().keySet();
		Integer[] ids = new Integer[seckillKeySet.size()];
		return seckillKeySet.toArray(ids);
	}

	@Override
	public void reorder(String ids) {
		// 重新排序，勾选的倒序设置serial，这样查询后，按照serial查询就与勾选顺序相同
		Integer[] idArray = StringUtil.convertStringToIds(ids);
		for (int i = idArray.length -1; i >= 0; i--) {
			itemSetMapper.updateSerial(idArray[i], keyGenService.generateId(Admin.KEYGEN_ITEM_SET_SERIAL));
		}
		
		// 为了简便开发，这里没有过滤idArray中存在的秒杀商品集合id，而是直接重新刷新了一下秒杀商品集合对象的serial
		// 为保证秒杀商品集合一直为最大serial状态，要重新刷新一下
		Integer[] seckillTempIds = getSeckillTempIds();
		for (int i = seckillTempIds.length -1; i >= 0; i--) {
			itemSetMapper.updateSerial(seckillTempIds[i], keyGenService.generateId(Admin.KEYGEN_ITEM_SET_SERIAL));
		}
	}
	
}

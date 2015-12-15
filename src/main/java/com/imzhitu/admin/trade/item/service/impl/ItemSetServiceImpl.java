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
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.OpMsgBulletin;
import com.imzhitu.admin.common.util.AdminLoginUtil;
import com.imzhitu.admin.op.mapper.OpMsgBulletinMapper;
import com.imzhitu.admin.privileges.service.AdminService;
import com.imzhitu.admin.trade.item.dao.ItemCache;
import com.imzhitu.admin.trade.item.dao.ItemSetCache;
import com.imzhitu.admin.trade.item.mapper.ItemMapper;
import com.imzhitu.admin.trade.item.mapper.ItemSetMapper;
import com.imzhitu.admin.trade.item.mapper.ItemSetRelationMapper;
import com.imzhitu.admin.trade.item.pojo.Item;
import com.imzhitu.admin.trade.item.pojo.ItemSet;
import com.imzhitu.admin.trade.item.pojo.ItemSetDTO;
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
	private ItemMapper itemMapper;
	
	@Autowired
	private OpMsgBulletinMapper msgBulletinMapper;
	
	@Autowired
	private ItemSetCache itemSetCache;

	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private ItemSetRelationMapper itemAndSetRelationMapper;
	
	@Autowired
	private ItemCache itemCache;
	
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
		// 定义存储到缓存的有奖活动集合，由于是更新供客户端调用的redis，所以定义为web端对象
		List<com.hts.web.operations.pojo.ItemSetBulletin> awardActivityList = new ArrayList<com.hts.web.operations.pojo.ItemSetBulletin>();
		
		// 根据勾选的公告id，查询将要操作的公告集合
		Integer[] bullentinIds = StringUtil.convertStringToIds(ids);
		
		if ( bullentinIds.length != 0) {
			for (Integer bullentinId : bullentinIds) {
				OpMsgBulletin bulletin = msgBulletinMapper.getMsgBulletinById(bullentinId);
				// 转换对象
				com.hts.web.operations.pojo.ItemSetBulletin awardActivity = new com.hts.web.operations.pojo.ItemSetBulletin();
				awardActivity.setId(bulletin.getId());
				awardActivity.setBulletinName(bulletin.getBulletinName());
				awardActivity.setBulletinPath(bulletin.getBulletinPath());
				awardActivity.setBulletinThumb(bulletin.getBulletinThumb());
				awardActivity.setBulletinType(bulletin.getBulletinType());
				awardActivity.setLink(bulletin.getLink());
				
				awardActivityList.add(awardActivity);
			}
			
			// 倒序设置一遍serial，这样才能保证按照list的顺序，serial排序是依次减小的
			for (int i = awardActivityList.size() -1; i >= 0; i--) {
				// 由于整体活动内商品集合展示需要，并且要根据serial提示是否有更新，所以此处serial的设值使用itemSet的serial
				awardActivityList.get(i).setSerial(webKeyGenService.generateId(KeyGenServiceImpl.ITEM_SET_SERIAL));
			}
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
			List<com.hts.web.operations.pojo.ItemSetBulletin> seckillList = itemBulletinCache.querySeckill(new RowSelection(1,0));
			for (com.hts.web.operations.pojo.ItemSetBulletin seckill : seckillList) {
				ItemSetDTO dto = new ItemSetDTO();
				dto.setId(seckill.getId());
				dto.setTitle(seckill.getBulletinName());		// 由于公告名称对应标题
				dto.setDescription(seckill.getBulletinDesc());
				dto.setPath(seckill.getBulletinPath());
				dto.setThumb(seckill.getBulletinThumb());
				dto.setType(seckill.getBulletinType());
				dto.setLink(seckill.getLink());
				dto.setDeadline(new Date(seckill.getDeadline()));
				rtnList.add(dto);
			}
			total = seckillList.size();
		} else if ( cacheFlag == recommendItemCacheFlag ) {
			List<com.hts.web.operations.pojo.ItemSetBulletin> rItemList = itemBulletinCache.queryRecommendItem(new RowSelection(1,0));
			for (com.hts.web.operations.pojo.ItemSetBulletin rItem : rItemList) {
				ItemSetDTO dto = new ItemSetDTO();
				dto.setId(rItem.getId());
				dto.setTitle(rItem.getBulletinName());		// 由于公告名称对应标题
				dto.setDescription(rItem.getBulletinDesc());
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
				dto.setTitle(itemSet.getTitle());
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
	public void addItemSet(String title, String description, String path, String thumb) throws Exception {
		// 根据流水得到id
		Integer id = webKeyGenService.generateId(KeyGenServiceImpl.ITEM_SET_ID);
		
		// 链接类型为网页链接，则设置为1，是以公告处类型为标准设定
		Integer type = 1;
		
		// 根据流水得到serial
		Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.ITEM_SET_SERIAL);
		
		itemSetMapper.insert(id, title, description, path, thumb, type, handleLink(id), AdminLoginUtil.getCurrentLoginId(), serial);
	}
	
	@Override
	public void updateItemSet(Integer id, String title, String description, String path, String thumb) throws Exception {
		itemSetMapper.update(id, title, description, path, thumb, AdminLoginUtil.getCurrentLoginId());
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
			itemSetCache.deleteFromSeckillTempById(id);
		}
	}
	
	@Override
	public void addSeckillToTemp(Integer id, Date deadline) throws Exception {
		itemSetCache.addSeckillTemp(id, deadline);
		// 当添加到秒杀商品集合时，刷新序号，使其排到最顶层
		itemSetMapper.updateSerial(id, webKeyGenService.generateId(KeyGenServiceImpl.ITEM_SET_SERIAL));
	}
	
	@Override
	public void cancelSeckillFromTemp(Integer id) throws Exception {
		itemSetCache.deleteFromSeckillTempById(id);
	}

	@Override
	public void refreshItemSetCache() throws Exception {
		// 刷新限时秒杀
		refreshSeckillCache();
		
		// 刷新推荐商品
		refreshRecommendItemCache();
		
		// 刷新商品集合其下的商品列表
		refreshItemListToCache();
	}

	/**
	 * 刷新限时秒杀商品集合redis缓存
	 * 
	 * @author zhangbo	2015年12月12日
	 */
	private void refreshSeckillCache() {
		// 定义web秒杀商品集合列表
		List<com.hts.web.operations.pojo.ItemSetBulletin> seckillList = new ArrayList<com.hts.web.operations.pojo.ItemSetBulletin>();
		
		// 得到秒杀临时存储中的itemSetId与截止时间Map
		Map<Integer, Date> seckillTemp = itemSetCache.getSeckillTemp();
		if ( seckillTemp != null ) {
			// 循环处理商品集合id
			for (Integer ItemSetId : seckillTemp.keySet()) {
				// 定义秒杀对象公告
				com.hts.web.operations.pojo.ItemSetBulletin seckill = new com.hts.web.operations.pojo.ItemSetBulletin();
				
				// 查询商品集合对象，然后转换成web端存储的秒杀公告
				ItemSet itemSet = itemSetMapper.getItemSetById(ItemSetId);
				
				// 转换对应属性
				seckill.setId(itemSet.getId());
				seckill.setBulletinName(itemSet.getTitle());	// 将标题设置在bulletinName字段中
				seckill.setBulletinDesc(itemSet.getDescription());
				seckill.setBulletinPath(itemSet.getPath());
				seckill.setBulletinThumb(itemSet.getThumb());
				seckill.setBulletinType(itemSet.getType());
				seckill.setLink(itemSet.getLink());
				seckill.setDeadline(seckillTemp.get(ItemSetId).getTime());	// 限时秒杀要设置截止日期
				seckill.setSerial(itemSet.getSerial());
				seckillList.add(seckill);
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
		// 定义商品集合列表，由于业务确定，只刷新商品集合列表中的前10条数据到redis中
		List<ItemSet> itemSetList = new ArrayList<ItemSet>();
		
		// 若秒杀中没有数据，则直接查数据库
		Integer[] seckillItemSetIds = getSeckillTempIds();
		if ( seckillItemSetIds.length != 0 ) {
			itemSetList = itemSetMapper.queryItemSetListNotInIds(seckillItemSetIds , 0, 10);
		} else {
			itemSetList = itemSetMapper.queryItemSetList(0, 10);
		}
		
		// 定义web推荐商品集合列表
		List<com.hts.web.operations.pojo.ItemSetBulletin> recommendItemList = new ArrayList<com.hts.web.operations.pojo.ItemSetBulletin>();
		
		// 循环处理商品集合id
		for (ItemSet itemSet : itemSetList) {
			// 定义推荐商品对象公告
			com.hts.web.operations.pojo.ItemSetBulletin recommendItem = new com.hts.web.operations.pojo.ItemSetBulletin();
			
			// 转换对应属性
			recommendItem.setId(itemSet.getId());
			recommendItem.setBulletinName(itemSet.getTitle());	// 将标题设置在bulletinName字段中
			recommendItem.setBulletinDesc(itemSet.getDescription());
			recommendItem.setBulletinPath(itemSet.getPath());
			recommendItem.setBulletinThumb(itemSet.getThumb());
			recommendItem.setBulletinType(itemSet.getType());
			recommendItem.setLink(itemSet.getLink());
			recommendItem.setSerial(itemSet.getSerial());
			recommendItemList.add(recommendItem);
		}
		// 更新限时秒杀集合
		itemSetCache.updateRecommendItem(recommendItemList);
	}
	
	/**
	 * 刷新全部商品集合其下的商品列表到redis中
	 * 
	 * @author zhangbo	2015年12月12日
	 */
	private void refreshItemListToCache() {
		// 查询全部商品集合，分页传递为null，则表明查询全部
		List<ItemSet> itemSetList = itemSetMapper.queryItemSetList(null, null);
		
		// 先全部清空所有商品对应的位置redis缓存，然后再由下面的循环处理，重新赋值商品位置缓存
		itemCache.emptyItemPosition(getItemIds(itemMapper.queryItemList(null)));
		
		for (ItemSet itemSet : itemSetList) {
			// 查询将要刷新的商品list
			List<com.hts.web.trade.item.dto.ItemDTO> webItemList = itemListToWebItemList(itemAndSetRelationMapper.queryItemListBySetId(itemSet.getId()));
			
			// 刷新此商品集合id，其下对应的商品列表redis集合
			itemCache.updateItemListBySetId(itemSet.getId(), webItemList);
			
			// 刷新此商品集合id，其下对应的商品位置redis缓存
			itemCache.updateItemPosition(itemSet.getId(), webItemList);
		}
	}

	/**
	 * 商品列表转换成web端使用的存储在redis缓存中的item对象
	 * 
	 * @param itemList	要转换的商品列表
	 * @param deadline	若传递为null，则不为秒杀商品，若为秒杀商品，必须传递截止日期
	 * @return
	 * @author zhangbo	2015年12月11日
	 */
	private List<com.hts.web.trade.item.dto.ItemDTO> itemListToWebItemList(List<Item> itemList) {
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
				dto.setItemId(item.getTaobaoId());
				dto.setItemType(item.getTaobaoType());
				dto.setLike(item.getLikeNum());
				dto.setLink(item.getLink());
				rtnList.add(dto);
			}
		}
		return rtnList;
	}
	
	private Integer[] getItemIds(List<Item> itemList) {
		Integer[] ids = new Integer[itemList.size()];
		for (int i = 0; i < itemList.size(); i++) {
			ids[i] = itemList.get(i).getId();
		}
		return ids;
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
			itemSetMapper.updateSerial(idArray[i], webKeyGenService.generateId(KeyGenServiceImpl.ITEM_SET_SERIAL));
		}
		
		// 为了简便开发，这里没有过滤idArray中存在的秒杀商品集合id，而是直接重新刷新了一下秒杀商品集合对象的serial
		// 为保证秒杀商品集合一直为最大serial状态，要重新刷新一下
		Integer[] seckillTempIds = getSeckillTempIds();
		for (int i = seckillTempIds.length -1; i >= 0; i--) {
			itemSetMapper.updateSerial(seckillTempIds[i], webKeyGenService.generateId(KeyGenServiceImpl.ITEM_SET_SERIAL));
		}
	}
	
}

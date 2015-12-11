package com.imzhitu.admin.trade.item.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
			// TODO 由于客户端，即web代码，接口方法对app提供的BulletinType对象，类型为5的，表明是有奖活动，这里可能后续有变化
			awardActivity.setBulletinType(5);
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
			/*	dto.setOperator(AdminLoginUtil.getAdminUserName(itemSet.getOperator()));*/
				dto.setCreateTime(itemSet.getCreateTime());
				dto.setModifyTime(itemSet.getModifyTime());
				rtnList.add(dto);
			}
			total = itemSetMapper.queryItemSetTotal();
		}
		
		jsonMap.put(OptResult.JSON_KEY_ROWS, rtnList);
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		
	}
	
	@Override
	public void addItemSet(String description, String path, String thumb, Integer type, String link) throws Exception {
		// 采用公告流水序号
		Integer  serial = keyGenService.generateId(Admin.KEYGEN_OP_MSG_BULLETIN_SERIAL);
		itemSetMapper.insert(description, path, thumb, type, link, AdminLoginUtil.getCurrentLoginId(), serial);
	}
	
	@Override
	public void updateItemSet(Integer id, String description, String path, String thumb, Integer type, String link) throws Exception {
		// 采用公告流水序号
		Integer  serial = keyGenService.generateId(Admin.KEYGEN_OP_MSG_BULLETIN_SERIAL);
		itemSetMapper.update(id, description, path, thumb, type, link, AdminLoginUtil.getCurrentLoginId(), serial);
	}
	
	@Override
	public void batchDelete(Integer[] idArray) throws Exception {
		for (Integer id : idArray) {
			itemSetMapper.deleteById(id);
		}
	}
	
	@Override
	public void refreshSeckillSetCache(Integer[] ids, String deadline) throws Exception {
		
		// 定义并转化限时秒杀截止日期
		Date deadlineDate = null;
		if ( deadline != null && deadline != "") {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			deadlineDate = format.parse(deadline);
		}
		
		// 定义web秒杀商品集合列表
		List<com.hts.web.operations.pojo.SeckillBulletin> seckillList = new ArrayList<com.hts.web.operations.pojo.SeckillBulletin>();
		
		// 循环处理商品集合id
		for (Integer ItemSetId : ids) {
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
			seckill.setDeadline(deadlineDate.getTime());	// 限时秒杀要设置截止日期
			seckillList.add(seckill);
			
			// 刷新此商品集合id，其下对应的商品列表redis集合
			List<Item> itemList = itemAndSetRelationMapper.queryItemListBySetId(ItemSetId);
			itemCache.updateItemListBySetId(ItemSetId, itemListToWebItemList(itemList, deadlineDate));
			
			// 将查询出的商品列表设置为秒杀商品
			for (Item item : itemList) {
				// id与deadline确定唯一性，重复插入会报错，不影响整体循环
				try {
					itemSeckillMapper.insert(item.getId(), deadlineDate);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// 更新限时秒杀集合
		itemSetCache.updateSeckill(seckillList);
	}

	@Override
	public void refreshRecommendItemSetCache(Integer[] ids) throws Exception {
		// 定义web推荐商品集合列表
		List<com.hts.web.operations.pojo.RecommendItemBulletin> recommendItemList = new ArrayList<com.hts.web.operations.pojo.RecommendItemBulletin>();
		
		// 循环处理商品集合id
		for (Integer ItemSetId : ids) {
			// 定义推荐商品对象公告
			com.hts.web.operations.pojo.RecommendItemBulletin recommendItem = new com.hts.web.operations.pojo.RecommendItemBulletin();
			
			// 查询商品集合对象，然后转换成web端存储的推荐商品公告
			ItemSet itemSet = itemSetMapper.getItemSetById(ItemSetId);
			
			// 转换对应属性
			recommendItem.setId(itemSet.getId());
			recommendItem.setBulletinName(itemSet.getDescription());
			recommendItem.setBulletinPath(itemSet.getPath());
			recommendItem.setBulletinThumb(itemSet.getThumb());
			recommendItem.setBulletinType(itemSet.getType());
			recommendItem.setLink(itemSet.getLink());
			recommendItemList.add(recommendItem);
			
			// 刷新此商品集合id，其下对应的商品列表redis集合
			List<Item> itemList = itemAndSetRelationMapper.queryItemListBySetId(ItemSetId);
			itemCache.updateItemListBySetId(ItemSetId, itemListToWebItemList(itemList, null));
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
	
}

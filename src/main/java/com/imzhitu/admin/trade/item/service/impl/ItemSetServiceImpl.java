package com.imzhitu.admin.trade.item.service.impl;

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
import com.imzhitu.admin.trade.item.dao.ItemSetCache;
import com.imzhitu.admin.trade.item.dto.ItemSetDTO;
import com.imzhitu.admin.trade.item.mapper.ItemSetMapper;
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
	public void buildItemSetList(Integer page, Integer rows, Integer cacheFlag, Map<String, Object> jsonMap) {
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
				dto.setOperator(AdminLoginUtil.getAdminUserName(itemSet.getOperator()));
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
	public void addItemSet(String description, String path, String thumb, Integer type, String link) {
		// 采用公告流水序号
		Integer  serial = keyGenService.generateId(Admin.KEYGEN_OP_MSG_BULLETIN_SERIAL);
		itemSetMapper.insert(description, path, thumb, type, link, AdminLoginUtil.getCurrentLoginId(), serial);
	}
	
	@Override
	public void updateItemSet(Integer id, String description, String path, String thumb, Integer type, String link) {
		// 采用公告流水序号
		Integer  serial = keyGenService.generateId(Admin.KEYGEN_OP_MSG_BULLETIN_SERIAL);
		itemSetMapper.update(id, description, path, thumb, type, link, AdminLoginUtil.getCurrentLoginId(), serial);
	}
	
	@Override
	public void batchDelete(Integer[] idArray) {
		for (Integer id : idArray) {
			itemSetMapper.deleteById(id);
		}
	}
	
}

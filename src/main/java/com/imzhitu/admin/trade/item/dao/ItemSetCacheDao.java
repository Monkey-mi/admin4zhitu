package com.imzhitu.admin.trade.item.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * 商品集合banner展示Redis缓存数据操作类
 * 此类用于更新“限时秒杀”、“有奖活动”、“好物推荐”三个分类下所属banner
 * 
 * @author zhangbo	2015年12月8日
 *
 */
public interface ItemSetCacheDao extends BaseCacheDao {
	
	/**
	 * 更新限时秒杀redis缓存集合
	 * 
	 * @param seckillList<com.hts.web.operations.pojo.SeckillBulletin>	限时秒杀公告集合，由于是用于客户端调用，所以泛型存储的为web端秒杀公告对象
	 * @author zhangbo	2015年12月8日
	 */
	public void updateSeckill(List<com.hts.web.operations.pojo.ItemSetBulletin> seckillList);
	
	/**
	 * 更新有奖活动redis缓存集合
	 * 
	 * @param awardActivityList<com.hts.web.operations.pojo.AwardActivityBulletin>	有奖活动公告集合，由于是用于客户端调用，所以泛型存储的为web端有奖活动公告对象
	 * @author zhangbo	2015年12月8日
	 */
	public void updateAwardActivity(List<com.hts.web.operations.pojo.ItemSetBulletin> awardActivityList);
	
	/**
	 * 更新商品推荐redis缓存集合
	 * 
	 * @param recommendItemList<com.hts.web.operations.pojo.RecommendItemBulletin>	商品推荐公告集合，由于是用于客户端调用，所以泛型存储的为web端推荐商品公告对象
	 * @author zhangbo	2015年12月8日
	 */
	public void updateRecommendItem(List<com.hts.web.operations.pojo.ItemSetBulletin> recommendItemList);
	
}

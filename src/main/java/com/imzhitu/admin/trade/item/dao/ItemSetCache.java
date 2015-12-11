package com.imzhitu.admin.trade.item.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;

/**
 * 商品集合banner展示Redis缓存数据操作类
 * 此类用于更新“限时秒杀”、“有奖活动”、“好物推荐”三个分类下所属banner
 * 
 * @author zhangbo	2015年12月8日
 *
 */
@Repository
public class ItemSetCache {
	
	/**
	 * redis操作对象，存储数据供客户端使用，为web端公告对象子类
	 * @author zhangbo	2015年12月8日
	 */
	@Autowired
	private RedisTemplate<String, ? extends com.hts.web.common.pojo.OpMsgBulletin> redisTemplate;
	
	/**
	 * 更新限时秒杀redis缓存集合
	 * 
	 * @param seckillList<com.hts.web.operations.pojo.SeckillBulletin>	限时秒杀公告集合，由于是用于客户端调用，所以泛型存储的为web端秒杀公告对象
	 * @author zhangbo	2015年12月8日
	 */
	@SuppressWarnings("unchecked")
	public void updateSeckill(List<com.hts.web.operations.pojo.SeckillBulletin> seckillList) {
		// 若存在redis的key值，则清空缓存
		if(redisTemplate.hasKey(CacheKeies.OP_MSG_SECKILL)) {
			redisTemplate.delete(CacheKeies.OP_MSG_SECKILL);
		}
		if(seckillList.size() > 0) {
			com.hts.web.operations.pojo.SeckillBulletin[] list = new com.hts.web.operations.pojo.SeckillBulletin[seckillList.size()];
			BoundListOperations<String, com.hts.web.operations.pojo.SeckillBulletin> boundListOps = (BoundListOperations<String, com.hts.web.operations.pojo.SeckillBulletin>) redisTemplate.opsForList().getOperations().boundListOps(CacheKeies.OP_MSG_SECKILL);
			
			// 从右放入数据，即与现有的list数据顺序保持一致
			boundListOps.rightPushAll(seckillList.toArray(list));
		}
	}
	
	/**
	 * 更新有奖活动redis缓存集合
	 * 
	 * @param awardActivityList<com.hts.web.operations.pojo.AwardActivityBulletin>	有奖活动公告集合，由于是用于客户端调用，所以泛型存储的为web端有奖活动公告对象
	 * @author zhangbo	2015年12月8日
	 */
	@SuppressWarnings("unchecked")
	public void updateAwardActivity(List<com.hts.web.operations.pojo.AwardActivityBulletin> awardActivityList) {
		// 若存在redis的key值，则清空缓存
		if(redisTemplate.hasKey(CacheKeies.OP_MSG_AWARD_ACTIVITY)) {
			redisTemplate.delete(CacheKeies.OP_MSG_AWARD_ACTIVITY);
		}
		if(awardActivityList.size() > 0) {
			com.hts.web.operations.pojo.AwardActivityBulletin[] list = new com.hts.web.operations.pojo.AwardActivityBulletin[awardActivityList.size()];
			BoundListOperations<String, com.hts.web.operations.pojo.AwardActivityBulletin> boundListOps = (BoundListOperations<String, com.hts.web.operations.pojo.AwardActivityBulletin>) redisTemplate.opsForList().getOperations().boundListOps(CacheKeies.OP_MSG_AWARD_ACTIVITY);
			
			// 从右放入数据，即与现有的list数据顺序保持一致
			boundListOps.rightPushAll(awardActivityList.toArray(list));
		}
	}
	
	/**
	 * 更新商品推荐redis缓存集合
	 * 
	 * @param recommendItemList<com.hts.web.operations.pojo.RecommendItemBulletin>	商品推荐公告集合，由于是用于客户端调用，所以泛型存储的为web端推荐商品公告对象
	 * @author zhangbo	2015年12月8日
	 */
	@SuppressWarnings("unchecked")
	public void updateRecommendItem(List<com.hts.web.operations.pojo.RecommendItemBulletin> recommendItemList) {
		// 若存在redis的key值，则清空缓存
		if(redisTemplate.hasKey(CacheKeies.OP_MSG_RECOMMEND_ITEM)) {
			redisTemplate.delete(CacheKeies.OP_MSG_RECOMMEND_ITEM);
		}
		if(recommendItemList.size() > 0) {
			com.hts.web.operations.pojo.RecommendItemBulletin[] list = new com.hts.web.operations.pojo.RecommendItemBulletin[recommendItemList.size()];
			BoundListOperations<String, com.hts.web.operations.pojo.RecommendItemBulletin> boundListOps = (BoundListOperations<String, com.hts.web.operations.pojo.RecommendItemBulletin>) redisTemplate.opsForList().getOperations().boundListOps(CacheKeies.OP_MSG_RECOMMEND_ITEM);
			
			// 从右放入数据，即与现有的list数据顺序保持一致
			boundListOps.rightPushAll(recommendItemList.toArray(list));
		}
	}
	
}

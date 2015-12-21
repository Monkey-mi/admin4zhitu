package com.imzhitu.admin.trade.item.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.operations.pojo.ItemSetBulletin;
import com.imzhitu.admin.trade.item.dao.ItemSetCacheDao;

/**
 * @author zhangbo	2015年12月21日
 *
 */
@Repository("com.imzhitu.admin.trade.item.dao.impl.ItemSetCacheDaoImpl")
public class ItemSetCacheDaoImpl extends BaseCacheDaoImpl<com.hts.web.operations.pojo.ItemSetBulletin> implements ItemSetCacheDao {

	@Override
	public void updateSeckill(List<ItemSetBulletin> seckillList) {
		// 若存在redis的key值，则清空缓存
		if(getRedisTemplate().hasKey(CacheKeies.OP_MSG_SECKILL)) {
			getRedisTemplate().delete(CacheKeies.OP_MSG_SECKILL);
		}
		if(seckillList.size() > 0) {
			com.hts.web.operations.pojo.ItemSetBulletin[] list = new com.hts.web.operations.pojo.ItemSetBulletin[seckillList.size()];
			
			// 从右放入数据，即与现有的list数据顺序保持一致
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_MSG_SECKILL, seckillList.toArray(list));
		}
	}

	@Override
	public void updateAwardActivity(List<ItemSetBulletin> awardActivityList) {
		// 若存在redis的key值，则清空缓存
		if(getRedisTemplate().hasKey(CacheKeies.OP_MSG_AWARD_ACTIVITY)) {
			getRedisTemplate().delete(CacheKeies.OP_MSG_AWARD_ACTIVITY);
		}
		if(awardActivityList.size() > 0) {
			com.hts.web.operations.pojo.ItemSetBulletin[] list = new com.hts.web.operations.pojo.ItemSetBulletin[awardActivityList.size()];
			
			// 从右放入数据，即与现有的list数据顺序保持一致
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_MSG_AWARD_ACTIVITY, awardActivityList.toArray(list));
		}
	}

	@Override
	public void updateRecommendItem(List<ItemSetBulletin> recommendItemList) {
		// 若存在redis的key值，则清空缓存
		if(getRedisTemplate().hasKey(CacheKeies.OP_MSG_RECOMMEND_ITEM)) {
			getRedisTemplate().delete(CacheKeies.OP_MSG_RECOMMEND_ITEM);
		}
		if(recommendItemList.size() > 0) {
			com.hts.web.operations.pojo.ItemSetBulletin[] list = new com.hts.web.operations.pojo.ItemSetBulletin[recommendItemList.size()];
			
			// 从右放入数据，即与现有的list数据顺序保持一致
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_MSG_RECOMMEND_ITEM, recommendItemList.toArray(list));
		}
	}

}

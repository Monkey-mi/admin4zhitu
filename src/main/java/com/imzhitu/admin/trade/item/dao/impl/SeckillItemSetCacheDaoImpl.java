package com.imzhitu.admin.trade.item.dao.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.imzhitu.admin.trade.item.dao.SeckillItemSetCacheDao;

/**
 * @author zhangbo	2015年12月21日
 *
 */
@Repository("com.imzhitu.admin.trade.item.dao.impl.SeckillItemSetCacheDaoImpl")
public class SeckillItemSetCacheDaoImpl extends BaseCacheDaoImpl<Map<Integer, Date>> implements SeckillItemSetCacheDao {

	@Override
	public void addSeckillTemp(Integer itemSetId, Date deadline) {
		// 从redis中获取秒杀商品集合记录
		BoundHashOperations<String, Integer, Date> boundHashOps = getRedisTemplate().boundHashOps(CacheKeies.ITEM_SECKILLITEMSET_TEMP);
		boundHashOps.put(itemSetId, deadline);
	}

	@Override
	public void deleteFromSeckillTempById(Integer itemSetId) {
		// 从redis中获取秒杀商品集合记录
		BoundHashOperations<String, Integer, Date> boundHashOps = getRedisTemplate().boundHashOps(CacheKeies.ITEM_SECKILLITEMSET_TEMP);
		boundHashOps.delete(itemSetId);
	}

	@Override
	public Map<Integer, Date> getSeckillTemp() {
		// 从redis中获取秒杀商品集合记录
		BoundHashOperations<String, Integer, Date> boundHashOps = getRedisTemplate().boundHashOps(CacheKeies.ITEM_SECKILLITEMSET_TEMP);
		return boundHashOps.entries();
	}

}

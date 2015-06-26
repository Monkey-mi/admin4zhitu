package com.imzhitu.admin.op.dao.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.imzhitu.admin.op.dao.ChannelPVCacheDao;

@Repository
public class ChannelPVCacheDaoImpl extends BaseCacheDaoImpl<Integer> implements
		ChannelPVCacheDao {

	@Override
	public Map<Integer, Integer> queryAllPV() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		BoundHashOperations<String, Integer, Integer> bound = 
				getRedisTemplate().boundHashOps(CacheKeies.OP_CHANNEL_PV);
		Set<Integer> keies = bound.keys();
		for(Integer id : keies) {
			map.put(id, bound.get(id));
		}
		return map;
	}

	@Override
	public void clearAllPV() {
		if(getRedisTemplate().hasKey(CacheKeies.OP_CHANNEL_PV)) {
			getRedisTemplate().delete(CacheKeies.OP_CHANNEL_PV);
		}
	}

}

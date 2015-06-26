package com.imzhitu.admin.op.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.imzhitu.admin.op.dao.ChannelPVCacheDao;

@Repository
public class ChannelPVCacheDaoImpl extends BaseCacheDaoImpl<Integer> implements
		ChannelPVCacheDao {

	@Override
	public Map<Integer, Integer> queryAllPV() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
//		BoundHashOperations<String, Integer, Integer> bound = 
//				getRedisTemplate().boundHashOps(CacheKeies.OP_CHANNEL_PV);
//		Set<Integer> keies = bound.keys();
//		for(Integer id : keies) {
//			map.put(id, bound.get(id));
//		}
		return map;
	}

	@Override
	public void clearAllPV() {
		// TODO Auto-generated method stub
		
	}

}

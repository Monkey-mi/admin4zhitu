package com.imzhitu.admin.ztworld.dao.impl;

import java.util.List;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorld;
import com.imzhitu.admin.ztworld.dao.HTWorldCacheDao;

@Repository
public class HTWorldCacheDaoImpl extends BaseCacheDaoImpl<HTWorld> implements
		HTWorldCacheDao {

	@Override
	public void deleteOverFlowLatestCache(int limit) {
		ListOperations<String, HTWorld> listOps = getRedisTemplate().opsForList();
		if(listOps.size(CacheKeies.ZTWORLD_LATEST_WORLD) > limit) {
			listOps.trim(CacheKeies.ZTWORLD_LATEST_WORLD, 0, limit);
		}
	}
	
	@Override
	public void deleteLatestCache(Integer worldId) {
		ListOperations<String, HTWorld> listOpt = getRedisTemplate().opsForList();
		long size = listOpt.size(CacheKeies.ZTWORLD_LATEST_WORLD);
		List<HTWorld> list = listOpt.range(CacheKeies.ZTWORLD_LATEST_WORLD, 0, size-1);
		for(int i = 0; i < list.size(); i++) {
			HTWorld w = list.get(i);
			if(w.getId().equals(worldId)) {
				listOpt.remove(CacheKeies.ZTWORLD_LATEST_WORLD, 1, 
						listOpt.index(CacheKeies.ZTWORLD_LATEST_WORLD,i));
				break;
			}
		}
	}

}

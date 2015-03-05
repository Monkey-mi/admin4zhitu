package com.imzhitu.admin.ztworld.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldChildWorldType;
import com.imzhitu.admin.ztworld.dao.HTWorldChildWorldTypeCacheDao;
import com.imzhitu.admin.ztworld.dao.HTWorldChildWorldTypeDao;

@Repository
public class HTWorldChildWorldTypeCacheDaoImpl extends BaseCacheDaoImpl<HTWorldChildWorldType>
		implements HTWorldChildWorldTypeCacheDao {

	@Autowired
	private HTWorldChildWorldTypeDao worldChildWorldTypeDao;
	
	@Override
	public void updateLatestType(int limit) {
		if(getRedisTemplate().hasKey(CacheKeies.ZTWORLD_CHILD_LATEST_TYPE)) {
			getRedisTemplate().delete(CacheKeies.ZTWORLD_CHILD_LATEST_TYPE);
		}
		if(limit > 0) {
			List<HTWorldChildWorldType> list = worldChildWorldTypeDao.queryType(
					new RowSelection(1, limit));
			HTWorldChildWorldType[] types = new HTWorldChildWorldType[list.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.ZTWORLD_CHILD_LATEST_TYPE, 
					list.toArray(types));
		}
	}

}

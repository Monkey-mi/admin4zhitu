package com.imzhitu.admin.ztworld.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldType;
import com.imzhitu.admin.ztworld.dao.HTWorldTypeCacheDao;
import com.imzhitu.admin.ztworld.mapper.ZTWorldTypeMapper;

@Repository("HTWorldTypeCacheDao")
public class HTWorldTypeCacheDaoImpl extends BaseCacheDaoImpl<HTWorldType>
	implements HTWorldTypeCacheDao {
	
	@Autowired
	private ZTWorldTypeMapper typeMapper;
	
	private static final int OTHER_TYPE_ID = 5;
	
	@Override
	public void updateTypeCache() {
		List<HTWorldType> list = typeMapper.queryCacheType();
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getId().equals(OTHER_TYPE_ID)) {
				list.remove(i);
				--i;
			}
		}
		if (getRedisTemplate().hasKey(CacheKeies.ZTWORLD_TYPE)) {
			getRedisTemplate().delete(CacheKeies.ZTWORLD_TYPE);
		}
		if (list.size() > 0) {
			HTWorldType[] objs = new HTWorldType[list.size()];
			getRedisTemplate().opsForList().rightPushAll(
					CacheKeies.ZTWORLD_TYPE, list.toArray(objs));
		}
	}

}

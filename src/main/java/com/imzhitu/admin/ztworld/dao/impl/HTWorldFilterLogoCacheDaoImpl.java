package com.imzhitu.admin.ztworld.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldFilterLogo;
import com.imzhitu.admin.ztworld.dao.HTWorldFilterLogoCacheDao;

@Repository
public class HTWorldFilterLogoCacheDaoImpl extends BaseCacheDaoImpl<HTWorldFilterLogo>
		implements HTWorldFilterLogoCacheDao {

	@Override
	public void updateLogo(HTWorldFilterLogo logo) {
		getRedisTemplate().opsForValue().set(CacheKeies.ZTWORLD_FILTER_LOGO, logo);
	}

	@Override
	public HTWorldFilterLogo queryLogo() {
		return getRedisTemplate().opsForValue().get(CacheKeies.ZTWORLD_FILTER_LOGO);
	}
}

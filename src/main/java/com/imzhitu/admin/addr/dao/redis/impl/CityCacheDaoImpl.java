package com.imzhitu.admin.addr.dao.redis.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.pojo.AddrCity;
import com.imzhitu.admin.addr.dao.redis.CityCacheDao;
import com.imzhitu.admin.addr.mapper.CityMapper;
import com.imzhitu.filter.common.dao.impl.BaseCacheDaoImpl;

@Repository
public class CityCacheDaoImpl extends BaseCacheDaoImpl<AddrCity> implements CityCacheDao {

	@Autowired
	private CityMapper cityMapper;
	
	@Override
	public void updateCache() {
		List<AddrCity> list = cityMapper.queryAllCityCache();
		
		if(getRedisTemplate().hasKey(CacheKeies.ADDR_CITY)) {
			getRedisTemplate().delete(CacheKeies.ADDR_CITY);
		}
		if(list.size() > 0) {
			BoundHashOperations<String, Object, Object> ops 
				= getRedisTemplate().boundHashOps(CacheKeies.ADDR_CITY);
			for(AddrCity city : list) {
				ops.put(city.getShortName(), city);
			}
		}
		
	}

}

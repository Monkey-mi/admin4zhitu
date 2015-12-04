package com.imzhitu.admin.addr.dao.redis;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * 城市缓存数据访问接口
 * 
 * @author lynch 2015-12-03
 *
 */
public interface CityCacheDao extends BaseCacheDao {

	public void updateCache();
	
}
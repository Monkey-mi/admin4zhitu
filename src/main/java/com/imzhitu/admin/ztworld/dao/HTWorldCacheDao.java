package com.imzhitu.admin.ztworld.dao;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * <p>
 * 织图缓存数据访问接口
 * </p>
 * 
 * 创建时间：2014-4-9
 * @author tianjie
 *
 */
public interface HTWorldCacheDao extends BaseCacheDao {

	/**
	 * 删除多余缓存
	 * 
	 * @param limit 保留缓存大小
	 */
	public void deleteOverFlowLatestCache(int limit);
	
	/**
	 * 删除最新织图缓存
	 * 
	 * @param worldId
	 */
	public void deleteLatestCache(Integer worldId);
}

package com.imzhitu.admin.op.dao;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * <p>
 * 活动缓存数据访问接口
 * </p>
 * 
 * 创建时间：2014-4-9
 * @author tianjie
 *
 */
public interface ActivityCacheDao extends BaseCacheDao {

	/**
	 * 更新活动缓存信息
	 */
	public void updateCacheActivity(int limit);
	
}

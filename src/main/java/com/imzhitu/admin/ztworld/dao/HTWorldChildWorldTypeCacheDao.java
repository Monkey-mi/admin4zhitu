package com.imzhitu.admin.ztworld.dao;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * <p>
 * 子世界类型缓存数据访问接口
 * </p>
 * 
 * 创建时间：2014-6-13
 * @author tianjie
 *
 */
public interface HTWorldChildWorldTypeCacheDao extends BaseCacheDao {

	/**
	 * 更新最新类型
	 * 
	 * @param limit
	 */
	public void updateLatestType(int limit);
}

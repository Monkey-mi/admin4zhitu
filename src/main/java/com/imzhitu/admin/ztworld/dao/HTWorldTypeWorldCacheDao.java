package com.imzhitu.admin.ztworld.dao;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * <p>
 * 分类织图数据访问接口
 * </p>
 * 
 * 创建时间：2014-4-29
 * @author tianjie
 *
 */
public interface HTWorldTypeWorldCacheDao extends BaseCacheDao {
	
	/**
	 * 更新精品
	 */
	public void updateSuperb();
	
	/**
	 * 更新分类列表
	 * 
	 */
	public void updateTypeWorld();
	
}

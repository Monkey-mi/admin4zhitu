package com.imzhitu.admin.ztworld.dao;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * 
 * @author tianjie
 *
 */
public interface HTWorldLabelCacehDao extends BaseCacheDao {
	
	/**
	 * 更新热门标签
	 */
	public void updateHotLabel(int limit);
	
	/**
	 * 更新活动标签
	 * 
	 * @param limit
	 */
	public void updateActivityLabel(int limit);

}

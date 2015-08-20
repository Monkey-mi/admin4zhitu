package com.imzhitu.admin.op.dao;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpActivity;

public interface ActivityStarCacheDao extends BaseCacheDao {

	public void updateStar(Integer activityId, Integer limit);
	
	/**
	 * 根据新活动清除就活动明星缓存
	 * 
	 * @param oas
	 */
	public void deleteStarByActs(OpActivity[] oas);
}

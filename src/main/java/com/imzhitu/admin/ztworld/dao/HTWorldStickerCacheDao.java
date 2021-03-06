package com.imzhitu.admin.ztworld.dao;

import com.hts.web.common.dao.BaseCacheDao;

public interface HTWorldStickerCacheDao extends BaseCacheDao {

	/**
	 * 更新推荐贴纸
	 * 
	 * @param typeId
	 * @param limit
	 */
	public int updateRecommendSticker(Integer typeId, Integer limit);
	
	/**
	 * 更新热门贴纸
	 * 
	 * @param startTime
	 * @param endTime
	 * @param typeId
	 * @param limit
	 * @return
	 */
	public int updateHotSticker(long startTime, long endTime, 
			Integer typeId, Integer limit);
	
}

package com.imzhitu.admin.ztworld.dao;

import com.hts.web.common.dao.BaseCacheDao;

public interface HTWorldStickerCacheDao extends BaseCacheDao {

	/**
	 * 更新置顶贴纸
	 */
	public void updateTopSticker();
	
	/**
	 * 更新推荐贴纸
	 */
	public void updateRecommendSticker(Integer[] typeIds, Integer limit);
}

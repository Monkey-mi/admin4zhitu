package com.imzhitu.admin.ztworld.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.HTWorldStickerTypeDto;

public interface HTWorldStickerTypeCacheDao extends BaseCacheDao {
	
	/**
	 * 更新推荐类型
	 */
	public void updateRecommendType();

	/**
	 * 更新贴纸类型缓存
	 */
	public void updateStickerType();
	
	
	/**
	 * 更新贴纸类型缓存
	 */
	public void updateRecommendType(List<HTWorldStickerTypeDto> types);
	
}

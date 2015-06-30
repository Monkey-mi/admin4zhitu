package com.imzhitu.admin.ztworld.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.HTWorldStickerSet;

/**
 * 置顶贴纸缓存数据访问接口
 * 
 * @author lynch
 *
 */
public interface StickerTopCacheDao extends BaseCacheDao {

	/**
	 * 更新置顶贴纸
	 * 
	 * @param sets
	 */
	public void updateTopSticker(List<HTWorldStickerSet> sets);
}

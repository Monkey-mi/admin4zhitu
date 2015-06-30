package com.imzhitu.admin.ztworld.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldStickerDto;
import com.hts.web.common.pojo.HTWorldStickerSet;
import com.hts.web.common.pojo.HTWorldStickerTop;
import com.imzhitu.admin.ztworld.dao.StickerTopCacheDao;
import com.imzhitu.admin.ztworld.mapper.ZTWorldStickerMapper;

@Repository
public class StickerTopCacheDaoImpl extends BaseCacheDaoImpl<HTWorldStickerTop> implements
		StickerTopCacheDao {

	@Autowired
	private ZTWorldStickerMapper stickerMapper;

	@Override
	public void updateTopSticker(List<HTWorldStickerSet> sets) {
		if(sets == null) {
			return;
		}
		if(getRedisTemplate().hasKey(CacheKeies.ZTWORLD_STICKER_TOP)) {
			getRedisTemplate().delete(CacheKeies.ZTWORLD_STICKER_TOP);
		}
		List<HTWorldStickerTop> cacheList = new ArrayList<HTWorldStickerTop>();
		for(HTWorldStickerSet set : sets) {
			Integer setId = set.getId();
			List<HTWorldStickerDto> list = stickerMapper.queryCacheTopSticker(setId);
			if(list.size() > 0) {
				HTWorldStickerDto sticker = list.get(0);
				HTWorldStickerTop top = new HTWorldStickerTop(
				sticker.getRecommendId(),
				sticker.getId(),
				sticker.getTypeId(),
				sticker.getSetId(),
				sticker.getStickerPath(),
				sticker.getStickerThumbPath(),
				sticker.getStickerDemoPath(),
				sticker.getStickerName(),
				sticker.getStickerDesc(),
				sticker.getSharePath(),
				sticker.getHasLock(),
				sticker.getUnlock(),
				sticker.getLabelId(),
				sticker.getFill());
				
				top.setSets(list);
				cacheList.add(top);
			}
			
		}
		
		if(cacheList.size() > 0) {
			HTWorldStickerTop[] objs = new HTWorldStickerTop[cacheList.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.ZTWORLD_STICKER_TOP, 
					cacheList.toArray(objs));
		}
	}
}

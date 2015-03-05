package com.imzhitu.admin.ztworld.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldStickerDto;
import com.imzhitu.admin.ztworld.dao.HTWorldStickerCacheDao;
import com.imzhitu.admin.ztworld.mapper.ZTWorldStickerMapper;

@Repository
public class HTWorldStickerCacheDaoImpl extends BaseCacheDaoImpl<HTWorldStickerDto> implements
		HTWorldStickerCacheDao {

	@Autowired
	private ZTWorldStickerMapper stickerMapper;
	
	@Override
	public void updateTopSticker() {
		if(getRedisTemplate().hasKey(CacheKeies.ZTWORLD_STICKER_TOP)) {
			getRedisTemplate().delete(CacheKeies.ZTWORLD_STICKER_TOP);
		}
		List<HTWorldStickerDto> list = stickerMapper.queryCacheTopStickerDto();
		if(list.size() > 0) {
			HTWorldStickerDto[] objs = new HTWorldStickerDto[list.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.ZTWORLD_STICKER_TOP, 
					list.toArray(objs));
		}
	}

	@Override
	public void updateRecommendSticker(Integer[] typeIds, Integer limit) {
		if(getRedisTemplate().hasKey(CacheKeies.ZTWORLD_STICKER_RECOMMEND)) {
			getRedisTemplate().delete(CacheKeies.ZTWORLD_STICKER_RECOMMEND);
		}
		for(Integer typeId : typeIds) {
			List<HTWorldStickerDto> list = stickerMapper.queryCacheRecommendStickerDto(typeId, limit);
			if(list.size() > 0) {
				HTWorldStickerDto[] objs = new HTWorldStickerDto[list.size()];
				getRedisTemplate().opsForList().rightPushAll(CacheKeies.ZTWORLD_STICKER_RECOMMEND, 
						list.toArray(objs));
			}
		}
	}
	
}

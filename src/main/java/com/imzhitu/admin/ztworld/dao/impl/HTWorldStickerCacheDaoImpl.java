package com.imzhitu.admin.ztworld.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
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

	private static Logger log = Logger.getLogger(HTWorldStickerCacheDaoImpl.class);
	
	@Autowired
	private ZTWorldStickerMapper stickerMapper;
	
//	@Override
//	public void updateTopSticker() {
//		if(getRedisTemplate().hasKey(CacheKeies.ZTWORLD_STICKER_TOP)) {
//			getRedisTemplate().delete(CacheKeies.ZTWORLD_STICKER_TOP);
//		}
//		List<HTWorldStickerDto> list = stickerMapper.queryCacheTopStickerDto();
//		if(list.size() > 0) {
//			HTWorldStickerDto[] objs = new HTWorldStickerDto[list.size()];
//			getRedisTemplate().opsForList().rightPushAll(CacheKeies.ZTWORLD_STICKER_TOP, 
//					list.toArray(objs));
//		}
//	}

	@Override
	public int updateRecommendSticker(Integer typeId, Integer limit) {
		if(getRedisTemplate().hasKey(CacheKeies.ZTWORLD_STICKER_RECOMMEND)) {
			getRedisTemplate().delete(CacheKeies.ZTWORLD_STICKER_RECOMMEND);
		}
		List<HTWorldStickerDto> list = stickerMapper.queryCacheRecommendStickerDto(limit);
		if(list.size() > 0) {
			for(HTWorldStickerDto dto : list) {
				dto.setTypeId(typeId);
				getRedisTemplate().opsForList().rightPush(CacheKeies.ZTWORLD_STICKER_RECOMMEND, 
						dto);
			}
			return 0;
		}
		return -1;
	}

	@Override
	public int updateHotSticker(long startTime, long endTime, Integer typeId, Integer limit) {
		List<HTWorldStickerDto> list = stickerMapper.queryCacheHotStickerDto(startTime, endTime, limit);
		if(list.size() > 0) {
			for(HTWorldStickerDto dto : list) {
				dto.setTypeId(typeId);
				getRedisTemplate().opsForList().rightPush(CacheKeies.ZTWORLD_STICKER_RECOMMEND, 
						dto);
			}
			return 0;
		}
		return -1;
	}
	
}

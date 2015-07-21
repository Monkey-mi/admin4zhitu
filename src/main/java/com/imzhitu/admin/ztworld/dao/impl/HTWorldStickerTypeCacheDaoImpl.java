package com.imzhitu.admin.ztworld.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldStickerTypeDto;
import com.imzhitu.admin.ztworld.dao.HTWorldStickerTypeCacheDao;
import com.imzhitu.admin.ztworld.mapper.ZTWorldStickerTypeMapper;

@Repository
public class HTWorldStickerTypeCacheDaoImpl extends BaseCacheDaoImpl<HTWorldStickerTypeDto>
		implements HTWorldStickerTypeCacheDao {

	@Autowired
	private ZTWorldStickerTypeMapper typeMapper;
	
	@Override
	public void updateStickerType() {
		if(getRedisTemplate().hasKey(CacheKeies.ZTWORLD_STICKER_TYPE)) {
			getRedisTemplate().delete(CacheKeies.ZTWORLD_STICKER_TYPE);
		}
		List<HTWorldStickerTypeDto> list = typeMapper.queryCacheType();
		if(list.size() > 0) {
			list.add(0, new HTWorldStickerTypeDto(-1, "推荐"));
			HTWorldStickerTypeDto[] objs = new HTWorldStickerTypeDto[list.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.ZTWORLD_STICKER_TYPE, 
					list.toArray(objs));
		}
		
	}

	@Override
	public void updateRecommendType() {
		List<HTWorldStickerTypeDto> types = typeMapper.queryCacheType();
		updateRecommendType(types);
		
	}

	@Override
	public void updateRecommendType(List<HTWorldStickerTypeDto> types) {
		if(getRedisTemplate().hasKey(CacheKeies.ZTWORLD_STICKER_RECOMMEND_TYPE)) {
			getRedisTemplate().delete(CacheKeies.ZTWORLD_STICKER_RECOMMEND_TYPE);
		}
		if(types.size() > 0) {
			HTWorldStickerTypeDto[] objs = new HTWorldStickerTypeDto[types.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.ZTWORLD_STICKER_RECOMMEND_TYPE, 
					types.toArray(objs));
		}
	}
	
	
	
	

}

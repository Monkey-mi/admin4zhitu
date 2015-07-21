package com.imzhitu.admin.ztworld.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldStickerDto;
import com.hts.web.common.pojo.HTWorldStickerSetDto;
import com.hts.web.common.pojo.HTWorldStickerTypeDto;
import com.imzhitu.admin.ztworld.dao.StickerSetDtoCacheDao;
import com.imzhitu.admin.ztworld.mapper.StickerSetMapper;
import com.imzhitu.admin.ztworld.mapper.ZTWorldStickerMapper;
import com.imzhitu.admin.ztworld.mapper.ZTWorldStickerTypeMapper;

@Repository
public class StickerSetDtoCacheDaoImpl extends BaseCacheDaoImpl<HTWorldStickerSetDto> implements
		StickerSetDtoCacheDao {

	
	@Autowired
	private ZTWorldStickerTypeMapper typeMapper;
	
	@Autowired
	private ZTWorldStickerMapper stickerMapper;
	
	@Autowired
	private StickerSetMapper setMapper;
	
	@Value("${op.stickerIntro}")
	private String sharePath;
	
	public String getSharePath() {
		return sharePath;
	}

	public void setSharePath(String sharePath) {
		this.sharePath = sharePath;
	}



	@Override
	public void updateLib() {
		List<HTWorldStickerTypeDto> typeList = typeMapper.queryCacheType();
		List<HTWorldStickerSetDto> setList = setMapper.queryAllCacheSet();
		List<HTWorldStickerDto> stickerList= stickerMapper.queryAllSticker();
		Map<Integer, List<HTWorldStickerSetDto>> map = new HashMap<Integer, List<HTWorldStickerSetDto>>();
		Map<Integer, Integer> idxMap = new HashMap<Integer, Integer>();
		
		HTWorldStickerTypeDto recType = new HTWorldStickerTypeDto(-1, "推荐");
		
		// 查询精选贴纸
		List<HTWorldStickerDto> recommendStickerList = stickerMapper.queryCacheRecommendStickerDto(1000);
		HTWorldStickerSetDto recSet = new HTWorldStickerSetDto();
		recSet.setId(-1);
		recSet.setSetName("精选贴纸");
		recSet.setTypeId(-1);
		setList.add(recSet);
		
		for(HTWorldStickerDto dto : recommendStickerList) {
			dto.setSetId(-1);
			recSet.getSets().add(dto);
		}
		typeList.add(0, recType);
		
		// 关联所有系列与对应的分类
		for(int i = 0; i < setList.size(); i++) {
			HTWorldStickerSetDto set = setList.get(i);
			List<HTWorldStickerSetDto> list = map.get(set.getTypeId());
			if(list == null) {
				list = new ArrayList<HTWorldStickerSetDto>();
				map.put(set.getTypeId(), list);
			}
			list.add(set);
			
			idxMap.put(set.getId(), i);
		}
		
		//　关联所有贴纸与系列
		for(HTWorldStickerDto sticker : stickerList) {
			if(sticker.getHasLock().equals(Tag.TRUE)) {
				sticker.setSharePath(sharePath + "?stid=" + sticker.getId());
			}
			Integer setId = sticker.getSetId();
			Integer idx = idxMap.get(setId);
			if(idx != null) {
				HTWorldStickerSetDto set = setList.get(idx);
				set.getSets().add(sticker);
			}
		}
		
		// 刷新贴纸库缓存
		for(HTWorldStickerTypeDto dto : typeList) {
			Integer typeId = dto.getId();
			String cacheKey = CacheKeies.ZTWORLD_STICKER_TYPE_ID + typeId;
			if(getRedisTemplate().hasKey(cacheKey)) {
				getRedisTemplate().delete(cacheKey);
			}
			
			List<HTWorldStickerSetDto> cacheList = map.get(typeId);
			if(cacheList != null && cacheList.size() > 0) {
				HTWorldStickerSetDto[] objs = new HTWorldStickerSetDto[cacheList.size()];
				getRedisTemplate().opsForList().rightPushAll(cacheKey, cacheList.toArray(objs));
			}
		}
	}

}

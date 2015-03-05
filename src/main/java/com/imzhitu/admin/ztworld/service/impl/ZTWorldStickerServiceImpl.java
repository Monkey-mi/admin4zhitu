package com.imzhitu.admin.ztworld.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.pojo.HTWorldStickerTypeDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.ZTWorldSticker;
import com.imzhitu.admin.common.pojo.ZTWorldStickerType;
import com.imzhitu.admin.ztworld.dao.HTWorldStickerCacheDao;
import com.imzhitu.admin.ztworld.dao.HTWorldStickerTypeCacheDao;
import com.imzhitu.admin.ztworld.mapper.ZTWorldStickerMapper;
import com.imzhitu.admin.ztworld.mapper.ZTWorldStickerTypeMapper;
import com.imzhitu.admin.ztworld.service.ZTWorldStickerService;

@Service
public class ZTWorldStickerServiceImpl extends BaseServiceImpl implements
		ZTWorldStickerService {
	
	@Autowired
	private ZTWorldStickerMapper stickerMapper;
	
	@Autowired
	private ZTWorldStickerTypeMapper stickerTypeMapper;
	
	@Autowired
	private HTWorldStickerTypeCacheDao typeCacheDao;
	
	@Autowired
	private HTWorldStickerCacheDao stickerCacheDao;
	
	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	private Integer recommendStickerLimit = 9;
	
	public Integer getRecommendStickerLimit() {
		return recommendStickerLimit;
	}

	public void setRecommendStickerLimit(Integer recommendStickerLimit) {
		this.recommendStickerLimit = recommendStickerLimit;
	}

	@Override
	public void buildTypes(ZTWorldStickerType type, final int start, int limit,
			Map<String, Object> jsonMap, final Boolean addAllTag) throws Exception {
		buildNumberDtos(OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID, 
				"getSerial", type, start, limit, jsonMap, new NumberDtoListAdapter<ZTWorldStickerType>(){

					@Override
					public List<? extends Serializable> queryList(
							ZTWorldStickerType dto) {
						List<ZTWorldStickerType> list = stickerTypeMapper.queryTypes(dto);
						return list;
					}

					@Override
					public long queryTotal(ZTWorldStickerType dto) {
						return stickerTypeMapper.queryTotal(dto);
					}
		});
		
		if(start == 1) {
			Integer maxSerial = stickerTypeMapper.queryMaxSerial();
			jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxSerial);
		}
		
	}

	@Override
	public void saveType(ZTWorldStickerType type) throws Exception {
		Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_STICKER_TYPE_ID);
		if(type.getWeight() != null && type.getWeight() > 0) {
			type.setWeight(serial);
		}
		type.setSerial(serial);
		stickerTypeMapper.save(type);
	}

	@Override
	public void updateType(ZTWorldStickerType type) throws Exception {
		stickerTypeMapper.update(type);
	}

	@Override
	public void deleteTypes(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		if(ids != null && ids.length > 0)
			stickerTypeMapper.deleteByIds(ids);
	}
	
	@Override
	public void updateTypeValid(String idsStr, Integer valid) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		if(ids != null && ids.length > 0)
			stickerTypeMapper.updateValidByIds(ids, valid);
		
	}
	
	@Override
	public Integer updateTypeWeight(Integer id, Integer weight) throws Exception {
		if(weight > 0)
			weight = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_STICKER_TYPE_ID);
		
		ZTWorldStickerType type = new ZTWorldStickerType();
		type.setId(id);
		type.setWeight(weight);
		stickerTypeMapper.update(type);
		return weight;
	}

	@Override
	public void updateTypeSerial(String[] idStrs) throws Exception {
		for(int i = idStrs.length - 1; i >= 0; i--) {
			if(StringUtil.checkIsNULL(idStrs[i]))
				continue;
			int id = Integer.parseInt(idStrs[i]);
			Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_STICKER_TYPE_ID);
			stickerTypeMapper.updateSerialById(id, serial);
		}
	}
	
	@Override
	public ZTWorldStickerType queryTypeById(Integer id) throws Exception {
		return stickerTypeMapper.queryById(id);
	}

	@Override
	public void buildStickers(ZTWorldSticker sticker, int start, int limit, 
			Map<String, Object> jsonMap) throws Exception {
		buildNumberDtos(OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID, 
				"getSerial", sticker, start, limit, jsonMap, new NumberDtoListAdapter<ZTWorldSticker>(){

					@Override
					public List<? extends Serializable> queryList(
							ZTWorldSticker dto) {
						return stickerMapper.queryStickers(dto);
					}

					@Override
					public long queryTotal(ZTWorldSticker dto) {
						return stickerMapper.queryTotal(dto);
					}
			
		});
	}

	@Override
	public void saveSticker(ZTWorldSticker sticker) throws Exception {
		Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_STICKER_ID);
		sticker.setSerial(serial);
		if(sticker.getLabelId() == null) {
			sticker.setLabelId(0);
		}
		stickerMapper.save(sticker);
	}

	@Override
	public void updateSticker(ZTWorldSticker sticker) throws Exception {
		stickerMapper.update(sticker);
	}

	@Override
	public void deleteStickers(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		if(ids != null && ids.length > 0)
			stickerMapper.deleteByIds(ids);
	}

	@Override
	public void updateStickerValid(String idsStr, Integer valid)
			throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		if(ids != null && ids.length > 0)
			stickerMapper.updateValidByIds(ids, valid);
		
	}

	@Override
	public void updateStickerSerial(String[] idStrs) throws Exception {
		for(int i = idStrs.length - 1; i >= 0; i--) {
			if(StringUtil.checkIsNULL(idStrs[i]))
				continue;
			int id = Integer.parseInt(idStrs[i]);
			Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_STICKER_ID);
			stickerMapper.updateSerialById(id, serial);
		}
	}

	@Override
	public Integer updateStickerWeight(Integer id, Integer weight)
			throws Exception {
		if(weight > 0)
			weight = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_STICKER_ID);
		
		ZTWorldSticker sticker = new ZTWorldSticker();
		sticker.setId(id);
		sticker.setWeight(weight);
		stickerMapper.update(sticker);
		return weight;
	}

	@Override
	public Integer updateStickerTopWeight(Integer id, Integer topWeight)
			throws Exception {
		if(topWeight > 0)
			topWeight = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_STICKER_ID);
		
		ZTWorldSticker sticker = new ZTWorldSticker();
		sticker.setId(id);
		sticker.setTopWeight(topWeight);
		stickerMapper.update(sticker);
		return topWeight;
	}

	@Override
	public void updateStickerCache(String[] typeIdStrs) throws Exception {
		if(typeIdStrs == null) {
			throw new Exception("please select recommend sticker type");
		}
		
		List<HTWorldStickerTypeDto> recTypes = null;
		Integer[] typeIds = null;
		
		if(typeIdStrs.length > 0 && typeIdStrs[0].equals("0")) {
			recTypes = stickerTypeMapper.queryCacheRecommendType();
			typeIds = new Integer[recTypes.size()];
			for(int i = 0; i < typeIds.length; i++) {
				typeIds[i] = recTypes.get(i).getId();
			}
		} else {
			typeIds = new Integer[typeIdStrs.length];
			for(int i = 0; i < typeIdStrs.length; i++) {
				typeIds[i] = Integer.parseInt(typeIdStrs[i]);
			}
			recTypes = stickerTypeMapper.queryCacheRecommendTypeByIds(typeIds);
		}
		
		if(recTypes.size() > 0) {
			stickerCacheDao.updateRecommendSticker(typeIds, recommendStickerLimit);
		}
		stickerCacheDao.updateTopSticker();
		typeCacheDao.updateRecommendType(recTypes);
		typeCacheDao.updateStickerType();
	}


	@Override
	public ZTWorldSticker queryStickerById(Integer id) throws Exception {
		return stickerMapper.queryById(id);
	}

	@Override
	public List<ZTWorldStickerType> queryAllType(Boolean addAllTag, ZTWorldStickerType type) throws Exception {
		List<ZTWorldStickerType> list = stickerTypeMapper.queryAllType(type);
		if(addAllTag) {
			ZTWorldStickerType all = new ZTWorldStickerType();
			all.setId(0);
			all.setTypeName("所有分类");
			all.setSelected(true);
			list.add(0, all);
		}
		return list;
	}


}

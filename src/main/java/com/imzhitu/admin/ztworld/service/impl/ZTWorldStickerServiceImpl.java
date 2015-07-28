package com.imzhitu.admin.ztworld.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.pojo.HTWorldStickerSet;
import com.hts.web.common.pojo.HTWorldStickerTypeDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.ZTWorldSticker;
import com.imzhitu.admin.common.pojo.ZTWorldStickerSet;
import com.imzhitu.admin.common.pojo.ZTWorldStickerType;
import com.imzhitu.admin.ztworld.dao.HTWorldStickerCacheDao;
import com.imzhitu.admin.ztworld.dao.HTWorldStickerTypeCacheDao;
import com.imzhitu.admin.ztworld.dao.StickerSetDtoCacheDao;
import com.imzhitu.admin.ztworld.dao.StickerTopCacheDao;
import com.imzhitu.admin.ztworld.mapper.StickerSetMapper;
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
	private StickerTopCacheDao stickerTopCacheDao;
	
	@Autowired
	private StickerSetDtoCacheDao stickerSetDtoCacheDao;
	
	@Autowired
	private StickerSetMapper stickerSetMapper;
	
	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	private static final int REC_TYPE_SUPERB = -1;
	
	private static final int REC_TYPE_HOT = -2;
	
	private Integer recommendStickerLimit = 60;
	
	private Integer hotStickerLimit = 9;
	
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
	public void buildStickers(final ZTWorldSticker sticker, int start, int limit, 
			Map<String, Object> jsonMap) throws Exception {
		buildNumberDtos(OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID, 
				"getSerial", sticker, start, limit, jsonMap, new NumberDtoListAdapter<ZTWorldSticker>(){

					@Override
					public List<? extends Serializable> queryList(
							ZTWorldSticker dto) {
						if(!StringUtil.checkIsNULL(dto.getStickerName())) {
							try {
								Integer id = Integer.parseInt(dto.getStickerName());
								dto.setId(id);
							} catch(NumberFormatException e) {
								dto.setStickerName("%" + dto.getStickerName() + "%");
							}
						} else {
							dto.setStickerName(null);
						}
						return stickerMapper.queryStickers(dto);
					}

					@Override
					public long queryTotal(ZTWorldSticker dto) {
						if(!StringUtil.checkIsNULL(dto.getStickerName())) {
							try {
								Integer id = Integer.parseInt(dto.getStickerName());
								dto.setId(id);
							} catch(NumberFormatException e) {
								dto.setStickerName("%" + dto.getStickerName() + "%");
							}
						} else {
							dto.setStickerName(null);
						}
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
		
		List<HTWorldStickerTypeDto> recTypes = new ArrayList<HTWorldStickerTypeDto>();
		
		int res = stickerCacheDao.updateRecommendSticker(REC_TYPE_SUPERB, recommendStickerLimit);
		if(res == 0) {
			recTypes.add(new HTWorldStickerTypeDto(REC_TYPE_SUPERB, "精选贴纸"));
		}
		
		long endTime = new Date().getTime();
		long startTime = endTime - 7*24*60*60*1000;
		res = stickerCacheDao.updateHotSticker(startTime, endTime, REC_TYPE_HOT, hotStickerLimit);
		if(res == 0) {
			recTypes.add(new HTWorldStickerTypeDto(REC_TYPE_HOT, "近期最热"));
		}
		
		typeCacheDao.updateRecommendType(recTypes);
		
		/* 2.9.93之后，贴纸按系列划分后需要用到的缓存数据 */
		stickerSetDtoCacheDao.updateLib();
		typeCacheDao.updateStickerType();
		List<HTWorldStickerSet> cacheSets = stickerSetMapper.queryTopCacheSet();
		stickerTopCacheDao.updateTopSticker(cacheSets);
	}

	@Override
	public ZTWorldSticker queryStickerById(Integer id) throws Exception {
		return stickerMapper.queryById(id);
	}

	@Override
	public List<ZTWorldStickerType> queryAllType(Boolean addAllTag, ZTWorldStickerType type) throws Exception {
		List<ZTWorldStickerType> list = stickerTypeMapper.queryAllType(type);
		
		ZTWorldStickerType defaultType = new ZTWorldStickerType();
		defaultType.setId(0);
		defaultType.setTypeName("默认分类");
		defaultType.setSelected(false);
		list.add(defaultType);
		
		if(addAllTag) {
			ZTWorldStickerType all = new ZTWorldStickerType();
			all.setId(0);
			all.setTypeName("所有分类");
			all.setSelected(true);
			list.add(0, all);
		}
		return list;
	}

	@Override
	public void buildSet(ZTWorldStickerSet set, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		
		buildNumberDtos(OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID, 
				"getSerial", set, start, limit, jsonMap, 
				new NumberDtoListAdapter<ZTWorldStickerSet>(){

					@Override
					public List<? extends Serializable> queryList(
							ZTWorldStickerSet dto) {
						if(dto.getTypeId() != null && dto.getTypeId() == 0) {
							dto.setTypeId(null);
						}
						if(!StringUtil.checkIsNULL(dto.getSetName())) {
							dto.setSetName("%" + dto.getSetName() + "%");
						} else {
							dto.setSetName(null);
						}
						return stickerSetMapper.querySets(dto);
					}

					@Override
					public long queryTotal(ZTWorldStickerSet dto) {
						if(dto.getTypeId() != null && dto.getTypeId() == 0) {
							dto.setTypeId(null);
						}
						if(!StringUtil.checkIsNULL(dto.getSetName())) {
							dto.setSetName("%" + dto.getSetName() + "%");
						} else {
							dto.setSetName(null);
						}
						return stickerSetMapper.queryTotal(dto);
					}
		});
	}
	
	@Override
	public void saveSet(ZTWorldStickerSet set) throws Exception {
		Integer serial = webKeyGenService.generateId(
				KeyGenServiceImpl.HTWORLD_STICKER_SET_SERIAL);
		set.setSerial(serial);
		stickerSetMapper.save(set);
	}

	@Override
	public void updateSet(ZTWorldStickerSet set) throws Exception {
		stickerSetMapper.update(set);
	}

	@Override
	public void deleteSets(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		if(ids != null && ids.length > 0)
			stickerSetMapper.deleteByIds(ids);
	}

	@Override
	public void updateSetSerial(String[] idStrs) throws Exception {
		for(int i = idStrs.length - 1; i >= 0; i--) {
			if(StringUtil.checkIsNULL(idStrs[i]))
				continue;
			int id = Integer.parseInt(idStrs[i]);
			Integer serial = webKeyGenService.generateId(
					KeyGenServiceImpl.HTWORLD_STICKER_SET_SERIAL);
			ZTWorldStickerSet set = new ZTWorldStickerSet();
			set.setId(id);
			set.setSerial(serial);
			stickerSetMapper.update(set);
		}
	}

	@Override
	public Integer updateSetWeight(Integer id, Integer weight) throws Exception {
		ZTWorldStickerSet set = new ZTWorldStickerSet();
		set.setId(id);
		set.setWeight(weight);
		stickerSetMapper.update(set);
		return weight;
	}

	@Override
	public ZTWorldStickerSet querySetById(Integer id) throws Exception {
		return stickerSetMapper.querySetById(id);
	}

}

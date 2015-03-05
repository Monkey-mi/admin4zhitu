package com.imzhitu.admin.ztworld.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.ZTWorldSticker;
import com.imzhitu.admin.common.pojo.ZTWorldStickerType;

public interface ZTWorldStickerService extends BaseService {

	public void buildTypes(ZTWorldStickerType type, int start, int limit, 
			Map<String, Object> jsonMap, Boolean addAllTag) throws Exception;

	public ZTWorldStickerType queryTypeById(Integer id) throws Exception;
	
	public void saveType(ZTWorldStickerType type) throws Exception;
	
	public void updateType(ZTWorldStickerType type) throws Exception;
	
	public void deleteTypes(String idsStr) throws Exception;
	
	public void updateTypeValid(String idsStr, Integer valid) throws Exception;
	
	public void updateTypeSerial(String[] idStrs) throws Exception;
	
	public Integer updateTypeWeight(Integer id, Integer weight) throws Exception;
	
	public void buildStickers(ZTWorldSticker sticker, int start, int limit, 
			Map<String, Object> jsonMap) throws Exception;
	
	public ZTWorldSticker queryStickerById(Integer id) throws Exception;
	
	public void saveSticker(ZTWorldSticker sticker) throws Exception;
	
	public void updateSticker(ZTWorldSticker sticker) throws Exception;
	
	public void deleteStickers(String idsStr) throws Exception;
	
	public void updateStickerValid(String idsStr, Integer valid) throws Exception;
	
	public void updateStickerSerial(String[] idStrs) throws Exception;
	
	public Integer updateStickerWeight(Integer id, Integer weight) throws Exception;
	
	public Integer updateStickerTopWeight(Integer id, Integer topWeight) throws Exception;
	
	public void updateStickerCache(String[] typeIdStrs) throws Exception;
	
	public List<ZTWorldStickerType> queryAllType(Boolean addAllTag, ZTWorldStickerType type) throws Exception;
	
	
}

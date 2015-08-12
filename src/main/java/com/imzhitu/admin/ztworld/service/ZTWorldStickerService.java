package com.imzhitu.admin.ztworld.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.ZTWorldSticker;
import com.imzhitu.admin.common.pojo.ZTWorldStickerSet;
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
	
	public void updateStickerCache() throws Exception;
	
	public List<ZTWorldStickerType> queryAllType(Boolean addAllTag, ZTWorldStickerType type) throws Exception;
	
	/**
	 * 构建系列列表
	 * 
	 * @param set
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildSet(ZTWorldStickerSet set, int start, int limit, 
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 根据id查询系列
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ZTWorldStickerSet querySetById(Integer id) throws Exception;
	
	/**
	 * 保存系列
	 * 
	 * @param set
	 * @throws Exception
	 */
	public void saveSet(ZTWorldStickerSet set) throws Exception;
	
	/**
	 * 更新系列
	 * 
	 * @param set
	 * @throws Exception
	 */
	public void updateSet(ZTWorldStickerSet set) throws Exception;
	
	/**
	 * 删除系列
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void deleteSets(String idsStr) throws Exception;
	
	/**
	 * 更新系列序号
	 * 
	 * @param idStrs
	 * @throws Exception
	 */
	public void updateSetSerial(String[] idStrs) throws Exception;
	
	/**
	 * 更新系列权重
	 * 
	 * @param id
	 * @param weight
	 * @return
	 * @throws Exception
	 */
	public Integer updateSetWeight(Integer id, Integer weight) throws Exception;
}

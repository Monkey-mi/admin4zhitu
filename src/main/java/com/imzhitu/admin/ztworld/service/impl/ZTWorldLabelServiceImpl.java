package com.imzhitu.admin.ztworld.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.HTWorldLabel;
import com.hts.web.common.pojo.HTWorldLabelWorld;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.hts.web.ztworld.dao.impl.HTWorldLabelCacheDaoImpl;
import com.imzhitu.admin.common.pojo.ZTWorldLabelWorldDto;
import com.imzhitu.admin.op.dao.ActivityCacheDao;
import com.imzhitu.admin.ztworld.dao.HTWorldLabelCacehDao;
import com.imzhitu.admin.ztworld.dao.HTWorldLabelDao;
import com.imzhitu.admin.ztworld.dao.HTWorldLabelWorldDao;
import com.imzhitu.admin.ztworld.service.ZTWorldLabelService;
import com.imzhitu.admin.ztworld.service.ZTWorldService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class ZTWorldLabelServiceImpl extends BaseServiceImpl implements
		ZTWorldLabelService {
	
	private Integer hotLimit = 10;
	private Integer activityLimit = 5;

	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private com.hts.web.ztworld.dao.HTWorldLabelWorldDao webWorldLabelWorldDao;
	
	@Autowired
	private com.hts.web.userinfo.service.UserInfoService webUserInfoService;
	
	@Autowired
	private HTWorldLabelDao worldLabelDao;
	
	@Autowired
	private HTWorldLabelWorldDao worldLabelWorldDao;
	
	@Autowired
	private HTWorldLabelCacehDao worldLabelCacheDao;
	
	@Autowired
	private ZTWorldService worldService;
	
	@Autowired
	private ActivityCacheDao activityCacheDao;
	
	@Autowired
	private HTWorldLabelCacheDaoImpl webWorldLabelCacheDao;
	
	public Integer getHotLimit() {
		return hotLimit;
	}

	public void setHotLimit(Integer hotLimit) {
		this.hotLimit = hotLimit;
	}

	public Integer getActivityLimit() {
		return activityLimit;
	}

	public void setActivityLimit(Integer activityLimit) {
		this.activityLimit = activityLimit;
	}

	@Override
	public void buildLabel(Integer maxSerial, Integer labelState, String labelName,
			String orderKey, final String order, int start, int limit, Map<String, Object> jsonMap) throws Exception {
		final LinkedHashMap<String, Object> attrMap = new LinkedHashMap<String, Object>();
		
		if(labelState != null) {
			attrMap.put("label_state", labelState);
		}
		if(!StringUtil.checkIsNULL(labelName)) {
			attrMap.put("label_name", "%" + labelName +"%");
		}

		final String sortKey = convertBuildLabelOrderKey(orderKey);
		
		buildSerializables("getSerial", maxSerial, start, limit, jsonMap, 
				new SerializableListAdapter<HTWorldLabel>() {

			@Override
			public List<HTWorldLabel> getSerializables(RowSelection rowSelection) {
				return worldLabelDao.queryLabel(attrMap, sortKey, order, rowSelection);
			}

			@Override
			public List<HTWorldLabel> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return worldLabelDao.queryLabel(maxId, attrMap, sortKey, order, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return worldLabelDao.queryLabelCount(maxId, attrMap);
			}
			
		}, OptResult.JSON_KEY_ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_SERIAL);
	}
	
	/**
	 * 查询标签
	 */
	@Override
	public List<HTWorldLabel> queryLabelForCombobox()throws Exception{
		return webWorldLabelCacheDao.queryHotLabel(new RowSelection(1,10));
	}

	/**
	 * 转换查询标签orderKey
	 * 
	 * @param orderKey
	 * @return
	 */
	private String convertBuildLabelOrderKey(String orderKey) {
		if(StringUtil.checkIsNULL(orderKey)) {
			return null;
		} else if(orderKey.equals("worldCount")) {
			return "world_count";
		}
		return orderKey;
	}

	@Override
	public Integer saveLabel(String labelName)throws Exception {
		Integer id = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_ID);
		HTWorldLabel label = worldLabelDao.queryLabelByName(labelName);
		if(label != null) {
			worldLabelDao.updateSerial(label.getId(), id);
		} else {
			String pinyin = StringUtil.getPinYin(labelName);
			worldLabelDao.saveLabel(new HTWorldLabel(id, labelName, pinyin, 0,
					0, new Date(), Tag.FALSE, Tag.TRUE, id, 0));
		}
		return id;
	}

	@Override
	public void deleteLabel(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		worldLabelDao.deleteByIds(ids);
//		worldLabelWorldDao.deleteByLabelIds(ids);
		updateHotLabel(hotLimit, activityLimit);
	}

	@Override
	public void updateLabelSerial(String[] ids) {
		for(int i = ids.length - 1; i >= 0; i--) {
			String idStr = ids[i];
			if(!StringUtil.checkIsNULL(idStr)) {
				Integer id = Integer.parseInt(idStr);
				Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_ID);
				worldLabelDao.updateSerial(id, serial);
			}
		}
		worldLabelCacheDao.updateHotLabel(hotLimit);
	}

	@Override
	public void updateHotLabel(int hotLimit, int activityLimit) throws Exception {
		worldLabelCacheDao.updateHotLabel(hotLimit);
		activityCacheDao.updateCacheActivity(activityLimit);
	}

	@Override
	public void buildLabelWorld(final Integer maxId, final Integer labelId, Integer valid, int start, int limit,
			final Map<String, Object> jsonMap) throws Exception {
		final LinkedHashMap<String, Object> attrMap = new LinkedHashMap<String, Object>();
		if(valid != null && !valid.equals(-1)) {
			attrMap.put("valid", valid);
		}
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<ZTWorldLabelWorldDto>() {

			@Override
			public List<ZTWorldLabelWorldDto> getSerializables(RowSelection rowSelection) {
				List<ZTWorldLabelWorldDto> list = worldLabelWorldDao.queryLabelWorld(labelId, attrMap, rowSelection);
				worldService.extractInteractInfo(list);
				Integer max = worldLabelWorldDao.queryMaxId();
				jsonMap.put(OptResult.JSON_KEY_MAX_ID, max);
				webUserInfoService.extractVerify(list);
				return list;
			}

			@Override
			public List<ZTWorldLabelWorldDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<ZTWorldLabelWorldDto> list = worldLabelWorldDao.queryLabelWorld(maxId, labelId, attrMap, rowSelection);
				worldService.extractInteractInfo(list);
				jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxId);
				webUserInfoService.extractVerify(list);
				return list;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				Integer max = (Integer) jsonMap.get(OptResult.JSON_KEY_MAX_ID);
				return worldLabelWorldDao.queryLabelWorldCount(max, labelId, attrMap);
			}
			
		}, OptResult.JSON_KEY_ROWS, OptResult.TOTAL);
		
	}

	@Override
	public void updateLabelWorldValid(Integer labelId, String idsStr, Integer valid)
			throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		worldLabelWorldDao.updateLabelWorldValid(ids, valid);
		Long count = webWorldLabelWorldDao.queryWorldCountByLabelId(labelId);
		worldLabelDao.updateWorldCount(labelId, count.intValue());
	}

	@Override
	public void buildLabelIdsWithoutReject(Integer worldId, Map<String, Object> jsonMap) {
		List<Integer> idsList = worldLabelWorldDao.queryLabelIdsWithoutReject(worldId);
		jsonMap.put(OptResult.JSON_KEY_LABEL_INFO, idsList);
	}

	@Override
	public void saveLabelWorld(Integer worldId,Integer userId,Integer labelId,String labelStr)throws Exception{
		worldLabelWorldDao.deleteByWorldId(worldId);
		if(labelId==null || userId == null || worldId == null)return;
		Integer lwid = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_WORLD_ID);
		worldLabelWorldDao.saveLabelWorld(new HTWorldLabelWorld(lwid, worldId, userId, labelId, Tag.TRUE, lwid, 0) );
		worldLabelWorldDao.updateLabelNameInHtworldHtworld(worldId, labelStr);
	}

	@Override
	public void updateByJSON(String json) throws Exception {
		JSONArray jsArray = JSONArray.fromObject(json);
		for(int i = 0; i < jsArray.size(); i++) {
			JSONObject jsObj = jsArray.getJSONObject(i);
			int id = jsObj.optInt("id");
			int worldCount = jsObj.optInt("worldCount");
			worldLabelDao.updateWorldCount(id, worldCount);
		}
	}

}

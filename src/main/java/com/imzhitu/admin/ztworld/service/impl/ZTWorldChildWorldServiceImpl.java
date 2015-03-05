package com.imzhitu.admin.ztworld.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.HTWorldChildWorldType;
import com.hts.web.common.pojo.HTWorldChildWorldTypeDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.ztworld.dao.HTWorldChildWorldTypeCacheDao;
import com.imzhitu.admin.ztworld.dao.HTWorldChildWorldTypeDao;
import com.imzhitu.admin.ztworld.service.ZTWorldChildWorldService;

@Service
public class ZTWorldChildWorldServiceImpl extends BaseServiceImpl implements
		ZTWorldChildWorldService {
	
	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private HTWorldChildWorldTypeDao worldChildWorldTypeDao;
	
	@Autowired
	private HTWorldChildWorldTypeCacheDao worldChildWorldTypeCacheDao;
	
	
	@Override
	public void saveChildType(String typePath, Integer total, 
			Integer useCount, String typeDesc, String descPath, String labelName) throws Exception {
		Integer id = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_CHILD_TYPE_ID);
		worldChildWorldTypeDao.saveType(new HTWorldChildWorldType(id, typePath, total,
				useCount, typeDesc, descPath, labelName, id));
	}
	
	@Override
	public void updateChildType(Integer id, String typePath, Integer total,
			Integer useCount, String typeDesc, String descPath, String labelName, Integer serial) throws Exception {
		worldChildWorldTypeDao.updateType(new HTWorldChildWorldType(id, typePath, total, 
				useCount, typeDesc, descPath, labelName, serial));
	}

	@Override
	public void buildChildType(int maxSerial, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		buildSerializables("getSerial", maxSerial, start, limit, jsonMap, 
				new SerializableListAdapter<HTWorldChildWorldTypeDto>() {

					@Override
					public List<HTWorldChildWorldTypeDto> getSerializables(
							RowSelection rowSelection) {
						return worldChildWorldTypeDao.queryTypeDto(rowSelection);
					}

					@Override
					public List<HTWorldChildWorldTypeDto> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						return worldChildWorldTypeDao.queryTypeDto(maxId, rowSelection);
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return worldChildWorldTypeDao.queryTypeCount(maxId);
					}
					
		}, OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_SERIAL);
	}

	@Override
	public void deleteChildType(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		worldChildWorldTypeDao.deleteType(ids);
	}

	@Override
	public void updateChildTypeSerial(String[] idStrs) throws Exception {
		for(int i = idStrs.length - 1; i >= 0; i--) {
			String idStr = idStrs[i];
			if(idStr != null && idStr != "") {
				int id = Integer.parseInt(idStrs[i]);
				Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_CHILD_TYPE_ID);
				worldChildWorldTypeDao.updateSerial(id, serial);
			}
		}
	}

	@Override
	public void updateLatestChildType(int limit) throws Exception {
		worldChildWorldTypeCacheDao.updateLatestType(limit);
	}

	@Override
	public HTWorldChildWorldTypeDto getChildTypeById(Integer id)
			throws Exception {
		return worldChildWorldTypeDao.queryTypeById(id);
	}


}

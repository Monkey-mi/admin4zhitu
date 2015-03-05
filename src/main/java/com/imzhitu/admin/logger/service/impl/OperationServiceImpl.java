package com.imzhitu.admin.logger.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.LoggerOperation;
import com.imzhitu.admin.common.pojo.LoggerUserOperationDto;
import com.imzhitu.admin.common.service.KeyGenService;
import com.imzhitu.admin.logger.dao.OperationDao;
import com.imzhitu.admin.logger.dao.UserOperationDao;
import com.imzhitu.admin.logger.service.OperationService;

@Service
public class OperationServiceImpl extends BaseServiceImpl implements OperationService {
	
	@Autowired
	private KeyGenService keyGenService;
	
	@Autowired
	private OperationDao loggerOperationDao;
	
	@Autowired
	private UserOperationDao loggerUserOperationDao;
	

	@Override
	public void buildOperation(int maxSerial, final Boolean addAllTag, int start, int limit,
			Map<String, Object> jsonMap) throws Exception{
		buildSerializables("getSerial", maxSerial, start, limit, jsonMap,
				new SerializableListAdapter<LoggerOperation>() {

					@Override
					public List<LoggerOperation> getSerializables(
							RowSelection rowSelection) {
						List<LoggerOperation> opList = loggerOperationDao.queryOperation(rowSelection);
						if(addAllTag && opList.size() > 0) {
							LoggerOperation opt = new LoggerOperation(-1, "", "所有操作", "", 0);
							opList.add(0, opt);
						}
						return opList;
					}

					@Override
					public List<LoggerOperation> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						return loggerOperationDao.queryOperation(maxId, rowSelection);
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return loggerOperationDao.queryOperationCount(maxId);
					}
					
		}, OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_SERIAL);
	}
	
	@Override
	public LoggerOperation getOperationById(Integer id) throws Exception {
		return loggerOperationDao.queryOperation(id);
	}

	@Override
	public void saveOperation(Integer id, String optInterface, String optName, String optDesc)
			throws Exception {
		Integer serial = keyGenService.generateId(Admin.KEYGEN_LOGGER_OPERATION_SERIAL);
		loggerOperationDao.saveOperation(new LoggerOperation(id, optInterface, optName, optDesc, serial));
	}

	@Override
	public void updateOperation(Integer id, String optInterface, String optName, String optDesc,
			Integer serial) throws Exception {
		loggerOperationDao.updateOperation(new LoggerOperation(id, optInterface, optName, optDesc, serial));
	}
	
	@Override
	public void deleteOperation(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		loggerOperationDao.deleteOperation(ids);
	}
	
	@Override
	public void updateOperationSerial(String[] idStrs) throws Exception {
		for(int i = idStrs.length - 1; i >= 0; i--) {
			String idStr = idStrs[i];
			if(idStr != null && idStr != "") {
				int id = Integer.parseInt(idStrs[i].trim());
				Integer serial = keyGenService.generateId(Admin.KEYGEN_LOGGER_OPERATION_SERIAL);
				loggerOperationDao.updateSerial(id, serial);
			}
		}
	}
	
	
	@Override
	public void buildUserOperation(Integer maxId, Integer start, Integer limit, 
			Integer userId, Integer optId, Date startDate, Date endDate, Map<String, Object> jsonMap) throws Exception {
		final LinkedHashMap<String, Object> attrMap = new LinkedHashMap<String, Object>();
		if(userId != null && !userId.equals(-1)) {
			attrMap.put("user_id", userId);
		}
		if(optId != null && !optId.equals(-1)) {
			attrMap.put("opt_id", optId);
		}
		if(startDate != null) {
			attrMap.put("start_date", startDate);
		}
		if(endDate != null) {
			attrMap.put("end_date", endDate);
		}
		
		buildSerializables(maxId, start, limit, jsonMap,
				new SerializableListAdapter<LoggerUserOperationDto>() {

					@Override
					public List<LoggerUserOperationDto> getSerializables(
							RowSelection rowSelection) {
						return loggerUserOperationDao.queryUserOperationDto(attrMap, rowSelection);
					}

					@Override
					public List<LoggerUserOperationDto> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						return loggerUserOperationDao.queryUserOperationDto(maxId, attrMap, rowSelection);
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return loggerUserOperationDao.queryUserOperationCount(maxId, attrMap);
					}
					
		}, OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);
	}

}

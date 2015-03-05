package com.imzhitu.admin.logger.service;

import java.util.Date;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.LoggerOperation;

public interface OperationService extends BaseService {

	/**
	 * 构建操作信息列表
	 * 
	 * @param maxSerial
	 * @param addAllTag
	 * @param start
	 * @param limit
	 * @param jsonMap
	 */
	public void buildOperation(int maxSerial, Boolean addAllTag, int start, int limit,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 根据id查询操作信息
	 * 
	 * @param id
	 * @throws Exception
	 */
	public LoggerOperation getOperationById(Integer id) throws Exception;
	
	/**
	 * 保存操作信息
	 * 
	 * @param id
	 * @param optInterface
	 * @param optName
	 * @param optDesc
	 * @throws Exception
	 */
	public void saveOperation(Integer id, String optInterface, String optName, 
			String optDesc) throws Exception;
	
	/**
	 * 更新操作信息
	 * 
	 * @param id
	 * @param optInterface
	 * @param optName
	 * @param optDesc
	 * @param serial
	 * @throws Exception
	 */
	public void updateOperation(Integer id, String optInterface, String optName, 
			String optDesc, Integer serial) throws Exception;
	
	/**
	 * 删除操作信息
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void deleteOperation(String idsStr) throws Exception;
	
	/**
	 * 更新操作排序
	 * 
	 * @param idStrs
	 * @throws Exception
	 */
	public void updateOperationSerial(String[] idStrs) throws Exception;
	
	/**
	 * 构建用户操作信息列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param userId
	 * @param optId
	 * @param startDate
	 * @param endDate
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildUserOperation(Integer maxId, Integer start, Integer limit, 
			Integer userId, Integer optId, Date startDate, Date endDate,
			Map<String, Object> jsonMap) throws Exception;
}

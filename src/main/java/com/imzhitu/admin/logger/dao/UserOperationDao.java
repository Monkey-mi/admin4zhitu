package com.imzhitu.admin.logger.dao;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.LoggerUserOperationDto;

public interface UserOperationDao extends BaseDao {

	/**
	 * 保存日志记录
	 * 
	 * @param userId
	 * @param optId
	 * @param optDate
	 */
	public void saveLog(Integer userId, Integer optId, String args, Date optDate);

	/**
	 * 查询用户操作信息列表
	 * 
	 * @param attrMap
	 * @param rowSelection
	 * @return
	 */
	public List<LoggerUserOperationDto> queryUserOperationDto(
			LinkedHashMap<String, Object> attrMap, RowSelection rowSelection);

	/**
	 * 查询用户操作信息列表
	 * 
	 * @param maxId
	 * @param attrMap
	 * @param rowSelection
	 * @return
	 */
	public List<LoggerUserOperationDto> queryUserOperationDto(Integer maxId,
			LinkedHashMap<String, Object> attrMap, RowSelection rowSelection);
	
	/**
	 * 查询用户操作信息总数
	 * 
	 * @param maxId
	 * @return
	 */
	public long queryUserOperationCount(Integer maxId, LinkedHashMap<String, Object> attrMap);
}

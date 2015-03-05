package com.imzhitu.admin.logger.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.LoggerOperation;

public interface OperationDao extends BaseDao {

	/**
	 * 查询操作信息
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<LoggerOperation> queryOperation(RowSelection rowSelection);
	
	/**
	 * 查询操作信息
	 * 
	 * @param maxSerial
	 * @param rowSelection
	 * @return
	 */
	public List<LoggerOperation> queryOperation(Integer maxSerial, RowSelection rowSelection);
	
	/**
	 * 查询操作信息总数
	 * 
	 * @param maxSerial
	 * @return
	 */
	public long queryOperationCount(Integer maxSerial);
	
	/**
	 * 根据id查询操作信息
	 * 
	 * @param id
	 * @return
	 */
	public LoggerOperation queryOperation(Integer id);
	
	/**
	 * 保存操作信息
	 * 
	 * @param id
	 * @param optName
	 * @param optDesc
	 */
	public void saveOperation(LoggerOperation opt);
	
	/**
	 * 更新操作信息
	 * 
	 * @param id
	 * @param optName
	 * @param optDesc
	 */
	public void updateOperation(LoggerOperation opt);
	
	/**
	 * 删除操作信息
	 * 
	 * @param id
	 */
	public void deleteOperation(Integer[] ids);
	
	/**
	 * 更新排序
	 * 
	 * @param id
	 * @param serial
	 */
	public void updateSerial(Integer id, Integer serial);
}

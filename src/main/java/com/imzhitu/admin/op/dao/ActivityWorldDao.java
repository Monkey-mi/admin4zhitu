package com.imzhitu.admin.op.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.OpActivityWorldCheckDto;
import com.imzhitu.admin.common.pojo.OpActivityWorldDto;

/**
 * <p>
 * 活动织图数据访问对象
 * </p>
 * 
 * 创建时间：2014-4-9
 * @author tianjie
 *
 */
public interface ActivityWorldDao extends BaseDao {
	
	/**
	 * 查询活动织图列表
	 * 
	 * @param activityId
	 * @param attrMap
	 * @param rowSelection
	 * 
	 * @return
	 */
	public List<OpActivityWorldDto> queryActivityWorldDto(Integer activityId, 
			LinkedHashMap<String, Object> attrMap, RowSelection rowSelection);
	
	/**
	 * 查询活动织图列表
	 * 
	 * @param maxId
	 * @param activityId
	 * @param attrMap
	 * @param rowSelection
	 * 
	 * @return
	 */
	public List<OpActivityWorldDto> queryActivityWorldDto(Integer maxId, Integer activityId, 
			LinkedHashMap<String, Object> attrMap, RowSelection rowSelection);
	
	/**
	 * 查询活动织图总数
	 * 
	 * @param maxId
	 * @param activityId
	 * @param attrMap
	 * 
	 * @return
	 */
	public long queryActivityWorldCount(Integer maxId,
			Integer activityId, LinkedHashMap<String, Object> attrMap);
	
	/**
	 * 查询活动织图审核列表
	 * 
	 * @param ids
	 * @return
	 */
	public List<OpActivityWorldCheckDto> queryWorldCheck(Integer[] ids);
	
	/**
	 * 构建OpActivityWorldDto
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public OpActivityWorldDto buildActivityWorldDto(ResultSet rs) throws SQLException;

	/**
	 * 根据织图标签表主键id查询对应的活动织图
	 * 活动其实也是一个标签
	 * 
	 * @param id
	 * @return
	 * @author zhangbo	2015年9月16日
	 */
	public OpActivityWorldDto queryLabelActivityWorld(Integer id);
	
}

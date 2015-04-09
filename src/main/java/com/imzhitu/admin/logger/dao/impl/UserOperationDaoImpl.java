package com.imzhitu.admin.logger.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.util.CollectionUtil;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.LoggerUserOperationDto;
import com.imzhitu.admin.logger.dao.UserOperationDao;

@Repository
public class UserOperationDaoImpl extends BaseDaoImpl implements UserOperationDao{

	private static String table = Admin.LOGGER_USER_OPERATION;
	
	private static final String O0_INFO = "o0.opt_interface,o0.opt_name,o0.opt_desc";

	/**
	 * 保存日志
	 */
	private static final String SAVE_LOG = "insert into " + table 
			+ " (user_id,opt_id,opt_args,opt_date) values (?,?,?,?)";
	
	/**
	 * 查询用户操作日志SQL头部
	 */
	private static final String QUERY_U_OPERATION_HEAD = "select uo.*,u.*"
			+ " from (select uo0.*," + O0_INFO + " from " + table + " as uo0," 
			+ Admin.LOGGER_OPERATION + " as o0 where uo0.opt_id=o0.id";
	
	/**
	 * 查询用户操作日志SQL尾部
	 */
	private static final String QUERY_U_OPERATION_FOOT = " order by uo0.id desc limit ?,?) as uo,"
			+ Admin.ADMIN_USER_INFO + " as u where uo.user_id=u.id";
	
	
	/**
	 * 查询用户操作日志总数
	 */
	private static final String QUERY_U_OPERATION_COUNT = "select count(*)  from " + table 
			+ " as uo0," + Admin.LOGGER_OPERATION + " as o0 where uo0.opt_id=o0.id";
	
	@Override
	public void saveLog(Integer userId, Integer optId, String args, Date optDate) {
		getMasterJdbcTemplate().update(SAVE_LOG, new Object[]{userId, optId, args, optDate});
	}

	@Override
	public List<LoggerUserOperationDto> queryUserOperationDto(
			LinkedHashMap<String, Object> attrMap, RowSelection rowSelection) {
		String selection = buildSelection(attrMap);
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		argsList.add(rowSelection.getFirstRow());
		argsList.add(rowSelection.getLimit());
		String sql = QUERY_U_OPERATION_HEAD + selection + QUERY_U_OPERATION_FOOT;
		
		return getJdbcTemplate().query(sql, argsList.toArray(), 
				new RowMapper<LoggerUserOperationDto>() {

					@Override
					public LoggerUserOperationDto mapRow(ResultSet rs,
							int rowNum) throws SQLException {
						return buildUserOperationDto(rs);
					}
		});
	}

	@Override
	public List<LoggerUserOperationDto> queryUserOperationDto(Integer maxId,
			LinkedHashMap<String, Object> attrMap, RowSelection rowSelection) {
		String selection = buildSelection(attrMap);
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		argsList.add(maxId);
		argsList.add(rowSelection.getFirstRow());
		argsList.add(rowSelection.getLimit());
		String sql = QUERY_U_OPERATION_HEAD + selection + " and uo0.id<=?" 
				+ QUERY_U_OPERATION_FOOT;
		
		return getJdbcTemplate().query(sql, argsList.toArray(), 
				new RowMapper<LoggerUserOperationDto>() {

					@Override
					public LoggerUserOperationDto mapRow(ResultSet rs,
							int rowNum) throws SQLException {
						return buildUserOperationDto(rs);
					}
		});
	}

	@Override
	public long queryUserOperationCount(Integer maxId, LinkedHashMap<String, Object> attrMap) {
		String selection = buildSelection(attrMap);
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		argsList.add(maxId);
		String sql = QUERY_U_OPERATION_COUNT + selection + " and uo0.id<=?";
		return getJdbcTemplate().queryForLong(sql, argsList.toArray());
	}
	
	
	/**
	 * 构建查询条件
	 * 
	 * @param attrMap
	 * @return
	 */
	private String buildSelection(Map<String, Object> attrMap) {
		StringBuilder sqlBuild = new StringBuilder();
		Set<String> keies = attrMap.keySet();
		for(String key : keies) {
			sqlBuild.append(" and uo0.");
			if(key.equals("start_date")) {
				sqlBuild.append("opt_date>=?");
			} else if(key.equals("end_date")){
				sqlBuild.append("opt_date<=?");
			} else {
				sqlBuild.append(key).append("=?");
			}
			
		}
		return sqlBuild.toString();
	}
	
	/**
	 * 构建LoggerUserOperationDto
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public LoggerUserOperationDto buildUserOperationDto(ResultSet rs) throws SQLException {
		return new LoggerUserOperationDto(
				rs.getInt("id"), 
				rs.getString("opt_args"), 
				(Date)rs.getObject("opt_date"),
				rs.getInt("opt_id"),
				rs.getString("opt_interface"),
				rs.getString("opt_name"), 
				rs.getString("opt_desc"), 
				rs.getInt("user_id"),
				rs.getString("user_name"));
	}


}

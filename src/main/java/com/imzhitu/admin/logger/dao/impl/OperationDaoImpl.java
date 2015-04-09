package com.imzhitu.admin.logger.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.LoggerOperation;
import com.imzhitu.admin.logger.dao.OperationDao;

@Repository
public class OperationDaoImpl extends BaseDaoImpl implements OperationDao {

	private static String table = Admin.LOGGER_OPERATION;
	
	/**
	 * 保存操作信息
	 */
	private static final String SAVE_OPT = "insert into " + table
			+ " (id,opt_interface,opt_name,opt_desc,serial) values (?,?,?,?,?)";
	
	/**
	 * 更新操作信息
	 */
	private static final String UPDATE_OPT = "update " + table
			+ " set opt_interface=?,opt_name=?,opt_desc=?,serial=? where id=?";
	
	/**
	 * 查询操作信息SQL头部
	 */
	private static final String QUERY_OPT_HEAD = "select * from " + table;
	
	/**
	 * 查询操作信息
	 */
	private static final String QUERY_OPT = QUERY_OPT_HEAD + ORDER_BY_SERIAL_DESC;
	
	/**
	 * 根据最大序号查询操作信息
	 */
	private static final String QUERY_OPT_BY_MAX_SERIAL = QUERY_OPT_HEAD 
			+ " where serial<=?" + ORDER_BY_SERIAL_DESC;
	
	/**
	 * 根据最大序号查询操作信息总数
	 */
	private static final String QUERY_OPT_COUNT_BY_MAX_SERIAL = "select count(*) from " + table
			+ " where serial<=?";
	
	/**
	 * 根据id查询操作信息
	 */
	private static final String QUERY_OPT_BY_ID = QUERY_OPT_HEAD + " where id=?";

	/**
	 * 更新排序
	 */
	private static final String UPDATE_SERIAL = "update " + table
			+ " set serial=? where id=?";
	
	@Override
	public List<LoggerOperation> queryOperation(RowSelection rowSelection) {
		return queryForPage(QUERY_OPT, new RowMapper<LoggerOperation>() {

			@Override
			public LoggerOperation mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildOperation(rs);
			}
		}, rowSelection);
	}

	@Override
	public List<LoggerOperation> queryOperation(Integer maxSerial,
			RowSelection rowSelection) {
		return queryForPage(QUERY_OPT_BY_MAX_SERIAL, new Object[]{maxSerial}, 
				new RowMapper<LoggerOperation>() {

			@Override
			public LoggerOperation mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildOperation(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public long queryOperationCount(Integer maxSerial) {
		return getJdbcTemplate().queryForLong(QUERY_OPT_COUNT_BY_MAX_SERIAL,
				new Object[]{maxSerial});
	}
	
	@Override
	public LoggerOperation queryOperation(Integer id) {
		return queryForObjectWithNULL(QUERY_OPT_BY_ID, new Object[]{id}, 
				new RowMapper<LoggerOperation>() {

					@Override
					public LoggerOperation mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildOperation(rs);
					}
		});
	}

	
	@Override
	public void saveOperation(LoggerOperation opt) {
		getMasterJdbcTemplate().update(SAVE_OPT, new Object[]{
			opt.getId(),
			opt.getOptInterface(),
			opt.getOptName(),
			opt.getOptDesc(),
			opt.getSerial()
		});
	}

	@Override
	public void updateOperation(LoggerOperation opt) {
		getMasterJdbcTemplate().update(UPDATE_OPT, new Object[]{
			opt.getOptInterface(),
			opt.getOptName(),
			opt.getOptDesc(),
			opt.getSerial(),
			opt.getId()
		});
	}

	@Override
	public void deleteOperation(Integer[] ids) {
		deleteByIds(table, ids);
	}

	@Override
	public void updateSerial(Integer id, Integer serial) {
		getMasterJdbcTemplate().update(UPDATE_SERIAL, new Object[]{serial, id});
	}
	
	public LoggerOperation buildOperation(ResultSet rs) throws SQLException {
		return new LoggerOperation(
				rs.getInt("id"),
				rs.getString("opt_interface"),
				rs.getString("opt_name"),
				rs.getString("opt_desc"),
				rs.getInt("serial"));
	}


}

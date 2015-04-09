package com.imzhitu.admin.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserVerify;
import com.imzhitu.admin.userinfo.dao.UserVerifyDao;

@Repository
public class UserVerifyDaoImpl extends BaseDaoImpl implements UserVerifyDao {

	private static String table = HTS.USER_VERIFY;
	
	/**
	 * 添加认证
	 * 
	 */
	private static final String SAVE_VERIFY = "insert into " + table 
			+ " (id,verify_name,verify_desc,verify_icon,serial) values (?,?,?,?,?)";
	
	/**
	 * 更新认证
	 */
	private static final String UPDATE_VERIFY = "update " + table
			+ " set verify_name=?,verify_desc=?,verify_icon=?,serial=? where id=?";
	
	/**
	 * 查询认证公用SQL头部
	 */
	private static final String QUERY_VERIFY_HEAD = "select * from " + table;
	
	/**
	 * 查询认证列表
	 */
	private static final String QUERY_VERIFY = QUERY_VERIFY_HEAD + ORDER_BY_SERIAL_DESC;
	
	/**
	 * 根据最大serial查询认证列表
	 * 
	 */
	private static final String QUERY_VERIFY_BY_MAX_ID = QUERY_VERIFY_HEAD 
			+ " where serial<=?" + ORDER_BY_SERIAL_DESC;
	
	
	/**
	 * 根据最大serial查询认证信息总数
	 */
	private static final String QUERY_VERIFY_COUNT_BY_MAX_ID = "select count(*) from " + table
			+ " where serial<=?";
	
	
	/**
	 * 更新序号
	 * 
	 */
	private static final String UPDATE_VERIFY_SERIAL = "update " + table 
			+ " set serial=? where id=?";
	
	/**
	 * 根据id查询认证信息
	 * 
	 */
	private static final String QUERY_VERIFY_BY_ID = "select * from " + table
			+ " where id=?";
	
	
	@Override
	public void saveVerify(UserVerify verify) {
		getMasterJdbcTemplate().update(SAVE_VERIFY, new Object[]{
				verify.getId(),
				verify.getVerifyName(),
				verify.getVerifyDesc(),
				verify.getVerifyIcon(),
				verify.getSerial()
		});
	}

	@Override
	public List<UserVerify> queryVerify(RowSelection rowSelection) {
		return queryForPage(QUERY_VERIFY, new RowMapper<UserVerify>(){

			@Override
			public UserVerify mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildVerify(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public List<UserVerify> queryVerify(Integer maxSerial,
			RowSelection rowSelection) {
		return queryForPage(QUERY_VERIFY_BY_MAX_ID, new Object[]{maxSerial},
				new RowMapper<UserVerify>(){

			@Override
			public UserVerify mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildVerify(rs);
			}
			
		}, rowSelection);
	}
	
	@Override
	public long queryVerifyCount(Integer maxSerial) {
		return getJdbcTemplate().queryForLong(QUERY_VERIFY_COUNT_BY_MAX_ID,
				new Object[]{maxSerial});
	}

	@Override
	public void deleteVerify(Integer[] ids) {
		deleteByIds(table, ids);
	}

	@Override
	public void updateVerifySerial(Integer id, Integer serial) {
		getMasterJdbcTemplate().update(UPDATE_VERIFY_SERIAL, 
				new Object[]{serial, id});
	}
	
	@Override
	public UserVerify queryVerify(Integer id) {
		return queryForObjectWithNULL(QUERY_VERIFY_BY_ID, new RowMapper<UserVerify>(){

			@Override
			public UserVerify mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildVerify(rs);
			}
			
		}, new Object[]{id});
	}

	@Override
	public Map<Integer, UserVerify> queryAllVerifyMap() {
		final Map<Integer, UserVerify> map = new HashMap<Integer, UserVerify>();
		getJdbcTemplate().query(QUERY_VERIFY_HEAD, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				UserVerify v = buildVerify(rs);
				map.put(v.getId(), v);
			}
		});
		return map;
	}
	
	@Override
	public void updateVerify(UserVerify verify) {
		getMasterJdbcTemplate().update(UPDATE_VERIFY, new Object[]{
				verify.getVerifyName(),
				verify.getVerifyDesc(),
				verify.getVerifyIcon(),
				verify.getSerial(),
				verify.getId()
		});
	}
	
	public UserVerify buildVerify(ResultSet rs) throws SQLException {
		return new UserVerify(
				rs.getInt("id"),
				rs.getString("verify_name"),
				rs.getString("verify_desc"),
				rs.getString("verify_icon"),
				rs.getInt("serial"));
	}

}

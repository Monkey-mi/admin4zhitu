package com.imzhitu.admin.privileges.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.AdminUser;
import com.imzhitu.admin.common.pojo.AdminUserDto;
import com.imzhitu.admin.privileges.dao.AdminDao;

/**
 * <p>
 * 后台管理基本数据访问对象
 * </p>
 * 
 * 创建时间：2012-11-08
 * 
 * @author ztj
 * 
 */
@Repository
public class AdminDaoImpl extends BaseDaoImpl implements AdminDao{

	/**
	 * 表名：管理员信息表
	 */
	private static String table = Admin.ADMIN_USER_INFO;
	
	/**
	 * 用户--角色关联表
	 */
	private static String tableUserInfoRole = Admin.ADMIN_USER_INFO_ROLE;
	
	/**
	 * 用户信息--查询
	 */
	private static final String QUERY_USER_INFO = "select * from " + table;
	
	/**
	 * 保存用户--角色关联关系
	 */
	private static final String SAVE_UERR_INFO_ROLE = "insert into " + tableUserInfoRole + " (user_id,role_id) values (?,?)";

	/**
	 * 根据用户id查询其拥有的角色信息
	 */
	private static final String QUERY_ROLE_BY_USER_ID = "select role_id from " + tableUserInfoRole + " where user_id=?";
	
	/**
	 * 根据用户id删除用户拥有的角色信息
	 */
	private static final String DELETE_USER_INFO_ROLE_BY_USER_ID = "delete from " + tableUserInfoRole + " where user_id=?";
	
	/**
	 * 根据账号查询用户信息
	 */
	private static final String QUERY_USER_INFO_BY_LOGINCODE = "select * from " + table + " where login_code=?";
	
	/**
	 * 根据id查询用户信息
	 */
	private static final String QUERY_USER_INFO_BY_ID = "select * from " + table + " where id=?";
	
	/**
	 * 更新密码
	 */
	private static final String UPDATE_PASSWORD = "update " + table + " set password=? where id=?";
	
	@Override
	public AdminUserDto queryUserInfo(Map<String, Object> attrMap) {
		return query(attrMap, table, new RowMapper<AdminUserDto>() {

			@Override
			public AdminUserDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AdminUserDto userInfo = new AdminUserDto(
						rs.getInt("id"),
						rs.getString("login_code"),
						rs.getString("user_name"),
						rs.getBytes("password"),
						rs.getInt("valid"));
				return userInfo;
			}
		});
	}
	
	@Override
	public Integer queryUserInfoTotalCount(Map<String,Object> attrMap) {
		return queryForLong(attrMap, table).intValue();
	}
	
	@Override
	public List<AdminUser> queryUserInfoList( int page, int rows, Map<String,Object> attrMap) {
		
		String selection = SQLUtil.buildSelection(attrMap);
		String sql = QUERY_USER_INFO + selection + ORDER_BY_ID_DESC;
		return queryForPage(sql, attrMap.values().toArray(), new RowMapper<AdminUser>() {

			@Override
			public AdminUser mapRow(ResultSet rs, int rowNum)
					throws SQLException {
//				return new AdminUser(
//						rs.getInt("id"),
//						rs.getString("login_code"),
//						rs.getString("user_name"),
//						null,
//						rs.getInt("valid"));
				return null;
			}
		}, new RowSelection(page, rows));
	}
	
	@Override
	public void saveUserInfoRole( Integer userId, Integer roleId){
		getMasterJdbcTemplate().update(SAVE_UERR_INFO_ROLE, new Object[]{userId,roleId});
	}
	
	@Override
	public Integer[] queryRoleIdsByUserId( Integer userId) {
		Integer[] roleIds = null;
		List<Map<String, Object>> metaList = getJdbcTemplate().queryForList(QUERY_ROLE_BY_USER_ID, userId);
		roleIds = new Integer[metaList.size()];
		for(int i = 0; i < metaList.size(); i++) {
			roleIds[i] = (Integer)metaList.get(i).get("role_id");
		}
		return roleIds;
	}
	
	@Override
	public void deleteUserInfoRoleByUserId( Integer userId){
		getMasterJdbcTemplate().update(DELETE_USER_INFO_ROLE_BY_USER_ID, new Object[]{userId});
	}

	@Override
	public void updatePassword(Integer userId, byte[] password) {
		getMasterJdbcTemplate().update(UPDATE_PASSWORD, new Object[]{password, userId});
	}

	
	@Override
	public AdminUserDto queryUserInfo(String loginCode) {
		return queryForObjectWithNULL(QUERY_USER_INFO_BY_LOGINCODE, new Object[]{loginCode}, new RowMapper<AdminUserDto>() {

			@Override
			public AdminUserDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return new AdminUserDto(
						rs.getInt("id"),
						rs.getString("login_code"),
						rs.getString("user_name"),
						rs.getBytes("password"),
						rs.getInt("valid"));
			}
		});
	}

	@Override
	public AdminUserDto queryUserInfo(Integer userId) {
		return queryForObjectWithNULL(QUERY_USER_INFO_BY_ID, new Object[]{userId}, new RowMapper<AdminUserDto>() {

			@Override
			public AdminUserDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return new AdminUserDto(
						rs.getInt("id"),
						rs.getString("login_code"),
						rs.getString("user_name"),
						rs.getBytes("password"),
						rs.getInt("valid"));
			}
		});
	}
	
}

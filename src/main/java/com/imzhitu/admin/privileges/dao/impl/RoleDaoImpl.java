package com.imzhitu.admin.privileges.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.AdminRole;
import com.imzhitu.admin.privileges.dao.RoleDao;

/**
 * <p>
 * 系统角色信息数据访问对象
 * </p>
 * 
 * 创建时间：2013-2-17
 * 
 * @author ztj
 *
 */
@Repository
public class RoleDaoImpl extends BaseDaoImpl implements RoleDao{

	/**
	 * 系统角色表
	 */
	private static String table = Admin.ADMIN_ROLE;
	
	/**
	 * 角色--权限分组关联表
	 */
	private static String tableRolePrivilegesGroup = Admin.ADMIN_ROLE_PRIVILEGES_GROUP;
	
	/**
	 * 保存角色--权限分组联系
	 */
	private static final String SAVE_ROLE_PRIVILEGES_GROUP = "insert into " + tableRolePrivilegesGroup 
			+ " (role_id,group_id) values (?,?)";
	
	/**
	 * 根据角色id查询其所有的权限组id列表
	 */
	private static final String QUERY_GROUP_ID_BY_ROLE_ID = "select group_id from " + tableRolePrivilegesGroup + " where role_id=?";
	
	/**
	 * 根据角色id删除其所有用的权限组信息
	 */
	private static final String DELETE_ROLE_GROUP_BY_ROLE_ID = "delete from " + tableRolePrivilegesGroup + " where role_id=?";
	
	/**
	 * 根据角色ids查询所有权限分组信息
	 */
	private static final String SELECT_GROUP_BY_ROLE_IDS = "select distinct(rg.group_id), g.* from " + tableRolePrivilegesGroup 
			+ " as rg, " + Admin.ADMIN_PRIVILEGES_GROUP + " as g"
			+ " where rg.group_id = g.id ";
	
	/**
	 * 查询用户角色列表
	 */
	private static final String SELECT_ROLES_BY_USER_ID = "select distinct r.* from " + table + " as r," + Admin.ADMIN_USER_INFO_ROLE + " as ur"
			+ " where ur.role_id=r.id and ur.user_id=?";
	
	
	@Override
	public void saveRolePrivilegesGroup(Integer roleId, Integer groupId) {
		getJdbcTemplate().update(SAVE_ROLE_PRIVILEGES_GROUP, new Object[]{roleId,groupId});
	}
	
	@Override
	public Integer[] queryGroupIdsByRoleId(Integer roleId) {
		Integer[] groupIds = null;
		List<Map<String, Object>> metaList = getJdbcTemplate().queryForList(QUERY_GROUP_ID_BY_ROLE_ID, roleId);
		groupIds = new Integer[metaList.size()];
		for(int i = 0; i < metaList.size(); i++) {
			groupIds[i] = (Integer)metaList.get(i).get("group_id");
		}
		return groupIds;
	}
	
	@Override
	public void deleteRolePrivilegesGroupByRoleId(Integer roleId) {
		getJdbcTemplate().update(DELETE_ROLE_GROUP_BY_ROLE_ID, new Object[]{roleId});
	}
	
	@Override
	public List<Map<String, Object>> queryPrivilegesGroupByRoleIds(Integer[] roleIds) {
		String inSelection = null;
		StringBuilder builder = new StringBuilder();
		if(roleIds.length > 0) {
			builder.append(" and ").append("rg.role_id").append(" in (");
		}
		for(int i = 0; i < roleIds.length; i++) {
			if(i == roleIds.length - 1) {
				builder.append("?)");
			} else {
				builder.append("?,");
			}
		}
		inSelection = builder.toString();
		String sql = SELECT_GROUP_BY_ROLE_IDS + inSelection + " order by g.serial asc";
		Object[] args = new Object[roleIds.length];
		for(int i = 0; i < roleIds.length; i++) {
			args[i] = roleIds[i];
		}
		return getJdbcTemplate().queryForList(sql, args);
	}

	@Override
	public List<AdminRole> queryRoleByUserId(Integer userId) {
		return getJdbcTemplate().query(SELECT_ROLES_BY_USER_ID, new Object[]{userId}, new RowMapper<AdminRole>() {

			@Override
			public AdminRole mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new AdminRole(rs.getInt("id"), 
						rs.getString("role_name"), 
						rs.getString("role_desc"));
			}
		});
	}
}

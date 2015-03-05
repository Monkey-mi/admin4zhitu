package com.imzhitu.admin.privileges.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.privileges.dao.PrivilegesGroupDao;

/**
 * <p>
 * 权限分组数据访问对象
 * </p>
 * 
 * 创建时间：2013-2-16
 * @author ztj
 *
 */
@Repository
public class PrivilegesGroupDaoImpl extends BaseDaoImpl implements PrivilegesGroupDao{
	

	/**
	 * 权限分组表
	 */
	private static String table = Admin.ADMIN_PRIVILEGES_GROUP;
	
	/**
	 * 查询最大权限分组序号
	 */
	private static final String QUERY_MAX_SERIAL = "select max(serial) from " + table;
	
	/**
	 * 根据id删除角色权限分组信息
	 */
	private static final String DELETE_ROLE_GROUP_BY_GROUP_ID = "delete from " + Admin.ADMIN_ROLE_PRIVILEGES_GROUP
			+ " where group_id=?";
	
	@Override
	public Integer queryMaxSerial() {
		return getJdbcTemplate().queryForInt(QUERY_MAX_SERIAL);
	}

	@Override
	public void deleteRolePrivilegesGroupByGroupId(Integer groupId){
		getJdbcTemplate().update(DELETE_ROLE_GROUP_BY_GROUP_ID, new Object[]{groupId});
	}
}

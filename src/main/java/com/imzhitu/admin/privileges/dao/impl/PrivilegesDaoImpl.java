package com.imzhitu.admin.privileges.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.privileges.dao.PrivilegesDao;

/**
 * <p>
 * 权限信息数据访问对象
 * </p>
 * 
 * 创建时间：2013-2-16
 * @author ztj
 *
 */
@Repository
public class PrivilegesDaoImpl extends BaseDaoImpl implements PrivilegesDao{
	

	/**
	 * 权限表
	 */
	private static String table = Admin.ADMIN_PRIVILEGES;
	
	/**
	 * 根据分组信息删除权限信息
	 */
	private static final String DELETE_PRIVILEGES_BY_GROUP_ID = "delete from " + table + " where group_id=?";
	
	/**
	 * 查询最后一条记录
	 */
	private static final String QUERY_LAST_PRIVILEGES = "select * from " + table + " order by id desc limit 1";
	
	/**
	 * 根据权限分组id查询该分组下的最大序号
	 */
	private static final String QUERY_MAX_SERIAL_BY_GROUP_ID = "select max(serial) from " + table + " where group_id=?";
	
	/**
	 * 根据分组id查询权限
	 */
	private static final String QUERY_PRIVILEGES_BY_GROUP_ID = "select * from " + table + " where group_id=?";
	
	@Override
	public void deletePrivilegesByGroupId(Integer groupId) {
		getJdbcTemplate().update(DELETE_PRIVILEGES_BY_GROUP_ID, new Object[]{groupId});
	}
	
	@Override
	public Map<String, Object> queryLastPrivilegesMeta() {
		return getJdbcTemplate().queryForMap(QUERY_LAST_PRIVILEGES);
	}
	
	@Override
	public Integer queryMaxSerialByGroupId(Integer groupId) {
		return getJdbcTemplate().queryForInt(QUERY_MAX_SERIAL_BY_GROUP_ID, groupId);
	}
	
	@Override
	public List<Map<String, Object>> queryPrivilegesByGroupId(Integer groupId) {
		return getJdbcTemplate().queryForList(QUERY_PRIVILEGES_BY_GROUP_ID, groupId);
	}
}

package com.imzhitu.admin.privileges.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.AdminRole;

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
public interface RoleDao extends BaseDao {

	/**
	 * 保存角色--权限分组联系
	 * 
	 * @param roleId
	 * @param groupId
	 * @param serial
	 */
	public void saveRolePrivilegesGroup(Integer roleId, Integer groupId);
	
	/**
	 * 根据角色id查询其所有的权限组id列表
	 * 
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	public Integer[] queryGroupIdsByRoleId(Integer roleId);
	
	/**
	 * 查询角色拥有的最大权限组序号
	 * 
	 * @param roleId
	 */
	public void deleteRolePrivilegesGroupByRoleId(Integer roleId);
	
	/**
	 * 根据角色ids查询所有权限分组信息
	 * 
	 * @param roleIds
	 * @return
	 */
	public List<Map<String, Object>> queryPrivilegesGroupByRoleIds(Integer[] roleIds);
	
	/**
	 * 根据用户id查询角色列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<AdminRole> queryRoleByUserId(Integer userId);
	
	/**
	 * 校验管理员权限，是否具有super_admin或op_admin
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isSuperOrOpAdmin(Integer userId);
	
	/**
	 * 校验当前登陆管理员，是否具有super_admin或op_admin权限
	 *
	 * @return
	 * @author zhangbo 2015年6月16日
	 */
	public boolean isSuperOrOpAdminCurrentLogin();
}

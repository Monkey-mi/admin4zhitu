package com.imzhitu.admin.privileges.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.AdminPrivileges;
import com.imzhitu.admin.common.pojo.AdminPrivilegesGroup;
import com.imzhitu.admin.common.pojo.AdminUserPrivileges;

public interface AdminUserPrivilegesMapper {
	/**
	 * 查询用户所拥有的全部权限id
	 * @param dto
	 * @return
	 */
	public List<Integer> queryUserPrivilegeIdListByUID(AdminUserPrivileges dto);
	
	/**
	 * 查询用户所拥有的全部权限
	 * @param dto
	 * @return
	 */
	public List<AdminUserPrivileges> queryUserPrivilegesByUID(AdminUserPrivileges dto);
	
	/**
	 * 查询所有的权限组
	 * @return
	 */
	public List<AdminPrivilegesGroup> queryPrivilegesGroupList();
	
	/**
	 * 给用户增加权限
	 * @param dto
	 */
	public void addUserPrivilege(AdminUserPrivileges dto);
	
	
	/**
	 * 查询该用户是否具有该权限
	 * @param dto
	 * @return
	 */
	public long queryCountByPrivilegeId(AdminUserPrivileges dto);
	
	/**
	 * 查询一个人权限的总数
	 * @param dto
	 * @return
	 */
	public long queryUserPrivilegeCountByUID(AdminUserPrivileges dto);
	
	/**
	 * 查询所有权限
	 * @return
	 */
	public List<AdminPrivileges> queryAllPrivileges();
	
	/**
	 * 查询所有权限总数
	 * @return
	 */
	public long queryAllPrivilegesCount();
	
	/**
	 * 查询一个人的所有权限，用于分配权限
	 * @param dto
	 * @return
	 */
	public List<AdminPrivileges> queryUserPrivilegesForTable(Integer  userId);
	
	/**
	 * 查询一个人所有的权限总数，用于分配权限
	 * @param dto
	 * @return
	 */
	public long queryUserPrivilegesCountForTable(Integer userId);
	
	/**
	 * 更新有效性
	 * @param dto
	 */
	public void updateValid(AdminUserPrivileges dto);
}

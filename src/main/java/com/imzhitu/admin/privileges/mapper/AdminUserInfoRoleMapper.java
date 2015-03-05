package com.imzhitu.admin.privileges.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.AdminUserRole;

public interface AdminUserInfoRoleMapper {
	
	public void saveUserRole(AdminUserRole userRole);
	
	public void deleteByUserId(Integer uid);
	
	public Integer[] queryRoleIdsByUserId(Integer userId);
	
	/**
	 * 根据用户id查询其角色信息
	 * @param dto
	 * @return
	 */
	public List<AdminUserRole> queryRoleInfoByUserId(AdminUserRole dto);
	
	/**
	 * 查询该用户是否具有该角色
	 * @param userRole
	 * @return
	 */
	public long queryUserRoleCountByUserId(AdminUserRole userRole);
	
	/**
	 * 根据角色名字查询角色id
	 */
	public Integer queryRoleIdByRoleName(String roleName);
}

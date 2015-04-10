package com.imzhitu.admin.privileges.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.AdminUserRole;

public interface AdminUserInfoRoleMapper {
	
	@DataSource("master")
	public void saveUserRole(AdminUserRole userRole);
	
	@DataSource("master")
	public void deleteByUserId(Integer uid);
	
	@DataSource("slave")
	public Integer[] queryRoleIdsByUserId(Integer userId);
	
	/**
	 * 根据用户id查询其角色信息
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<AdminUserRole> queryRoleInfoByUserId(AdminUserRole dto);
	
	/**
	 * 查询该用户是否具有该角色
	 * @param userRole
	 * @return
	 */
	@DataSource("slave")
	public long queryUserRoleCountByUserId(AdminUserRole userRole);
	
	/**
	 * 根据角色名字查询角色id
	 */
	@DataSource("slave")
	public Integer queryRoleIdByRoleName(String roleName);
}

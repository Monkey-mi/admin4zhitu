package com.imzhitu.admin.privileges.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.AdminPrivileges;
import com.imzhitu.admin.common.pojo.AdminPrivilegesGroup;
import com.imzhitu.admin.common.pojo.AdminTimeManageDto;
import com.imzhitu.admin.common.pojo.AdminUserPrivileges;

public interface AdminUserPrivilegesMapper {
	/**
	 * 查询用户所拥有的全部权限id
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<Integer> queryUserPrivilegeIdListByUID(AdminUserPrivileges dto);
	
	/**
	 * 查询用户所拥有的全部权限
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<AdminUserPrivileges> queryUserPrivilegesByUID(AdminUserPrivileges dto);
	
	/**
	 * 查询所有的权限组
	 * @return
	 */
	@DataSource("slave")
	public List<AdminPrivilegesGroup> queryPrivilegesGroupList();
	
	/**
	 * 给用户增加权限
	 * @param dto
	 */
	@DataSource("master")
	public void addUserPrivilege(AdminUserPrivileges dto);
	
	
	/**
	 * 查询该用户是否具有该权限
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryCountByPrivilegeId(AdminUserPrivileges dto);
	
	/**
	 * 查询一个人权限的总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryUserPrivilegeCountByUID(AdminUserPrivileges dto);
	
	/**
	 * 查询所有权限
	 * @return
	 */
	@DataSource("slave")
	public List<AdminPrivileges> queryAllPrivileges();
	
	/**
	 * 查询所有权限总数
	 * @return
	 */
	@DataSource("slave")
	public long queryAllPrivilegesCount();
	
	/**
	 * 查询一个人的所有权限，用于分配权限
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<AdminPrivileges> queryUserPrivilegesForTable(Integer  userId);
	
	/**
	 * 查询一个人所有的权限总数，用于分配权限
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryUserPrivilegesCountForTable(Integer userId);
	
	/**
	 * 更新有效性
	 * @param dto
	 */
	@DataSource("master")
	public void updateValid(AdminUserPrivileges dto);
	
	/**
	 * 插入时间管理模块数据
	 * @param dto 
		*	2015年8月21日
		*	mishengliang
	 */
	@DataSource("master")
	public void insertAdminTimeManage(AdminTimeManageDto dto)throws Exception;
	
	/**
	 * 查询时间管理模块的数据
	 * @return 
		*	2015年8月21日
		*	mishengliang
	 */
	@DataSource("slave")
	public List<AdminTimeManageDto> queryAdminTimeManage();
	
	/**
	 * 通过userId删除时间管理模块中的数据
	 * @param userId 
		*	2015年8月21日
		*	mishengliang
	 */
	@DataSource("master")
	public void deleteAdminTimeManageByUserId(Integer userId);
}

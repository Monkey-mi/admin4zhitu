package com.imzhitu.admin.privileges.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.AdminPrivileges;
import com.imzhitu.admin.common.pojo.AdminPrivilegesGroup;
import com.imzhitu.admin.common.pojo.AdminRole;
import com.imzhitu.admin.common.pojo.AdminUser;


/**
 * <p>
 * 权限管理业务逻辑访问对象
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface PrivilegesService extends BaseService {

	/*
	 * 权限分组管理业务逻辑
	 */
	
	/**
	 * 构建权限分组信息分页列表JSON
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception 
	 */
	public void buildPrivilegesGroupPaginationJSON(Integer page, Integer rows, Map<String,Object> jsonMap) throws Exception;
	
	/**
	 * 根据id查询权限分组信息
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public AdminPrivilegesGroup getPrivilegesGroupById(Integer id) throws Exception;

	/**
	 * 获取所有权限分组信息
	 * @return
	 */
	public List<AdminPrivilegesGroup> getAllPrivilegesGroup() throws Exception;
	
	/**
	 * 检测权限分组名是否存在
	 * 
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	public boolean checkPrivilegesGroupNameExist(String name) throws Exception;
	
	/**
	 * 保存权限分组信息
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public Integer savePrivilegesGroup(AdminPrivilegesGroup group) throws Exception;
	
	/**
	 * 更新权限分组信息
	 * @param group
	 * @param id
	 * @throws Exception 
	 */
	public void updatePrivilegesGroup(AdminPrivilegesGroup group, Integer id) throws Exception;
	
	/**
	 * 根据id删除权限分组信息
	 * @param id
	 * @throws Exception 
	 */
	public void deletePrivilegesGroupById(String idsStr) throws Exception;
	
	public void buildPrivilegesPaginationJSON(Integer groupId, Integer page, Integer rows, Map<String,Object> jsonMap) throws Exception;
	
	/**
	 * 根据id查询权限分组信息
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public AdminPrivileges getPrivilegesById(Integer id) throws Exception;
	
	/**
	 * 检测权限名称是否存在
	 * @return
	 * @throws Exception 
	 */
	public boolean checkPrivilegesNameExist(String name) throws Exception;
	
	/**
	 * 检测权限URL是否存在
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public boolean checkPrivilegesURLExist(String url) throws Exception;
	
	/**
	 * 保存权限
	 * @param privileges
	 * @return
	 * @throws Exception 
	 */
	public Integer savePrivileges(AdminPrivileges privileges) throws Exception;
	
	/**
	 * 更新权限分组信息
	 * @param group
	 * @param id
	 * @throws Exception 
	 */
	public void updatePrivileges(AdminPrivileges privileges, Integer id) throws Exception;
	
	/**
	 * 根据id删除权限分组信息
	 * @param id
	 * @throws Exception 
	 */
	public void deletePrivilegesById(String idsStr) throws Exception;
	
	/**
	 * 构建角色信息分页JSON
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildRolePaginationJSON(Integer page, Integer rows, Map<String,Object> jsonMap) throws Exception;
	
	/**
	 * 获取所有角色信息
	 * 
	 * @return
	 */
	public List<AdminRole> getAllRole() throws Exception;
	
	/**
	 * 根据id获取角色信息
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public AdminRole getRoleById(Integer id) throws Exception;
	
	/**
	 * 检测角色名称是否存在
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	public boolean checkRoleNameExist(String name) throws Exception;
	
	/**
	 * 保存角色信息
	 * @param role
	 * @return
	 * @throws Exception 
	 */
	public Integer saveRole(AdminRole role, String[] groupIds) throws Exception;
	
	/**
	 * 根据ids批量删除角色信息
	 * @param idsStr
	 * @throws Exception 
	 */
	public void deleteRoleByIds(String idsStr) throws Exception;

	/**
	 * 更新角色信息 
	 * @param role
	 * @param id
	 * @throws Exception 
	 */
	public void updateRole(AdminRole role, String[] groupIds, Integer id) throws Exception;

	/**
	 * 根据角色获取导航菜单
	 * @param roleIds
	 * @return
	 * @throws Exception 
	 */
	public void buildNavMenuJSONByRoleIds(Integer[] roleIds,Map<String,Object> jsonMap) throws Exception;
	
	/**
	 * 根据用户id来获取导航菜单
	 * @param uid
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildNavMenuJSONByUserId(Integer uid,Map<String,Object> jsonMap) throws Exception;
	
	/**
	 * 查询用户所拥有的权限组
	 * @param uid
	 * @param jsonMap
	 * @throws Exception
	 */
	public List<AdminUser> querySubordinatesByUserId(Integer uid)throws Exception;
	
	/**
	 * 查询用户所拥有的权限组
	 * @param uid
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryUserPrivilegesByUserId(Integer uid,int page, int row,Map<String,Object> jsonMap)throws Exception;
	
	/**
	 * 为用户增加权限
	 * @param uid
	 * @param privilegeIdsStr
	 * @param operatorId
	 * @throws Exception
	 */
	public void addPrivilegesToUser(Integer uid,String privilegeIdsStr,Integer operatorId)throws Exception;
	
	/**
	 * 查询用户所拥有的权限组 用于分配权限
	 * @param uid
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryUserPrivilegesByUserIdForTable(Integer uid,int page, int row,Map<String,Object> jsonMap)throws Exception;
	
	/**
	 * 删除权限
	 * @param uid
	 * @param privilegeIdsStr
	 * @param operatorId
	 * @throws Exception
	 */
	public void deletePrivileges(Integer uid,String privilegeIdsStr,Integer operatorId)throws Exception;
}

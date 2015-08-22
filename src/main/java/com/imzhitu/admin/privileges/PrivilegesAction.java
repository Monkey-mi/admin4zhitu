package com.imzhitu.admin.privileges;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.xml.crypto.Data;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.Log;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.AdminPrivileges;
import com.imzhitu.admin.common.pojo.AdminPrivilegesGroup;
import com.imzhitu.admin.common.pojo.AdminRole;
import com.imzhitu.admin.common.pojo.AdminTimeManageDto;
import com.imzhitu.admin.common.pojo.AdminUser;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.privileges.service.PrivilegesService;

/**
 * <p>
 * 权限管理控制器
 * </p>
 * 
 * 创建时间：2013-2-16
 * @author ztj
 *
 */
public class PrivilegesAction extends BaseCRUDAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4325213446028700701L;
	
	
	private AdminPrivilegesGroup privilegesGroup;
	private AdminPrivileges privileges;
	private AdminRole role;
	private Integer groupId;
	private Integer id;
	private String ids;
	private String privilegeIdsStr;	//权限ids 字符串
	private Integer userId;			//用户id

	private Integer userNameId;
	private String workStartTime;
	private String workEndTime;
	private String userNameIds;
	
	@Autowired
	private PrivilegesService privilegesService;
	
	public String getPrivilegeIdsStr() {
		return privilegeIdsStr;
	}

	public void setPrivilegeIdsStr(String privilegeIdsStr) {
		this.privilegeIdsStr = privilegeIdsStr;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	
	public PrivilegesService getPrivilegesService() {
		return privilegesService;
	}

	public void setPrivilegesService(PrivilegesService privilegesService) {
		this.privilegesService = privilegesService;
	}

	public AdminPrivilegesGroup getPrivilegesGroup() {
		return privilegesGroup;
	}

	public void setPrivilegesGroup(AdminPrivilegesGroup privilegesGroup) {
		this.privilegesGroup = privilegesGroup;
	}
	
	public AdminPrivileges getPrivileges() {
		return privileges;
	}

	public void setPrivileges(AdminPrivileges privileges) {
		this.privileges = privileges;
	}

	public AdminRole getRole() {
		return role;
	}

	public void setRole(AdminRole role) {
		this.role = role;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	
	
	public Integer getUserNameId() {
		return userNameId;
	}

	public void setUserNameId(Integer userNameId) {
		this.userNameId = userNameId;
	}

	public String getWorkStartTime() {
		return workStartTime;
	}

	public void setWorkStartTime(String workStartTime) {
		this.workStartTime = workStartTime;
	}

	public String getWorkEndTime() {
		return workEndTime;
	}

	public void setWorkEndTime(String workEndTime) {
		this.workEndTime = workEndTime;
	}

	
	/*
	 * 权限分组管理子模块
	 */
	/**
	 * 查询权限分组列表
	 * @return
	 */
	public String queryPrivilegesGroup() {
		try {
			privilegesService.buildPrivilegesGroupPaginationJSON(page, rows, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据id查询权限分组信息
	 * @return
	 */
	public String queryPrivilegesGroupById() {
		try {
			AdminPrivilegesGroup group = privilegesService.getPrivilegesGroupById(id);
			JSONUtil.querySuccess(group, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询所有分组信息 
	 * @return
	 */
	public String queryAllPrivilegesGroup() {
		PrintWriter out = null;
		try {
			List<AdminPrivilegesGroup> groupList = privilegesService.getAllPrivilegesGroup();
			JSONArray jsArray = JSONArray.fromObject(groupList);
			out = response.getWriter();
			out.print(jsArray.toString());
			out.flush();
		} catch (Exception e) {
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * 检测分组名称是否存在
	 * 
	 * @return
	 */
	public String checkPrivilegesGroupNameExist() {
		String groupName = StringUtil.encodeToUTF8(privilegesGroup.getGroupName());
		try {
			if(privilegesService.checkPrivilegesGroupNameExist(groupName)) {
				JSONUtil.optSuccess("0", jsonMap);
			} else {
				JSONUtil.optSuccess("-1", jsonMap);
			}
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 添加权限分组信息
	 * @return
	 */
	public String savePrivilegesGroup() {
		try {
			privilegesService.savePrivilegesGroup(privilegesGroup);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新权限分组信息
	 * @return
	 */
	public String updatePrivilegesGroup() {
		try {
			privilegesService.updatePrivilegesGroup(privilegesGroup, id);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除权限分组
	 * @return
	 */
	public String deletePrivilegesGroup() {
		try {
			privilegesService.deletePrivilegesGroupById(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/*
	 * 权限管理子模块
	 */
	
	/**
	 * 查询权限
	 * @return
	 */
	public String queryPrivileges() {
		try {
			privilegesService.buildPrivilegesPaginationJSON(groupId, page, rows, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据id查询权限信息
	 * @return
	 */
	public String queryPrivilegesById() {
		try {
			AdminPrivileges privileges = privilegesService.getPrivilegesById(id);
			JSONUtil.querySuccess(privileges, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 检测权限名称是否存在
	 * 
	 * @return
	 */
	public String checkPrivilegesNameExist() {
		String privilegesName = StringUtil.encodeToUTF8(privileges.getPrivilegesName());
		try {
			if(privilegesService.checkPrivilegesNameExist(privilegesName)) {
				JSONUtil.optSuccess("0", jsonMap);
			} else {
				JSONUtil.optSuccess("-1", jsonMap);
			}
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 检测权限路径是否存在
	 * 
	 * @return
	 */
	public String checkPrivilegesURLExist() {
		String privilegesURL = privileges.getPrivilegesURL();
		try {
			if(privilegesService.checkPrivilegesURLExist(privilegesURL)) {
				JSONUtil.optSuccess("0", jsonMap);
			} else {
				JSONUtil.optSuccess("-1", jsonMap);
			}
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存权限
	 * @return
	 */
	public String savePrivileges() {
		try {
			privilegesService.savePrivileges(privileges);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新权限信息
	 * @param privileges
	 * @param id
	 * @return
	 */
	public String updatePrivileges() {
		try {
			privilegesService.updatePrivileges(privileges, id);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除权限信息
	 * @return
	 */
	public String deletePrivileges() {
		try {
			privilegesService.deletePrivilegesById(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/*
	 * 系统角色管理子模块
	 */
	
	/**
	 * 查询角色列表
	 * @return
	 */
	public String queryRole() {
		try {
			privilegesService.buildRolePaginationJSON(page, rows, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据id查询角色信息
	 */
	public String queryRoleById() {
		try {
			AdminRole role = privilegesService.getRoleById(id);
			JSONUtil.querySuccess(role, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询所有角色信息
	 * 
	 * @return
	 */
	public String queryAllRole() {
		PrintWriter out = null;
		try {
			List<AdminRole> list = privilegesService.getAllRole();
			JSONArray jsArray = JSONArray.fromObject(list);
			out = response.getWriter();
			out.print(jsArray.toString());
			out.flush();
		} catch (Exception e) {
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * 检测角色名称是否存在
	 * @return
	 */
	public String checkRoleNameExist() {
		String roleName = StringUtil.encodeToUTF8(role.getRoleName());
		try {
			if(privilegesService.checkRoleNameExist(roleName)) {
				JSONUtil.optSuccess("0", jsonMap);
			} else {
				JSONUtil.optSuccess("-1", jsonMap);
			}
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存角色信息
	 * @return
	 */
	public String saveRole() {
		try {
			String[] groupIds = request.getParameterValues("groupIds");
			privilegesService.saveRole(role, groupIds);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新角色信息
	 * @return
	 */
	public String updateRole() {
		try {
			String[] groupIds = request.getParameterValues("groupIds");
			privilegesService.updateRole(role, groupIds, id);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除角色信息
	 * @return
	 */
	public String deleteRole() {
		try {
			privilegesService.deleteRoleByIds(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 初始化导航菜单
	 * @return
	 */
	public String initNavMenu() {
		Log.debug("初始化导航菜单");
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			privilegesService.buildNavMenuJSONByUserId(user.getId(), jsonMap);
		}catch (Exception e) {
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询用户权限
	 * @return
	 */
	public String queryUserPrivilegesByUserId(){
		try{
			if(userId == null){
				AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				userId = user.getId();
			}
			privilegesService.queryUserPrivilegesByUserId(userId,page,rows, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询用户权限，用于分配权限
	 * @return
	 */
	public String queryUserPrivilegesByUserIdForTable(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			privilegesService.queryUserPrivilegesByUserIdForTable(user.getId(),page,rows, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询下属人员
	 * @return
	 */
	public String querySubordinatesByUserId(){
		PrintWriter out =null;
		try{
			out = response.getWriter();
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<AdminUser> list = privilegesService.querySubordinatesByUserId(user.getId());
			JSONArray ja = JSONArray.fromObject(list);
			out.print(ja.toString());
			out.flush();
		}catch(Exception e){
			e.printStackTrace();
			jsonMap.clear();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(json.toString());
			out.flush();
		}finally{
			out.close();
		}
		return null;
	}
	
	/**
	 * 添加等级
	 * @return
	 */
	public String addPrivilegesToUser(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			privilegesService.addPrivilegesToUser(userId, privilegeIdsStr, user.getId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String deleteUserPrivileges(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			privilegesService.deletePrivileges(userId, privilegeIdsStr, user.getId());
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String addAdminTimeManage(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			privilegesService.addAdminTimeManage(userNameId, workStartTime, workEndTime, user.getId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 查询管理员时间管理模块的信息
	 * @return
	 */
	public String queryAdminTimeManage(){
		try{
			privilegesService.queryAdminTimeManage(maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
//		jsonMap.put("result", 0);
		}catch(Exception e){
		}
		return StrutsKey.JSON;
	}
	
	public String deleteAdminTimeManageByUserIds(){
		try{
			privilegesService.deleteAdminTimeManageByUserIds(userNameIds);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
}

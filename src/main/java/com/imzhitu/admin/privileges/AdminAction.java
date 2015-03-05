package com.imzhitu.admin.privileges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.SessionKeies;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.AdminUser;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.privileges.service.AdminService;

/**
 * <p>
 * 后台管理系统登录Action
 * </p>
 * 
 * 创建时间：2012-11-07
 * 
 * @author ztj
 * 
 */
public class AdminAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1025479648125575248L;
	
	private AdminUser adminUser = new AdminUser();
	private Integer id;
	private String ids;
	private Boolean addAllTag = false;
	
	@Autowired
	private AdminService adminService;
	
	/**
	 * 管理员登出接口
	 * 
	 * @return
	 */
	public String logout() {
		session.remove(SessionKeies.ADMIN_USER_NAME);
		session.remove(SessionKeies.ADMIN_ROLE_IDS);
		session.clear();
		return LOGIN;
	}

	/**
	 * 管理员注册接口
	 * 
	 * @return
	 */
	public String register() {
		try {
			String[] roleIds = request.getParameterValues("roleIds");
			adminService.register(adminUser, roleIds);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 检测账号是否存在
	 * 
	 * @return
	 */
	public String checkLoginCode() {
		try {
			adminUser.setUserName(null);
			if (adminService.checkExist(adminUser)) {
				JSONUtil.checkExist(jsonMap);
			} else {
				JSONUtil.checkNotExist(jsonMap);
			}
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 检测用户名是否存在
	 * 
	 * @return
	 */
	public String checkUserName() {
		try {
			String userName = StringUtil.encodeToUTF8(adminUser.getUserName());
			adminUser.setLoginCode(null);
			adminUser.setUserName(userName);
			if (adminService.checkExist(adminUser)) {
				JSONUtil.checkExist(jsonMap);
			} else {
				JSONUtil.checkNotExist(jsonMap);
			}
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 查询用户信息列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryUserInfo() throws Exception {
		try {
			adminService.buildUserInfo(addAllTag, adminUser, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 根据id查询用户信息
	 * 
	 * @return
	 */
	public String queryUserInfoById() {
		try {
			AdminUser userInfo = adminService.getUserInfoById(id);
			JSONUtil.querySuccess(userInfo, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 更新用户信息
	 * 
	 * @return
	 */
	public String updateUserInfo() {
		String[] roleIds = request.getParameterValues("roleIds");
		try {
			adminService.updateUserInfo(adminUser, roleIds);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}

		return StrutsKey.JSON;
	}

	/**
	 * 更新密码
	 * 
	 * @return
	 */
	public String updatePassword() {
		AdminUserDetails user = (AdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer id = -1;
		if (user != null) {
			id = user.getId();
		}
		try {
			adminUser.setId(id);
			adminService.updateUserInfo(adminUser, null);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 删除用户信息
	 * 
	 * @return
	 */
	public String deleteUserInfo() {
		try {
			adminService.deleteUserInfo(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AdminUser getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Boolean getAddAllTag() {
		return addAllTag;
	}

	public void setAddAllTag(Boolean addAllTag) {
		this.addAllTag = addAllTag;
	}
	
}

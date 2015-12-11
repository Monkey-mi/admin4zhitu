package com.imzhitu.admin.common.util;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.imzhitu.admin.common.pojo.AdminUser;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.privileges.dao.AdminDao;
import com.imzhitu.admin.privileges.dao.impl.AdminDaoImpl;

/**
 * <p>
 * 登陆工具类
 * </p>
 * 
 * @author lynch
 *
 */
public class AdminLoginUtil {
	
	/**
	 * 管理员账号集合
	 * @author zhangbo	2015年12月9日
	 */
	private static List<AdminUser> adminUserList;
			
	/**
	 * 获取当前登陆用户id
	 * 
	 * @return
	 * @author lynch
	 */
	public static Integer getCurrentLoginId() {
		Integer uid = -1;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			uid = ((AdminUserDetails) auth.getPrincipal()).getId();
		}
		return uid;
	}
	
	/**
	 * 根据管理员账号id，获取管理员名称
	 * 
	 * @param adminUserId	管理员账号id
	 * @return
	 * @author zhangbo	2015年12月9日
	 */
	public static String getAdminUserName(Integer adminUserId) {
		if ( adminUserList == null ) {
			AdminDao adminDao = new AdminDaoImpl();
			adminUserList = adminDao.queryUserInfoList(1, 100, new HashMap<String, Object>());
		}
		String adminUserName = "";
		for (AdminUser adminUser : adminUserList) {
			if ( adminUserId.equals(adminUser.getId())) {
				adminUserName = adminUser.getUserName();
			}
		}
		return adminUserName;
	}
	
}

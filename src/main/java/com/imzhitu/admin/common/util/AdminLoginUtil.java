package com.imzhitu.admin.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.imzhitu.admin.common.pojo.AdminUserDetails;

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
	
}

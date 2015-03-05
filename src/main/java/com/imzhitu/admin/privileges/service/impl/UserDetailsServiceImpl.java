package com.imzhitu.admin.privileges.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.common.pojo.AdminUserDto;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.common.pojo.AdminRole;
import com.imzhitu.admin.privileges.dao.AdminDao;
import com.imzhitu.admin.privileges.dao.RoleDao;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Override
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {
		AdminUserDto dto = null;
		Integer uid = null;
		try {
			uid = Integer.parseInt(username);
			dto = adminDao.queryUserInfo(uid);
		} catch(NumberFormatException e) {
			dto = adminDao.queryUserInfo(username);
			uid = dto.getId();
		}
		
		List<AdminRole> roles = null;
		if(dto.getValid().equals(Tag.TRUE)) {
			roles = roleDao.queryRoleByUserId(uid);
		} else {
			roles = new ArrayList<AdminRole>();
		}
		return new AdminUserDetails(
				dto.getId(), 
				dto.getLoginCode(), 
				dto.getUserName(),
				dto.getPassword(), 
				roles);
	}

}

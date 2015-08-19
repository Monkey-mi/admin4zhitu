package com.imzhitu.admin.privileges.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.HTSException;
import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.MD5Encrypt;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.AdminUser;
import com.imzhitu.admin.common.pojo.AdminUserRole;
import com.imzhitu.admin.privileges.mapper.AdminUserInfoMapper;
import com.imzhitu.admin.privileges.mapper.AdminUserInfoRoleMapper;
import com.imzhitu.admin.privileges.service.AdminService;

/**
 * <p>
 * 后台管理基本业务逻辑访问对象
 * </p>
 * 
 * 创建时间：2012-11-08
 * @author ztj
 *
 */
@Service
public class AdminServiceImpl extends BaseServiceImpl implements AdminService{

	
	private Logger logger = Logger.getLogger(AdminService.class);
	/**
	 * 默认密码
	 */
	private static final String DEFAULT_PASS = "huanghao20110820";

	@Autowired
	private AdminUserInfoMapper adminUserInfoMapper;
	@Autowired
	private AdminUserInfoRoleMapper adminUserInfoRoleMapper;

	@Override
	public void register(AdminUser userInfo,String[] roleIds) throws Exception {
		String password = userInfo.getPassword();
		if(password == null) {
			password = DEFAULT_PASS;
		}
		byte[] passEncrypt = MD5Encrypt.encryptByMD5(password);
		userInfo.setPasswordEncrypt(passEncrypt);
		Integer id = adminUserInfoMapper.saveUser(userInfo);
		if(roleIds != null) {
			for(int i = 0; i < roleIds.length; i++) {
				Integer roleId = Integer.parseInt(roleIds[i]);
				AdminUserRole urole = new AdminUserRole();
				urole.setUserId(id);
				urole.setRoleId(roleId);
				adminUserInfoRoleMapper.saveUserRole(urole);
			}
		}
	}
	
	@Override
	public void updateUserInfo(AdminUser userInfo, String[] roleIds) throws Exception {
		String password = userInfo.getPassword();
		byte[] enPass = null;
		if(password != null && !password.equals("")) {
			try {
				enPass = MD5Encrypt.encryptByMD5(password);
			} catch (NoSuchAlgorithmException e) {
				throw new HTSException(e);
			}
			userInfo.setPasswordEncrypt(enPass);
		}
		adminUserInfoMapper.updateUser(userInfo);
		if(roleIds != null) { //重新添加角色信息
			for(int i = 0; i < roleIds.length; i++) {
				Integer roleId = Integer.parseInt(roleIds[i]);
				AdminUserRole urole = new AdminUserRole();
				urole.setUserId(userInfo.getId());
				urole.setRoleId(roleId);
				adminUserInfoRoleMapper.saveUserRole(urole);
			}
		}
	}

	@Override
	public void deleteUserInfo(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		adminUserInfoMapper.deleteByIds(ids);
		for(Integer userId : ids) {
			adminUserInfoRoleMapper.deleteByUserId(userId);
		}
	}
	
	@Override
	public AdminUser getUserInfoById(Integer id) throws Exception {
		AdminUser userInfo = adminUserInfoMapper.queryAdminUserById(id);
		Integer[] roleIds = adminUserInfoRoleMapper.queryRoleIdsByUserId(id);
		userInfo.setRoleIds(roleIds);
		return userInfo;
	}
	
	@Override
	public boolean checkExist(AdminUser adminUser) throws Exception {
		boolean flag = false;
		long count = adminUserInfoMapper.queryCount(adminUser);
		if(count > 0) {
			flag = true;
		}
		return flag;
	}

	
	@Override
	public void buildUserInfo(final Boolean addAllTag, final AdminUser adminUser,
			final int page,int rows, Map<String, Object> jsonMap) throws Exception {
		
		buildNumberDtos(adminUser, page, rows, jsonMap, new NumberDtoListAdapter<AdminUser>() {
			
			@Override
			public long queryTotal(AdminUser dto) {
				return adminUserInfoMapper.queryCount(adminUser);
			}
			
			@Override
			public List<? extends AbstractNumberDto> queryList(AdminUser dto) {
				List<AdminUser> userList = adminUserInfoMapper.queryUsers(dto);
				if(page == 1 && addAllTag) {
					AdminUser u = new AdminUser();
					u.setId(-1);
					u.setUserName("所有用户");
					userList.add(0,u);
				}
				return userList;
			}
		});
	}

	@Override
	public void logProperty() {
//		logger.debug(test);
	}
	
}

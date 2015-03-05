package com.imzhitu.admin.privileges.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.AdminUser;

/**
 * <p>
 * 管理员数据映射接口
 * </p>
 * 
 * 创建时间：2014-8-19
 * @author tianjie
 *
 */
public interface AdminUserInfoMapper {

	public Integer saveUser(AdminUser adminUser);
	
	public void updateUser(AdminUser adminUser);
	
	public void deleteByIds(Integer[] ids);
	
	public AdminUser queryAdminUserById(Integer id);
	
	public List<AdminUser> queryUsers(AdminUser adminUser);
	
	public long queryCount(AdminUser adminUser);
	
	/**
	 * 查询所有人员
	 * @return
	 */
	public List<AdminUser> queryUserNameAndId();
	
	/**
	 * 查询所有非超级管理员非运营管理员
	 * @param adminUser
	 * @return
	 */
	public List<AdminUser> queryAllNotSuperAdminOrOpAdminUserInfo();
	
}

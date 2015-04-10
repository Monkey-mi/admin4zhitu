package com.imzhitu.admin.privileges.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
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

	@DataSource("master")
	public Integer saveUser(AdminUser adminUser);
	
	@DataSource("master")
	public void updateUser(AdminUser adminUser);
	
	@DataSource("master")
	public void deleteByIds(Integer[] ids);
	
	@DataSource("slave")
	public AdminUser queryAdminUserById(Integer id);
	
	@DataSource("slave")
	public List<AdminUser> queryUsers(AdminUser adminUser);
	
	@DataSource("slave")
	public long queryCount(AdminUser adminUser);
	
	/**
	 * 查询所有人员
	 * @return
	 */
	@DataSource("slave")
	public List<AdminUser> queryUserNameAndId();
	
	/**
	 * 查询所有非超级管理员非运营管理员
	 * @param adminUser
	 * @return
	 */
	@DataSource("slave")
	public List<AdminUser> queryAllNotSuperAdminOrOpAdminUserInfo();
	
}

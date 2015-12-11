package com.imzhitu.admin.privileges.service;

import java.util.Map;

import com.hts.web.base.HTSException;
import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.AdminUser;

/**
 * <p>
 * 后台管理基本业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface AdminService extends BaseService{
	
	/**
	 * 注册管理员账号
	 * 
	 * @param userInfo
	 * @param roleIds
	 * @throws Exception
	 */
	public void register(AdminUser userInfo,String[] roleIds) throws Exception;
	
	/**
	 * 更新用户信息
	 * 
	 * @param userInfo
	 * @param roleIds
	 * @throws Exception
	 */
	public void updateUserInfo(AdminUser userInfo, String[] roleIds) throws Exception;

	/**
	 * 删除用户信息
	 * 
	 * @param idsStr
	 * @throws HTSException 
	 */
	public void deleteUserInfo(String idsStr) throws Exception;
	
	/**
	 * 根据id查询用户信息
	 * 
	 * @param id
	 * @return
	 * @throws HTSException 
	 */
	public AdminUser getUserInfoById(Integer id) throws Exception;
	
	/**
	 * 判断用户是否存在
	 * 
	 * @param adminUser
	 * @return
	 * @throws Exception
	 */
	public boolean checkExist(AdminUser adminUser) throws Exception;
	
	/**
	 * 查询用户信息
	 * 
	 * @param valid
	 * @param addAllTag
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildUserInfo(Boolean addAllTag, AdminUser adminUser, int page, int rows,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 演示如何从properties文件中获取参数并注入到bean属性中
	 */
	public void logProperty();
	
	/**
	 * 根据id，查询管理员名称
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhangbo	2015年12月11日
	 */
	public String getAdminUserNameById(Integer id) throws Exception; 
	
}

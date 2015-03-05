package com.imzhitu.admin.privileges.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.AdminUser;
import com.imzhitu.admin.common.pojo.AdminUserDto;

/**
 * <p>
 * 后台管理基本数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * 
 * @author ztj
 * 
 */
public interface AdminDao extends BaseDao {

	/**
	 * 根据账号查询管理员账号信息
	 * 
	 * @param loginCode
	 * @return
	 * @throws SQLException
	 */
	public AdminUserDto queryUserInfo(Map<String, Object> attrMap);

	/**
	 * 根据账号查询账号信息
	 * @param loginCode
	 * @return
	 */
	public AdminUserDto queryUserInfo(String loginCode);
	
	/**
	 * 根据id查询账号信息
	 * @param userId
	 * @return
	 */
	public AdminUserDto queryUserInfo(Integer userId);
	
	/**
	 * 查询用户总数
	 * 
	 * @param conn
	 * @param attrMap
	 * @return
	 * @throws SQLException
	 */
	public Integer queryUserInfoTotalCount(Map<String, Object> attrMap) ;

	/**
	 * 查询用户信息列表
	 * 
	 * @param conn
	 * @param page
	 * @param rows
	 * @param attrMap
	 * @return
	 * @throws SQLException
	 */
	public List<AdminUser> queryUserInfoList(int page, int rows, Map<String, Object> attrMap);

	/**
	 * 保存用户--角色关联关系
	 * 
	 * @param conn
	 * @param userId
	 * @param roleId
	 * @throws SQLException
	 */
	public void saveUserInfoRole(Integer userId, Integer roleId);

	/**
	 * 根据用户id查询其拥有的角色id列表
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Integer[] queryRoleIdsByUserId(Integer userId);

	/**
	 * 根据用户id删除用户拥有的角色信息
	 * 
	 * @param conn
	 * @param userId
	 * @throws SQLException
	 */
	public void deleteUserInfoRoleByUserId(Integer userId);
	
	/**
	 * 更新密码
	 * 
	 * @param userId
	 * @param password
	 */
	public void updatePassword(Integer userId, byte[] password);
}

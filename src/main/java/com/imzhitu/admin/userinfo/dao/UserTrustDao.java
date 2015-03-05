package com.imzhitu.admin.userinfo.dao;

import java.util.Date;

import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.UserTrust;

public interface UserTrustDao extends BaseDao{
	/**
	 * 增加用户信任记录
	 * @param userId
	 * @param addDate
	 * @param modifyDate
	 * @param trust
	 */
	public void addUserTrust(Integer userId,Date addDate,Date modifyDate,Integer trust,Integer operatorId);
	
	/**
	 * 根据用户ids删除用户信任
	 * @param uids
	 */
	public void delUserTrust(Integer[] uids);
	
	/**
	 * 更新用户信任记录
	 * @param userId
	 * @param modifyDate
	 * @param trust
	 */
	public void updateUserTrustByUid(Integer userId,Date modifyDate,Integer trust,Integer operatorId);
	
	/**
	 * 更新用户信任记录
	 * @param userId
	 * @param modifyDate
	 * @param trust
	 */
	public void updateUserTrustById(Integer id,Date modifyDate,Integer trust,Integer operatorId);
	
	/**
	 * 根据用户id查询用户信任记录
	 * @param userId
	 * @return
	 */
	public UserTrust queryUserTrustByUid(Integer userId);
	
	/**
	 * 根据id查询用户信任记录
	 * @param userId
	 * @return
	 */
	public UserTrust queryUserTrustById(Integer id);

}

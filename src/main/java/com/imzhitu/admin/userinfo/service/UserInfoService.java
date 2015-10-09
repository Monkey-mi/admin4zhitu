package com.imzhitu.admin.userinfo.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.UserWithInteract;

/**
 * <p>
 * 用户管理业务逻辑接口
 * </p>
 * 
 * 创建时间：2013-8-29
 * @author ztj
 *
 */
public interface UserInfoService extends BaseService {

	/**
	 * 构建用户列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 */
	public void buildUser(Integer  userId,String userName,Integer platformVerify,int maxId, int start, int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 屏蔽用户
	 * @param userId
	 */
	public void shieldUser(Integer userId) throws Exception;
	
	/**
	 * 解除屏蔽用户
	 * 
	 * @param userid
	 */
	public void unShieldUser(Integer userId) throws Exception;
	
	/**
	 * 保存用户
	 * 
	 * @param userName
	 * @param loginCode
	 * @param userAvatar
	 * @param userAvatarL
	 */
	public void saveUser(String userName, String loginCode, String userAvatar, String userAvatarL) throws Exception;
	
	/**
	 * 更新信任标记
	 * 
	 * @param userId
	 * @param trust
	 * @throws Exception
	 */
	public void updateTrust(Integer userId, Integer trust,Integer operatorId) throws Exception;
	
	/**
	 * 获取互动标志信息
	 * 
	 * @param userList
	 */
	public void extractInteractInfo(final List<? extends UserWithInteract> userList);
	
	/**
	 * 更新用户签名
	 */
	public void updateSignature(String userInfoJSON) throws Exception;

	/**
	 * 交换用户数据
	 * 
	 * @param id
	 * @param toId
	 */
	public void updateExchangeUsers(Integer id, Integer toId) throws Exception;
	
	/**
	 * 查询用户信息
	 * @param userId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryUserByUserIdAndCheckIsZombie(Integer userId,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 查询 用户的信息，主要是查询用户的推荐状态
	 * @param userId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryUserInfoByUserId(Integer userId,Map<String,Object>jsonMap)throws Exception;

	/**
	 * 全局过滤用户名
	 * 
	 * @throws Exception
	 */
	public void trimUserName() throws Exception;
	
}



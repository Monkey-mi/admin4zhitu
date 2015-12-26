package com.imzhitu.admin.userinfo.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.UserWithInteract;
import com.imzhitu.admin.common.pojo.UserInfo;

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
	 * 根据id获取用户信息
	 * 
	 * @param userId	用户id
	 * @return
	 * @throws Exception
	 * @author zhangbo	2015年11月26日
	 */
	UserInfo getUserInfo(Integer userId) throws Exception;
	
	/**
	 * 根据用户名称得到用户id集合，名称为模糊匹配
	 * 
	 * @param userName	用户名
	 * 
	 * @return List<Integer>	用户id集合
	 * 
	 * @throws Exception
	 * @author zhangbo	2015年12月17日
	 */
	List<Integer> getUserIdsByName(String userName) throws Exception;

	/**
	 * 查询默认用户背景图片
	 * 
	 * @param jsonMap
	 * @author zhangbo	2015年12月25日
	 */
	void queryDefaultBackground(Map<String, Object> jsonMap);
	
	/**
	 * 批量删除默认用户背景图片
	 * 
	 * @author zhangbo	2015年12月25日
	 */
	void batchDeleteDefaultBackground(Integer[] ids);

	/**
	 * 刷新默认背景图片缓存
	 * 
	 * @author zhangbo	2015年12月25日
	 */
	void refreshDefaultBackgroundCache();

	/**
	 * @param background
	 * @author zhangbo	2015年12月25日
	 */
	void saveDefaultBackground(String background);

}



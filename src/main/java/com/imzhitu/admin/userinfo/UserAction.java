package com.imzhitu.admin.userinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.PlatFormCode;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.hts.web.userinfo.service.impl.UserInfoServiceImpl;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.userinfo.service.UserInfoService;

/**
 * <p>
 * 用户管理控制器
 * </p>
 * 
 * 创建时间：2013-8-29
 * @author ztj
 */
public class UserAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5773870220181896581L;
	
	private Integer id;
	private Integer toId;
	private Integer userId;
	private String userName;
	private String loginCode;
	private String userAvatar;
	private String userAvatarL;
	private Integer trust;
	private String signature;
	private String userInfoJSON;
	private Integer platformVerify;
	private Integer sex;
	
	
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private com.hts.web.userinfo.service.UserInfoService webUserInfoService;

	public UserInfoService getUserInfoService() {
		return userInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	
	public com.hts.web.userinfo.service.UserInfoService getWebUserInfoService() {
		return webUserInfoService;
	}

	public void setWebUserInfoService(
			com.hts.web.userinfo.service.UserInfoService webUserInfoService) {
		this.webUserInfoService = webUserInfoService;
	}

	/**
	 * 检测织图账号是否存在
	 * 
	 * @return
	 */
	public String checkLoginCodeExists() {
		try {
			Integer isExists = webUserInfoService.checkLoginCodeExists(loginCode, PlatFormCode.ZHITU);
			if(isExists == Tag.TRUE) {
				JSONUtil.optResult(Tag.EXIST, "账号已存在", jsonMap);
			} else {
				JSONUtil.optResult(Tag.NOT_EXIST, "账号不存在", jsonMap);
			}
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 检测织图用户名是否存在
	 * 
	 * @return
	 */
	public String checkUserNameExists() {
		try {
			Integer isExists = webUserInfoService.checkUserNameExists(userName);
			if(isExists.equals(Tag.TRUE)) {
				JSONUtil.optResult(Tag.EXIST, "用户名已存在", jsonMap);
			} else {
				JSONUtil.optResult(Tag.NOT_EXIST, "用户名不存在", jsonMap);
			}
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询用户列表
	 * 
	 * @return
	 */
	public String queryUser() {
		try {
			if(StringUtil.checkIsNULL(userName)) {
				userInfoService.buildUser(null,null,platformVerify,maxId, page, rows, jsonMap); // 无条件查询
			} else {
				try {
					Integer userId = Integer.parseInt(userName);
					userInfoService.buildUser(userId,null,platformVerify,maxId, page, rows, jsonMap); // 根据id查询
				} catch(NumberFormatException e) {
					userInfoService.buildUser(null ,userName,platformVerify,maxId, page, rows, jsonMap); // 根据用户名查询
				}
			}
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据userId来查询用户信息
	 */
	public String queryUserByUserId(){
		try{
			userInfoService.buildUser( userId, null,null,maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询某用户的信息
	 */
	public String queryUserByUserIdAndCheckIsZombie(){
		try{
			userInfoService.queryUserByUserIdAndCheckIsZombie(userId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新用户签名
	 */
	public String updateSignature(){
		try {
			userInfoService.updateSignature(userInfoJSON);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 屏蔽用户
	 * 
	 * @return
	 */
	public String shieldUser() {
		try {
			userInfoService.shieldUser(userId);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 解除用户屏蔽
	 * @return
	 */
	public String unShieldUser() {
		try {
			userInfoService.unShieldUser(userId);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存用户
	 * @return
	 */
	public String saveUser() {
		try {
			userInfoService.saveUser(userName, loginCode, userAvatar, userAvatarL);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新信任标记
	 * 
	 * @return
	 */
	public String updateTrust() {
		try {
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			userInfoService.updateTrust(userId, trust,user.getId());
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 交换用户信息
	 * 
	 * @return
	 */
	public String updateExchangeUsers()	{
		try {
			userInfoService.updateExchangeUsers(id, toId);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询用户的信息，主要是查询用户的推荐状态
	 * @return
	 */
	public String queryUserInfoByUserId(){
		try {
			userInfoService.queryUserInfoByUserId(userId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 全局过滤用户名
	 * 
	 * @return
	 */
	public String trimUserName(){
		try {
			userInfoService.trimUserName();
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新用户名及性别 
	 * 
	 * @return
	 * @author zhangbo	2015年9月30日
	 */
	public String updateUserNameAndSex() {
		try {
			webUserInfoService.updateUserName(userId, userName);
			webUserInfoService.updateSex(userId, sex);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public String getUserAvatarL() {
		return userAvatarL;
	}

	public void setUserAvatarL(String userAvatarL) {
		this.userAvatarL = userAvatarL;
	}

	public Integer getTrust() {
		return trust;
	}

	public void setTrust(Integer trust) {
		this.trust = trust;
	}
	
	
	public void setSignature(String signature){
		this.signature = signature;
	}
	public String getSignature(){
		return this.signature;
	}
	
	public void setUserInfoJSON(String userInfoJSON){
		this.userInfoJSON = userInfoJSON;
	}
	public String getUserInfoJSON(){
		return this.userInfoJSON;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getToId() {
		return toId;
	}

	public void setToId(Integer toId) {
		this.toId = toId;
	}
	public void setPlatformVerify(Integer platformVerify){
		this.platformVerify = platformVerify;
	}
	
	public Integer getPlatformVerify(){
		return this.platformVerify;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
}

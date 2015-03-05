package com.imzhitu.admin.op;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.op.service.OpUserService;

/**
 * <p>
 * 织图用户运营管理控制器
 * </p>
 * 
 * 创建时间：2014-3-14
 * @author tianjie
 *
 */
public class OpUserAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2559668292965466174L;
	
	private Integer id;
	private String ids;
	private Integer userId;
	private Integer verifyId;
	private String userIds;
	private String userName; //用户名
	private String loginCode; // 账号
	private String userAvatar; //头像
	private String userAvatarL; //头像大图
	
	private String userJSON;
	private String notifyTip;
	private String recommendDesc; // 推荐描述
	private String recommendType;
	private Integer notified;
	private Integer sysAccept;
	private Integer userAccept;
	private Boolean accepted = false;
	private Boolean deleteStar = false;
//	private Integer fixPos;
	private Boolean isAdd = true;
	private Integer weight;
	private String signture;	//签名
	private Integer sex;
	private String rowJson;
	private String worldCount;	//织图数量范围，eg：1,2。表示织图数量1到2之间.eg：3.表示织图数量范围3到3之间.
	
	private Integer lastUsed; //一个月没有登录的标记
	private Boolean insertMessage = false;

	private Date begin;	//最后发图的开始时间
	private Date end;	//最后发图的结束时间
	



	@Autowired
	private OpUserService opUserService;
	
	
	/*
	 *******用户推荐模块******
	 */
	
	/**
	 * 查询推荐用户列表
	 * 
	 * @return
	 */
	public String queryRecommendUser() {
		try {
			opUserService.buildRecommendUser(maxId, page, rows,
					userAccept, sysAccept, notified, weight, verifyId, userName,lastUsed, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存推荐用户
	 * 
	 * @return
	 */
	public String saveRecommendUser() {
		AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Integer recommenderId = user.getId();
		try {
			opUserService.saveRecommendUser(userId, verifyId, recommendDesc, recommenderId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据用户id删除推荐信息
	 * 
	 * @return
	 */
	public String deleteRecommendUserByUserId() {
		try {
			opUserService.deleteRecommendUserByUserId(userId, true);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 批量删除推荐用户信息
	 * 
	 * @return
	 */
	public String deleteRecommendUsers() {
		try {
			opUserService.deleteRecommendUserByIds(ids, deleteStar,insertMessage);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据JSON更新推荐推荐用户信息
	 * 
	 * @return
	 */
	public String updateRecommendUserByJSON() {
		try {
			opUserService.updateRecommendUserByJSON(userJSON);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新推荐用户系统允许标记
	 * 
	 * @return
	 */
	public String updateRecommendSysAccept() {
		try {
			opUserService.updateRecommendSysAccept(ids, sysAccept);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新推荐用户索引
	 * @return
	 */
	public String reIndexRecommendUser() {
		String[] ids = request.getParameterValues("reIndexId");
		try {
			opUserService.updateRecommendUserIndex(ids);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 通知推荐用户
	 * @return
	 */
	public String notifyRecommendUser() {
		try {
			opUserService.addRecommendUserMsg(id, userId, userName, notifyTip, recommendType, userAccept, accepted);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新认证类型
	 * 
	 * @return
	 */
	public String updateRecommendVerify() {
		try {
			opUserService.updateRecommendVerify(id, verifyId);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新推荐用户权重
	 * 
	 * @return
	 */
	public String updateRecommendWeight() {
		try {
			Integer weight = opUserService.updateRecommendWeight(id, isAdd);
			jsonMap.put(OptResult.JSON_KEY_WEIGHT, weight);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据用户id更新推荐用户权重
	 * 
	 * @return
	 */
	public String updateRecommendWeightByUID() {
		try {
			Integer weight = opUserService.updateRecommendWeightByUID(userId, isAdd, getCurrentLoginUserId());
			jsonMap.put(OptResult.JSON_KEY_WEIGHT, weight);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/*
	 * 僵尸用户模块
	 */
	
	/**
	 * 查询推荐用户列表
	 * 
	 * @return
	 */
	public String queryZombieUser() {
		try {
			Integer min = 0;
			Integer max = 10000;
			if(null != worldCount && !worldCount.equals("")){
				try{
					String[]str = worldCount.split(",");
					if(str.length > 1){
						min = Integer.parseInt(str[0]);
						max = Integer.parseInt(str[1]);
						if(min.compareTo(max)>0){
							Integer tmp = min;
							min = max;
							max = tmp;
						}
					}else if(str.length == 1){
						min = Integer.parseInt(str[0]);
						max = min;
					}
					
				}catch(Exception e1){
					
				}
			}
			opUserService.buildZombieUser(min,max,userId,userName,begin,end,page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存僵尸用户
	 * 
	 * @return
	 */
	public String saveZombieUser() {
		AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String recommender = user.getName();
		try {
			opUserService.saveZombieUser(userName, loginCode, userAvatar, userAvatarL, recommender);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存僵尸用户
	 * @return
	 */
	public String saveZombieUserByIds() {
		AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String recommender = user.getName();
		try {
			opUserService.saveZombieUsers(ids, recommender);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存僵尸用户
	 * 
	 * @return
	 */
	public String saveZombieUserByIdsDirect() {
		try {
			opUserService.saveZombieUsers(ids, userName);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 批量删除僵尸用户
	 * 
	 * @return
	 */
	public String deleteZombieUsers() {
		try {
			opUserService.batchDeleteZombieUsers(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 屏蔽僵尸
	 * @return
	 */
	public String shieldZombie() {
		try {
			opUserService.shieldZombie(userId);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 解除僵尸屏蔽
	 * @return
	 */
	public String unShieldZombie() {
		try {
			opUserService.unShieldZombie(userId);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新所有马甲登陆状态
	 * 
	 * @return
	 */
	public String updateZombieLoginStatus() {
		try {
			opUserService.saveOrUpdateZombieLoginStatus();
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除明星标志
	 * @return
	 */
	public String delStar(){
		try{
			opUserService.delStar(userId);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	

	/**
	 * 直接让用户接受推荐邀请
	 * @return
	 */
	public String userAcceptRecommendDirect(){
		try{
			opUserService.userAcceptRecommendDirect(userId, verifyId);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 修改性别
	 * @return
	 */
	public String updateZombieSex(){
		try{
			opUserService.updateZombieSex(sex, userId);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(OptResult.UPDATE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 修改昵称
	 * @return
	 */
	public String updateZombieUserName(){
		try{
			opUserService.updateZombieUserName(userName, userId);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(OptResult.UPDATE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 修改签名
	 * @return
	 */
	public String updateZombieSignture(){
		try{
			opUserService.updateZombieSignText(signture, userId);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(OptResult.UPDATE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更改马甲信息
	 * @return
	 */
	public String updateZombie(){
		try{
			opUserService.updateZombie(rowJson);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(OptResult.UPDATE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
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

	public String getUserJSON() {
		return userJSON;
	}

	public void setUserJSON(String userJSON) {
		this.userJSON = userJSON;
	}

	public String getNotifyTip() {
		return notifyTip;
	}

	public void setNotifyTip(String notifyTip) {
		this.notifyTip = notifyTip;
	}

	public String getRecommendDesc() {
		return recommendDesc;
	}

	public void setRecommendDesc(String recommendDesc) {
		this.recommendDesc = recommendDesc;
	}

	public OpUserService getOpUserService() {
		return opUserService;
	}

	public void setOpUserService(OpUserService opUserService) {
		this.opUserService = opUserService;
	}

	public Boolean getAccepted() {
		return accepted;
	}

	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}

	public Integer getSysAccept() {
		return sysAccept;
	}

	public void setSysAccept(Integer sysAccept) {
		this.sysAccept = sysAccept;
	}

	public Integer getNotified() {
		return notified;
	}

	public void setNotified(Integer notified) {
		this.notified = notified;
	}

	public Integer getUserAccept() {
		return userAccept;
	}

	public void setUserAccept(Integer userAccept) {
		this.userAccept = userAccept;
	}

	public Boolean getDeleteStar() {
		return deleteStar;
	}

	public void setDeleteStar(Boolean deleteStar) {
		this.deleteStar = deleteStar;
	}

	public Integer getVerifyId() {
		return verifyId;
	}

	public void setVerifyId(Integer verifyId) {
		this.verifyId = verifyId;
	}

	public Boolean getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(Boolean isAdd) {
		this.isAdd = isAdd;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	public String getSignture() {
		return signture;
	}

	public void setSignture(String signture) {
		this.signture = signture;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}


	public String getRowJson() {
		return rowJson;
	}

	public void setRowJson(String rowJson) {
		this.rowJson = rowJson;
	}
	public String getWorldCount() {
		return worldCount;
	}

	public void setWorldCount(String worldCount) {
		this.worldCount = worldCount;
	}

	public String getRecommendType() {
		return recommendType;
	}

	public void setRecommendType(String recommendType) {
		this.recommendType = recommendType;
	}
	

	public Integer getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Integer lastUsed) {
		this.lastUsed = lastUsed;
	}
	

	public Boolean getInsertMessage() {
		return insertMessage;
	}

	public void setInsertMessage(Boolean insertMessage) {
		this.insertMessage = insertMessage;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
}

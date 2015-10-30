package com.imzhitu.admin.userinfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.UserMsgConversationDto;
import com.imzhitu.admin.userinfo.service.UserMsgService;

/**
 * <p>
 * 用户消息控制器
 * </p>
 * 
 * 创建时间：2014-4-29
 * 
 * @author tianjie
 * 
 */
public class UserMsgAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9199851051016750617L;

	private String ids;
	private String content;
	private Integer worldId;
	private Integer userId;
	private Integer otherId;
	private Integer phoneCode;
	private String activityName;
	private Integer senderId;
	
	private UserMsgConversationDto conver = new UserMsgConversationDto();
	
	

	@Autowired
	private UserMsgService userMsgService;

	/**
	 * 查询发送者私信列表索引
	 * 
	 * @return
	 */
	public String queryConversation() {
		try {
			userMsgService.buildConversation(conver, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询私信列表
	 * 
	 * @return
	 */
	public String queryMsg() {
		try {
			userMsgService.buildUserMsg(otherId, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 发送私信
	 * 
	 * @return
	 */
	public String sendMsg() {
		try {
			userMsgService.sendMsg(otherId, content);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 发送多条私信
	 * 
	 * @return
	 */
	public String sendMultiMsg() {
		try {
			String[] ids = request.getParameterValues("ids");
			userMsgService.sendMsgs(ids, content);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询弹幕
	 * 
	 * @return
	 */
	public String queryDanmu() {
		try {
			userMsgService.buildUserMsgDanmu(maxId, start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public UserMsgService getUserMsgService() {
		return userMsgService;
	}

	public void setUserMsgService(UserMsgService userMsgService) {
		this.userMsgService = userMsgService;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getOtherId() {
		return otherId;
	}

	public void setOtherId(Integer otherId) {
		this.otherId = otherId;
	}

	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	public Integer getSenderId() {
		return senderId;
	}

	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}

	public UserMsgConversationDto getConver() {
		return conver;
	}

	public void setConver(UserMsgConversationDto conver) {
		this.conver = conver;
	}
	
}

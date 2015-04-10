package com.imzhitu.admin.userinfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.HTSException;
import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
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
	
	

	@Autowired
	private UserMsgService userMsgService;
	@Autowired
	private com.hts.web.userinfo.service.UserMsgService webUserMsgService;

	/**
	 * 推送系统消息
	 * 
	 * @return
	 */
	public String saveSysMsg() {
		try {
			userMsgService.pushSysMsg(ids, content);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 推送app消息，即向全体用户推送消息
	 * @return
	 */
	public String saveAppMsg() {
		try {
			String[] phoneTypes = request.getParameterValues("phoneType");
			if(phoneTypes == null && ids == null) {
				throw new HTSException("必须选择平台或指定用户");
			} else if(!StringUtil.checkIsNULL(ids)) {
				userMsgService.pushListMsg(content, worldId, activityName, ids);
			} else {
				userMsgService.pushAppMsg(content, worldId, activityName, phoneTypes);
			}
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询发送者私信列表索引
	 * 
	 * @return
	 */
	public String queryRecipientMsgBox() {
		try {
			userMsgService.buildRecipientMsgBox(maxId,senderId, userId, phoneCode, page, rows, jsonMap);
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
			userMsgService.buildUserMsg(userId, otherId, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除收件箱
	 * 
	 * @return
	 */
	public String deleteRecipientMsgBox() {
		try {
			userMsgService.updateRecipientMsgBoxUnValid(ids, userId);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch(Exception e) {
			e.printStackTrace();
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
			webUserMsgService.saveUserMsg(userId, otherId, content, Tag.USER_MSG_NORMAL);
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

	public com.hts.web.userinfo.service.UserMsgService getWebUserMsgService() {
		return webUserMsgService;
	}

	public void setWebUserMsgService(
			com.hts.web.userinfo.service.UserMsgService webUserMsgService) {
		this.webUserMsgService = webUserMsgService;
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
	
}

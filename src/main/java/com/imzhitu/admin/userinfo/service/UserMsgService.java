package com.imzhitu.admin.userinfo.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 用户消息业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2014-4-29
 * @author tianjie
 *
 */
public interface UserMsgService extends BaseService {

	/**
	 * 批量推送系统消息
	 * 
	 * @param idsStr
	 */
	public void pushSysMsg(String idsStr, String content) throws Exception;
	
	/**
	 * 推送App消息
	 * 
	 * @param content
	 * @param worldId
	 * @param activityName
	 * @param phoneTypes
	 */
	public void pushAppMsg(String content, Integer worldId, String activityName, 
			String[] phoneTypes) throws Exception;
	
	/**
	 * 向多用户推送消息
	 * 
	 * @param content
	 * @param worldId
	 * @param idsStr
	 */
	public void pushListMsg(String content, Integer worldId, String activityName, 
			String idsStr)throws Exception;
	
	
	/**
	 * 构建收件箱消息列表索引
	 * 
	 * @param maxId
	 * @param recipientId
	 * @param phoneCode
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildRecipientMsgBox(Integer maxId, Integer senderId,Integer recipientId, Integer phoneCode, int start, int limit,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建私信列表
	 * 
	 * @param userId
	 * @param otherId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildUserMsg(Integer userId, Integer otherId, int maxId, int start,
			int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 设置和指定用户的收件无效
	 * 
	 * @param senderIdsStr
	 * @param recipientId
	 */
	public void updateRecipientMsgBoxUnValid(String senderIdsStr, Integer recipientId);
	
	/**
	 * 构建弹幕消息
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildUserMsgDanmu(int maxId, int start, int limit,
			Map<String, Object> jsonMap) throws Exception; 
}

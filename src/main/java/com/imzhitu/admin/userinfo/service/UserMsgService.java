package com.imzhitu.admin.userinfo.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.UserMsgConversationDto;

/**
 * <p>
 * 用户消息业务逻辑访问接口
 * </p>
 * 
 * @author tianjie 2014-4-29 2015-10-29
 *
 */
public interface UserMsgService extends BaseService {

	/**
	 * 构建对话列表
	 * 
	 * @param conver
	 * @param start
	 * @param limit
	 * @param jsonMap
	 */
	public void buildConversation(UserMsgConversationDto conver, 
			Integer start, Integer limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 删除对话
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void delConver(String idsStr) throws Exception;
	
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
	public void buildUserMsg(Integer otherId, int maxId, int start,
			int limit, Map<String, Object> jsonMap) throws Exception;

	/**
	 * 发送私信
	 * 
	 * @param userId
	 * @param content
	 * @throws Exception
	 */
	public void sendMsg(Integer userId, String content, Boolean keep) throws Exception;
	
	/**
	 * 发送批量私信
	 * 
	 * @param idStrs
	 * @param content
	 * @throws Exception
	 */
	public void sendMsgs(String idsStr, String content) throws Exception;
	
	/**
	 * 发送批量私信
	 * 
	 * @param idStrs
	 * @param content
	 * @throws Exception
	 */
	public void sendMsgs(String[] idStrs, String content) throws Exception;
	
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

package com.imzhitu.admin.op.service;

import java.util.Map;

import com.hts.web.common.pojo.OpNotice;
import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.OpSysMsg;

/**
 * 系统发送通知公告接口类
 * 1、包括banner通知的各种操作
 * 2、包括开屏页通知的各种操作
 * 3、包括基于频道发送通知的各种操作
 * 
 * @author ztj	2015年9月2日
 *
 */
public interface OpMsgService extends BaseService {

	/**
	 * 保存公告
	 * 
	 * @param path
	 * @param phoneCode
	 */
	public void saveNotice(String path, String link, Integer phoneCode) throws Exception;

	/**
	 * 更新公告
	 * 
	 * @param id
	 * @param path
	 * @param link
	 */
	public void updateNotice(Integer id, String path, String link, Integer phoneCode) throws Exception;

	/**
	 * 构建公告列表
	 * 
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildNotice(Map<String, Object> jsonMap) throws Exception;

	/**
	 * 删除公告
	 * 
	 * @param phoneCode
	 * @throws Exception
	 */
	public void deleteNotice(String phoneCodes) throws Exception;

	/**
	 * 根据phoneCode获取公告
	 * 
	 * @param phoneCode
	 * @return
	 * @throws Exception
	 */
	public OpNotice getNotice(Integer phoneCode) throws Exception;

	/**
	 * 向所有用户发送消息
	 * 
	 * @param msg
	 * @param inApp 是否插入数据库
	 * @param noticed 是否发送通知
	 * @param uidsStr
	 * @throws Exception
	 */
	public void pushAppMsg(OpSysMsg msg, Boolean inApp, Boolean noticed, String uidsStr) throws Exception;

	/**
	 * 保存频道通知模板内容
	 * 
	 * @param channelId			频道主键id
	 * @param contentTmpl		模板内容
	 * @param channelNoticeType	频道通知类型
	 * @throws Exception		若保存的通知模板内容不符合规定模式则抛异常，规定模式：必须包含"${userName}"与"${channelName}"
	 * @author zhangbo 2015年9月2日
	 */
	void saveChannelNoticeTemplate(Integer channelId, String contentTmpl, String channelNoticeType) throws Exception;

	/**
	 * 根据频道通知类型获取通知信息模板内容
	 * 
	 * @param channelId
	 *            频道id
	 * @param channelNoticeType
	 *            频道通知类型
	 * @return contantTmpl 通知信息模板内容
	 * @throws Exception
	 * @author zhangbo 2015年9月6日
	 */
	String getChannelNoticeTemplateByType(Integer channelId, String channelNoticeType) throws Exception;

	/**
	 * 发送频道中各种系统消息，通过消息类型区分 
	 * 目前包括此功能包括： 1、织图被编辑选入频道的通知 2、频道内织图被选为精选的通知 3、频道成员被选为红人的通知
	 * 
	 * @param receiverId
	 *            消息接收人ID（user_id）
	 * @param sendType
	 *            发送消息类型 定义在Admin常量类中，
	 *            分为： 
	 *            1:NOTICE_WORLD_INTO_CHANNEL 织图被编辑选入频道的通知 
	 *            2:NOTICE_CHANNELWORLD_TO_SUPERB 频道内织图被选为精选的通知 
	 *            3:NOTICE_CHANNELMEMBER_TO_STAR 频道成员被选为红人的通知
	 * @param channelId
	 *            频道ID
	 * @param worldId
	 *            频道织图id，sendType为频道成员被选为红人时，传递null
	 * @author zhangbo 2015年9月2日
	 */
	void sendChannelSystemNotice(Integer receiverId, String sendType, Integer channelId, Integer worldId) throws Exception;


	/**
	 * 保存系统消息
	 * 
	 * @param recipientId
	 * @param content
	 * @param objType
	 * @param objId
	 * @param objMeta
	 * @param objMeta2
	 * @param thumbPath
	 * @throws Exception
	 */
	public void saveSysMsg(Integer recipientId, 
			String content, Integer objType, Integer objId, 
			String objMeta, String objMeta2, String thumbPath) throws Exception;
}

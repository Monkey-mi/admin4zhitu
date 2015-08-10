package com.imzhitu.admin.op.service;

import java.util.Date;
import java.util.Map;

import com.hts.web.common.pojo.OpNotice;
import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.OpSysMsg;

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
	 */
	public void pushAppMsg(OpSysMsg msg, 
			Boolean inApp, Boolean noticed, Integer uid) throws Exception;

//	/**
//	 * 更新启动缓存
//	 * 
//	 * @throws Exception
//	 */
//	public void updateStartPageCache(String linkPath, Integer linkType, String link,
//			Date beginDate, Date endDate, Integer showCount) throws Exception;
}

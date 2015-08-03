package com.imzhitu.admin.op.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

/**
 * 骚扰私聊处理接口
 * 
 * @author zhangbo	2015年8月3日
 *
 */
public interface ChatService extends BaseService {

	/**
	 * @param page 		对应分页查询limit
	 * @param rows 		page对应分页查询start
	 * @param jsonMap 	返回前台结果集
	 * 
	 * @author zhangbo	2015年8月3日
	 */
	void buildChatList(Integer page, Integer rows, Map<String, Object> jsonMap) throws Exception;

	/**
	 * 批量删除骚扰私聊
	 * 
	 * @param ids
	 * @author zhangbo	2015年8月3日
	 */
	void deleteChats(Integer[] ids) throws Exception;

}

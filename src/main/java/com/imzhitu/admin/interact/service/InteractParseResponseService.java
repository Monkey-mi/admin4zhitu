package com.imzhitu.admin.interact.service;

import com.hts.web.common.service.BaseService;

public interface InteractParseResponseService extends BaseService{
	/**
	 * 过滤需要回复的回复中的表情。
	 * @param qStr
	 * @return
	 * @throws Exception
	 */
	public String parserQString(String qStr)throws Exception;
	
	/**
	 * 过来从机器人那边返回的数据。
	 * @param aStr
	 * @return
	 * @throws Exception
	 */
	public String parserAString(String aStr)throws Exception;
}

package com.imzhitu.admin.propertiesSet.service;

import java.util.Map;

public interface PropertiesSetService {
	/**
	 * 
	 * @param channelId
	 * @throws Exception 
		*	2015年8月25日
		*	mishengliang
	 */
	public String queryNotifyByChannelId(Integer channelId,Map<String , Object> jsonMap)throws Exception;
	
	/**
	 * 
	 * @param channelId
	 * @return
	 * @throws Exception 
		*	2015年8月25日
		*	mishengliang
	 */
	public void addNotifyByChannelId(Integer channelId,Map<String, String> notifyMap,Map<String , Object> jsonMap)throws Exception;
}

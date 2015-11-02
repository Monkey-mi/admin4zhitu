package com.imzhitu.admin.channel.service;

import com.hts.web.common.service.BaseService;

/**
 * 频道织图生效规划的互动相关操作接口类
 * 
 * @author zhangbo	2015年10月29日
 *
 */
public interface ChannelWorldInteractSchedulerService extends BaseService {
	
	/**
	 * 新增频道织图生效规划互动
	 *
	 * @param channelId		频道id
	 * @param worldId		织图id
	 *  
	 * @author zhangbo	2015年10月29日
	 */
	void addChannelWorldInteractScheduler(Integer channelId, Integer worldId) throws Exception; 
}

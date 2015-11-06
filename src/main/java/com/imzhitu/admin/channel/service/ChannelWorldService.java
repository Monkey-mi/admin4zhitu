package com.imzhitu.admin.channel.service;

/**
 * 频道织图业务层接口类
 * 
 * @author zhangbo	2015年11月5日
 *
 */
public interface ChannelWorldService {
	
	/**
	 * 根据频道id和织图id设置频道织图生效（小编手动操作生效）
	 * 
	 * @param channelId	频道id
	 * @param worldId	织图id
	 * @throws Exception
	 */
	void setChannelWorldValidByOperator(Integer channelId, Integer worldId) throws Exception;
	
	/**
	 * 根据频道id和织图id设置频道织图失效（小编手动操作失效）
	 * 
	 * @param channelId	频道id
	 * @param worldId	织图id
	 * @throws Exception
	 */
	void setChannelWorldInvalidByOperator(Integer channelId, Integer worldId) throws Exception;
	
	/**
	 * 根据频道id和织图id设置频道织图生效（计划中生效）
	 * 
	 * @param channelId
	 * @param worldId
	 * @throws Exception
	 * @author zhangbo	2015年11月5日
	 */
	void setChannelWorldValidByScheduler(Integer channelId, Integer worldId) throws Exception;

}

package com.imzhitu.admin.interact.service;

import java.util.Map;

/**
 * 频道织图互动计划接口类 
 * @author zhangbo	2015年10月30日
 *
 */
public interface InteractChannelWorldService {

	/**
	 * 保存频道织图互动评论
	 * 
	 * @param channelId
	 * @param worldId
	 * @param commentIds
	 *  
	 * @author zhangbo	2015年10月30日
	 */
	void saveChannelWorldInteractComment(Integer channelId, Integer worldId, Integer[] commentIds) throws Exception;
	
	/**
	 * 保存频道织图互动相关数据，生成各项计划，包括点赞，播放，评论，加粉
	 * 
	 * @param channelId	频道id
	 * @param worldId	织图id
	 * @author zhangbo	2015年10月30日
	 */
	void saveChannelWorldInteract(Integer channelId, Integer worldId) throws Exception;

	/**
	 * 查询频道织图规划的互动相关数量（不是真实执行后的数量，只是给操作者展示的概念数据，大概数量会添加多少的展示），包括点赞数、播放数、评论数（评论数是真实的）
	 * 
	 * @param channelId	频道id
	 * @param worldId	织图id
	 * 
	 * @return	map key为clickCount、commentCount、likedCount，分别是播放数、评论数、点赞数 
	 * 
	 * @throws Exception
	 * @author zhangbo	2015年10月31日
	 */
	Map<String, Integer> queryWorldUNInteractCount(Integer channelId, Integer worldId) throws Exception;

}

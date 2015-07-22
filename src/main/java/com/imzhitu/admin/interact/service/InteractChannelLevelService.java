package com.imzhitu.admin.interact.service;

import java.util.List;
import java.util.Map;

import com.imzhitu.admin.common.pojo.InteractChannelLevel;

public interface InteractChannelLevelService {
	/**
	 * 增加
	 * @param dto
	 */
	public void insertChannelLevel(Integer channelId,
			Integer unSuperMinCommentCount,	Integer unSuperMaxCommentCount,Integer superMinCommentCount,Integer superMaxCommentCount,
			Integer unSuperMinLikeCount,	Integer unSuperMaxLikeCount,Integer superMinLikeCount,Integer superMaxLikeCount,
			Integer unSuperMinFollowCount,	Integer unSuperMaxFollowCount,Integer superMinFollowCount,Integer superMaxFollowCount,
			Integer unSuperMinClickCount,	Integer unSuperMaxClickCount,Integer superMinClickCount,Integer superMaxClickCount,
			Integer minutetime)throws Exception;
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void batchDeleteChannelLevel(String idsStr)throws Exception;
	
	/**
	 * 更新
	 * @param dto
	 */
	public void updateChannelLevel(Integer id,
			Integer unSuperMinCommentCount,Integer unSuperMaxCommentCount,Integer superMinCommentCount,Integer superMaxCommentCount,
			Integer unSuperMinLikeCount,	Integer unSuperMaxLikeCount,Integer superMinLikeCount,Integer superMaxLikeCount,
			Integer unSuperMinFollowCount,	Integer unSuperMaxFollowCount,Integer superMinFollowCount,Integer superMaxFollowCount,
			Integer unSuperMinClickCount,	Integer unSuperMaxClickCount,Integer superMinClickCount,Integer superMaxClickCount,
			Integer minutetime)throws Exception;
	
	/**
	 * 查询
	 * @param dto
	 * @return
	 */
	public List<InteractChannelLevel> queryChannelLevel(Integer channelId,Integer id)throws Exception;
	
	/**
	 * 分页查询
	 * @param id
	 * @param channelId
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryChannelLevel(Integer id,Integer channelId,Integer maxId,int page,int rows ,Map<String,Object>jsonMap)throws Exception;
}

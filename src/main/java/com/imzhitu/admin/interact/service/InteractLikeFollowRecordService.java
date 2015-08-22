package com.imzhitu.admin.interact.service;

import java.util.List;
import java.util.Map;

import com.imzhitu.admin.common.pojo.InteractLikeFollowRecord;

public interface InteractLikeFollowRecordService {
	/**
	 * 添加
	 * @param zombieId 马甲id
	 * @param worldId 织图id
	 * @param userId 织图作者id
	 * @param type 类型。0表示点赞，1表示互粉
	 * @param complete 整个互动过程完成情况
	 * @throws Exception
	 */
	public void insertLikeFollowRecord(Integer zombieId,Integer worldId,Integer userId,Integer type,Integer complete,Integer interactWorldCommentId)throws Exception;
	
	/**
	 * 批量删除
	 * @param idsStr 多个interact_like_follow_record表中的id构成的字符串，以逗号隔开。eg："12,13,15"
	 * @throws Exception
	 */
	public void batchDeleteLikeFollowRecord(String idsStr)throws Exception;
	
	/**
	 * 批量更新完成情况
	 * @param complete 0表示未完成，1表示完成
	 * @param ids
	 * @throws Exception
	 */
	public void batchUpdateLikeFollowRecord(Integer complete,Integer[] ids)throws Exception;
	
	/**
	 * 分页查询
	 * @param id
	 * @param zombieId 马甲id
	 * @param worldId 织图id
	 * @param userId 织图作者id
	 * @param type  类型。0表示点赞，1表示互粉
	 * @param complete 0表示未完成，1表示完成
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryLikeFollowRecord(Integer id,Integer zombieId,Integer worldId,Integer userId,Integer type,
			Integer complete,Integer interactWorldCommentId,Integer maxId,int page,int rows,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 查询未完成的互粉互赞
	 * @param type 类型，用以区分互粉还是互赞。0表示互赞，1表示互粉
	 * @return
	 */
	public List<InteractLikeFollowRecord> queryUnCompleteLikeFollowRecordByType(Integer type)throws Exception;
	
	/**
	 * 扫描互粉互赞，添加对应的计划，job
	 * @throws Exception
	 */
	public void doLikeFollowJob()throws Exception;
	
	/**
	 * 
	 * @param userId
	 * @param worldId
	 * @param type 0赞。
	 */
	public void addLikeFollowInteract(Integer userId,Integer worldId,Integer type);
}

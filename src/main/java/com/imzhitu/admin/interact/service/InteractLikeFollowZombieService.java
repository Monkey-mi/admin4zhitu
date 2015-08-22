package com.imzhitu.admin.interact.service;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface InteractLikeFollowZombieService {
	/**
	 * 批量添加马甲
	 * @param file
	 * @throws Exception
	 */
	public void batchInsertLikeFollowZombie(File file)throws Exception;
	
	/**
	 * 批量删除
	 * @param idsStr
	 * @throws Exception
	 */
	public void batchDeleteLikeFollowZombie(String idsStr)throws Exception;
	
	/**
	 * 随机查询n个未互动的马甲
	 * @param userId
	 * @param worldId
	 * @return
	 * @throws Exception
	 */
	public List<Integer> queryNRandomNotCommentNotFollowZombieId(Integer userId,Integer worldId,Integer n)throws Exception;
	
	/**
	 * 分页查询
	 * @param id
	 * @param zombieId
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryLikeFollowZombie(Integer id,Integer zombieId,Integer maxId,int page,int rows,Map<String, Object>jsonMap)throws Exception;
}

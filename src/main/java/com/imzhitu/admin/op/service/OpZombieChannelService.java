package com.imzhitu.admin.op.service;

import java.util.Map;

public interface OpZombieChannelService {
	/**
	 * 添加
	 * @param userId
	 * @param channelId
	 * @throws Exception
	 */
	public void insertZombieChannel(Integer userId,Integer channelId)throws Exception;
	
	/**
	 * 批量删除
	 * @param idsStr
	 * @throws Exception
	 */
	public void batchDeleteZombieChannel(String idsStr)throws Exception;
	
	/**
	 * 分页查询
	 * @param id
	 * @param userId
	 * @param channelId
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryZombieChannel(Integer id,Integer userId,Integer channelId,Integer maxId,int page,int rows,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 分页查询总数
	 * @param id
	 * @param userId
	 * @param channelId
	 * @param maxId
	 * @return
	 * @throws Exception
	 */
	public long queryZombieChannelTotalCount(Integer id,Integer userId,Integer channelId,Integer maxId)throws Exception;
}

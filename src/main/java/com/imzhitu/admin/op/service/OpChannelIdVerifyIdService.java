package com.imzhitu.admin.op.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

public interface OpChannelIdVerifyIdService extends BaseService{
	/**
	 * 插入
	 * @param dto
	 */
	public void insertChannelIdVerifyId(Integer channelId,Integer verifyId)throws Exception;
	
	/**
	 * 检查是否存在与verifyId对应的channelId
	 * @param dto
	 * @return
	 */
	public boolean checkIsExistByVerifyId(Integer verifyId)throws Exception;
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void batchDeleteChannelIdVerifyId(String idsStr)throws Exception;
	
	
	/**
	 * 根据verifyId来查询channelId
	 * @param dto
	 * @return
	 */
	public Integer queryChannelIdByVerifyId(Integer verifyId)throws Exception;
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	public void queryChannelIdVerifyIdForList(int maxId, int start, int limit,Map<String, Object> jsonMap)throws Exception;
}

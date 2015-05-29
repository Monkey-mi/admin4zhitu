package com.imzhitu.admin.op.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

public interface OpSuperbChannelRecommendService extends BaseService{
	/**
	 * insert
	 * @param dto
	 */
	public void insertSuperbChannelRecommend(Integer channelId,Integer valid,Integer operator)throws Exception;
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void batchDeleteSuperbChannelRecommend(String idsStr)throws Exception;
	
	/**
	 * 批量更新有效性
	 * @param ids
	 */
	public void batchUpdateSuperbChannelRecommendValid(Integer valid,String idsStr)throws Exception;
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	public void qeurySuperbChannelRecommend(Integer id,Integer channelId,Integer valid,Integer maxId,
			int page,int rows,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	public long querySuperbChannelRecommendCount(Integer id,Integer channelId,Integer valid,Integer maxId)throws Exception;
}

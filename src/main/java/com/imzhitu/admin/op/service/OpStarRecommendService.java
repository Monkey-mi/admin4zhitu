package com.imzhitu.admin.op.service;

import java.util.Map;


public interface OpStarRecommendService {
	/**
	 * 增加
	 */
	public void insertStarRecommend(Integer userId,Integer top,Integer valid)throws Exception;
	
	/**
	 * 删除
	 */
	public void deleteStarRecommend(Integer id,Integer userId,Integer top,Integer valid)throws Exception;
	
	/**
	 * 修改
	 */
	public void updateStarRecommend(Integer id,Integer userId,Integer top,Integer valid,Integer activity)throws Exception;
	
	/**
	 * 分页查询
	 */
	public void queryStarRecommend(int maxId,int page, int rows,Map<String,Object>jsonMap,Integer id,Integer userId,Integer top,Integer valid)throws Exception;
	
	/**
	 * 分页查询总数
	 */
	public long queryStarRecommendTotalCount(int maxId,Integer id,Integer userId,Integer top,Integer valid)throws Exception;
}

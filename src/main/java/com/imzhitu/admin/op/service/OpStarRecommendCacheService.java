package com.imzhitu.admin.op.service;

public interface OpStarRecommendCacheService {
	/**
	 * 更新达人推荐缓存
	 * @throws Exception
	 */
	public void updateStarRecommendCache()throws Exception;
	
	/**
	 * 执行更新达人推荐缓存计划
	 */
	public void doUpdateStarRecommendCacheSchedula();
}

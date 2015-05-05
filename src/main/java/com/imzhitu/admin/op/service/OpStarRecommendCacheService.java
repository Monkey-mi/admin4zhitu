package com.imzhitu.admin.op.service;

import java.util.List;

import com.hts.web.common.pojo.OpStarRecommendDto;

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
	
	/**
	 * 从缓存中读取达人推荐
	 * @return
	 * @throws Exception
	 */
	public List<OpStarRecommendDto> queryStarRecommendCache()throws Exception;
}

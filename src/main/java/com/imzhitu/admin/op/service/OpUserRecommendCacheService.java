package com.imzhitu.admin.op.service;

public interface OpUserRecommendCacheService {
	/**
	 * 更新所有的缓存
	 * @throws Exception
	 */
	public void  updateUserRecommendCache()throws Exception;
	
	/**
	 * 跟新某种分类的缓存
	 */
	public void updateUserRecommendCache(Integer verifyId)throws Exception;
	
	/**
	 * 更新用户推荐缓存job
	 */
	public void doUpdateUserRecommendCacheJob();
}

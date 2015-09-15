package com.imzhitu.admin.op.dao;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * 批量推送消息标记位缓存
 * 
 * 
 * @author lynch 2015-09-15
 * @version 3.0.0
 */
public interface OpBatchPushLockCacheDao extends BaseCacheDao {

	/**
	 * 设置加锁标记,有效期为1天,redis自动清除此key
	 */
	public void lock();
	
}

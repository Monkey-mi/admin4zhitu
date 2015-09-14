package com.imzhitu.admin.op.dao;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * 频道自动通过id缓存数据访问接口
 * 
 * @author lynch 2015-09-14
 * @version 3.0.0
 */
public interface ChannelAutoPassIdCacheDao extends BaseCacheDao {

	/**
	 * 添加自动通过id
	 * 
	 * @param id
	 * @author lynch 2015-09-14
	 */
	public void addId(Integer channelId);
	
	/**
	 * 删除id
	 * 
	 * @param id
	 * @author lynch 2015-09-14
	 * 
	 */
	public void deleteId(Integer channelId);
}

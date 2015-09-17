package com.imzhitu.admin.op.dao;

import java.util.Set;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * 频道自动通过id缓存数据访问接口
 * 
 * @author lynch 2015-09-14
 * @version 3.0.0
 */
public interface ChannelAutoRejectIdCacheDao extends BaseCacheDao {

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
	
	/**
	 * 获取用户发图到频道中不生效的频道id集合
	 * 
	 * @return
	 * @author zhangbo	2015年9月17日
	 */
	public Set<Integer> getAutoRejectChannelCache();
	
	
}

package com.imzhitu.admin.op.dao;

import java.util.Map;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * 频道PV缓存数据访问接口
 * 
 * @author lynch
 *
 */
public interface ChannelPVCacheDao extends BaseCacheDao {

	/**
	 * 查询所有PV
	 * @return
	 */
	public Map<Integer, Integer> queryAllPV();
	
	/**
	 * 清空所有PV
	 */
	public void clearAllPV();
}

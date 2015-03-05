package com.imzhitu.admin.op.dao;

import com.hts.web.common.dao.BaseCacheDao;

public interface ChannelCoverCacheDao extends BaseCacheDao {

	public void updateCoverCache(Integer[] channelIds, Integer limit);
}

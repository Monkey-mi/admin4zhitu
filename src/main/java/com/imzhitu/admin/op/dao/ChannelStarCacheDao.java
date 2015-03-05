package com.imzhitu.admin.op.dao;

import com.hts.web.common.dao.BaseCacheDao;
import com.imzhitu.admin.common.pojo.OpChannelStar;

public interface ChannelStarCacheDao extends BaseCacheDao {

	/**
	 * 更新频道明星
	 * 
	 * @param channelId
	 */
	public void updateChannelStar(OpChannelStar star);
}

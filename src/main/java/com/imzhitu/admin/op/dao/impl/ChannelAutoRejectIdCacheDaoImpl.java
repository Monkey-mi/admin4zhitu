package com.imzhitu.admin.op.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.imzhitu.admin.op.dao.ChannelAutoRejectIdCacheDao;

@Repository
public class ChannelAutoRejectIdCacheDaoImpl extends BaseCacheDaoImpl<Integer>
	implements ChannelAutoRejectIdCacheDao {

	@Override
	public void addId(Integer channelId) {
		getRedisTemplate().boundSetOps(CacheKeies.OP_CHANNEL_AUTO_REJECT_ID).add(channelId);
	}

	@Override
	public void deleteId(Integer channelId) {
		getRedisTemplate().boundSetOps(CacheKeies.OP_CHANNEL_AUTO_REJECT_ID).remove(channelId);
	}

}

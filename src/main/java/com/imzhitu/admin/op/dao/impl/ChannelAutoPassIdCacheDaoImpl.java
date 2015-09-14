package com.imzhitu.admin.op.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.imzhitu.admin.op.dao.ChannelAutoPassIdCacheDao;

@Repository
public class ChannelAutoPassIdCacheDaoImpl extends BaseCacheDaoImpl<Integer>
	implements ChannelAutoPassIdCacheDao {

	@Override
	public void addId(Integer channelId) {
		getRedisTemplate().boundSetOps(CacheKeies.OP_CHANNEL_AUTO_PASS_ID).add(channelId);
	}

	@Override
	public void deleteId(Integer channelId) {
		getRedisTemplate().boundSetOps(CacheKeies.OP_CHANNEL_AUTO_PASS_ID).remove(channelId);
	}

}

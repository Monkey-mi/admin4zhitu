package com.imzhitu.admin.op.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpChannel;
import com.hts.web.operations.dao.ChannelDao;
import com.imzhitu.admin.op.dao.ChannelCacheDao;

@Repository
public class ChannelCacheDaoImpl extends BaseCacheDaoImpl<OpChannel> implements
		ChannelCacheDao {
	
	@Autowired
	private ChannelDao webChannelDao;
	
	@Override
	public void updateChannel(Integer limit) {
		List<OpChannel> clist = webChannelDao.querySuperbChannel(limit);
		if(getRedisTemplate().hasKey(CacheKeies.OP_CHANNEL)) {
			getRedisTemplate().delete(CacheKeies.OP_CHANNEL);
		}
		if(clist.size() > 0) {
			OpChannel[] list = new OpChannel[clist.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_CHANNEL, clist.toArray(list));
		}
	}

	@Override
	public List<OpChannel> queryChannel(Integer limit) {
		return getRedisTemplate().opsForList().range(CacheKeies.OP_CHANNEL, 0, limit - 1);
	}

	@Override
	public void updateOldChannel() {
		List<OpChannel> clist = webChannelDao.queryOldChannel();
		if(getRedisTemplate().hasKey(CacheKeies.OP_CHANNEL_OLD)) {
			getRedisTemplate().delete(CacheKeies.OP_CHANNEL_OLD);
		}
		if(clist.size() > 0) {
			OpChannel[] list = new OpChannel[clist.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_CHANNEL_OLD, clist.toArray(list));
		}
	}

}

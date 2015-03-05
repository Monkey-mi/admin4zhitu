package com.imzhitu.admin.op.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpChannelCover;
import com.imzhitu.admin.op.dao.ChannelCoverCacheDao;
import com.imzhitu.admin.op.mapper.ChannelCoverMapper;

@Repository
public class ChannelCoverCacheDaoImpl extends BaseCacheDaoImpl<OpChannelCover> implements
		ChannelCoverCacheDao {
	
	@Autowired
	private ChannelCoverMapper coverMapper;
	
	@Override
	public void updateCoverCache(Integer[] channelIds, Integer limit) {
		List<OpChannelCover> list = coverMapper.queryCacheCover(channelIds, limit);
		if(getRedisTemplate().hasKey(CacheKeies.OP_CHANNEL_COVER)) {
			getRedisTemplate().delete(CacheKeies.OP_CHANNEL_COVER);
		}
		if(list.size() > 0) {
			OpChannelCover[] objs = new OpChannelCover[list.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_CHANNEL_COVER, 
					list.toArray(objs));
		}
	}
}

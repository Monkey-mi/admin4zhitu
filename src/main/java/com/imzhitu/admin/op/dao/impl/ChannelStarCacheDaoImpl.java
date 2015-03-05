package com.imzhitu.admin.op.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.userinfo.service.UserInfoService;
import com.imzhitu.admin.common.pojo.OpChannelStar;
import com.imzhitu.admin.op.dao.ChannelStarCacheDao;
import com.imzhitu.admin.op.mapper.ChannelStarMapper;


@Repository
public class ChannelStarCacheDaoImpl extends BaseCacheDaoImpl<com.hts.web.common.pojo.OpChannelStar> implements
		ChannelStarCacheDao {
	
	@Autowired
	private ChannelStarMapper channelStarMapper;
	
	@Autowired
	private UserInfoService userInfoService;

	@Override
	public void updateChannelStar(OpChannelStar star) {
		//查询置顶明星列表
		List<com.hts.web.common.pojo.OpChannelStar> cacheList = channelStarMapper.queryCacheStarWithWeight(star);
		//查询普通明星列表
		if(cacheList.size() < star.getLimit()) {
			star.setLimit(star.getLimit() - cacheList.size());
			List<com.hts.web.common.pojo.OpChannelStar> noWeightlist = channelStarMapper.queryCacheStar(star);
			cacheList.addAll(noWeightlist);
		}
		userInfoService.extractVerify(cacheList);
		HashOperations<String, Integer, List<com.hts.web.common.pojo.OpChannelStar>> ops = 
				getRedisTemplate().opsForHash();
		ops.put(CacheKeies.OP_CHANNEL_STAR, star.getChannelId(), cacheList);
	}
	
}

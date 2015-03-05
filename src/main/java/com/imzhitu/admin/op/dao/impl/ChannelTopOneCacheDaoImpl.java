package com.imzhitu.admin.op.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpChannelTopOne;
import com.hts.web.userinfo.service.UserInfoService;
import com.imzhitu.admin.op.dao.ChannelTopOneCacheDao;
import com.imzhitu.admin.op.mapper.ChannelTopOneMapper;

@Repository
public class ChannelTopOneCacheDaoImpl extends BaseCacheDaoImpl<OpChannelTopOne> 
	implements ChannelTopOneCacheDao {
	
	@Autowired
	private ChannelTopOneMapper channelTopOneMapper;
	
	@Autowired
	private UserInfoService userInfoService;

	@Override
	public void updateTopOne() {
		List<OpChannelTopOne> list = channelTopOneMapper.queryCacheTopOne();
		userInfoService.extractVerify(list);
		if(getRedisTemplate().hasKey(CacheKeies.OP_CHANNEL_TOP_ONE)) {
			getRedisTemplate().delete(CacheKeies.OP_CHANNEL_TOP_ONE);
		}
		if(list.size() > 0) {
			OpChannelTopOne[] objs = new OpChannelTopOne[list.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_CHANNEL_TOP_ONE, 
					list.toArray(objs));
		}
	}

}

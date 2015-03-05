package com.imzhitu.admin.op.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpChannelTopOneTitle;
import com.imzhitu.admin.op.dao.ChannelTopOneTitleCacheDao;

@Repository
public class ChannelTopOneTitleCacheDaoImpl extends BaseCacheDaoImpl<OpChannelTopOneTitle>
		implements ChannelTopOneTitleCacheDao {

	@Override
	public void updateTitle(OpChannelTopOneTitle title) {
		getRedisTemplate().opsForValue().set(CacheKeies.OP_CHANNEL_TOP_ONE_TITLE, title);
	}

}

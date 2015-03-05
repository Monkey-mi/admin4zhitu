package com.imzhitu.admin.op.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpChannel;

public interface ChannelCacheDao extends BaseCacheDao {

	public void updateChannel(Integer limit);
	
	public List<OpChannel> queryChannel();
}

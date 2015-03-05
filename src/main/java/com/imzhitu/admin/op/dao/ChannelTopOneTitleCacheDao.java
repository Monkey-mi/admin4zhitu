package com.imzhitu.admin.op.dao;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpChannelTopOneTitle;

public interface ChannelTopOneTitleCacheDao extends BaseCacheDao {

	public void updateTitle(OpChannelTopOneTitle title);
}

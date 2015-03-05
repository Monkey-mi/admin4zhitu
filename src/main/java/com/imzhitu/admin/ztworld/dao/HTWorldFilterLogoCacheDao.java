package com.imzhitu.admin.ztworld.dao;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.HTWorldFilterLogo;

public interface HTWorldFilterLogoCacheDao extends BaseCacheDao {
	
	public void updateLogo(HTWorldFilterLogo logo);
	
	public HTWorldFilterLogo queryLogo();
}

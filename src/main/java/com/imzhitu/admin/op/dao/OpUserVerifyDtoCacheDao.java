package com.imzhitu.admin.op.dao;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseCacheDao;

public interface OpUserVerifyDtoCacheDao extends BaseCacheDao {

	/**
	 * 更新缓存列表
	 * 
	 * @param rowSelection
	 */
	public void updateVerifyDto(RowSelection rowSelection);
	
}

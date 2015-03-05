package com.imzhitu.admin.common.dao;

import com.hts.web.common.dao.BaseDao;

public interface KeyGenDao extends BaseDao {


	/**
	 * 查询最大id和步长
	 * 
	 * @param keyId
	 * @return
	 */
	public Integer[] queryMaxIdAndStepForUpdate(Integer keyId);
	
	/**
	 * 更新最大id
	 * 
	 * @param keyId
	 * @param maxId
	 */
	public void updateMaxId(int keyId, int maxId);
}

package com.imzhitu.admin.aliyun.dao;

import net.sf.json.JSONObject;

public interface OsUserInfoDao {

	/**
	 * 搜索搜索用户
	 * 
	 * @param userName
	 * @param rowSelection
	 * @return
	 */
	public JSONObject searchId(String userName, int start, int limit);
	
}

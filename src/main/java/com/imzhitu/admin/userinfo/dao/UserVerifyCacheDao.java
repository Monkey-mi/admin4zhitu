package com.imzhitu.admin.userinfo.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.UserVerify;

public interface UserVerifyCacheDao extends BaseCacheDao {

	/**
	 * 更新缓存认证信息
	 * 
	 */
	public void updateVerify();
	
	/**
	 * 获取所有认证信息
	 * 
	 * @return
	 */
	public List<UserVerify> queryAllVerify();
}

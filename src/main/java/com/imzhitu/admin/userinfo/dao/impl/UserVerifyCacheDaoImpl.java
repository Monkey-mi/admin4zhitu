package com.imzhitu.admin.userinfo.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.UserVerify;
import com.imzhitu.admin.userinfo.dao.UserVerifyCacheDao;
import com.imzhitu.admin.userinfo.dao.UserVerifyDao;

@Repository
public class UserVerifyCacheDaoImpl extends BaseCacheDaoImpl<UserVerify> implements
		UserVerifyCacheDao {
	
	@Autowired
	private UserVerifyDao userVerifyDao;
	
	@Override
	public void updateVerify() {
		Map<Integer, UserVerify> map = userVerifyDao.queryAllVerifyMap();
		if(getRedisTemplate().hasKey(CacheKeies.USER_VERIFY)) {
			getRedisTemplate().delete(CacheKeies.USER_VERIFY);
		}
		BoundHashOperations<String, Integer, UserVerify> ops = getRedisTemplate()
				.boundHashOps(CacheKeies.USER_VERIFY);
		ops.putAll(map);
	}

	@Override
	public List<UserVerify> queryAllVerify() {
		BoundHashOperations<String, Integer, UserVerify> ops = getRedisTemplate()
				.boundHashOps(CacheKeies.USER_VERIFY);
		return ops.multiGet(ops.keys());
	}

}

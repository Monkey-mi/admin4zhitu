package com.imzhitu.admin.op.dao.impl;

import java.util.List;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpNotice;
import com.imzhitu.admin.op.dao.NoticeCacheDao;

@Repository
public class NoticeCacheDaoImpl extends BaseCacheDaoImpl<OpNotice> implements
		NoticeCacheDao {

	@Override
	public List<OpNotice> queryNotice() {
		HashOperations<String, Integer, OpNotice> ops = getRedisTemplate().opsForHash();
		return ops.values(CacheKeies.OP_NOTICE);
	}

	@Override
	public void saveNotice(OpNotice notice) {
		getRedisTemplate().opsForHash().put(CacheKeies.OP_NOTICE, 
				notice.getPhoneCode(), notice);
	}

	@Override
	public void deleteNotice(Integer[] phoneCodes) {
		getRedisTemplate().opsForHash().delete(CacheKeies.OP_NOTICE, (Object[])phoneCodes);
	}

	@Override
	public OpNotice queryNotice(Integer phoneCode) {
		HashOperations<String, Integer, OpNotice> ops = getRedisTemplate().opsForHash();
		return ops.get(CacheKeies.OP_NOTICE, phoneCode);
	}

}

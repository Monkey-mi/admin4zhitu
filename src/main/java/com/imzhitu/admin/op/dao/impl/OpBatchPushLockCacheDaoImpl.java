package com.imzhitu.admin.op.dao.impl;

import java.util.Calendar;

import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.op.dao.OpBatchPushLockCacheDao;

@Repository
public class OpBatchPushLockCacheDaoImpl extends BaseCacheDaoImpl<Integer> implements OpBatchPushLockCacheDao {

	@Override
	public void lock() {
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DAY_OF_MONTH, 1);
		
		BoundValueOperations<String, Integer> ops = getRedisTemplate()
				.boundValueOps(Admin.ADMIN_BATCH_PUSH_LOCK);
		ops.set(Tag.TRUE);
		ops.expireAt(ca.getTime());
	}
	
}

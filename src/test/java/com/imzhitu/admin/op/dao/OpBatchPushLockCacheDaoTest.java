package com.imzhitu.admin.op.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class OpBatchPushLockCacheDaoTest extends BaseTest {
	
	@Autowired
	private OpBatchPushLockCacheDao dao;

	@Test
	public void lockTest() throws Exception {
		dao.lock();
	}
	
}

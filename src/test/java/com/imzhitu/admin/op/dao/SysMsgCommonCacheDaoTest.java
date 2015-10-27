package com.imzhitu.admin.op.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class SysMsgCommonCacheDaoTest extends BaseTest {

	@Autowired
	private SysMsgCommonCacheDao dao;
	
	@Test
	public void updateCacheTest() {
		dao.updateCache();
	}
	
}

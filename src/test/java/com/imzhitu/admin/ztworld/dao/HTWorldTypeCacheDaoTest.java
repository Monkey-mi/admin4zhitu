package com.imzhitu.admin.ztworld.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class HTWorldTypeCacheDaoTest extends BaseTest {

	@Autowired
	private HTWorldTypeCacheDao dao;
	
	@Test
	public void updateTypeCacheTest() {
		dao.updateTypeCache();
	}
}

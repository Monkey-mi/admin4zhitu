package com.imzhitu.admin.op.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class ActivityStarCacheDaoTest extends BaseTest {

	@Autowired
	private ActivityStarCacheDao dao;
	
	@Test
	public void updateStarTest() throws Exception {
		dao.updateStar(93, 10);
	}
}

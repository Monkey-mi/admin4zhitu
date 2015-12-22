package com.imzhitu.admin.statistics.dao;

import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class PVCacheDaoTest extends BaseTest {
	
	@Autowired
	private PVCacheDao dao;

	@Test
	public void queryAllKeies() {
		Set<String> keies = dao.queryAllPvKey();
		logger.debug(keies);
	}
	
	@Test
	public void getPvTest() {
		long l = dao.getPv("stat:pv:1:0");
		logger.debug(l);
	}
	
	@Test
	public void incPvTest() {
		dao.incPv(1, 0, 10);
	}
}

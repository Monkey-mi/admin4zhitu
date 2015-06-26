package com.imzhitu.admin.op.dao;

import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class ChannelPVCacheDaoTest extends BaseTest {

	private static Logger log = Logger.getLogger(ChannelPVCacheDaoTest.class);
	
	@Autowired
	private ChannelPVCacheDao dao;
	
	@Test
	public void queryAllPVTest() throws Exception {
		Map<Integer, Integer> map = dao.queryAllPV();
		log.info(map);
	}
}

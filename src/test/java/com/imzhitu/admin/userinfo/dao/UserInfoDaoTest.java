package com.imzhitu.admin.userinfo.dao;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.common.util.Log;
import com.imzhitu.admin.base.BaseTest;

public class UserInfoDaoTest extends BaseTest {

	@Autowired
	private UserInfoDao dao;
	
	@Test
	public void testQueryVer() {
		Float ver = dao.queryVer(381);
		Log.debug(ver);
	}
}

package com.imzhitu.admin.userinfo.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class UserVerifyServiceTest extends BaseTest {

	@Autowired
	private UserVerifyService service;
	
	@Test
	public void testBuildVerify() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildVerify(100, 1, 10, jsonMap);
		service.buildVerify(0, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
//	@Test
	public void testSaveVerify() throws Exception {
		service.saveVerify("明星认证", "认证描述", "icon");
	}
	
//	@Test
	public void testUpdateVerify() throws Exception {
		service.updateVerify(1, "明星认证", "认证描述1", "icon1", 1);
	}
	
	@Test
	public void testUpdateVerifyCache() throws Exception {
		service.updateVerifyCache(4);
	}
	
	@Test
	public void testQueryVerifyById() throws Exception {
		service.queryVerify(1);
	}
}

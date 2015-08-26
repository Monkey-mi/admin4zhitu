package com.imzhitu.admin.userinfo.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;


public class UserInfoServiceTest extends BaseTest {

	@Autowired
	private UserInfoService service;

	@Test
	public void testBuildUser() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildUser(null, "飘飘",null,0, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildUser2() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildUser(null,null,20,0, 1, 10,  jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testUpdateExchangeUsers() throws Exception {
		service.updateExchangeUsers(485, 349);
	}
}

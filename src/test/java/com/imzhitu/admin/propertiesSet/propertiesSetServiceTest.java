package com.imzhitu.admin.propertiesSet;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.propertiesSet.service.propertiesSetService;

public class propertiesSetServiceTest extends BaseTest {

	@Autowired
	private propertiesSetService service;
	
	@Test
	public void queryChannelNotifyTest() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		 service.queryNotifyByChannelId(null,jsonMap);
	}
	
	@Test
	public void addChannelNotifyTest() throws Exception {
	Map<String, Object> jsonMap = new HashMap<String, Object>();
	Map<String, String> notifyMap =new HashMap<String, String>();
	service.addNotifyByChannelId(null,notifyMap,jsonMap);
	}
}

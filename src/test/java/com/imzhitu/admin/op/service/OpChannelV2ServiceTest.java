package com.imzhitu.admin.op.service;


import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;


public class OpChannelV2ServiceTest extends BaseTest {
	
	private Logger log = Logger.getLogger(OpChannelV2ServiceTest.class);
	
	@Autowired
	private OpChannelV2Service service;
	
	@Test
	public void queryChannelLabelTest()throws Exception{
		service.queryOpChannelLabel("女神");
	}
	
	@Test
	public void queryChannelTest()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		service.queryOpChannel(null,"人生",null, null, null, null, null, null, null,null, null, null, 1, 10, 0, jsonMap);
	}
	
	@Test
	public void insertChannelTest()throws Exception{
//		service.insertOpChannel();
	}
	
	@Test
	public void updateChannelTest()throws Exception{
//		service.updateOpChannel();
	}
	
	@Test
	public void queryYestodayWorldIncreasementTest()throws Exception{
//		long r = service.queryYestodayWorldIncreasement(null, null, 11834);
//		log.info("====================>"+r);
	}
	
	@Test
	public void queryYestodayMemberIncreasementTest()throws Exception{
		long r = service.queryYestodayMemberIncreasement(null, null, 11834);
		log.info("====================>"+r);
	}

	@Test
	public void addAutoPassIdTest() throws Exception {
		service.addAutoRejectId(12);
	}
	
	@Test
	public void queryChannelThemeListTest() throws Exception{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.queryChannelThemeList(jsonMap);
	}
	
}

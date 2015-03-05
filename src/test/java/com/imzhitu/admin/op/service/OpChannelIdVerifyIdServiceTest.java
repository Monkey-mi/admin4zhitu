package com.imzhitu.admin.op.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class OpChannelIdVerifyIdServiceTest  extends BaseTest{
	public Logger logger = Logger.getLogger(OpChannelIdVerifyIdServiceTest.class);
	@Autowired
	private OpChannelIdVerifyIdService service;
	
	@Test
	public void batchDeleteChannelIdVerifyId()throws Exception {
		service.batchDeleteChannelIdVerifyId("1,2");
	}
	
//	@Test
	public void insertChannelIdVerifyIdTest()throws Exception{
		service.insertChannelIdVerifyId(1, 1);
	}
	
	@Test
	public void checkIsExistByVerifyIdTest()throws Exception{
		boolean r = service.checkIsExistByVerifyId(1);
		logger.info("===================== r is " + r);
	}
	
	
	
	@Test
	public void queryChannelIdByVerifyIdTest()throws Exception{
		Integer r = service.queryChannelIdByVerifyId(1);
		logger.info("=============== result is " + r);
	}
	
	@Test
	public void queryChannelIdVerifyIdForListTest()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		service.queryChannelIdVerifyIdForList(0, 1, 10, jsonMap);
		logger.info("===================== jsonMap is " + jsonMap);
	}
}

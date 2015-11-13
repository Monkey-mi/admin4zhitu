package com.imzhitu.admin.interact.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class InteractAutoResponseServiceTest extends BaseTest {
	private Logger log = Logger.getLogger(InteractAutoResponseServiceTest.class);
	
	@Autowired
	private InteractAutoResponseService service;
	
	@Test
	public void scanResponseAndGetAnswerTest()throws Exception{
		service.scanResponseAndGetAnswer();
	}
	
	@Test
	public void queryUncompleteResponseTest()throws Exception{
		Map<String, Object> jsonMap = new HashMap<String,Object>();
		service.queryUncompleteResponse(null,1,10,null,null,jsonMap);
		log.info(jsonMap);
	}
	
	@Test
	public void updateResponseCompleteByIdTest()throws Exception{
		service.updateResponseCompleteById(76);
	}
}

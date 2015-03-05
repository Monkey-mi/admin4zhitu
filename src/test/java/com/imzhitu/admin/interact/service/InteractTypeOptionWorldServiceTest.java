package com.imzhitu.admin.interact.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class InteractTypeOptionWorldServiceTest extends BaseTest{
	@Autowired
	private InteractTypeOptionWorldService service;
	private Logger logger=Logger.getLogger(InteractTypeOptionWorldServiceTest.class);
	
	@Test
	public void autoAddStarWorldTest()throws Exception{
		service.autoAddStarWorld();
	}
	
	@Test
	public void queryTypeOptionWorldForListTest()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		service.queryTypeOptionWorldForList(0, 1, 10, null, null, null, null, null,1, jsonMap);
		logger.info(jsonMap);
	}
	
	

}

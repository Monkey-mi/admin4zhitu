package com.imzhitu.admin.op.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.base.BaseTest;

public class OpStarRecommendServiceTest extends BaseTest{
	@Autowired
	private OpStarRecommendService service;
	
	private Logger log = Logger.getLogger(OpStarRecommendServiceTest.class);
	
	@Test
	public void insertStarRecommendTest()throws Exception{
		service.insertStarRecommend(1595, Tag.TRUE, Tag.TRUE);
	}
	
	@Test
	public void queryStarRecommendTest()throws Exception{
		Map<String,Object>jsonMap = new HashMap<String,Object>();
		service.queryStarRecommend(0, 1, 3, jsonMap, null, null, null, null);
		log.info(jsonMap.toString());
	}

}

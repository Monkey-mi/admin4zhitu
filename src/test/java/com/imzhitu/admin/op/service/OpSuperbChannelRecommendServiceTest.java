package com.imzhitu.admin.op.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.base.BaseTest;

public class OpSuperbChannelRecommendServiceTest extends BaseTest {
	@Autowired
	private OpSuperbChannelRecommendService service;
	
	@Test
	public void insertSuperbChannelRecommendTest()throws Exception{
		service.insertSuperbChannelRecommend(11834, Tag.TRUE,0);
	}
	
	@Test
	public void querySuperbChannelRecommendTest()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		service.qeurySuperbChannelRecommend(null, null, null, null, 1, 10, jsonMap);
	}
	
	@Test
	public void batchUpdateSuperbChannelRecommendValidTest()throws Exception{
		service.batchUpdateSuperbChannelRecommendValid(Tag.FALSE, "1");
	}
}

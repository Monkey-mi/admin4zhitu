package com.imzhitu.admin.op.service;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class OpChannelWorldSchedulaServiceTest extends BaseTest{
	@Autowired
	private OpChannelWorldSchedulaService service;
	private Logger logger = Logger.getLogger(OpChannelWorldSchedulaServiceTest.class);
	
	@Test
	public void insertChannelWorldSchedulaTest()throws Exception{
		service.insertChannelWorldSchedula( 1163, 1, 0, 0, 0);
	}
	
	@Test
	public void updateChannelWorldSchedulaTest()throws Exception{
		service.updateChannelWorldSchedula(null, null, 1163, 2, 1, 1, 1, new Date());
	}
	
	@Test
	public void queryChannelWorldSchedulaForListTest()throws Exception{
		Map<String, Object> jsonMap = new HashMap<String , Object>();
		service.queryChannelWorldSchedulaForList(0, 1, 10, null, null, null, 1, null, null, null, null, jsonMap);
		logger.info(jsonMap.toString());
	}
	
	@Test
	public  void delChannelWorldSchedulaTest()throws Exception{
		service.delChannelWorldSchedula("1,2,3,4");
	}

	@Test
	public void channelWorldSchedulaTest()throws Exception{
		service.channelWorldSchedula();
	}
}

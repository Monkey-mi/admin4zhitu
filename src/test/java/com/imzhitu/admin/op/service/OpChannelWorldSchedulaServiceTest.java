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
	public void updateChannelWorldSchedulaTest()throws Exception{
		service.updateChannelWorldValidSchedula(null, null, 1163, 2, 1, 1, 1, new Date());
	}
	
	@Test
	public void queryChannelWorldSchedulaForListTest()throws Exception{
		Map<String, Object> jsonMap = new HashMap<String , Object>();
		service.queryChannelWorldValidSchedulaForList(0, 1, 10, null, null, null, 1, null, null, null, null, jsonMap);
		logger.info(jsonMap.toString());
	}
	
	@Test
	public  void delChannelWorldSchedulaTest()throws Exception{
		service.delChannelWorldValidSchedula("1,2,3,4");
	}

}

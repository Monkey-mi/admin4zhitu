package com.imzhitu.admin.interact.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.ZombieWorldSchedulaDto;

public class InteractZombieWorldSchedulaServiceTest extends BaseTest{
	@Autowired
	private InteractZombieWorldSchedulaService service;
	private Logger log = Logger.getLogger(InteractZombieWorldSchedulaServiceTest.class);
	
	@Test
	public void insertZombieWorldSchedulaTest()throws Exception{
		service.insertZombieWorldSchedula(286, new Date(),14);
	}
	
	@Test
	public void batchDeleteZombieWorldSchedulaTest()throws Exception{
		service.batchDeleteZombieWorldSchedula("1,2,3");
	}
	
	@Test
	public void updateZombieWorldSchedulaTest()throws Exception{
		service.updateZombieWorldSchedula(null, 286, Tag.FALSE, Tag.TRUE, null, 14);
	}
	
	@Test
	public void queryZombieWorldSchedulaTest()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		service.queryZombieWorldSchedula(null, null, null, null, 0, 1, 10, jsonMap);
		log.info(jsonMap);
	}
	
	@Test
	public void queryZombieWorldSchedulaTotalCountTest()throws Exception{
		long totalCount = service.queryZombieWorldSchedulaTotalCount(null, null, null, null, null);
		log.info("totalCount ===================="+totalCount);
	}
	
	@Test
	public void queryZombieWorldSchedulaByTimeTest()throws Exception{
		Date now = new Date();
		List<ZombieWorldSchedulaDto> list = service.queryZombieWorldSchedulaByTime(new Date(now.getTime()-10*60*1000),now,  Tag.TRUE, Tag.FALSE);
		log.info(list);
	}
	
	@Test
	public void doZombieWorldSchedulaJobTest()throws Exception{
		service.doZombieWorldSchedulaJob();
	}
}

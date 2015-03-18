package com.imzhitu.admin.op.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.OpXiaoMiShuResponse;

public class OpXiaoMiShuResponseServiceTest extends BaseTest{
	@Autowired
	private OpXiaoMiShuResponseService service;
	
	private Logger log = Logger.getLogger(OpXiaoMiShuResponseServiceTest.class);
	
	@Test
	public void insertResponseModuleTest()throws Exception{
		service.insertResponseModule(1,"TestModule", Tag.TRUE, 0);
	}
	
	@Test
	public void insertResponseTest()throws Exception{
		service.insertResponse(1, "test content", 1, 0);
	}
	
	@Test
	public void insertResponseKeyTest()throws Exception{
		service.insertResponseKey(1, 1, "TestKey", Tag.TRUE, 1, 0);
	}
	
	@Test
	public void updateResponseTest()throws Exception{
		service.updateResponse(1, "test content updated", 14);
	}
	
	@Test
	public void updateResponseKeyTest()throws Exception{
		service.updateResponseKey(1, "TestKey Updated", Tag.FALSE, 14);
	}
	
	@Test
	public void updateResponseModuleTest()throws Exception{
		service.updateResponseModule(1, Tag.FALSE, 14);
	}
	
	@Test
	public void queryResponseAndKeyTest()throws Exception{
		Map<String,Object>jsonMap = new HashMap<String,Object>();
		service.queryResponseAndKey(null, 1, 10, null, null, null, null, null, jsonMap);
		log.info(jsonMap);
	}
	
	@Test
	public void queryResponseModuleForTableTest()throws Exception{
		Map<String,Object>jsonMap = new HashMap<String,Object>();
		service.queryResponseModuleForTable(null, 1, 10, null, null, jsonMap);
		log.info(jsonMap);
	}
	
	@Test
	public void queryResponseContentForTableTest()throws Exception{
		Map<String,Object>jsonMap = new HashMap<String,Object>();
		service.queryResponseContentForTable(null, 1, 10, null, jsonMap);
		log.info(jsonMap);
	}
	
	@Test
	public void queryResponseModuleTest()throws Exception{
		List<OpXiaoMiShuResponse>list = service.queryResponseModule(1, null, Tag.TRUE);
		log.info(list);
	}
	
	
	@Test
	public void deleteResponseTest()throws Exception{
		service.deleteResponse(1);
	}
	
	@Test
	public void deleteResponseKeyTest()throws Exception{
		service.deleteResponseKey(1);
	}
	
	@Test
	public void deleteResponseModuleTest()throws Exception{
		service.deleteResponseModule(1, null);
	}
}

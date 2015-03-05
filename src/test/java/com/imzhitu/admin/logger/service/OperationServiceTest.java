package com.imzhitu.admin.logger.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class OperationServiceTest extends BaseTest {

	@Autowired
	private OperationService service;

//	@Test
	public void testSaveOperation() throws Exception {
		service.deleteOperation("-144");
		service.saveOperation(-144,
				"void com.imzhitu.admin.ztworld.service.ZTWorldService.updateLatestValid(Integer,Integer)", "测试名称", "测试");
		
	}
	
//	@Test
	public void testDeleteOperation() throws Exception {
		service.deleteOperation("-144");
	}
	
	@Test
	public void testBuildOperation() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildOperation(0, true, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildUserOperation() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildUserOperation(1000, 1, 10, null, null, null, null, jsonMap);
		service.buildUserOperation(0, 1, 10, null, null, null, null, jsonMap);
		logObj(jsonMap);
	}
}

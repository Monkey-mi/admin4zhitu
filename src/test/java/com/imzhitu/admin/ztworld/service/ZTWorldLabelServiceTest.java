package com.imzhitu.admin.ztworld.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

/**
 * <p>
 * 织图标签业务逻辑单元测试
 * </p>
 * 
 * 创建时间:2014-5-7
 * @author tianjie
 *
 */
public class ZTWorldLabelServiceTest extends BaseTest {

	@Autowired
	private ZTWorldLabelService service;
	
	@Test
	public void testBuildLabel() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildLabel(0, 1, null, "serial", "desc", 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testUpdateHotLabel() throws Exception {
		service.updateHotLabel(10, 5);
	}
	
	@Test
	public void testBuildLabelWorld() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildLabelWorld(12016, 2, null, 1, 10, jsonMap);
		service.buildLabelWorld(0, 2, null, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildLabelIdsWithoutReject() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildLabelIdsWithoutReject(12167, jsonMap);
		logObj(jsonMap);
	}
	
}

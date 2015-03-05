package com.imzhitu.admin.ztworld.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class ZTWorldChildWorldServiceTest extends BaseTest {

	@Autowired
	private ZTWorldChildWorldService service;
	
//	@Test
	public void testSaveChildType() throws Exception {
		service.saveChildType("http://imzhitu.qiniudn.com/world/thumbs/octagon.png",
				100,
				100,
				"测试描述",
				"毕业季",
				"http://imzhitu.qiniudn.com/world/thumbs/octagon.png");
	}
	
	@Test
	public void testBuildChildType() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildChildType(1000, 1, 10, jsonMap);
		service.buildChildType(0, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testDeleteChildType() throws Exception {
		service.deleteChildType("2");
	}
	
	@Test
	public void testUpdateChilTypeSerial() throws Exception {
		String[] idStrs = new String[]{"3"};
		service.updateChildTypeSerial(idStrs);
	}
	
	@Test
	public void testUpdateLatestChildType() throws Exception {
		service.updateLatestChildType(10);
	}
}

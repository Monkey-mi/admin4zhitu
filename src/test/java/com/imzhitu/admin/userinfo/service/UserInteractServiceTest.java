package com.imzhitu.admin.userinfo.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.UserReportDto;


public class UserInteractServiceTest extends BaseTest {

	private static Logger logger = Logger.getLogger(UserInteractServiceTest.class);
	
	@Autowired
	private UserInteractService service;
	
	@Test
	public void testBuildFollow() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildFollow(527, 0, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildConcern() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildConcern(2063, 0, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildReport() throws Exception {
		logNumberList(logger, new TestNumberListAdapter() {

			@Override
			public void buildTestNumberList(Map<String, Object> jsonMap)
					throws Exception {
				UserReportDto report = new UserReportDto();
				service.buildReport(report, 1, 10, jsonMap);
			}
			
		});
	}
	
	@Test
	public void testUpdateReport() throws Exception {
		service.updateReportFollowed("14");
	}
}

package com.imzhitu.admin.statistics.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class SummaryStatisticsServiceTest extends BaseTest {

	private static Logger logger = Logger.getLogger(SummaryStatisticsServiceTest.class);
	
	@Autowired
	private SummaryStatisticsService service;

//	@Test
//	public void testBuildSummary() throws Exception {
//		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		service.buildSummary(null, null, jsonMap);
//		logObj(jsonMap);
//	}
	
	@Test
	public void testBuildSummary() throws Exception {
		logNumberList(logger, new TestNumberListAdapter() {

			@Override
			public void buildTestNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buidlSummary(null, 1, 10, jsonMap);
			}
		});
	}
}

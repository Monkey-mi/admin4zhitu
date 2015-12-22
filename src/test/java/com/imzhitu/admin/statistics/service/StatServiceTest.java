package com.imzhitu.admin.statistics.service;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.statistics.pojo.StatPvDto;

public class StatServiceTest extends BaseTest {

	@Autowired
	private StatService service;

	@Test
	public void buildPvTest() throws Exception {
		logNumberList(logger, new TestNumberListAdapter() {
			
			@Override
			public void buildTestNumberList(Map<String, Object> jsonMap) throws Exception {
				service.buildPV(new StatPvDto(), null, null, 1, 10, jsonMap);
			}
		});
	}
}

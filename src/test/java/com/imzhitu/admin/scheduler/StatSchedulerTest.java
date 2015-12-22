package com.imzhitu.admin.scheduler;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

/**
 * 统计计划业务逻辑单元测试
 * 
 * @author lynch 2015-12-21
 *
 */
public class StatSchedulerTest extends BaseTest {

	@Autowired
	private StatScheduler sche;
	
	@Test
	public void refreshPv2Test() throws Exception {
		sche.refreshPv();
	}
	
}

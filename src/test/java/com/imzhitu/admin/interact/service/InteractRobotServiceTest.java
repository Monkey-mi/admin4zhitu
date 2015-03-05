package com.imzhitu.admin.interact.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
public class InteractRobotServiceTest extends BaseTest{
	
	@Autowired
	private InteractRobotService service;
	
//	@Test
//	public void getAnswerTest()throws Exception{
//		String str = service.getAnswer("你好！");
//		logger.info("======================== str is "+ str);
//	}
	@Test
	public void getAnswerFromTulingTest()throws Exception{
		String str = service.getAnswerFromTuLing("你好");
		logger.info("======================== str is "+ str);
	}
}

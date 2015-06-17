package com.imzhitu.admin.aliyun.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class OsPushServiceTest extends BaseTest {

	@Autowired
	private OsPushService service;
	
	@Test
	public void pushUpdateTest() throws InterruptedException {
		service.pushUpdate();
		Thread.sleep(3000);
	}
}

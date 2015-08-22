package com.imzhitu.admin.interact.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class InteractLikeFollowRecordServiceTest extends BaseTest{
	
	@Autowired
	private InteractLikeFollowRecordService service;
	
	
	@Test
	public void addLikeFollowInteractTest()throws Exception{
		service.addLikeFollowInteract(485, 27846, 0);
	}
	
	@Test
	public void doLikeFollowJobTest()throws Exception{
		service.doLikeFollowJob();
	}

}

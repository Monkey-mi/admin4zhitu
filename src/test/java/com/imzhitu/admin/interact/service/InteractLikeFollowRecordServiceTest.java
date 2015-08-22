package com.imzhitu.admin.interact.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
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
	
	@Test
	public void batchUpdateLikeFollowRecordTest()throws Exception{
		Integer[] ids = new Integer[1];
		ids[0] = 4;
		service.batchUpdateLikeFollowRecord(Tag.TRUE, ids);
	}

}

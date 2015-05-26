package com.imzhitu.admin.op.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class OpChannelLinkServiceTest extends BaseTest{
	@Autowired
	private OpChannelLinkService service;
	
	@Test
	public void insertOpChannelLinkTest()throws Exception{
		service.insertOpChannelLink(11809, 11714);
	}
	
	@Test
	public void deleteOpChannelLinkTest()throws Exception{
		service.deleteOpChannelLink(11809, 11714);
	}

}

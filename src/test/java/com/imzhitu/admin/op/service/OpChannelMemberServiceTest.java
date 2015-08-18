package com.imzhitu.admin.op.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class OpChannelMemberServiceTest extends BaseTest{
	
	@Autowired
	private OpChannelMemberService service;
	
	@Test
	public void insertChannelMemberTest()throws Exception{
//		service.insertChannelMember(11834, 527,0);
	}
	
	@Test
	public void updateChannelMemberDegreeTest()throws Exception{
		service.updateChannelMemberDegree(11834, 527, 1);
	}
	
}

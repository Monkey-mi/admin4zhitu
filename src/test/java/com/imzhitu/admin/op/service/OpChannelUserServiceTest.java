package com.imzhitu.admin.op.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.OpChannelUserDto;

public class OpChannelUserServiceTest extends BaseTest{
	public Logger logger = Logger.getLogger(OpChannelUserServiceTest.class);
	@Autowired
	private OpChannelUserService service;
	
	@Test
	public void queryChannelUserRankTopNTest()throws Exception{
		List<Integer> list = service.queryChannelUserRankTopN(1);
		logger.info("=============================Rank Top N is : " + list);
	}
	
	
	
	@Test
	public void deleteChannelUserTest()throws Exception{
		service.deleteChannelUser(null, 888, null, null, null);
	}
	
	@Test
	public void deleteChannelUserByIdsTest() throws Exception{
		service.deleteChannelUserByIds("888,889");
	}
	
	@Test
	public void insertChannelUserTest()throws Exception{
		service.insertChannelUser(888, 2, 1, 0);
	}
	
	@Test
	public void updateChannelUserValidTest()throws Exception{
		service.updateChannelUserValid(null, 888, 2, 1, 14);
	}
	
	
	@Test
	public void queryChannelUserForListTest()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		OpChannelUserDto dto = new OpChannelUserDto();
		service.queryChannelUserForList(dto,0, 1, 10, jsonMap);
		for(int i=0; i<jsonMap.size();i++){
			logger.info("======================= jsonMap is : " + jsonMap.toString());
		}
	}
	
	
}

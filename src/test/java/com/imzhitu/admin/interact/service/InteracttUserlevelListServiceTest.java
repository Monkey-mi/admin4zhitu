package com.imzhitu.admin.interact.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.UserLevelListDto;

public class InteracttUserlevelListServiceTest extends BaseTest{
	
	@Autowired
	InteractUserlevelListService service;
	
	@Test
	public void testQueryUserlevelList()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		service.QueryUserlevelList(100, 1035,null,null,null,1, 10, jsonMap);
		logger.debug(jsonMap.toString());
	}
	
	@Test
	public void testDeleteUserlevelByIds()throws Exception{
		service.DeleteUserlevelByIds("3,4");
	}
	
	@Test
	public void testAddUserlevelList()throws Exception{
		Date now = new Date();
		service.AddUserlevel(new UserLevelListDto(0,1035,45,1,null,null,now,now,0,null));
	}
	
	@Test
	public void testScanNewWorldAndJoinIntoInteract(){
		service.ScanNewWorldAndJoinIntoInteract();
	}
	
	@Test
	public void testQueryUserlevelByUserId()throws Exception{
		service.QueryUserlevelByUserId(1035);
	}
	
	@Test
	public void testCheckUserlevelExistByUserId()throws Exception{
		boolean b = service.CheckUserlevelExistByUserId(527);
		logger.debug("========================================================b is "+b);
	}
}

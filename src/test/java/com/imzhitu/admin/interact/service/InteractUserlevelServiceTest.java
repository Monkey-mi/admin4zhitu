package com.imzhitu.admin.interact.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.UserLevelDto;

public class InteractUserlevelServiceTest extends BaseTest{
	
	@Autowired
	InteractUserlevelService service;
	
	@Test
	public void testQueryUserlevelList()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		service.QueryUserlevelList(20, 1, 10, jsonMap);
		JSONObject jsObj = JSONObject.fromObject(jsonMap);
		logger.debug(jsObj);
	}
	
	@Test
	public void testAddUserlevel()throws Exception{
		service.AddUserlevel(new UserLevelDto(19,10,20,10,20,10,20,10,20,24,"A-",0));
	}
	
	@Test
	public void testDeleteUserlevelByIds()throws Exception{
		service.DeleteUserlevelByIds("1,19");
	}
	
	@Test
	public void testQueryUserLevel()throws Exception{
		service.QueryUserLevel();
	}
}

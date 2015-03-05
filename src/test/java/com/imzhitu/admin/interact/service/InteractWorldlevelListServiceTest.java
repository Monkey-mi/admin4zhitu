package com.imzhitu.admin.interact.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.InteractWorldLevelListDto;

public class InteractWorldlevelListServiceTest extends BaseTest{
	
	@Autowired
	InteractWorldlevelListService service;
	
	
	@Test
	public void testAddWorldlevelList()throws Exception{
		service.addWorldlevelList(12318, 59, 0,null,null,0);
	}
	@Test
	public void testUpdateWorldlevelListValidity()throws Exception{
		service.updateWorldlevelListValidity(13772, 60, 1,0);
	}
	@Test
	public void testDelWorldlevelListByWIds()throws Exception{
		service.delWorldlevelListByWIds("12318,12317");
	}
	
	@Test
	public void testQueryWorldlevelList()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		service.queryWorldlevelList(0,12318,null,null,null, 1, 10, jsonMap);
		logger.info(jsonMap);
	}
	
	@Test
	public void testQueryWorldLevelListByWid()throws Exception{
		if(service.queryWorldLevelListByWid(12318)==null){
			logger.info("======================queryWorldLevelListByWid is null============--=");
		}else{
			logger.info("======================queryWorldLevelListByWid is  not null============--=");
		}
	}
	
	@Test
	public void testQueryWorldLevelByWid()throws Exception {
		InteractWorldLevelListDto  dto = service.queryWorldLevelListByWid(12318);
		if(null == dto)
			logger.info("======================QueryWorldLevelByWid is   null============--=");
		else
			logger.info("======================QueryWorldLevelByWid is not null============--=");
	}
	
	@Test
	public void testUpdate()throws Exception{
		service.updateWorldLevelList(12318, 60, 1, "1,2,3", "1,2,3",14);
	}
	@Test
	public void testQueryUnInteractCount()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		service.queryWorldUNInteractCount(12318, jsonMap);
		logger.info(jsonMap.toString());
	}
	
}

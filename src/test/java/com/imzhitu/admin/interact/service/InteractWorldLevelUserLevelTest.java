package com.imzhitu.admin.interact.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class InteractWorldLevelUserLevelTest extends BaseTest{
	@Autowired
	private InteractWorldLevelUserLevelService service;
	
	@Test
	public void delWorldLevelUserLevelByIdsTest()throws Exception{
		service.delWorldLevelUserLevel("1,2,3");
	}
	
//	@Test
	public void addWorldLevelUserLevelTest()throws Exception{
		service.addWorldLevelUserLevel(1, 1, 14);
	}
	
	@Test
	public void queryWorldLevelUserLevelByUidTest()throws Exception{
		service.queryWorldLevelUserLevelByUid(527);
	}
	
	
}

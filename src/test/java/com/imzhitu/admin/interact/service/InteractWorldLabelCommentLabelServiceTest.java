package com.imzhitu.admin.interact.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class InteractWorldLabelCommentLabelServiceTest extends BaseTest{
	
	@Autowired
	InteractWorldLabelCommentLabelService service;
	
	@Test
	public void QueryULCLList()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		service.QueryULCLList(100, 1, 10, jsonMap);
		JSONObject jsObj = JSONObject.fromObject(jsonMap);
		logger.debug(jsObj);
	}
	
	@Test
	public void testQueryWorldLabelTree()throws Exception{
		service.GetWorldLabelTree();
	}
	
	@Test
	public void testQueryCommentLabel(){
		service.QueryCommentLabel();
	}
	
	@Test
	public void testDeleteUserLabelCommentLabelByIds(){
		service.DeleteWorldLabelCommentLabelByIds("1,2,3");
	}
}

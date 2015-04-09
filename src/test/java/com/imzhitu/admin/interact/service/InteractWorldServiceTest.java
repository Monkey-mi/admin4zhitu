package com.imzhitu.admin.interact.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.imzhitu.admin.base.BaseTest;

public class InteractWorldServiceTest extends BaseTest {

	@Autowired
	InteractWorldService service;
	
//	@Test
	public void testSaveInteract() throws Exception {
		service.saveInteract(14302, 50, 0, null, 2);
	}
	
	@Test
	public void testSaveInteractV2() throws Exception {
		String[] s = "1234,1235,1236,1237,1238,1239,12310,12311,12332,12333,12334".split(",");
		service.saveInteractV2(14303, 5000, 4,s, 20);
	}
	
	@Test
	public void testBuildInteracts() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildInteracts(16220, 0, 1, 10, jsonMap);
		JSONObject jsObj = JSONObject.fromObject(jsonMap);
		logger.debug(jsObj);
	}
	
	@Test
	public void testBuildComments() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildComments(13367, 0, 2, 10, jsonMap);
		JSONObject jsObj = JSONObject.fromObject(jsonMap);
		logger.debug(jsObj);
	}
	
	@Test
	public void testBuildClicks() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildClicks(17, 52, 2, 10, jsonMap);
		JSONObject jsObj = JSONObject.fromObject(jsonMap);
		logger.debug(jsObj);
	}
	
	@Test
	public void testBuildLikeds() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildLikeds(101, 63, 1, 10, jsonMap);
		JSONObject jsObj = JSONObject.fromObject(jsonMap);
		logger.debug(jsObj);
	}
	
	@Test
	public void testUpdateInteract() throws Exception {
		service.updateInteractValid(17);
	}
	
	@Test
	public void testQueryInteractSum() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildInteractSum(10962, jsonMap);
		JSONObject jsObj = JSONObject.fromObject(jsonMap);
		logger.debug(jsObj);
	}
	
	@Test
	public void testBuildInteractByWorldIds() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildInteractByWorldIds("18188,1000", jsonMap);
		JSONObject jsObj = JSONObject.fromObject(jsonMap);
		logger.debug(jsObj);
	}
	
	@Test
	public void testUpdateUnFinishedInteractSchedule() throws Exception	 {
		service.updateUnFinishedInteractSchedule();
	}

	@Test
	public void testSaveUserInteract() throws Exception {
		service.saveUserInteract(145, 1, 24);
	}
	
	@Test
	public void testTrackInteract() throws Exception {
		service.trackInteract();
	}
	
	@Test
	public void testCommitComment() throws Exception {
		service.commitComment();
	}
	
	
	@Test
	public void testGetScheduleCountV2()throws Exception{
		List<Integer> list = service.getScheduleCountV2(3000,120);
		logger.info(list);
	}
}

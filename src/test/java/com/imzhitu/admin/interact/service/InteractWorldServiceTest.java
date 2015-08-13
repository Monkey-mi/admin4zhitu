package com.imzhitu.admin.interact.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.base.BaseTest;

public class InteractWorldServiceTest extends BaseTest {

	@Autowired
	InteractWorldService service;
	
	//@Test
	public void testSaveInteract() throws Exception {
		service.saveInteractV3(27522, 350, 10, "1234,1235,1236,1237,1238,1239,12310,12311".split(","), 60);
	}
	
	//@Test
	public void testSaveInteractV3() throws Exception {
		String[] s = "1234,1235,1236,1237,1238,1239,12310,12311,12332,12333,12334".split(",");
		service.saveInteractV3(14303, 5000, 4,s, 20);
	}
	
//	@Test
	public void testSaveInteractV3ByUserLevelAndLabel()throws Exception{
		service.saveInteractV3(25594, "39,40");
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
		service.buildClicks(697, 0, 1, 10, jsonMap);
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
		service.saveUserInteract(485, 1, 24);
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
	
	@Test
	public void testGetScheduleV3()throws Exception{
		Date  now = new Date();
		List<Date> dateList = service.getScheduleV3(new Date(now.getTime() - 60*60*1000L), 60,8 );
		logger.info("dateList =======>"+dateList);
		Date end = new Date();
		logger.info("cost:"+(end.getTime() - now.getTime() ) +"ms.");
	}
	
	
	@Test
	public void testCommit()throws Exception{
		service.commitClick();
		service.commitComment();
		service.commitFollow();
		service.commitLiked();
	}
	
}

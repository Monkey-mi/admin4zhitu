package com.imzhitu.admin.interact.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.InteractCommentLabel;

public class InteractCommentServiceTest extends BaseTest {

	@Autowired
	private InteractCommentService service;
	
	@Test
	public void testBatchSaveComment() throws Exception {
//		service.batchSaveComment(new File("E:/fff.txt"), 31);
	}
	
	@Test
	public void testBuildComments() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildComments(1,"好", 0, 1, 10, jsonMap);
		JSONObject jsObj = JSONObject.fromObject(jsonMap);
		logger.debug(jsObj);
	}
	
	@Test
	public void testGetRandomCommentIds() throws Exception {
		List<Integer> list = service.getRandomCommentIds(1, 10);
		for(Integer id : list) {
			logger.debug(id);
		}
	}
	
	@Test
	public void testQueryAllLabels() throws Exception {
		List<InteractCommentLabel> list = service.getAllLabels();
		System.out.println("-------------------------->"+list.get(0).getLabelName()+"<---------------------------------------");
	}
	
	@Test
	public void testSaveLabel()throws Exception{
		service.saveLabel("夏威夷",35);
	}
	
}

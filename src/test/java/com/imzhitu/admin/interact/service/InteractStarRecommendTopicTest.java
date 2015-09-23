package com.imzhitu.admin.interact.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
public class InteractStarRecommendTopicTest extends BaseTest{
	
	@Autowired
	private InteractStarRecommendTopicService interactStarRecommendTopicService;
	
	
	@Test
	public void  getTopicId() throws Exception{
	List<Integer>  list  = 	interactStarRecommendTopicService.getTopicId();
/*	for(Integer i : list){

	}*/
	}
	
}

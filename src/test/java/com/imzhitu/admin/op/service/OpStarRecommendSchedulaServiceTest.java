package com.imzhitu.admin.op.service;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.base.BaseTest;

public class OpStarRecommendSchedulaServiceTest  extends BaseTest{
	@Autowired
	private OpStarRecommendSchedulaService service;
	
	@Test
	public void doStarRecommendSchedulaTest()throws Exception{
		service.doStarRecommendSchedula();
	}
	
	@Test
	public void updateStarRecommendSchedulaTest()throws Exception{
		service.updateStarRecommendSchedula(18, null, null, null,new Date(), 14, Tag.TRUE);
	}
}

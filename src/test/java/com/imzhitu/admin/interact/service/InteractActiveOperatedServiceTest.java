package com.imzhitu.admin.interact.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.base.BaseTest;
public class InteractActiveOperatedServiceTest extends BaseTest{
	
	@Autowired
	InteractActiveOperatedService service;
	
	@Test
	public void testAddOperated()throws Exception{
		service.addOperated(123, Tag.TRUE);
	}
	
	@Test
	public void checkIsOperatedByWIdTest()throws Exception{
		boolean r = service.checkIsOperatedByWId(123);
		logger.info("==============================>>>>>>>>>r="+r);
	}
	
	@Test
	public void updateOperatedTest()throws Exception{
		service.updateOperated(123, Tag.FALSE);
	}
	
	@Test
	public void delOperatedTest()throws Exception{
		service.delOperated(123);
	}
}

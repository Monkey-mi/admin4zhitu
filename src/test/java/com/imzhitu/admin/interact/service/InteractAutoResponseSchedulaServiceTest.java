package com.imzhitu.admin.interact.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.InteractAutoResponseSchedula;

public class InteractAutoResponseSchedulaServiceTest extends BaseTest{
	@Autowired
	private InteractAutoResponseSchedulaService service;
	
	private Logger log = Logger.getLogger(InteractAutoResponseSchedulaServiceTest.class);
	
	@Test
	public void addAutoResponseSchedulaTest()throws Exception{
		service.addAutoResponseSchedula(100, 1, 0, null, 0);
	}
	
	
	@Test
	public void delInteractAutoResponseSchedulaTest()throws Exception{
		service.delInteractAutoResponseSchedula("1,2,3");
	}
	
	@Test
	public void updateAutoResponseSchedulaTest()throws Exception{
		service.updateAutoResponseSchedula(1, 1, 1, 1);
	}
	
	@Test
	public void queryUnCompleteSchedulaTest()throws Exception{
		List<InteractAutoResponseSchedula> list = service.queryUnCompleteSchedula(0, 1, null, null);
		log.info(list.toString());
	}
	
	@Test
	public void doAutoResponseSchedulaJobTest()throws Exception{
		service.doAutoResponseSchedulaJob();
	}
	
	
	@Test
	public void queryAutoResponseSchedulaForTableTest()throws Exception{
		Map<String,Object>jsonMap = new HashMap<String,Object>();
		service.queryAutoResponseSchedulaForTable(null, null, null, null, null,null, 1, 10, jsonMap);
		log.info(jsonMap);
	}

}

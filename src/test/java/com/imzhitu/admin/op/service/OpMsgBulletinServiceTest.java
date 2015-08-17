package com.imzhitu.admin.op.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.base.BaseTest;

public class OpMsgBulletinServiceTest extends BaseTest{
	@Autowired
	private OpMsgBulletinService service;
	
	private Logger log = Logger.getLogger(OpMsgBulletinServiceTest.class);
	
	@Test
	public void insertMsgBulletinTest()throws Exception{
		service.insertMsgBulletin("http://static.imzhitu.com/ios/image/2015/06/15/15/6e59030b0b4a2c977203581e0ca5015d_20991@2x.jpg", 1, "http://www.imzhitu.com", 0,null,null);
	}
	
	@Test
	public void batchDeleteMsgBulletinTest()throws Exception{
		service.batchDeleteMsgBulletin("2,3,4,5,6,7,8,9");
	}
	
	@Test
	public void updateMsgBulletinTest()throws Exception{
		service.updateMsgBulletin(1, "http://static.imzhitu.com/ios/image/2015/06/15/15/6e59030b0b4a2c977203581e0ca5015d_20991@2x.jpg", 1, "http://www.imzhitu.com", 1, 14,null,null);
	}
	
	@Test
	public void batchUpdateMsgBulletinValid()throws Exception{
		service.batchUpdateMsgBulletinValid("1,2,3,4",Tag.TRUE,14);
	}
	
	@Test
	public void queryMsgBulletinTest()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		service.queryMsgBulletin(null, null, null, null, null, 1, 10, jsonMap);
		log.info(jsonMap);
	}
	
	@Test
	public void queryMsgBulletinTotalCount()throws Exception{
		long total = service.queryMsgBulletinTotalCount(null, null, null, null);
		log.info("===========total count:"+total);
	}
}

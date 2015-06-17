package com.imzhitu.admin.op.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.base.BaseTest;

public class OpMsgServiceTest extends BaseTest {

	@Autowired
	private OpMsgService service;
	
	@Test
	public void testBuildNotice() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildNotice(jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testSaveNotice() throws Exception {
		service.saveNotice("http://imzhitu.qiniudn.com/op/notice/2014081101.jpg", "http://www.imzhitu.com", Tag.IOS);
	}

//	@Test
//	public void updateStartPageTest() throws Exception {
//		Date beginDate = new Date();
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date endDate = df.parse("2015-06-20 00:00:00");
//		service.updateStartPageCache(
//				"http://imzhitu.qiniudn.com/op/notice/2015061001.png",
//				3,"自拍情结",
//				beginDate , endDate, 10);
//	}
	
}

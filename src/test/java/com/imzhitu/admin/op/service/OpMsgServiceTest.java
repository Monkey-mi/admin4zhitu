package com.imzhitu.admin.op.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.OpSysMsg;

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
//		service.saveNotice("http://static.imzhitu.com/op/notice/2014081101.jpg", "http://www.imzhitu.com", Tag.IOS);
	}
	
	@Test
	public void testPushAppMsg() throws Exception {
//		OpSysMsg msg = new OpSysMsg();
//		msg.setObjType(Tag.USER_MSG_SYS);
//		msg.setContent("哈哈哈哈哈222！ ");
//		service.pushAppMsg(msg, true, false, null);
	}

//	@Test
//	public void updateStartPageTest() throws Exception {
//		Date beginDate = new Date();
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date endDate = df.parse("2015-06-20 00:00:00");
//		service.updateStartPageCache(
//				"http://static.imzhitu.com/op/notice/2015061001.png",
//				3,"自拍情结",
//				beginDate , endDate, 10);
//	}
	
}

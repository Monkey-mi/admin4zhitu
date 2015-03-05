package com.imzhitu.admin.op.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.OpSysMsg;
import com.imzhitu.admin.op.service.OpMsgService;

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
//	public void pushAppMsgTest() throws Exception {
//		OpSysMsg msg = new OpSysMsg();
//		msg.setObjId(0);
//		msg.setObjType(9);
//		msg.setObjMeta("http://www.imzhitu.com");
//		msg.setContent("呵呵");
//		service.pushAppMsg(msg, true, true);
//	}
}

package com.imzhitu.admin.userinfo.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class UserMsgServiceTest extends BaseTest {

	@Autowired
	private UserMsgService service;
	
	@Test
	public void testPushSysMsg() throws Exception {
		service.pushSysMsg("485", "您已经有几天没来织图了，粉丝们都很想念您");
	}
	
	@Test
	public void testPushListMsg() throws Exception {
//		String idsStr = "527";
//		service.pushListMsg("多用户群推送测试", 12046, null, idsStr);
	}
	
	@Test
	public void testBuildSenderMsgIndex() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildRecipientMsgBox(0,null, 485, null, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildUserMsg() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildUserMsg(485, 400, 0, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void buildDanmu() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildUserMsgDanmu(0, 1, 10, jsonMap);
		logObj(jsonMap);
	}
}

package com.imzhitu.admin.userinfo.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.UserMsgConversationDto;

public class UserMsgServiceTest extends BaseTest {

	
	@Autowired
	private UserMsgService service;
	
	@Test
	public void buildConversationTest() throws Exception {
		logNumberList(logger, new TestNumberListAdapter() {
			
			@Override
			public void buildTestNumberList(Map<String, Object> jsonMap) throws Exception {
				UserMsgConversationDto conver = new UserMsgConversationDto();
				conver.setUserId(13);
				conver.setFirstRow(0);
				conver.setLimit(10);
				service.buildConversation(conver, 1, 10, jsonMap);
			}
		});
	}
	
	@Test
	public void testBuildUserMsg() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildUserMsg(400, 0, 1, 10, jsonMap);
		logObj(jsonMap);
	}
	
//	@Test
//	public void buildDanmu() throws Exception {
//		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		service.buildUserMsgDanmu(0, 1, 10, jsonMap);
//		logObj(jsonMap);
//	}
}

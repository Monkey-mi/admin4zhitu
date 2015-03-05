package com.imzhitu.admin.privileges.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.AdminUser;

public class AdminServiceTest extends BaseTest {
	
	private Logger log = Logger.getLogger(AdminServiceTest.class);
	
	@Autowired
	private AdminService service;
	
	@Test
	public void testRegister() throws Exception {
		AdminUser u = new AdminUser();
		u.setLoginCode("zzzzzz");
		u.setUserName("zzz");
		u.setPassword("");
		u.setValid(1);
		service.register(u, new String[]{"12","3"});
	}
	
	@Test
	public void deleteUserInfo() throws Exception {
		service.deleteUserInfo("22,23");
	}
	
	@Test
	public void testGetUserInfoById() throws Exception {
		AdminUser u = service.getUserInfoById(1);
		log.debug(u);
	}
	
	@Test
	public void testUpdateUserInfo() throws Exception {
		AdminUser u = new AdminUser();
		u.setId(1);
//		u.setPassword("123456");
		u.setUserName("tom");
		service.updateUserInfo(u, null);
	}
	
	@Test
	public void testCheckExist() throws Exception {
		AdminUser u = new AdminUser();
		u.setUserName("bbb");
		u.setLoginCode("zhutianjie123");
		boolean flag = service.checkExist(u);
		log.debug(flag);
	}
	
	@Test
	public void testBuildUserInfo() throws Exception {
		logNumberList(log, new TestNumberListAdapter() {
			
			@Override
			public void buildTestNumberList(Map<String, Object> jsonMap) throws Exception {
				AdminUser user = new AdminUser();
				user.setMaxId(2);
				service.buildUserInfo(true, user, 2, 10, jsonMap);
			}
		});
	}
	
	@Test
	public void testLogTestProperty() throws Exception {
		service.logProperty();
	}
}

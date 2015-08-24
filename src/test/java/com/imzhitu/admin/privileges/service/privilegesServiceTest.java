package com.imzhitu.admin.privileges.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.AdminTimeManageDto;
import com.imzhitu.admin.privileges.service.impl.PrivilegesServiceImpl;

public class privilegesServiceTest extends BaseTest {

	
	@Autowired
	private PrivilegesServiceImpl service;
	
	@Test
	public void adminTimeManageTest() throws Exception{
		
/*		//测试添加数据
		service.addAdminTimeManage(14, "01:00:00", "03:56:14", 11);*/
		
		//测试展示数据
		List<AdminTimeManageDto> list = service.queryAdminTimeManage(null, null, null, null);
	}
	
	/**
	 * 
	 * @throws Exception 
		*	2015年8月21日
		*	mishengliang
	 */
	@Test
	public void deleteAdminTimeManageByUserIds() throws Exception{
		service.deleteAdminTimeManageByUserIds("20,14");
	}
	
}

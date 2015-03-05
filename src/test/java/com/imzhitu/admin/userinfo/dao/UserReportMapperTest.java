package com.imzhitu.admin.userinfo.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.UserReportDto;
import com.imzhitu.admin.userinfo.mapper.UserReportMapper;

public class UserReportMapperTest extends BaseTest {

	@Autowired
	private UserReportMapper mapper;
	
	@Test
	public void testQueryReport() {
		UserReportDto dto = new UserReportDto();
		dto.setMaxId(1000);
		mapper.queryReport(dto);
	}
}

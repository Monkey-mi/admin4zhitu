package com.imzhitu.admin.userinfo.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.userinfo.mapper.UserInfoMapper;

public class UserInfoMapperTest extends BaseTest {

	@Autowired
	private UserInfoMapper mapper;
	
	@Test
	public void updateTrustTest() {
		mapper.updateTrust(485, Tag.TRUE);
	}
}

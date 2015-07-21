package com.imzhitu.admin.userinfo.mapper;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;

public interface UserSocialAccountMapper {

	@DataSource("master")
	public void deleteByUID(Integer userId);
	
}

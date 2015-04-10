package com.imzhitu.admin.userinfo.mapper;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;

public interface UserLoginPersistentMapper {

	@DataSource("master")
	public int deleteByUserId(Integer userId);
	
}

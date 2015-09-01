package com.imzhitu.admin.notifySet.mapper;


import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;

public interface NotifyMapper {

	@DataSource("slave")
	public String queryNotify(String notifyType);
	
	
	@DataSource("master")
	public void addNotify(@Param("notifyType")String notifyType,@Param("notify")String notify);
}

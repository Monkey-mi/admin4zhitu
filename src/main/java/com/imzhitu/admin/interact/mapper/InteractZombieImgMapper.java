package com.imzhitu.admin.interact.mapper;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;

public interface InteractZombieImgMapper {
	@DataSource("slave")
	public long queryCount(String imgName);
	@DataSource("master")
	public long insertZombieImg(String imgName);
}

package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.ZombieChildWorld;

public interface InteractZombieChildWorldMapper {
	
	/**
	 * 增加
	 * @param dto
	 */
	@DataSource("master")
	public void insertZombieChildWorld(ZombieChildWorld dto);
	
	/**
	 * 查询子世界
	 * @param zombieWorldId
	 * @return
	 */
	@DataSource("slave")
	public List<ZombieChildWorld> queryZombieChildWorld(Integer zombieWorldId);
	
	@DataSource("slave")
	public List<String> queryZombieChildWorldPath(Integer zombieWorldId);
}

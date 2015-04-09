package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.ZombieChildWorld;

public interface InteractZombieChildWorldMapper {
	
	/**
	 * 增加
	 * @param dto
	 */
	public void insertZombieChildWorld(ZombieChildWorld dto);
	
	/**
	 * 查询子世界
	 * @param zombieWorldId
	 * @return
	 */
	public List<ZombieChildWorld> queryZombieChildWorld(Integer zombieWorldId);
	
	public List<String> queryZombieChildWorldPath(Integer zombieWorldId);
}

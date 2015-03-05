package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.ZombieChildWorld;

public interface InteractZombieChildWorldMapper {
	public void insertZombieChildWorld(ZombieChildWorld dto);
	public List<ZombieChildWorld> queryZombieChildWorld(Integer zombieWorldId);
	public List<String> queryZombieChildWorldPath(Integer zombieWorldId);
}

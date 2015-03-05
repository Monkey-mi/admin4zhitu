package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.ZombieWorld;

public interface InteractZombieWorldMapper {
	public void insertZombieWorld(ZombieWorld dto);
	public void updateComplete(ZombieWorld dto);
	public List<Integer> queryZombieWorldId(ZombieWorld dto);
	public List<ZombieWorld> queryZombieWorld(ZombieWorld dto);
	public List<ZombieWorld> queryZombieWorldForTable(ZombieWorld dto);
	public long queryZombieWorldTotalCount();
	public long queryZombieWorldTotalCountForTable(ZombieWorld dto);
}

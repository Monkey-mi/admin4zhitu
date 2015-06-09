package com.imzhitu.admin.interact.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.ZombieWorld;

public interface InteractZombieWorldMapper {
	@DataSource("master")
	public void insertZombieWorld(ZombieWorld dto);
	@DataSource("master")
	public void updateComplete(ZombieWorld dto);
	
	@DataSource("slave")
	public List<Integer> queryZombieWorldId(ZombieWorld dto);
	
	@DataSource("slave")
	public List<ZombieWorld> queryZombieWorld(ZombieWorld dto);
	
	@DataSource("slave")
	public List<ZombieWorld> queryZombieWorldForTable(ZombieWorld dto);
	
	@DataSource("slave")
	public long queryZombieWorldTotalCount();
	
	@DataSource("slave")
	public long queryZombieWorldTotalCountForTable(ZombieWorld dto);
	
	@DataSource("master")
	public void updateZombieWorldLabel(ZombieWorld dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DataSource("master")
	public void batchDeleteZombieWorld(Integer[] ids);
}

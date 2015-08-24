package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.ZombieWorld;

public interface InteractZombieWorldMapper {
	@DataSource("master")
	public void insertZombieWorld(ZombieWorld dto);
	
	/**
	 * 更新马甲织图，可更新马甲织图的描述，所在频道
	 * @param dto	马甲织图数据传输对象
	 * @author zhangbo	2015年7月28日
	 */
	@DataSource("master")
	public void updateZombieWorld(ZombieWorld dto);
	
	/**
	 * TODO 可以合并到updateZombieWorld方法中
	 * @param dto
	 * @author zhangbo	2015年8月24日
	 */
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
	
	/**
	 * TODO 可以合并到updateZombieWorld方法中
	 * @param dto
	 * @author zhangbo	2015年8月24日
	 */
	@DataSource("master")
	public void updateZombieWorldLabel(ZombieWorld dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DataSource("master")
	public void batchDeleteZombieWorld(Integer[] ids);
}

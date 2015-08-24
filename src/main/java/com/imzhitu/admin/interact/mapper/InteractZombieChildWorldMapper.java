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
	
	/**
	 * 更新织图子图，目前只能更新子图描述
	 * 
	 * @param dto	马甲织图子图数据传输对象
	 * @author zhangbo	2015年8月24日
	 */
	@DataSource("master")
	public void updateZombieChildWorld(ZombieChildWorld dto);
}

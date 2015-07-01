package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpZombieDegree;

public interface OpZombieDegreeMapper {
	/**
	 * 增加
	 * @param dto
	 */
	@DataSource("master")
	public void insertZombieDegree(OpZombieDegree dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DataSource("master")
	public void batchDeleteZombieDegree(Integer[] ids);
	
	/**
	 * 修改
	 * @param dto
	 */
	@DataSource("master")
	public void updateZombieDegree(OpZombieDegree dto);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpZombieDegree> queryZombieDegree(OpZombieDegree dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryZombieDegreeTotalCount(OpZombieDegree dto);
	
	/**
	 * 查询所有的记录
	 * @return
	 */
	@DataSource("slave")
	public List<OpZombieDegree> queryAllZombieDegree();
}

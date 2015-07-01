package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpZombieDegreeUserLevel;

public interface OpZombieDegreeUserLevelMapper {
	/**
	 * 增加
	 * @param dto
	 */
	@DataSource("master")
	public void insertZombieDegreeUserLevel(OpZombieDegreeUserLevel dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DataSource("master")
	public void batchDeleteZombieDegreeUserLevel(Integer[] ids);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpZombieDegreeUserLevel> queryZombieDegreeUserLevel(OpZombieDegreeUserLevel dto);
	
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryZombieDegreeUserLevelTotalCount(OpZombieDegreeUserLevel dto);
}

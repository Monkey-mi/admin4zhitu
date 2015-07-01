package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpZombie;

public interface OpZombieMapper {
	/**
	 * 增加
	 * @param dto
	 */
	@DataSource("master")
	public void insertZombie(OpZombie dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DataSource("master")
	public void batchDeleteZombie(Integer[] ids);
	
	/**
	 * 批量修改
	 * @param lastModify
	 * @param concernCount
	 * @param commentCount
	 * @param ids
	 */
	@DataSource("master")
	public void batchUpdateZombie(@Param("lastModify")Long lastModify,@Param("concernCount")Integer concernCount,
							@Param("commentCount")Integer commentCount,@Param("ids")Integer[] ids);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpZombie> queryZombie(OpZombie dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryZombieTotalCount(OpZombie dto);
}

package com.imzhitu.admin.interact.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.ZombieWorldSchedulaDto;

public interface InteractZombieWorldSchedulaMapper {
	
	/**
	 * 增加
	 * @param dto
	 */
	@DataSource("master")
	public void insertZombieWorldSchedula(ZombieWorldSchedulaDto dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DataSource("master")
	public void batchDeleteZombieWorldSchedula(Integer[] ids);
	
	/**
	 * 更新
	 * @param dto
	 */
	@DataSource("master")
	public void updateZombieWorldSchedula(ZombieWorldSchedulaDto dto);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<ZombieWorldSchedulaDto> queryZombieWorldSchedula(ZombieWorldSchedulaDto dto);
	
	
	 
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryZombieWorldSchedulaTotalCount(ZombieWorldSchedulaDto dto);
	
	/**
	 * 根据计划时间查询
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@DataSource("slave")
	public List<ZombieWorldSchedulaDto> queryZombieWorldSchedulaByTime(@Param("beginDate") Date beginDate,
			@Param("endDate") Date endDate,@Param("valid")Integer valid,@Param("finished")Integer finished);

}

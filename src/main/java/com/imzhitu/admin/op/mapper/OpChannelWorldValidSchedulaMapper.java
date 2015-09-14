package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpChannelWorldSchedulaDto;

public interface OpChannelWorldValidSchedulaMapper {
	/**
	 * 增加
	 * @param dto
	 */
	@DataSource("master")
	public void insertChannelWorldValidSchedula(OpChannelWorldSchedulaDto dto);
	
	/**
	 * 删除频道有效性计划
	 * @param ids
	 */
	@DataSource("master")
	public void delChannelWorldValidSchedula(Integer[] ids);
	
	/**
	 * 更新频道有效性计划
	 * @param dto
	 */
	@DataSource("master")
	public void updateChannelWorldValidSchedula(OpChannelWorldSchedulaDto dto);
	
	/**
	 * 分页查询频道织图计划
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpChannelWorldSchedulaDto> queryChannelWorldValidSchedulaForList(OpChannelWorldSchedulaDto dto);
	
	/**
	 * 分页查询有效性总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryChannelWorldValidSchedulaCount(OpChannelWorldSchedulaDto dto);
	
}

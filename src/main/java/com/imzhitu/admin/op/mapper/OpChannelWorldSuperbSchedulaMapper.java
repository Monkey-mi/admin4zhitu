package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpChannelWorldSchedulaDto;

public interface OpChannelWorldSuperbSchedulaMapper {
	/**
	 * 增加
	 * @param dto
	 */
	@DataSource("master")
	public void insertChannelWorldSuperbSchedula(OpChannelWorldSchedulaDto dto);
	
	/**
	 * 删除频道精选计划
	 * mishengliang
	 * @param ids
	 */
	@DataSource("master")
	public void delChannelWorldSuperbSchedula(Integer[] ids);
	
	/**
	 * 更新频道精选计划
	 * @param dto
	 */
	@DataSource("master")
	public void updateChannelWorldSuperbSchedula(OpChannelWorldSchedulaDto dto);
	
	/**
	 * mishengliang 
	 * 2015-09-12
	 * 分页查询频道精选计划
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpChannelWorldSchedulaDto> queryChannelWorldSuperbSchedulaForList(OpChannelWorldSchedulaDto dto);
	
	/**
	 * 分页查询精选总数
	 * mishengliang
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryChannelWorldSuperbSchedulaCount(OpChannelWorldSchedulaDto dto);
	
}

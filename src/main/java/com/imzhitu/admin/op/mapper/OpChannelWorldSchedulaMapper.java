package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpChannelWorldSchedulaDto;

public interface OpChannelWorldSchedulaMapper {
	/**
	 * 增加
	 * @param dto
	 */
	@DataSource("master")
	public void insertChannelWorldSchedula(OpChannelWorldSchedulaDto dto);
	
	/**
	 * 删除
	 * @param ids
	 */
	@DataSource("master")
	public void delChannelWorldSchedula(Integer[] ids);
	
	/**
	 * 更新
	 * @param dto
	 */
	@DataSource("master")
	public void updateChannelWorldSchedula(OpChannelWorldSchedulaDto dto);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpChannelWorldSchedulaDto> queryChannelWorldSchedulaForList(OpChannelWorldSchedulaDto dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryChannelWorldSchedulaCount(OpChannelWorldSchedulaDto dto);
	
	/**
	 * 查询
	 * @param dto
	 * @param dto.addDate 和 modifyDate 是用来先限定 schedulaDate 的
	 * @return
	 */
	@DataSource("slave")
	public List<OpChannelWorldSchedulaDto>queryChannelWorldSchedula(OpChannelWorldSchedulaDto dto);
	
}

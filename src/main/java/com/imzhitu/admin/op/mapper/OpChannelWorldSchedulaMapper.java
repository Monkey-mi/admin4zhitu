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
	 * 删除频道有效性计划
	 * @param ids
	 */
	@DataSource("master")
	public void delChannelWorldSchedula(Integer[] ids);
	
	
	/**
	 * 删除频道精选计划
	 * mishengliang
	 * @param ids
	 */
	@DataSource("master")
	public void delChannelWorldSuperbSchedula(Integer[] ids);
	
	/**
	 * 更新
	 * @param dto
	 */
	@DataSource("master")
	public void updateChannelWorldSchedula(OpChannelWorldSchedulaDto dto);
	
	
	/**
	 * 更新
	 * @param dto
	 */
	@DataSource("master")
	public void updateChannelWorldSuperbSchedula(OpChannelWorldSchedulaDto dto);
	
	/**
	 * 分页查询频道织图计划
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpChannelWorldSchedulaDto> queryChannelWorldSchedulaForList(OpChannelWorldSchedulaDto dto);
	
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
	 * 分页查询有效性总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryChannelWorldSchedulaCount(OpChannelWorldSchedulaDto dto);
	
	/**
	 * 分页查询精选总数
	 * mishengliang
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryChannelWorldSuperbSchedulaCount(OpChannelWorldSchedulaDto dto);
	
	/**
	 * 查询
	 * @param dto
	 * @param dto.addDate 和 modifyDate 是用来先限定 schedulaDate 的
	 * @return
	 */
	@DataSource("slave")
	public List<OpChannelWorldSchedulaDto>queryChannelWorldSchedula(OpChannelWorldSchedulaDto dto);
	
}

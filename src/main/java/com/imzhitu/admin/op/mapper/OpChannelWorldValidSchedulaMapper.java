package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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

	/**
	 * 根据频道id与织图id查询频道织图有效性计划集合（理论上只能查出一个）
	 * 
	 * @param channelId	频道id
	 * @param worldId	织图id
	 * @return
	 * @author zhangbo	2015年11月4日
	 */
	public List<OpChannelWorldSchedulaDto> queryChannelWorldValidSchedulaListByChannelIdAndWorldId(@Param("channelId")Integer channelId, @Param("worldId")Integer worldId);
	
}

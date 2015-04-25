package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpStarRecommendSchedulaDto;

public interface OpStarRecommendSchedulaMapper {
	/**
	 * 增加
	 * @param dto
	 */
	@DataSource("master")
	public void insertStarRecommendSchedula(OpStarRecommendSchedulaDto dto);
	
	/**
	 * 删除
	 * @param dto
	 */
	@DataSource("master")
	public void deleteStarRecommendSchedula(OpStarRecommendSchedulaDto dto);
	
	/**
	 * 修改
	 * @param dto
	 */
	@DataSource("master")
	public void updateStarRecommendSchedula(OpStarRecommendSchedulaDto dto);
	
	/**
	 * 分页查询
	 * @param dto
	 */
	@DataSource("slave")
	public List<OpStarRecommendSchedulaDto> queryStarRecommendSchedula(OpStarRecommendSchedulaDto dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 */
	@DataSource("slave")
	public long queryStarRecommendSchedulaTotalCount(OpStarRecommendSchedulaDto dto);
	
	/**
	 * 完成有效性的更新
	 * @param dto
	 */
	@DataSource("master")
	public void updateStarRecommendSchedulaFinish(OpStarRecommendSchedulaDto dto);
}

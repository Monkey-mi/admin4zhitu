package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpStarRecommendDto;

public interface OpStarRecommendMapper {
	/**
	 * 增加
	 */
	@DataSource("master")
	public void insertStarRecommend(OpStarRecommendDto dto);
	
	/**
	 * 删除
	 */
	@DataSource("master")
	public void deleteStarRecommend(OpStarRecommendDto dto);
	
	/**
	 * 修改
	 */
	@DataSource("master")
	public void updateStarRecommend(OpStarRecommendDto dto);
	
	/**
	 * 分页查询
	 */
	@DataSource("slave")
	public List<OpStarRecommendDto> queryStarRecommend(OpStarRecommendDto dto);
	
	/**
	 * 分页查询总数
	 */
	@DataSource("slave")
	public long queryStarRecommendTotalCount(OpStarRecommendDto dto);
	
	/**
	 * 查询
	 */
	@DataSource("slave")
	public List<OpStarRecommendDto> selectStarRecommend(OpStarRecommendDto dto);
}

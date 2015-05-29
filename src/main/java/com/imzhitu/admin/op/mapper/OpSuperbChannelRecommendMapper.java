package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpSuperbChannelRecommend;

public interface OpSuperbChannelRecommendMapper {
	/**
	 * insert
	 * @param dto
	 */
	@DataSource("master")
	public void insertSuperbChannelRecommend(OpSuperbChannelRecommend dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DataSource("master")
	public void batchDeleteSuperbChannelRecommend(Integer[] ids);
	
	/**
	 * 批量更新有效性
	 * @param ids
	 */
	@DataSource("master")
	public void batchUpdateSuperbChannelRecommendValid(@Param("valid")Integer valid,@Param("ids")Integer[] ids);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpSuperbChannelRecommend> qeurySuperbChannelRecommend(OpSuperbChannelRecommend dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long querySuperbChannelRecommendCount(OpSuperbChannelRecommend dto);
}

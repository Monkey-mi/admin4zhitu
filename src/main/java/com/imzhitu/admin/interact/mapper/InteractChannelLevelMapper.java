package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.InteractChannelLevel;

public interface InteractChannelLevelMapper {
	/**
	 * 增加
	 * @param dto
	 */
	@DataSource("master")
	public void insertChannelLevel(InteractChannelLevel dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DataSource("master")
	public void batchDeleteChannelLevel(Integer[] ids);
	
	/**
	 * 更新
	 * @param dto
	 */
	@DataSource("master")
	public void updateChannelLevel(InteractChannelLevel dto);
	
	/**
	 * 查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<InteractChannelLevel> queryChannelLevel(InteractChannelLevel dto);
	
	/**
	 * 查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryChannelLevelTotalCount(InteractChannelLevel dto);
}

package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.InteractChannelWorldLabel;

public interface InteractChannelWorldLabelMapper {
	/**
	 * 增加
	 * @param dto
	 */
	@DataSource("master")
	public void insertChannelWorldLabel(InteractChannelWorldLabel dto);
	
	/**
	 * 查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<InteractChannelWorldLabel> queryChannelWorldLabel(InteractChannelWorldLabel dto);
	
	/**
	 * 查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryChannelWorldLabelTotalCount(InteractChannelWorldLabel dto);
}

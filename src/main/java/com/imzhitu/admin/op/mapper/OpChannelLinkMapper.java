package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpChannelLink;

public interface OpChannelLinkMapper {
	/**
	 * 增加
	 * @param dto
	 */
	@DataSource("master")
	public void insertOpChannelLink(OpChannelLink dto);
	
	/**
	 * 删除
	 * @param dto
	 */
	@DataSource("master")
	public void deleteOpChannelLink(OpChannelLink dto);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpChannelLink> queryOpChannelLink(OpChannelLink dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryOpChannelLinkTotalCount(OpChannelLink dto);
}

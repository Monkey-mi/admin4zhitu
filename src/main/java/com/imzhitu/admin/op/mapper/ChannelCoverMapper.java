package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpChannelCover;

public interface ChannelCoverMapper {

	/**
	 * 查询封面缓存
	 * 
	 * @param channelIds
	 * @param limit
	 */
	@DataSource("slave")
	public List<com.hts.web.common.pojo.OpChannelCover> 
		queryCacheCover(@Param("channelIds")Integer[] channelIds, @Param("limit")Integer limit);
	
	
	/**
	 * 保存封面
	 * 
	 * @param cover
	 */
	public void save(OpChannelCover cover);
	
	/**
	 * 更新封面
	 * 
	 * @param cover
	 */
	public void update(OpChannelCover cover);
	
	/**
	 * 删除封面
	 * 
	 * @param cover
	 */
	public void deleteByChannelIdAndWID(OpChannelCover cover);
}

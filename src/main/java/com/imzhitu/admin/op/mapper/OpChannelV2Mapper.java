package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.OpChannelV2Dto;

public interface OpChannelV2Mapper {
	/**
	 * 增加
	 * @param dto
	 */
	public void insertOpChannel(OpChannelV2Dto dto);
	
	/**
	 * 删除
	 * @param dto
	 */
	public void deleteOpChannel(OpChannelV2Dto dto);
	
	/**
	 * 修改
	 * @param dto
	 */
	public void updateOpChannel(OpChannelV2Dto dto);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	public List<OpChannelV2Dto> queryOpChannel(OpChannelV2Dto dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	public long queryOpChannelTotalCount(OpChannelV2Dto dto);
}

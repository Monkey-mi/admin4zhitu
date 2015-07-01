package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.OpZombieChannel;

public interface OpZombieChannelMapper {
	/**
	 * 增加
	 * @param dto
	 */
	public void insertZombieChannel(OpZombieChannel dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void batchDeleteZombieChannel(Integer[] ids);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	public List<OpZombieChannel> queryZombieChannel(OpZombieChannel dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	public long queryZombieChannelTotalCount(OpZombieChannel dto);
}

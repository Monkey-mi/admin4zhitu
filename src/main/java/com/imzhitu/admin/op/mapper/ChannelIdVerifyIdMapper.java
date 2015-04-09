package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpChannelIdVerifyIdDto;

public interface ChannelIdVerifyIdMapper {
	/**
	 * 插入
	 * @param dto
	 */
	public void insertChannelIdVerifyId(OpChannelIdVerifyIdDto dto);
	
	/**
	 * 检查是否存在与verifyId对应的channelId
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long checkIsExistByVerifyId(OpChannelIdVerifyIdDto dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void batchDeleteChannelIdVerifyId(Integer[] ids);
	
	
	/**
	 * 根据verifyId来查询channelId
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public Integer queryChannelIdByVerifyId(OpChannelIdVerifyIdDto dto);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpChannelIdVerifyIdDto> queryChannelIdVerifyIdForList(OpChannelIdVerifyIdDto dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryChannelIdVerifyIdCount(OpChannelIdVerifyIdDto dto);
}

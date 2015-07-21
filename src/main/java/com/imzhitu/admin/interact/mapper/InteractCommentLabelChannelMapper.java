package com.imzhitu.admin.interact.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.InteractCommentLabelChannel;

public interface InteractCommentLabelChannelMapper {
	/**
	 * 添加
	 * @param dto
	 */
	@DataSource("master")
	public void insertCommentLabelChannel(InteractCommentLabelChannel dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DataSource("master")
	public void batchDeleteCommentLabelChannel(Integer[] ids);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<InteractCommentLabelChannel> queryCommentLabelChannel(InteractCommentLabelChannel dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryComemntLabelChannelTotalCount(InteractCommentLabelChannel dto);
	
	/**
	 * 根据频道Id查询标签Id
	 * @param channelId
	 * @return
	 */
	@DataSource("slave")
	public List<InteractCommentLabelChannel> queryCommentLabelIdByChannelId(@Param("channelId") Integer channelId);
}

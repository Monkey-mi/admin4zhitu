package com.imzhitu.admin.channel.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.ChannelWorldInteractComment;

/**
 * 频道织图互动的评论关系存储数据操作类
 * 
 * 注：这个表是频道织图互动的评论记录表，频道织图在保存互动信息时，其中评论id的记录，哪一个频道织图将对应哪一条评论，
 * 此表当中一条数据，用于互动计划时使用，再配上马甲号，形成一条真实的评论回复
 * 
 * @author zhangbo	2015年10月30日
 *
 */
public interface ChannelWorldInteractCommentMapper {

	/**
	 * 保存频道织图互动的评论关系
	 * 
	 * @param channelId	频道id
	 * @param worldId	织图id
	 * @param commentId	评论id
	 * 
	 * @author zhangbo	2015年10月31日
	 */
	@DataSource("master")
	void insert(@Param("channelId")Integer channelId, @Param("worldId")Integer worldId, @Param("commentId")Integer commentId);
	
	/**
	 * 更新频道织图互动的评论关系变为完成
	 * 
	 * @param channelId	频道id
	 * @param worldId	织图id
	 * @param commentId	评论id
	 * 
	 * @author zhangbo	2015年10月31日
	 */
	@DataSource("master")
	void complete(@Param("channelId")Integer channelId, @Param("worldId")Integer worldId, @Param("commentId")Integer commentId);

	/**
	 * 根据频道id与织图id，查询未完成的频道织图互动的评论关系
	 * 
	 * @param channelId	频道id
	 * @param worldId	织图id
	 * @author zhangbo	2015年10月31日
	 */
	@DataSource("slave")
	List<ChannelWorldInteractComment> queryNotCompletedCommentByChannelIdAndWorldId(@Param("channelId")Integer channelId, @Param("worldId")Integer worldId);
	
}

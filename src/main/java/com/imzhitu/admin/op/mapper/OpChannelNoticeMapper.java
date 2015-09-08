package com.imzhitu.admin.op.mapper;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;

/**
 * 频道通知数据接口 作为持久层操作，对数据库进行的一系列操作
 * 
 * @author zhangbo 2015年9月2日
 *
 */
public interface OpChannelNoticeMapper {

	/**
	 * 保存频道通知
	 * 
	 * @param channelId
	 *            频道id
	 * @param noticeType
	 *            通知类型： 
	 *            1、织图被选入频道：world_into_channel 
	 *            2、频道织图被选为精选：channelworld_to_superb 
	 *            3、频道成员被选为红人：channelmember_to_star
	 * @param noticeTmplId
	 * @author zhangbo 2015年9月2日
	 */
	@DataSource("master")
	public void insertChannelNotice(@Param("channelId") Integer channelId, @Param("noticeType") String noticeType, @Param("noticeTmplId") Integer noticeTmplId);

	/**
	 * 更新频道通知，根据频道id和频道通知类型
	 * 
	 * @param channelId
	 *            频道id
	 * @param noticeType
	 *            通知类型： 
	 *            1、织图被选入频道：world_into_channel 
	 *            2、频道织图被选为精选：channelworld_to_superb 
	 *            3、频道成员被选为红人：channelmember_to_star
	 * @param noticeTmplId
	 * @author zhangbo 2015年9月6日
	 */
	public void updateChannelNotice(@Param("channelId") Integer channelId, @Param("noticeType") String noticeType, @Param("noticeTmplId") Integer noticeTmplId);

	/**
	 * 根据频道通知类型获取通知信息模板id
	 * 
	 * @param channelId
	 *            频道id
	 * @param noticeType
	 *            通知类型： 
	 *            1、织图被选入频道：world_into_channel 
	 *            2、频道织图被选为精选：channelworld_to_superb 
	 *            3、频道成员被选为红人：channelmember_to_star
	 * @author zhangbo 2015年9月6日
	 */
	public Integer getNoticeTpmlIdByType(@Param("channelId") Integer channelId, @Param("noticeType") String noticeType);

}

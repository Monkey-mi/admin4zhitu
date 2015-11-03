package com.imzhitu.admin.channel.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.ChannelWorldInteractScheduler;

/**
 * @author zhangbo	2015年10月29日
 *
 */
public interface ChannelWorldInteractSchedulerMapper {
	
	/**
	 * 插入数据到频道织图生效规划互动表中
	 * 
	 * @param channelId		频道id
	 * @param worldId		织图id
	 * @param scheduleDate	计划时间
	 * @param operator		操作者
	 * 
	 * @author zhangbo	2015年10月29日
	 */
	@DataSource("master")
	void insert(@Param("channelId")Integer channelId, @Param("worldId")Integer worldId, @Param("operator")Integer operator);
	
	/**
	 * 更新频道织图生效规划互动表中数据
	 * 
	 * @param channelId	频道id
	 * @param worldId	织图id
	 * @param complete	完成标志，完成：1，未完成：0
	 * 
	 * @author zhangbo	2015年10月29日
	 */
	@DataSource("master")
	void update(@Param("channelId")Integer channelId, @Param("worldId")Integer worldId, @Param("complete")Integer complete);
	
	/**
	 * 根据频道id与织图id，查询频道织图生效规划互动表未完成的数据
	 * 
	 * @param channelId	频道id
	 * @param worldId	织图id
	 * @return	频道织图规划的互动集合
	 * @author zhangbo	2015年11月3日
	 */
	@DataSource("slave")
	List<ChannelWorldInteractScheduler> queryChannelWorldInteractSchedulerNotCompleteList(@Param("channelId")Integer channelId, @Param("worldId")Integer worldId);
	
	/**
	 * 根据时间段查询频道织图生效规划互动表未完成的数据
	 * 
	 * @param beginTime	开始时间
	 * @param endTime	结束时间
	 * @return	频道织图规划的互动集合
	 * @author zhangbo	2015年10月29日
	 */
	@DataSource("slave")
	List<ChannelWorldInteractScheduler> queryChannelWorldInteractSchedulerListByTime(@Param("beginTime")Date beginTime, @Param("endTime")Date endTime);
	
}

/**
 * 
 */
package com.imzhitu.admin.statistics.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpDataStatisticsDto;

/**
 * 
 *
 * @author zhangbo 2015年6月23日
 */
public interface DailyDataStatisticsMapper {
	
	/**
	 * 插入每日频道数据统计
	 * 
	 * @param dto	数据统计对象
	 *
	 * @author zhangbo 2015年6月23日
	 */
	@DataSource("master")
	public void insertData(OpDataStatisticsDto dto);
	
	/**
	 * 查询每日频道新增织图数量，去除马甲号的结果
	 *
	 * @param startTime	查询开始时间	例：2015-01-01 00:00:00
	 * @param endTime	查询结束时间	例：2015-01-02 00:00:00
	 * @param	以上为查询了2015-01-01，当天增加的频道中织图数
	 * @return
	 * @author zhangbo 2015年6月19日
	 */
	@DataSource("slave")
	public List<OpDataStatisticsDto> queryDailyAddedChannelWorldCount(@Param("startTime")Date startTime, @Param("endTime")Date endTime);
	
	/**
	 * 查询每日频道新增订阅数量
	 *
	 * @param startTime	查询开始时间	例：2015-01-01 00:00:00
	 * @param endTime	查询结束时间	例：2015-01-02 00:00:00
	 * @param	以上为查询了2015-01-01，当天增加的频道的订阅数
	 * @return
	 * @author zhangbo 2015年6月19日
	 */
	@DataSource("slave")
	public List<OpDataStatisticsDto> queryDailyAddedChannelMemberCount(@Param("startTime")long startTime, @Param("endTime")long endTime);
	
	/**
	 * 查询每日频道新增织图的评论数量，去除马甲号的结果
	 *
	 * @param startTime	查询开始时间	例：2015-01-01 00:00:00
	 * @param endTime	查询结束时间	例：2015-01-02 00:00:00
	 * @param	以上为查询了2015-01-01，当天增加的频道中织图的评论数量
	 * @return
	 * @author zhangbo 2015年6月23日
	 */
	@DataSource("slave")
	public List<OpDataStatisticsDto> queryDailyAddedChannelWorldCommentCount(@Param("startTime")Date startTime, @Param("endTime")Date endTime);
	
	/**
	 * 查询每日频道新增织图的点赞数量，去除马甲号的结果
	 *
	 * @param startTime	查询开始时间	例：2015-01-01 00:00:00
	 * @param endTime	查询结束时间	例：2015-01-02 00:00:00
	 * @param	以上为查询了2015-01-01，当天增加的频道中织图的点赞数量
	 * @return
	 * @author zhangbo 2015年6月23日
	 */
	@DataSource("slave")
	public List<OpDataStatisticsDto> queryDailyAddedChannelWorldLikedCount(@Param("startTime")Date startTime, @Param("endTime")Date endTime);

	/**
	 * 分页查询每日数据统计结果集
	 * 
	 * @param dto	数据统计DTO类
	 * @return
	 * @author zhangbo 2015年6月25日
	 */
	@DataSource("slave")
	public List<OpDataStatisticsDto> queryDailyData(OpDataStatisticsDto dto);
	
	/**
	 * 每日数据统计总数
	 * 
	 * @param dto	数据统计DTO类
	 * @return
	 * @author zhangbo 2015年6月26日
	 */
	@DataSource("slave")
	public long queryDailyDataTotalCount(OpDataStatisticsDto dto);

}

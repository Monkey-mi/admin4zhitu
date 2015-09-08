package com.imzhitu.admin.op.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpChannelMemberDTO;

/**
 * 频道成员相关数据接口
 * 作为持久层操作，对数据库进行的一系列操作
 * 
 * TODO 对红人表的操作，单拿出来一个Mapper去操作，一个Mapper只操作一张表
 * 
 * @author zhangbo	2015年8月18日
 *
 */
public interface OpChannelMemberMapper {

	/**
	 * 新增频道成员 TODO 这个方法要废弃掉，直接用web端的代码进行插入控制，保证方法入口统一
	 * 
	 * @param dto
	 */
	@DataSource("master")
	public void insertChannelMember(OpChannelMemberDTO dto);

	/**
	 * 修改频道成员
	 * 
	 * 可修改字段：
	 * 		频道成员等级：degree	频道主：1，管理员：2，普通成员：0
	 * 		频道成员是否被屏蔽：shield	屏蔽：1，未屏蔽：0 
	 * 		频道成员是否为频道红人：channelStar	红人：1，非红人：0
	 * 可使用条件：
	 * 		频道成员表主键id：channelMemberId
	 * 		频道id与用户id：channelId与userId联合确定一条
	 * 		（三个参数允许同时传递，但使用其中一种条件即可）
	 * 
	 * @param memberDto
	 * @author zhangbo 2015年8月17日
	 */
	@DataSource("master")
	public void updateChannelMember(OpChannelMemberDTO dto);

	/**
	 * 查询总数
	 * 
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryChannelMemberTotalCount(OpChannelMemberDTO dto);

	/**
	 * 分页查询，根据dto中传递过来的信息进行各种查询 用于前台页面展示， 查询结果携带了成员相关联的user信息
	 * 
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpChannelMemberDTO> queryChannelMember(OpChannelMemberDTO dto);

	/**
	 * 查询频道成员当前最大id
	 * 
	 * @return
	 * @author zhangbo 2015年8月14日
	 */
	@DataSource("slave")
	public long getChannelMemberMaxId();

	/**
	 * 查询频道成员对象（不携带成员相关联的user信息），返回一个结果 
	 * 
	 * @param dto
	 *            频道成员数据传输对象 
	 * 可使用条件：
	 * 		频道成员表主键id：channelMemberId
	 * 		频道id与用户id：channelId与userId联合确定一条
	 * 		（三个参数允许同时传递，但使用其中一种条件即可）
	 * 
	 * @return OpChannelMemberDto 频道成员数据传递对象（成员user相关信息为空）
	 * @author zhangbo 2015年8月17日
	 */
	@DataSource("slave")
	public OpChannelMemberDTO getChannelMember(OpChannelMemberDTO dto);

	/**
	 * 新增频道红人， 频道红人来源与频道成员 频道成员表主键id与频道红人表主键id保持一致
	 * 
	 * @param dto
	 * @author zhangbo 2015年8月17日
	 */
	@DataSource("master")
	public void insertChannelStar(OpChannelMemberDTO dto);

	/**
	 * 查询频道红人对象（不携带成员相关联的user信息），返回一个结果
	 * 
	 * @param dto
	 *            频道成员数据传输对象（也是频道红人的数据传输对象）
	 * 可使用条件：
	 * 		频道红人表主键id：channelMemberId
	 * 		频道id与用户id：channelId与userId联合确定一条
	 * 		（三个参数允许同时传递，但使用其中一种条件即可）
	 * 
	 * @return OpChannelMemberDto 频道成员数据传递对象（红人也属于频道成员，成员user相关信息为空）
	 * @author zhangbo 2015年8月17日
	 */
	@DataSource("slave")
	public OpChannelMemberDTO getChannelStar(OpChannelMemberDTO dto);
	
	/**
	 * 更新频道红人相关信息
	 * 频道红人id，channel_id，user_id保持不变 其他属性发生变化
	 * 此方法根据频道红人id，操作唯一一条数据，不通过channel_id与user_id联合确定唯一一条数据 
	 * 频道成员表主键id与频道红人表主键id保持一致
	 * 
	 * @param dto
	 * @author zhangbo 2015年8月17日
	 */
	@DataSource("master")
	public void updateChannelStar(OpChannelMemberDTO dto);

	/**
	 * 批量删除频道红人，根据频道红人id集合
	 * 
	 * @param ids
	 *            将要被删除的频道红人主键id集合
	 * @author zhangbo 2015年8月17日
	 */
	@DataSource("master")
	public void deleteChannelStarByIds(Integer[] ids);
	
	/**
	 * 新增频道红人排序调度
	 * 
	 * @param dto			频道红人DTO，其中携带了频道红人主键id，频道id，用户id
	 * @param scheduleDate	设置的排序生效时间
	 * @param operatorId	操作者id，即管理员账号id
	 * @author zhangbo	2015年8月19日
	 */
	@DataSource("master")
	public void insertChannelStarSchedule(@Param("channelMemberDTO")OpChannelMemberDTO dto, @Param("scheduleDate")Date scheduleDate, @Param("operatorId")Integer operatorId);
	
	/**
	 * 更新频道红人排序调度，主要是更新finish状态
	 * 
	 * @param dto		频道红人DTO，其中携带了频道红人主键id，频道id，用户id
	 * @param finish	是否完成，完成：1，未完成：0
	 * @author zhangbo	2015年8月20日
	 */
	@DataSource("master")
	public void updateChannelStarSchedule(@Param("channelMemberDTO")OpChannelMemberDTO dto, @Param("finish")Integer finish);

	/**
	 * 查询频道红人排序计划，返回时间间隔中的数据，并且同时根据完成状态进行查询
	 * 返回结果根据计划排序时间正序获取结果
	 * 
	 * @param startTime	起始时间
	 * @param endTime	结束时间
	 * @param finish	是否完成，完成：1，未完成：0
	 * 
	 * @return list	频道红人对象集合，dto中主要内容只包括频道红人主键id，频道id，用户id三个属性 
	 * @author zhangbo	2015年8月20日
	 */
	@DataSource("slave")
	public List<OpChannelMemberDTO> queryChannelStarSchedule(@Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("finish")Integer finish);

}

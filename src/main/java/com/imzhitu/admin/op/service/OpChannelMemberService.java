package com.imzhitu.admin.op.service;

import java.util.Date;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.OpChannelMemberDTO;

/**
 * 频道成员相关操作的业务接口
 * 包括对频道成员的CRUD，与频道红人的CRUD及消息推送等操作
 * 
 * @author zhangbo	2015年8月18日
 *
 */
public interface OpChannelMemberService extends BaseService {
	
	/**
	 * 修改频道成员等级
	 * 成员等级分为：群主：1，管理员：2，普通成员：0
	 * 
	 * 这个接口目前只被频道更换群主时调用
	 * 但是以后可能开放出来给运营，让运营直接操作成员的等级
	 * 
	 * @param dto
	 */
	public void updateChannelMemberDegree(Integer channelId, Integer userId, Integer degree) throws Exception;
	
	/**
	 * 分页查询频道成员
	 * 
	 * @param channelId
	 * @param userId
	 * @param userName
	 * @param userStarId
	 * @param notified
	 * @param shield
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 * @author zhangbo	2015年8月18日
	 */
	public void buildChannelMemberList(Integer channelId, Integer userId, String userName, Integer userStarId, Integer notified, Integer shield, Integer maxId, int page, int rows, Map<String, Object> jsonMap) throws Exception;
	
	public OpChannelMemberDTO getChannelStarByChannelIdAndUserId(Integer channelId, Integer userId);

	/**
	 * 保存频道红人， 频道红人来源与频道成员
	 * 频道成员表主键id与频道红人表主键id保持一致
	 * 此方法执行从频道成员表冗余到频道红人中
	 * 
	 * @param channelMemberId	频道成员表主键id
	 * @author zhangbo	2015年8月17日
	 */
	public void saveChannelStar(Integer channelMemberId) throws Exception;

	/**
	 * 批量删除频道红人 
	 * 
	 * @param channelMemberIds
	 * @author zhangbo	2015年8月17日
	 */
	public void deleteChannelStars(Integer[] channelMemberIds) throws Exception;

	/**
	 * 推送消息给频道红人，通知其成为频道红人
	 * TODO 批量通知要废弃掉，十一以后要废掉，现在通知都涵盖在了成为红人后，就直接发通知出去了
	 * 
	 * @param channelStarId	频道红人表主键id
	 * @author zhangbo	2015年8月18日
	 */
	public void addStarRecommendMsg(Integer channelStarIds) throws Exception;

	/**
	 * 更新频道红人的通知状态
	 * 
	 * @param channelStarId	频道红人表主键id
	 * @param notified	通知状态	1：已通知	0：未通知
	 * @author zhangbo	2015年9月6日
	 */
	void updateChannelStarNotified(Integer channelStarId, Integer notified);
	
	/**
	 * 对频道红人进行重新排序
	 * 
	 * @param csIds			频道红人表主键id集合
	 * @param scheduleDate	计划进行排序的时间
	 * @author zhangbo	2015年8月19日
	 */
	public void sortChannelStarsSchedule(Integer[] csIds, Date scheduleDate);
	
	/**
	 * 设置频道红人在排序的最新一位
	 * 
	 * @param channelStarId	频道红人表主键id
	 * @author zhangbo	2015年8月18日
	 */
	public void setChannelStarTop(Integer channelStarId);

}

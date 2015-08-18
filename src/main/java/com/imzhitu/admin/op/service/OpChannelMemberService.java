package com.imzhitu.admin.op.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

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
	 * @param channelId	频道id
	 * @param userId	用户id
	 * @param degree
	 * @param userStarId
	 * @param sheild
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryChannelMember(Integer channelId, Integer userId, Integer userStarId, Integer notified, Integer shield, Integer maxId, int page, int rows, Map<String, Object> jsonMap) throws Exception;

	/**
	 * 通过用户id查找频道成员
	 * 
	 * @param channelId	频道id
	 * @param userId	用户id
	 * @param page		page对应分页查询start
     * @param rows		对应分页查询limit
     * @param jsonMap	返回前台结果集
     * 
	 * @author zhangbo 2015年8月14日
	 */
	public void queryChannelMemberByUserId(Integer channelId, Integer userId, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 通过用户名查找频道成员
	 * 
	 * @param channelId	频道id
	 * @param userName	用户名称 
	 * @param page		page对应分页查询start
     * @param rows		对应分页查询limit
     * @param jsonMap	返回前台结果集
     * 
	 * @author zhangbo 2015年8月14日
	 */
	public void queryChannelMemberByUserName(Integer channelId, String userName, Integer page, Integer rows, Map<String, Object> jsonMap) throws Exception;

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
	 * 
	 * @param channelStarId	频道红人表主键id
	 * @author zhangbo	2015年8月18日
	 */
	public void addStarRecommendMsg(Integer channelStarIds) throws Exception;

	/**
	 * 对频道红人进行重新排序
	 * 
	 * @param channelStarIds	频道红人表主键id集合
	 * @author zhangbo	2015年8月18日
	 */
	public void serialChannelStars(Integer[] channelStarIds);

}

package com.imzhitu.admin.op.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

public interface OpChannelMemberService extends BaseService {
	
	/**
	 * 增加
	 * 
	 * @param dto
	 */
	public void insertChannelMember(Integer channelId, Integer userId, Integer degree) throws Exception;

	/**
	 * 修改频道成员等级
	 * 成员等级分为：群主--0，管理员--1，普通成员--2
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
	public void queryChannelMember(Integer channelId, Integer userId, Integer userStarId, Integer shield, Integer maxId, int page, int rows, Map<String, Object> jsonMap) throws Exception;

	/**
	 * 通过用户名查找频道成员
	 * 
	 * @param channelId	频道id
	 * @param userId	用户id
	 * @param page		
	 * @param rows
	 * @param jsonMap
	 * @author zhangbo 2015年8月14日
	 */
	public void queryChannelMemberByUserName(Integer channelId, Integer userId, Integer page, Integer rows, Map<String, Object> jsonMap) throws Exception;

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

}

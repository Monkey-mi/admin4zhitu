package com.imzhitu.admin.op.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

public interface OpChannelMemberService  extends BaseService{
	/**
	 * 增加
	 * @param dto
	 */
	public void insertChannelMember(Integer channelId,Integer userId,Integer degree)throws Exception;
	

	/**
	 * 修改等级
	 * @param dto
	 */
	public void updateChannelMemberDegree(Integer id,Integer channelId,Integer userId,Integer degree)throws Exception;
	
	/**
	 * 查询总数
	 * @param dto
	 * @return
	 */
	public long queryChannelMemberTotalCount(Integer id,Integer channelId,Integer userId,Integer degree)throws Exception;
	
	/**
	 * 分页查询
	 * @param id
	 * @param channelId
	 * @param userId
	 * @param degree
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryChannelMember(Integer id,Integer channelId,Integer userId,Integer degree,Integer maxId,int page,int rows ,Map<String,Object>jsonMap)throws Exception;
}

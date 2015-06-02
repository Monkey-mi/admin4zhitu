package com.imzhitu.admin.op.service;

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
}

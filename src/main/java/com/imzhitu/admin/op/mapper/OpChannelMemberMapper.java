package com.imzhitu.admin.op.mapper;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpChannelMemberDto;

public interface OpChannelMemberMapper {
	
	/**
	 * 增加
	 * @param dto
	 */
	@DataSource("master")
	public void insertChannelMember(OpChannelMemberDto dto);
	
	/**
	 * 修改等级
	 * @param dto
	 */
	@DataSource("master")
	public void updateChannelMemberDegree(OpChannelMemberDto dto);
	
	/**
	 * 查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryChannelMemberTotalCount(OpChannelMemberDto dto);

}
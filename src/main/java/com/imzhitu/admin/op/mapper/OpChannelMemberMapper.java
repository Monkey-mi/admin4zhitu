package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpChannelMemberDto;

public interface OpChannelMemberMapper {
	
	/**
	 * 新增频道成员
	 * TODO 这个方法要废弃掉，直接用web端的代码进行插入控制，保证方法入口统一
	 * @param dto
	 */
	@DataSource("master")
	public void insertChannelMember(OpChannelMemberDto dto);
	
	/**
	 * 修改频道成员的等级
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
	
	/**
	 * 分页查询，根据dto中传递过来的信息进行各种查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpChannelMemberDto> queryChannelMember(OpChannelMemberDto dto);

	/**
	 * 查询频道成员当前最大id
	 * @return
	 * @author zhangbo	2015年8月14日
	 */
	public long getChannelMemberMaxId();

}

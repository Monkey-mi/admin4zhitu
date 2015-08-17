package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpChannelMemberDto;

public interface OpChannelMemberMapper {

	/**
	 * 新增频道成员 TODO 这个方法要废弃掉，直接用web端的代码进行插入控制，保证方法入口统一
	 * 
	 * @param dto
	 */
	@DataSource("master")
	public void insertChannelMember(OpChannelMemberDto dto);

	/**
	 * 修改频道成员
	 * 
	 * @param memberDto
	 * @author zhangbo 2015年8月17日
	 */
	@DataSource("master")
	public void updateChannelMember(OpChannelMemberDto dto);

	/**
	 * 修改频道成员的等级
	 * TODO 这个方法可以改掉， 废弃掉，让调用的地方直接进行更新频道成员的动作
	 * @param dto
	 */
	@DataSource("master")
	public void updateChannelMemberDegree(OpChannelMemberDto dto);

	/**
	 * 查询总数
	 * 
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryChannelMemberTotalCount(OpChannelMemberDto dto);

	/**
	 * 分页查询，根据dto中传递过来的信息进行各种查询 用于前台页面展示， 查询结果携带了成员相关联的user信息
	 * 
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpChannelMemberDto> queryChannelMember(OpChannelMemberDto dto);

	/**
	 * 查询频道成员当前最大id
	 * 
	 * @return
	 * @author zhangbo 2015年8月14日
	 */
	@DataSource("slave")
	public long getChannelMemberMaxId();

	/**
	 * 根据频道成员主键id，查询频道成员对象（不携带成员相关联的user信息）
	 * 
	 * @param id
	 *            频道成员主键id
	 * @return OpChannelMemberDto 频道成员数据传递对象（成员user相关信息为空）
	 * @author zhangbo 2015年8月17日
	 */
	@DataSource("slave")
	public OpChannelMemberDto queryChannelMemberById(Integer id);

	/**
	 * 新增频道红人， 频道红人来源与频道成员 频道成员表主键id与频道红人表主键id保持一致
	 * 
	 * @param dto
	 * @author zhangbo 2015年8月17日
	 */
	@DataSource("master")
	public void insertChannelStar(OpChannelMemberDto dto);

	/**
	 * 更新频道红人相关信息， 频道红人id，channel_id，user_id保持不变 其他属性发生变化，此方法根据频道红人id，操作唯一一条数据，不通过channel_id与user_id联合确定唯一一条数据 频道成员表主键id与频道红人表主键id保持一致
	 * 
	 * @param dto
	 * @author zhangbo 2015年8月17日
	 */
	@DataSource("master")
	public void updateChannelStar(OpChannelMemberDto dto);

	/**
	 * 批量删除频道红人，根据频道红人id集合
	 * 
	 * @param ids
	 *            将要被删除的频道红人主键id集合
	 * @author zhangbo 2015年8月17日
	 */
	@DataSource("master")
	public void deleteChannelStarByIds(Integer[] ids);

}

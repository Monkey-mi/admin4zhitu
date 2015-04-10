package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpChannelUserDto;

public interface ChannelUserMapper {
	/**
	 * 查询前n个频道用户，计算公式：平均几天上精选*3+平均几天上频道*3-注册多少个周/2+最近发图时间*2。
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<Integer> queryChannelUserRankTopN(OpChannelUserDto dto);
	
	/**
	 * 插入
	 * @param dto
	 */
	@DataSource("master")
	public void insertChannelUser(OpChannelUserDto dto);
	
	/**
	 * 删除
	 * @param dto
	 */
	@DataSource("master")
	public void deleteChannelUser(OpChannelUserDto dto);
	
	/**
	 * 批量删除
	 */
	@DataSource("master")
	public  void deleteChannelUserByIds(Integer[] ids);
	
	/**
	 * 更新Valid
	 * @param dto
	 */
	@DataSource("master")
	public void updateChannelUserValid(OpChannelUserDto dto);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpChannelUserDto> queryChannelUserForList(OpChannelUserDto dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryChannelUserCount(OpChannelUserDto dto);
	
	/**
	 * 根据织图id查询用户id
	 * @param worldId
	 * @return
	 */
	@DataSource("slave")
	public Integer queryUserIdByWorldId(Integer worldId);
	
	/**
	 *  查询用户平均几天上精选*3 对应的分数 
	 * @param userId
	 * @return
	 */
	@DataSource("slave")
	public Integer querySuperbScore(Integer userId);
	
	/**
	 * 平均几天上频道*3 对应的分数
	 * @param userId
	 * @return
	 */
	@DataSource("slave")
	public Integer queryChannelScore(Integer userId);
	
	/**
	 * 注册多少个周/2
	 * @param userId
	 * @return
	 */
	@DataSource("slave")
	public Integer queryRegisterScore(Integer userId);
	
	/**
	 * 最近发图时间*2
	 * @param userId
	 * @return
	 */
	@DataSource("slave")
	public Integer queryLastWorldScore(Integer userId);
}

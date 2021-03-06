package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpZombieChannel;

public interface OpZombieChannelMapper {
	/**
	 * 增加
	 * @param dto
	 */
	@DataSource("master")
	public void insertZombieChannel(OpZombieChannel dto);
	
	/**
	 * 批量增加
	 * @param list
	 */
	public void batchInsertZombieChannel(List<OpZombieChannel> list);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DataSource("master")
	public void batchDeleteZombieChannel(Integer[] ids);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpZombieChannel> queryZombieChannel(OpZombieChannel dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryZombieChannelTotalCount(OpZombieChannel dto);
	
	/**
	 * 随机查询n个没有互动的粉丝马甲
	 * 
	 * @param concernId	被关注的人的id,即非马甲
	 * @param channelId	频道id
	 * @param worldId	织图主键id
	 * @param limit		查询数量的上限
	 * @return
	 * @author zhangbo	2015年9月28日
	 */
	@DataSource("master")
	public List<Integer> queryNotInteractNRandomFollowZombie(@Param("concernId")Integer concernId,@Param("channelId")Integer channelId,@Param("worldId")Integer worldId,@Param("limit")Integer limit);
	
	/**
	 * 查询n个没有互动的频道粉丝马甲总数
	 * 
	 * @param concernId	被关注的人的id,即非马甲
	 * @param channelId	频道id
	 * @param worldId	织图主键id
	 * @param limit		查询数量的上限
	 * @return
	 * @author zhangbo	2015年9月28日
	 */
	@DataSource("slave")
	public Integer queryNotInteractNRandomFollowZombieCount(@Param("concernId")Integer concernId,@Param("channelId")Integer channelId,@Param("worldId")Integer worldId);
	
	/**
	 * 随机查询n个没有互动的非粉丝马甲
	 * @param concernId	被关注的人的id,即非马甲
	 * @param channelId	频道id
	 * @param worldId	织图主键id
	 * @param limit		查询数量的上限
	 * @return
	 */
	@DataSource("master")
	public List<Integer> queryNotInteractNRandomNotFollowZombie(@Param("concernId")Integer concernId,@Param("channelId")Integer channelId,@Param("worldId")Integer worldId,@Param("limit")Integer limit);
	
	/**
	 * 查询n个没有互动的非粉丝马甲总数 
	 * 
	 * @param concernId	被关注的人的id,即非马甲
	 * @param channelId	频道id
	 * @param worldId	织图主键id
	 * @param limit		查询数量的上限
	 * @return
	 * @author zhangbo	2015年9月28日
	 */
	@DataSource("slave")
	public Integer queryNotInteractNRandomNotFollowZombieCount(@Param("concernId")Integer concernId,@Param("channelId")Integer channelId,@Param("worldId")Integer worldId);
	
}

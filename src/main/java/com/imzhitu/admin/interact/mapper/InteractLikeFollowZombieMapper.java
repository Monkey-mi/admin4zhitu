package com.imzhitu.admin.interact.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.InteractLikeFollowZombie;

public interface InteractLikeFollowZombieMapper {
	/**
	 * 增加
	 * @param dto
	 */
	@DataSource("master")
	public void insertLikeFollowZombie(InteractLikeFollowZombie dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DataSource("master")
	public void batchDeleteLikeFollowZombie(Integer[] ids);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<InteractLikeFollowZombie> queryLikeFollowZombie(InteractLikeFollowZombie dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryLikeFollowZombieTotalCount(InteractLikeFollowZombie dto);
	
	/**
	 * 随机查询n个未关注未评论该织图的马甲
	 * @param userId
	 * @param worldId
	 * @return
	 */
	@DataSource("slave")
	public List<Integer> queryNRandomNotCommentNotFollowZombieId(@Param("userId")Integer userId,@Param("worldId")Integer worldId);
}

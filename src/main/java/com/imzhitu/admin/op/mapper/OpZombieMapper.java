package com.imzhitu.admin.op.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpZombie;

public interface OpZombieMapper {
	/**
	 * 增加
	 * @param dto
	 */
	@DataSource("master")
	public void insertZombie(OpZombie dto);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DataSource("master")
	public void batchDeleteZombie(Integer[] ids);
	
	/**
	 * 批量修改
	 * @param lastModify
	 * @param concernCount
	 * @param commentCount
	 * @param ids
	 */
	@DataSource("master")
	public void batchUpdateZombie(@Param("lastModify")Long lastModify,@Param("concernCount")Integer concernCount,
							@Param("commentCount")Integer commentCount,@Param("ids")Integer[] ids);
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<OpZombie> queryZombie(OpZombie dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryZombieTotalCount(OpZombie dto);
	
	/**
	 * 根据最后修改时间 查询n个马甲
	 * @param limit
	 * @param degreeId
	 * @return
	 */
	@DataSource("master")
	public List<OpZombie> queryZombieByLastModifyASC(@Param("degreeId")Integer degreeId,@Param("limit")Integer limit);
	
	/**
	 * 随机查询n个没有互动的粉丝马甲
	 * @param userId
	 * @param worldId
	 * @return
	 */
	@DataSource("slave")
	public List<Integer> queryNotInteractNRandomFollowZombie(@Param("userId")Integer userId,@Param("worldId")Integer worldId,@Param("limit")Integer limit);
	
	
	@DataSource("slave")
	public Integer queryNotInteractNRandomFollowZombieCount(@Param("userId")Integer userId,@Param("worldId")Integer worldId,@Param("limit")Integer limit);
	
	
	/**
	 * 随机查询n个没有互动的非粉丝马甲
	 * @param concernId 被关注的人的id,即非马甲
	 * @param degreeId
	 * @return
	 */
	@DataSource("master")
	public List<Integer> queryNotInteractNRandomNotFollowZombie(@Param("concernId")Integer concernId,@Param("degreeId")Integer degreeId,@Param("worldId")Integer worldId,@Param("limit")Integer limit);
	
	
	@DataSource("master")
	public Integer queryNotInteractNRandomNotFollowZombieCount(@Param("concernId")Integer concernId,@Param("degreeId")Integer degreeId,@Param("worldId")Integer worldId,@Param("limit")Integer limit);
	
	
	/**
	 * 
	 * @param concernId
	 * @param degreeId
	 * @param interactId
	 * @param limit
	 * @return
	 */
	@DataSource("slave")
	public List<Integer> queryNRandomNotFollowZombie(@Param("concernId")Integer concernId,@Param("degreeId")Integer degreeId,@Param("limit")Integer limit);
	
	@DataSource("master")
	public void updateSexAndSignature(@Param("userId")Integer userId,@Param("sex")Integer sex,@Param("signature")String signature);
}

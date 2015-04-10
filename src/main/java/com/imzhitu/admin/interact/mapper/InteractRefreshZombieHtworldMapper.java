package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.InteractRefreshZombieHtworld;
import com.imzhitu.admin.common.pojo.UserZombieHtworld;

public interface InteractRefreshZombieHtworldMapper {
	/**
	 * 刷新织图时间
	 * @param dto
	 */
	@DataSource("master")
	public void refreshWorldCreateDate(InteractRefreshZombieHtworld dto);
	
	/**
	 * 刷新织图评论时间
	 * @param dto
	 */
	@DataSource("master")
	public void refreshCommentCreateDate(InteractRefreshZombieHtworld dto);
	
	/**
	 * 查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryZombieHtworldTotalByMaxDate(UserZombieHtworld dto);
	
	/**
	 * 分页查询马甲织图
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<UserZombieHtworld> queryZombieHtworld(UserZombieHtworld dto);
	
	/**
	 * 根据wid查询其所有的评论
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<UserZombieHtworld> queryZombieComment(InteractRefreshZombieHtworld dto);
	
	/**
	 * 根据wid查询织图
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public  UserZombieHtworld queryZombieHtworldByWid(InteractRefreshZombieHtworld dto);
	
	/**
	 * 根据马甲id查询去所有的织图id
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<Integer> queryWidByUid(InteractRefreshZombieHtworld dto);
}

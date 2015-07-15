package com.imzhitu.admin.interact.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.InteractUserFollow;

public interface InteractUserFollowMapper {
	/**
	 * 批量添加
	 * @param list
	 */
	@DataSource("master")
	public void batchSaveUserFollow(List<InteractUserFollow> list);
	
	/**
	 * 批量删除 by Id
	 * @param id
	 */
	@DataSource("master")
	public void batchDeleteUserFollowById(Integer[] ids);
	
	/**
	 * 批量删除 by interactIds
	 * @param interactIds
	 */
	@DataSource("master")
	public void batchDeleteUserFollowByInteractId(Integer[] interactIds);
	
	/**
	 * 修改
	 * @param dto
	 */
	@DataSource("master")
	public void updateUserFollow(InteractUserFollow dto);
	
	/**
	 * 查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<InteractUserFollow> queryUserFollow(InteractUserFollow dto);
	
	/**
	 * 查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryUserFollowTotalCount(InteractUserFollow dto);
	
	/**
	 * 根据maxInteractId来更新有效性
	 * @param dto
	 */
	@DataSource("master")
	public void updateUserFollowValidByMaxInteractId(InteractUserFollow dto);
	
	/**
	 * 批量更新有效性
	 * @param valid
	 * @param ids
	 */
	@DataSource("master")
	public void batchUpdateUserFollowValid(@Param("valid")Integer valid,@Param("ids")List<Integer> ids);
	
	/**
	 * 批量更新完成情况
	 * @param finished
	 * @param ids
	 */
	@DataSource("master")
	public void batchUpdateUserFollowFinished(@Param("finished")Integer finished,@Param("ids")List<Integer> ids);
}

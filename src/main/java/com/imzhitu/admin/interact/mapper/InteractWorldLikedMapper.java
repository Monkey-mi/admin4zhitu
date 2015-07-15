package com.imzhitu.admin.interact.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.InteractWorldLiked;

public interface InteractWorldLikedMapper {
	/**
	 * 批量添加
	 * @param list
	 */
	@DataSource("master")
	public void batchSaveWorldLiked(List<InteractWorldLiked> list);
	
	/**
	 * 批量删除 by Id
	 * @param id
	 */
	@DataSource("master")
	public void batchDeleteWorldLikedById(Integer[] ids);
	
	/**
	 * 批量删除 by interactIds
	 * @param interactIds
	 */
	@DataSource("master")
	public void batchDeleteWorldLikedByInteractId(Integer[] interactIds);
	
	/**
	 * 修改
	 * @param dto
	 */
	@DataSource("master")
	public void updateWorldLiked(InteractWorldLiked dto);
	
	/**
	 * 查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<InteractWorldLiked> queryWorldLiked(InteractWorldLiked dto);
	
	/**
	 * 查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryWorldLikedTotalCount(InteractWorldLiked dto);
	
	/**
	 * 根据maxInteractId来更新有效性
	 * @param dto
	 */
	@DataSource("master")
	public void updateWorldLikedValidByMaxInteractId(InteractWorldLiked dto);
	
	/**
	 * 批量更新有效性
	 * @param valid
	 * @param ids
	 */
	@DataSource("master")
	public void batchUpdateWorldLikedValid(@Param("valid")Integer valid,@Param("ids")List<Integer> ids);
	
	/**
	 * 批量更新完成情况
	 * @param finished
	 * @param ids
	 */
	@DataSource("master")
	public void batchUpdateWorldLikedFinished(@Param("finished")Integer finished,@Param("ids")List<Integer> ids);
}

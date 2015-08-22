package com.imzhitu.admin.interact.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.InteractWorldCommentDto;

public interface InteractWorldCommentMapper {
	
	/**
	 * 批量添加
	 * @param list
	 */
	@DataSource("master")
	public void insertWorldComment(InteractWorldCommentDto dto);
	
	/**
	 * 批量添加
	 * @param list
	 */
	@DataSource("master")
	public void batchSaveWorldComment(List<InteractWorldCommentDto> list);
	
	/**
	 * 批量删除 by Id
	 * @param id
	 */
	@DataSource("master")
	public void batchDeleteWorldCommentById(Integer[] ids);
	
	/**
	 * 批量删除 by interactIds
	 * @param interactIds
	 */
	@DataSource("master")
	public void batchDeleteWorldCommentByInteractId(Integer[] interactIds);
	
	/**
	 * 修改
	 * @param dto
	 */
	@DataSource("master")
	public void updateWorldComment(InteractWorldCommentDto dto);
	
	/**
	 * 查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<InteractWorldCommentDto> queryWorldComment(InteractWorldCommentDto dto);
	
	/**
	 * 查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryWorldCommentTotalCount(InteractWorldCommentDto dto);
	
	/**
	 * 根据maxInteractId来更新有效性
	 * @param dto
	 */
	@DataSource("master")
	public void updateWorldCommentValidByMaxInteractId(InteractWorldCommentDto dto);
	
	/**
	 * 批量更新有效性
	 * @param valid
	 * @param ids
	 */
	@DataSource("master")
	public void batchUpdateWorldCommentValid(@Param("valid")Integer valid,@Param("ids")List<Integer> ids);
	
	/**
	 * 批量更新完成情况
	 * @param finished
	 * @param ids
	 */
	@DataSource("master")
	public void batchUpdateWorldCommentFinished(@Param("finished")Integer finished,@Param("ids")List<Integer> ids);
}

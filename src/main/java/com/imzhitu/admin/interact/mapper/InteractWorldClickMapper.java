package com.imzhitu.admin.interact.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.InteractWorldClick;

public interface InteractWorldClickMapper {
	/**
	 * 批量添加
	 * @param list
	 */
	@DataSource("master")
	public void batchSaveWorldClick(List<InteractWorldClick> list);
	
	/**
	 * 批量删除 by Id
	 * @param id
	 */
	@DataSource("master")
	public void batchDeleteWorldClickById(Integer[] ids);
	
	/**
	 * 批量删除 by interactIds
	 * @param interactIds
	 */
	@DataSource("master")
	public void batchDeleteWorldClickByInteractId(Integer[] interactIds);
	
	/**
	 * 修改
	 * @param dto
	 */
	@DataSource("master")
	public void updateWorldClick(InteractWorldClick dto);
	
	/**
	 * 查询
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<InteractWorldClick> queryWorldClick(InteractWorldClick dto);
	
	/**
	 * 查询总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryWorldClickTotalCount(InteractWorldClick dto);
	
	/**
	 * 根据maxInteractId来更新有效性
	 * @param dto
	 */
	@DataSource("master")
	public void updateWorldClickValidByMaxInteractId(InteractWorldClick dto);
	
	/**
	 * 批量更新有效性
	 * @param valid
	 * @param ids
	 */
	@DataSource("master")
	public void batchUpdateWorldClickValid(@Param("valid")Integer valid,@Param("ids")List<Integer> ids);
	
	/**
	 * 批量更新完成情况
	 * @param finished
	 * @param ids
	 */
	@DataSource("master")
	public void batchUpdateWorldClickFinished(@Param("finished")Integer finished,@Param("ids")List<Integer> ids);
	

}

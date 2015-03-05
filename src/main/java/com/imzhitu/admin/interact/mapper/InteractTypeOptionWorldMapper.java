package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.InteractTypeOptionWorldDto;

public interface InteractTypeOptionWorldMapper {
	/**
	 * 添加
	 * @param dto
	 */
	public void insertTypeOptionWorld(InteractTypeOptionWorldDto dto);
	
	/**
	 * 删除根据ids
	 * @param dto
	 */
	public void delTypeOptionWorldByIds(Integer[] ids);
	
	/**
	 * 删除，根据world——ids
	 * @param wids
	 */
	public void delTypeOptionWorldByWIds(Integer[] wids);
	
	/**
	 * 修改
	 * @param dto
	 */
	public void updateTypeOptionWorld(InteractTypeOptionWorldDto dto);
	
	/**
	 * 分页查询
	 */
	public List<InteractTypeOptionWorldDto> queryTypeOptionWorldForList(InteractTypeOptionWorldDto dto);
	
	/**
	 * 分页查询总数
	 * @param dto
	 * @return
	 */
	public long queryTypeOptionWorldCount(InteractTypeOptionWorldDto dto);
	
	/**
	 * 查询明星发的图
	 */
	public List<InteractTypeOptionWorldDto> queryStarWorld(InteractTypeOptionWorldDto dto);
	
	/**
	 * 检查是否存在
	 * @param dto
	 * @return
	 */
	public long chechIsExist(InteractTypeOptionWorldDto dto);
	
	/**
	 * 根据wid来删除
	 */
	public void delTypeOptionWorldByWid(Integer worldId);
	
	/**
	 * 修改精选点评
	 * @param dto
	 */
	public void updateReview(InteractTypeOptionWorldDto dto);
	
	/**
	 * 查询某个精选备选
	 * @param dto
	 * @return
	 */
	public InteractTypeOptionWorldDto queryTypeOptionWorld(InteractTypeOptionWorldDto dto);
	

}

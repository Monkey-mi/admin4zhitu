package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.InteractPlanComment;

public interface InteractPlanCommentMapper {
	
	/**
	 * 增加计划评论
	 * @param dto
	 */
	public void addPlanComment(InteractPlanComment dto);
	
	/**
	 * 删除计划评论
	 * @param ids
	 */
	public void delPlanCommentByIds(Integer[]ids);
	
	/**
	 * 查询计划评论
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<InteractPlanComment> queryInteractPlanComment(InteractPlanComment dto);
	
	/**
	 * 查询计划评论总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryInteractPlanCommentTotalCount(InteractPlanComment dto);
	
	/**
	 * 根据组id查询计划评论
	 * @param groupId
	 * @return
	 */
	@DataSource("slave")
	public long queryInteractPlanCommentCountByGroupId(Integer groupId);
	
	/**
	 * 更新计划评论内容 根据id
	 * @param dto
	 */
	public void updateCommentContentById(InteractPlanComment dto);
	
	/**
	 * 更新评论内容有效性
	 * @param dto
	 */
	public void updateCommentContentValid(InteractPlanComment dto);
}

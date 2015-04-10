package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.InteractPlanCommentLabel;

public interface InteractPlanCommentLabelMapper {
	/**
	 * 增加计划评论标签
	 * @param dto
	 */
	@DataSource("master")
	public void addPlanCommentLabel(InteractPlanCommentLabel dto);
	
	/**
	 * 查询互动计划评论标签总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long queryInteractPlanCommentLabelCount(InteractPlanCommentLabel dto);
	
	/**
	 * 查询互动计划评论标签
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<InteractPlanCommentLabel> queryInteractPlanCommentLabel(InteractPlanCommentLabel dto);
	
	/**
	 * 查询互动评论计划标签列表
	 * @return
	 */
	@DataSource("slave")
	public List<InteractPlanCommentLabel> queryInteractPlanCommentLabelList();
	
	/**
	 * 更新计划评论的有效性 根据id
	 * @param dto
	 */
	@DataSource("master")
	public void updatePlanCommentValidById(InteractPlanCommentLabel dto);
	
	/**
	 * 根据时间（日期和时间段如：日期为2015年1月1日。时间9:00）查询互动计划评论标签
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<InteractPlanCommentLabel> queryInteractPlanCommentLabelByDateAndTime(InteractPlanCommentLabel dto);
}

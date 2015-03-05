package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.InteractPlanCommentLabel;

public interface InteractPlanCommentLabelMapper {
	public void addPlanCommentLabel(InteractPlanCommentLabel dto);
	public long queryInteractPlanCommentLabelCount(InteractPlanCommentLabel dto);
	public List<InteractPlanCommentLabel> queryInteractPlanCommentLabel(InteractPlanCommentLabel dto);
	public List<InteractPlanCommentLabel> queryInteractPlanCommentLabelList();
	public void updatePlanCommentValidById(InteractPlanCommentLabel dto);
	public List<InteractPlanCommentLabel> queryInteractPlanCommentLabelByDateAndTime(InteractPlanCommentLabel dto);
}

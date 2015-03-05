package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.InteractPlanComment;

public interface InteractPlanCommentMapper {
	public void addPlanComment(InteractPlanComment dto);
	public void delPlanCommentByIds(Integer[]ids);
	public List<InteractPlanComment> queryInteractPlanComment(InteractPlanComment dto);
	public long queryInteractPlanCommentTotalCount(InteractPlanComment dto);
	public long queryInteractPlanCommentCountByGroupId(Integer groupId);
	public void updateCommentContentById(InteractPlanComment dto);
	public void updateCommentContentValid(InteractPlanComment dto);
}

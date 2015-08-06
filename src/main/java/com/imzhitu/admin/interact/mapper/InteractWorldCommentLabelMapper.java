package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.InteractWorldCommentLabel;

/**
 * 记录 织图被添加的评论标签
 * @author demonsader
 *
 */
public interface InteractWorldCommentLabelMapper {
	/**
	 * 增加
	 * @param dto
	 */
	public void insertWorldCommentLabel(InteractWorldCommentLabel dto);
	
	/**
	 * 查询
	 * @param dto
	 * @return
	 */
	public List<InteractWorldCommentLabel> queryWorldCommentLabel(InteractWorldCommentLabel dto);
}

package com.imzhitu.admin.interact.service;

import java.util.List;

import com.imzhitu.admin.common.pojo.InteractWorldCommentLabel;

public interface InteractWorldCommentLabelService {
	/**
	 * 添加
	 * @param worldId
	 * @param idsStr
	 * @throws Exception
	 */
	public void insertWorldCommentLabel(Integer worldId,String labelIds)throws Exception;
	
	/**
	 * 查询
	 * @param worldId
	 * @throws Exception
	 */
	public List<InteractWorldCommentLabel> queryWorldCommentLabel(Integer worldId)throws Exception;
}

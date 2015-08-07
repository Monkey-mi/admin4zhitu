package com.imzhitu.admin.interact.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.InteractWorldCommentLabel;
import com.imzhitu.admin.interact.mapper.InteractWorldCommentLabelMapper;
import com.imzhitu.admin.interact.service.InteractWorldCommentLabelService;

@Service
public class InteractWorldCommentLabelServiceImpl extends BaseServiceImpl implements InteractWorldCommentLabelService{
	
	@Autowired
	private InteractWorldCommentLabelMapper worldCommentLabelMapper;

	@Override
	public void insertWorldCommentLabel(Integer worldId, String labelIds, Integer operator) throws Exception {
		InteractWorldCommentLabel dto = new InteractWorldCommentLabel();
		dto.setWorldId(worldId);
		dto.setLabelIds(labelIds);
		dto.setOperator(operator);
		worldCommentLabelMapper.insertWorldCommentLabel(dto);
	}

	@Override
	public List<InteractWorldCommentLabel> queryWorldCommentLabel(Integer worldId) throws Exception {
		InteractWorldCommentLabel dto = new InteractWorldCommentLabel();
		dto.setWorldId(worldId);
		List<InteractWorldCommentLabel> list = null;
		list = worldCommentLabelMapper.queryWorldCommentLabel(dto);
		return list;
	}

}

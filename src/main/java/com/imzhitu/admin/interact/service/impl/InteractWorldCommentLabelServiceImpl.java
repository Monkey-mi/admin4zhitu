package com.imzhitu.admin.interact.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.InteractWorldCommentLabel;
import com.imzhitu.admin.interact.mapper.InteractWorldCommentLabelMapper;
import com.imzhitu.admin.interact.service.InteractWorldCommentLabelService;

public class InteractWorldCommentLabelServiceImpl extends BaseServiceImpl implements InteractWorldCommentLabelService{
	
	@Autowired
	private InteractWorldCommentLabelMapper worldCommentLabelMapper;

	@Override
	public void insertWorldCommentLabel(Integer worldId, String labelIds) throws Exception {
		// TODO Auto-generated method stub
		InteractWorldCommentLabel dto = new InteractWorldCommentLabel();
		dto.setWorldId(worldId);
		dto.setLabelIds(labelIds);
		worldCommentLabelMapper.insertWorldCommentLabel(dto);
	}

	@Override
	public List<InteractWorldCommentLabel> queryWorldCommentLabel(Integer worldId) throws Exception {
		// TODO Auto-generated method stub
		InteractWorldCommentLabel dto = new InteractWorldCommentLabel();
		dto.setWorldId(worldId);
		List<InteractWorldCommentLabel> list = null;
		list = worldCommentLabelMapper.queryWorldCommentLabel(dto);
		return list;
	}

}

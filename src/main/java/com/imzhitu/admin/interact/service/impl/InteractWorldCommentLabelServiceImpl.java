package com.imzhitu.admin.interact.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.InteractWorldCommentLabel;
import com.imzhitu.admin.interact.mapper.InteractWorldCommentLabelMapper;
import com.imzhitu.admin.interact.service.InteractWorldCommentLabelService;
import com.imzhitu.admin.interact.service.InteractWorldService;

@Service
public class InteractWorldCommentLabelServiceImpl extends BaseServiceImpl implements InteractWorldCommentLabelService{
	
	@Autowired
	private InteractWorldCommentLabelMapper worldCommentLabelMapper;
	
	@Autowired
	private InteractWorldService worldService;

	@Override
	public void insertWorldCommentLabel(Integer worldId, String labelIds, Integer operator) throws Exception {
		if(worldId == null || labelIds == null || "".equals(labelIds.trim())){
			throw new Exception("worldId ="+worldId+"\nlabelIds="+labelIds);
		}
		InteractWorldCommentLabel dto = new InteractWorldCommentLabel();
		dto.setWorldId(worldId);
		dto.setLabelIds(labelIds);
		dto.setOperator(operator);
		worldCommentLabelMapper.insertWorldCommentLabel(dto);
		worldService.saveInteractV3(worldId, labelIds);
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

package com.imzhitu.admin.interact.service;

import java.util.List;

import com.imzhitu.admin.common.pojo.InteractCommentLabel;

public interface InteractCommentLabelIdService {
	
	public void insertLableId(Integer lableId) throws Exception;
	
	public void deleteLabelId(Integer lableId)throws Exception;
	
	public Integer selectLabelId(Integer lableId)throws Exception;
	
	public List<InteractCommentLabel> selectLableAll()throws Exception;
	}

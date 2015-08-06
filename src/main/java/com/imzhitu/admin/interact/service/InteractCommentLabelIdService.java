package com.imzhitu.admin.interact.service;

import java.util.List;

import com.imzhitu.admin.common.pojo.InteractCommentLabelId;

public interface InteractCommentLabelIdService {

	public void insertLableId(Integer lableId) throws Exception;
	
	public void deleteLabelId(Integer lableId)throws Exception;
	
	public Integer selectLabelId(Integer lableId)throws Exception;
	
	public List<InteractCommentLabelId> selectLableIdAll()throws Exception;
	}

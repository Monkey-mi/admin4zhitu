package com.imzhitu.admin.interact;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.interact.service.InteractLikeFollowCommentLabelService;

public class InteractLikeFollowCommentLabelAction extends BaseCRUDAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3223373831149361893L;
	private Integer id;
	private Integer labelId;
	private String idsStr;
	private String labelIdsStr;
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}

	public String getIdsStr() {
		return idsStr;
	}

	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}

	public String getLabelIdsStr() {
		return labelIdsStr;
	}

	public void setLabelIdsStr(String labelIdsStr) {
		this.labelIdsStr = labelIdsStr;
	}

	@Autowired
	private InteractLikeFollowCommentLabelService likeFollowCommentLabelService;
	
	public String insertLikeFollowCommentLabel(){
		try{
			likeFollowCommentLabelService.insertLikeFollowCommentLabel(labelId);
			JSONUtil.optFailed(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchDeleteLikeFollowCommentLabel(){
		try{
			likeFollowCommentLabelService.batchDeleteLikeFollowCommentLabel(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String queryLikeFollowCommentLabel(){
		try{
			likeFollowCommentLabelService.queryLikeFollowCommentLabel(id, labelId, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
}

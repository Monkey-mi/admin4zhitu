package com.imzhitu.admin.interact;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.interact.service.InteractCommentLabelChannelService;

public class InteractCommentLabelChannelAction extends BaseCRUDAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3334991756211200745L;
	
	@Autowired
	private InteractCommentLabelChannelService commentLabelChannelService;
	
	private Integer id;
	private Integer channelId;
	private Integer commentLabelId;
	private String idsStr;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Integer getCommentLabelId() {
		return commentLabelId;
	}
	public void setCommentLabelId(Integer commentLabelId) {
		this.commentLabelId = commentLabelId;
	}
	public String getIdsStr() {
		return idsStr;
	}
	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}
	
	public String insertCommentLabelChannel(){
		try{
			commentLabelChannelService.insertCommentLabelChannel(channelId, commentLabelId, getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchDeleteCommentLabelChannel(){
		try{
			commentLabelChannelService.batchDeleteCommentLabelChannel(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String queryCommentLabelChannel(){
		try{
			commentLabelChannelService.queryCommentLabelChannel(id, channelId, commentLabelId, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
}

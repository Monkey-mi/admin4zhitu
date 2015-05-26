package com.imzhitu.admin.op;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.op.service.OpChannelLinkService;

public class OpChannelLinkAction extends BaseCRUDAction{

	private static final long serialVersionUID = 4365256620702330156L;
	
	@Autowired
	private OpChannelLinkService opChannelLinkService;
	
	private Integer channelId;
	private Integer linkId;
	private String rowJson;
	
	public String queryOpChannelLink(){
		try{
			opChannelLinkService.queryOpChannelLink(maxId, page, rows, channelId, linkId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String insertOpChannelLink(){
		try{
			opChannelLinkService.insertOpChannelLink(channelId, linkId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchDeleteOpChannelLink(){
		try{
			opChannelLinkService.batchDeleteOpChannelLink(rowJson);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	public String getRowJson() {
		return rowJson;
	}

	public void setRowJson(String rowJson) {
		this.rowJson = rowJson;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}
	

}

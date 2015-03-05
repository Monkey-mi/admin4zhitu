package com.imzhitu.admin.op;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.op.service.OpChannelIdVerifyIdService;

public class OpChannelIdVerifyIdAction extends BaseCRUDAction{
	private static final long serialVersionUID = -6300614196468192285L;
	@Autowired
	private OpChannelIdVerifyIdService service;
	
	private Integer id;
	private String idsStr;
	private Integer channelId;
	private Integer verifyId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIdsStr() {
		return idsStr;
	}
	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Integer getVerifyId() {
		return verifyId;
	}
	public void setVerifyId(Integer verifyId) {
		this.verifyId = verifyId;
	}

	public String insertChannelIdVerifyId(){
		try{
			service.insertChannelIdVerifyId(channelId, verifyId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optSuccess(OptResult.ADD_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchDeleteChannelIdVerifyId(){
		try{
			service.batchDeleteChannelIdVerifyId(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optSuccess(OptResult.DELETE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String queryChannelIdVerifyIdForList(){
		try{
			service.queryChannelIdVerifyIdForList(maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
}

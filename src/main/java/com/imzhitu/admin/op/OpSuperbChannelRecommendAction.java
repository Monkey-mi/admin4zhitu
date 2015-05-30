package com.imzhitu.admin.op;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.op.service.OpSuperbChannelRecommendService;

public class OpSuperbChannelRecommendAction extends BaseCRUDAction{

	private static final long serialVersionUID = -7727857332376130119L;
	
	@Autowired
	private OpSuperbChannelRecommendService service;
	
	private Integer id;
	private Integer channelId;
	private Integer valid;
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
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public String getIdsStr() {
		return idsStr;
	}
	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	public String querySuperbChannelRecommend(){
		try{
			service.qeurySuperbChannelRecommend(id, channelId, valid, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 批量删除
	 * @return
	 */
	public String batchDeleteSuperbChannelRecommend(){
		try{
			service.batchDeleteSuperbChannelRecommend(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 批量更新有效性
	 * @return
	 */
	public String batchUpdateSuperbChannelRecommendValid(){
		try{
			service.batchUpdateSuperbChannelRecommendValid(valid,idsStr);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertSuperbChannelRecommend(){
		try{
			service.insertSuperbChannelRecommend(channelId, Tag.TRUE, getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String updateSuperbChannnelRecommendCache(){
		try{
			service.updateSuperbChannelRecommendCache();
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

}

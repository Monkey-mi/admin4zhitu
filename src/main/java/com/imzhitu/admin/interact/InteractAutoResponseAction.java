package com.imzhitu.admin.interact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.interact.service.InteractAutoResponseService;
public class InteractAutoResponseAction extends BaseCRUDAction{

	private static final long serialVersionUID = 6281163937095805626L;
	
	private String idsStr;//interact_auto_response表中id串
	private Integer autoResponseId;//interact_auto_response表中的response_id字段，对应htworld_comment表中的id，就是马甲回复用户的回复的id
	private String responseIdsStr;//同上，id字符串
	private String rowJson;//页面行对应的json
	private String question;
	private String answer;
	private Integer userLevelId;
	
	public Integer getUserLevelId() {
		return userLevelId;
	}
	public void setUserLevelId(Integer userLevelId) {
		this.userLevelId = userLevelId;
	}

	@Autowired
	private InteractAutoResponseService interactAutoResponseService;
	
	public void setQuestion(String question){
		this.question = question;
	}
	public String getQuestion(){
		return this.question;
	}
	
	public void setAnswer(String answer){
		this.answer = answer;
	}
	public String getAnswer(){
		return this.answer;
	}
	
	public void setInteractAutoResponseService(InteractAutoResponseService interactAutoResponseService){
		this.interactAutoResponseService = interactAutoResponseService;
	}
	public InteractAutoResponseService getInteractAutoResponseService(){
		return this.interactAutoResponseService;
	}
	
	public void setResponseIdsStr(String responseIdsStr){
		this.responseIdsStr = responseIdsStr;
	}
	public String getResponseIdsStr(){
		return this.responseIdsStr;
	}
	
	public void setRowJson(String rowJson){
		this.rowJson = rowJson;
	}
	public String getRowJson(){
		return this.rowJson;
	}
	
	public void setAutoResponseId(Integer autoResponseId){
		this.autoResponseId = autoResponseId;
	}
	public Integer getAutoResponseId(){
		return this.autoResponseId;
	}
	
	public void setIdsStr(String idsStr){
		this.idsStr = idsStr;
	}
	public String getIdsStr(){
		return this.idsStr;
	}
	
	public String queryUncompleteResponse(){
		try{
			interactAutoResponseService.queryUncompleteResponse(maxId, page, rows,userLevelId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	public String updateResponseCompleteByIds(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			interactAutoResponseService.updateResponseCompleteByIds(idsStr,responseIdsStr,user.getId());
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	public String queryResponseGroupById(){
		try{
			interactAutoResponseService.queryResponseGroupById(autoResponseId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	public String delAutoResponseByIds(){
		try{
			interactAutoResponseService.delAutoResponseByIds(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	public String updateCommentContentByRowJson(){
		try{
			interactAutoResponseService.updateCommentContentByRowJson(rowJson);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS,jsonMap);
		}catch(Exception e ){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	public String teachTuLingRobot(){
		try{
			interactAutoResponseService.teachTuLingRobot(question, answer, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}

}

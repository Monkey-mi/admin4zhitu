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
	
	/**
	 * 频道id，根据频道查询结果集时使用
	 * @author zhangbo	2015年9月1日
	 */
	private Integer channelId;
	
	public String getIdsStr() {
		return idsStr;
	}

	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}

	public Integer getAutoResponseId() {
		return autoResponseId;
	}

	public void setAutoResponseId(Integer autoResponseId) {
		this.autoResponseId = autoResponseId;
	}

	public String getResponseIdsStr() {
		return responseIdsStr;
	}

	public void setResponseIdsStr(String responseIdsStr) {
		this.responseIdsStr = responseIdsStr;
	}

	public String getRowJson() {
		return rowJson;
	}

	public void setRowJson(String rowJson) {
		this.rowJson = rowJson;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getUserLevelId() {
		return userLevelId;
	}

	public void setUserLevelId(Integer userLevelId) {
		this.userLevelId = userLevelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	@Autowired
	private InteractAutoResponseService interactAutoResponseService;
	
	public String queryUncompleteResponse(){
		try{
			interactAutoResponseService.queryUncompleteResponse(maxId, page, rows,userLevelId, channelId, jsonMap);
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

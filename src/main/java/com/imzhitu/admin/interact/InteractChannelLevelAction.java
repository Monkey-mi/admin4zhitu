package com.imzhitu.admin.interact;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.interact.service.InteractChannelLevelService;

/**
 * 
 * @author zxx 2015年7月20日 16:37:27
 *
 */
public class InteractChannelLevelAction extends BaseCRUDAction {

	private static final long serialVersionUID = 8029977349440082187L;
	
	@Autowired
	private InteractChannelLevelService channelLevelService;
	
	private Integer id;
	private Integer channelId;
	private Integer unSuperMinCommentCount;
	private Integer unSuperMaxCommentCount;
	private Integer superMinCommentCount;
	private Integer superMaxCommentCount;
	private Integer minuteTime;
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
	public Integer getUnSuperMinCommentCount() {
		return unSuperMinCommentCount;
	}
	public void setUnSuperMinCommentCount(Integer unSuperMinCommentCount) {
		this.unSuperMinCommentCount = unSuperMinCommentCount;
	}
	public Integer getUnSuperMaxCommentCount() {
		return unSuperMaxCommentCount;
	}
	public void setUnSuperMaxCommentCount(Integer unSuperMaxCommentCount) {
		this.unSuperMaxCommentCount = unSuperMaxCommentCount;
	}
	public Integer getSuperMinCommentCount() {
		return superMinCommentCount;
	}
	public void setSuperMinCommentCount(Integer superMinCommentCount) {
		this.superMinCommentCount = superMinCommentCount;
	}
	public Integer getSuperMaxCommentCount() {
		return superMaxCommentCount;
	}
	public void setSuperMaxCommentCount(Integer superMaxCommentCount) {
		this.superMaxCommentCount = superMaxCommentCount;
	}
	public Integer getMinuteTime() {
		return minuteTime;
	}
	public void setMinuteTime(Integer minuteTime) {
		this.minuteTime = minuteTime;
	}
	public String getIdsStr() {
		return idsStr;
	}
	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}
	
	public String insertChannelLevel(){
		try{
			channelLevelService.insertChannelLevel(channelId, unSuperMinCommentCount, unSuperMaxCommentCount, superMinCommentCount, superMaxCommentCount, minuteTime);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchDeleteChannelLevel(){
		try{
			channelLevelService.batchDeleteChannelLevel(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String updateChannelLevel(){
		try{
			channelLevelService.updateChannelLevel(id, unSuperMinCommentCount, unSuperMaxCommentCount, superMinCommentCount, superMaxCommentCount, minuteTime);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	public String queryChannelLevel(){
		try{
			channelLevelService.queryChannelLevel(id, channelId, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess( jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	

}

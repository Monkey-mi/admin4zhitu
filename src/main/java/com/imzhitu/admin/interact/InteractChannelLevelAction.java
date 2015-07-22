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
	
	private Integer unSuperMinLikeCount;
	private Integer unSuperMaxLikeCount;
	private Integer superMinLikeCount;
	private Integer superMaxLikeCount;
	private Integer unSuperMinFollowCount;
	private Integer unSuperMaxFollowCount;
	private Integer superMinFollowCount;
	private Integer superMaxFollowCount;
	private Integer unSuperMinClickCount;
	private Integer unSuperMaxClickCount;
	private Integer superMinClickCount;
	private Integer superMaxClickCount;
	
	private Integer minuteTime;
	private String idsStr;
	
	
	public Integer getUnSuperMinLikeCount() {
		return unSuperMinLikeCount;
	}
	public void setUnSuperMinLikeCount(Integer unSuperMinLikeCount) {
		this.unSuperMinLikeCount = unSuperMinLikeCount;
	}
	public Integer getUnSuperMaxLikeCount() {
		return unSuperMaxLikeCount;
	}
	public void setUnSuperMaxLikeCount(Integer unSuperMaxLikeCount) {
		this.unSuperMaxLikeCount = unSuperMaxLikeCount;
	}
	public Integer getSuperMinLikeCount() {
		return superMinLikeCount;
	}
	public void setSuperMinLikeCount(Integer superMinLikeCount) {
		this.superMinLikeCount = superMinLikeCount;
	}
	public Integer getSuperMaxLikeCount() {
		return superMaxLikeCount;
	}
	public void setSuperMaxLikeCount(Integer superMaxLikeCount) {
		this.superMaxLikeCount = superMaxLikeCount;
	}
	public Integer getUnSuperMinFollowCount() {
		return unSuperMinFollowCount;
	}
	public void setUnSuperMinFollowCount(Integer unSuperMinFollowCount) {
		this.unSuperMinFollowCount = unSuperMinFollowCount;
	}
	public Integer getUnSuperMaxFollowCount() {
		return unSuperMaxFollowCount;
	}
	public void setUnSuperMaxFollowCount(Integer unSuperMaxFollowCount) {
		this.unSuperMaxFollowCount = unSuperMaxFollowCount;
	}
	public Integer getSuperMinFollowCount() {
		return superMinFollowCount;
	}
	public void setSuperMinFollowCount(Integer superMinFollowCount) {
		this.superMinFollowCount = superMinFollowCount;
	}
	public Integer getSuperMaxFollowCount() {
		return superMaxFollowCount;
	}
	public void setSuperMaxFollowCount(Integer superMaxFollowCount) {
		this.superMaxFollowCount = superMaxFollowCount;
	}
	public Integer getUnSuperMinClickCount() {
		return unSuperMinClickCount;
	}
	public void setUnSuperMinClickCount(Integer unSuperMinClickCount) {
		this.unSuperMinClickCount = unSuperMinClickCount;
	}
	public Integer getUnSuperMaxClickCount() {
		return unSuperMaxClickCount;
	}
	public void setUnSuperMaxClickCount(Integer unSuperMaxClickCount) {
		this.unSuperMaxClickCount = unSuperMaxClickCount;
	}
	public Integer getSuperMinClickCount() {
		return superMinClickCount;
	}
	public void setSuperMinClickCount(Integer superMinClickCount) {
		this.superMinClickCount = superMinClickCount;
	}
	public Integer getSuperMaxClickCount() {
		return superMaxClickCount;
	}
	public void setSuperMaxClickCount(Integer superMaxClickCount) {
		this.superMaxClickCount = superMaxClickCount;
	}
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
			channelLevelService.insertChannelLevel(channelId, unSuperMinCommentCount, unSuperMaxCommentCount, 
					superMinCommentCount, superMaxCommentCount, unSuperMinLikeCount, unSuperMaxLikeCount, superMinLikeCount, 
					superMaxLikeCount, unSuperMinFollowCount, unSuperMaxFollowCount, superMinFollowCount, superMaxFollowCount,
					unSuperMinClickCount, unSuperMaxClickCount, superMinClickCount, superMaxClickCount, minuteTime);
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
			channelLevelService.updateChannelLevel(id, unSuperMinCommentCount, unSuperMaxCommentCount, superMinCommentCount, 
					superMaxCommentCount, unSuperMinLikeCount, unSuperMaxLikeCount, superMinLikeCount, superMaxLikeCount, 
					unSuperMinFollowCount, unSuperMaxFollowCount, superMinFollowCount, superMaxFollowCount, unSuperMinClickCount, 
					unSuperMaxClickCount, superMinClickCount, superMaxClickCount, minuteTime);
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

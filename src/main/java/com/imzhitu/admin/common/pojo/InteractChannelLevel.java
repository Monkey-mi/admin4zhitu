package com.imzhitu.admin.common.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;

public class InteractChannelLevel extends AbstractNumberDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5112779501624723334L;
	private Integer id;
	private Integer channelId;
	private String  channelName;
	private Integer unSuperMinCommentCount;
	private Integer unSuperMmaxCommentCount;
	private Integer superMinCommentCount;
	private Integer superMaxCommentCount;
	private Integer minuteTime;
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
	public Integer getUnSuperMmaxCommentCount() {
		return unSuperMmaxCommentCount;
	}
	public void setUnSuperMmaxCommentCount(Integer unSuperMmaxCommentCount) {
		this.unSuperMmaxCommentCount = unSuperMmaxCommentCount;
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
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Integer getMinuteTime() {
		return minuteTime;
	}
	public void setMinuteTime(Integer minuteTime) {
		this.minuteTime = minuteTime;
	}
}

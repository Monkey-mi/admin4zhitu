package com.imzhitu.admin.common.pojo;

import java.util.Date;

import com.hts.web.common.pojo.AbstractNumberDto;

public class InteractCommentLabelChannel extends AbstractNumberDto{
	private static final long serialVersionUID = -6361967518393164096L;
	private Integer id;
	private Integer channelId;
	private Integer commentLabelId;
	private Integer operator;
	private Date addDate;
	private String channelName;
	private String commentLabelName;
	private String operatorName;
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
	public Integer getOperator() {
		return operator;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getCommentLabelName() {
		return commentLabelName;
	}
	public void setCommentLabelName(String commentLabelName) {
		this.commentLabelName = commentLabelName;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
}

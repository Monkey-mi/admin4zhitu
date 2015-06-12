package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

import com.hts.web.common.pojo.AbstractNumberDto;

public class OpChannelMemberDto extends AbstractNumberDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1992889803568935357L;
	
	private Integer id;
	private Integer channelId;
	private Integer userId;
	private Long subTime;
	private Integer degree;
	
	private String channelName;
	private String userName;
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Long getSubTime() {
		return subTime;
	}
	public void setSubTime(Long subTime) {
		this.subTime = subTime;
	}
	public Integer getDegree() {
		return degree;
	}
	public void setDegree(Integer degree) {
		this.degree = degree;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}

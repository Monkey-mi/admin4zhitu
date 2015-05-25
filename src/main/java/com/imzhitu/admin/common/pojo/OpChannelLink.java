package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

import com.hts.web.common.pojo.AbstractNumberDto;

public class OpChannelLink extends AbstractNumberDto implements Serializable{
	private static final long serialVersionUID = 5345237551471715603L;
	
	private Integer channelId;
	private String channelName;
	private Integer linkChannelId;
	private String linkChannelName;
	private Integer serial;
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Integer getLinkChannelId() {
		return linkChannelId;
	}
	public void setLinkChannelId(Integer linkChannelId) {
		this.linkChannelId = linkChannelId;
	}
	public String getLinkChannelName() {
		return linkChannelName;
	}
	public void setLinkChannelName(String linkChannelName) {
		this.linkChannelName = linkChannelName;
	}
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	
	
}

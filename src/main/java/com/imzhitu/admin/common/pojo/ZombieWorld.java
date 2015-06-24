package com.imzhitu.admin.common.pojo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class ZombieWorld  extends AbstractNumberDto{

	private static final long serialVersionUID = 2926817667482060554L;
	private Integer id;	
	private String worldName;
	private String worldDesc;
	private String worldLabel;
	private String worldLabelIds;
	private Integer authorId;
	private String coverPath;
	private String titlePath;
	private String thumbTitlePath;
	private Double longitude;
	private Double latitude;
	private String locationAddr;
	private Integer size;
	private Integer complete;
	private Date addDate;
	private Date modifyDate;
	private Integer channelId;
	
	private Integer schedulaFlag;	//计划标志，用以查询的过滤条件
	
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
	private String channelName;
	
	private Integer htworldId;
	private String shortLink;
	
	public String getShortLink() {
		return shortLink;
	}
	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	public Integer getHtworldId() {
		return htworldId;
	}
	public void setHtworldId(Integer htworldId) {
		this.htworldId = htworldId;
	}
	@JSON(format="yyyy/MM/dd HH:mm:ss")
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
	@JSON(format="yyyy/MM/dd HH:mm:ss")
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWorldName() {
		return worldName;
	}
	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}
	public String getWorldDesc() {
		return worldDesc;
	}
	public void setWorldDesc(String worldDesc) {
		this.worldDesc = worldDesc;
	}
	public String getWorldLabel() {
		return worldLabel;
	}
	public void setWorldLabel(String worldLabel) {
		this.worldLabel = worldLabel;
	}
	public String getWorldLabelIds() {
		return worldLabelIds;
	}
	public void setWorldLabelIds(String worldLabelIds) {
		this.worldLabelIds = worldLabelIds;
	}
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	public String getCoverPath() {
		return coverPath;
	}
	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}
	public String getTitlePath() {
		return titlePath;
	}
	public void setTitlePath(String titlePath) {
		this.titlePath = titlePath;
	}
	public String getThumbTitlePath() {
		return thumbTitlePath;
	}
	public void setThumbTitlePath(String thumbTitlePath) {
		this.thumbTitlePath = thumbTitlePath;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		if(null == longitude){
			this.longitude = 0.0;
		} else {
			this.longitude = longitude;
		}
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		if(null == latitude){
			this.latitude = 0.0;
		} else {
			this.latitude = latitude;
		}
	}
	public String getLocationAddr() {
		return locationAddr;
	}
	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getComplete() {
		return complete;
	}
	public void setComplete(Integer complete) {
		this.complete = complete;
	}
	public Integer getSchedulaFlag() {
		return schedulaFlag;
	}
	public void setSchedulaFlag(Integer schedulaFlag) {
		this.schedulaFlag = schedulaFlag;
	}

}

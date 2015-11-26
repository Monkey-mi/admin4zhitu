package com.imzhitu.admin.ztworld.pojo;

import java.io.Serializable;
import java.util.Date;

public class ZTWorld implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8407087511918773995L;
	
	private Integer id;//马甲ID
	private String worldDesc;
	private String worldLabel;
	private String worldLabelIds;
	private Integer author_id;
	private String coverPath;
	private String titlePath;
	private String titleThumbPath;
	private Double longitude;
	private Double latitude;
	private String locationAddr;
	private String province;
	private String city;
	private Integer size;
	private Integer complete;//？
	private Integer htworldId;//织图ID
	private String shortLink;
	private Integer channelId;
	private Date schedula;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(Integer author_id) {
		this.author_id = author_id;
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
	public String getTitleThumbPath() {
		return titleThumbPath;
	}
	public void setTitleThumbPath(String titleThumbPath) {
		this.titleThumbPath = titleThumbPath;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getLocationAddr() {
		return locationAddr;
	}
	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	public Integer getHtworldId() {
		return htworldId;
	}
	public void setHtworldId(Integer htworldId) {
		this.htworldId = htworldId;
	}
	public String getShortLink() {
		return shortLink;
	}
	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Date getSchedula() {
		return schedula;
	}
	public void setSchedula(Date schedula) {
		this.schedula = schedula;
	}
	
	
}

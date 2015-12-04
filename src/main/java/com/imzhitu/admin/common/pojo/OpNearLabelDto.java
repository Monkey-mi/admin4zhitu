package com.imzhitu.admin.common.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;

public class OpNearLabelDto extends AbstractNumberDto{
	private static final long serialVersionUID = -2650022252782052499L;
	private Integer id;
	private Integer cityId;
	private String cityName;
	private String labelName;
	private Double longitude;
	private Double latitude;
	private String description;
	private String bannerUrl;
	private Integer serial;
	private Integer maxId = 0;
	private Integer firstRow; // 起始位置
	private Integer limit;
	
	public Integer getMaxId() {
		return maxId;
	}

	public void setMaxId(Integer maxId) {
		this.maxId = maxId;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(Integer firstRow) {
		this.firstRow = firstRow;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
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
	public String getBannerUrl() {
		return bannerUrl;
	}
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}

package com.imzhitu.admin.addr.pojo;

import java.io.Serializable;

/**
 * @author zhangbo	2015年12月25日
 *
 */
public class AddrDistrictDto implements Serializable {
	/**
	 * @author zhangbo	2015年12月25日
	 */
	private static final long serialVersionUID = -1281124884717613961L;
	private Integer id;
	private Integer gbtId;
	private Integer cityId;
	private String cityName;
	private String distictName;
	private Double longitude;
	private Double latitude;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGbtId() {
		return gbtId;
	}
	public void setGbtId(Integer gbtId) {
		this.gbtId = gbtId;
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
	public String getDistictName() {
		return distictName;
	}
	public void setDistictName(String distictName) {
		this.distictName = distictName;
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
}

package com.imzhitu.admin.op.pojo;

/**
 * 附近公告城市信息数据传输对象
 * 
 * @author lynch 2015-12-15
 *
 */
public class NearBulletinCityDto {

	private Integer bulletinId;
	private Integer cityId;
	private String cityName;

	public Integer getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(Integer bulletinId) {
		this.bulletinId = bulletinId;
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

}

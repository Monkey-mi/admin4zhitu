package com.imzhitu.admin.common.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * 附近推荐城市
 * @author zxx 2015-12-4 16:17:30
 *
 */
public class OpNearRecommendCityDto extends AbstractNumberDto{
	private static final long serialVersionUID = -3641217309180821232L;
	private Integer id;
	private Integer cityId;
	private Integer cityGroupId;
	private Integer serial;
	private String cityName;
	private String cityGroupName;
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
	public Integer getCityGroupId() {
		return cityGroupId;
	}
	public void setCityGroupId(Integer cityGroupId) {
		this.cityGroupId = cityGroupId;
	}
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityGroupName() {
		return cityGroupName;
	}
	public void setCityGroupName(String cityGroupName) {
		this.cityGroupName = cityGroupName;
	}
}

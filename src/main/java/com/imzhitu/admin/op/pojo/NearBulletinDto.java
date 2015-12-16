package com.imzhitu.admin.op.pojo;

import java.util.ArrayList;
import java.util.List;

import com.imzhitu.admin.common.pojo.OpMsgBulletin;

/**
 * 附近公告DTO
 * 
 * @author lynch 2015-12-15
 *
 */
public class NearBulletinDto extends OpMsgBulletin {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6479759565171307517L;

	private List<Integer> cityIds = new ArrayList<Integer>(); // 所在城市id列表
	private List<String> cities = new ArrayList<String>(); // 所在城市列表
	
	private Integer bulletinId; // 关联具体的公告id
	private Integer cityId; // 城市id
	private Double[] loc; // 经纬度
	
	public List<Integer> getCityIds() {
		return cityIds;
	}

	public void setCityIds(List<Integer> cityIds) {
		this.cityIds = cityIds;
	}

	public List<String> getCities() {
		return cities;
	}

	public void setCities(List<String> cities) {
		this.cities = cities;
	}

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

	public Double[] getLoc() {
		return loc;
	}

	public void setLoc(Double[] loc) {
		this.loc = loc;
	}

	
	
}

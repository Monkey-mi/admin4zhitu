package com.imzhitu.admin.addr.pojo;

import java.io.Serializable;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * 城市对象，与数据库保持一致
 * 
 * @author zhangbo 2015年11月19日
 *
 */
public class City extends AbstractNumberDto {

	/**
	 * 序列号
	 * 
	 * @author zhangbo 2015年11月19日
	 */
	private static final long serialVersionUID = 3497285948768007521L;

	/**
	 * 城市主键id
	 * 
	 * @author zhangbo 2015年11月19日
	 */
	private Integer id;

	/**
	 * 名称
	 * 
	 * @author zhangbo 2015年11月19日
	 */
	private String name;

	/**
	 * 简称
	 * 
	 * @author lynch 2015-12-05
	 */
	private String shortName;

	/**
	 * 省份id
	 * 
	 * @author zhangbo 2015年11月19日
	 */
	private Integer provinceId;

	/**
	 * 经度
	 * 
	 * @author lynch 2015-12-05
	 */
	private Double longitude;

	/**
	 * 纬度
	 * 
	 * @author lynch 2015-12-05
	 */
	private Double latitude;
	
	/**
	 * 半径
	 * 
	 * @author lynch 2015-12-05
	 */
	private Float radius;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the provinceId
	 */
	public Integer getProvinceId() {
		return provinceId;
	}

	/**
	 * @param provinceId
	 *            the provinceId to set
	 */
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
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

	public Float getRadius() {
		return radius;
	}

	public void setRadius(Float radius) {
		this.radius = radius;
	}
	
}

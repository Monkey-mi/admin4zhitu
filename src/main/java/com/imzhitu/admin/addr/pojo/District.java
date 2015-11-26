package com.imzhitu.admin.addr.pojo;

import java.io.Serializable;

/**
 * 行政区对象，与数据库保持一致
 * 
 * @author zhangbo	2015年11月19日
 *
 */
public class District implements Serializable {

	/**
	 * 序列号
	 * @author zhangbo	2015年11月19日
	 */
	private static final long serialVersionUID = -3204552972622075233L;
	
	/**
	 * 行政区主键id
	 * @author zhangbo	2015年11月19日
	 */
	private Integer id;
	
	/**
	 * 名称
	 * @author zhangbo	2015年11月19日
	 */
	private String name;
	
	/**
	 * 城市id
	 * @author zhangbo	2015年11月19日
	 */
	private Integer cityId;
	
	/**
	 * 最小经度
	 * @author zhangbo	2015年11月19日
	 */
	private Long longitudeMin;
	
	/**
	 * 最大经度
	 * @author zhangbo	2015年11月19日
	 */
	private Long longitudeMax;
	
	/**
	 * 最小纬度
	 * @author zhangbo	2015年11月19日
	 */
	private Long latitudeMin;
	
	/**
	 * 最大纬度
	 * @author zhangbo	2015年11月19日
	 */
	private Long latitudeMax;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}

	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the longitudeMin
	 */
	public Long getLongitudeMin() {
		return longitudeMin;
	}

	/**
	 * @param longitudeMin the longitudeMin to set
	 */
	public void setLongitudeMin(Long longitudeMin) {
		this.longitudeMin = longitudeMin;
	}

	/**
	 * @return the longitudeMax
	 */
	public Long getLongitudeMax() {
		return longitudeMax;
	}

	/**
	 * @param longitudeMax the longitudeMax to set
	 */
	public void setLongitudeMax(Long longitudeMax) {
		this.longitudeMax = longitudeMax;
	}

	/**
	 * @return the latitudeMin
	 */
	public Long getLatitudeMin() {
		return latitudeMin;
	}

	/**
	 * @param latitudeMin the latitudeMin to set
	 */
	public void setLatitudeMin(Long latitudeMin) {
		this.latitudeMin = latitudeMin;
	}

	/**
	 * @return the latitudeMax
	 */
	public Long getLatitudeMax() {
		return latitudeMax;
	}

	/**
	 * @param latitudeMax the latitudeMax to set
	 */
	public void setLatitudeMax(Long latitudeMax) {
		this.latitudeMax = latitudeMax;
	}

}

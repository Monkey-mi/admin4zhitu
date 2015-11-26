package com.imzhitu.admin.addr.pojo;

import java.io.Serializable;

/**
 * 省份对象，与数据库保持一致
 * 
 * @author zhangbo	2015年11月19日
 *
 */
public class Province implements Serializable{

	/**
	 * 序列号
	 * @author zhangbo	2015年11月19日
	 */
	private static final long serialVersionUID = 6535004023631011435L;
	
	/**
	 * 省份主键id
	 * @author zhangbo	2015年11月19日
	 */
	private Integer id;
	
	/**
	 * 名称
	 * @author zhangbo	2015年11月19日
	 */
	private String name;
	
	/**
	 * 国家id
	 * @author zhangbo	2015年11月19日
	 */
	private Integer countryId;
	
	/**
	 * 区域id
	 * @author zhangbo	2015年11月19日
	 */
	private Integer regionId;
	
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
	 * @return the countryId
	 */
	public Integer getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the regionId
	 */
	public Integer getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId the regionId to set
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
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

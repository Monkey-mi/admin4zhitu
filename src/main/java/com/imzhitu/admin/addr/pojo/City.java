package com.imzhitu.admin.addr.pojo;

import java.io.Serializable;

/**
 * 城市对象，与数据库保持一致
 * 
 * @author zhangbo	2015年11月19日
 *
 */
public class City implements Serializable {

	/**
	 * 序列号
	 * @author zhangbo	2015年11月19日
	 */
	private static final long serialVersionUID = 3497285948768007521L;

	/**
	 * 城市主键id
	 * @author zhangbo	2015年11月19日
	 */
	private Integer id;
	
	/**
	 * 名称
	 * @author zhangbo	2015年11月19日
	 */
	private String name;
	
	/**
	 * 省份id
	 * @author zhangbo	2015年11月19日
	 */
	private Integer provinceId;
	
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
	 * @return the provinceId
	 */
	public Integer getProvinceId() {
		return provinceId;
	}

	/**
	 * @param provinceId the provinceId to set
	 */
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
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

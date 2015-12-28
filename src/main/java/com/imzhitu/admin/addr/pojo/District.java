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
	 * 国标id
	 * @author zhangbo	2015年11月19日
	 */
	private Integer gbtId;
	
	/**
	 * 城市id
	 * @author zhangbo	2015年11月19日
	 */
	private Integer cityId;
	
	/**
	 * 中心经度
	 * @author zhangbo	2015年11月19日
	 */
	private Double longitudeCenter;
	
	/**
	 * 中心纬度
	 * @author zhangbo	2015年11月19日
	 */
	private Double latitudeCenter;
	

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
	 * @return the gbtId
	 */
	public Integer getGbtId() {
		return gbtId;
	}

	/**
	 * @param gbtId the gbtId to set
	 */
	public void setGbtId(Integer gbtId) {
		this.gbtId = gbtId;
	}

	/**
	 * @return the longitudeCenter
	 */
	public Double getLongitudeCenter() {
		return longitudeCenter;
	}

	/**
	 * @param longitudeCenter the longitudeCenter to set
	 */
	public void setLongitudeCenter(Double longitudeCenter) {
		this.longitudeCenter = longitudeCenter;
	}

	/**
	 * @return the latitudeCenter
	 */
	public Double getLatitudeCenter() {
		return latitudeCenter;
	}

	/**
	 * @param latitudeCenter the latitudeCenter to set
	 */
	public void setLatitudeCenter(Double latitudeCenter) {
		this.latitudeCenter = latitudeCenter;
	}

}

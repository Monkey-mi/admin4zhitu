package com.imzhitu.admin.trade.shop.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 商家对象，与数据库保持一致
 * 
 * @author zhangbo	2015年11月19日
 *
 */
public class Shop implements Serializable {

	/**
	 * 序列号
	 * @author zhangbo	2015年11月19日
	 */
	private static final long serialVersionUID = 2437942134220583423L;
	
	/**
	 * 商家主键id
	 * @author zhangbo	2015年11月19日
	 */
	private Integer id;
	
	/**
	 * 商家名称
	 * @author zhangbo	2015年11月19日
	 */
	private String name;
	
	/**
	 * 描述
	 * @author zhangbo	2015年11月19日
	 */
	private String description;
	
	/**
	 * 经度
	 * @author zhangbo	2015年11月19日
	 */
	private Double longitude;
	
	/**
	 * 纬度
	 * @author zhangbo	2015年11月19日
	 */
	private Double latitude;
	
	/**
	 * 国家id，对应所在国家
	 * @author zhangbo	2015年11月19日
	 */
	private Integer countryId;
	
	/**
	 * 省id，对应所在省份
	 * @author zhangbo	2015年11月19日
	 */
	private Integer provinceId;
	
	/**
	 * 市id，对应所在城市
	 * @author zhangbo	2015年11月19日
	 */
	private Integer cityId;
	
	/**
	 * 区id，对应所在城市行政区
	 * @author zhangbo	2015年11月19日
	 */
	private Integer districtId;
	
	/**
	 * 详细地址
	 * @author zhangbo	2015年11月19日
	 */
	private String address;
	
	/**
	 * 国家手机号代码
	 * 注：默认中国，代码86
	 * @author zhangbo	2015年11月19日
	 */
	private Integer phoneCode = 86;
	
	/**
	 * 手机号码
	 * @author zhangbo	2015年11月19日
	 */
	private Integer phone;
	
	/**
	 * 电话固话区号
	 * @author zhangbo	2015年11月19日
	 */
	private String telCode;
	
	/**
	 * 电话号码
	 * @author zhangbo	2015年11月19日
	 */
	private Integer tel;
	
	/**
	 * 创建时间
	 * @author zhangbo	2015年11月19日
	 */
	private Date createTime;
	
	/**
	 * 平均消费
	 * @author zhangbo	2015年11月19日
	 */
	private Integer consumptionAvg;
	
	/**
	 * 用户织图总数
	 * @author zhangbo	2015年11月19日
	 */
	private Integer customWorldCount;
	
	private String banner;
	
	private String businessHours;

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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
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
	 * @return the districtId
	 */
	public Integer getDistrictId() {
		return districtId;
	}

	/**
	 * @param districtId the districtId to set
	 */
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the phoneCode
	 */
	public Integer getPhoneCode() {
		return phoneCode;
	}

	/**
	 * @param phoneCode the phoneCode to set
	 */
	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

	/**
	 * @return the phone
	 */
	public Integer getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	/**
	 * @return the telCode
	 */
	public String getTelCode() {
		return telCode;
	}

	/**
	 * @param telCode the telCode to set
	 */
	public void setTelCode(String telCode) {
		this.telCode = telCode;
	}

	/**
	 * @return the tel
	 */
	public Integer getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(Integer tel) {
		this.tel = tel;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the consumptionAvg
	 */
	public Integer getConsumptionAvg() {
		return consumptionAvg;
	}

	/**
	 * @param consumptionAvg the consumptionAvg to set
	 */
	public void setConsumptionAvg(Integer consumptionAvg) {
		this.consumptionAvg = consumptionAvg;
	}

	/**
	 * @return the customWorldCount
	 */
	public Integer getCustomWorldCount() {
		return customWorldCount;
	}

	/**
	 * @param customWorldCount the customWorldCount to set
	 */
	public void setCustomWorldCount(Integer customWorldCount) {
		this.customWorldCount = customWorldCount;
	}

	/**
	 * @return the banner
	 */
	public String getBanner() {
		return banner;
	}

	/**
	 * @param banner the banner to set
	 */
	public void setBanner(String banner) {
		this.banner = banner;
	}

	/**
	 * @return the businessHours
	 */
	public String getBusinessHours() {
		return businessHours;
	}

	/**
	 * @param businessHours the businessHours to set
	 */
	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}
	
}

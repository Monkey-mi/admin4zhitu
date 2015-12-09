package com.imzhitu.admin.trade.shop;

import java.util.Date;

/**
 * @author zhangbo	2015年11月23日
 *
 */
public class ShopBAK {

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
	 * 国家id，对应所在国家
	 * @author zhangbo	2015年11月19日
	 */
	private String countryId;
	
	/**
	 * 省id，对应所在省份
	 * @author zhangbo	2015年11月19日
	 */
	private String provinceId;
	
	/**
	 * 市id，对应所在城市
	 * @author zhangbo	2015年11月19日
	 */
	private String cityId;
	
	/**
	 * 区id，对应所在城市行政区
	 * @author zhangbo	2015年11月19日
	 */
	private String districtId;
	
	/**
	 * 详细地址
	 * @author zhangbo	2015年11月19日
	 */
	private String address;
	
	/**
	 * 邮箱
	 * @author zhangbo	2015年11月19日
	 */
	private String email;
	
	/**
	 * 邮编
	 * @author zhangbo	2015年11月19日
	 */
	private String zipcode;
	
	/**
	 * 网站
	 * @author zhangbo	2015年11月19日
	 */
	private String website;
	
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
	 * QQ号码
	 * @author zhangbo	2015年11月19日
	 */
	private Integer qq;
	
	/**
	 * 创建时间
	 * @author zhangbo	2015年11月19日
	 */
	private Date createTime;
	
	/**
	 * 商家类型，存储的为类型id，以逗号分隔
	 * @author zhangbo	2015年11月19日
	 */
	private String type;
	
	/**
	 * 商家标签，存储的为标签id，以逗号分隔
	 * @author zhangbo	2015年11月19日
	 */
	private String label;
	
	/**
	 * 平均评星
	 * @author zhangbo	2015年11月19日
	 */
	private Long starAvg;
	
	/**
	 * 口味平均分
	 * @author zhangbo	2015年11月19日
	 */
	private Long tasteAvg;
	
	/**
	 * 环境平均分
	 * @author zhangbo	2015年11月19日
	 */
	private Long viewAvg;
	
	/**
	 * 服务平均分
	 * @author zhangbo	2015年11月19日
	 */
	private Long serviceAvg;
	
	/**
	 * 评论总数
	 * @author zhangbo	2015年11月19日
	 */
	private String comment;
	
	private String poi;

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
	 * @return the countryId
	 */
	public String getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the provinceId
	 */
	public String getProvinceId() {
		return provinceId;
	}

	/**
	 * @param provinceId the provinceId to set
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	/**
	 * @return the cityId
	 */
	public String getCityId() {
		return cityId;
	}

	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the districtId
	 */
	public String getDistrictId() {
		return districtId;
	}

	/**
	 * @param districtId the districtId to set
	 */
	public void setDistrictId(String districtId) {
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * @param zipcode the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
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
	 * @return the qq
	 */
	public Integer getQq() {
		return qq;
	}

	/**
	 * @param qq the qq to set
	 */
	public void setQq(Integer qq) {
		this.qq = qq;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the starAvg
	 */
	public Long getStarAvg() {
		return starAvg;
	}

	/**
	 * @param starAvg the starAvg to set
	 */
	public void setStarAvg(Long starAvg) {
		this.starAvg = starAvg;
	}

	/**
	 * @return the tasteAvg
	 */
	public Long getTasteAvg() {
		return tasteAvg;
	}

	/**
	 * @param tasteAvg the tasteAvg to set
	 */
	public void setTasteAvg(Long tasteAvg) {
		this.tasteAvg = tasteAvg;
	}

	/**
	 * @return the viewAvg
	 */
	public Long getViewAvg() {
		return viewAvg;
	}

	/**
	 * @param viewAvg the viewAvg to set
	 */
	public void setViewAvg(Long viewAvg) {
		this.viewAvg = viewAvg;
	}

	/**
	 * @return the serviceAvg
	 */
	public Long getServiceAvg() {
		return serviceAvg;
	}

	/**
	 * @param serviceAvg the serviceAvg to set
	 */
	public void setServiceAvg(Long serviceAvg) {
		this.serviceAvg = serviceAvg;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the poi
	 */
	public String getPoi() {
		return poi;
	}

	/**
	 * @param poi the poi to set
	 */
	public void setPoi(String poi) {
		this.poi = poi;
	}
	
	
		
}

package com.imzhitu.admin.trade.shop.pojo;

import java.io.Serializable;

/**
 * 商家信息，与前台展示、操作对应
 * 
 * @author zhangbo	2015年11月19日
 *
 */
public class ShopDTO implements Serializable {
	
	/**
	 * 序列号
	 * @author zhangbo	2015年11月19日
	 */
	private static final long serialVersionUID = -5151834584526633466L;
	
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
	 * 省/市/区
	 * @author zhangbo	2015年11月19日
	 */
	private String PCD;
	
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
	 * @return the pCD
	 */
	public String getPCD() {
		return PCD;
	}

	/**
	 * @param pCD the pCD to set
	 */
	public void setPCD(String pCD) {
		PCD = pCD;
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

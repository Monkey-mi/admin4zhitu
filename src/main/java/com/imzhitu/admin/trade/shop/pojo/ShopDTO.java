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
	 * 设置省/市/区
	 * @param province	省份
	 * @param city		城市
	 * @param district	行政区
	 * @author zhangbo	2015年11月19日
	 */
	public void setPCD(String province, String city, String district) {
		PCD = province + "/" + city + "/" + district;
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
	
	
	
}

package com.imzhitu.admin.trade.shop.pojo;

import java.io.Serializable;

/**
 * 商家分类对象，与数据库保持一致
 * 
 * @author zhangbo	2015年11月19日
 *
 */
public class ShopType implements Serializable {

	/**
	 * 序列号
	 * @author zhangbo	2015年11月19日
	 */
	private static final long serialVersionUID = 443546263016455491L;

	/**
	 * 商家类型主键id
	 * @author zhangbo	2015年11月19日
	 */
	private Integer id;
	
	/**
	 * 商家类型名称
	 * @author zhangbo	2015年11月19日
	 */
	private String name;

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
	
}

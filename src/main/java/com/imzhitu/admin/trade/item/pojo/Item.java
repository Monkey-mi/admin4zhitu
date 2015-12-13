package com.imzhitu.admin.trade.item.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * 商品数据映射对象
 * 
 * @author zhangbo	2015年12月9日
 *
 */
public class Item extends AbstractNumberDto implements Serializable {

	/**
	 * 序列号
	 * @author zhangbo	2015年12月9日
	 */
	private static final long serialVersionUID = -7475538724248855547L;
	
	private Integer itemSetSerial;
	private Integer itemId;
	
	/**
	 * 商品主键id
	 * @author zhangbo	2015年12月9日
	 */
	private Integer id;
	
	/**
	 * 名称
	 * @author zhangbo	2015年12月9日
	 */
	private String name;
	
	/**
	 * 简介
	 * @author zhangbo	2015年12月9日
	 */
	private String summary;
	
	/**
	 * 详情描述
	 * @author zhangbo	2015年12月9日
	 */
	private String description;
	
	/**
	 * 关联织图id，可以为空
	 * @author zhangbo	2015年12月9日
	 */
	private Integer worldId;
	
	/**
	 * 商品图片路径
	 * @author zhangbo	2015年12月9日
	 */
	private String imgPath;
	
	/**
	 * 商品缩略图路径
	 * @author zhangbo	2015年12月9日
	 */
	private String imgThumb;
	
	/**
	 * 价格
	 * @author zhangbo	2015年12月9日
	 */
	private BigDecimal price;
	
	/**
	 * 促销价
	 * @author zhangbo	2015年12月9日
	 */
	private BigDecimal sale;
	
	/**
	 * 销售量
	 * @author zhangbo	2015年12月9日
	 */
	private Integer sales;

	/**
	 * 库存量
	 * @author zhangbo	2015年12月9日
	 */
	private Integer stock;
	
	/**
	 * 淘宝商品真实id
	 * @author zhangbo	2015年12月9日
	 */
	private Long taobaoId;
	
	/**
	 * 淘宝物品:1,天猫:2
	 * @author zhangbo	2015年12月9日
	 */
	private Integer taobaoType;
	
	/**
	 * 类别id
	 * @author zhangbo	2015年12月9日
	 */
	private Integer categoryId;
	
	/**
	 * 品牌id
	 * @author zhangbo	2015年12月9日
	 */
	private Integer brandId;
	
	/**
	 * 点赞数量
	 * @author zhangbo	2015年12月10日
	 */
	private Integer likeNum;
	
	/**
	 * 商品链接内容
	 * @author zhangbo	2015年12月11日
	 */
	private String link;
	
	
	/**
	 * 商品集合ID
	 */
	private Integer itemSetId;

	
	/**
	 * 排序字段
	 */
	private Integer serial;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**i
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
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
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
	 * @return the worldId
	 */
	public Integer getWorldId() {
		return worldId;
	}

	/**
	 * @param worldId the worldId to set
	 */
	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	/**
	 * @return the imgPath
	 */
	public String getImgPath() {
		return imgPath;
	}

	/**
	 * @param imgPath the imgPath to set
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	/**
	 * @return the imgThumb
	 */
	public String getImgThumb() {
		return imgThumb;
	}

	/**
	 * @param imgThumb the imgThumb to set
	 */
	public void setImgThumb(String imgThumb) {
		this.imgThumb = imgThumb;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the sale
	 */
	public BigDecimal getSale() {
		return sale;
	}

	/**
	 * @param sale the sale to set
	 */
	public void setSale(BigDecimal sale) {
		this.sale = sale;
	}

	/**
	 * @return the sales
	 */
	public Integer getSales() {
		return sales;
	}

	/**
	 * @param sales the sales to set
	 */
	public void setSales(Integer sales) {
		this.sales = sales;
	}

	/**
	 * @return the stock
	 */
	public Integer getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(Integer stock) {
		this.stock = stock;
	}


	/**
	 * @return the categoryId
	 */
	public Integer getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the brandId
	 */
	public Integer getBrandId() {
		return brandId;
	}

	/**
	 * @param brandId the brandId to set
	 */
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	public Integer getItemSetId() {
		return itemSetId;
	}

	public void setItemSetId(Integer itemSetId) {
		this.itemSetId = itemSetId;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public Long getTaobaoId() {
		return taobaoId;
	}

	public void setTaobaoId(Long taobaoId) {
		this.taobaoId = taobaoId;
	}

	public Integer getTaobaoType() {
		return taobaoType;
	}

	public void setTaobaoType(Integer taobaoType) {
		this.taobaoType = taobaoType;
	}

	public Integer getItemSetSerial() {
		return itemSetSerial;
	}

	public void setItemSetSerial(Integer itemSetSerial) {
		this.itemSetSerial = itemSetSerial;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	
}

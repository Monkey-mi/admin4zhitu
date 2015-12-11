package com.imzhitu.admin.trade.item.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 商品集合数据传输对象
 * 
 * @author zhangbo	2015年12月9日
 *
 */
public class ItemSetDTO implements Serializable {
	
	/**
	 * 序列号
	 * @author zhangbo	2015年12月9日
	 */
	private static final long serialVersionUID = 4235311839330302461L;

	/**
	 * 商品集合主键id
	 * @author zhangbo	2015年12月8日
	 */
	private Integer id;
	
	/**
	 * 商品集合描述
	 * @author zhangbo	2015年12月8日
	 */
	private String description;
	
	/**
	 * 商品集合图片路径
	 * @author zhangbo	2015年12月8日
	 */
	private String path;
	
	/**
	 * 商品集合缩略图路径
	 * @author zhangbo	2015年12月8日
	 */
	private String thumb;
	
	/**
	 * 商品集合类型，此类型是与公告类型保持一致，并且由频道公告这个页面中的链接类型流水而来，对于商家集合来说，类型固定为5、7两个数值
	 * @author zhangbo	2015年12月8日
	 */
	private Integer type;
	
	/**
	 * 商品集合banner点击跳转内容，此内容针对商家集合来说，都是网页链接地址，都跳转到h5页面，来展示具体商品
	 * @author zhangbo	2015年12月8日
	 */
	private String link;
	
	/**
	 * 操作者名称，即操作此数据的管理员
	 * @author zhangbo	2015年12月9日
	 */
	private String operator;
	
	/**
	 * 序号，序号越大排序越靠前
	 * @author zhangbo	2015年12月9日
	 */
	private Integer serial;
	
	/**
	 * 创建时间
	 * @author zhangbo	2015年12月9日
	 */
	private Date createTime;

	/**
	 * 更新时间
	 * @author zhangbo	2015年12月9日
	 */
	private Date modifyTime;
	
	/**
	 * 限时秒杀商品集合，截止时间
	 * @author zhangbo	2015年12月9日
	 */
	private Date deadline;
	
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
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the thumb
	 */
	public String getThumb() {
		return thumb;
	}

	/**
	 * @param thumb the thumb to set
	 */
	public void setThumb(String thumb) {
		this.thumb = thumb;
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
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
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

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the serial
	 */
	public Integer getSerial() {
		return serial;
	}

	/**
	 * @param serial the serial to set
	 */
	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	/**
	 * @return the createTime
	 */
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
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
	 * @return the modifyTime
	 */
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the deadline
	 */
	public Date getDeadline() {
		return deadline;
	}

	/**
	 * @param deadline the deadline to set
	 */
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
}

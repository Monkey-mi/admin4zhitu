package com.imzhitu.admin.common.pojo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class OpMsgBulletin extends AbstractNumberDto{
	
	private static final long serialVersionUID = -4313547862727503386L;
	
	/**
	 * 公告主键id
	 * @author zhangbo	2015年12月18日
	 */
	private Integer id;
	
	/**
	 * 公告分类	1：有奖活动，2：无奖活动，3：达人专题，4：内容专题
	 * @author zhangbo	2015年12月18日
	 */
	private Integer category;
	
	/**
	 * 公告图片路径
	 * @author zhangbo	2015年12月18日
	 */
	private String bulletinPath;
	
	/**
	 * 公告链接类型	0：无需跳转，1：网页链接，2：频道id，3：用户id，4：活动标签（名称）
	 * @author zhangbo	2015年12月18日
	 */
	private Integer bulletinType;
	
	/**
	 * 公告链接内容
	 * @author zhangbo	2015年12月18日
	 */
	private String link;
	
	/**
	 * 操作者id，即管理员id
	 * @author zhangbo	2015年12月18日
	 */
	private Integer operator;
	
	/**
	 * 操作者名称，即管理员名称
	 * @author zhangbo	2015年12月18日
	 */
	private String operatorName;
	
	/**
	 * 添加日期
	 * @author zhangbo	2015年12月18日
	 */
	private Date addDate;
	
	/**
	 * 更新日期
	 * @author zhangbo	2015年12月18日
	 */
	private Date modifyDate;
	
	/**
	 * 公告名称，即公告的描述
	 * @author zhangbo	2015年12月18日
	 */
	private String bulletinName;
	
	/**
	 * 公告缩略图路径
	 * @author zhangbo	2015年12月18日
	 */
	private String bulletinThumb;
	
	/**
	 * 序号
	 * @author zhangbo	2015年12月18日
	 */
	private Integer serial;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getBulletinPath() {
		return bulletinPath;
	}

	public void setBulletinPath(String bulletinPath) {
		this.bulletinPath = bulletinPath;
	}

	public Integer getBulletinType() {
		return bulletinType;
	}

	public void setBulletinType(Integer bulletinType) {
		this.bulletinType = bulletinType;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getBulletinName() {
		return bulletinName;
	}

	public void setBulletinName(String bulletinName) {
		this.bulletinName = bulletinName;
	}

	public String getBulletinThumb() {
		return bulletinThumb;
	}

	public void setBulletinThumb(String bulletinThumb) {
		this.bulletinThumb = bulletinThumb;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	
}

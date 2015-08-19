package com.imzhitu.admin.common.pojo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class OpMsgBulletin extends AbstractNumberDto{
	private static final long serialVersionUID = -4313547862727503386L;
	private Integer id;
	private String bulletinPath;
	private Integer bulletinType;
	private String link;
	private Integer operator;
	private String operatorName;
	private Date addDate;
	private Date modifyDate;
	private Integer valid;
	private String bulletinName;
	private String bulletinThumb;
	private Integer serial;
	
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
	}
}

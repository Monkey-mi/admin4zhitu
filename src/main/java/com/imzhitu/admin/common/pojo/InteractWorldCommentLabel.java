package com.imzhitu.admin.common.pojo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class InteractWorldCommentLabel extends AbstractNumberDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8291906294146340327L;
	private Integer id;
	private Integer worldId;
	private Integer operator;
	private Date addDate;
	private String labelIds;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getWorldId() {
		return worldId;
	}
	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}
	public Integer getOperator() {
		return operator;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getLabelIds() {
		return labelIds;
	}
	public void setLabelIds(String labelIds) {
		this.labelIds = labelIds;
	}
}

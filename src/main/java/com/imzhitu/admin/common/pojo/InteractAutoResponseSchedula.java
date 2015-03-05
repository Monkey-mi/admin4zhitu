package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class InteractAutoResponseSchedula extends AbstractNumberDto implements Serializable{
	private static final long serialVersionUID = -3451975810611118529L;
	private Integer id;				//id
	private Integer autoResponseId;	//自动回复id
	private Date addDate;			//添加时间
	private Date modifyDate;		//最后修改时间
	private Integer operatorId;		//最后操作者id
	private String operatorName;	//最后操作者名字
	private Integer valid;			//有效性
	private Integer complete;		//完成情况
	private Date schedula;			//计划时间
	private Integer worldId;		//织图id
	
	private String currentComment;	//当前计划的评论
	private String preComment;		//前一条评论
	
	
	public String getCurrentComment() {
		return currentComment;
	}
	public void setCurrentComment(String currentComment) {
		this.currentComment = currentComment;
	}
	public String getPreComment() {
		return preComment;
	}
	public void setPreComment(String preComment) {
		this.preComment = preComment;
	}
	public Integer getWorldId() {
		return worldId;
	}
	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getSchedula() {
		return schedula;
	}
	public void setSchedula(Date schedula) {
		this.schedula = schedula;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAutoResponseId() {
		return autoResponseId;
	}
	public void setAutoResponseId(Integer autoResponseId) {
		this.autoResponseId = autoResponseId;
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
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public Integer getComplete() {
		return complete;
	}
	public void setComplete(Integer complete) {
		this.complete = complete;
	}
}

package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class InteractWorldLevelUserLevel extends AbstractNumberDto implements Serializable{
	private static final long serialVersionUID = -3239708809983534546L;
	private Integer worldLevelId;	//织图等级id
	private Integer userLevelId;	//用户等级id
	private Integer worldLevelWeight;//织图等级权重
	private Integer userLevelWeight;//用户等级权重
	private Integer worldId;		//织图id
	private Integer userId;			//用户id
	private Integer operatorId;		//操作者id
	private Date addDate;			//添加日期
	private Date modifyDate;		//操作日期
	private String operatorName;	//操作者名字
	private Integer valid;			//有效性
	private Integer id;				//id
	private String worldLevelDesc;	//织图等级描述
	private String userLevelDesc;	//用户等级描述
	
	
	
	public String getWorldLevelDesc() {
		return worldLevelDesc;
	}
	public void setWorldLevelDesc(String worldLevelDesc) {
		this.worldLevelDesc = worldLevelDesc;
	}
	public String getUserLevelDesc() {
		return userLevelDesc;
	}
	public void setUserLevelDesc(String userLevelDesc) {
		this.userLevelDesc = userLevelDesc;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
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
	public Integer getWorldId() {
		return worldId;
	}
	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getWorldLevelId() {
		return worldLevelId;
	}
	public void setWorldLevelId(Integer worldLevelId) {
		this.worldLevelId = worldLevelId;
	}
	public Integer getUserLevelId() {
		return userLevelId;
	}
	public void setUserLevelId(Integer userLevelId) {
		this.userLevelId = userLevelId;
	}
	public Integer getWorldLevelWeight() {
		return worldLevelWeight;
	}
	public void setWorldLevelWeight(Integer worldLevelWeight) {
		this.worldLevelWeight = worldLevelWeight;
	}
	public Integer getUserLevelWeight() {
		return userLevelWeight;
	}
	public void setUserLevelWeight(Integer userLevelWeight) {
		this.userLevelWeight = userLevelWeight;
	}
	
	
}

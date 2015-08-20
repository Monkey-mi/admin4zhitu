package com.imzhitu.admin.common.pojo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class InteractLikeFollowRecord extends AbstractNumberDto{
	private static final long serialVersionUID = -5170372730793763211L;
	private Integer id;
	private Integer userId;
	private Integer worldId;
	private Integer zombieId;
	private Integer type;	//类型，0表示互赞，1表示互粉
	private Integer complete;
	private Integer interactWorldCommentId;
	private Date addDate;
	private Date modifyDate;
	
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getWorldId() {
		return worldId;
	}
	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}
	public Integer getZombieId() {
		return zombieId;
	}
	public void setZombieId(Integer zombieId) {
		this.zombieId = zombieId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getComplete() {
		return complete;
	}
	public void setComplete(Integer complete) {
		this.complete = complete;
	}
	public Integer getInteractWorldCommentId() {
		return interactWorldCommentId;
	}
	public void setInteractWorldCommentId(Integer interactWorldCommentId) {
		this.interactWorldCommentId = interactWorldCommentId;
	}
	
}

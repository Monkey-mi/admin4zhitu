package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

public class OpActivityWorldCheckDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5677638080272069658L;
	/**
	 * 
	 */
	private Integer id;
	private Integer activityId;
	private String activityName;
	private Integer worldId;
	private Integer userId;
	private String userName;
	
	public OpActivityWorldCheckDto() {
		super();
	}
	
	public OpActivityWorldCheckDto(Integer id, Integer activityId, String activityName,
			Integer worldId, Integer userId, String userName) {
		super();
		this.id = id;
		this.activityId = activityId;
		this.activityName = activityName;
		this.worldId = worldId;
		this.userId = userId;
		this.userName = userName;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	
	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}

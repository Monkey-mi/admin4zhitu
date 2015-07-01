package com.imzhitu.admin.common.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;

public class OpZombieDegreeUserLevel extends AbstractNumberDto{
	private static final long serialVersionUID = -5344985637917698144L;
	private Integer id;
	private Integer zombieDegreeId;
	private String  zombieDegreeName;
	private Integer userLevelId;
	private String  userLevelName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getZombieDegreeId() {
		return zombieDegreeId;
	}
	public void setZombieDegreeId(Integer zombieDegreeId) {
		this.zombieDegreeId = zombieDegreeId;
	}
	public String getZombieDegreeName() {
		return zombieDegreeName;
	}
	public void setZombieDegreeName(String zombieDegreeName) {
		this.zombieDegreeName = zombieDegreeName;
	}
	public Integer getUserLevelId() {
		return userLevelId;
	}
	public void setUserLevelId(Integer userLevelId) {
		this.userLevelId = userLevelId;
	}
	public String getUserLevelName() {
		return userLevelName;
	}
	public void setUserLevelName(String userLevelName) {
		this.userLevelName = userLevelName;
	}
	
}

package com.imzhitu.admin.common.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;

public class InteractLikeFollowZombie extends AbstractNumberDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 760507713321820863L;
	private Integer id;
	private Integer zombieId;
	private String zombieName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getZombieId() {
		return zombieId;
	}
	public void setZombieId(Integer zombieId) {
		this.zombieId = zombieId;
	}
	public String getZombieName() {
		return zombieName;
	}
	public void setZombieName(String zombieName) {
		this.zombieName = zombieName;
	}
	
}

package com.imzhitu.admin.common.pojo;

import java.io.Serializable;


public class InteractWorldLabelDto implements Serializable{

	private static final long serialVersionUID = 2663700335228964869L;
	private Integer worldId;
	private Integer user_id;
	private Integer user_level_id;
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getUser_level_id() {
		return user_level_id;
	}
	public void setUser_level_id(Integer user_level_id) {
		this.user_level_id = user_level_id;
	}
	public void setWorldId(Integer worldId){
		this.worldId = worldId;
	}
	public Integer getWorldId(){
		return this.worldId;
	}
	
	
	public InteractWorldLabelDto(){
		super();
	}
	public InteractWorldLabelDto(Integer worldId, Integer user_id,
			Integer user_level_id) {
		super();
		this.worldId = worldId;
		this.user_id = user_id;
		this.user_level_id = user_level_id;
	}
	
	
	
}

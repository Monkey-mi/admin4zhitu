package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class UserLevelListDto implements Serializable{
	
	private static final long serialVersionUID = -6289135562731753856L;
	
	private Integer id;
	private Integer user_id;
	private Integer user_level_id;
	private Integer validity;
	private String user_name;
	private String level_description;
	private Date addDate;
	private Date modifyDate;
	private Integer operatorId;
	private String operatorName;
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public void setAddDate(Date addDate){
		this.addDate = addDate;
	}
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getAddDate(){
		return this.addDate;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public void setModifyDate(Date modifyDate){
		this.modifyDate = modifyDate;
	}
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getModifyDate(){
		return this.modifyDate;
	}
	
	public void setOperatorId(Integer operatorId){
		this.operatorId = operatorId;
	}
	public Integer getOperatorId(){
		return this.operatorId;
	}
	
	public void setOperatorName(String operatorName){
		this.operatorName = operatorName;
	}
	
	public String getOperatorName(){
		return this.operatorName;
	}
	
	public void setUser_name(String user_name){
		this.user_name =user_name;
	}
	public String getUser_name(){
		return this.user_name;
	}
	
	public void setLevel_description(String level_description){
		this.level_description =level_description;
	}
	public String getLevel_description(){
		return this.level_description;
	}
	
	public void setId(Integer id)
	{
		this.id = id;
	}
	public Integer getId(){
		return this.id;
	}
	
	public void setUser_id(Integer user_id){
		this.user_id = user_id;
	}
	public Integer getUser_id(){
		return this.user_id;
	}
	
	public void setUser_level_id(Integer user_level_id){
		this.user_level_id = user_level_id;
	}
	public Integer getUser_level_id(){
		return this.user_level_id;
	}
	
	public void setValidity(Integer validity){
		this.validity = validity;
	}
	public Integer getValidity(){
		return this.validity;
	}
	
	public UserLevelListDto(){
		super();
	}
	/*
	public UserLevelListDto(Integer id,Integer userId,Integer userLevelId,Integer validity){
		super();
		this.id = id;
		this.user_id = userId;
		this.user_level_id = userLevelId;
		this.validity = validity;
	}*/
	public UserLevelListDto(Integer id,Integer userId,Integer userLevelId,Integer validity,
			String userName,String levelDescription,
			Date addDate,Date modifyDate,Integer operatorId,String operatorName){
		super();
		this.id = id;
		this.user_id = userId;
		this.user_level_id = userLevelId;
		this.validity = validity;
		this.user_name =userName;
		this.level_description = levelDescription;
		this.addDate = addDate;
		this.modifyDate = modifyDate;
		this.operatorId = operatorId;
		this.operatorName = operatorName;
	}
	
}

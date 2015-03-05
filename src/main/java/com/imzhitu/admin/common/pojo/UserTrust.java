package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class UserTrust implements Serializable{
	private static final long serialVersionUID = -3714374609110918001L;
	private Integer id;	//id
	private Integer userId;//用户id
	private Date addDate;//添加时间
	private Date modifyDate;//最后修改时间
	private Integer trust;//信任标记，冗余字段
	private String operatorName;//操作者名称
	private Integer operatorId;//操作者id
	
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
	
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return this.id;
	}
	
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public Integer getUserId(){
		return this.userId;
	}
	
	public void setAddDate(Date addDate){
		this.addDate = addDate; 
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAddDate(){
		return this.addDate;
	}
	
	public void setModifyDate(Date modifyDate){
		this.modifyDate = modifyDate;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getModifyDate(){
		return this.modifyDate;
	}
	
	public void setTrust(Integer trust){
		this.trust = trust;
	}
	public Integer getTrust(){
		return this.trust;
	}
	
	public UserTrust(){
		super();
	}
	public UserTrust(Integer id,Integer userId,String operatorName,Date addDate,Date modifyDate,Integer trust,Integer operatorId){
		super();
		this.id = id;
		this.userId = userId;
		this.operatorName = operatorName;
		this.addDate = addDate ;
		this.modifyDate = modifyDate;
		this.trust = trust;
		this.operatorId = operatorId;
	}
	
	
}

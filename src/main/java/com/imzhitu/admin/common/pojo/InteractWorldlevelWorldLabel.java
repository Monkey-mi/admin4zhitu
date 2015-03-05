package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
public class InteractWorldlevelWorldLabel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2026944883761815718L;
	private  Integer id; 
	private  Integer worldId;
	private  Integer labelId;
	private  Integer complete;
	private  Date dateAdd;
	
	public void setId(Integer id ){
		this.id = id;
	}
	public Integer getId(){
		return this.id;
	}
	
	public void setWorldId(Integer worldId){
		this.worldId = worldId;
	}
	public Integer getWorldId(){
		return this.worldId;
	}
	
	public void setLabelId(Integer labelId){
		this.labelId = labelId;
	}
	public Integer getLabelId(){
		return this.labelId;
	}
	
	public void setComplete(Integer complete){
		this.complete = complete;
	}
	public Integer getComplete(){
		return this.complete;
	}
	
	public void setDateAdd(Date dateAdd){
		this.dateAdd = dateAdd;
	}
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDateAdd(){
		return this.dateAdd;
	}
	
	public InteractWorldlevelWorldLabel(){
		super();
	}
	
	public InteractWorldlevelWorldLabel(Integer id,Integer worldId,Integer labelId,Integer complete,Date dateAdd){
		super();
		this.id = id;
		this.worldId = worldId;
		this.labelId = labelId;
		this.complete = complete;
		this.dateAdd = dateAdd;
	}

}

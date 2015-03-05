package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 广场分类计划dto
 * @author zxx
 *
 */
public class HTWorldTypeWorldSchedulaDto implements Serializable{
	private static final long serialVersionUID = 8147800291893672592L;
	private Integer type_world_id;	//织图id
	private Date schedula;			//计划更新时间
	private Integer complete;		//完成标志，默认为0，0：未完成，1：已完成
	private Date addDate;			//添加时间
	private Date modifyDate;		//最后修改时间
	private Integer operatorId;		//操作者id
	private String operatorName;	//操作者名称
	
	public void setAddDate(Date dateAdd){
		this.addDate = dateAdd;
	}
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getAddDate(){
		return this.addDate;
	}
	
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
	
	public void setType_world_id(Integer type_world_id){
		this.type_world_id = type_world_id;
	}
	public Integer getType_world_id(){
		return this.type_world_id;
	}
	
	public void setSchedula(Date schedula){
		this.schedula = schedula;
	}
	
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getSchedula(){
		return this.schedula;
	}
	
	public void setComplete(Integer complete){
		this.complete = complete;
	}
	public Integer getComplete(){
		return this.complete;
	}
	
	public HTWorldTypeWorldSchedulaDto(){
		super();
	}
	public HTWorldTypeWorldSchedulaDto(
			Integer type_world_id,
			Date schedula,
			Date addDate,
			Date modifyDate,
			Integer operatorId,
			String operatorName,
			Integer complete){
		super();
		this.complete = complete;
		this.schedula = schedula;
		this.type_world_id = type_world_id;
		this.addDate = addDate;
		this.modifyDate = modifyDate;
		this.operatorId = operatorId;
		this.operatorName = operatorName;
	}
}

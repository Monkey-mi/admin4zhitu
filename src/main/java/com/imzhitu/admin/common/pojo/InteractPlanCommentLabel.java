package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class InteractPlanCommentLabel extends AbstractNumberDto implements Serializable{
	private static final long serialVersionUID = 7441200440828103963L;
	private Integer id;					//id
	private String description;			//标签描述
	private Date startTime;				//有效开始时间1971-1-1到
	private Date deadline;				//有效结束时间
	private Date workStartTime;				//工作时间,范围是00:00:00 到 23:59:59
	private Date workEndTime;
	private Integer valid;				//有效性
	private Date addDate;		//创建时间
	private Date modifyDate;	//最后修改时间
	private Integer operatorId;			//操作者id
	private Integer groupId;			//组id
	private String operatorName;		//操作者名称
	
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return this.id;
	}
	
	public void setDescription(String description ){
		this.description  = description;
	}
	public String getDescription(){
		return this.description;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getStartTime(){
		return this.startTime;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public void setDeadline(Date deadline){
		this.deadline = deadline;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getDeadline(){
		return this.deadline;
	}
	
	public void setWorkStartTime(Date workStartTime){
		this.workStartTime = workStartTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getWorkStartTime(){
		return this.workStartTime;
	}
	
	public void setWorkEndTime(Date workEndTime){
		this.workEndTime = workEndTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getWorkEndTime(){
		return this.workEndTime;
	}
	
	public void setValid(Integer valid){
		this.valid = valid;
	}
	public Integer getValid(){
		return this.valid;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public void setAddDate(Date addDate){
		this.addDate = addDate;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAddDate(){
		return this.addDate;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public void setModifyDate(Date modifyDate){
		this.modifyDate = modifyDate;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
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
	
	public void setGroupId(Integer grouoId){
		this.groupId = grouoId;
	}
	public Integer getGroupId(){
		return this.groupId;
	}
	
	public InteractPlanCommentLabel(){
		super();
	}
	
	public InteractPlanCommentLabel(
			Integer id,
			String description,
			Date startTime,
			Date deadline,
			Date workStartTime,
			Date workEndTime,
			Integer valid,
			Date addDate,
			Date modifyDate,
			Integer operatorId,
			String operatorName,
			Integer groupId
			){
		super();
		this.id = id;
		this.description = description;
		this.startTime = startTime;
		this.deadline = deadline;
		this.workStartTime = workStartTime;
		this.workEndTime = workEndTime;
		this.valid = valid;
		this.addDate = addDate;
		this.modifyDate = modifyDate;
		this.operatorId = operatorId;
		this.operatorName = operatorName;
		this.groupId = groupId;
	}

}

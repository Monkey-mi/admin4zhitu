package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

import com.imzhitu.admin.common.pojo.InteractWorldlevelWorldLabel;
import com.imzhitu.admin.common.pojo.InteractWorldlevelWorldComment;

import java.util.List;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class InteractWorldLevelListDto implements Serializable{
	private static final long serialVersionUID = -5241290791630955344L;
	private Integer world_id;//织图
	private Integer world_level_id;//织图等级id
	private Integer validity;//有效性
	private Date addDate;//添加时间
	private Date modifyDate;//最后修改时间
	private Integer operatorId;//操作者id
	private String operatorName;//操作者名字
	private List<InteractWorldlevelWorldLabel> worldLabelList;
	private List<InteractWorldlevelWorldComment> worldCommentList;
	
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public void setAddDate(Date addDate){
		this.addDate = addDate;
	}
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getAddDate(){
		return this.addDate;
	}
	
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
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
	
	public void setWorldLabelList(List<InteractWorldlevelWorldLabel> worldLabelList){
		this.worldLabelList = worldLabelList;
	}
	public List<InteractWorldlevelWorldLabel> getWorldLabelList(){
		return this.worldLabelList;
	}
	
	public void setWorldCommentList(List<InteractWorldlevelWorldComment> worldCommentList){
		this.worldCommentList = worldCommentList;
	}
	public List<InteractWorldlevelWorldComment> getWorldCommentList(){
		return this.worldCommentList;
	}
	
	//为了兼容
	public void setId(Integer id){
		this.world_id = id;
	}
	public Integer getId(){
		return this.world_id;
	}
		
	public void setValidity(Integer validity){
		this.validity = validity;
	}
	public Integer getValidity(){
		return this.validity;
	}
	public void setWorld_id(Integer world_id){
		this.world_id = world_id;
	}
	public Integer getWorld_id(){
		return this.world_id;
	}
	public void setWorld_level_id(Integer world_level_id){
		this.world_level_id = world_level_id;
	}
	public Integer getWorld_level_id(){
		return this.world_level_id;
	}
	public InteractWorldLevelListDto(){
		super();
	}
	public InteractWorldLevelListDto(Integer world_id,Integer world_level_id,Integer validity,Date addDate,Date modifyDate,Integer operatorId,String operatorName){
		super();
		this.world_id = world_id;
		this.world_level_id = world_level_id;
		this.validity = validity;
		this.addDate = addDate;
		this.modifyDate = modifyDate;
		this.operatorId = operatorId;
		this.operatorName = operatorName;
	}
}

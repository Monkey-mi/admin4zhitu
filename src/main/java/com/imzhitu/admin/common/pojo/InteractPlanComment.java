package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class InteractPlanComment extends AbstractNumberDto implements Serializable{
	private static final long serialVersionUID = -1890104187601293549L;
	private Integer id;			//id
	private Integer groupId;	//分组id
	private String groupName;	//分组名
	private String content;		//评论内容
	private Date addDate;		//增加时间
	private Date modifyDate;	//最后修改时间
	private Integer operatorId;	//操作者id
	private String operatorName;//操作者姓名
	private Integer valid;		//有效性
	private Integer interactCommentId;//对应互动评论表中的id，目的是冗余数据，使得互动计划的实施能够顺利进行
	
	public Integer getInteractCommentId() {
		return interactCommentId;
	}
	public void setInteractCommentId(Integer interactCommentId) {
		this.interactCommentId = interactCommentId;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return this.id;
	}
	
	public void setGroupId(Integer groupId){
		this.groupId = groupId;
	}
	public Integer getGroupId(){
		return this.groupId;
	}
	
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}
	public String getGroupName(){
		return this.groupName;
	}
	
	public String getContent(){
		return this.content;
	}
	public void setContent(String content){
		this.content = content;
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
	
	public void setValid(Integer valid){
		this.valid = valid;
	}
	public Integer getValid(){
		return this.valid;
	}
	
	public InteractPlanComment(){
		super();
	}
	
	public InteractPlanComment(
			Integer id,
			Integer groupId,
			String groupName,
			String content,
			Date addDate,
			Date modifyDate,
			Integer operatorId,
			String operatorName,
			Integer valid,
			Integer interactCommentId
			){
		super();
		this.id = id;
		this.groupId = groupId;
		this.groupName = groupName;
		this.content = content;
		this.addDate = addDate;
		this.modifyDate = modifyDate;
		this.operatorId = operatorId;
		this.operatorName = operatorName;
		this.valid = valid;
		this.interactCommentId = interactCommentId;
	}
	

}

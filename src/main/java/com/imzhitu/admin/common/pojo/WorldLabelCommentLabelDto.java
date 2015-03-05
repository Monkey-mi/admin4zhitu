package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

public class WorldLabelCommentLabelDto implements Serializable{
	private static final long serialVersionUID = -2834786977294621522L;
	private Integer id;
	private Integer world_label_id;
	private Integer comment_label_id;
	private String label_name;//织图标签
	private String commentLabelName;//评论标签
	
	public void setCommentLabelName(String commentLabelName){
		this.commentLabelName = commentLabelName;
	}
	public String getCommentLabelName(){
		return this.commentLabelName;
	}
	
	public void setLabel_name(String label_name){
		this.label_name = label_name;
	}
	public String getLabel_name(){
		return this.label_name;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return this.id;
	}
	
	public void setWorld_label_id(Integer world_label_id){
		this.world_label_id = world_label_id;
	}
	public Integer getWorld_label_id(){
		return this.world_label_id;
	}
	
	public void setComment_label_id(Integer comment_label_id){
		this.comment_label_id = comment_label_id;
	}
	public Integer getComment_label_id(){
		return this.comment_label_id;
	}
	
	public WorldLabelCommentLabelDto(){
		super();
	}
	
	public WorldLabelCommentLabelDto(Integer id,Integer world_label_id,Integer comment_label_id,String label_name,String commentLabelName){
		super();
		this.id = id;
		this.world_label_id = world_label_id;
		this.comment_label_id = comment_label_id;
		this.label_name =label_name;
		this.commentLabelName = commentLabelName;
	}
}

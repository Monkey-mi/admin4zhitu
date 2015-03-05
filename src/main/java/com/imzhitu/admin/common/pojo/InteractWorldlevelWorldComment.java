package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class InteractWorldlevelWorldComment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -499496216603559639L;
	private  Integer id; 
	private  Integer worldId;
	private  Integer commentId;
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
	
	public void setCommentId(Integer commentId){
		this.commentId = commentId;
	}
	public Integer getCommentId(){
		return this.commentId;
	}
	
	public void setComplete(Integer complete){
		this.complete = complete;
	}
	public Integer getComplete(){
		return this.complete;
	}
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public void setDateAdd(Date dateAdd){
		this.dateAdd = dateAdd;
	}
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDateAdd(){
		return this.dateAdd;
	}
	
	public InteractWorldlevelWorldComment(){
		super();
	}
	public InteractWorldlevelWorldComment(Integer worldId,Integer commentId,Integer complete){
		super();
		this.worldId = worldId;
		this.commentId = commentId;
		this.complete = complete;
	}
	public InteractWorldlevelWorldComment(Integer id,Integer worldId,Integer commentId,Integer complete,Date dateAdd){
		super();
		this.id = id;
		this.worldId = worldId;
		this.commentId = commentId;
		this.complete = complete;
		this.dateAdd = dateAdd;
	}


}

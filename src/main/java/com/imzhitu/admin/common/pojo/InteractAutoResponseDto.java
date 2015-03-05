package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Value;

import com.hts.web.common.pojo.AbstractNumberDto;

public class InteractAutoResponseDto extends AbstractNumberDto implements Serializable{
	private static final long serialVersionUID = 2517163255919123042L;
	

	private Integer id;//自动回复的id
	private Integer worldId;//织图id
	private Integer responseId;//回复评论id
	private Integer commentId;//被评论的id
	private Integer complete;//完成情况
	private String worldURL;//短链
	private Integer author;//回复者id
	private String authorName;//回复者姓名
	private Integer reAuthor;//被回复者id
	private String reAuthorName;//被回复者姓名
	private String context;//回复内容
	private String reContext;//被回复内容
	private Date commentDate;//评论时间
	private Integer worldAuthorId;
	private String preComment;//上上次评论内容
	private Integer preReId;	//上上次评论id
	private Integer userLevelId;	//用户等级
	private String shortLink;

	public String getShortLink() {
		return shortLink;
	}
	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	public Integer getUserLevelId() {
		return userLevelId;
	}
	public void setUserLevelId(Integer userLevelId) {
		this.userLevelId = userLevelId;
	}
	public String getPreComment() {
		return preComment;
	}
	public void setPreComment(String preComment) {
		this.preComment = preComment;
	}
	public Integer getPreReId() {
		return preReId;
	}
	public void setPreReId(Integer preReId) {
		this.preReId = preReId;
	}
	public void setWorldAuthorId(Integer worldAuthorId){
		this.worldAuthorId = worldAuthorId;
	}
	public Integer getWorldAuthorId(){
		return this.worldAuthorId;
	}
	
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public void setCommentDate(Date commentDate){
		this.commentDate = commentDate;
	}
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCommentDate(){
		return this.commentDate;
	}
	
	public void setWorldId(Integer worldId){
		this.worldId = worldId;
	}
	public Integer getWorldId(){
		return this.worldId;
	}
	
	public void setReContext(String reContext){
		this.reContext = reContext;
	}
	public String getReContext(){
		return this.reContext;
	}
	
	public void setContext(String context){
		this.context = context;
	}
	public String getContext(){
		return this.context;
	}
	
	public void setReAuthorName(String reAuthorName){
		this.reAuthorName = reAuthorName;
	}
	public String getReAuthorName(){
		return this.reAuthorName;
	}
	
	public void setReAuthor(Integer reAuthor){
		this.reAuthor = reAuthor;
	}
	public Integer getReAuthor(){
		return this.reAuthor;
	}
	
	public void setAuthorName(String authorName){
		this.authorName = authorName;
	}
	public String getAuthorName(){
		return this.authorName;
	}
	
	public void setAuthor(Integer author){
		this.author = author;
	}
	public Integer getAuthor(){
		return this.author;
	}
	
	
	public void setWorldURL(String worldURL){
		this.worldURL =  worldURL;
	}
	public String getWorldURL(){
		return this.worldURL;
	}
	
	
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return this.id;
	}
	
	public void setResponseId(Integer responseId){
		this.responseId = responseId;
	}
	public Integer getResponseId(){
		return this.responseId;
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
	
	public InteractAutoResponseDto(){
		super();
	}

	
	public InteractAutoResponseDto(
			Integer id,
			Integer worldId,
			Integer responseId,
			Integer commentId,
			Integer complete,
			String worldURL,
			Integer author,
			String authorName,
			Integer reAuthor,
			String reAuthorName,
			String context,
			String reContext,
			Date commentDate
			){
		super();
		this.id = id;
		this.worldId = worldId;
		this.responseId = responseId;
		this.commentId = commentId;
		this.complete = complete;
		this.author = author;
		this.worldURL = worldURL;
		this.authorName = authorName;
		this.reAuthor = reAuthor;
		this.reAuthorName = reAuthorName;
		this.context = context;
		this.reContext = reContext;
		this.commentDate = commentDate;
	}
	
	public InteractAutoResponseDto(
			Integer id,
			Integer responseId,
			String authorName,
			String context,
			Date commentDate){
		super();
		this.id = id;
		this.responseId = responseId;
		this.authorName = authorName;
		this.context = context;
		this.commentDate = commentDate;
	}
	
	public InteractAutoResponseDto(
			Integer responseId,
			Integer author_id,
			String content,
			Integer world_id,
			Integer world_author_id,
			Integer re_author_id,
			String authorName
			){
		super();
		this.responseId = responseId;
		this.author = author_id;
		this.worldId = world_id;
		this.context = content;
		this.worldAuthorId = world_author_id;
		this.reAuthor = re_author_id;
		this.authorName = authorName;
	}
}

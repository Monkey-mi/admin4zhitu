package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class UserZombieHtworld extends AbstractNumberDto implements Serializable{
	private static final long serialVersionUID = -1279650611258692691L;
	private Integer worldId;		//织图id
	private Integer userId;			//用户id
	private Date createDate;		//创建时间
	private String shortLink;		//短链
	private String titlePath;		//封面图片路径
	private Date afterDate;			//查询条件，用于在此之后的织图
	private Integer commentId;		//评论id
	
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public Date getAfterDate() {
		return afterDate;
	}
	public void setAfterDate(Date afterDate) {
		this.afterDate = afterDate;
	}
	public Integer getWorldId() {
		return worldId;
	}
	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getShortLink() {
		return shortLink;
	}
	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	public String getTitlePath() {
		return titlePath;
	}
	public void setTitlePath(String titlePath) {
		this.titlePath = titlePath;
	}

}

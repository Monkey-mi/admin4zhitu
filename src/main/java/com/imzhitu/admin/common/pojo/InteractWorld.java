package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class InteractWorld implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 831165207866323108L;

	private Integer id;
	private Integer worldId;
	private Integer clickCount;
	private Integer commentCount;
	private Integer likedCount;
	private Integer duration;
	private Date dateAdded;
	private Integer valid;

	public InteractWorld() {
		super();
	}

	public InteractWorld(Integer id, Integer worldId, Integer clickCount,
			Integer commentCount, Integer likedCount, Integer duration,
			Date dateAdded, Integer valid) {
		this.id = id;
		this.worldId = worldId;
		this.clickCount = clickCount;
		this.commentCount = commentCount;
		this.likedCount = likedCount;
		this.duration = duration;
		this.dateAdded = dateAdded;
		this.valid = valid;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getLikedCount() {
		return likedCount;
	}

	public void setLikedCount(Integer likedCount) {
		this.likedCount = likedCount;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

}

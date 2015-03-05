package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 用户关注互动POJO
 * </p>
 * 
 * 创建时间:2014-2-19
 * @author tianjie
 *
 */
public class InteractUserFollow implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -637454657932692436L;
	
	private Integer id;
	private Integer interactId;
	private Integer userId;
	private Integer followId;
	private Date dateAdded;
	private Date dateSchedule;
	private Integer valid;
	private Integer finished;
	
	public InteractUserFollow() {
		super();
	}
	
	public InteractUserFollow(Integer id, Integer interactId, Integer userId,
			Integer followId, Date dateAdded, Date dateSchedule, Integer valid,
			Integer finished) {
		super();
		this.id = id;
		this.interactId = interactId;
		this.userId = userId;
		this.followId = followId;
		this.dateAdded = dateAdded;
		this.dateSchedule = dateSchedule;
		this.valid = valid;
		this.finished = finished;
	}
	
	public InteractUserFollow(Integer interactId, Integer userId,
			Integer followId, Date dateAdded, Date dateSchedule, Integer valid,
			Integer finished) {
		super();
		this.interactId = interactId;
		this.userId = userId;
		this.followId = followId;
		this.dateAdded = dateAdded;
		this.dateSchedule = dateSchedule;
		this.valid = valid;
		this.finished = finished;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInteractId() {
		return interactId;
	}

	public void setInteractId(Integer interactId) {
		this.interactId = interactId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFollowId() {
		return followId;
	}

	public void setFollowId(Integer followId) {
		this.followId = followId;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getDateSchedule() {
		return dateSchedule;
	}

	public void setDateSchedule(Date dateSchedule) {
		this.dateSchedule = dateSchedule;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getFinished() {
		return finished;
	}

	public void setFinished(Integer finished) {
		this.finished = finished;
	}
	
}

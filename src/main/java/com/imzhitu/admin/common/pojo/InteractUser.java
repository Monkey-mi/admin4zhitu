package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 用户互动POJO
 * </p>
 * 
 * 创建时间：2014-2-19
 * 
 * @author tianjie
 * 
 */
public class InteractUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3236453857990224511L;

	private Integer id;
	private Integer userId;
	private Integer followCount = 0;
	private Integer duration;
	private Date dateAdded;
	private Integer valid;

	public InteractUser() {
		super();
	}

	public InteractUser(Integer id, Integer userId, Integer followCount,
			Integer duration, Date dateAdded, Integer valid) {
		super();
		this.id = id;
		this.userId = userId;
		this.followCount = followCount;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
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

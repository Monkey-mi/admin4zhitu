package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class InteractWorldLiked implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8967113508033777506L;

	private Integer id;
	private Integer interactId;
	private Integer worldId;
	private Integer userId;
	private Date dateAdded;
	private Date dateSchedule;
	private Integer valid;
	private Integer finished;
	
	public InteractWorldLiked(Integer id, Integer interactId, Integer worldId, Integer userId,
			Date dateAdded, Date dateSchedule, Integer valid, Integer finished) {
		this.id = id;
		this.interactId = interactId;
		this.worldId = worldId;
		this.userId = userId;
		this.dateAdded = dateAdded;
		this.dateSchedule = dateSchedule;
		this.valid = valid;
		this.finished = finished;
	}
	
	public InteractWorldLiked(Integer interactId, Integer worldId, Integer userId, Date dateAdded,
			Date dateSchedule, Integer valid, Integer finished) {
		this.interactId = interactId;
		this.worldId = worldId;
		this.userId = userId;
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

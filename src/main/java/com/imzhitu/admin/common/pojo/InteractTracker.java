package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 互动跟踪POJO
 * </p>
 * 
 * 创建时间：2014-2-27
 * 
 * @author tianjie
 * 
 */
public class InteractTracker implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5837293221112774814L;

	private Integer id;
	private String interactDesc;
	private Integer interactStep;
	private Integer interactBegin;
	private Integer interactStop;
	private Date lastInteractDate;
	private Date lastTrackDate;
	private Integer valid = Tag.FALSE; // 重置标志位

	public InteractTracker() {
		super();
	}

	public InteractTracker(Integer id, String interactDesc,
			Integer interactStep, Integer interactBegin, Integer interactStop,
			Date lastInteractDate, Date lastTrackDate, Integer valid) {
		super();
		this.id = id;
		this.interactDesc = interactDesc;
		this.interactStep = interactStep;
		this.interactBegin = interactBegin;
		this.interactStop = interactStop;
		this.lastInteractDate = lastInteractDate;
		this.lastTrackDate = lastTrackDate;
		this.valid = valid;
	}

	public InteractTracker(String interactDesc,
			Integer interactStep, Integer interactBegin, Integer interactStop,
			Date lastInteractDate, Date lastTrackDate, Integer valid) {
		super();
		this.interactDesc = interactDesc;
		this.interactStep = interactStep;
		this.interactBegin = interactBegin;
		this.interactStop = interactStop;
		this.lastInteractDate = lastInteractDate;
		this.lastTrackDate = lastTrackDate;
		this.valid = valid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInteractDesc() {
		return interactDesc;
	}

	public void setInteractDesc(String interactDesc) {
		this.interactDesc = interactDesc;
	}

	public Integer getInteractStep() {
		return interactStep;
	}

	public void setInteractStep(Integer interactStep) {
		this.interactStep = interactStep;
	}

	public Integer getInteractBegin() {
		return interactBegin;
	}

	public void setInteractBegin(Integer interactBegin) {
		this.interactBegin = interactBegin;
	}

	public Integer getInteractStop() {
		return interactStop;
	}

	public void setInteractStop(Integer interactStop) {
		this.interactStop = interactStop;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getLastInteractDate() {
		return lastInteractDate;
	}

	public void setLastInteractDate(Date lastInteractDate) {
		this.lastInteractDate = lastInteractDate;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getLastTrackDate() {
		return lastTrackDate;
	}

	public void setLastTrackDate(Date lastTrackDate) {
		this.lastTrackDate = lastTrackDate;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

}

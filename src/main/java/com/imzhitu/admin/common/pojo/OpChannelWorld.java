package com.imzhitu.admin.common.pojo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class OpChannelWorld extends AbstractNumberDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5226004933986221804L;

	private Integer id;
	private Integer channelId;
	private Integer worldId;
	private Integer authorId;
	private Date dateAdded;
	private Integer valid;
	private Integer notified;
	private String channelName;
	private Integer superb;
	private Integer weight;
	private Integer serial;
	private Integer superbSerial;
	
	public Integer getSuperb() {
		return superb;
	}

	public void setSuperb(Integer superb) {
		this.superb = superb;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getNotified() {
		return notified;
	}

	public void setNotified(Integer notified) {
		this.notified = notified;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Integer getSuperbSerial() {
		return superbSerial;
	}

	public void setSuperbSerial(Integer superbSerial) {
		this.superbSerial = superbSerial;
	}
	
}

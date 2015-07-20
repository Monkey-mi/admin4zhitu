package com.imzhitu.admin.common.pojo;

import java.util.Date;

import com.hts.web.common.pojo.AbstractNumberDto;

public class InteractChannelWorldLabel extends AbstractNumberDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2685553580565283879L;
	private Integer id;
	private Integer channelId;
	private Integer operator;
	private Integer worldId;
	private String label_ids;
	private Date addDate;
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
	public Integer getOperator() {
		return operator;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
	public Integer getWorldId() {
		return worldId;
	}
	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}
	public String getLabel_ids() {
		return label_ids;
	}
	public void setLabel_ids(String label_ids) {
		this.label_ids = label_ids;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
}

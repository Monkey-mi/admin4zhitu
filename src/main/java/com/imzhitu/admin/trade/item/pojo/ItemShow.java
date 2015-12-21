package com.imzhitu.admin.trade.item.pojo;

import java.io.Serializable;

import com.hts.web.common.pojo.AbstractNumberDto;

public class ItemShow extends AbstractNumberDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4457708669894649617L;
	
	private Integer id;

	private Integer itemSetId;
	
	private Integer worldId;
	
	private Integer serial;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemSetId() {
		return itemSetId;
	}

	public void setItemSetId(Integer itemSetId) {
		this.itemSetId = itemSetId;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	
	
}

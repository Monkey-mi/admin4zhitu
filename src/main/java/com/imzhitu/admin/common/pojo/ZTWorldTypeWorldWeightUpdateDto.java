package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

import com.hts.web.common.pojo.AbstractNumberDto;

public class ZTWorldTypeWorldWeightUpdateDto extends AbstractNumberDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1195253947289038053L;

	private Integer id;
	private Integer typeWorldId;
	private long endTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTypeWorldId() {
		return typeWorldId;
	}
	public void setTypeWorldId(Integer typeWorldId) {
		this.typeWorldId = typeWorldId;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	
}

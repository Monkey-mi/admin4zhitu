package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 活动织图审核状态POJO
 * </p>
 * 
 * 创建时间：
 * @author lynch
 *
 */
public class OpActivityWorldValidDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7813572770320111378L;
	private Integer worldId;
	private Integer valid;
	
	public OpActivityWorldValidDto() {
		super();
	}
	
	public OpActivityWorldValidDto(Integer worldId, Integer valid) {
		super();
		this.worldId = worldId;
		this.valid = valid;
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

}

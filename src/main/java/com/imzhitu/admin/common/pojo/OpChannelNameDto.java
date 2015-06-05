package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 频道名称POJO
 * </p>
 * 
 * 创建时间: 2015-06-04
 * @author lynch
 *
 */
public class OpChannelNameDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 121901096230073784L;
	private Integer worldId;
	private String channelName;

	
	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

}

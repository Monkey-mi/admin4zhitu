package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 频道封面POJO
 * </p>
 * 
 * 创建时间:2015-02-09
 * @author lynch
 *
 */
public class OpChannelCover implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 775480382533839163L;
	private Integer id;
	private Integer channelId;
	private Integer worldId;

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

}

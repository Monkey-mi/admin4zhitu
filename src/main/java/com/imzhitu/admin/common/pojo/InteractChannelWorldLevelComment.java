package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

/**
 * 表interact_channel_world_level_comment的映射类，频道织图互动记录评论的相关表
 * @author zhangbo	2015年10月29日
 *
 */
public class InteractChannelWorldLevelComment implements Serializable {

	/**
	 * 序列号
	 * @author zhangbo	2015年10月29日
	 */
	private static final long serialVersionUID = -7910356799794943241L;
	
	/**
	 * 主键id
	 * @author zhangbo	2015年10月29日
	 */
	private Integer id;
	
	/**
	 * 频道id
	 * @author zhangbo	2015年10月29日
	 */
	private Integer channelId;
	
	/**
	 * 织图id
	 * @author zhangbo	2015年10月29日
	 */
	private Integer worldId;
	
	/**
	 * 评论id
	 * @author zhangbo	2015年10月29日
	 */
	private Integer commentId;
	
	/**
	 * 完成标志，完成：1，未完成：0
	 * @author zhangbo	2015年10月29日
	 */
	private Integer complete;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the channelId
	 */
	public Integer getChannelId() {
		return channelId;
	}

	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	/**
	 * @return the worldId
	 */
	public Integer getWorldId() {
		return worldId;
	}

	/**
	 * @param worldId the worldId to set
	 */
	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	/**
	 * @return the commentId
	 */
	public Integer getCommentId() {
		return commentId;
	}

	/**
	 * @param commentId the commentId to set
	 */
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	/**
	 * @return the complete
	 */
	public Integer getComplete() {
		return complete;
	}

	/**
	 * @param complete the complete to set
	 */
	public void setComplete(Integer complete) {
		this.complete = complete;
	}
	
}

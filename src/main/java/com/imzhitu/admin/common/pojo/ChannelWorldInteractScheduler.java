package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 表interact_channel_world_scheduler的映射类，频道织图规划的互动数据表
 * 
 * @author zhangbo	2015年10月29日
 *
 */
public class ChannelWorldInteractScheduler implements Serializable {
	
	/**
	 * 序列号
	 * @author zhangbo	2015年10月30日
	 */
	private static final long serialVersionUID = -2109822430198408178L;

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
	 * 计划执行时间
	 * @author zhangbo	2015年10月29日
	 */
	private Date scheduleDate;
	
	/**
	 * 操作者，即管理员账号id
	 * @author zhangbo	2015年10月29日
	 */
	private Integer operator;
	
	/**
	 * 完成标志，完成：1，未完成：0
	 * @author zhangbo	2015年10月29日
	 */
	private Integer complete;

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
	 * @return the scheduleDate
	 */
	public Date getScheduleDate() {
		return scheduleDate;
	}

	/**
	 * @param scheduleDate the scheduleDate to set
	 */
	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	/**
	 * @return the operator
	 */
	public Integer getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(Integer operator) {
		this.operator = operator;
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

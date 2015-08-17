package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

/**
 * 频道成员数据传输对象 继承与userInfo，可携带成员user相关联信息 此DTO其中包含了频道红人信息
 * 
 * @author zhangbo 2015年8月17日
 *
 */
public class OpChannelMemberDto extends UserInfoBase implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1992889803568935357L;

	/**
	 * 频道成员表主键id
	 * 
	 * @author zhangbo 2015年8月14日
	 */
	private Integer channelMemberId;

	/**
	 * 频道id
	 * 
	 * @author zhangbo 2015年8月14日
	 */
	private Integer channelId;

	/**
	 * 用户id 与父类UserInfoBase中id一致，主要是为了方便记录
	 * 
	 * @author zhangbo 2015年8月14日
	 */
	private Integer userId;

	/**
	 * 订阅频道时间
	 * 
	 * @author zhangbo 2015年8月14日
	 */
	private Long subTime;

	/**
	 * 用户在频道当中的等级 成员等级分为：群主：0，管理员：1，普通成员：2
	 * 
	 * @author zhangbo 2015年8月14日
	 */
	private Integer degree;

	/**
	 * 是否推送通知 不通知：0，通知：1
	 * 
	 * @author zhangbo 2015年8月14日
	 */
	private Integer notified;

	/**
	 * 是否屏蔽 不屏蔽：0，屏蔽：1
	 * 
	 * @author zhangbo 2015年8月14日
	 */
	private Integer shield;

	/**
	 * 序号，用于排序，序号根据特定sequence流水而来
	 * 
	 * @author zhangbo 2015年8月14日
	 */
	private Integer serial;

	/**
	 * 是否为频道红人 是红人：1， 不是红人：0
	 * 
	 * @author zhangbo 2015年8月17日
	 */
	private Integer channelStar;

	/**
	 * @return the channelMemberId
	 */
	public Integer getChannelMemberId() {
		return channelMemberId;
	}

	/**
	 * @param channelMemberId
	 *            the channelMemberId to set
	 */
	public void setChannelMemberId(Integer channelMemberId) {
		this.channelMemberId = channelMemberId;
	}

	/**
	 * @return the channelId
	 */
	public Integer getChannelId() {
		return channelId;
	}

	/**
	 * @param channelId
	 *            the channelId to set
	 */
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the subTime
	 */
	public Long getSubTime() {
		return subTime;
	}

	/**
	 * @param subTime
	 *            the subTime to set
	 */
	public void setSubTime(Long subTime) {
		this.subTime = subTime;
	}

	/**
	 * @return the degree
	 */
	public Integer getDegree() {
		return degree;
	}

	/**
	 * @param degree
	 *            the degree to set
	 */
	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	/**
	 * @return the notified
	 */
	public Integer getNotified() {
		return notified;
	}

	/**
	 * @param notified
	 *            the notified to set
	 */
	public void setNotified(Integer notified) {
		this.notified = notified;
	}

	/**
	 * @return the shield
	 */
	public Integer getShield() {
		return shield;
	}

	/**
	 * @param shield
	 *            the shield to set
	 */
	public void setShield(Integer shield) {
		this.shield = shield;
	}

	/**
	 * @return the serial
	 */
	public Integer getSerial() {
		return serial;
	}

	/**
	 * @param serial
	 *            the serial to set
	 */
	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	/**
	 * @return the channelStar
	 */
	public boolean isChannelStar() {
		return channelStar == 1 ? true : false;
	}

	/**
	 * @param channelStar the channelStar to set
	 */
	public void setChannelStar(Integer channelStar) {
		this.channelStar = channelStar;
	}

}

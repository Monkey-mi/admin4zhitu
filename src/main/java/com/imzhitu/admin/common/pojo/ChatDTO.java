package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * 骚扰私聊数据传输对象
 * 
 * @author zhangbo 2015年8月3日
 *
 */
public class ChatDTO extends AbstractNumberDto implements Serializable {

	/**
	 * 序列号
	 * 
	 * @author zhangbo 2015年8月3日
	 */
	private static final long serialVersionUID = -2747660084868740362L;

	/**
	 * 骚扰私聊记录表主键id
	 * 
	 * @author zhangbo 2015年8月3日
	 */
	private Integer id;

	/**
	 * 发送私聊用户id
	 * 
	 * @author zhangbo 2015年8月3日
	 */
	private Integer uId;

	/**
	 * 发送私聊用户的名称
	 * 
	 * @author zhangbo 2015年8月3日
	 */
	private String uName;

	/**
	 * 接收私聊用户的id
	 * 
	 * @author zhangbo 2015年8月3日
	 */
	private Integer tId;

	/**
	 * 接收私聊用户的名称
	 * 
	 * @author zhangbo 2015年8月3日
	 */
	private String tName;

	/**
	 * 用户关系，-1：未关注，0：已关注，1：互相关注
	 * 
	 * @author zhangbo 2015年8月3日
	 */
	private Integer relationship;

	/**
	 * 私聊消息内容
	 * 
	 * @author zhangbo 2015年8月3日
	 */
	private String msg;

	/**
	 * 发送私聊的时间
	 * 
	 * @author zhangbo 2015年8月3日
	 */
	private Date date;
	
	/**
	 * 发私聊用户是否被屏蔽
	 * 
	 * @author zhangbo	2015年8月3日
	 */
	private Integer userShield;

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
	 * @return the uId
	 */
	public Integer getuId() {
		return uId;
	}

	/**
	 * @param uId the uId to set
	 */
	public void setuId(Integer uId) {
		this.uId = uId;
	}

	/**
	 * @return the uName
	 */
	public String getuName() {
		return uName;
	}

	/**
	 * @param uName the uName to set
	 */
	public void setuName(String uName) {
		this.uName = uName;
	}

	/**
	 * @return the tId
	 */
	public Integer gettId() {
		return tId;
	}

	/**
	 * @param tId the tId to set
	 */
	public void settId(Integer tId) {
		this.tId = tId;
	}

	/**
	 * @return the tName
	 */
	public String gettName() {
		return tName;
	}

	/**
	 * @param tName the tName to set
	 */
	public void settName(String tName) {
		this.tName = tName;
	}

	/**
	 * @return the relationship
	 */
	public Integer getRelationship() {
		return relationship;
	}

	/**
	 * @param relationship the relationship to set
	 */
	public void setRelationship(Integer relationship) {
		this.relationship = relationship;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the userShield
	 */
	public Integer getUserShield() {
		return userShield;
	}

	/**
	 * @param userShield the userShield to set
	 */
	public void setUserShield(Integer userShield) {
		this.userShield = userShield;
	}

}

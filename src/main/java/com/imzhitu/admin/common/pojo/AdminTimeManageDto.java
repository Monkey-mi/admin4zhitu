package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class AdminTimeManageDto extends AbstractNumberDto implements Serializable{
	/**
	 * userNameId:管理员的id
	 * userName:管理员的姓名
	 * startTime:开始时间
	 * endTime:结束时间
	 * updateTime:更新时间
	 * operatorId:操作者ID
	 * operatorName:操作者ID
	 */

	private int userNameId;
	private Time startTime;
	private Time endTime;
	private Date updateTime;
	private int operatorId;
	
	private String userName;
	private String operatorName;
	
	private static final long serialVersionUID = 2880516048588819039L;

	public int getUserNameId() {
		return userNameId;
	}
	public void setUserNameId(int userNameId) {
		this.userNameId = userNameId;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return this.startTime;
	}
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return this.endTime;
	}
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return this.updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public AdminTimeManageDto(int userNameId, Time startTime, Time endTime, Date updateTime, int operatorId,
			String userName, String operatorName) {
		super();
		this.userNameId = userNameId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.updateTime = updateTime;
		this.operatorId = operatorId;
		this.userName = userName;
		this.operatorName = operatorName;
	}
	
	public AdminTimeManageDto() {
		super();
		}
}

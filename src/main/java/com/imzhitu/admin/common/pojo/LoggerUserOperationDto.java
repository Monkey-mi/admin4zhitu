package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class LoggerUserOperationDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7585962285999246904L;
	private Integer id;
	private String optArgs;
	private Date optDate;
	private Integer optId;
	private String optInterface;
	private String optName;
	private String optDesc;
	private Integer userId;
	private String userName;
	
	
	public LoggerUserOperationDto() {
		super();
	}
	
	public LoggerUserOperationDto(Integer id, String optArgs, Date optDate, Integer optId,
			String optInterface, String optName, String optDesc,
			Integer userId, String userName) {
		super();
		this.id = id;
		this.optArgs = optArgs;
		this.optDate = optDate;
		this.optId = optId;
		this.optInterface = optInterface;
		this.optName = optName;
		this.optDesc = optDesc;
		this.userId = userId;
		this.userName = userName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getOptId() {
		return optId;
	}

	public void setOptId(Integer optId) {
		this.optId = optId;
	}
	
	public String getOptArgs() {
		return optArgs;
	}

	public void setOptArgs(String optArgs) {
		this.optArgs = optArgs;
	}
	
	public String getOptInterface() {
		return optInterface;
	}

	public void setOptInterface(String optInterface) {
		this.optInterface = optInterface;
	}

	public String getOptName() {
		return optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public String getOptDesc() {
		return optDesc;
	}

	public void setOptDesc(String optDesc) {
		this.optDesc = optDesc;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getOptDate() {
		return optDate;
	}

	public void setOptDate(Date optDate) {
		this.optDate = optDate;
	}
	
	
	
}

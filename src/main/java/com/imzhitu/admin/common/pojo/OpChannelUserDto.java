package com.imzhitu.admin.common.pojo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class OpChannelUserDto extends AbstractNumberDto{

	private static final long serialVersionUID = 7905752089313744651L;

	private Integer id;			//id
	private Integer userId;		//用户id
	private String userName;	//用户名字
	private Date registerDate;	//注册时间,冗余,方便查询
	private Integer valid;		//有效性
	private Integer channelId;	//频道id
	private Integer operatorId;	//推荐该用户为频道用户的管理员id
	private String operatorName;//管理员名称
	private Date addDate;		//添加时间
	private Date modifyDate;	//最后修改时间
	private String channelName;	//频道描述
	private Date lastWorldDate;	//最后发图时间
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getLastWorldDate() {
		return lastWorldDate;
	}
	public void setLastWorldDate(Date lastWorldDate) {
		this.lastWorldDate = lastWorldDate;
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
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAddDate() {
		return addDate;
	}
	
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getModifyDate() {
		return modifyDate;
	}
	
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	
	
}

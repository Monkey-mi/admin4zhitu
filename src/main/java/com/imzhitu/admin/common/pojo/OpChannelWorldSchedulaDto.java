package com.imzhitu.admin.common.pojo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class OpChannelWorldSchedulaDto extends AbstractNumberDto{

	private static final long serialVersionUID = 7061432886764890004L;
	
	private Integer id;			//id
	private Integer worldId;	//织图id
	private Integer userId;		//用户id
	private Integer valid;		//有效性，0无效，1有效
	private Integer channelId;	//频道id
	private Integer operatorId;	//最后操作人id
	private String operatorName;//最后操作人名字
	private String channelName;	//频道名称
	private String worldLink;	//织图连接
	private String userName;	//用户名称
	private Date addDate;		//添加日期
	private Date modifyDate;	//最后修改时间
	private Date schedulaDate;	//计划时间
	private Integer finish;		//完成标志
	
	public Integer getFinish() {
		return finish;
	}
	public void setFinish(Integer finish) {
		this.finish = finish;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getSchedulaDate() {
		return schedulaDate;
	}
	public void setSchedulaDate(Date schedulaDate) {
		this.schedulaDate = schedulaDate;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getWorldId() {
		return worldId;
	}
	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
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
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getWorldLink() {
		return worldLink;
	}
	public void setWorldLink(String worldLink) {
		this.worldLink = worldLink;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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

}

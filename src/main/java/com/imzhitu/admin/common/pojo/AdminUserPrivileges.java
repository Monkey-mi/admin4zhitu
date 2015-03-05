package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class AdminUserPrivileges extends AbstractNumberDto implements Serializable{
	private static final long serialVersionUID = 4791306280542285425L;
	private Integer userId;			//用户id
	private Integer id;				//id
	private Integer privilegeId;	//权限id
	private Integer valid;			//有效性
	private Integer operatorId;		//最后操作者id
	private String operatorName;	//最后操作者名字
	private Date addDate;			//创建时间
	private Date modifyDate;		//最后修改时间
	private String privilegeName;	//权限名称
	private String privilegeUrl;	//权限url
	private String icon;			//权限icon
	private Integer privilegeGroupId;//权限所在的组id
	private Integer serial;			//序号
	
	public String getPrivilegeName() {
		return privilegeName;
	}
	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}
	public String getPrivilegeUrl() {
		return privilegeUrl;
	}
	public void setPrivilegeUrl(String privilegeUrl) {
		this.privilegeUrl = privilegeUrl;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getPrivilegeGroupId() {
		return privilegeGroupId;
	}
	public void setPrivilegeGroupId(Integer privilegeGroupId) {
		this.privilegeGroupId = privilegeGroupId;
	}
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
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
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAddDate() {
		return addDate;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getModifyDate() {
		return modifyDate;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
}

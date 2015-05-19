package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * 管理员账号与织图用户绑定关系的数据传输对象
 * 
 * @author zhangbo 2015-05-13
 *
 */
public class AdminAndUserRelationshipDto  extends AbstractNumberDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7157006682718357535L;
	
	private Integer id;
	private Integer adminUserId;
	private Integer userId;
	private String adminUserName;
	private String userName;
	private String createTime;
	private String updateTime;
	
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
	 * @return the adminUserId
	 */
	public Integer getAdminUserId() {
	    return adminUserId;
	}

	/**
	 * @param adminUserId the adminUserId to set
	 */
	public void setAdminUserId(Integer adminUserId) {
	    this.adminUserId = adminUserId;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
	    return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
	    this.userId = userId;
	}

	/**
	 * @return the adminUserName
	 */
	public String getAdminUserName() {
	    return adminUserName;
	}
	
	/**
	 * @param adminUserName the adminUserName to set
	 */
	public void setAdminUserName(String adminUserName) {
	    this.adminUserName = adminUserName;
	}
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
	    return userName;
	}
	
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
	    this.userName = userName;
	}
	
	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
	    return createTime;
	}
	
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
	    this.createTime = createTime;
	}
	
	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
	    return updateTime;
	}
	
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
	    this.updateTime = updateTime;
	}
	
}

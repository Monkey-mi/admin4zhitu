package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 管理员信息数据传输对象
 * </p>
 * 
 * 创建时间：2012-11-08
 * 
 * @author ztj
 * 
 */
public class AdminUserDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5539084278334821700L;
	private Integer id;
	private String loginCode;
	private String userName;
	private byte[] password;
	private Integer valid = Tag.FALSE;
	private Integer[] roleIds;

	public AdminUserDto() {
		super();
	}


	public AdminUserDto(Integer id, String loginCode, String userName,
			byte[] password, Integer valid) {
		super();
		this.id = id;
		this.loginCode = loginCode;
		this.userName = userName;
		this.password = password;
		this.valid = valid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public Integer[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Integer[] roleIds) {
		this.roleIds = roleIds;
	}


	public Integer getValid() {
		return valid;
	}


	public void setValid(Integer valid) {
		this.valid = valid;
	}
	
}

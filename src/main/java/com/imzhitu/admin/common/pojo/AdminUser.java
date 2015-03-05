package com.imzhitu.admin.common.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * <p>
 * 管理员账号信息POJO
 * </p>
 * 
 * 创建时间：2012-11-07
 * 
 * @author ztj
 * 
 */
public class AdminUser extends AbstractNumberDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 704950938814631017L;
	private Integer id;
	private String loginCode;
	private String userName;
	private String password;
	private byte[] passwordEncrypt;
	private Integer valid;
	private Integer[] roleIds;
	
	public AdminUser() {
		super();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
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

	public byte[] getPasswordEncrypt() {
		return passwordEncrypt;
	}

	public void setPasswordEncrypt(byte[] passwordEncrypt) {
		this.passwordEncrypt = passwordEncrypt;
	}
	
}

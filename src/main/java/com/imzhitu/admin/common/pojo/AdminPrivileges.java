package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * <p>
 * 系统权限信息POJO
 * </p>
 * 
 * 创建时间：2013-2-16
 * @author ztj
 * 
 */
public class AdminPrivileges extends AbstractNumberDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2881630085260883155L;
	private Integer id;
	private String privilegesName;
	private String privilegesDesc;
	private String privilegesURL;
	private String icon;
	private Integer groupId;
	private Integer serial;

	public AdminPrivileges() {
		super();
	}

	public AdminPrivileges(Integer id, String privilegesName, String privilegesDesc,
			String privilegesURL, String icon, Integer groupId, Integer serial) {
		super();
		this.id = id;
		this.privilegesName = privilegesName;
		this.privilegesDesc = privilegesDesc;
		this.privilegesURL = privilegesURL;
		this.icon = icon;
		this.groupId = groupId;
		this.serial = serial;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrivilegesName() {
		return privilegesName;
	}

	public void setPrivilegesName(String privilegesName) {
		this.privilegesName = privilegesName;
	}

	public String getPrivilegesDesc() {
		return privilegesDesc;
	}

	public void setPrivilegesDesc(String privilegesDesc) {
		this.privilegesDesc = privilegesDesc;
	}

	public String getPrivilegesURL() {
		return privilegesURL;
	}

	public void setPrivilegesURL(String privilegesURL) {
		this.privilegesURL = privilegesURL;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	
	

}

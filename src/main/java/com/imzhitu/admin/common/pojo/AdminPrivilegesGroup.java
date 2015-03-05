package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 权限分组信息POJO
 * </p>
 * 
 * 创建时间：2013-2-16
 * 
 * @author ztj
 * 
 */
public class AdminPrivilegesGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 30685058809080481L;
	private Integer id;
	private String groupName;
	private String groupDesc;
	private String icon;
	private Integer serial;
	private Boolean selected;

	public AdminPrivilegesGroup() {
		super();
	}

	public AdminPrivilegesGroup(Integer id, String groupName, String groupDesc,
			String icon, Integer serial) {
		super();
		this.id = id;
		this.groupName = groupName;
		this.groupDesc = groupDesc;
		this.icon = icon;
		this.serial = serial;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
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
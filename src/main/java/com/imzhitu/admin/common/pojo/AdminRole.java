package com.imzhitu.admin.common.pojo;

import org.springframework.security.core.GrantedAuthority;


/**
 * <p>
 * 系统角色信息POJO
 * </p>
 * 
 * 创建时间：2013-2-17
 * 
 * @author ztj
 * 
 */
public class AdminRole implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3061843135784519666L;
	private Integer id;
	private String roleName;
	private String roleDesc;
	private Integer[] groupIds;
	private Boolean selected;

	public AdminRole() {
		super();
	}

	public AdminRole(Integer id, String roleName, String roleDesc) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Integer[] getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(Integer[] groupIds) {
		this.groupIds = groupIds;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	@Override
	public String getAuthority() {
		return this.roleName;
	}

}

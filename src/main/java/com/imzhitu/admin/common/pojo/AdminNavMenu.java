package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 一级导航菜单数据传输对象
 * </p>
 * 
 * 创建时间：2013-2-17
 * 
 * @author Administrator
 * 
 */
public class AdminNavMenu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4643098427455535495L;

	private Integer menuid;
	private String menuname;
	private String icon;
	private List<AdminSubNavMenu> menus = new ArrayList<AdminSubNavMenu>();
	

	public AdminNavMenu() {
		super();
	}
	
	public AdminNavMenu(Integer menuid, String menuname, String icon) {
		super();
		this.menuid = menuid;
		this.menuname = menuname;
		this.icon = icon;
	}



	public Integer getMenuid() {
		return menuid;
	}

	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<AdminSubNavMenu> getMenus() {
		return menus;
	}

	public void setMenus(List<AdminSubNavMenu> menus) {
		this.menus = menus;
	}
	
	/**
	 * 添加子菜单
	 * @param menu
	 */
	public void addMenu(AdminSubNavMenu menu) {
		this.menus.add(menu);
	}
	
	/**
	 * 删除子菜单
	 * @param menu
	 */
	public void remove(AdminSubNavMenu menu) {
		this.menus.remove(menu);
	}

}

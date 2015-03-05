package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 二级导航菜单数据传输对象
 * </p>
 * 
 * 创建时间：2013-2-17
 * 
 * @author ztj
 * 
 */
public class AdminSubNavMenu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9100606157280230555L;
	private Integer menuid;
	private String menuname;
	private String icon;
	private String url;

	public AdminSubNavMenu() {
		super();
	}

	public AdminSubNavMenu(Integer menuid, String menuname, String icon, String url) {
		super();
		this.menuid = menuid;
		this.menuname = menuname;
		this.icon = icon;
		this.url = url;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

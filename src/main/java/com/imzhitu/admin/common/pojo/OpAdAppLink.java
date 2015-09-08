package com.imzhitu.admin.common.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * APP换量链接POJO
 * 
 * 创建时间:2015-09-07
 * 
 * @author lynch
 *
 */
public class OpAdAppLink extends AbstractNumberDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4172566219188183354L;
	private Integer id;
	private String appName;
	private String appIcon;
	private String appIconL;
	private String appDesc;
	private String appLink;
	private String shortLink;
	private Integer phoneCode;
	private Integer clickCount;
	private Integer serial;
	private Integer open;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(String appIcon) {
		this.appIcon = appIcon;
	}

	public String getAppIconL() {
		return appIconL;
	}

	public void setAppIconL(String appIconL) {
		this.appIconL = appIconL;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public String getAppLink() {
		return appLink;
	}

	public void setAppLink(String appLink) {
		this.appLink = appLink;
	}

	public String getShortLink() {
		return shortLink;
	}

	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}

	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public Integer getOpen() {
		return open;
	}

	public void setOpen(Integer open) {
		this.open = open;
	}

}

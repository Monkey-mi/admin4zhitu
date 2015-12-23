package com.imzhitu.admin.trade.item.pojo;

import java.io.Serializable;

public class ItemShowDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1599709177833923673L;

	private Integer id;
	/**
	 * 织图ID
	 */
	private Integer worldId;
	/**
	 * 短链
	 */
	private String shortLink;
	/**
	 * 织图首图
	 */
	private String titleThumbPath;
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 用户头像
	 */
	private String authorAvatar;
	/**
	 * 用户名
	 */
	private String authorName;
	/**
	 *集合ID
	 */
	private Integer itemSetId;
	/**
	 * 集合标题
	 */
	private String itemSetTitle;
	/**
	 * 排序序列
	 */
	private Integer serial;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getWorldId() {
		return worldId;
	}
	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}
	public String getShortLink() {
		return shortLink;
	}
	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	public String getTitleThumbPath() {
		return titleThumbPath;
	}
	public void setTitleThumbPath(String titleThumbPath) {
		this.titleThumbPath = titleThumbPath;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAuthorAvatar() {
		return authorAvatar;
	}
	public void setAuthorAvatar(String authorAvatar) {
		this.authorAvatar = authorAvatar;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public Integer getItemSetId() {
		return itemSetId;
	}
	public void setItemSetId(Integer itemSetId) {
		this.itemSetId = itemSetId;
	}
	public String getItemSetTitle() {
		return itemSetTitle;
	}
	public void setItemSetTitle(String itemSetTitle) {
		this.itemSetTitle = itemSetTitle;
	}
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	
}

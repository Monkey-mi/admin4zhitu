package com.imzhitu.admin.common.pojo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * 私信对话POJO
 * 
 * @author lynch 2015-10-29
 *
 */
public class UserMsgConversationDto extends AbstractNumberDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3700312803502162817L;

	private Integer contentId;
	private Integer userId;
	private Integer otherId;
	
	private Integer unreadCount;
	private String content;
	private Date msgDate;

	private String userName;
	private String userAvatar;
	private String userAvatarL;
	private Integer star;
	private Float ver;
	private Integer phoneCode;
	private String phoneVer;
	private String phoneSys;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getOtherId() {
		return otherId;
	}

	public void setOtherId(Integer otherId) {
		this.otherId = otherId;
	}

	public Integer getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(Integer unreadCount) {
		this.unreadCount = unreadCount;
	}

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getMsgDate() {
		return msgDate;
	}

	public void setMsgDate(Date msgDate) {
		this.msgDate = msgDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public String getUserAvatarL() {
		return userAvatarL;
	}

	public void setUserAvatarL(String userAvatarL) {
		this.userAvatarL = userAvatarL;
	}

	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getPhoneVer() {
		return phoneVer;
	}

	public void setPhoneVer(String phoneVer) {
		this.phoneVer = phoneVer;
	}

	public Float getVer() {
		return ver;
	}

	public void setVer(Float ver) {
		this.ver = ver;
	}

	public String getPhoneSys() {
		return phoneSys;
	}

	public void setPhoneSys(String phoneSys) {
		this.phoneSys = phoneSys;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}
	
}

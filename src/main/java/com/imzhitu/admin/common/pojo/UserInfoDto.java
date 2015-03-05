package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.ObjectWithUserVerify;
import com.imzhitu.admin.common.UserWithInteract;

public class UserInfoDto implements Serializable, UserWithInteract,
		ObjectWithUserVerify {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4206965477665912347L;

	private Integer id;
	private Integer platformCode; // 社交平台代号
	private String platformToken; // 社交平台授权token
	private Long platformTokenExpires; // 社交平台授权过期时间
	private String loginCode;

	private String userName;
	private String userAvatar;
	private String userAvatarL;
	private Integer sex = Tag.SEX_UNKNOWN; // 性别
	private String email; // 邮箱
	private String address; // 地址
	private Date birthday; // 生日
	private String signature;
	private String userLabel;
	private Date registerDate;
	private String pushToken;
	private Integer phoneCode;
	private String phoneSys;
	private String phoneVer;
	private Integer online = Tag.TRUE;
	private Integer acceptSysPush = Tag.TRUE; // 接收系统消息推送
	private Integer acceptCommentPush = Tag.TRUE; // 接收评论推送
	private Integer acceptReplyPush = Tag.TRUE; // 接收回复推送
	private Integer acceptLikedPush = Tag.TRUE; // 接收喜欢推送
	private Integer acceptKeepPush = Tag.TRUE; // 接收收藏推送
	private Integer acceptConcernPush = Tag.TRUE; // 接收关注推送
	private Integer acceptMsgPush = Tag.TRUE; // 接收系统私信推送
	private Integer acceptUmsgPush = Tag.TRUE; // 接收用户私信推送
	private Integer concernCount; // 关注总数
	private Integer followCount; // 粉丝总数
	private Integer worldCount; // 织图总数
	private Integer likedCount; // 喜欢总数
	private Integer keepCount; // 收藏总数
	private Float ver; // APP版本
	private Integer star; // 明星标记
	private String verifyName;
	private String verifyIcon;
	private Integer trust; // 信任标记
	private Integer shield; // 屏蔽标记
	private Integer userAccept; // 推荐用户允许标记
	private Integer sysAccept; // 推荐系统允许标记
	private Integer interacted = Tag.FALSE;

	public UserInfoDto() {
		super();
	}

	public UserInfoDto(Integer id, Integer platformCode, String platformToken,
			Long platformTokenExpires, String loginCode, String userName,
			String userAvatar, String userAvatarL, Integer sex, String email,
			String address, Date birthday, String signature, String userLabel,
			Date registerDate, String pushToken, Integer phoneCode,
			String phoneSys, String phoneVer, Integer online,
			Integer acceptSysPush, Integer acceptCommentPush,
			Integer acceptReplyPush, Integer acceptLikedPush,
			Integer acceptKeepPush, Integer acceptConcernPush,
			Integer acceptMsgPush, Integer acceptUmsgPush,
			Integer concernCount, Integer followCount, Integer worldCount,
			Integer likedCount, Integer keepCount, Float ver, Integer star,
			Integer trust, Integer shield, Integer userAccept, Integer sysAccept) {
		this.id = id;
		this.platformCode = platformCode;
		this.platformToken = platformToken;
		this.platformTokenExpires = platformTokenExpires;
		this.loginCode = loginCode;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.userAvatarL = userAvatarL;
		this.sex = sex;
		this.email = email;
		this.address = address;
		this.birthday = birthday;
		this.signature = signature;
		this.userLabel = userLabel;
		this.registerDate = registerDate;
		this.pushToken = pushToken;
		this.phoneCode = phoneCode;
		this.phoneSys = phoneSys;
		this.phoneVer = phoneVer;
		this.online = online;
		this.acceptSysPush = acceptSysPush;
		this.acceptCommentPush = acceptCommentPush;
		this.acceptReplyPush = acceptReplyPush;
		this.acceptLikedPush = acceptLikedPush;
		this.acceptKeepPush = acceptKeepPush;
		this.acceptConcernPush = acceptConcernPush;
		this.acceptMsgPush = acceptMsgPush;
		this.acceptUmsgPush = acceptUmsgPush;
		this.concernCount = concernCount;
		this.followCount = followCount;
		this.worldCount = worldCount;
		this.likedCount = likedCount;
		this.keepCount = keepCount;
		this.ver = ver;
		this.star = star;
		this.trust = trust;
		this.shield = shield;
		this.userAccept = userAccept;
		this.sysAccept = sysAccept;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Integer getUserId() {
		return id;
	}

	public Integer getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(Integer platformCode) {
		this.platformCode = platformCode;
	}

	public String getPlatformToken() {
		return platformToken;
	}

	public void setPlatformToken(String platformToken) {
		this.platformToken = platformToken;
	}

	public Long getPlatformTokenExpires() {
		return platformTokenExpires;
	}

	public void setPlatformTokenExpires(Long platformTokenExpires) {
		this.platformTokenExpires = platformTokenExpires;
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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getPushToken() {
		return pushToken;
	}

	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}

	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}
	
	public String getPhoneSys() {
		return phoneSys;
	}

	public void setPhoneSys(String phoneSys) {
		this.phoneSys = phoneSys;
	}

	public String getPhoneVer() {
		return phoneVer;
	}

	public void setPhoneVer(String phoneVer) {
		this.phoneVer = phoneVer;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public Integer getAcceptSysPush() {
		return acceptSysPush;
	}

	public void setAcceptSysPush(Integer acceptSysPush) {
		this.acceptSysPush = acceptSysPush;
	}

	public Integer getAcceptCommentPush() {
		return acceptCommentPush;
	}

	public void setAcceptCommentPush(Integer acceptCommentPush) {
		this.acceptCommentPush = acceptCommentPush;
	}

	public Integer getAcceptReplyPush() {
		return acceptReplyPush;
	}

	public void setAcceptReplyPush(Integer acceptReplyPush) {
		this.acceptReplyPush = acceptReplyPush;
	}

	public Integer getAcceptLikedPush() {
		return acceptLikedPush;
	}

	public void setAcceptLikedPush(Integer acceptLikedPush) {
		this.acceptLikedPush = acceptLikedPush;
	}

	public Integer getAcceptKeepPush() {
		return acceptKeepPush;
	}

	public void setAcceptKeepPush(Integer acceptKeepPush) {
		this.acceptKeepPush = acceptKeepPush;
	}

	public Integer getAcceptConcernPush() {
		return acceptConcernPush;
	}

	public void setAcceptConcernPush(Integer acceptConcernPush) {
		this.acceptConcernPush = acceptConcernPush;
	}

	public Integer getAcceptMsgPush() {
		return acceptMsgPush;
	}

	public void setAcceptMsgPush(Integer acceptMsgPush) {
		this.acceptMsgPush = acceptMsgPush;
	}

	public Integer getConcernCount() {
		return concernCount;
	}

	public String getUserLabel() {
		return userLabel;
	}

	public void setUserLabel(String userLabel) {
		this.userLabel = userLabel;
	}

	public Integer getAcceptUmsgPush() {
		return acceptUmsgPush;
	}

	public void setAcceptUmsgPush(Integer acceptUmsgPush) {
		this.acceptUmsgPush = acceptUmsgPush;
	}

	public void setConcernCount(Integer concernCount) {
		this.concernCount = concernCount;
	}

	public Integer getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
	}

	public Integer getWorldCount() {
		return worldCount;
	}

	public void setWorldCount(Integer worldCount) {
		this.worldCount = worldCount;
	}

	public Integer getLikedCount() {
		return likedCount;
	}

	public void setLikedCount(Integer likedCount) {
		this.likedCount = likedCount;
	}

	public Integer getKeepCount() {
		return keepCount;
	}

	public void setKeepCount(Integer keepCount) {
		this.keepCount = keepCount;
	}

	public Integer getUserAccept() {
		return userAccept;
	}

	public void setUserAccept(Integer userAccept) {
		this.userAccept = userAccept;
	}

	public Integer getSysAccept() {
		return sysAccept;
	}

	public void setSysAccept(Integer sysAccept) {
		this.sysAccept = sysAccept;
	}

	public Integer getShield() {
		return shield;
	}

	public void setShield(Integer shield) {
		this.shield = shield;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Integer getTrust() {
		return trust;
	}

	public void setTrust(Integer trust) {
		this.trust = trust;
	}

	public Integer getInteracted() {
		return interacted;
	}

	public void setInteracted(Integer interacted) {
		this.interacted = interacted;
	}

	public Float getVer() {
		return ver;
	}

	public void setVer(Float ver) {
		this.ver = ver;
	}

	public String getVerifyName() {
		return verifyName;
	}

	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}

	public String getVerifyIcon() {
		return verifyIcon;
	}

	public void setVerifyIcon(String verifyIcon) {
		this.verifyIcon = verifyIcon;
	}

	@Override
	public Integer getVerifyId() {
		return star;
	}

}

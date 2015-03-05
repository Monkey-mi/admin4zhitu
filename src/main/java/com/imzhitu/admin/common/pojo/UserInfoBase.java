package com.imzhitu.admin.common.pojo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.pojo.ObjectWithUserVerify;

/**
 * <p>
 * 用户信息公用信息POJO，所有需要查询用户信息的POJO统一继承此类
 * </p>
 * 
 * 创建时间:2014-11-01
 * 
 * @author lynch
 *
 */
public abstract class UserInfoBase extends AbstractNumberDto implements
		ObjectWithUserVerify {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6145349686556705845L;
	private Integer id;
	private Integer platformCode; // 社交平台代号
	private String platformToken; // 社交平台授权token
	private Long platformTokenExpires; // 社交平台授权过期时间
	private String platformSign;
	private Integer platformVerify;
	private String loginCode;
	private String userName;
	private String userAvatar;
	private String userAvatarL;
	private Integer sex; // 性别
	private String email; // 邮箱
	private String address; // 地址
	private Date birthday; // 生日
	private String signature;
	private String userLabel;
	private String province;
	private String city;
	private Double longitude;
	private Double latitude;
	private Date registerDate;
	private Integer concernCount; // 关注总数
	private Integer followCount; // 粉丝总数
	private Integer worldCount; // 织图总数
	private Integer likedCount; // 喜欢总数
	private Integer keepCount; // 收藏总数
	private String pushToken;
	private Integer phoneCode;
	private String phoneSys;
	private String phoneVer;
	private Integer online;
	private Integer acceptSysPush; // 接收系统消息推送
	private Integer acceptCommentPush; // 接收评论推送
	private Integer acceptReplyPush; // 接收回复推送
	private Integer acceptLikedPush; // 接收喜欢推送
	private Integer acceptKeepPush; // 接收收藏推送
	private Integer acceptConcernPush; // 接收关注推送
	private Integer acceptMsgPush; // 接收系统私信推送
	private Integer acceptUmsgPush; // 接收用户私信推送
	private Float ver;
	private Integer star; // 明星标记
	private Integer trust; // 信任标记
	private Integer shield;
	private Integer activity;

	private String verifyName;
	private String verifyIcon;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getPlatformSign() {
		return platformSign;
	}

	public void setPlatformSign(String platformSign) {
		this.platformSign = platformSign;
	}

	public Integer getPlatformVerify() {
		return platformVerify;
	}

	public void setPlatformVerify(Integer platformVerify) {
		this.platformVerify = platformVerify;
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

	public String getUserLabel() {
		return userLabel;
	}

	public void setUserLabel(String userLabel) {
		this.userLabel = userLabel;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Integer getConcernCount() {
		return concernCount;
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

	public Integer getAcceptUmsgPush() {
		return acceptUmsgPush;
	}

	public void setAcceptUmsgPush(Integer acceptUmsgPush) {
		this.acceptUmsgPush = acceptUmsgPush;
	}

	public Float getVer() {
		return ver;
	}

	public void setVer(Float ver) {
		this.ver = ver;
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

	public Integer getShield() {
		return shield;
	}

	public void setShield(Integer shield) {
		this.shield = shield;
	}

	public Integer getActivity() {
		return activity;
	}

	public void setActivity(Integer activity) {
		this.activity = activity;
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
	
	public Integer getVerifyId() {
		return this.star;
	}

}

package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.ObjectWithUserVerify;
import com.imzhitu.admin.common.UserWithInteract;

/**
 * <p>
 * 用户推荐数据传输对象POJO
 * </p>
 * 
 * 创建时间：2014-3-18
 * 
 * @author ztj
 * 
 */
public class OpUserRecommendDto implements Serializable, UserWithInteract,
		ObjectWithUserVerify {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3629398488572596005L;

	private Integer id; // 推荐id
	private Integer userId;
	private String recommendDesc;
	private String recommender;
	private Date dateAdded;
	private Date dateModified;
	private Integer userAccept;
	private Integer sysAccept;
	private Integer notified;
	private Integer fixPos;// 固定排名位置
	private Integer weight;
	private Integer verifyId;
	private String verifyName;
	private String verifyIcon;

	private Integer platformCode; // 社交平台代号
	private String userName;
	private String userAvatar;
	private String userAvatarL;
	private Integer sex = Tag.SEX_UNKNOWN; // 性别
	private String email; // 邮箱
	private String address; // 地址
	private String province;
	private String city;
	private Date birthday; // 生日
	private String signature;
	private String userLabel;
	private Date registerDate;
	private Integer phoneCode;
	private String phoneSys;
	private String phoneVer;
	private Integer online = Tag.TRUE;
	private Integer concernCount; // 关注总数
	private Integer followCount; // 粉丝总数
	private Integer worldCount; // 织图总数
	private Integer likedCount; // 喜欢总数
	private Integer keepCount; // 收藏总数
	private Float ver; // 版本
	private Integer shield; // 是否屏蔽
	private Integer star = Tag.FALSE; // 明星标记
	private Integer trust = Tag.FALSE; // 信任标记
	private String loginCode;

	private Integer interacted = Tag.FALSE;

	public OpUserRecommendDto() {
		super();
	}

	public OpUserRecommendDto(Integer id, Integer userId, String recommendDesc,
			String recommender, Date dateAdded, Date dateModified,
			Integer userAccept, Integer sysAccept, Integer notified,
			Integer weight, Integer verifyId, String verifyName,
			String verifyIcon, Integer platformCode, String userName,
			String userAvatar, String userAvatarL, Integer sex, String email,
			String address, String province, String city, Date birthday,
			String signature, String userLabel, Date registerDate,
			Integer phoneCode, String phoneSys, String phoneVer, Integer online,
			Integer concernCount, Integer followCount, Integer worldCount,
			Integer likedCount, Integer keepCount, Float ver, Integer star,
			Integer trust) {
		super();
		this.id = id;
		this.userId = userId;
		this.recommendDesc = recommendDesc;
		this.recommender = recommender;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.userAccept = userAccept;
		this.sysAccept = sysAccept;
		this.notified = notified;
		this.weight = weight;
		this.verifyId = verifyId;
		this.verifyName = verifyName;
		this.verifyIcon = verifyIcon;
		this.platformCode = platformCode;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.userAvatarL = userAvatarL;
		this.sex = sex;
		this.email = email;
		this.address = address;
		this.province = province;
		this.city = city;
		this.birthday = birthday;
		this.signature = signature;
		this.userLabel = userLabel;
		this.registerDate = registerDate;
		this.phoneCode = phoneCode;
		this.phoneSys = phoneSys;
		this.phoneVer = phoneVer;
		this.online = online;
		this.concernCount = concernCount;
		this.followCount = followCount;
		this.worldCount = worldCount;
		this.likedCount = likedCount;
		this.keepCount = keepCount;
		this.ver = ver;
		this.star = star;
		this.trust = trust;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
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

	public Integer getShield() {
		return shield;
	}

	public void setShield(Integer shield) {
		this.shield = shield;
	}

	public String getRecommendDesc() {
		return recommendDesc;
	}

	public void setRecommendDesc(String recommendDesc) {
		this.recommendDesc = recommendDesc;
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

	public Integer getNotified() {
		return notified;
	}

	public void setNotified(Integer notified) {
		this.notified = notified;
	}

	public String getRecommender() {
		return recommender;
	}

	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
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

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(Integer platformCode) {
		this.platformCode = platformCode;
	}

	public Float getVer() {
		return ver;
	}

	public void setVer(Float ver) {
		this.ver = ver;
	}

	@Override
	public Integer getUserId() {
		return this.userId;
	}

	@Override
	public void setInteracted(Integer interacted) {
		this.interacted = interacted;
	}

	public Integer getInteracted() {
		return this.interacted;
	}

	public void setFixPos(Integer fixPos) {
		this.fixPos = fixPos;
	}

	public Integer getFixPos() {
		return this.fixPos;
	}

	public Integer getVerifyId() {
		return verifyId;
	}

	public void setVerifyId(Integer verifyId) {
		this.verifyId = verifyId;
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

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}
	
}

package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.ObjectWithUserVerify;
import com.imzhitu.admin.common.WorldWithInteract;

/**
 * <p>
 * 织图活动数据传输对象
 * </p>
 * 
 * 创建时间：2014-2-27
 * 
 * @author ztj
 * 
 */
public class OpActivityWorldDto implements Serializable, WorldWithInteract, ObjectWithUserVerify {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Integer id; // 织图id
	private String shortLink; // 织图短链
	private String worldName; // 世界名称
	private String worldDesc; // 世界描述
	private String worldLabel; // 世界标签
	private String worldType; // 世界分类
	private Integer typeId; // 分类id
	private Date dateAdded; // 添加时间
	private Date dateModified; // 创建时间
	private Integer authorId; // 作者id
	private String authorName; // 作者名字
	private String authorAvatar; // 作者头像链接
	private Integer star = Tag.FALSE; // 明星标记
	private String verifyName;
	private String verifyIcon;
	private Integer trust; // 信任标记
	private Integer clickCount; // 查看次数
	private Integer likeCount; // 被“赞”次数
	private Integer commentCount; // 被评论次数
	private Integer keepCount; // 收藏次数
	private String coverPath; // 封面路径
	private String titlePath; // 首页路径
	private String titleThumbPath; // 首页缩略图路径
	private Double longitude;// 经度
	private Double latitude;// 纬度
	private String locationDesc;// 位置描述
	private String locationAddr; // 位置地址
	private Integer phoneCode;// 手机辨别代号
	private String province;// 所在省份
	private String city;// 所在城市
	private Integer size; // 织图大小
	private Integer childCount; // 子世界张数

	private Integer activityId; // 活动id
	private Integer activityWorldId; // 活动织图id
	private Integer valid; // 有效标记
	private Integer serial;
	private Integer weight; // 权重
	
	private String worldURL;
	private Object isWinner = Tag.FALSE; // 获胜织图标记
	private Integer awardId;
	private String awardName;
	private String awardThumbPath;
	private Integer interacted = Tag.FALSE;
	private Integer superb; // 是否为活动加精标记，1：加精，0：未加精
	
	public OpActivityWorldDto() {
		super();
	}

	public OpActivityWorldDto(Integer id, String shortLink, String worldName,
			String worldDesc, String worldLabel, String worldType,
			Integer typeId, Date dateAdded, Date dateModified,
			Integer authorId, String authorName, String authorAvatar, 
			Integer star, Integer trust, Integer clickCount, Integer likeCount, 
			Integer commentCount, Integer keepCount, String coverPath,
			String titlePath, String titleThumbPath,
			Double longitude, Double latitude, String locationDesc,
			String locationAddr, Integer phoneCode, String province,
			String city, Integer size, Integer childCount,
			Integer activityId, Integer activityWorldId, Integer valid, Integer serial, Integer weight, Integer superb) {
		this.id = id;
		this.shortLink = shortLink;
		this.worldName = worldName;
		this.worldDesc = worldDesc;
		this.worldLabel = worldLabel;
		this.worldType = worldType;
		this.typeId = typeId;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.authorId = authorId;
		this.authorName = authorName;
		this.authorAvatar = authorAvatar;
		this.star = star;
		this.trust = trust;
		this.clickCount = clickCount;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.keepCount = keepCount;
		this.coverPath = coverPath;
		this.titlePath = titlePath;
		this.titleThumbPath = titleThumbPath;
		this.longitude = longitude;
		this.latitude = latitude;
		this.locationDesc = locationDesc;
		this.locationAddr = locationAddr;
		this.phoneCode = phoneCode;
		this.province = province;
		this.city = city;
		this.size = size;
		this.childCount = childCount;
		this.activityId = activityId;
		this.activityWorldId = activityWorldId;
		this.valid = valid;
		this.serial = serial;
		this.weight = weight;
		this.superb = superb;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShortLink() {
		return shortLink;
	}

	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}

	public String getWorldName() {
		return worldName;
	}

	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}

	public String getWorldDesc() {
		return worldDesc;
	}

	public void setWorldDesc(String worldDesc) {
		this.worldDesc = worldDesc;
	}

	public String getWorldLabel() {
		return worldLabel;
	}

	public void setWorldLabel(String worldLabel) {
		this.worldLabel = worldLabel;
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

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorAvatar() {
		return authorAvatar;
	}

	public void setAuthorAvatar(String authorAvatar) {
		this.authorAvatar = authorAvatar;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getKeepCount() {
		return keepCount;
	}

	public void setKeepCount(Integer keepCount) {
		this.keepCount = keepCount;
	}

	public String getCoverPath() {
		return coverPath;
	}

	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}

	public String getTitlePath() {
		return titlePath;
	}

	public void setTitlePath(String titlePath) {
		this.titlePath = titlePath;
	}

	public String getTitleThumbPath() {
		return titleThumbPath;
	}

	public void setTitleThumbPath(String titleThumbPath) {
		this.titleThumbPath = titleThumbPath;
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

	public String getLocationDesc() {
		return locationDesc;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	public String getLocationAddr() {
		return locationAddr;
	}

	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
	}

	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
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

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public String getWorldURL() {
		return worldURL;
	}

	public void setWorldURL(String worldURL) {
		this.worldURL = worldURL;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	
	public Integer getActivityWorldId() {
		return activityWorldId;
	}

	public void setActivityWorldId(Integer activityWorldId) {
		this.activityWorldId = activityWorldId;
	}

	public String getWorldType() {
		return worldType;
	}

	public void setWorldType(String worldType) {
		this.worldType = worldType;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
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

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getTrust() {
		return trust;
	}

	public void setTrust(Integer trust) {
		this.trust = trust;
	}
	
	public Object getIsWinner() {
		return isWinner;
	}

	public void setIsWinner(Object isWinner) {
		this.isWinner = isWinner;
	}
	
	public Integer getAwardId() {
		return awardId;
	}

	public void setAwardId(Integer awardId) {
		this.awardId = awardId;
	}
	
	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public String getAwardThumbPath() {
		return awardThumbPath;
	}

	public void setAwardThumbPath(String awardThumbPath) {
		this.awardThumbPath = awardThumbPath;
	}

	@Override
	public Integer getWorldId() {
		return this.id;
	}

	public Integer getInteracted() {
		return interacted;
	}

	@Override
	public void setInteracted(Integer interacted) {
		this.interacted = interacted;
	}

	@Override
	public Integer getVerifyId() {
		return star;
	}

	public Integer getSuperb() {
		return superb;
	}

	public void setSuperb(Integer superb) {
		this.superb = superb;
	}

}

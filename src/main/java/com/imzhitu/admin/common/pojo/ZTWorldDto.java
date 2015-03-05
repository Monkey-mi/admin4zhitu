package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.pojo.ObjectWithUserVerify;
import com.imzhitu.admin.common.WorldWithInteract;

/**
 * <p>
 * 织图管理数据传输对象
 * </p>
 * 
 * 创建时间：2013-8-8
 * 
 * @author ztj
 * 
 */
public class ZTWorldDto extends AbstractNumberDto implements Serializable, WorldWithInteract,
		ObjectWithUserVerify {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4452561917338410488L;
	private Integer id;
	private Integer worldId; // 织图id
	private String shortLink; // 短链
	private Integer authorId; // 作者id
	private String authorName; // 作者名字
	private String authorAvatar; // 作者头像
	private Integer star = Tag.FALSE; // 明星标记
	private String verifyName;
	private String verifyIcon;
	private Integer trust; // 信任标记
	private Float appVer = 0f;
	private String phoneSys;
	private String phoneVer;
	private String worldName; // 世界名称
	private String worldDesc; // 世界描述
	private String worldLabel; // 世界标签
	private String worldType; // 织图分类
	private Integer typeId; // 分类id
	private Date dateAdded; // 添加时间
	private Date dateModified; // 创建时间
	private Integer clickCount; // 查看次数
	private Integer likeCount; // 被“赞”次数
	private Integer commentCount; // 被评论次数
	private Integer keepCount; // 被收藏次数
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
	private Integer valid; // 是否有效标志
	private Integer latestValid; // 是否最新有效标记
	private Integer shield; // 是否被屏蔽
	private String worldURL; // 连接路径
	private Object squarerecd = Tag.FALSE; // 是否在广场推荐
	private Object activityrecd; // 是否在活动
	private Integer interacted = Tag.FALSE;

	private String thumbs; // 多张缩略图路径
	private Integer childCount; // 子世界总数
	private Integer ver; // 织图版本

//	private UserLevelListDto userlevel;// 用户等级
	private Integer user_level_id;	//用户等级id
	private String level_description;//用户等级描述
	
	private Integer typeInteracted;

	private Integer activeOperated = -1;// 活动是否被添加过
	private String channelName;	//频道名称
	
	

	private String trustOperatorName;//添加为信任的人的名字
	private Date trustModifyDate;//最后操作信任字段的时间		
	private Integer trustOperatorId;//添加为信任的人的id
	
	private String orderKey;	//排序的key
	
	
	public Integer getUser_level_id() {
		return user_level_id;
	}
	public void setUser_level_id(Integer user_level_id) {
		this.user_level_id = user_level_id;
	}
	public String getLevel_description() {
		return level_description;
	}
	public void setLevel_description(String level_description) {
		this.level_description = level_description;
	}
	public String getOrderKey() {
		return orderKey;
	}
	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	public void setTrustOperatorName(String trustOperatorName){
		this.trustOperatorName = trustOperatorName;
	}
	public String getTrustOperatorName(){
		return this.trustOperatorName;
	}
	
	public void setTrustModifyDate(Date trustModifyDate){
		this.trustModifyDate = trustModifyDate;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getTrustModifyDate(){
		return this.trustModifyDate;
	}
	
	public void setTrustOperatorId(Integer trustOperatorId){
		this.trustOperatorId = trustOperatorId;
	}
	public Integer getTrustOperatorId(){
		return this.trustOperatorId;
	}

	public void setActiveOperated(Integer activeOperated) {
		this.activeOperated = activeOperated;
	}

	public Integer getActiveOperated() {
		return this.activeOperated;
	}

	public void setTypeInteracted(Integer typeInteracted) {
		this.typeInteracted = typeInteracted;
	}

	public Integer getTypeInteracted() {
		return this.typeInteracted;
	}
/*
	public void setUserlevel(UserLevelListDto userlevel) {
		this.userlevel = userlevel;
	}

	public UserLevelListDto getUserlevel() {
		return this.userlevel;
	}
*/
	public ZTWorldDto() {
		super();
	}

	public ZTWorldDto(Integer id, String shortLink, Integer authorId,
			String authorName, String authorAvatar, Integer star,
			Integer trust, Float appVer, String phoneSys, String phoneVer, String worldName,
			String worldDesc, String worldLabel, String worldType,
			Integer typeId, Date dateAdded, Date dateModified,
			Integer clickCount, Integer likeCount, Integer commentCount,
			Integer keepCount, String coverPath, String titlePath,
			String titleThumbPath, Double longitude, Double latitude,
			String locationDesc, String locationAddr, Integer phoneCode,
			String province, String city, Integer size, Integer valid,
			Integer latestValid, Integer shield, Object squarerecd) {
		super();
		this.id = id;
		this.shortLink = shortLink;
		this.authorId = authorId;
		this.authorName = authorName;
		this.authorAvatar = authorAvatar;
		this.star = star;
		this.trust = trust;
		this.appVer = appVer;
		this.phoneSys = phoneSys;
		this.phoneVer = phoneVer;
		this.worldName = worldName;
		this.worldDesc = worldDesc;
		this.worldLabel = worldLabel;
		this.worldType = worldType;
		this.typeId = typeId;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
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
		this.valid = valid;
		this.latestValid = latestValid;
		this.shield = shield;
		this.squarerecd = squarerecd;
	}

	public ZTWorldDto(Integer id, String shortLink, Integer authorId,
			String worldName, String worldDesc, String worldLabel,
			String worldType, Integer typeId, Date dateAdded,
			Date dateModified, Integer clickCount, Integer likeCount,
			Integer commentCount, Integer keepCount, String coverPath,
			String titlePath, String titleThumbPath, String thumbs,
			Double longitude, Double latitude, String locationDesc,
			String locationAddr, Integer phoneCode, String province,
			String city, Integer size, Integer childCount, Integer ver,
			Integer valid, Integer shield) {
		super();
		this.id = id;
		this.shortLink = shortLink;
		this.authorId = authorId;
		this.worldName = worldName;
		this.worldDesc = worldDesc;
		this.worldLabel = worldLabel;
		this.worldType = worldType;
		this.typeId = typeId;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.clickCount = clickCount;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.keepCount = keepCount;
		this.coverPath = coverPath;
		this.titlePath = titlePath;
		this.titleThumbPath = titleThumbPath;
		this.thumbs = thumbs;
		this.longitude = longitude;
		this.latitude = latitude;
		this.locationDesc = locationDesc;
		this.locationAddr = locationAddr;
		this.phoneCode = phoneCode;
		this.province = province;
		this.city = city;
		this.size = size;
		this.childCount = childCount;
		this.ver = ver;
		this.valid = valid;
		this.shield = shield;
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

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
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

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getShield() {
		return shield;
	}

	public void setShield(Integer shield) {
		this.shield = shield;
	}

	public String getWorldURL() {
		return worldURL;
	}

	public void setWorldURL(String worldURL) {
		this.worldURL = worldURL;
	}

	public Object getSquarerecd() {
		return squarerecd;
	}

	public void setSquarerecd(Object squarerecd) {
		this.squarerecd = squarerecd;
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

	public String getWorldType() {
		return worldType;
	}

	public void setWorldType(String worldType) {
		this.worldType = worldType;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Object getActivityrecd() {
		return activityrecd;
	}

	public void setActivityrecd(Object activityrecd) {
		this.activityrecd = activityrecd;
	}

	public Integer getLatestValid() {
		return latestValid;
	}

	public void setLatestValid(Integer latestValid) {
		this.latestValid = latestValid;
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

	@Override
	public void setInteracted(Integer interacted) {
		this.interacted = interacted;
	}

	public String getThumbs() {
		return thumbs;
	}

	public void setThumbs(String thumbs) {
		this.thumbs = thumbs;
	}

	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
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

	public Float getAppVer() {
		return appVer;
	}

	public void setAppVer(Float appVer) {
		this.appVer = appVer;
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
	
}

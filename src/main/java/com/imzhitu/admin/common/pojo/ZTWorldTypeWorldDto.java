package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.pojo.ObjectWithUserVerify;
import com.hts.web.common.pojo.UserInfoDto;
import com.imzhitu.admin.common.WorldWithInteract;

public class ZTWorldTypeWorldDto extends AbstractNumberDto implements Serializable, WorldWithInteract,
		ObjectWithUserVerify {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer worldId; // 织图id
	private Integer typeId;
	private Integer superb;
	private Integer serial;
	private Integer typeValid;
	private Integer weight;
	private Integer recommenderId;
	private String recommenderName;
	private Date addDate;
	private Date modifyDate;

	private String shortLink; // 织图短链
	private String worldName; // 世界名称
	private String worldDesc; // 世界描述
	private String worldLabel; // 世界标签
	private String worldType; // 织图分类
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
	private Integer valid;
	private Integer shield;
	private String worldURL;
	private String channelName;	//频道名称

	private UserInfoDto userInfo;

	private Integer interacted;

	private UserLevelListDto userlevel;// 用户等级

	private Integer typeInteracted;//是否被操作过
	
	private String trustOperatorName;//添加为信任的人的名字
	private Date trustModifyDate;//最后操作信任字段的时间		
	private Integer trustOperatorId;//添加为信任的人的id
	
	private String reView;		//精选点评
	
	private Integer isSorted; //是否被排序过，0未排序，1已排序
	
	private String order;		//用以数据的排序
	private String sort;		//用户数据的排序
	
	public String getReView() {
		return reView;
	}
	public void setReView(String reView) {
		this.reView = reView;
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

	public void setTypeInteracted(Integer typeInteracted) {
		this.typeInteracted = typeInteracted;
	}

	public Integer getTypeInteracted() {
		return this.typeInteracted;
	}

	public void setUserlevel(UserLevelListDto userlevel) {
		this.userlevel = userlevel;
	}

	public UserLevelListDto getUserlevel() {
		return this.userlevel;
	}

	public ZTWorldTypeWorldDto() {
		super();
	}

	public ZTWorldTypeWorldDto(Integer id, Integer worldId, Integer typeId,
			Integer superb, Integer typeValid, Integer serial, Integer weight,
			Integer recommenderId, String shortLink, String worldName,
			String worldDesc, String worldLabel, String worldType,
			Date dateAdded, Date dateModified, Integer authorId,
			String authorName, String authorAvatar, Integer star,
			Integer trust, Integer clickCount, Integer likeCount,
			Integer commentCount, Integer keepCount, String coverPath,
			String titlePath, String titleThumbPath, Double longitude,
			Double latitude, String locationDesc, String locationAddr,
			Integer phoneCode, String province, String city, Integer size,
			Integer childCount, Integer valid, Integer shield, String worldURL,
			String recommenderName,Date addDate,Date modifyDate) {
		super();
		this.id = id;
		this.serial = serial;
		this.typeId = typeId;
		this.superb = superb;
		this.typeValid = typeValid;
		this.weight = weight;
		this.recommenderId = recommenderId;
		this.worldId = worldId;
		this.shortLink = shortLink;
		this.worldName = worldName;
		this.worldDesc = worldDesc;
		this.worldLabel = worldLabel;
		this.worldType = worldType;
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
		this.valid = valid;
		this.shield = shield;
		this.worldURL = worldURL;
		this.recommenderName = recommenderName;
		this.addDate =addDate;
		this.modifyDate = modifyDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public Integer getKeepCount() {
		return keepCount;
	}

	public void setKeepCount(Integer keepCount) {
		this.keepCount = keepCount;
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

	public String getWorldURL() {
		return worldURL;
	}

	public void setWorldURL(String worldURL) {
		this.worldURL = worldURL;
	}

	public String getShortLink() {
		return shortLink;
	}

	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
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

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public UserInfoDto getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoDto userInfo) {
		this.userInfo = userInfo;
	}

	public Integer getSuperb() {
		return superb;
	}

	public void setSuperb(Integer superb) {
		this.superb = superb;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getRecommenderId() {
		return recommenderId;
	}

	public void setRecommenderId(Integer recommenderId) {
		this.recommenderId = recommenderId;
	}

	public String getWorldType() {
		return worldType;
	}

	public void setWorldType(String worldType) {
		this.worldType = worldType;
	}

	public Integer getTypeValid() {
		return typeValid;
	}

	public void setTypeValid(Integer typeValid) {
		this.typeValid = typeValid;
	}

	public Integer getShield() {
		return shield;
	}

	public void setShield(Integer shield) {
		this.shield = shield;
	}

	public Integer getInteracted() {
		return interacted;
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

	public void setInteracted(Integer interacted) {
		this.interacted = interacted;
	}

	@Override
	public Integer getVerifyId() {
		return star;
	}
	
	public String getRecommenderName(){
		return this.recommenderName;
	}
	public void setRecommenderName(String recommenderName){
		this.recommenderName = recommenderName;
	}
	
	public void setAddDate(Date addDate){
		this.addDate = addDate;
	}
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getAddDate(){
		return this.addDate;
	}
	
	public void setModifyDate(Date modifyDate){
		this.modifyDate = modifyDate;
	}
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getModifyDate(){
		return this.modifyDate;
	}
	public Integer getIsSorted() {
		return isSorted;
	}
	public void setIsSorted(Integer isSorted) {
		this.isSorted = isSorted;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}

}

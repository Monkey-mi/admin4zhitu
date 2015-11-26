package com.imzhitu.admin.ztworld.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 织图表数据映射对象
 * 
 * @author zhangbo	2015年11月26日
 *
 */
public class ZTWorld implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8407087511918773995L;
	/**
	 * 主键id
	 * @author zhangbo	2015年11月26日
	 */
	private Integer id;
	
	/**
	 * 描述
	 * @author zhangbo	2015年11月26日
	 */
	private String description;
	
	/**
	 * 短链
	 * @author zhangbo	2015年11月26日
	 */
	private String shortLink;
	
	/**
	 * 织图标签
	 * @author zhangbo	2015年11月26日
	 */
	private String worldLabel;
	
	/**
	 * 织图分类id
	 * @author zhangbo	2015年11月26日
	 */
	private Integer typeId;
	
	/**
	 * 织图分类
	 * @author zhangbo	2015年11月26日
	 */
	private String worldType;
	
	/**
	 * 创建时间
	 * @author zhangbo	2015年11月26日
	 */
	private Date createTime;
	
	/**
	 * 频道id集合，通过“,”分隔，与频道名称一一对应
	 * @author zhangbo	2015年11月26日
	 */
	private String channelIds;
	
	/**
	 * 频道名称集合，通过“,”分隔，与频道id一一对应
	 * @author zhangbo	2015年11月26日
	 */
	private String channelNames;
	
	/**
	 * 作者id，即用户id
	 * @author zhangbo	2015年11月26日
	 */
	private Integer authorId;
	
	/**
	 * 点击次数，即播放次数
	 * @author zhangbo	2015年11月26日
	 */
	private Integer clickCount;
	
	/**
	 * 喜欢次数，即点赞次数
	 * @author zhangbo	2015年11月26日
	 */
	private Integer likeCount;
	
	/**
	 * 评论次数
	 * @author zhangbo	2015年11月26日
	 */
	private Integer commentCount;
	
	/**
	 * 收藏次数
	 * @author zhangbo	2015年11月26日
	 */
	private Integer keepCount;
	
	/**
	 * 经度
	 * @author zhangbo	2015年11月26日
	 */
	private Double longitude;
	
	/**
	 * 纬度
	 * @author zhangbo	2015年11月26日
	 */
	private Double latitude;
	
	/**
	 * 详细地理位置
	 * @author zhangbo	2015年11月26日
	 */
	private String address;
	
	/**
	 * 省份
	 * @author zhangbo	2015年11月26日
	 */
	private String province;
	
	/**
	 * 城市
	 * @author zhangbo	2015年11月26日
	 */
	private String city;
	
	/**
	 * 客户端类型，0：IOS，1：android
	 * @author zhangbo	2015年11月26日
	 */
	private Integer phoneCode;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the shortLink
	 */
	public String getShortLink() {
		return shortLink;
	}

	/**
	 * @param shortLink the shortLink to set
	 */
	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}

	/**
	 * @return the worldLabel
	 */
	public String getWorldLabel() {
		return worldLabel;
	}

	/**
	 * @param worldLabel the worldLabel to set
	 */
	public void setWorldLabel(String worldLabel) {
		this.worldLabel = worldLabel;
	}

	/**
	 * @return the typeId
	 */
	public Integer getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the worldType
	 */
	public String getWorldType() {
		return worldType;
	}

	/**
	 * @param worldType the worldType to set
	 */
	public void setWorldType(String worldType) {
		this.worldType = worldType;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the channelIds
	 */
	public String getChannelIds() {
		return channelIds;
	}

	/**
	 * @param channelIds the channelIds to set
	 */
	public void setChannelIds(String channelIds) {
		this.channelIds = channelIds;
	}

	/**
	 * @return the channelNames
	 */
	public String getChannelNames() {
		return channelNames;
	}

	/**
	 * @param channelNames the channelNames to set
	 */
	public void setChannelNames(String channelNames) {
		this.channelNames = channelNames;
	}

	/**
	 * @return the authorId
	 */
	public Integer getAuthorId() {
		return authorId;
	}
	/**
	 * @param authorId the authorId to set
	 */
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	/**
	 * @return the clickCount
	 */
	public Integer getClickCount() {
		return clickCount;
	}

	/**
	 * @param clickCount the clickCount to set
	 */
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	/**
	 * @return the likeCount
	 */
	public Integer getLikeCount() {
		return likeCount;
	}

	/**
	 * @param likeCount the likeCount to set
	 */
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	/**
	 * @return the commentCount
	 */
	public Integer getCommentCount() {
		return commentCount;
	}

	/**
	 * @param commentCount the commentCount to set
	 */
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	/**
	 * @return the keepCount
	 */
	public Integer getKeepCount() {
		return keepCount;
	}

	/**
	 * @param keepCount the keepCount to set
	 */
	public void setKeepCount(Integer keepCount) {
		this.keepCount = keepCount;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the phoneCode
	 */
	public Integer getPhoneCode() {
		return phoneCode;
	}

	/**
	 * @param phoneCode the phoneCode to set
	 */
	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}
	
	private Integer id;//马甲ID
	private String worldDesc;
	private String worldLabel;
	private String worldLabelIds;
	private Integer author_id;
	private String coverPath;
	private String titlePath;
	private String titleThumbPath;
	private Double longitude;
	private Double latitude;
	private String locationAddr;
	private String province;
	private String city;
	private Integer size;
	private Integer complete;//？
	private Integer htworldId;//织图ID
	private String shortLink;
	private Integer channelId;
	private Date schedula;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getWorldLabelIds() {
		return worldLabelIds;
	}
	public void setWorldLabelIds(String worldLabelIds) {
		this.worldLabelIds = worldLabelIds;
	}
	public Integer getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(Integer author_id) {
		this.author_id = author_id;
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
	public String getLocationAddr() {
		return locationAddr;
	}
	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
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
	public Integer getComplete() {
		return complete;
	}
	public void setComplete(Integer complete) {
		this.complete = complete;
	}
	public Integer getHtworldId() {
		return htworldId;
	}
	public void setHtworldId(Integer htworldId) {
		this.htworldId = htworldId;
	}
	public String getShortLink() {
		return shortLink;
	}
	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Date getSchedula() {
		return schedula;
	}
	public void setSchedula(Date schedula) {
		this.schedula = schedula;
	}
	
	
	private Integer id;//马甲ID
	private String worldDesc;
	private String worldLabel;
	private String worldLabelIds;
	private Integer author_id;
	private String coverPath;
	private String titlePath;
	private String titleThumbPath;
	private Double longitude;
	private Double latitude;
	private String locationAddr;
	private String province;
	private String city;
	private Integer size;
	private Integer complete;//？
	private Integer htworldId;//织图ID
	private String shortLink;
	private Integer channelId;
	private Date schedula;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getWorldLabelIds() {
		return worldLabelIds;
	}
	public void setWorldLabelIds(String worldLabelIds) {
		this.worldLabelIds = worldLabelIds;
	}
	public Integer getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(Integer author_id) {
		this.author_id = author_id;
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
	public String getLocationAddr() {
		return locationAddr;
	}
	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
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
	public Integer getComplete() {
		return complete;
	}
	public void setComplete(Integer complete) {
		this.complete = complete;
	}
	public Integer getHtworldId() {
		return htworldId;
	}
	public void setHtworldId(Integer htworldId) {
		this.htworldId = htworldId;
	}
	public String getShortLink() {
		return shortLink;
	}
	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Date getSchedula() {
		return schedula;
	}
	public void setSchedula(Date schedula) {
		this.schedula = schedula;
	}
	
	
}

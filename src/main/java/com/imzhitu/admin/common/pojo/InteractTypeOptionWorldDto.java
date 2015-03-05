package com.imzhitu.admin.common.pojo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class InteractTypeOptionWorldDto extends AbstractNumberDto{

	private static final long serialVersionUID = 2767462914313015607L;
	private Integer id;				//id
	private Integer worldId;		//织图id
	private String shortLink;		//织图短链
	private Integer userId;			//用户id
	private String userName;		//用户名
	private Integer superb;			//精品标志
	private Integer operatorId;		//最后操作者
	private String operatorName;	//最后操作者名称
	private Integer valid;			//有效性
	private Integer top;			//置顶
	private Date addDate;			//添加时间
	private Date  modifyDate;		//最后修改时间
	
	private String authorAvatar;	//头像
	private Integer userLevel;
	private String userLevelDesc;	
	private Integer clickCount;
	private Integer likeCount;
	private Integer commentCount;
	private String worldLabel;
	private String titleThumbPath;
	private Integer channelId;
	private String channelName;
	private Integer trust;
	
	
	private String trustOperatorName;//添加为信任的人的名字
	private Date trustModifyDate;//最后操作信任字段的时间		
	private Integer trustOperatorId;//添加为信任的人的id
	private Integer typeInteracted;//是否被操作过
	
	private String reView;	//精选点评
	
	
	
	
	public String getReView() {
		return reView;
	}
	public void setReView(String reView) {
		this.reView = reView;
	}
	public Integer getTop() {
		return top;
	}
	public void setTop(Integer top) {
		this.top = top;
	}
	public Integer getTypeInteracted() {
		return typeInteracted;
	}
	public void setTypeInteracted(Integer typeInteracted) {
		this.typeInteracted = typeInteracted;
	}
	public String getTrustOperatorName() {
		return trustOperatorName;
	}
	public void setTrustOperatorName(String trustOperatorName) {
		this.trustOperatorName = trustOperatorName;
	}
	public Date getTrustModifyDate() {
		return trustModifyDate;
	}
	public void setTrustModifyDate(Date trustModifyDate) {
		this.trustModifyDate = trustModifyDate;
	}
	public Integer getTrustOperatorId() {
		return trustOperatorId;
	}
	public void setTrustOperatorId(Integer trustOperatorId) {
		this.trustOperatorId = trustOperatorId;
	}
	public Integer getTrust() {
		return trust;
	}
	public void setTrust(Integer trust) {
		this.trust = trust;
	}
	public String getAuthorAvatar() {
		return authorAvatar;
	}
	public void setAuthorAvatar(String authorAvatar) {
		this.authorAvatar = authorAvatar;
	}
	public Integer getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}
	public String getUserLevelDesc() {
		return userLevelDesc;
	}
	public void setUserLevelDesc(String userLevelDesc) {
		this.userLevelDesc = userLevelDesc;
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
	public String getWorldLabel() {
		return worldLabel;
	}
	public void setWorldLabel(String worldLabel) {
		this.worldLabel = worldLabel;
	}
	public String getTitleThumbPath() {
		return titleThumbPath;
	}
	public void setTitleThumbPath(String titleThumbPath) {
		this.titleThumbPath = titleThumbPath;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getShortLink() {
		return shortLink;
	}
	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getSuperb() {
		return superb;
	}
	public void setSuperb(Integer superb) {
		this.superb = superb;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	

}

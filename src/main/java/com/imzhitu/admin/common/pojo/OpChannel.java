package com.imzhitu.admin.common.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;

public class OpChannel extends AbstractNumberDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4105884798332317238L;
	private Integer id;
	private Integer ownerId;
	private String channelName;
	private String channelTitle;
	private String subtitle;
	private String channelDesc;
	private String channelIcon;
	private String subIcon;
	private Integer channelType;
	private Integer worldCount;
	private Integer childCount;
	private Integer memberCount;
	private Integer superbCount;
	private Long createTime;
	private Long lastModified;
	private Integer childCountBase;
	private Integer valid;
	private Integer serial;
	private Integer danmu;
	private Integer mood;
	private Integer world;

	public OpChannel() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelTitle() {
		return channelTitle;
	}

	public void setChannelTitle(String channelTitle) {
		this.channelTitle = channelTitle;
	}

	public String getChannelIcon() {
		return channelIcon;
	}

	public void setChannelIcon(String channelIcon) {
		this.channelIcon = channelIcon;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public Integer getChildCountBase() {
		return childCountBase;
	}

	public void setChildCountBase(Integer childCountBase) {
		this.childCountBase = childCountBase;
	}

	public String getSubIcon() {
		return subIcon;
	}

	public void setSubIcon(String subIcon) {
		this.subIcon = subIcon;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getChannelDesc() {
		return channelDesc;
	}

	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public Integer getWorldCount() {
		return worldCount;
	}

	public void setWorldCount(Integer worldCount) {
		this.worldCount = worldCount;
	}

	public Integer getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}

	public Integer getSuperbCount() {
		return superbCount;
	}

	public void setSuperbCount(Integer superbCount) {
		this.superbCount = superbCount;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getLastModified() {
		return lastModified;
	}

	public void setLastModified(Long lastModified) {
		this.lastModified = lastModified;
	}

	public Integer getDanmu() {
		return danmu;
	}

	public void setDanmu(Integer danmu) {
		this.danmu = danmu;
	}

	public Integer getMood() {
		return mood;
	}

	public void setMood(Integer mood) {
		this.mood = mood;
	}

	public Integer getWorld() {
		return world;
	}

	public void setWorld(Integer world) {
		this.world = world;
	}
	
}

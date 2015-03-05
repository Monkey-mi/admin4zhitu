package com.imzhitu.admin.common.pojo;

public class OpChannelStarDto extends UserInfoBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7019536974032331140L;
	private Integer channelStarId;
	private Integer channelId;
	private Integer userId;
	private Integer valid;
	private Integer notified;
	private Integer weight;
	
	private String channelName;
	private String channelTitle;
	private String channelIcon;
	
	/**
	 * 记录入选分数
	 * 
	 */
	private Integer superbScore;	//平均几天上精选*3 对应的分数
	private Integer channelScore;	//平均几天上频道*3 对应的分数
	private Integer registerScore;	//注册多少个周/2
	private Integer lastWorldScore;	//最近发图时间*2
	

	public Integer getSuperbScore() {
		return superbScore;
	}

	public void setSuperbScore(Integer superbScore) {
		this.superbScore = superbScore;
	}

	public Integer getChannelScore() {
		return channelScore;
	}

	public void setChannelScore(Integer channelScore) {
		this.channelScore = channelScore;
	}

	public Integer getRegisterScore() {
		return registerScore;
	}

	public void setRegisterScore(Integer registerScore) {
		this.registerScore = registerScore;
	}

	public Integer getLastWorldScore() {
		return lastWorldScore;
	}

	public void setLastWorldScore(Integer lastWorldScore) {
		this.lastWorldScore = lastWorldScore;
	}

	public Integer getChannelStarId() {
		return channelStarId;
	}

	public void setChannelStarId(Integer channelStarId) {
		this.channelStarId = channelStarId;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getNotified() {
		return notified;
	}

	public void setNotified(Integer notified) {
		this.notified = notified;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	

}

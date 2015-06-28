/**
 * 
 */
package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * 数据统计DTO类
 * 
 * @author zhangbo 2015年6月19日
 */
public class OpDataStatisticsDto extends AbstractNumberDto implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5501012793414992211L;
	
	/**
	 * 主键id
	 */
	private Integer id;
	
	/**
	 * 频道ID
	 */
	private Integer channelId;
	
	/**
	 * 频道名称
	 */
	private String channelName;
	
	/**
	 * 每日频道被访问数
	 */
	private Integer pvCount = 0;
	
	/**
	 * 每日频道新增织图数
	 */
	private Integer worldAddCount = 0;
	
	/**
	 * 每日频道新增订阅数
	 */
	private Integer memberAddCount = 0;
	
	/**
	 * 每日频道新增评论数
	 */
	private Integer commentAddCount = 0;
	
	/**
	 * 每日频道新增点赞数
	 */
	private Integer likedAddCount = 0;
	
	/**
	 * 数据收集的日期
	 */
	private Date dataCollectDate;
	
	/**
	 * 查询起始时间
	 */
	private Date startTime;
	
	/**
	 * 查询结束时间
	 */
	private Date endTime;
	
	/**
	 * 排序关键字 
	 */
	private String sortKey;
	
	/**
	 * 正序/倒序
	 */
	private String order;
	
	/**
	 * 频道主题ID
	 */
	private Integer themeId;

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
	 * @return the channelId
	 */
	public Integer getChannelId() {
		return channelId;
	}

	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}

	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * @return the pvCount
	 */
	public Integer getPvCount() {
		return pvCount;
	}

	/**
	 * @param pvCount the pvCount to set
	 */
	public void setPvCount(Integer pvCount) {
		this.pvCount = pvCount;
	}

	/**
	 * @return the worldAddCount
	 */
	public Integer getWorldAddCount() {
		return worldAddCount;
	}

	/**
	 * @param worldAddCount the worldAddCount to set
	 */
	public void setWorldAddCount(Integer worldAddCount) {
		this.worldAddCount = worldAddCount;
	}

	/**
	 * @return the memberAddcount
	 */
	public Integer getMemberAddCount() {
		return memberAddCount;
	}

	/**
	 * @param memberAddcount the memberAddcount to set
	 */
	public void setMemberAddCount(Integer memberAddCount) {
		this.memberAddCount = memberAddCount;
	}

	/**
	 * @return the commentAddCount
	 */
	public Integer getCommentAddCount() {
		return commentAddCount;
	}

	/**
	 * @param commentAddCount the commentAddCount to set
	 */
	public void setCommentAddCount(Integer commentAddCount) {
		this.commentAddCount = commentAddCount;
	}

	/**
	 * @return the likedAddCount
	 */
	public Integer getLikedAddCount() {
		return likedAddCount;
	}

	/**
	 * @param likedAddCount the likedAddCount to set
	 */
	public void setLikedAddCount(Integer likedAddCount) {
		this.likedAddCount = likedAddCount;
	}

	/**
	 * @return the dataCollectDate
	 */
	public Date getDataCollectDate() {
		return dataCollectDate;
	}

	/**
	 * @param dataCollectDate the dataCollectDate to set
	 */
	public void setDataCollectDate(Date dataCollectDate) {
		this.dataCollectDate = dataCollectDate;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the sortKey
	 */
	public String getSortKey() {
		return sortKey;
	}

	/**
	 * @param sortKey the sortKey to set
	 */
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	/**
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * @return the themeId
	 */
	public Integer getThemeId() {
		return themeId;
	}

	/**
	 * @param themeId the themeId to set
	 */
	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}
}

package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 汇总统计POJO
 * </p>
 * 
 * @author tianjie
 * 
 */
public class StatisticsSummary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3193524567591893569L;

	private String date;
	private Long worldCount = 0l;
	private Long childCount = 0l;
	private Long userCount = 0l;

	public StatisticsSummary() {
		super();
	}
	
	public StatisticsSummary(String date, Long worldCount, Long childCount,
			Long userCount) {
		super();
		this.date = date;
		this.worldCount = worldCount;
		this.childCount = childCount;
		this.userCount = userCount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getWorldCount() {
		return worldCount;
	}

	public void setWorldCount(Long worldCount) {
		this.worldCount = worldCount;
	}

	public Long getChildCount() {
		return childCount;
	}

	public void setChildCount(Long childCount) {
		this.childCount = childCount;
	}

	public Long getUserCount() {
		return userCount;
	}

	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}

}

package com.imzhitu.admin.common.pojo;

public class OpChannelTopOneDto extends UserInfoBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6724481147044612616L;

	private Integer topOneId;
	private Integer topId;
	private Integer userId;
	private Integer period;
	private Integer valid;
	private Integer notified;
	private String topDesc;

	public Integer getTopOneId() {
		return topOneId;
	}

	public void setTopOneId(Integer topOneId) {
		this.topOneId = topOneId;
	}

	public Integer getTopId() {
		return topId;
	}

	public void setTopId(Integer topId) {
		this.topId = topId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
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

	public String getTopDesc() {
		return topDesc;
	}

	public void setTopDesc(String topDesc) {
		this.topDesc = topDesc;
	}

}

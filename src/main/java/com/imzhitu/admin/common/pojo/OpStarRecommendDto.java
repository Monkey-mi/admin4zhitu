package com.imzhitu.admin.common.pojo;

public class OpStarRecommendDto extends UserInfoBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3339744739355989323L;
	private Integer id;
	private Integer userId;
	private Integer top;
	private Integer valid;
	private Integer activity;
	public Integer getActivity() {
		return activity;
	}
	public void setActivity(Integer activity) {
		this.activity = activity;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getTop() {
		return top;
	}
	public void setTop(Integer top) {
		this.top = top;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}

}

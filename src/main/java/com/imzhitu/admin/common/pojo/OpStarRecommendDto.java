package com.imzhitu.admin.common.pojo;

import com.hts.web.base.constant.Tag;

public class OpStarRecommendDto extends UserInfoBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3339744739355989323L;
	private Integer id;
	private Integer userId;
	private Integer top;
	private Integer valid;
	private Integer activity;		  // 活跃值
	private Integer star = Tag.FALSE; // 明星标记
	private Integer orderBy;		  // 用以排序的
	
	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}
	
	@Override
	public Integer getVerifyId() {
		return star;
	}
	
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

package com.imzhitu.admin.common.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;

public class InteractLikeFollowCommentLabel extends AbstractNumberDto{
	private static final long serialVersionUID = -6682788418942749941L;
	private Integer id;
	private Integer labelId;
	private String labelName;
	private Integer type;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLabelId() {
		return labelId;
	}
	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}

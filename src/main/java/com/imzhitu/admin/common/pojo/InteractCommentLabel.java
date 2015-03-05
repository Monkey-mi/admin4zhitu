package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

/**
 * 评论标签POJO对象
 * @author ztj
 *
 */
public class InteractCommentLabel implements Serializable {

	private static final long serialVersionUID = 3325651237285547891L;

	private Integer id;
	private String labelName;
	private Integer groupId;
	private Boolean selected;
	
	public InteractCommentLabel() {
		super();
	}
	
	public InteractCommentLabel(Integer id, String labelName, Integer groupId) {
		super();
		this.id = id;
		this.labelName = labelName;
		this.groupId = groupId;
	}
	
	public InteractCommentLabel(String labelName, Integer groupId) {
		super();
		this.labelName = labelName;
		this.groupId = groupId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	
}

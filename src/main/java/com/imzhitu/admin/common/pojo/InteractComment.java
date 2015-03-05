package com.imzhitu.admin.common.pojo;

import java.io.Serializable;


public class InteractComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -639486415773303002L;
	private Integer id;
	private String content;
	private Integer count;
	private Integer labelId;

	public InteractComment() {
		super();
	}

	public InteractComment(Integer id, String content, Integer count,
			Integer labelId) {
		this.id = id;
		this.content = content;
		this.count = count;
		this.labelId = labelId;
	}

	public InteractComment(String content, Integer count, Integer labelId) {
		this.content = content;
		this.count = count;
		this.labelId = labelId;
	}
	
	public InteractComment(Integer id ,String content, Integer labelId) {
		this.id = id;
		this.content = content;
		this.labelId = labelId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}


}

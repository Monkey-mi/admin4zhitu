package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

public class InteractCommentLabelId implements Serializable{

	private static final long serialVersionUID = 8336902062123401810L;
	private Integer id;
	private Integer lableId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLableId() {
		return lableId;
	}
	public void setLableId(Integer lableId) {
		this.lableId = lableId;
	}


	
}

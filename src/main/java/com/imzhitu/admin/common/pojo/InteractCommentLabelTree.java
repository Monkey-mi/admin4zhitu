package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 评论标签树形结构数据传输对象
 * </p>
 * 
 * 创建时间：2014-2-11
 * 
 * @author tianjie
 * 
 */
public class InteractCommentLabelTree implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1200637569286882966L;

	private Integer id;
	private String text;
	private String state = "closed";
	private Boolean checked = false;
	private List<InteractCommentLabelTree> children = new ArrayList<InteractCommentLabelTree>();
	
	public InteractCommentLabelTree() {
		super();
	}
	
	public InteractCommentLabelTree(Integer id, String text, String state,
			Boolean checked) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
		this.checked = checked;
	}

	public InteractCommentLabelTree(Integer id, String text, String state) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public List<InteractCommentLabelTree> getChildren() {
		return children;
	}

	public void setChildren(List<InteractCommentLabelTree> children) {
		this.children = children;
	}
	
	public void addChildren(InteractCommentLabelTree label) {
		this.children.add(label);
	}
	
	public void removeChildren(int index) {
		this.children.remove(index);
	}

}

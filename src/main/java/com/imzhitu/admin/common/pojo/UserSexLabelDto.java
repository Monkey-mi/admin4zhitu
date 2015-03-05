package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户性别标签数据传输对象，用于构建combotree
 * </p>
 * 
 * 创建时间：2014-2-5
 * @author tianjie
 * 
 */
public class UserSexLabelDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4952556221884280315L;

	private Integer id;
	private String text;
	private String state = "closed";
	private List<UserLabelDto> children = new ArrayList<UserLabelDto>();

	public UserSexLabelDto() {
		super();
	}
	
	public UserSexLabelDto(Integer id, String text) {
		super();
		this.id = id;
		this.text = text;
	}
	
	public UserSexLabelDto(Integer id, String text, String state) {
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
	
	public List<UserLabelDto> getChildren() {
		return children;
	}

	public void setChildren(List<UserLabelDto> children) {
		this.children = children;
	}

	public void addChildren(UserLabelDto label) {
		this.children.add(label);
	}
	
	public void removeChildren(int index) {
		this.children.remove(index);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	

}

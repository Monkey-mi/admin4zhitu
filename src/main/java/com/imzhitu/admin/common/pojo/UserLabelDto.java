package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 用户标签数据传输对象，用于构建combotree
 * </p>
 * 
 * 创建时间：2014-2-5
 * @author tianjie
 *
 */
public class UserLabelDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1015541765520973190L;
	private Integer id;
	private String text;
	private Integer labelSex;

	public UserLabelDto() {
		super();
	}

	public UserLabelDto(Integer id, String text, Integer labelSex) {
		super();
		this.id = id;
		this.text = text;
		this.labelSex = labelSex;
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

	public Integer getLabelSex() {
		return labelSex;
	}

	public void setLabelSex(Integer labelSex) {
		this.labelSex = labelSex;
	}
	
}

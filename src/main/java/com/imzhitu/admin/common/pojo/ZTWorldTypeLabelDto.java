package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 织图分类标签DTO
 * </p>
 * 
 * 创建时间：2014-1-16
 * @author ztj
 *
 */
public class ZTWorldTypeLabelDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4343388391456776784L;
	private Integer id;
	private String text;
	private String state;
	
	public ZTWorldTypeLabelDto(Integer id, String text, String state) {
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
	
	
	
	
}

package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

public class UserMsgDanmu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1055970574783148373L;
	private Integer id;
	private String text;
	private String color = "white";
	private Integer size = 0;
	private Integer position = 0;
	private Integer time = 1;
	
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

}

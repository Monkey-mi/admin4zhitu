package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * 织图标签树
 * @author zxx
 *
 */
public class InteractWorldLabelTreeDto implements Serializable{

	private static final long serialVersionUID = 7741594721137584343L;
	private Integer id;
	private String text;
	private List<InteractWorldLabelTreeDto> children= new ArrayList<InteractWorldLabelTreeDto>();
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return this.id;
	}
	
	public void setText(String text){
		this.text = text;
	}
	public String getText(){
		return this.text;
	}
	

	
	public void setChildren(List<InteractWorldLabelTreeDto> children){
		this.children = children;
	}
	public List<InteractWorldLabelTreeDto> getChildren(){
		return this.children;
	}
	
	public InteractWorldLabelTreeDto(){
		super();
	}
	public InteractWorldLabelTreeDto(Integer id, String text) {
		super();
		this.id = id;
		this.text = text;
	}
	

}

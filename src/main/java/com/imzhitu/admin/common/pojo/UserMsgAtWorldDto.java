package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 织图描述中的@数据
 * @author mishengliang
 *11-11-2015
 */
public class UserMsgAtWorldDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8595901535823938601L;
	
	private Integer id;
	private Integer worldId;
	private Integer atId;
	private String atName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getWorldId() {
		return worldId;
	}
	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}
	public Integer getAtId() {
		return atId;
	}
	public void setAtId(Integer atId) {
		this.atId = atId;
	}
	public String getAtName() {
		return atName;
	}
	public void setAtName(String atName) {
		this.atName = atName;
	}
	
	
}

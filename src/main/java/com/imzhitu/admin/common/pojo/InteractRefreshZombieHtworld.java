package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class InteractRefreshZombieHtworld extends AbstractNumberDto implements Serializable{
	private static final long serialVersionUID = -2977414833914257921L;
	
	private Integer wid;		//织图id 
	private Date refreshDate;	//刷新时间，将创建时间刷新为这个时间
	private Integer uid;		//用户id
	private Integer cid;		//评论id

	
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Integer getWid() {
		return wid;
	}
	public void setWid(Integer wid) {
		this.wid = wid;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getRefreshDate() {
		return refreshDate;
	}
	public void setRefreshDate(Date refreshDate) {
		this.refreshDate = refreshDate;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}

	
	

}

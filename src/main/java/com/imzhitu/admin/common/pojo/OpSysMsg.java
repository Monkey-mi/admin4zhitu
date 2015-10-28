package com.imzhitu.admin.common.pojo;

import java.util.Date;

/**
 * 系统通知对象 
 * ORM对象，用于记录发送的系统通知
 * 
 * @author zhangbo	2015年9月7日
 * @author lynch 2015-10-26
 *
 */
public class OpSysMsg {

	private Integer id;
	private Integer recipientId;
	private Date msgDate;
	private String content;
	private Integer objType;
	private Integer objId;
	private String objMeta;
	private String objMeta2;
	private String thumbPath;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(Integer recipientId) {
		this.recipientId = recipientId;
	}

	public Date getMsgDate() {
		return msgDate;
	}

	public void setMsgDate(Date msgDate) {
		this.msgDate = msgDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getObjType() {
		return objType;
	}

	public void setObjType(Integer objType) {
		this.objType = objType;
	}

	public Integer getObjId() {
		return objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public String getObjMeta() {
		return objMeta;
	}

	public void setObjMeta(String objMeta) {
		this.objMeta = objMeta;
	}

	public String getObjMeta2() {
		return objMeta2;
	}

	public void setObjMeta2(String objMeta2) {
		this.objMeta2 = objMeta2;
	}

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}


}

package com.imzhitu.admin.common.pojo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * APP换量链接POJO
 * 
 * 创建时间:2015-09-07
 * @author lynch
 *
 */
public class OpAdAppLinkRecord extends AbstractNumberDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7682599561663064370L;
	private Integer id;
	private String recordIp;
	private Date recordDate;
	private Integer appId;

	public OpAdAppLinkRecord() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRecordIp() {
		return recordIp;
	}

	public void setRecordIp(String recordIp) {
		this.recordIp = recordIp;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

}

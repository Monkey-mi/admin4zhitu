package com.imzhitu.admin.common.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;

public class ZTWorldSubtitle extends AbstractNumberDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8973090608065297903L;

	private Integer id;
	private String subtitle;
	private String subtitleEn;
	private String transTo = "en";
	private Integer serial;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getSubtitleEn() {
		return subtitleEn;
	}

	public void setSubtitleEn(String subtitleEn) {
		this.subtitleEn = subtitleEn;
	}

	public String getTransTo() {
		return transTo;
	}

	public void setTransTo(String transTo) {
		this.transTo = transTo;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	
}

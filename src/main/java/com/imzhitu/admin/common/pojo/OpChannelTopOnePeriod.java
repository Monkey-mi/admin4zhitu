package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

import com.hts.web.common.pojo.AbstractNumberDto;

public class OpChannelTopOnePeriod extends AbstractNumberDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1776279239236759483L;
	private Integer period;
	private String text;

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public String getText() {
		if(text != null) {
			return text;
		}
		return period.toString();
	}

	public void setText(String text) {
		this.text = text;
	}
	
	

}

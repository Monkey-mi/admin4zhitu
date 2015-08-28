package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class StatisticsRegisterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3555027318790826286L;

	private Long androidCnt;
	private Long iosCnt;
	private Long total;
	private Date start;
	private Date end;
	
	public Long getAndroidCnt() {
		return androidCnt;
	}

	public void setAndroidCnt(Long androidCnt) {
		this.androidCnt = androidCnt;
	}

	public Long getIosCnt() {
		return iosCnt;
	}

	public void setIosCnt(Long iosCnt) {
		this.iosCnt = iosCnt;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}

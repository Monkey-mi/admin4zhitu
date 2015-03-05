package com.imzhitu.admin.common.pojo;

import java.util.Date;
import com.hts.web.common.pojo.AbstractNumberDto;

public class OpChannelTopOne extends AbstractNumberDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6399689635447696139L;
	private Integer id;		//id
	private Integer topId;	//top类型
	private Integer userId;	//用户id
	private Integer period;	//第几期的top榜
	private Integer valid;	//有效性
	private Integer notified;	//通知标志，默认为0
	
	private Date beginDate;	//查询条件的开始时间
	private Date endDate;	//查询条件的截至时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTopId() {
		return topId;
	}

	public void setTopId(Integer topId) {
		this.topId = topId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getNotified() {
		return notified;
	}

	public void setNotified(Integer notified) {
		this.notified = notified;
	}

	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}

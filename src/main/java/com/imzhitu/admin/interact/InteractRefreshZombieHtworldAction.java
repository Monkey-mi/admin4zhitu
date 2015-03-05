package com.imzhitu.admin.interact;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.interact.service.InteractRefreshZombieHtWorldService;

public class InteractRefreshZombieHtworldAction extends BaseCRUDAction{

	private static final long serialVersionUID = -674187429903525463L;
	
	private Integer days;	//多少天之前到现在所发的织图
	private Date refreshDate;	//更新时间
	private String uidsStr;		//worldId字符串
	private Integer minDaySpan;	//最小时间间隔，单位：天
	private Integer maxDaySpan;	//最大时间间隔，单位：天
	
	@Autowired
	InteractRefreshZombieHtWorldService service;
	
	public Integer getMinDaySpan() {
		return minDaySpan;
	}

	public void setMinDaySpan(Integer minDaySpan) {
		this.minDaySpan = minDaySpan;
	}

	public Integer getMaxDaySpan() {
		return maxDaySpan;
	}

	public void setMaxDaySpan(Integer maxDaySpan) {
		this.maxDaySpan = maxDaySpan;
	}

	public Date getRefreshDate() {
		return refreshDate;
	}

	public void setRefreshDate(Date refreshDate) {
		this.refreshDate = refreshDate;
	}

	public String getUidsStr() {
		return uidsStr;
	}

	public void setUidsStr(String uidsStr) {
		this.uidsStr = uidsStr;
	}


	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	
	public String queryZombieHtWorld(){
		try{
			service.queryZombieHtworldList(days, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String updateZombieHtWorld(){
		try{
			service.updateZombieHtworld(uidsStr, refreshDate);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String autoUpdateZombieHtWorld(){
		try{
			Date now = new Date();
			Calendar rightnow = Calendar.getInstance(Locale.CHINA);
			rightnow.setTime(now);
			rightnow.set(Calendar.DATE, rightnow.get(Calendar.DATE) - days);
			int daySpan = maxDaySpan > minDaySpan ? minDaySpan + (int)(Math.random()*1117)%(maxDaySpan-minDaySpan) : maxDaySpan + (int)(Math.random()*1117)%(minDaySpan - maxDaySpan);
			daySpan = daySpan > 0 ? daySpan : -1*daySpan;
			service.updateZombieHtworldByUserIds(uidsStr,new Date(rightnow.getTimeInMillis()),daySpan);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

}

package com.imzhitu.admin.interact;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.interact.service.InteractZombieWorldSchedulaService;

public class InteractZombieWorldSchedulaAction extends BaseCRUDAction{

	private static final long serialVersionUID = -8344442720459432793L;
	
	@Autowired
	private InteractZombieWorldSchedulaService zombieWorldSchedulaService;
	
	private Integer id;
	private Integer zombieWorldId;
	private Date schedula;
	private Integer valid;
	private Integer finished;
	private String idsStr;
	private Integer minuteTimeSpan;
	private String zombieWorldIdsStr;
	
	public String getZombieWorldIdsStr() {
		return zombieWorldIdsStr;
	}

	public void setZombieWorldIdsStr(String zombieWorldIdsStr) {
		this.zombieWorldIdsStr = zombieWorldIdsStr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getZombieWorldId() {
		return zombieWorldId;
	}

	public void setZombieWorldId(Integer zombieWorldId) {
		this.zombieWorldId = zombieWorldId;
	}

	public Date getSchedula() {
		return schedula;
	}

	public void setSchedula(Date schedula) {
		this.schedula = schedula;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getFinished() {
		return finished;
	}

	public void setFinished(Integer finished) {
		this.finished = finished;
	}

	public String getIdsStr() {
		return idsStr;
	}

	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}

	public Integer getMinuteTimeSpan() {
		return minuteTimeSpan;
	}

	public void setMinuteTimeSpan(Integer minuteTimeSpan) {
		this.minuteTimeSpan = minuteTimeSpan;
	}

	public String queryZombieWorldSchedula(){
		try{
			zombieWorldSchedulaService.queryZombieWorldSchedula(id, valid, finished, zombieWorldId, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchInsertZombieWorldSchedula(){
		try{
			zombieWorldSchedulaService.batchInsertZombieWorldSchedual(zombieWorldIdsStr, schedula, minuteTimeSpan, getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchDeleteZombieWorldSchedula(){
		try{
			zombieWorldSchedulaService.batchDeleteZombieWorldSchedula(idsStr);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	
}

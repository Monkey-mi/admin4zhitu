package com.imzhitu.admin.op;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.op.service.OpZombieDegreeUserLevelService;

public class OpZombieDegreeUserLevelAction extends BaseCRUDAction{
	private static final long serialVersionUID = -8129957739362002834L;
	
	@Autowired
	private OpZombieDegreeUserLevelService zombieDegreeUserLevelService;
	
	private Integer id;
	private Integer userLevelId;
	private Integer zombieDegreeId;
	private String idsStr;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserLevelId() {
		return userLevelId;
	}
	public void setUserLevelId(Integer userLevelId) {
		this.userLevelId = userLevelId;
	}
	public Integer getZombieDegreeId() {
		return zombieDegreeId;
	}
	public void setZombieDegreeId(Integer zombieDegreeId) {
		this.zombieDegreeId = zombieDegreeId;
	}
	public String getIdsStr() {
		return idsStr;
	}
	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}
	
	public String insertZombieDegreeUserLevel(){
		try{
			zombieDegreeUserLevelService.insertZombieDegreeUserLevel(zombieDegreeId, userLevelId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchDeleteZombieDegreeUserLevel(){
		try{
			zombieDegreeUserLevelService.batchDeleteZombieDegreeUserLevel(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String queryZombieDegreeUserLevel(){
		try{
			zombieDegreeUserLevelService.queryZombieDegreeUserLevel(id, zombieDegreeId, userLevelId, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
}

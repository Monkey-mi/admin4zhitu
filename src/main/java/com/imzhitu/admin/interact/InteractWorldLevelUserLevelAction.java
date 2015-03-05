package com.imzhitu.admin.interact;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.interact.service.InteractWorldLevelUserLevelService;

public class InteractWorldLevelUserLevelAction extends BaseCRUDAction{

	private static final long serialVersionUID = 3639258854285547197L;
	
	private String idsStr;		//id字符串
	private Integer userLevelId;	//用户等级id
	private Integer worldLevelId;	//织图等级id
	
	public Integer getUserLevelId() {
		return userLevelId;
	}

	public void setUserLevelId(Integer userLevelId) {
		this.userLevelId = userLevelId;
	}

	public Integer getWorldLevelId() {
		return worldLevelId;
	}

	public void setWorldLevelId(Integer worldLevelId) {
		this.worldLevelId = worldLevelId;
	}

	public String getIdsStr() {
		return idsStr;
	}

	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}

	@Autowired
	private InteractWorldLevelUserLevelService service;
	
	//分页查询
	public String queryWorldLevelUserLevel(){
		try{
			service.queryWorldLevelUserLevel(maxId, page,rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	//批量删除
	public String delWorldLevelUserLevel(){
		try{
			service.delWorldLevelUserLevel(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(OptResult.DELETE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	//增加
	public String addWorldLevelUserLevel(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			service.addWorldLevelUserLevel(worldLevelId, userLevelId, user.getId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(OptResult.ADD_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}

}

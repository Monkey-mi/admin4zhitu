package com.imzhitu.admin.op;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.op.service.OpZombieService;

public class OpZombieAction extends BaseCRUDAction{
	private static final long serialVersionUID = -5383834291840935779L;

	@Autowired
	private OpZombieService zombieService;
	
	private Integer userId;
	private String idsStr;
	private Integer degreeId;
	private String zombieInfoJSON;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getIdsStr() {
		return idsStr;
	}

	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}

	public Integer getDegreeId() {
		return degreeId;
	}

	public void setDegreeId(Integer degreeId) {
		this.degreeId = degreeId;
	}
	
	public String getZombieInfoJSON() {
		return zombieInfoJSON;
	}

	public void setZombieInfoJSON(String zombieInfoJSON) {
		this.zombieInfoJSON = zombieInfoJSON;
	}


	/**
	 * 分页查询
	 * @return
	 */
	public String queryZombie(){
		try{
			zombieService.queryZombie(userId, degreeId, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 批量删除
	 * @return
	 */
	public String batchDeleteZombie(){
		try{
			zombieService.batchDeleteZombie(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertZombie(){
		try{
			zombieService.insertZombie(userId, degreeId, null, null);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 更新性别和签名
	 * @return
	 */
	public String updateSexAndSignature(){
		try{
			zombieService.updateSexAndSignature(zombieInfoJSON);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
}

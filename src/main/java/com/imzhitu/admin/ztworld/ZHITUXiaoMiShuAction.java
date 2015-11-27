package com.imzhitu.admin.ztworld;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.ztworld.service.ZHITUXiaoMiShuService;

public class ZHITUXiaoMiShuAction extends BaseCRUDAction{
	
	private static final long serialVersionUID = 7298471167380931288L;
	
	private Integer worldId;
	private String worldDesc;
	private String idsStr;
	private Integer authorId;
	private Integer limitNum;
	private String worldLable;
	
	@Autowired
	private ZHITUXiaoMiShuService xiaoMiShuService;
	
	public void setWorldLable(String worldLable){
		this.worldLable = worldLable;
	}
	public String getWorldLable(){
		return this.worldLable;
	}
	
	public void setLimitNum(Integer limitNum){
		this.limitNum = limitNum;
	}
	public Integer getLimitNum(){
		return this.limitNum;
	}
	
	public void setAuthorId(Integer authorId){
		this.authorId = authorId;
	}
	public Integer getAuthorId(){
		return this.authorId;
	}
	
	public void setIdsStr(String idsStr){
		this.idsStr = idsStr;
	}
	public String getIdsStr(){
		return this.idsStr;
	}
	
	public void setWorldId(Integer worldId){
		this.worldId = worldId;
	}
	public Integer getWorldId(){
		return this.worldId;
	}
	
	public void setWorldDesc(String worldDesc){
		this.worldDesc = worldDesc;
	}
	public String getWorldDesc(){
		return this.worldDesc;
	}
	
	public void setXiaoMiShuService(ZHITUXiaoMiShuService xiaoMiShuService){
		this.xiaoMiShuService = xiaoMiShuService;
	}
	
	public ZHITUXiaoMiShuService getXiaoMiShuService(){
		return this.xiaoMiShuService;
	}
	
	public String delWorldByWId(){
		try{
			xiaoMiShuService.delZTWorldByIds(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String updateWorldDescByWid(){
		try{
			xiaoMiShuService.updateWorldDescByWid(worldId, worldDesc);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String updateWorldLabelByWid(){
		try{
			xiaoMiShuService.updateWorldLabelByWid(worldId, worldLable);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
}

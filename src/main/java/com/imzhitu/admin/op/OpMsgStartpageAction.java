package com.imzhitu.admin.op;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.op.service.OpMsgStartpageService;

public class OpMsgStartpageAction extends BaseCRUDAction{
	
	private static final long serialVersionUID = 6508699712220766896L;
	private Integer id;
	private Integer valid;
	private Integer type;
	private String path;
	private String link;
	private String idsStr;
	private Integer isCache;
	private Date beginDate;
	private Date endDate;
	
	@Autowired
	private OpMsgStartpageService startpageService;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getIdsStr() {
		return idsStr;
	}
	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}
	public Integer getIsCache() {
		return isCache;
	}
	public void setIsCache(Integer isCache) {
		this.isCache = isCache;
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
	
	public String insertMsgStartpage(){
		try{
			startpageService.insertMsgStartpage(path, type, link, beginDate, endDate, getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	public String batchDeleteMsgStartpage(){
		try{
			startpageService.batchDeleteMsgStartpage(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String updateMsgStartpage(){
		try{
			startpageService.updateMsgStartpage(id, path, type, link, valid, beginDate, endDate, getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchUpdateMsgStartpageValid(){
		try{
			startpageService.batchUpdateMsgStartpageValid(idsStr, valid, getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String queryMsgStartpage(){
		try{
			startpageService.queryMsgStartpage(id, type, valid, isCache, beginDate, endDate, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String updateMsgStartpageCache(){
		try{
			startpageService.updateMsgStartpageCache(idsStr);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

}

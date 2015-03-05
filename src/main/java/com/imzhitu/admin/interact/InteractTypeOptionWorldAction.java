package com.imzhitu.admin.interact;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.interact.service.InteractTypeOptionWorldService;

public class InteractTypeOptionWorldAction extends BaseCRUDAction{

	private static final long serialVersionUID = 7390302764896612813L;
	private Integer id;
	private Integer userId;
	private Integer worldId;
	private Integer superb;
	private Integer valid;
	private String idsStr;
	private String widsStr;
	private String schedula;
	private Integer top;	//置顶
	private String review;	//精选点评
	
	
	

	

	@Autowired
	private InteractTypeOptionWorldService service;
	
	public String addTypeOptionWorld(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			service.insertTypeOptionWorld(worldId, userId, Tag.TRUE, Tag.FALSE, user.getId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(OptResult.ADD_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String queryTypeOptionWorldForList(){
		try{
			service.queryTypeOptionWorldForList(maxId, page, rows, id, worldId, userId, valid, superb,top, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String reSort(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String[] wids = request.getParameterValues("reIndexId");
			service.reSort(wids, df.parse(schedula), user.getId());
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String delTypeOptionWorldByIds(){
		try{
			service.delTypeOptionWorldByIds(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(OptResult.DELETE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String delTypeOptionWorldByWIds(){
		try{
			service.delTypeOptionWorldByWIds(widsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(OptResult.DELETE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String updateTypeOptionWorld(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			service.updateTypeOptionWorld(id, worldId, userId, valid, superb,top, user.getId());
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(OptResult.UPDATE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新精选点评
	 * @return
	 */
	public String updateTypeOptionWorldReview(){
		try{
			service.updateReview(worldId, review);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(OptResult.UPDATE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getWorldId() {
		return worldId;
	}
	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}
	public Integer getSuperb() {
		return superb;
	}
	public void setSuperb(Integer superb) {
		this.superb = superb;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public String getIdsStr() {
		return idsStr;
	}
	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}
	public String getWidsStr() {
		return widsStr;
	}
	public void setWidsStr(String widsStr) {
		this.widsStr = widsStr;
	}
	public String getSchedula() {
		return schedula;
	}

	public void setSchedula(String schedula) {
		this.schedula = schedula;
	}
	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}
	
	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
}

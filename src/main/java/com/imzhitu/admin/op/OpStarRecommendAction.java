package com.imzhitu.admin.op;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.op.service.OpStarRecommendService;

public class OpStarRecommendAction extends BaseCRUDAction{

	private static final long serialVersionUID = 4332608874077457873L;
	
	@Autowired
	private OpStarRecommendService opStarRecommendService;
	
	/**
	 * 分页查询
	 * @return
	 */
	public String queryStarRecommendForTable(){
		try{
			opStarRecommendService.queryStarRecommend(maxId, page, rows, jsonMap, id, userId, top, valid);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 置顶与取消置顶
	 * @return
	 */
	public String toBeTop(){
		try{
			opStarRecommendService.updateStarRecommend(id, userId, top, valid,activity);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除记录
	 * @return
	 */
	public String delStarRecommend(){
		try{
			opStarRecommendService.deleteStarRecommend(id, userId, top, valid);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 批量删除记录
	 * @return
	 */
	public String batchDelStarRecommend(){
		try{
			if(idStr != null && !"".equals(idStr.trim())){
				Integer[] idsArray = StringUtil.convertStringToIds(idStr);
				for(int i=0; i<idsArray.length; i++){
					opStarRecommendService.deleteStarRecommend(idsArray[i], null, null,null);
				}
			}
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 增加
	 * @return
	 */
	public String addStarRecommend(){
		try{
			opStarRecommendService.insertStarRecommend(userId, Tag.FALSE, Tag.TRUE);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	private Integer userId;
	private Integer id;
	private Integer valid;
	private Integer top;
	private String idStr;
	private Integer activity;

	public Integer getActivity() {
		return activity;
	}

	public void setActivity(Integer activity) {
		this.activity = activity;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
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
	public Integer getTop() {
		return top;
	}
	public void setTop(Integer top) {
		this.top = top;
	}
	public String getIdStr() {
		return idStr;
	}
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}
	

}

package com.imzhitu.admin.op;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.op.service.OpStarRecommendSchedulaService;

public class OpStarRecommendSchedulaAction extends BaseCRUDAction{
	private static final long serialVersionUID = 9016453298196440036L;
	
	@Autowired
	private OpStarRecommendSchedulaService starRecommendSchedulaService;
	
	/**
	 * 分页查询
	 * @return
	 */
	public String queryStarRecommendSchedula(){
		try{
			starRecommendSchedulaService.queryStarRecommendSchedula(maxId, page, rows, jsonMap, id, osrId, userId, valid, addDate, modifyDate,top,finish);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 增加
	 * @return
	 */
	public String addStarRecommendSchedula(){
		try{
			starRecommendSchedulaService.insertStarRecommendSchedula(osrId, userId, schedula, getCurrentLoginUserId(), Tag.TRUE,top);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String batchDelStarRecommendSchedula(){
		try{
			if(idStr != null && !"".equals(idStr.trim())){
				Integer[] idArray = StringUtil.convertStringToIds(idStr);
				for(int i=0; i<idArray.length; i++){
					starRecommendSchedulaService.deleteStarRecommendSchedula(idArray[i], null, null, null);
				}
			}
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String reSort(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String[] osrIds = request.getParameterValues("reIndexId");
			starRecommendSchedulaService.reSort(osrIds, schedula, user.getId(),timeMinute);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
		
	}
	
	private Integer id;
	private Integer userId;
	private Integer osrId;
	private Integer valid;
	private Date schedula;
	private Date addDate;
	private Date modifyDate;
	private String idStr;
	private Integer timeMinute;	//持续置顶时间
	private Integer top;
	private Integer finish;
	

	public Integer getFinish() {
		return finish;
	}

	public void setFinish(Integer finish) {
		this.finish = finish;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public Integer getTimeMinute() {
		return timeMinute;
	}

	public void setTimeMinute(Integer timeMinute) {
		this.timeMinute = timeMinute;
	}

	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
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
	public Integer getOsrId() {
		return osrId;
	}
	public void setOsrId(Integer osrId) {
		this.osrId = osrId;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public Date getSchedula() {
		return schedula;
	}
	public void setSchedula(Date schedula) {
		this.schedula = schedula;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

}

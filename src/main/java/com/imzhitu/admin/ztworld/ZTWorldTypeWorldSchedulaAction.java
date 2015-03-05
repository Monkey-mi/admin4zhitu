package com.imzhitu.admin.ztworld;

//import java.text.SimpleDateFormat;
import java.util.Date;







import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.ztworld.service.ZTWorldTypeWorldSchedulaService;
import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;


public class ZTWorldTypeWorldSchedulaAction extends BaseCRUDAction {
	
	private static final long serialVersionUID = -5771681156819965844L;
	
	private Integer type_world_id;
	private Date schedula;
	private Integer complete;
	private String idStr;
	private Date maxSchedula;
	private Integer timeType;//时间类型，0计划时间，1，添加时间，2修改时间.用来代表beginTime、和endTime的类型
	private Date beginTime;
	private Date endTime;
	
	@Autowired
	private ZTWorldTypeWorldSchedulaService typeWorldSchedulaService;
	
	public void setTimeType(Integer timeType){
		this.timeType = timeType;
	}
	public Integer getTimeType(){
		return this.timeType;
	}
	
	public void setBeginTime(Date beginTime){
		this.beginTime = beginTime;
	}
	public Date getBeginTime(){
		return this.beginTime;
	}
	
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	public Date getEndTime(){
		return this.endTime;
	}
	
	public void setTypeWorldSchedulaService(ZTWorldTypeWorldSchedulaService typeWorldSchedulaService){
		this.typeWorldSchedulaService = typeWorldSchedulaService;
	}
	public ZTWorldTypeWorldSchedulaService getTypeWorldSchedulaService(){
		return this.typeWorldSchedulaService;
	}
	public void setType_world_id(Integer type_world_id){
		this.type_world_id = type_world_id;
	}
	public Integer getType_world_id(){
		return this.type_world_id;
	}

	public void setSchedula(Date schedula){
		this.schedula = schedula;
	}

	public Date getSchedula(){
		return this.schedula;
	}
	public Integer getComplete(){
		return this.complete;
	}
	public void setComplete(Integer complete){
		this.complete = complete;
	}
	public void setIdStr(String idStr){
		this.idStr = idStr;
	}
	public String getIdStr(){
		return this.idStr;
	}
	public Date getMaxSchedula(){
		return this.maxSchedula;
	}
	public void setMaxSchedula(Date maxSchedula){
		this.maxSchedula = maxSchedula;
	}
	
	public String queryTypeWorldSchedula(){
		try{
			typeWorldSchedulaService.queryTypeWorldSchedula(maxSchedula,type_world_id,timeType,beginTime,endTime, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	public String addTypeWorldSchedula(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			typeWorldSchedulaService.addTypeWorldSchedula(type_world_id, schedula, user.getId(),complete);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 批量删除
	 * @return
	 */
	public String delTypeWorldSchedulaByIds(){
		try{
			typeWorldSchedulaService.delTypeWorldSchedulaByIds(idStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS,jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(OptResult.DELETE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 重新排序
	 */
	public String reSort(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String[] wids = request.getParameterValues("reIndexId");
			typeWorldSchedulaService.reSort(wids, schedula, user.getId());
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
}

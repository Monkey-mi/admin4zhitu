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
import com.imzhitu.admin.op.service.OpChannelWorldSchedulaService;

public class OpChannelWorldSchedulaAction extends BaseCRUDAction{

	private static final long serialVersionUID = -5809937833246743576L;
	private Integer id;			//id
	private Integer userId;		//用户id
	private Integer worldId;	//织图id
	private Integer channelId;	//频道id
	private Integer finish;		//完成标志位
	private Integer valid;		//有效标志位
	private Date addDate;		//添加时间
	private Date modifyDate;	//最后修改时间
	private String idsStr;		//id 字符串
	private Date schedula;		//计划时间字符串
	private String superbWids;		//计划时间字符串
	private Integer minuteTimeSpan;//计划时间间隔

	
	@Autowired
	private OpChannelWorldSchedulaService service;
	
	public OpChannelWorldSchedulaService getService() {
		return service;
	}

	public void setService(OpChannelWorldSchedulaService service) {
		this.service = service;
	}

	
	
	public Date getSchedula() {
		return schedula;
	}

	public void setSchedula(Date schedula) {
		this.schedula = schedula;
	}

	public String queryChannelWorldSchedulaForList(){
		try{
			service.queryChannelWorldSchedulaForList(maxId, page, rows, id, userId, worldId, channelId, finish, valid, addDate, modifyDate, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String delChannelWorldSchedula(){
		try{
			service.delChannelWorldSchedula(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(OptResult.DELETE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchAddChannelWorldSchedula(){
		try{
			String[] wids = StringUtil.convertStringToStrs(request.getParameter("wids"));
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			service.batchAddChannelWorldSchedula(wids, getSuperbWids(), schedula,minuteTimeSpan, channelId, Tag.FALSE, Tag.TRUE, user.getId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(OptResult.ADD_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 重新排序
	 */
	public String reSort(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String[] ids = request.getParameterValues("reIndexId");
			service.reSort(ids, schedula,minuteTimeSpan, user.getId());
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
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
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Integer getFinish() {
		return finish;
	}
	public void setFinish(Integer finish) {
		this.finish = finish;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
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
	public String getIdsStr() {
		return idsStr;
	}
	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}

	/**
	 * @return the superbWids
	 */
	public String getSuperbWids() {
	    return superbWids;
	}

	/**
	 * @param superbWids the superbWids to set
	 */
	public void setSuperbWids(String superbWids) {
	    this.superbWids = superbWids;
	}

	
	public Integer getMinuteTimeSpan() {
		return minuteTimeSpan;
	}

	public void setMinuteTimeSpan(Integer minuteTimeSpan) {
		this.minuteTimeSpan = minuteTimeSpan;
	}

	
	

}

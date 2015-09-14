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
	private Integer minuteTimeSpan;//计划时间间隔
	
	/**
	 * 织图id集合
	 * @author zhangbo	2015年9月12日
	 */
	private String wids;
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public void setFinish(Integer finish) {
		this.finish = finish;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}

	public void setSchedula(Date schedula) {
		this.schedula = schedula;
	}

	public void setMinuteTimeSpan(Integer minuteTimeSpan) {
		this.minuteTimeSpan = minuteTimeSpan;
	}

	public void setWids(String wids) {
		this.wids = wids;
	}

	@Autowired
	private OpChannelWorldSchedulaService service;
	
	public String queryChannelWorldValidSchedulaForList(){
		try{
			service.queryChannelWorldValidSchedulaForList(maxId, page, rows, id, userId, worldId, channelId, finish, valid, addDate, modifyDate, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 
	 * @return 
		*	2015年9月12日
		*	mishengliang
	 */
	public String queryChannelWorldSuperbSchedulaForList(){
		try{
			service.queryChannelWorldSuperbSchedulaForList(maxId, page, rows, id, userId, worldId, channelId, finish, valid, addDate, modifyDate, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String delChannelWorldValidSchedula(){
		try{
			service.delChannelWorldValidSchedula(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(OptResult.DELETE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 
	 * @return 
		*	2015年9月12日
		*	mishengliang
	 */
	public String delChannelWorldSuperbSchedula(){
		try{
			service.delChannelWorldSuperbSchedula(idsStr);
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
			service.batchAddChannelWorldSchedula(wids, schedula,minuteTimeSpan, channelId, Tag.FALSE, Tag.TRUE, user.getId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(OptResult.ADD_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchChannelWorldToSuperbSchedula() {
		try{
			Integer[] worldIds = StringUtil.convertStringToIds(wids);
			service.batchChannelWorldToSuperbSchedula(channelId, worldIds, schedula, minuteTimeSpan);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(OptResult.ADD_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 *有效性 重新排序
	 */
	public String reSortValid(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String[] ids = request.getParameterValues("reIndexId");
			service.reSortValid(ids, schedula,minuteTimeSpan, user.getId());
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 精选 重新排序
	 * @return 
		*	2015年9月12日
		*	mishengliang
	 */
	public String reSortSuperb(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String[] ids = request.getParameterValues("reIndexId");
			service.reSortSuperb(ids, schedula,minuteTimeSpan, user.getId());
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
}

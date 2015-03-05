package com.imzhitu.admin.interact;

import java.io.PrintWriter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.interact.service.InteractWorldlevelListService;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.common.pojo.InteractWorldLevelListDto;

/**
 * 等级织图控制器
 * @author zxx
 *
 */
public class InteractWorldlevelListAction extends BaseCRUDAction{
	
	private static final long serialVersionUID = -3975377531566364478L;
	
	private Integer world_id;
	private Integer world_level_id;
	private Integer validity;
	private String comment_ids;
	private String label_ids;
	private String widsStr;
	private Integer maxWId;
	private Integer timeType;//时间类型，0计划时间，1，添加时间，2修改时间.用来代表beginTime、和endTime的类型
	private Date beginTime;
	private Date endTime;
	
	@Autowired
	private InteractWorldlevelListService interactWorldlevelListService;
	
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
	
	public void setInteractWorldlevelListService(InteractWorldlevelListService interactWorldlevelListService){
		this.interactWorldlevelListService = interactWorldlevelListService;
	}
	public InteractWorldlevelListService getInteractWorldlevelListService(){
		return this.interactWorldlevelListService;
	}
	
	public void setValidity(Integer validity){
		this.validity = validity;
	}
	public Integer getValidity(){
		return this.validity;
	}
	
	public void setWidsStr(String widsStr){
		this.widsStr = widsStr;
	}
	public String getWidsStr(){
		return this.widsStr;
	}
	public void setWorld_id(Integer world_id){
		this.world_id = world_id;
	}
	public Integer getWorld_id(){
		return this.world_id;
	}
	public void setWorld_level_id(Integer world_level_id){
		this.world_level_id = world_level_id;
	}
	public Integer getWorld_level_id(){
		return this.world_level_id;
	}
	
	public Integer getMaxWId(){
		return this.maxWId;
	}
	public void setMaxId(Integer maxWId){
		this.maxWId = maxWId;
	}
	
	public void setLabel_ids(String label_ids){
		this.label_ids = label_ids;
	}
	public String getLabel_ids(){
		return this.label_ids;
	}
	
	public void setComment_ids(String comment_ids){
		this.comment_ids = comment_ids;
	}
	public String getComment_ids(){
		return this.comment_ids;
	}
	
	/**
	 * 查询等级织图列表
	 * @return
	 */
	public String queryWorldlevelList(){
		try{
			Integer maxId;
			if(maxWId==null)maxId=0;
			else maxId = maxWId;
			interactWorldlevelListService.queryWorldlevelList(maxId,world_id,timeType,beginTime,endTime, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		
		return StrutsKey.JSON;
	}
	/**
	 * 增加等级织图
	 * @return
	 */
	public String addWorldlevelList(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			interactWorldlevelListService.addWorldlevelList(world_id, world_level_id,validity,comment_ids,label_ids,user.getId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据织图ids删除织图等级列表
	 * @return
	 */
	public String delWorldlevelListByWIds(){
		try{
			interactWorldlevelListService.delWorldlevelListByWIds(widsStr);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据worldId查询织图等级
	 * @return
	 */
	public String queryWorldlevelListByWid(){
		PrintWriter out = null;
		try{
			out = response.getWriter();
			InteractWorldLevelListDto dto = interactWorldlevelListService.queryWorldLevelListByWid(world_id);
			JSONArray ja = JSONArray.fromObject(dto);
			out.print(ja.toString());
			out.flush();
		}catch(Exception e){
			jsonMap.clear();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(json.toString());
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}
	
	public String queryWorldUNInteractCount(){
		try{
			interactWorldlevelListService.queryWorldUNInteractCount(world_id, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		} 
		return  StrutsKey.JSON;
	}
}

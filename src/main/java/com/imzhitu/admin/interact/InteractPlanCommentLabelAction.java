package com.imzhitu.admin.interact;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.common.pojo.InteractPlanCommentLabel;
import com.imzhitu.admin.interact.service.InteractPlanCommentLabelService;

public class InteractPlanCommentLabelAction  extends BaseCRUDAction{
	
	private static final long serialVersionUID = 8663454394343055453L;
	
	private Integer id;//id
	private String description;//标签名称
	private Date startTime;//有效开始时间
	private Date deadline;//有效截止时间
	private String workStartTime;//工作开始时间
	private String workEndTime;//工作结束时间
	private Integer valid;//有效性
	private Date addDate;//添加时间
	private Date modifyDate;//最后修改时间
	private Integer groupId;//组id
	private Integer operatorId;//最后操作者id
	private String operatorName;//最后操作者名称
	private String rowJson;//行json
	private String ids;//ids
	
	@Autowired
	private InteractPlanCommentLabelService  interactPlanCommentLabelService;
	
	public void setInteractPlanCommentLabelService(InteractPlanCommentLabelService interactPlanCommentLabelService){
		this.interactPlanCommentLabelService = interactPlanCommentLabelService;
	}
	public InteractPlanCommentLabelService getInteractPlanCommentLabelService(){
		return this.interactPlanCommentLabelService;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return this.id;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	public String getDescription(){
		return this.description;
	}
	
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	public Date getStartTime(){
		return this.startTime;
	}
	
	public void setDeadline(Date deadline){
		this.deadline = deadline;
	}
	public Date getDeadline(){
		return this.deadline;
	}
	
	public void setWorkStartTime(String workStartTime){
		this.workStartTime = workStartTime;
	}
	public String getWorkStartTime(){
		return this.workStartTime;
	}
	
	public void setWorkEndTime(String workEndTime){
		this.workEndTime = workEndTime;
	}
	public String getWorkEnTime(){
		return this.workEndTime;
	}
	
	public void setValid(Integer valid){
		this.valid  = valid;
	}
	public Integer getValid(){
		return this.valid;
	}
	
	public void setAddDate(Date addDate){
		this.addDate = addDate;
	}
	public Date getAddDate(){
		return this.addDate;
	}
	
	public void setModifyDate(Date modifyDate){
		this.modifyDate = modifyDate;
	}
	public Date getModifyDate(){
		return this.modifyDate;
	}
	
	public void setGroupId(Integer groupId){
		this.groupId = groupId;
	}
	public Integer getGroupId(){
		return this.groupId;
	}
	
	public void setOperatorId(Integer operatorId){
		this.operatorId = operatorId;
	}
	
	public Integer getOperatorId(){
		return this.operatorId;
	}
	
	public void setOperatorName(String operatorName){
		this.operatorName = operatorName;
	}
	
	public String getOperatorName(){
		return this.operatorName;
	}
	
	public void setRowJson(String rowJson){
		this.rowJson = rowJson;
	}
	public String getRowJson(){
		return this.rowJson;
	}
	
	public void setIds(String ids){
		this.ids = ids;
	}
	public String getIds(){
		return this.ids;
	}
	
	public String queryPlanCommentLabelForTable(){
		try{
			interactPlanCommentLabelService.queryPlanCommentLabelForTable( maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		
		return StrutsKey.JSON;
	}
	
	public String addPlanCommentLabel(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			interactPlanCommentLabelService.addPlanCommentLabel(0, description, startTime, deadline, workStartTime, workEndTime, user.getId(), Tag.TRUE);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String delPlanCommentLabel(){
		try{
			AdminUserDetails user = (AdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			interactPlanCommentLabelService.delPlanCommentLabelByIds(ids, user.getId());
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String queryPlanCommentLabel(){
		PrintWriter out = null;
		try{
			out = response.getWriter();
			List<InteractPlanCommentLabel> list = interactPlanCommentLabelService.queryInteractPlanCommentLabel();
			JSONArray jsArray = JSONArray.fromObject(list);
			out.print(jsArray.toString());
			out.flush();
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONArray ja = JSONArray.fromObject(jsonMap);
			out.print(ja.toString());
			out.flush();
		}finally{
			out.close();
		}
		return null;
	}
	
}

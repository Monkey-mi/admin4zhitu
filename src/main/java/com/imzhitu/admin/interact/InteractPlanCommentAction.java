package com.imzhitu.admin.interact;



import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.interact.service.InteractPlanCommentService;

public class InteractPlanCommentAction extends BaseCRUDAction{
	
	private static final long serialVersionUID = 7176449584750470128L;
	
	private String ids;
	private Integer groupId;
	private String content;
	private Integer valid;
	private String rowJson;
	private File commentFile;
	
	@Autowired
	private InteractPlanCommentService interactPlanCommentService;
	
	public File getCommentFile() {
		return commentFile;
	}
	public void setCommentFile(File commentFile) {
		this.commentFile = commentFile;
	}
	public void setRowJson(String rowJson){
		this.rowJson = rowJson;
	}
	public String getRowJson(){
		return rowJson;
	}
	
	public void setGroupId(Integer groupId){
		this.groupId = groupId;
	}
	public Integer getGroupId(){
		return this.groupId;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	public String getContent(){
		return this.content;
	}
	
	public void setValid(Integer valid){
		this.valid = valid;
	}
	public Integer getValid(){
		return this.valid;
	}
	
	public void setIds(String ids){
		this.ids = ids;
	}
	public String getIds(){
		return this.ids;
	}
	
	public void setInteractPlanCommentService(InteractPlanCommentService interactPlanCommentService){
		this.interactPlanCommentService = interactPlanCommentService;
	}
	public InteractPlanCommentService getInteractPlanCommentService(){
		return this.interactPlanCommentService;
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	public String queryPlanCommentForPage(){
		try{
			interactPlanCommentService.queryPlanCommentForTable(maxId,groupId,content, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据ids来删除
	 * @return
	 */
	public String delPlanCommentByIds(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			interactPlanCommentService.delPlanCommentByIds(ids, user.getId());
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String addPlanComment(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			interactPlanCommentService.addPlanComment(commentFile,groupId, content, Tag.TRUE, user.getId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch( Exception e ){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String updatePlanCommentByRowJson(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			interactPlanCommentService.updatePlanCommentByRowJson(rowJson, user.getId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
}

package com.imzhitu.admin.interact;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.interact.service.InteractLikeFollowCommentLabelService;
import com.imzhitu.admin.interact.service.InteractLikeFollowZombieService;

public class InteractLikeFollowAction extends BaseCRUDAction{
	
	private static final long serialVersionUID = -2657682909752577233L;
	
	@Autowired 
	private InteractLikeFollowZombieService likeFollowZombieService;
	
	@Autowired
	private InteractLikeFollowCommentLabelService likeFollowCommentLabelService;
	
	private Integer id;
	private Integer zombieId;
	private File file;
	private String idsStr;
	
	private Integer labelId;
	private String labelIdsStr;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getZombieId() {
		return zombieId;
	}
	public void setZombieId(Integer zombieId) {
		this.zombieId = zombieId;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getIdsStr() {
		return idsStr;
	}
	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}
	
	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}


	public String getLabelIdsStr() {
		return labelIdsStr;
	}

	public void setLabelIdsStr(String labelIdsStr) {
		this.labelIdsStr = labelIdsStr;
	}

	
	
	public String insertLikeFollowCommentLabel(){
		try{
			likeFollowCommentLabelService.insertLikeFollowCommentLabel(labelId);
			JSONUtil.optFailed(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchDeleteLikeFollowCommentLabel(){
		try{
			likeFollowCommentLabelService.batchDeleteLikeFollowCommentLabel(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String queryLikeFollowCommentLabel(){
		try{
			likeFollowCommentLabelService.queryLikeFollowCommentLabel(id, labelId, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchInsertLikeFollowZombie(){
		try{
			likeFollowZombieService.batchInsertLikeFollowZombie(file);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchDeleteLikeFollowZombie(){
		try{
			likeFollowZombieService.batchDeleteLikeFollowZombie(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String queryLikeFollowZombie(){
		try{
			likeFollowZombieService.queryLikeFollowZombie(id, zombieId, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

}

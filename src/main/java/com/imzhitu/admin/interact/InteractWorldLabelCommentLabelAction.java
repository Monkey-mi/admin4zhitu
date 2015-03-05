package com.imzhitu.admin.interact;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.InteractCommentLabel;
import com.imzhitu.admin.common.pojo.InteractWorldLabelTreeDto;
import com.imzhitu.admin.common.pojo.WorldLabelCommentLabelDto;
import com.imzhitu.admin.interact.service.InteractWorldLabelCommentLabelService;

public class InteractWorldLabelCommentLabelAction extends BaseCRUDAction{
	
	private static final long serialVersionUID = 1258870980458563797L;
	
	private String ids;
	private Integer id;
	private Integer userLabelId;
	private Integer commentLabelId;
	private Boolean hasTotal;
	private Integer selected = 0;
	
	@Autowired
	private InteractWorldLabelCommentLabelService interactWorldLabelCommentLabelService;
	
	public Boolean getHasTotal() {
		return hasTotal;
	}

	public void setHasTotal(Boolean hasTotal) {
		this.hasTotal = hasTotal;
	}

	public Integer getSelected() {
		return selected;
	}

	public void setSelected(Integer selected) {
		this.selected = selected;
	}
	public void setIds(String ids){
		this.ids =ids;
	}
	public String getIds(){
		return this.ids;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return this.id;
	}
	public void setUserLabelId(Integer userLabelId){
		this.userLabelId = userLabelId;
	}
	public Integer getUserLabelId(){
		return this.userLabelId;
	}
	public void setCommentLabelId(Integer commentLabelId){
		this.commentLabelId = commentLabelId;
	}
	public Integer getCommentLabelId(){
		return this.commentLabelId;
	}
	
	public void setInteractWorldLabelCommentLabelService(InteractWorldLabelCommentLabelService interactWorldLabelCommentLabelService){
		this.interactWorldLabelCommentLabelService = interactWorldLabelCommentLabelService;
	}
	public InteractWorldLabelCommentLabelService getInteractWorldLabelCommentLabelService(){
		return this.interactWorldLabelCommentLabelService;
	}
	
	/**
	 * 查询织图标签--评论标签关联列表
	 * @return
	 */
	public String QueryWorldLabelCommentLabelList(){
		try{
			interactWorldLabelCommentLabelService.QueryULCLList(maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询用户标签列表
	 * @return
	 */
	public String GetWorldLabelTree(){
		PrintWriter out = null;
		try {
			out = response.getWriter();
			List<InteractWorldLabelTreeDto> list = interactWorldLabelCommentLabelService.GetWorldLabelTree();
			JSONArray jsArray = JSONArray.fromObject(list);
			out.print(jsArray.toString());
			out.flush();
		} catch(Exception e) {
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
	
	/**
	 * 查询评论标签列表
	 */
	public String QueryCommentLabel(){
		PrintWriter out = null;
		try {
			out = response.getWriter();
			List<InteractCommentLabel> list = interactWorldLabelCommentLabelService.QueryCommentLabel();
			JSONArray jsArray = JSONArray.fromObject(list);
			out.print(jsArray.toString());
			out.flush();
		} catch(Exception e) {
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
	
	public String DeleteWorldLabeCommentLabel(){
		try{
			interactWorldLabelCommentLabelService.DeleteWorldLabelCommentLabelByIds(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 增加用户标签评论标签关联
	 */
	public String AddWorldLabelCommentLabel(){
		try{
			interactWorldLabelCommentLabelService.AddWorldLabelCommentLabelCount(new WorldLabelCommentLabelDto(0,userLabelId,commentLabelId,"",""));
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}

}

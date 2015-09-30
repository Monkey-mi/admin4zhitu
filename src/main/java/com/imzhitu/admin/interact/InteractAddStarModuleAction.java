package com.imzhitu.admin.interact;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.StarModule;
import com.imzhitu.admin.interact.service.InteractStarModuleService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class InteractAddStarModuleAction extends BaseCRUDAction{
	
	private static final long serialVersionUID = 7883716253120239849L;
	
	@Autowired
	private InteractStarModuleService interactStarModuleService;

	private Integer id;
	private String  title;
	private String  subtitle;
	private Integer userId;
	private String pics;
	private String intro;
	private Integer topicId;
	
	public String add(){
		try {
			interactStarModuleService.add(title,subtitle,userId,pics,intro,topicId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		} catch (Exception e) {
			JSONUtil.optSuccess(e.getMessage(),jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	public String get(){
		PrintWriter out  = null;
		try {
			out = response.getWriter();
			List<StarModule> list  = interactStarModuleService.get(topicId);
			JSONArray  jsonArray = JSONArray.fromObject(list);
			out.print(jsonArray.toString());
			out.flush();
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		} catch (Exception e) {
			jsonMap.clear();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(json.toString());
			out.flush();
		}
		finally {
			out.close();
		}
		return StrutsKey.JSON;
	}
	
	public String update(){
		try {
			interactStarModuleService.update(id,title,subtitle,userId,pics,intro);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS,jsonMap);
		} catch (Exception e) {
			JSONUtil.optSuccess(e.getMessage(),jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	public String destroy(){
		try {
			interactStarModuleService.destory(id);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS,jsonMap);
		} catch (Exception e) {
			JSONUtil.optSuccess(e.getMessage(),jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getPics() {
		return pics;
	}
	public void setPics(String pics) {
		this.pics = pics;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}
	
	
}

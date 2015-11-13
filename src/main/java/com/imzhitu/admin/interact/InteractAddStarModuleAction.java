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
import com.imzhitu.admin.interact.service.InteractStarWorldModuleService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class InteractAddStarModuleAction extends BaseCRUDAction{
	
	private static final long serialVersionUID = 7883716253120239849L;
	
	@Autowired
	private InteractStarModuleService interactStarModuleService;
	
	@Autowired
	private InteractStarWorldModuleService interactStarWorldModuleService;

	private Integer id;
	private String  title;
	private String  subtitle;
	private Integer userId;
	private String pics;
	private String pic02;
	private String pic03;
	private String pic04;
	private String intro;
	private Integer topicId;
	
	private Integer worldId;
	
	public String add(){
		try {
			interactStarModuleService.add(title,subtitle,userId,pics,pic02,pic03,pic04,intro,topicId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		} catch (Exception e) {
			JSONUtil.optSuccess(e.getMessage(),jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	public String get(){
/*用流直接将数据传到前台，不经过jsonMap传值	*/	
/*  PrintWriter out  = null;
		try {
			out = response.getWriter();
			List<StarModule> list  = interactStarModuleService.get(page,rows,maxId,topicId,jsonMap);
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
		}*/
		try {
			interactStarModuleService.get(page,rows,maxId,topicId,jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String update(){
		try {
			interactStarModuleService.update(id,title,subtitle,userId,pics,pic02,pic03,pic04,intro);
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
	
	
	public String addWorldModule(){
		try {
			interactStarWorldModuleService.addWorldModule(title,subtitle,userId,worldId,intro,topicId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		} catch (Exception e) {
			JSONUtil.optSuccess(e.getMessage(),jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	public String getWorldModule(){
		try {
			interactStarWorldModuleService.getWorldModule(page,rows,maxId,topicId,jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String updateWorldModule(){
		try {
			interactStarWorldModuleService.updateWorldModule(id,title,subtitle,userId,worldId,intro);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS,jsonMap);
		} catch (Exception e) {
			JSONUtil.optSuccess(e.getMessage(),jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	public String destroyWorldModule(){
		try {
			interactStarWorldModuleService.destroyWorldModule(id);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS,jsonMap);
		} catch (Exception e) {
			JSONUtil.optSuccess(e.getMessage(),jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 重新给图片模块排序
	 * @return 
		*	2015年11月13日
		*	mishengliang
	 */
	public String reOrderIndexforPic(){
		try {
			String[]  ids = request.getParameterValues("reIndexId");
			interactStarModuleService.reOrderIndex(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS,jsonMap);
		} catch (Exception e) {
			JSONUtil.optSuccess(e.getMessage(),jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 重新给模块排序
	 * @return 
		*	2015年11月13日
		*	mishengliang
	 */
	public String reOrderIndexforWorld(){
		try {
			String[]  ids = request.getParameterValues("reIndexId");
			interactStarWorldModuleService.reOrderIndex(ids);
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
	
	public String getPic02() {
		return pic02;
	}

	public void setPic02(String pic02) {
		this.pic02 = pic02;
	}

	public String getPic03() {
		return pic03;
	}

	public void setPic03(String pic03) {
		this.pic03 = pic03;
	}

	public String getPic04() {
		return pic04;
	}

	public void setPic04(String pic04) {
		this.pic04 = pic04;
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

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

}

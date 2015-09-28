package com.imzhitu.admin.interact;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.StarRecommendTopic;
import com.imzhitu.admin.interact.service.InteractStarRecommendTopicService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class InteractStarRecommedTopicAction extends BaseCRUDAction{
	
	private static final long serialVersionUID = 7883716253120239849L;
	
	@Autowired
	private InteractStarRecommendTopicService interactStarRecommendTopicService;

	private Integer id;
	private String  backgroundColor; 
	private String  fileName;
	private String title;
	private String introduceHead;
	private String introduceFoot;
	private String stickerButton;
	private String shareButton;
	private String foot;
	
	public String add(){
		try {
			interactStarRecommendTopicService.addTopic(backgroundColor,fileName,title,introduceHead,introduceFoot,stickerButton,shareButton,foot);
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
			List<StarRecommendTopic> list  = interactStarRecommendTopicService.getTopic();
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
			interactStarRecommendTopicService.updateTopic(id,backgroundColor,fileName,title,introduceHead,introduceFoot,stickerButton,shareButton,foot);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS,jsonMap);
		} catch (Exception e) {
			JSONUtil.optSuccess(e.getMessage(),jsonMap);
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	public String destroy(){
		try {
			interactStarRecommendTopicService.destoryTopic(id);
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

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntroduceHead() {
		return introduceHead;
	}

	public void setIntroduceHead(String introduceHead) {
		this.introduceHead = introduceHead;
	}

	public String getIntroduceFoot() {
		return introduceFoot;
	}

	public void setIntroduceFoot(String introduceFoot) {
		this.introduceFoot = introduceFoot;
	}

	public String getStickerButton() {
		return stickerButton;
	}

	public void setStickerButton(String stickerButton) {
		this.stickerButton = stickerButton;
	}

	public String getShareButton() {
		return shareButton;
	}

	public void setShareButton(String shareButton) {
		this.shareButton = shareButton;
	}

	public String getFoot() {
		return foot;
	}

	public void setFoot(String foot) {
		this.foot = foot;
	}
	
	
}

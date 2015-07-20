package com.imzhitu.admin.interact;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.InteractChannelWorldLabel;
import com.imzhitu.admin.interact.service.InteractChannelWorldLabelService;

/**
 * 
 * @author zxx 2015年7月20日 16:38:46
 *
 */
public class InteractChannelWorldLabelAction extends BaseCRUDAction{

	private static final long serialVersionUID = -1574906282888550179L;
	
	@Autowired
	private InteractChannelWorldLabelService channelWorldLabelService;
	
	private Integer id;
	private Integer channelId;
	private Integer worldId;
	private String labelIds;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public String getLabelIds() {
		return labelIds;
	}

	public void setLabelIds(String labelIds) {
		this.labelIds = labelIds;
	}

	public String insertChannelWorldLabel(){
		try{
			channelWorldLabelService.insertChannelWorldLabel(channelId, worldId, labelIds, getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String queryChannelWorldLabel(){
		try{
			List<InteractChannelWorldLabel> list = channelWorldLabelService.queryChannelWorldLabel(id, worldId, channelId);
			jsonMap.put(OptResult.JSON_KEY_LABEL_INFO, list);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
}

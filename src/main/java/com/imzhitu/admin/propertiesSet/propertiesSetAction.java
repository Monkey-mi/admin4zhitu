package com.imzhitu.admin.propertiesSet;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.propertiesSet.service.propertiesSetService;

public class propertiesSetAction extends BaseCRUDAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1844920769848520753L;
	
	@Autowired
	public propertiesSetService service;
	private Integer channelId;
	private String channelAdd;
	private String channelStar;
	private String channelSuperb;

	public String getChannelSuperb() {
		return channelSuperb;
	}

	public void setChannelSuperb(String channelSuperb) {
		this.channelSuperb = channelSuperb;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getChannelAdd() {
		return channelAdd;
	}

	public void setChannelAdd(String channelAdd) {
		this.channelAdd = channelAdd;
	}

	public String getChannelStar() {
		return channelStar;
	}

	public void setChannelStar(String channelStar) {
		this.channelStar = channelStar;
	}

	/**
	 * 通过配置文件分别查询频道下 织图、红人、精选的通知文本
	 * @return 
		*	2015年8月25日
		*	mishengliang
	 */
	public String queryNotifyByChannelId() {
		try {
			service.queryNotifyByChannelId(channelId,jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	

	public String addNotifyByChannelId() {
		Map<String, String> notifyMap = new HashMap<String, String>();
		notifyMap.put("channelAdd", channelAdd);
		notifyMap.put("channelStar", channelStar);
		notifyMap.put("channelSuperb", channelSuperb);
		try {
			service.addNotifyByChannelId(channelId,notifyMap,jsonMap);
			JSONUtil.optSuccess("修改成功！",jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}


}

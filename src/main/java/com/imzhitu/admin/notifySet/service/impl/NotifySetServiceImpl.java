package com.imzhitu.admin.notifySet.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imzhitu.admin.notifySet.mapper.NotifyMapper;
import com.imzhitu.admin.notifySet.service.NotifySetService;



@Service
public class NotifySetServiceImpl implements NotifySetService {
	
@Autowired	
private NotifyMapper mapper;

	@Override
	public String queryNotifyByChannelId(Integer channelId,Map<String , Object> jsonMap) throws Exception {
		Map<String, String> notifyMap = new HashMap<String, String>();
		
		String channelAdd = mapper.queryNotify(channelId+"_add");
		String channelStar = mapper.queryNotify(channelId+"_star");
		String channelSuperb = mapper.queryNotify(channelId+"_superb");
		notifyMap.put("channelAdd", channelAdd);
		notifyMap.put("channelStar", channelStar);
		notifyMap.put("channelSuperb", channelSuperb);
		jsonMap.put("notifyMap", notifyMap);
		return null;
	}

	@Override
	public void addNotifyByChannelId(Integer channelId,Map<String, String> notifyMap,Map<String , Object> jsonMap) throws Exception {
		String channelAdd = notifyMap.get("channelAdd");
		String channelStar = notifyMap.get("channelStar");
		String channelSuperb = notifyMap.get("channelSuperb");
		mapper.addNotify(channelId+"_add",channelAdd);
		mapper.addNotify(channelId+"_star",channelStar);
		mapper.addNotify(channelId+"_superb",channelSuperb);
	}
}

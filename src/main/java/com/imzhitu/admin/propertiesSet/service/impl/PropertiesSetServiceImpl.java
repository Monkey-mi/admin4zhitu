package com.imzhitu.admin.propertiesSet.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.imzhitu.admin.common.PropertiesFileAddAndQuery;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.propertiesSet.service.PropertiesSetService;

@Service
public class PropertiesSetServiceImpl implements PropertiesSetService {
	

/*	
 *    热加载实现
 * private static File File = new File("/channelNotify.properties"); 
	
	private static long lastModifiedTime = 0L;
	
	private File getPropertyFile() {
		if (File.lastModified() != lastModifiedTime ) {
			File
		}
		return File;
	}*/

	@Override
	public String queryNotifyByChannelId(Integer channelId,Map<String , Object> jsonMap) throws Exception {
		String filePath = "/channelNotify.properties";
		Map<String, String> notifyMap = new HashMap<String, String>();
		PropertiesFileAddAndQuery propertiesFileAddAndQuery = new PropertiesFileAddAndQuery();
		
		String channelAdd = propertiesFileAddAndQuery.query(channelId+"_add",filePath);
		String channelStar = propertiesFileAddAndQuery.query(channelId+"_star",filePath);
		String channelSuperb = propertiesFileAddAndQuery.query(channelId+"_superb",filePath);
		notifyMap.put("channelAdd", channelAdd);
		notifyMap.put("channelStar", channelStar);
		notifyMap.put("channelSuperb", channelSuperb);
		jsonMap.put("notifyMap", notifyMap);
		return null;
	}

	@Override
	public void addNotifyByChannelId(Integer channelId,Map<String, String> notifyMap,Map<String , Object> jsonMap) throws Exception {
		String filePath = "/channelNotify.properties";
	 AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	 PropertiesFileAddAndQuery propertiesFileAddAndQuery = new PropertiesFileAddAndQuery();
	 
		Integer userId = user.getId();
		String comments = userId + "";
		String channelAdd = notifyMap.get("channelAdd");
		String channelStar = notifyMap.get("channelStar");
		String channelSuperb = notifyMap.get("channelSuperb");
		propertiesFileAddAndQuery.add(channelId+"_add",channelAdd,filePath,comments);
		propertiesFileAddAndQuery.add(channelId+"_star",channelStar,filePath,comments);
		propertiesFileAddAndQuery.add(channelId+"_superb",channelSuperb,filePath,comments);
	}
}

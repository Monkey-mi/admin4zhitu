package com.imzhitu.admin.propertiesSet.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.imzhitu.admin.common.PropertiesFileAddAndQuery;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.propertiesSet.service.propertiesSetService;

@Service
public class propertiesSetServiceImpl implements propertiesSetService {
	
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
		String filePath = "src/main/resources/channelNotify.properties";
		Map<String, String> notifyMap = new HashMap<String, String>();
		
		String channelAdd = PropertiesFileAddAndQuery.query(channelId+"_add",filePath);
		String channelStar = PropertiesFileAddAndQuery.query(channelId+"_star",filePath);
		String channelSuperb = PropertiesFileAddAndQuery.query(channelId+"_superb",filePath);
		notifyMap.put("channelAdd", channelAdd);
		notifyMap.put("channelStar", channelStar);
		notifyMap.put("channelSuperb", channelSuperb);
		jsonMap.put("notifyMap", notifyMap);
		return null;
	}

	@Override
	public void addNotifyByChannelId(Integer channelId,Map<String, String> notifyMap,Map<String , Object> jsonMap) throws Exception {
		String filePath = "src/main/resources/channelNotify.properties";
	 AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer userId = user.getId();
		String comments = userId + "";
		String channelAdd = notifyMap.get("channelAdd");
		String channelStar = notifyMap.get("channelStar");
		String channelSuperb = notifyMap.get("channelSuperb");
		PropertiesFileAddAndQuery.add(channelId+"_add",channelAdd,filePath,comments);
		PropertiesFileAddAndQuery.add(channelId+"_star",channelStar,filePath,comments);
		PropertiesFileAddAndQuery.add(channelId+"_superb",channelSuperb,filePath,comments);
	}
}

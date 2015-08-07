package com.imzhitu.admin.interact.service;

import java.util.Map;

/**
 * @author zhangbo	2015年8月6日
 *
 */
public interface InteractWorldLabelService {
	
	/**
	 * @param worldId
	 * @param jsonMap
	 * @author zhangbo	2015年8月6日
	 */
	void queryWorldLabels(Integer worldId, Map<String, Object> jsonMap) throws Exception;

}

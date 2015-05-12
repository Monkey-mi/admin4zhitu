package com.imzhitu.admin.aliyun.service;

import net.sf.json.JSONObject;

public interface OpenSearchService {
	/**
	 * 查询频道标签
	 * @param label
	 * @return
	 * @throws Exception
	 */
	public JSONObject queryChannelLabel(String label)throws Exception;
}

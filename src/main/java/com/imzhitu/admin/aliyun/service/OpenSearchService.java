package com.imzhitu.admin.aliyun.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface OpenSearchService {
	/**
	 * 查询频道标签
	 * @param label
	 * @return
	 * @throws Exception
	 */
	public JSONObject queryChannelLabel(String label)throws Exception;
	
	/**
	 * 查询织图地理位置信息（主要查询结果可使用信息为织图主键id集合）
	 * 
	 * @param worldLocation	织图地理位置信息
	 * @param startHit		OpenSearch开始查询位置 
	 * @param limit 		OpenSearch本次查询多少条
	 * @return	JSONObject	内容封装了对应改地理位置模糊匹配后的织图主键id集合
	 * @throws Exception
	 * @author zhangbo	2015年8月27日
	 */
	public JSONArray queryHTWolrdLocationInfo(String worldLocation, int startHit, int limit)throws Exception;
}

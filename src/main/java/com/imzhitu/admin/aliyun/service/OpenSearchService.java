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
	
	/**
	 * 查询织图地理位置信息（主要查询结果可使用信息为织图主键id集合，与根据地理位置信息所查询出的总数）
	 * 
	 * @param worldLocation	织图地理位置信息
	 * @param startHit		OpenSearch开始查询位置 
	 * @param limit 		OpenSearch本次查询多少条
	 * @return JSONObject	内容封装了对应地理位置模糊匹配后的织图主键id集合与总数
	 * @throws Exception
	 * @author zhangbo	2015年8月27日
	 */
	public JSONObject queryHTWolrdLocationInfo(String worldLocation, int startHit, int limit) throws Exception;
	
	/**
	 * 查询织图描述信息（主要查询结果可使用信息为织图主键id集合，与根据描述所查询出的总数）
	 * 
	 * @param worldDesc	织图描述
	 * @param startHit	OpenSearch开始查询位置 
	 * @param limit		OpenSearch本次查询多少条
	 * @return JSONObject	内容封装了对应描述模糊匹配后的织图主键id集合与总数
	 * @throws Exception
	 * @author zhangbo	2015年12月2日
	 */
	JSONObject queryWolrdDescInfo(String worldDesc, int startHit, int limit) throws Exception;
}

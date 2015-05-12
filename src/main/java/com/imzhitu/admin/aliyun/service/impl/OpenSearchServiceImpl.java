package com.imzhitu.admin.aliyun.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.imzhitu.admin.aliyun.service.OpenSearchService;
import com.opensearch.javasdk.CloudsearchClient;
import com.opensearch.javasdk.CloudsearchSearch;
import com.opensearch.javasdk.object.KeyTypeEnum;

@Service
public class OpenSearchServiceImpl implements OpenSearchService{
	
	@Value("${aliyun.accessKeyId}")
	private String aliyunAccessKeyId;
	
	@Value("${aliyun.accessKeySecret}")
	private String aliyunAccessKeySecret;
	
	@Value("${aliyun.appName.channelLabel}")
	private String channelLabelAppName;
	
	@Value("${aliyun.search.host}")
	private String aliyunHanZouHost;
	
	@Override
	public JSONObject queryChannelLabel(String label) throws Exception {
		if(null == label || "".equals(label.trim())){
			return null;
		}
		
		Map<String,Object>opts = new HashMap<String,Object>();
		
		//公网的opensearch
		//opts.put("host","http://opensearch-cn-hangzhou.aliyuncs.com");
		
		//内网的opensearch
		//opts.put("host", "http://intranet.opensearch-cn-hangzhou.aliyuncs.com");
		
		opts.put("host", aliyunHanZouHost);
		
		CloudsearchClient client = new CloudsearchClient(aliyunAccessKeyId,aliyunAccessKeySecret,opts,KeyTypeEnum.ALIYUN);
		CloudsearchSearch search = new CloudsearchSearch(client);
		
		//添加制定的搜索应用
		search.addIndex(channelLabelAppName);
		
		//指定结果集的条数
		search.setHits(20);
		//指定搜索的关键词，如果没有输入索引名称，则使用default
		//search.setQueryString(question);
		
		search.setQueryString("default:'"+label+"'");
		
		//或者指定某索引字段进行查找
		//索引字段名称是您在您的数据结构中的“索引到”字段
		//search.setQueryString("index_name:'"+question+"'");
		
		//指定搜索返回的格式
		search.setFormat("json");
		
		//设定过滤条件，字段必须设定为可过滤
		//search.addFilter("price>10");
		
		//设定排序方式，字段必须设定为可过滤
		//search.addSort("price", "+");
		
		//解析结果
		String result = search.search();
		JSONObject resultObj = JSONObject.fromObject(result).getJSONObject("result");
		
		if(resultObj.isNullObject()){
			return null;
		}
		
		Integer num = resultObj.optInt("num");
		if ( num ==null || num == 0){
			return null;
		}
		JSONArray items = resultObj.getJSONArray("items");
		if(items.isEmpty()){
			return null;
		}
		
		
		return resultObj;
	}

	public String getAliyunAccessKeyId() {
		return aliyunAccessKeyId;
	}

	public void setAliyunAccessKeyId(String aliyunAccessKeyId) {
		this.aliyunAccessKeyId = aliyunAccessKeyId;
	}

	public String getAliyunAccessKeySecret() {
		return aliyunAccessKeySecret;
	}

	public void setAliyunAccessKeySecret(String aliyunAccessKeySecret) {
		this.aliyunAccessKeySecret = aliyunAccessKeySecret;
	}


	public String getChannelLabelAppName() {
		return channelLabelAppName;
	}

	public void setChannelLabelAppName(String channelLabelAppName) {
		this.channelLabelAppName = channelLabelAppName;
	}

	public String getAliyunHanZouHost() {
		return aliyunHanZouHost;
	}

	public void setAliyunHanZouHost(String aliyunHanZouHost) {
		this.aliyunHanZouHost = aliyunHanZouHost;
	}

	

}

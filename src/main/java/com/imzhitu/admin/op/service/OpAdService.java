package com.imzhitu.admin.op.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 运营广告管理数据访问接口
 * </p>
 * 
 * 创建时间：2013-12-1
 * 
 * @author ztj
 * 
 */
public interface OpAdService extends BaseService {

	/**
	 * 保存APP链接
	 * 
	 * @param appName
	 * @param appIcon
	 * @param appDesc
	 * @param appLink
	 * @param phoneCode
	 * @param open
	 * @throws Exception
	 */
	public void saveAppLink(String appName, String appIcon, String appDesc, String appLink,
			Integer phoneCode, Integer open) throws Exception;
	
	/**
	 * 更新排序
	 * @param idStrs
	 * @throws Exception
	 */
	public void updateAppLinkSerial(String[] idStrs) throws Exception;
	
	/**
	 * 根据JSON更新链接
	 * 
	 * @param json
	 * @throws Exception
	 */
	public void updateAppLinkByJSON(String json) throws Exception;

	/**
	 * 构建APP点击记录列表
	 * 
	 * @param appId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildAppLinkRecord(Integer appId, int maxId, int start,
			int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建APP链接列表
	 * 
	 * @param open
	 * @param phoneCode
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildAppLink(Integer open, Integer phoneCode, int maxSerial, int start, 
			int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 删除链接
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void deleteAppLink(String idsStr) throws Exception;
	
	
}

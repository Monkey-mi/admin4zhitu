package com.imzhitu.admin.op.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.OpAdAppLink;
import com.imzhitu.admin.common.pojo.OpAdAppLinkRecord;

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
	public void saveAppLink(OpAdAppLink link) throws Exception;
	
	/**
	 * 更新链接
	 * 
	 * @param link
	 * @throws Exception
	 */
	public void updateAppLink(OpAdAppLink link) throws Exception;
	
	/**
	 * 更新排序
	 * @param idStrs
	 * @throws Exception
	 */
	public void updateAppLinkSerial(String[] idStrs) throws Exception;
	
	/**
	 * 更新有效性
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void updateAppLinkValid(String idsStr, Integer valid) throws Exception;

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
	public void buildAppLink(OpAdAppLink link, int start, 
			int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 删除链接
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void deleteAppLink(String idsStr) throws Exception;
	
	/**
	 * 根据id查询applink
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void buildAppLinkById(Integer id, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建APP点击记录列表
	 * 
	 * @param record
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildAppLinkRecord(OpAdAppLinkRecord record, int start,
			int limit, Map<String, Object> jsonMap) throws Exception;
	
	
}

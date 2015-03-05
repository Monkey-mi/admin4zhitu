package com.imzhitu.admin.userinfo.service;

import java.util.Map;

import com.hts.web.common.pojo.UserVerify;
import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 用户认证业务逻辑访问对象
 * </p>
 * 
 * 创建时间：2014-7-16
 * @author tianjie
 *
 */
public interface UserVerifyService extends BaseService {

	/**
	 * 构建认证信息列表
	 * 
	 * @param maxSerial
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildVerify(Integer maxSerial, int start, int limit, Map<String, Object> jsonMap)
			throws Exception;
	
	/**
	 * 构建所有认证信息列表
	 * 
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildVerify(Boolean addAllTag, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 查询认证信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public UserVerify queryVerify(Integer id) throws Exception;
	
	/**
	 * 保存认证信息
	 * 
	 * @param verifyName
	 * @param verifyDesc
	 * @param verifyIcon
	 * @throws Exception
	 */
	public void saveVerify(String verifyName, String verifyDesc,
			String verifyIcon) throws Exception;
	
	/**
	 * 更新认证
	 * 
	 * @param id
	 * @param verifyName
	 * @param verifyDesc
	 * @param verifyIcon
	 * @param serial
	 * @throws Exception
	 */
	public void updateVerify(Integer id, String verifyName, String verifyDesc,
			String verifyIcon, Integer serial) throws Exception;
	
	
	/**
	 * 删除认证
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void deleteVerify(String idsStr) throws Exception;

	/**
	 * 更新排序
	 * 
	 * @param idStrs
	 * @throws Exception
	 */
	public void updateVerifySerial(String[] idStrs) throws Exception;
	
	/**
	 * 更新认证列表缓存
	 * 
	 * @param limit
	 * @throws Exception
	 */
	public void updateVerifyCache(int limit) throws Exception;
}

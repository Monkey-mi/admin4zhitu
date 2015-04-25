package com.imzhitu.admin.plat.service;

import java.util.Map;

public interface PlatService {

	/**
	 * 保存被关注对象
	 * 
	 * @param cid
	 * @param cname
	 * @param pid
	 */
	public void saveBeConcern(String cid, String cname, Integer pid) throws Exception;
	
	/**
	 * 删除被关注对象
	 *
	 * @param index
	 * @throws Exception
	 */
	public void deleteBeConcern(int index) throws Exception;
	
	/**
	 * 构建被关注对象列表
	 * 
	 * @throws Exception
	 */
	public void buildBeConcern(Map<String, Object> jsonMap) throws Exception;
	
	public void pushConcern();
	
}

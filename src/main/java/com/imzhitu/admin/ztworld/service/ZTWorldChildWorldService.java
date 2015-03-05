package com.imzhitu.admin.ztworld.service;

import java.util.Map;

import com.hts.web.common.pojo.HTWorldChildWorldTypeDto;
import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 子世界业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2014-6-13
 * @author tianjie
 *
 */
public interface ZTWorldChildWorldService extends BaseService {

	/**
	 * 保存子世界类型
	 * 
	 * @param typePath
	 * @param total
	 * @param useCount
	 * @param typeDesc
	 * @param descPath
	 * @param labelName
	 * @throws Exception
	 */
	public void saveChildType(String typePath, Integer total, Integer useCount, 
			String typeDesc, String descPath, String labelName) throws Exception;
	
	/**
	 * 更新子世界类型
	 * 
	 * @param id
	 * @param typePath
	 * @param total
	 * @param useCount
	 * @param typeDesc
	 * @param descPath
	 * @param labelName
	 * @param serial
	 * @throws Exception
	 */
	public void updateChildType(Integer id, String typePath, Integer total, Integer useCount,
			String typeDesc, String descPath, String labelName, Integer serial) throws Exception;
	
	/**
	 * 构建子世界类型列表
	 * 
	 * @param maxSerial
	 * @param start
	 * @param limit
	 * @param jsonMap
	 */
	public void buildChildType(int maxSerial, int start, int limit,
			Map<String, Object> jsonMap) throws Exception;
	
	
	/**
	 * 删除子世界类型
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void deleteChildType(String idsStr) throws Exception;
	
	/**
	 * 更新子世界类型序号
	 * 
	 * @param idStrs
	 * @throws Exception
	 */
	public void updateChildTypeSerial(String[] idStrs) throws Exception;
	
	/**
	 * 更新最新子世界类型
	 * 
	 * @param limit
	 * @throws Exception
	 */
	public void updateLatestChildType(int limit) throws Exception;
	
	/**
	 * 根据id获取子世界分类
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public HTWorldChildWorldTypeDto getChildTypeById(Integer id) throws Exception;
	
}

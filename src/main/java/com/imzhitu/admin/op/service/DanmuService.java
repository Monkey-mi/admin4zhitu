package com.imzhitu.admin.op.service;

import java.io.File;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.OpSysDanmu;

/**
 * <p>
 * 弹幕业务逻辑访问接口
 * </p>
 * 
 * 创建时间: 2015-05-18
 * @author lynch
 *
 */
public interface DanmuService extends BaseService {

	public void buildSysDanmu(OpSysDanmu sysDanmu, int start, int limit, 
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 批量保存弹幕
	 * 
	 * @param channelId
	 * @param file
	 */
	public void saveSysDanmu(Integer channelId, File file) throws Exception;
	
	/**
	 *　保存弹幕
	 * 
	 * @param channelId
	 * @param file
	 */
	public void saveSysDanmu(OpSysDanmu sysDanmu) throws Exception;

	/**
	 * 删除弹幕
	 * 
	 * @param ids
	 */
	public void deleteSysDanmu(String idsStr) throws Exception;

	/**
	 * 更新弹幕
	 * 
	 * @param sysDanmu
	 */
	public void updateSysDanum(OpSysDanmu sysDanmu) throws Exception;

	/**
	 * 根据id查询弹幕
	 * 
	 * @return
	 */
	public OpSysDanmu querySysDanmuById(Integer id) throws Exception;

	/**
	 * 更新弹幕排序
	 * 
	 * @param idsStr
	 */
	public void updateSysDanmuSerial(String[] idsStr) throws Exception;
	
}

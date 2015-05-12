package com.imzhitu.admin.ztworld.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.pojo.HTWorldLabel;
import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 织图标签数据访问接口
 * </p>
 * 
 * 创建时间：2014-5-7
 * @author tianjie
 *
 */
public interface ZTWorldLabelService extends BaseService {

	/**
	 * 构建标签列表
	 * 
	 * @param maxSerial
	 * @param labelState
	 * @param labelName
	 * @param orderKey
	 * @param order
	 * @param start
	 * @param limit
	 * @param jsonMap
	 */
	public void buildLabel(Integer maxSerial, Integer labelState, String labelName, 
			String orderKey, String order, int start, int limit, 
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 保存标签
	 * 
	 * @param labelName
	 * @throws Exception
	 */
	public Integer saveLabel(String labelName) throws Exception;
	
	/**
	 * 删除标签
	 * 
	 * @param idsStr
	 */
	public void deleteLabel(String idsStr) throws Exception;
	
	/**
	 * 更新标签序号
	 * 
	 * @param ids
	 */
	public void updateLabelSerial(String[] ids) throws Exception;
	
	/**
	 * 更新热门标签
	 * 
	 * @param hotLimit
	 * @param activityLimit
	 * @throws Exception
	 */
	public void updateHotLabel(int hotLimit, int activityLimit) throws Exception;
	
	
	/**
	 * 构建标签织图列表
	 * 
	 * @param maxId
	 * @param labelId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLabelWorld(Integer maxId, Integer labelId, Integer valid, int start, int limit, 
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 更新标签织图有效性
	 * 
	 * @param labelId
	 * @param idsStr
	 * @param valid
	 * @throws Exception
	 */
	public void updateLabelWorldValid(Integer labelId, String idsStr, Integer valid) throws Exception;
	
	/**
	 * 构建标签id列表
	 * 
	 * @param worldId
	 * @param jsonMap
	 */
	public void buildLabelIdsWithoutReject(Integer worldId, Map<String, Object> jsonMap) throws Exception;

	
	/**
	 * 根据json字符串批量修改
	 * 
	 * @param json
	 */
	public void updateByJSON(String json) throws Exception;
	
	/**
	 * 保存织图标签
	 * @param worldId
	 * @param userId
	 * @param labelId
	 * @throws Exception
	 */
	public void saveLabelWorld(Integer worldId,Integer userId,Integer labelId,String labelStr)throws Exception;
	
	/**
	 * 查询10个热门标签
	 * @return
	 * @throws Exception
	 */
	public List<HTWorldLabel> queryLabelForCombobox()throws Exception;
}

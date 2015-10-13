package com.imzhitu.admin.ztworld.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.pojo.HTWorldFilterLogo;
import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.WorldWithInteract;

/**
 * <p>
 * 世界管理访问业务逻辑接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface ZTWorldService extends BaseService {
	
	/**
	 * 构建织图列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param startDateStr
	 * @param endDateStr
	 * @param shortLink
	 * @param phoneCode
	 * @param label
	 * @param authorName
	 * @param valid
	 * @param shield
	 * @param orderKey
	 * @param orderBy
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildWorld(int maxId, int start, int limit, String startDateStr, String endDateStr, 
			String shortLink, Integer phoneCode, String label, String authorName, Integer valid,
			Integer shield, String worldDesc, String worldLocation, Integer user_level_id,String orderKey,  
			String orderBy, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 屏蔽织图
	 * 
	 * @param worldId
	 */
	public void shieldWorld(Integer worldId) throws Exception;
	
	/**
	 * 解除屏蔽
	 * @param worldId
	 */
	public void unShieldWorld(Integer worldId) throws Exception;
	
	/**
	 * 根据JSON字符串更新批量更新织图
	 * 
	 * @param idKey
	 * @param worldJSON
	 * @throws Exception
	 */
	public void updateWorldByJSON(String idKey, String worldJSON) throws Exception;
	
	/**
	 * 删除织图
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void deleteWorld(String idsStr) throws Exception;
	
	/**
	 * 更新最新有效性
	 * 
	 * @param id
	 * @param valid
	 * @throws Exception
	 */
	public void updateLatestValid(Integer id, Integer valid) throws Exception;
	
	/**
	 * 删除最新织图多余缓存
	 * 
	 * @throws Exception
	 */
	public void deleteOverFlowLatestCache() throws Exception;
	
	/**
	 * 获取织图互动标志信息
	 * 
	 * @param worldList
	 * @throws Exception
	 */
	public void extractInteractInfo(final List<? extends WorldWithInteract> worldList);
	
	
	/**
	 * 构建滤镜logo
	 * 
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildFilterLogo(Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 获取滤镜logo
	 * 
	 * @return
	 * @throws Exception
	 */
	public HTWorldFilterLogo getFilterLogo() throws Exception;
	
	/**
	 * 更新滤镜logo缓存
	 * 
	 * @param ver
	 * @param logoPath
	 * @param logoDesc
	 * @param valid
	 */
	public void updateFilterLogo(Float ver, String logoPath, 
			String logoDesc, Integer valid) throws Exception;
	
	/**
	 * 更新最新为无效
	 * 
	 * @param authorId
	 * @throws Exception
	 */
	public void updateLatestInvalid(Integer authorId) throws Exception;
	
	/**
	 * 根据织图id刷新频道名称与频道id字段，以频道织图关联关系表数据为准
	 * 
	 * @param worldId	织图Id
	 * @throws Exception
	 * @author zhangbo	2015年10月13日
	 */
	void refreshChannelNamesAndChannelIds(Integer worldId) throws Exception;
}

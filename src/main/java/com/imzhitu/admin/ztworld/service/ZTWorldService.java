package com.imzhitu.admin.ztworld.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.pojo.HTWorldFilterLogo;
import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.WorldWithInteract;
import com.imzhitu.admin.common.pojo.ZTWorldDto;

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
	void buildWorld(int maxId, int start, int limit, String startDateStr, String endDateStr, 
			String shortLink, Integer phoneCode, String label, String authorName, Integer valid,
			Integer shield, String worldDesc, String worldLocation, Integer user_level_id,String orderKey,  
			String orderBy,Integer isZombie, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 查询织图描述中被@人的信息
	 * @param worldId 
	 * @param jsonMap
	 * @throws Exception 
		*	2015年11月10日
		*	mishengliang
	 */
	void queryCommentAt(int worldId,Map<String, Object> jsonMap) throws Exception;
	
	
	/**
	 * 屏蔽织图
	 * 
	 * @param worldId
	 */
	void shieldWorld(Integer worldId) throws Exception;
	
	/**
	 * 解除屏蔽
	 * @param worldId
	 */
	void unShieldWorld(Integer worldId) throws Exception;
	
	/**
	 * 根据JSON字符串更新批量更新织图
	 * 
	 * @param idKey
	 * @param worldJSON
	 * @throws Exception
	 */
	void updateWorldByJSON(String idKey, String worldJSON) throws Exception;
	
	/**
	 * 删除织图
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	void deleteWorld(String idsStr) throws Exception;
	
	/**
	 * 更新最新有效性
	 * 
	 * @param id
	 * @param valid
	 * @throws Exception
	 */
	void updateLatestValid(Integer id, Integer valid) throws Exception;
	
	/**
	 * 删除最新织图多余缓存
	 * 
	 * @throws Exception
	 */
	void deleteOverFlowLatestCache() throws Exception;
	
	/**
	 * 获取织图互动标志信息
	 * 
	 * @param worldList
	 * @throws Exception
	 */
	void extractInteractInfo(final List<? extends WorldWithInteract> worldList);
	
	
	/**
	 * 构建滤镜logo
	 * 
	 * @param jsonMap
	 * @throws Exception
	 */
	void buildFilterLogo(Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 获取滤镜logo
	 * 
	 * @return
	 * @throws Exception
	 */
	HTWorldFilterLogo getFilterLogo() throws Exception;
	
	/**
	 * 更新滤镜logo缓存
	 * 
	 * @param ver
	 * @param logoPath
	 * @param logoDesc
	 * @param valid
	 */
	void updateFilterLogo(Float ver, String logoPath, 
			String logoDesc, Integer valid) throws Exception;
	
	/**
	 * 更新最新为无效
	 * 
	 * @param authorId
	 * @throws Exception
	 */
	void updateLatestInvalid(Integer authorId) throws Exception;
	
	/**
	 * 根据织图id刷新频道名称与频道id字段，以频道织图关联关系表数据为准
	 * 
	 * @param worldId	织图Id
	 * @throws Exception
	 * @author zhangbo	2015年10月13日
	 */
	void refreshChannelNamesAndChannelIds(Integer worldId) throws Exception;

	/**
	 * 根据织图id，获取织图信息（不区分织图状态）
	 * 
	 * @param worldId	织图id
	 * @return
	 * @author zhangbo	2015年11月2日
	 */
	ZTWorldDto getZTWorldByWorldId(Integer worldId);

	/**
	 * 构建瀑布流织图信息
	 * 
	 * @param maxId		最大织图id
	 * @param page		分页查询：当前页面
	 * @param rows		分页查询：每页数量
	 * @param startTime	起始时间
	 * @param endTime	结束时间
	 * @param phoneCode	客户端代号：0：IOS，1：安卓，null：所有客户端
	 * @param valid		是否生效：1：生效，0：未生效（用户删除），null：所有状态
	 * @param jsonMap	返回页面参数
	 * 
	 * @throws Exception
	 * 
	 * @author zhangbo	2015年11月25日
	 */
	void buildWorldMasonry(Integer maxId, Integer page, Integer rows, String startTime, String endTime, Integer phoneCode, Integer valid, Map<String, Object> jsonMap) throws Exception;

	/**
	 * 构建瀑布流织图信息，通过用户等级查询
	 * 查询的主要条件，基于用户等级与有效性
	 * 
	 * @param maxId		最大织图id
	 * @param page		分页查询：当前页面
	 * @param rows		分页查询：每页数量
	 * @param startTime	起始时间
	 * @param endTime	结束时间
	 * @param valid		是否生效：1：生效，0：未生效（用户删除），null：所有状态
	 * @param jsonMap	返回页面参数
	 * 
	 * @throws Exception
	 * 
	 * @author zhangbo	2015年11月26日
	 */
	void buildWorldMasonryByUserLevel(Integer maxId, Integer page, Integer rows, String startTime, String endTime, Integer user_level_id, Integer valid, Map<String, Object> jsonMap) throws Exception;

	/**
	 * 构建瀑布流马甲织图信息
	 * 
	 * @param maxId		最大织图id
	 * @param page		分页查询：当前页面
	 * @param rows		分页查询：每页数量
	 * @param startTime	起始时间
	 * @param endTime	结束时间
	 * @param jsonMap	返回页面参数
	 * 
	 * @throws Exception
	 * 
	 * @author zhangbo	2015年11月30日
	 */
	void buildWorldMasonryByZombie(Integer maxId, Integer page, Integer rows, String startTime, String endTime, Map<String, Object> jsonMap) throws Exception;

	/**
	 * 构建瀑布流织图信息，通过织图描述查询
	 * 
	 * @param maxId		最大织图id
	 * @param page		分页查询：当前页面
	 * @param rows		分页查询：每页数量
	 * @param worldDesc	织图描述，为关键字匹配内容
	 * @param jsonMap	返回页面参数
	 * 
	 * @throws Exception
	 * 
	 * @author zhangbo	2015年12月2日
	 */
	void buildWorldMasonryByWorldDesc(Integer maxId, Integer page, Integer rows, String worldDesc, Integer valid,Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建瀑布流织图信息，通过织图地理位置信息查询
	 * 
	 * @param maxId			最大织图id
	 * @param page			分页查询：当前页面
	 * @param rows			分页查询：每页数量
	 * @param worldLocation	织图描述，为关键字匹配内容
	 * @param jsonMap		返回页面参数
	 * 
	 * @throws Exception
	 * 
	 * @author zhangbo	2015年12月2日
	 */
	void buildWorldMasonryByWorldLocation(Integer maxId, Integer page, Integer rows, String worldLocation, Integer valid,Map<String, Object> jsonMap) throws Exception;
	
	
	
	/**
	 * 构建瀑布流织图信息，通过织图ID查询
	 * 
	 * @param maxId     最大织图id
	 * @param page      分页查询：当前页面
	 * @param rows		分页查询：每页数量
	 * @param worldId 	织图ID
	 * @param jsonMap 	返回页面参数
	 * @throws Exception 
		*	2015年12月7日
		*	mishengliang
	 */
	void buildWorldMasonryByWorldId(Integer maxId, Integer page, Integer rows, Integer worldId, Integer valid,Map<String, Object> jsonMap) throws Exception;

	/**
	 * 构建瀑布流织图信息，通过用户名或id查询
	 * 
	 * @param maxId				最大织图id
	 * @param page				分页查询：当前页面
	 * @param rows				分页查询：每页数量
	 * @param authorNameOrId	用户名或用户id
	 * @param jsonMap			返回页面参数
	 * 
	 * @throws Exception
	 * 
	 * @author zhangbo	2015年12月17日
	 */
	void buildWorldMasonryByAuthorNameOrId(Integer maxId, Integer page, Integer rows, String authorNameOrId, Integer valid,Map<String, Object> jsonMap) throws Exception;
}

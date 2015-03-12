package com.imzhitu.admin.ztworld.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 织图互动管理业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2013-8-9
 * @author ztj
 *
 */
public interface ZTWorldInteractService extends BaseService {
	
	/**
	 * 构建评论数据
	 * 
	 * @param sinceId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param worldId
	 * @param authorName
	 */
	public void buildComments(Integer sinceId, Integer maxId, int start, int limit, 
			Integer worldId, String authorName, Map<String, Object> jsonMap) throws Exception;
		
	/**
	 * 屏蔽评论
	 * @param id
	 */
	public void shieldComment(Integer id) throws Exception;
	
	/**
	 * 取消屏蔽评论
	 * 
	 * @param id
	 */
	public void unShieldComment(Integer id)throws Exception;
	
	/**
	 * 根据用户来屏蔽用户所有评论
	 * @param userId
	 * @param shield
	 * @throws Exception
	 */
	public void updateCommentShieldByUserId(Integer userId,Integer shield)throws Exception;
	
	/**
	 * 构建喜欢指定织图的用户列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param worldId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLikedUser(Integer maxId, int start, int limit, Integer worldId, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 批量保存喜欢
	 * 
	 * @param ids
	 * @param worldId
	 * @throws Exception
	 */
	public void saveLikedUser(Integer[] ids, Integer worldId) throws Exception;
	
	/**
	 * 批量保存喜欢
	 * 
	 * @param idsStr
	 * @param worldId
	 * @throws Exception
	 */
	public void saveLikedUser(String idsStr, Integer worldId) throws Exception;
	
	/**
	 * 保存指定数量僵尸喜欢
	 * 
	 * @param count
	 * @param worldId
	 * @throws Exception
	 */
	public void saveLikedZombieUser(int count, Integer worldId) throws Exception;
	
	/**
	 * 构建收藏指定织图的用户列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param worldId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildKeepUser(Integer maxId, int start, int limit, Integer worldId, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 批量保存收藏
	 * 
	 * @param idsStr
	 * @param worldId
	 * @throws Exception
	 */
	public void saveKeepUser(String idsStr, Integer worldId) throws Exception;
	
	/**
	 * 保存指定数量僵尸收藏
	 * 
	 * @param count
	 * @param worldId
	 * @throws Exception
	 */
	public void saveKeepZombieUser(int count, Integer worldId) throws Exception;
	
	/**
	 * 查询举报
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param worldId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryReport(Integer maxId,Integer start, Integer limit,Integer worldId,Map<String, Object> jsonMap)throws Exception;
	
	
	/**
	 * 删除举报
	 * @param idsStr
	 * @throws Exception
	 */
	public void deleteReportById(String idsStr) throws Exception;
}

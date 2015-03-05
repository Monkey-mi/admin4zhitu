package com.imzhitu.admin.userinfo.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.UserReportDto;

/**
 * <p>
 * 用户互动管理业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2013-9-12
 * @author ztj
 *
 */
public interface UserInteractService extends BaseService {
	
	/**
	 * 构建指定用户的粉丝列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 */
	public void buildFollow(Integer userId, int maxId, int start, int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 保存粉丝
	 * 
	 * @param idsStr
	 * @param userId
	 */
	public void saveFollows(String idsStr, Integer userId) throws Exception;
	
	/**
	 * 从马甲中随机生成指定数量的粉丝
	 * @param count
	 * @param userId
	 */
	public void saveRandomFollows(Integer count, Integer userId) throws Exception;

	/**
	 * 构建指定用户的关注列表
	 * @param userId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildConcern(Integer userId, int maxId, int start, int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建举报列表
	 * 
	 * @param report
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildReport(UserReportDto report, int page, int rows, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 更新举报为已跟进状态
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void updateReportFollowed(String idsStr) throws Exception;
	
	
}

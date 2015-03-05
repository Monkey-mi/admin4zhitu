package com.imzhitu.admin.interact.service;


import java.util.Date;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.UserLevelListDto;

public interface InteractUserlevelListService extends BaseService {
	/**
	 * 查询等级用户列表
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void QueryUserlevelList(int maxId,Integer timeType,Date beginTime,Date endTime,Integer userId,int start,int limit,Map<String ,Object> jsonMap)throws Exception;
//	public UserLevelListDto QueryUserlevelById(Integer id)throws Exception;
	
	/**
	 * 根据用户id查询用户等级
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public UserLevelListDto QueryUserlevelByUserId(Integer userId)throws Exception;
	
	/**
	 * 查询是否存在对应的userId用户等级
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean CheckUserlevelExistByUserId(Integer userId)throws Exception;
//	public void UpdateValidityById(Integer id , Integer validity)throws Exception;
//	public void UpdateValidityByUserId(Integer userId, Integer validity) throws Exception;
	
	/**
	 * 根据ids删除等级用户
	 * @param idsStr
	 * @throws Exception
	 */
	public void DeleteUserlevelByIds(String idsStr)throws Exception;
//	public void DeleteUserlevelByUserIds(String userIdsStr)throws Exception;
	
	/**
	 * 、增加等级用户
	 * @param userLevelDto
	 * @throws Exception
	 */
	public void AddUserlevel(UserLevelListDto userLevelDto)throws Exception;
	
	/**
	 * 扫描新发的织图并将其添加到互动
	 */
	public void ScanNewWorldAndJoinIntoInteract();
	
	/**
	 * 更新等级用户
	 * @param userLevelListDto
	 * @throws Exception
	 */
	public void UpdateUserlevelByUserId(UserLevelListDto userLevelListDto)throws Exception;
}

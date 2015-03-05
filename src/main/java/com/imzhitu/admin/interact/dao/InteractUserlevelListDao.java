package com.imzhitu.admin.interact.dao;

import com.imzhitu.admin.common.pojo.InteractWorldLabelDto;
import com.imzhitu.admin.common.pojo.UserLevelListDto;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface InteractUserlevelListDao extends BaseDao{
	/**
	 * 查询用户等级列表
	 * @param rowSelection
	 * @return
	 */
	public List<UserLevelListDto> QueryUserlevelList(Map<String,Object> attr,RowSelection rowSelection);
	
	/**
	 * 查询用户等级列表
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<UserLevelListDto> QueryUserlevelListByMaxId(Integer maxId,Map<String,Object> attr,RowSelection rowSelection);
	
	/**
	 * 根据id查询用户等级
	 * @param id
	 * @return
	 */
	public UserLevelListDto QueryUserlevelById(Integer id);
	
	/**
	 * 根据用户id查询用户等级
	 * @param userId
	 * @return
	 */
	public UserLevelListDto QueryUserlevelByUserId(Integer userId);
	
	/**
	 * 根据用户id更新用户等级
	 * @param userId
	 * @param validity
	 */
	public void UpdateValidityByUserId(Integer userId,Integer validity);
	
	/**
	 * 根据id更新用户等级
	 * @param id
	 * @param validity
	 */
	public void UpdateValidityById(Integer id,Integer validity);
	
	/**
	 * 增加等级用户
	 * @param userLevelDto
	 */
	public void AddUserlevel(UserLevelListDto userLevelDto);
	
	/**
	 * 根据ids删除等级用户
	 * @param ids
	 */
	public void DeleteUserlevelByIds(Integer[] ids);
	
	/**
	 *根据用户ids删除等级用户
	 * @param userIds
	 */
	public void DeleteUserlevelByUserids(Integer[] userIds);
	
	/**
	 * 查询新发织图对应的用户等级
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<InteractWorldLabelDto> QueryNewWorldUserlevelListByTime(Date startTime,Date endTime);
	
	/**
	 * 获取等级用户总数
	 * @param maxId
	 * @return
	 */
	public long GetUserlevelListCount(Integer maxId,Map<String,Object> attr);
	
	/**
	 * 查询对应userId的用户等级是否存在
	 * @param userId
	 * @return
	 */
	public boolean CheckUserlevelExistByUserId(Integer userId);
	
	/**
	 * 根据用户id更新用户等级
	 * @param userLevelListDto
	 */
	public void UpdateUserlevelByUserId(UserLevelListDto userLevelListDto);
}

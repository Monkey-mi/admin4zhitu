package com.imzhitu.admin.interact.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.InteractUser;
import com.imzhitu.admin.common.pojo.InteractWorld;

/**
 * <p>
 * 织图互动业务逻辑访问接口
 * </p>
 * 
 * @author ztj
 *
 */
public interface InteractWorldService extends BaseService {

	/**f
	 * 构建互动列表
	 * 
	 * @param start
	 * @param limit
	 * @param jsonMap
	 */
	public void buildInteracts(Integer worldId, int maxId, int start, int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建互动信息总数
	 * 
	 * @param worldId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildInteractSum(Integer worldId, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 根据织图id构建互动信息
	 * 
	 * @param idsStr
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildInteractByWorldIds(String idsStr, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建互动评论列表
	 * 
	 * @param interactId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildComments(Integer interactId, int maxId, int start, int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建互动喜欢列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLikeds(Integer interactId, int maxId, int start, int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建互动播放列表
	 * 
	 * @param worldId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildClicks(Integer interactId, int maxId, int start, int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 获取计划时间列表
	 * 
	 * @param start
	 * @param duration 为期
	 * @param size
	 * @return
	 */
	public List<Date> getScheduleDates(Date start, int duration, int size);
	
	/**
	 * 获取计划次数列表
	 * 
	 * @param size
	 * @return
	 */
	public List<Integer> getScheduleCount(int size, int duration);
	
	
	/**
	 * 添加互动信息 
	 * 
	 * @param worldId
	 * @param clickCount
	 * @param likedCount
	 * @param commentIds
	 * @param duration
	 * @throws Exception
	 */
	public void saveInteract(Integer worldId, Integer clickCount, Integer likedCount, 
			String[] commentIds, Integer duration) throws Exception;
	
	/**
	 * 添加评论
	 * 
	 * @param worldId
	 * @param userId
	 * @param commentId
	 * @param dateAdded
	 * @param dateShedule
	 * @throws Exception
	 */
	public void saveComment(Integer interactId, Integer worldId, Integer userId, Integer commentId,
			Date dateAdded, Date dateShedule) throws Exception;
	
	/**
	 * 添加喜欢
	 * 
	 * @param worldId
	 * @param userId
	 * @param dateAdded
	 * @param dateShedule
	 * @throws Exception
	 */
	public void saveLiked(Integer interactId, Integer worldId, Integer userId, Date dateAdded, Date dateShedule) throws Exception;
	
	/**
	 * 添加播放次数
	 * 
	 * @param worldId
	 * @param clickCount
	 * @param dateAdded
	 * @param dateShedule
	 * @throws Exception
	 */
	public void saveClick(Integer interactId, Integer worldId, Integer clickCount, Date dateAdded, Date dateShedule) throws Exception;

	
	/**
	 * 更新所有互动
	 * 
	 * @param maxId
	 */
	public void updateInteractValid(Integer maxId) throws Exception;
	
	/**
	 * 删除互动
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void deleteInteract(String idsStr) throws Exception;
	
	
	/**
	 * 构建用户互动列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildUserInteract(int maxId, int start, int limit,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 根据用户id查询互动
	 * 
	 * @param userId
	 * @return
	 */
	public InteractUser getUserInteractByUID(Integer userId);
	
	
	/**
	 * 保存用户互动
	 * 
	 * @param userId
	 * @param followCount
	 * @param duration
	 * @throws Exception
	 */
	public void saveUserInteract(Integer userId, Integer followCount, Integer duration) throws Exception;
	
	/**
	 * 删除用户互动
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void deleteUserInteract(String idsStr) throws Exception;
	
	/**
	 * 构建粉丝列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildFollow(Integer interactId, int maxId, int start, int limit,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 提交播放互动
	 * 
	 * @param click
	 */
	public void commitClick();
	
	/**
	 * 提交评论互动
	 * 
	 * @param comment
	 */
	public void commitComment();
	
	/**
	 * 提交喜欢互动
	 * 
	 * @param liked
	 */
	public void commitLiked();
	
	/**
	 * 提交粉丝互动
	 * 
	 * @param follow
	 */
	public void commitFollow();
	
	/**
	 * 更新所有为完成的互动到今天
	 */
	public void updateUnFinishedInteractSchedule() throws Exception;
	
	/**
	 * 构建跟踪列表
	 */
	public void buildTracker(Map<String, Object> jsonMap);
	
	/**
	 * 跟踪互动
	 */
	public void trackInteract();
	
	/**
	 * 根据worldID列表来更新worldID对应的有效状态(织图互动表)
	 * @param wids
	 * @param valid
	 */
	public void updateInteractValidByWIDs(Integer[] wids,Integer valid) throws Exception;
	

	
	/**
	 *  根据id更新互动评论表中的调度时间和有效性
	 * @param valid
	 * @param date_schedule
	 * @param id
	 * @throws Exception
	 */
	public void updateCommentValidAndSCHTimeById(Integer valid,Date date_schedule,Integer id) throws Exception;
	
	/**
	 *  根据id更新互动评论表中的调度时间和有效性
	 * @param valid
	 * @param date_schedule
	 * @param id
	 * @throws Exception
	 */
	public void updateLikedValidAndSCHTimeById(Integer valid,Date date_schedule,Integer id) throws Exception;
	
	/**
	 *  根据id更新互动评论表中的调度时间和有效性
	 * @param valid
	 * @param date_schedule
	 * @param id
	 * @throws Exception
	 */
	public void updateClickValidAndSCHTimeById(Integer valid,Date date_schedule,Integer id) throws Exception;
	
	/**
	 * 更新播放互动计划的调度时间和有效性
	 * @param interactId
	 * @param date_add
	 * @throws Exception
	 */
	public void updateClickValidAndSCHTimeByInteractId(Integer interactId,Date date_add)throws Exception;
	
	/**
	 * 更新播放互动计划的调度时间和有效性
	 * @param interactId
	 * @param date_add
	 * @throws Exception
	 */
	public void updateLikedValidAndSCHTimeByInteractId(Integer interactId,Date date_add)throws Exception;
	
	/**
	 * 更新播放互动计划的调度时间和有效性
	 * @param interactId
	 * @param date_add
	 * @throws Exception
	 */
	public void updateCommentValidAndSCHTimeByInteractId(Integer interactId,Date date_add)throws Exception;
	
	/**
	 * 根据worldId查询互动表
	 * @param worldId
	 * @return
	 * @throws Exception
	 */
	public InteractWorld queryInteractByWorldId(Integer worldId) throws Exception;
	
	/**
	 * 更新互动播放喜欢评论计划
	 * @param wids
	 * @throws Exception
	 */
	public void updateInteractCommentLikedClickByWorldId(Integer[] wids)throws Exception;
	
	/**
	 * 根据织图id查询互动id
	 */
	public Integer queryIntegerIdByWorldId(Integer wId)throws Exception;
	
	/**
	 * 删除计划评论by ids
	 */	
	public void deleteInteractCommentByids(String idsStr)throws Exception;
	
	/**
	 * 第二版本的时间调度算法，主要是使用分钟来进行调度
	 * @param start
	 * @param minuteDuration
	 * @param size
	 * @return
	 * @author zxx
	 */
	public List<Date> getScheduleDatesV2(Date start,int minuteDuration,int size  );
	
	/**
	 * 第二版本的添加互动，主要是支持以分钟来进行调度
	 * @param worldId
	 * @param clickCount
	 * @param likedCount
	 * @param commentIds
	 * @param minuteDuration
	 * @throws Exception
	 * @author zxx
	 */
	public void saveInteractV2(Integer worldId, Integer clickCount,
			Integer likedCount, String[] commentIds, Integer minuteDuration) throws Exception ;
	
	/**
	 * 第二版本的获取计划时间
	 * @param total
	 * @param minutes
	 * @return
	 */
	public List<Integer> getScheduleCountV2(int total, int minutes) ;
	
}

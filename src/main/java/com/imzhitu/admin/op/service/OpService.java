package com.imzhitu.admin.op.service;

import java.util.Date;
import java.util.Map;

import com.hts.web.common.pojo.OpActivity;
import com.hts.web.common.pojo.OpActivityAward;
import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 织图运营管理业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2013-8-8
 * @author ztj
 *
 */
public interface OpService extends BaseService {

	/**
	 * 构建活动列表
	 * 
	 * @param maxSerial
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildActivity(int maxSerial, int start, int limit, 
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 保存活动
	 * 
	 * @param id
	 * @param titlePath
	 * @param titleThumbPath
	 * @param channelPath
	 * @param activityName
	 * @param activityTitle
	 * @param activityDesc
	 * @param channelLink
	 * @param activityLogo
	 * @param activityDate
	 * @param deadline
	 * @param commericial
	 * @param shareTitle
	 * @param shareDesc
	 * @param sponsorIds
	 * @param valid
	 * @throws Exception
	 */
	public void saveActivity(Integer id, String titlePath, String titleThumbPath, String channelPath,
			String activityName, String activityTitle, String activityDesc, 
			String activityLink, String activityLogo, Date activityDate, 
			Date deadline, Integer commericial, String shareTitle, String shareDesc,
			String sponsorIds, Integer valid) throws Exception;
	
	/**
	 * 根据id查询活动信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public OpActivity queryActivityById(Integer id) throws Exception;
	
	/**
	 * 更新活动
	 * 
	 * @param id
	 * @param titlePath
	 * @param titleThumbPath
	 * @param channelPath
	 * @param activityName
	 * @param activityTitle
	 * @param activityDesc
	 * @param activityLink
	 * @param activityLogo
	 * @param activityDate
	 * @param deadline
	 * @param commericial
	 * @param shareTitle
	 * @param sponsorIds
	 * @param valid
	 * @throws Exception
	 */
	public void updateActivity(Integer id, String titlePath, String titleThumbPath, 
			String channelPath,String activityName, String activityTitle, String activityDesc, 
			String activityLink, String activityLogo, Date activityDate, Date deadline, 
			Integer commericial, String shareTitle,String shareDesc,
			String sponsorIds, Integer valid) throws Exception;
	
	/**
	 * 根据id删除活动
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteActivityById(Integer id) throws Exception;
	
	/**
	 * 构建有效活动列表
	 * 
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildCacheActivity(Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建活动织图
	 * 
	 * @param maxId
	 * @param activityId
	 * @param valid
	 * @param weight
	 * @param worldId
	 * @param isWinner
	 * @param page
	 * @param rows
	 * @param jsonMap
	 */
	public void buildActivityWorld(Integer maxId, Integer activityId, Integer valid, Integer weight, Integer superb, Integer worldId,Integer userId,String userName,
			Integer isWinner, int page, int rows, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 取消广场活动织图屏蔽
	 * 
	 * @param activityId
	 * @param worldId
	 * @param authorId
	 * @param authorName
	 * @param notifyUser
	 * @param notifyTip
	 * @throws Exception
	 */
	public void updateActivityWorldValid(Integer valid, Integer activityWorldId, Integer activityId,
			Integer worldId,Integer authorId, String authorName, String notifyTip)
			throws Exception;
	
	/**
	 * 批量更新活动织图
	 * 
	 * @param idsStr
	 * @param valid
	 * @throws Exception
	 */
	public void updateActivityWorldValids(String idsStr, Integer valid) throws Exception;
	
	/**
	 * 添加广场活动审核通过提醒
	 *  
	 * @param activityId
	 * @param worldId
	 * @param recipientId
	 * @param recipientName
	 * @param msg
	 * @throws Exception
	 */
	public void addActivityWorldCheckMsg(Integer activityId, Integer worldId, Integer recipientId, String recipientName, String msg)
			throws Exception;

	/**
	 * 添加广场活动织图
	 * 
	 * @param worldId
	 * @param activityIds
	 */
	public void addActivityWorld(Integer worldId, String activityIds) throws Exception;
	
	/**
	 * 构建活动logo列表
	 * 
	 * @param maxSerial
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildActivityLogo(Integer maxSerial,int page,
			int rows, Map<String,Object> jsonMap) throws Exception;

	/**
	 * 保存活动Logo
	 * 
	 * @param activityId
	 * @param logoPath
	 * @throws Exception
	 */
	public void saveActivityLogo(Integer activityId, String logoPath) throws Exception;

	/**
	 * 根据ids删除活动logo
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void deleteActivityLogo(String idsStr) throws Exception;
	
	/**
	 * 更新logo有效性
	 * 
	 * @param idsStr
	 * @param valid
	 * @throws Exception
	 */
	public void updateActivityLogoValid(String idsStr, Integer valid) throws Exception;
	
	/**
	 * 更新LOGO排序
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void updateActivityLogoSerial(String[] idsStr) throws Exception;
	
	/**
	 * 构建活动奖品列表
	 * 
	 * @param maxSerial
	 * @param start
	 * @param limit
	 * @param activityId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildActivityAward(int maxSerial, int start, int limit, Integer activityId,  
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建活动奖品列表
	 * 
	 * @param maxSerial
	 * @param activityId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildActivityAward(Integer activityId,  
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 保存活动奖品
	 * 
	 * @param activityId
	 * @param iconThumbPath
	 * @param iconPath
	 * @param awardName
	 * @param awardDesc
	 * @param price
	 * @param awardLink
	 * @param total
	 * @param remain
	 * @throws Exception
	 */
	public void saveActivityAward(Integer activityId, String iconThumbPath, String iconPath,
			String awardName, String awardDesc, Double price, String awardLink,
			Integer total, Integer remain) throws Exception;
	
	/**
	 * 更新活动奖品
	 * 
	 * @param id
	 * @param activityId
	 * @param iconThumbPath
	 * @param iconPath
	 * @param awardName
	 * @param awardDesc
	 * @param price
	 * @param awardLink
	 * @param total
	 * @param remain
	 * @param serial
	 * @throws Exception
	 */
	public void updateActivityAward(Integer id, Integer activityId, String iconThumbPath, 
			String iconPath, String awardName, String awardDesc, Double price, 
			String awardLink, Integer total, Integer remain, Integer serial) throws Exception;
	
	/**
	 * 批量删除活动奖品
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void deleteActivityAward(String idsStr) throws Exception;
	
	/**
	 * 更新活动奖品排序
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void updateActivityAwardSerial(String[] idsStr) throws Exception;
	
	/**
	 * 根据id查询奖品
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public OpActivityAward getActivityAwardById(Integer id) throws Exception;
	
	/**
	 * 添加获胜织图
	 * 
	 * @param activityId
	 * @param worldId
	 * @param userId
	 * @param awardId
	 * @throws Exception
	 */
	public void saveActivityWinner(Integer activityId, Integer worldId, 
			Integer userId, Integer awardId) throws Exception;
	
	/**
	 * 删除获胜织图
	 * 
	 * @param activityId
	 * @param worldId
	 * @throws Exception
	 */
	public void deleteActivityWinner(Integer activityId, Integer worldId) throws Exception;

	/**
	 * 查询获胜织图奖品id
	 * 
	 * @param activityId
	 * @param worldId
	 * @return
	 * @throws Exception
	 */
	public Integer queryActivityWinnerAwardId(Integer activityId, Integer worldId) throws Exception;
	
	/**
	 * 更新获胜织图奖品
	 * 
	 * @param activityId
	 * @param worldId
	 * @param awardId
	 * @throws Exception
	 */
	public void updateActivityWinnerAward(Integer activityId, Integer worldId, Integer awardId) throws Exception;
	
	/**
	 * 更新广场分类，临时使用，还未做UI调用
	 * 
	 * @throws Exception
	 */
	public void updateOpWorldType() throws Exception;

	/**
	 * 更新活动织图的加精状态
	 * 
	 * @param id		
	 * @param superb	加精状态，1：加精，2：未加精
	 * @author zhangbo	2015年9月16日
	 */
	public void updateActivityWorldSuperb(Integer id, Integer superb);
}

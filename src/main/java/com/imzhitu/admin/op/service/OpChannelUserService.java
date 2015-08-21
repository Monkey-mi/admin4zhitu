package com.imzhitu.admin.op.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.OpChannelUserDto;

public interface OpChannelUserService extends BaseService{
	/**
	 * 查询前n个频道用户，计算公式：平均几天上精选*3+平均几天上频道*3-注册多少个周/2+最近发图时间*2。
	 * @param dto
	 * @return
	 */
	public List<Integer> queryChannelUserRankTopN(Integer channelId)throws Exception;
	
	/**
	 * 插入
	 * @param dto
	 */
	public void insertChannelUser(Integer userId,Integer channelId,Integer valid,Integer operatorId)throws Exception;
	
	/**
	 * 插入,根据认证id
	 * // TODO 这块入口不用了，要整改，要删除掉
	 * @param dto
	 */
	public void addChannelUserByVerifyId(Integer userId,Integer verifyId,Integer valid,Integer operatorId)throws Exception;
	
	/**
	 * 根据织图id来增加频道用户
	 * @param worldId
	 * @param channelId
	 * @param valid
	 * @param operatorId
	 * @throws Exception
	 */
	public void addChannelUserByWorldId(Integer worldId,Integer channelId,Integer valid,Integer operatorId)throws Exception;
	
	/**
	 * 批量插入
	 * @param userIdsStr
	 * @param channelId
	 * @param operatorId
	 * @throws Exception
	 */
	public void batchInsertChannelUser(String userIdsStr,Integer channelId,Integer operatorId)throws Exception;
	
	/**
	 * 删除
	 * @param dto
	 */
	public void deleteChannelUser(Integer id,Integer userId,Integer channelId,Integer valid,Integer operatorId)throws Exception;
	
	/**
	 * 批量删除
	 * @param ids
	 * @throws Exception
	 */
	public void deleteChannelUserByIds(String ids)throws Exception;
	
	/**
	 * 更新Valid
	 * @param dto
	 */
	public void updateChannelUserValid(Integer id,Integer userId,Integer channelId,Integer valid,Integer operatorId)throws Exception;
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	public void queryChannelUserForList(OpChannelUserDto dto ,int maxId, int start, int limit,Map<String, Object> jsonMap)throws Exception;
	
	/**
	 *  查询用户平均几天上精选*3 对应的分数 
	 * @param userId
	 * @return
	 */
	public Integer querySuperbScore(Integer userId)throws Exception;
	
	/**
	 * 平均几天上频道*3 对应的分数
	 * @param userId
	 * @return
	 */
	public Integer queryChannelScore(Integer userId)throws Exception;
	
	/**
	 * 注册多少个周/2
	 * @param userId
	 * @return
	 */
	public Integer queryRegisterScore(Integer userId)throws Exception;
	
	/**
	 * 最近发图时间*2
	 * @param userId
	 * @return
	 */
	public Integer queryLastWorldScore(Integer userId)throws Exception;
}

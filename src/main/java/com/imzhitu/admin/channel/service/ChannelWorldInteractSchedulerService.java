package com.imzhitu.admin.channel.service;

import java.util.List;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.ChannelWorldInteractScheduler;

/**
 * 频道织图生效规划的互动相关操作接口类
 * 
 * @author zhangbo	2015年10月29日
 *
 */
public interface ChannelWorldInteractSchedulerService extends BaseService {
	
	/**
	 * 新增频道织图生效规划互动
	 *
	 * @param channelId	频道id
	 * @param worldId	织图id
	 *  
	 * @author zhangbo	2015年10月29日
	 */
	void addChannelWorldInteractScheduler(Integer channelId, Integer worldId) throws Exception;

	/**
	 * 根据频道id与织图id，查询频道织图规划互动表未完成的数据
	 * 
	 * @param channelId	频道id
	 * @param worldId	织图id
	 * @return
	 * @throws Exception
	 * @author zhangbo	2015年11月3日
	 */
	List<ChannelWorldInteractScheduler> queryChannelWorldInteractSchedulerNotCompleteList(Integer channelId, Integer worldId) throws Exception;
	
	/**
	 * 根据频道id与织图id，查询频道织图规划互动表未生效的数据
	 * 
	 * @param channelId	频道id
	 * @param worldId	织图id
	 * @return
	 * @throws Exception
	 * @author zhangbo	2015年11月5日
	 */
	List<ChannelWorldInteractScheduler> queryChannelWorldInteractSchedulerInvalidList(Integer channelId, Integer worldId) throws Exception;
	
	/**
	 * 根据频道织图规划互动对象主键id集合，设置频道织图规划生效
	 * 
	 * @param ids
	 * @throws Exception
	 * @author zhangbo	2015年11月5日
	 */
	void setValidByIds(Integer[] ids) throws Exception;
	
}

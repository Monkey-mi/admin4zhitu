package com.imzhitu.admin.channel.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.channel.mapper.ChannelWorldInteractSchedulerMapper;
import com.imzhitu.admin.channel.service.ChannelWorldInteractSchedulerService;
import com.imzhitu.admin.common.pojo.ChannelWorldInteractScheduler;
import com.imzhitu.admin.common.util.AdminLoginUtil;
import com.imzhitu.admin.interact.service.InteractChannelWorldService;

/**
 * 频道织图生效规划的互动相关操作实现类
 * 
 * @author zhangbo	2015年10月29日
 *
 */
@Service("com.imzhitu.admin.channel.service.impl.ChannelWorldInteractSchedulerServiceImpl")
public class ChannelWorldInteractSchedulerServiceImpl implements ChannelWorldInteractSchedulerService {
	
	/**
	 * 查询频道织图生效规划互动的时间跨度，20分钟
	 * 
	 * @author zhangbo	2015年10月29日
	 */
	private long SPAN_TIME = 20 * 60 * 1000;
	
	@Autowired
	ChannelWorldInteractSchedulerMapper mapper;
	
	@Autowired
	InteractChannelWorldService interactChannelWorldService;

	private static Logger logger = Logger.getLogger(ChannelWorldInteractSchedulerServiceImpl.class);

	@Override
	public void addChannelWorldInteractScheduler(Integer channelId, Integer worldId) throws Exception {
		// 保存频道织图规划互动
		mapper.insert(channelId, worldId, AdminLoginUtil.getCurrentLoginId());
	}
	
	@Override
	public List<ChannelWorldInteractScheduler> queryChannelWorldInteractSchedulerNotCompleteList(Integer channelId, Integer worldId) throws Exception {
		return mapper.queryChannelWorldInteractSchedulerNotCompleteList(channelId, worldId);
	}
	
	@Override
	public List<ChannelWorldInteractScheduler> queryChannelWorldInteractSchedulerInvalidList(Integer channelId, Integer worldId) throws Exception {
		return mapper.queryChannelWorldInteractSchedulerInvalidList(channelId, worldId);
	}
	
	@Override
	public void setValidByIds(Integer[] ids) throws Exception {
		for (Integer id : ids) {
			mapper.setValidAndScheduleDateById(id, new Date());
		}
	}
	
	/**
	 * 执行频道织图规划互动
	 * 
	 * @author zhangbo	2015年10月29日
	 */
	public void doChannelWorldInteractSchedule(){
		
		logger.info("=== begin doChannelWorldInteractSchedule ===");
		Date endTime = new Date();
		Date beginTime = new Date(endTime.getTime() - SPAN_TIME);
		
		try {
			List<ChannelWorldInteractScheduler> list = mapper.queryChannelWorldInteractSchedulerListByTime(beginTime , endTime);
			if ( list != null && list.size() !=0 ) {
				for (ChannelWorldInteractScheduler scheduler : list) {
					
					// 开始生成频道织图互动计划
					interactChannelWorldService.saveChannelWorldInteract(scheduler.getChannelId(), scheduler.getWorldId());
					
					// 设置频道织图生效规划为完成
					mapper.update(scheduler.getChannelId(), scheduler.getWorldId(), Tag.TRUE);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		logger.info("=== end doChannelWorldInteractSchedule ===");
	}

}

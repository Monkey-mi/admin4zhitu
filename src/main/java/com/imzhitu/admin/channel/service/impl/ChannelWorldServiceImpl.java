package com.imzhitu.admin.channel.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.HTSException;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.imzhitu.admin.channel.service.ChannelWorldInteractSchedulerService;
import com.imzhitu.admin.channel.service.ChannelWorldService;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.ChannelWorldInteractScheduler;
import com.imzhitu.admin.common.pojo.OpChannelWorld;
import com.imzhitu.admin.common.pojo.OpChannelWorldSchedulaDto;
import com.imzhitu.admin.common.pojo.OpSysMsg;
import com.imzhitu.admin.op.dao.ChannelAutoRejectIdCacheDao;
import com.imzhitu.admin.op.mapper.ChannelWorldMapper;
import com.imzhitu.admin.op.mapper.SysMsgMapper;
import com.imzhitu.admin.op.service.OpChannelWorldSchedulaService;
import com.imzhitu.admin.op.service.OpMsgService;

/**
 * 频道织图业务层实现类
 * 
 * @author zhangbo	2015年11月5日
 *
 */
public class ChannelWorldServiceImpl implements ChannelWorldService {
	
	/**
	 * 织图被选入普通频道（往频道中发图直接生效的为普通频道）通知发送时间间隔，单位ms
	 * 间隔：一周
	 * 含义：一周内用户的织图被选入频道只提示一次
	 * 
	 * @author zhangbo	2015年9月7日
	 */
	private static final long NORMAL_CHANNEL_WORLD_SENDNOTICE_TIME_SPAN_MS = 7 * 24 * 60 * 60 * 1000;
	
	/**
	 * 织图被选入拒绝频道（往频道中发图需要小编审核后生效的为拒绝频道）通知发送时间间隔，单位ms
	 * 间隔：二周
	 * 含义：二周内用户的织图被选入频道只提示一次
	 * 
	 * @author zhangbo	2015年9月17日
	 */
	private static final long REJECT_CHANNEL_WORLD_SENDNOTICE_TIME_SPAN_MS = 2 * 7 * 24 * 60 * 60 * 1000;
	
	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private ChannelWorldMapper channelWorldMapper;
	
	@Autowired
	private com.hts.web.operations.service.ChannelService webChannelService;
	
	@Autowired
	private OpChannelWorldSchedulaService channelWorldSchedulaService;
	
	@Autowired
	private ChannelWorldInteractSchedulerService channelWorldInteractScheduler;
	
	@Autowired
	private SysMsgMapper sysMsgMapper;
	
	@Autowired
	private OpMsgService msgService;
	
	@Autowired
	private ChannelAutoRejectIdCacheDao rejectChannelCacheDao;

	@Override
	public void setChannelWorldValidByOperator(Integer channelId, Integer worldId) throws Exception {
		
		setChannelWorldValid(channelId, worldId);
		
		// 如果存在频道织图计划生效，则删除计划
		List<OpChannelWorldSchedulaDto> schedulerList = channelWorldSchedulaService.queryChannelWorldValidSchedulaForList(channelId, worldId);
		
		if ( schedulerList != null && schedulerList.size() !=0 ) {
			// 定义批量删除有效性计划的主键id集合字符串，以逗号“,”分隔
			String idString = "";
			for (int i = 0; i < schedulerList.size(); i++) {
				if ( i == 0 ) {
					idString += schedulerList.get(i).getId();
				} else {
					idString += "," + schedulerList.get(i).getId();
				}
			}
			channelWorldSchedulaService.delChannelWorldValidSchedula(idString);
		}
	}

	@Override
	public void setChannelWorldInvalidByOperator(Integer channelId, Integer worldId) throws Exception {
		
		channelWorldMapper.updateInvalid(channelId, worldId);
		
		// 更新频道织图与子图数
		webChannelService.updateWorldAndChildCount(channelId);
		
	}

	@Override
	public void setChannelWorldValidByScheduler(Integer channelId, Integer worldId) throws Exception {
		setChannelWorldValid(channelId, worldId);
	}
	
	/**
	 * 设置频道织图生效
	 * 
	 * @param channelId	频道id
	 * @param worldId	织图id
	 * @throws Exception
	 * @author zhangbo	2015年11月5日
	 */
	private void setChannelWorldValid(Integer channelId, Integer worldId) throws Exception {
		// 更新频道织图生效，并且更新为最新
		Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_WORLD_ID);
		channelWorldMapper.updateValidAndSerial(channelId, worldId, serial);
				
		// 更新频道织图与子图数
		webChannelService.updateWorldAndChildCount(channelId);
		
		// 发送通知
		addChannelWorldNoticeMsg(channelId, worldId);
				
		// 查询频道织图是否存在互动的规划，若存在，则设置为生效，使其开始在job中执行
		List<ChannelWorldInteractScheduler> schedulerInvalidList = channelWorldInteractScheduler.queryChannelWorldInteractSchedulerInvalidList(channelId, worldId);
		if ( schedulerInvalidList != null && schedulerInvalidList.size() != 0 ) {
			Integer[] ids = new Integer[schedulerInvalidList.size()];
			for (int i = 0; i < schedulerInvalidList.size(); i++) {
				ids[i] = schedulerInvalidList.get(i).getId();
			}
			channelWorldInteractScheduler.setValidByIds(ids);
		}
	}
	
	private void addChannelWorldNoticeMsg(Integer channelId, Integer worldId) throws Exception {
		OpChannelWorld world = channelWorldMapper.queryChannelWorldByChannelIdAndWorldId(channelId, worldId);
		if (world == null) {
			throw new HTSException("记录已经被删除");
		}
		
		// 查询出最后一次给此用户推送的通知，取出时间，与此时比较，大于一周的再发送通知，不超过一周的不发送通知	mishengliang
		OpSysMsg sysMsg = new OpSysMsg();
		sysMsg.setRecipientId(world.getAuthorId()); // 织图作者作为接收人
		sysMsg.setObjType(Tag.USER_MSG_CHANNEL_WORLD);	// 通知消息类型为织图被选入频道
		sysMsg.setObjMeta2(String.valueOf(world.getChannelId()));	// 要查询的消息中，附加消息objMeta2存储的为频道id
		
		OpSysMsg msgObject = sysMsgMapper.getLastMsg(sysMsg);
		
		// 查询结果为空，可以发送消息
		if ( msgObject == null ) {
			msgService.sendChannelSystemNotice(world.getAuthorId(), Admin.NOTICE_WORLD_INTO_CHANNEL, world.getChannelId(), world.getWorldId());
		} else {
			long now = new Date().getTime();
			long last = msgObject.getMsgDate().getTime();
			
			// 若织图所在频道为拒绝频道（往频道中发图需要小编审核后生效的为拒绝频道），则要判断发送通知的时间间隔是否大于两周，其他频道则判断是否大于一周
			if ( rejectChannelCacheDao.getAutoRejectChannelCache().contains(world.getChannelId()) ) {
				// 相隔时间大于二周的，可以发送消息
				if (last - now >= REJECT_CHANNEL_WORLD_SENDNOTICE_TIME_SPAN_MS) {
					msgService.sendChannelSystemNotice(world.getAuthorId(), Admin.NOTICE_WORLD_INTO_CHANNEL, world.getChannelId(), world.getWorldId());
				}
			} else {
				// 相隔时间大于一周的，可以发送消息
				if (last - now >= NORMAL_CHANNEL_WORLD_SENDNOTICE_TIME_SPAN_MS) {
					msgService.sendChannelSystemNotice(world.getAuthorId(), Admin.NOTICE_WORLD_INTO_CHANNEL, world.getChannelId(), world.getWorldId());
				}
			}
		}
	}

}

package com.imzhitu.admin.op.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.HTSException;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.OpChannelMemberDTO;
import com.imzhitu.admin.common.util.AdminLoginUtil;
import com.imzhitu.admin.op.mapper.OpChannelMemberMapper;
import com.imzhitu.admin.op.service.OpChannelMemberService;
import com.imzhitu.admin.op.service.OpMsgService;

/**
 * 频道成员相关操作的业务实现类
 * 包括对频道成员的CRUD，与频道红人的CRUD及消息推送等操作
 * 
 * @author zhangbo	2015年8月18日
 *
 */
@Service("com.imzhitu.admin.op.service.impl.OpChannelMemberServiceImpl")
public class OpChannelMemberServiceImpl extends BaseServiceImpl implements OpChannelMemberService {
	
	/**
	 * 频道红人计划调度，按时间查询，设置的查询范围跨度
	 * 当前是设置查1小时之内的数据
	 * @author zhangbo	2015年8月20日
	 */
	private static final long channelStarSchedulaTimeSpan = 60*60*1000;

	/**
	 * 频道成员相关数据接口
	 * @author zhangbo	2015年8月18日
	 */
	@Autowired
	private OpChannelMemberMapper channelMemberMapper;
	
	/**
	 * web端sequence业务逻辑访问接口
	 * @author zhangbo	2015年8月20日
	 */
	@Autowired
	private com.hts.web.common.service.KeyGenService keyGenService;
	
	/**
	 * 系统发送通知公告接口类
	 * @author zhangbo	2015年9月6日
	 */
	@Autowired
	private OpMsgService msgService;
	
	@Override
	public void updateChannelMemberDegree(Integer channelId, Integer userId, Integer degree) throws Exception {
		if (channelId == null || userId == null) {
			return;
		}
		OpChannelMemberDTO dto = new OpChannelMemberDTO();
		dto.setChannelId(channelId);
		dto.setUserId(userId);
		dto.setDegree(degree);
		channelMemberMapper.updateChannelMember(dto);

	}

	@Override
	public void buildChannelMemberList(Integer channelId, Integer userId, String userName, Integer userStarId, Integer notified, Integer shield, Integer maxId, int page, int rows, Map<String, Object> jsonMap) throws Exception {
		OpChannelMemberDTO dto = new OpChannelMemberDTO();
		dto.setChannelId(channelId);
		dto.setUserId(userId);
		dto.setUserName(userName);
		dto.setStar(userStarId);
		dto.setNotified(notified);
		dto.setShield(shield);

		buildNumberDtos(dto, page, rows, jsonMap, new NumberDtoListAdapter<OpChannelMemberDTO>() {
			@Override
			public long queryTotal(OpChannelMemberDTO dto) {
				return channelMemberMapper.queryChannelMemberTotalCount(dto);
			}

			@Override
			public List<? extends AbstractNumberDto> queryList(OpChannelMemberDTO dto) {
				return channelMemberMapper.queryChannelMember(dto);
			}
		}, new NumberDtoListMaxIdAdapter() {

			@Override
			public Serializable getMaxId(List<? extends Serializable> list) throws Exception {
				return channelMemberMapper.getChannelMemberMaxId();
			}

		});

	}

	@Override
	public void saveChannelStar(Integer channelMemberId) throws Exception {
		OpChannelMemberDTO memberDto = getChannelMemberById(channelMemberId);
		
		// 若不是红人，则插入到频道红人表中
		if ( !memberDto.isChannelStar() ) {
			// 频道成员被选为红人，则发送通知给用户，要先通知，此方法有抛出异常，若有异常，则不进行后续操作
			msgService.sendChannelSystemNotice(memberDto.getUserId(), Admin.NOTICE_CHANNELMEMBER_TO_STAR, memberDto.getChannelId(), null);
			
			// 因为频道成员与频道红人关键字段id，channel_id，user_id相同，所以直接使用dto进行插入操作
			channelMemberMapper.insertChannelStar(memberDto);
			
			
			// 设置channel_star为1，并刷新频道成员为红人
			memberDto.setChannelStar(1);
			channelMemberMapper.updateChannelMember(memberDto);
		}
	}

	@Override
	public void deleteChannelStars(Integer[] channelMemberIds) {
		channelMemberMapper.deleteChannelStarByIds(channelMemberIds);
		
		// 删除频道红人后，修改频道成员的是否是红人的标记位，都设置成不为红人 
		for (Integer channelMemberId : channelMemberIds) {
			OpChannelMemberDTO dto = new OpChannelMemberDTO();
			dto.setChannelMemberId(channelMemberId);
			dto.setChannelStar(Tag.FALSE);
			channelMemberMapper.updateChannelMember(dto);
		}
	}

	@Override
	public void addStarRecommendMsg(final Integer channelStarId) throws Exception {
		OpChannelMemberDTO starDto = getChannelStarById(channelStarId);
		if ( starDto == null ) {
			throw new HTSException("记录已经被删除");
		}

		// 当通知状态不为空，且为未通知时，执行添加推送通知信息的操作，然后进行push给用户
		if (starDto.getNotified() != null && starDto.getNotified().equals(Tag.FALSE)) {
			updateChannelStarNotified(channelStarId, Tag.TRUE);
			msgService.sendChannelSystemNotice(starDto.getUserId(), Admin.NOTICE_CHANNELMEMBER_TO_STAR, starDto.getChannelId(), null);
		}
	}
	
	@Override
	public void updateChannelStarNotified(Integer channelStarId, Integer notified) {
		OpChannelMemberDTO dto = new OpChannelMemberDTO();
		dto.setChannelMemberId(channelStarId);
		dto.setNotified( notified == null ? Tag.FALSE : Tag.TRUE );
		channelMemberMapper.updateChannelStar(dto);
	}

	@Override
	public void sortChannelStarsSchedule(Integer[] csIds, Date scheduleDate) {
		/*
		 *  反向排序，传递过来的集合，第一个是前台想排在前面的
		 *  下面是依次插入数据库，插入越晚，id越大，而id越大的，排序时就会越新，即serial越大，越靠前
		 */
		for ( int i = csIds.length - 1; i >= 0; i--) {
			OpChannelMemberDTO dto = getChannelStarById(csIds[i]);
			Integer operatorId = AdminLoginUtil.getCurrentLoginId();
			channelMemberMapper.insertChannelStarSchedule(dto, scheduleDate, operatorId);
		}
	}

	@Override
	public void setChannelStarTop(Integer channelStarId) {
		
		// 得到频道红人最新排序的sequence序号
		Integer serial = keyGenService.generateId(com.hts.web.common.service.impl.KeyGenServiceImpl.OP_CHANNEL_STAR_SERIAL);
		
		// 根据频道红人主键id查询红人
		OpChannelMemberDTO existStarDto = getChannelStarById(channelStarId);
		
		// 若查询结果不存在，即设置置顶的不为红人，则先设置为红人，然后进行重新排序
		if ( existStarDto == null ) {
			// 先从频道成员表中查询出主键id对应的成员，然后把成员插入频道红人表
			OpChannelMemberDTO dto = getChannelMemberById(channelStarId);
			dto.setSerial(serial);
			channelMemberMapper.insertChannelStar(dto);
			
			
			
			// 修改频道成员的是否红人的标记位，设置成红人
			dto.setChannelStar(Tag.TRUE);
			channelMemberMapper.updateChannelMember(dto);
			
		} else {
			// 更新频道红人的serial，设置为最新的，达到置顶的效果
			existStarDto.setSerial(serial);
			channelMemberMapper.updateChannelStar(existStarDto);
		}
		
	}
	
	/**
	 * 通过频道成员主键id，查询出频道成员对象（不携带成员相关联的user信息）
	 * 
	 * 此方法为私有，若有需要可以上升到接口中，供外部调用
	 * 
	 * @param channelMemberId	频道成员主键id
	 * @return
	 * @author zhangbo	2015年8月20日
	 */
	private OpChannelMemberDTO getChannelMemberById(Integer channelMemberId) {
		OpChannelMemberDTO dto = new OpChannelMemberDTO();
		dto.setChannelMemberId(channelMemberId);
		return channelMemberMapper.getChannelMember(dto);
	}
	
	/**
	 * 通过频道红人主键id，查询出频道红人对象（不携带红人相关联的user信息）
	 * 
	 * 此方法为私有，若有需要可以上升到接口中，供外部调用
	 * 
	 * @param channelStarId	频道红人主键id
	 * @return
	 * @author zhangbo	2015年8月20日
	 */
	private OpChannelMemberDTO getChannelStarById(Integer channelStarId) {
		OpChannelMemberDTO dto = new OpChannelMemberDTO();
		dto.setChannelMemberId(channelStarId);
		return channelMemberMapper.getChannelStar(dto);
	}

	@Override
	public OpChannelMemberDTO getChannelStarByChannelIdAndUserId(Integer channelId, Integer userId) {
		OpChannelMemberDTO dto = new OpChannelMemberDTO();
		dto.setChannelId(channelId);
		dto.setUserId(userId);
		return channelMemberMapper.getChannelStar(dto);
	}
	
	/**
	 * 频道红人排序调度方法，此方法配置的是20分钟执行一次
	 * 以当前时间，查询前1小时数据，数量不多
	 * 
	 * @author zhangbo	2015年8月20日
	 */
	public void doSortChannelStarSchedula() {
		Date endTime = new Date();
		Date startTime = new Date(endTime.getTime() - channelStarSchedulaTimeSpan);
		
		// 查询1小时之内未完成的频道红人排序计划
		List<OpChannelMemberDTO> dtoList = channelMemberMapper.queryChannelStarSchedule(startTime, endTime, Tag.FALSE);
		
		if ( dtoList != null ) {
			// 对得到的结果进行处理， 依照顺序处理即可，因为查询结果已经保障了刷新serial的顺序
			for (OpChannelMemberDTO opChannelMemberDTO : dtoList) {
				
				// 把频道红人排序计划设置为已完成
				channelMemberMapper.updateChannelStarSchedule(opChannelMemberDTO, Tag.TRUE);
				
				// 得到频道红人最新排序的sequence序号，进而执行刷新
				Integer serial = keyGenService.generateId(com.hts.web.common.service.impl.KeyGenServiceImpl.OP_CHANNEL_STAR_SERIAL);
				opChannelMemberDTO.setSerial(serial);
				channelMemberMapper.updateChannelStar(opChannelMemberDTO);
			}
		}
	}

}

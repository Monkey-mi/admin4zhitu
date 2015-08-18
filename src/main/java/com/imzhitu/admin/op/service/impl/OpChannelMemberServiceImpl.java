package com.imzhitu.admin.op.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.HTSException;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.PushUtil;
import com.hts.web.common.util.UserInfoUtil;
import com.hts.web.push.service.impl.PushServiceImpl.PushFailedCallback;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.OpChannel;
import com.imzhitu.admin.common.pojo.OpChannelMemberDto;
import com.imzhitu.admin.op.mapper.ChannelMapper;
import com.imzhitu.admin.op.mapper.OpChannelMemberMapper;
import com.imzhitu.admin.op.service.OpChannelMemberService;

/**
 * 频道成员相关操作的业务实现类
 * 包括对频道成员的CRUD，与频道红人的CRUD及消息推送等操作
 * 
 * @author zhangbo	2015年8月18日
 *
 */
@Service
public class OpChannelMemberServiceImpl extends BaseServiceImpl implements OpChannelMemberService {
	
	/**
	 * 频道红人推荐通知头信息
	 * 
	 * @author zhangbo	2015年8月18日
	 */
	private static final String CHANNEL_STAR_MSG_HEAD = "，恭喜！你被推荐为";
	
	/**
	 * 频道红人推荐通知尾 信息
	 * 
	 * @author zhangbo	2015年8月18日
	 */
	private static final String CHANNEL_STAR_MSG_FOOT =  "频道的红人啦！继续发光发亮哟，么么哒！";

	/**
	 * 频道成员相关数据接口
	 * 
	 * @author zhangbo	2015年8月18日
	 */
	@Autowired
	private OpChannelMemberMapper channelMemberMapper;
	
	/**
	 * 频道相关数据接口
	 * 
	 * @author zhangbo	2015年8月18日
	 */
	@Autowired
	private ChannelMapper channelMapper;
	
	/**
	 * web端用户信息数据接口
	 * 
	 * @author zhangbo	2015年8月18日
	 */
	@Autowired
	private com.hts.web.userinfo.dao.UserInfoDao webUserInfoDao;
	
	/**
	 * web端用户消息业务逻辑访问接口
	 * 
	 * @author zhangbo	2015年8月18日
	 */
	@Autowired
	private com.hts.web.userinfo.service.UserMsgService webUserMsgService;
	
	/**
	 * 消息推送服务接口
	 * 
	 * @author zhangbo	2015年8月18日
	 */
	@Autowired
	private com.hts.web.push.service.PushService pushService;

	@Override
	public void updateChannelMemberDegree(Integer channelId, Integer userId, Integer degree) throws Exception {
		if (channelId == null || userId == null) {
			return;
		}
		OpChannelMemberDto dto = new OpChannelMemberDto();
		dto.setChannelId(channelId);
		dto.setUserId(userId);
		dto.setDegree(degree);
		channelMemberMapper.updateChannelMember(dto);

	}

	@Override
	public void buildChannelMemberList(Integer channelId, Integer userId, String userName, Integer userStarId, Integer notified, Integer shield, Integer maxId, int page, int rows, Map<String, Object> jsonMap) throws Exception {
		OpChannelMemberDto dto = new OpChannelMemberDto();
		dto.setChannelId(channelId);
		dto.setUserId(userId);
		dto.setUserName(userName);
		dto.setStar(userStarId);
		dto.setNotified(notified);
		dto.setShield(shield);

		buildNumberDtos(dto, page, rows, jsonMap, new NumberDtoListAdapter<OpChannelMemberDto>() {
			@Override
			public long queryTotal(OpChannelMemberDto dto) {
				return channelMemberMapper.queryChannelMemberTotalCount(dto);
			}

			@Override
			public List<? extends AbstractNumberDto> queryList(OpChannelMemberDto dto) {
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
	public void saveChannelStar(Integer channelMemberId) {
		OpChannelMemberDto memberDto = channelMemberMapper.queryChannelMemberById(channelMemberId);
		
		// 若不是红人，则插入到频道红人表中
		if ( !memberDto.isChannelStar() ) {
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
	}

	@Override
	public void addStarRecommendMsg(final Integer channelStarId) throws Exception {
		OpChannelMemberDto starDto = channelMemberMapper.queryChannelStarById(channelStarId);
		if ( starDto == null ) {
			throw new HTSException("记录已经被删除");
		}

		// 当通知状态不为空，且为未通知时，执行添加推送通知信息的操作，然后进行push给用户
		if (starDto.getNotified() != null && starDto.getNotified().equals(Tag.FALSE)) {
			
			// 获取频道对象
			OpChannel channel = channelMapper.queryChannelById(starDto.getChannelId());
			String channelName = channel.getChannelName();	// 获取频道名称
			Integer recipientId = starDto.getUserId();		// 获取接收推荐通知的用户id，及被通知的频道红人userId
			Integer channelId = starDto.getChannelId();		// 获取频道id
			String recipientName = webUserInfoDao.queryUserNameById(recipientId);	// 获取被推荐的频道红人名称
			
			// 获取用户推送信息对象，并取得用户客户端系统版本代号
			UserPushInfo userPushInfo = webUserInfoDao.queryUserPushInfoById(recipientId);	
			Integer msgCode = UserInfoUtil.getSysMsgCode(userPushInfo.getVer(), Tag.USER_MSG_CHANNEL_STAR);

			// 生成推送消息，主消息，短消息
			String msg = CHANNEL_STAR_MSG_HEAD + channelName + CHANNEL_STAR_MSG_FOOT;
			String tip = recipientName + msg;
			String shortTip = PushUtil.getShortName(recipientName) + PushUtil.getShortTip(msg);

			// 保存消息
			webUserMsgService.saveSysMsg(Admin.ZHITU_UID, recipientId, tip, msgCode, recipientId, channelName, String.valueOf(channelId), null, 0);

			// 更新通知标记，设置成已经通知，及推送了信息
			updateChannelStarNotified(channelStarId, Tag.TRUE);

			// 推送消息
			pushService.pushSysMessage(shortTip, OpServiceImpl.ZHITU_UID, tip, userPushInfo, msgCode, new PushFailedCallback() {

				@Override
				public void onPushFailed(Exception e) {
					// 若推送消息失败，则要执行更新通知标记，设置成未推送信息
					updateChannelStarNotified(channelStarId, 0);
				}
			});
		}
	}
	
	/**
	 * 更新频道红人的通知状态
	 * FIXME 这个要是有需要的话，可以放开出来，升级到接口中，供外部调用，要分析一下 
	 * 
	 * @param channelStarId	频道红人表主键id
	 * @param notified 		通知状态
	 * @author zhangbo	2015年8月18日
	 */
	private void updateChannelStarNotified(Integer channelStarId, Integer notified) {
		OpChannelMemberDto dto = new OpChannelMemberDto();
		dto.setChannelMemberId(channelStarId);
		dto.setNotified( notified == null ? Tag.FALSE : Tag.TRUE );
		channelMemberMapper.updateChannelStar(dto);
	}

	@Override
	public void serialChannelStars(Integer[] channelStarIds) {
		for (Integer channelStarId : channelStarIds) {
			OpChannelMemberDto dto = new OpChannelMemberDto();
			dto.setChannelMemberId(channelStarId);
			// FIXME 合并以后调用的天杰提供的接口
			dto.setSerial(0);
			channelMemberMapper.updateChannelStar(dto);
		}
	}

}

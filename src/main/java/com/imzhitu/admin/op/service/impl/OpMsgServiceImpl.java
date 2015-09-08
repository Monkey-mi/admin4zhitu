package com.imzhitu.admin.op.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.HTSException;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.HTWorldDto;
import com.hts.web.common.pojo.OpNotice;
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.hts.web.push.service.impl.PushServiceImpl.PushFailedCallback;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.NoticeMsgTemplate;
import com.imzhitu.admin.common.pojo.OpChannel;
import com.imzhitu.admin.common.pojo.OpChannelMemberDTO;
import com.imzhitu.admin.common.pojo.OpSysMsg;
import com.imzhitu.admin.common.util.AdminLoginUtil;
import com.imzhitu.admin.constant.LoggerKeies;
import com.imzhitu.admin.op.dao.NoticeCacheDao;
import com.imzhitu.admin.op.mapper.ChannelMapper;
import com.imzhitu.admin.op.mapper.NoticeMsgTemplateMapper;
import com.imzhitu.admin.op.mapper.OpChannelNoticeMapper;
import com.imzhitu.admin.op.mapper.SysMsgMapper;
import com.imzhitu.admin.op.service.OpChannelMemberService;
import com.imzhitu.admin.op.service.OpMsgService;
import com.imzhitu.admin.userinfo.mapper.UserInfoMapper;

@Service
public class OpMsgServiceImpl extends BaseServiceImpl implements OpMsgService {

	private static Logger appPushLogger = Logger.getLogger(LoggerKeies.PUSH_APP);

	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;

	@Autowired
	private NoticeCacheDao noticeCacheDao;

	@Autowired
	private SysMsgMapper sysMsgMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private com.hts.web.push.service.PushService pushService;
	
	@Autowired
	private com.hts.web.ztworld.dao.HTWorldDao webWorldDao;
	
	@Autowired
	private ChannelMapper channelMapper;
	
	@Autowired
	private com.hts.web.userinfo.dao.UserInfoDao webUserInfoDao;
	
	@Autowired
	private com.hts.web.userinfo.service.UserMsgService webUserMsgService;
	
	@Autowired
	private NoticeMsgTemplateMapper noticeTmplMapper;
	
	@Autowired
	private OpChannelNoticeMapper channelNoticeMapper;
	
	@Autowired
	private OpChannelMemberService channelMemberService;
	

	/**
	 * 系统推送通知发送人ID，即官方账号ID
	 * 
	 * @author zhangbo 2015年9月2日
	 */
	private Integer appMsgSenderId = Admin.ZHITU_UID;

	/**
	 * app推送限定条数
	 */
	private Integer appPushLimit = 300;

	private Integer appPushGroup = 300000;
	
	/**
	 * 频道通知模板中用户名的代替符
	 * @author zhangbo	2015年9月8日
	 */
	private final String notice_userName_flag = "@{userName}";
	
	/**
	 * 频道通知模板中频道名的代替符
	 * @author zhangbo	2015年9月8日
	 */
	private final String notice_channelName_flag = "@{channelName}";
	
	@Override
	public void updateNotice(Integer id, String path, String link, Integer phoneCode) {
		noticeCacheDao.saveNotice(new OpNotice(id, path, link, phoneCode));
	}

	@Override
	public void buildNotice(Map<String, Object> jsonMap) throws Exception {
		List<OpNotice> list = noticeCacheDao.queryNotice();
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
		jsonMap.put(OptResult.JSON_KEY_TOTAL, list.size());
	}

	@Override
	public OpNotice getNotice(Integer phoneCode) throws Exception {
		return noticeCacheDao.queryNotice(phoneCode);
	}

	@Override
	public void deleteNotice(String phoneCodesStr) throws Exception {
		Integer[] phoneCodes = StringUtil.convertStringToIds(phoneCodesStr);
		noticeCacheDao.deleteNotice(phoneCodes);
	}

	@Override
	public void saveNotice(String path, String link, Integer phoneCode) {
		Integer id = webKeyGenService.generateId(KeyGenServiceImpl.OP_NOTICE_ID);
		noticeCacheDao.saveNotice(new OpNotice(id, path, link, phoneCode));
	}

	@Override
	public void pushAppMsg(final OpSysMsg msg, Boolean inApp, Boolean noticed, Integer uid) throws Exception {

		Integer pushAction = Tag.PUSH_ACTION_SYS;

		if (msg.getObjId() == null || msg.getObjId() == 0) {
			Integer objId = webKeyGenService.generateId(KeyGenServiceImpl.OP_SYS_MSG_ID);
			msg.setObjId(objId);
		}
		msg.setSenderId(appMsgSenderId);
		msg.setMsgDate(new Date());

		String sid = null;
		if (msg.getObjType().equals(Tag.USER_MSG_STAR_RECOMMEND)) {
			pushAction = Tag.PUSH_ACTION_USER_REC_MSG;
			sid = String.valueOf(msg.getObjId());
		}

		if (uid != null && uid != 0) {
			msg.setRecipientId(uid);
			sysMsgMapper.saveMsg(msg);
			List<Integer> ulist = new ArrayList<Integer>();
			ulist.add(uid);
			pushService.pushBulletin(pushAction, msg.getContent(), sid, ulist);
			return;
		}

		// 向数据库插入消息
		if (inApp) {
			sysMsgMapper.saveMsgByProcedure(msg);
		}

		// 向所有人推送消息
		if (noticed) {
			int factor = appPushGroup;
			int maxUID = userInfoMapper.selectMaxId();
			int parts = 0;
			if (maxUID % factor == 0) {
				parts = maxUID / factor;
			} else {
				parts = maxUID / factor + 1;
			}
			for (int i = 0; i < parts; i++) {
				int maxId = 0;
				int minId = 0;
				if (i == 0) {
					maxId = -1;
				} else {
					maxId = maxUID;
				}
				if (i == (parts - 1)) {
					minId = 1;
				} else {
					maxUID -= factor;
					minId = maxUID;
				}
				final int maxId4Thread = maxId;
				final int minId4Thread = minId;
				final String sidThread = sid;
				final int pushActionThread = pushAction;
				pushService.getPushExecutor().execute(new Runnable() {

					@Override
					public void run() {
						batchPushAppMsg(pushActionThread, msg.getContent(), sidThread, minId4Thread, maxId4Thread);
					}
				});
			}
		}
	}

	public void batchPushAppMsg(Integer pushAction, String msg, String sid, Integer minId, Integer maxId) {
		List<Integer> uids = null;
		if (maxId == -1) {
			uids = userInfoMapper.queryUID(minId, null, appPushLimit); // 第一次推送
		} else if (maxId != 0) {
			uids = userInfoMapper.queryUID(minId, maxId, appPushLimit); // 递归推送
		}

		if (uids != null && uids.size() > 0) {
			int startId = uids.get(0);
			int endId = uids.get(uids.size() - 1);
			try {
				pushService.pushBulletin(pushAction, msg, sid, uids);
			} catch (Exception e) {
				appPushLogger.warn("push app msg error : " + e.getMessage());
			}
			appPushLogger.info("push app msg from " + startId + "-" + endId);
			batchPushAppMsg(pushAction, msg, sid, minId, endId);
		}
	}

	@Override
	public void saveChannelNoticeTemplate(Integer channelId, String contentTmpl, String channelNoticeType) throws Exception {
		if ( !(contentTmpl.contains(notice_userName_flag) && contentTmpl.contains(notice_channelName_flag)) ) {
			throw new HTSException("保存的通知信息模板不正确，请检查！");
		}
		// 定义通知信息模板对象
		NoticeMsgTemplate nmt = new NoticeMsgTemplate();
		nmt.setContentTmpl(contentTmpl);
		nmt.setOperator(AdminLoginUtil.getCurrentLoginId());
		Integer tmplId = noticeTmplMapper.getContentTmplId(nmt);
		// 若无此模板，则先进行保存
		if ( tmplId == null ) {
			noticeTmplMapper.insertNoticeMsgTemplate(nmt);
			tmplId = nmt.getId();
		}
		saveChannelNotice(channelId, channelNoticeType, tmplId);
	}
	
	/**
	 * 保存频道通知
	 * 
	 * @param channelId
	 *            频道id
	 * @param channelNoticeType
	 *            频道通知类型
	 * @param tmplId
	 *            通知信息模板表主键id
	 * @author zhangbo 2015年9月6日
	 */
	private void saveChannelNotice(Integer channelId, String channelNoticeType, Integer tmplId) {
		Integer noticeMsgTpmlId = channelNoticeMapper.getNoticeTpmlIdByType(channelId, channelNoticeType);
		// 根据频道id与类型查询通知模板id，若不存在，则执行插入，若存在则更新
		if ( noticeMsgTpmlId == null ) {
			channelNoticeMapper.insertChannelNotice(channelId, channelNoticeType, tmplId);
		} else {
			channelNoticeMapper.updateChannelNotice(channelId, channelNoticeType, tmplId);
		}
	}
	
	@Override
	public String getChannelNoticeTemplateByType(Integer channelId, String channelNoticeType) throws Exception {
		// 获取通知消息模板表id
		Integer tmplId = channelNoticeMapper.getNoticeTpmlIdByType(channelId, channelNoticeType);
		return noticeTmplMapper.getContentTmplById(tmplId);
	}
	
	@Override
	public void sendChannelSystemNotice(Integer receiverId, String sendType, Integer channelId, Integer worldId) throws Exception {

		// 根据发送频道不同通知类型调用不同方法
		if ( Admin.NOTICE_WORLD_INTO_CHANNEL.equals(sendType) ) {
			sendWorldIntoChannelNotice(receiverId, channelId, worldId);
		} else if ( Admin.NOTICE_CHANNELWORLD_TO_SUPERB.equals(sendType) ) {
			sendChannelWorldToSuperbNotice(receiverId, channelId, worldId);
		} else if ( Admin.NOTICE_CHANNELMEMBER_TO_STAR.equals(sendType) ) {
			sendChannelMemberToStarNotice(receiverId, channelId);
		}
	}

	/**
	 * 发送织图被选入频道的通知
	 * 
	 * @param receiverId
	 * @param channelId
	 * @author zhangbo 2015年9月2日
	 * @throws Exception 
	 */
	private void sendWorldIntoChannelNotice(Integer receiverId, Integer channelId, Integer worldId) throws Exception {
		// 获取通知信息模板
		String contentTpml = getChannelNoticeTemplateByType(channelId, Admin.NOTICE_WORLD_INTO_CHANNEL);
		if ( contentTpml == null || "".equals(contentTpml)  ) {
			throw new HTSException("此频道未配置织图被选入频道中的通知信息模板！请在<频道通知配置>功能中进行配置。");
		}
		
		// 根据织图id获取织图相关信息
		HTWorldDto worldDto = webWorldDao.queryHTWorldDtoById(worldId);
		
		// 获取频道对象信息
		OpChannel channel = channelMapper.queryChannelById(channelId);
		
		// 获取用户推送信息对象
		UserPushInfo userPushInfo = webUserInfoDao.queryUserPushInfoById(receiverId);
		
		// 要推送到客户端的最终信息
		String tip = replaceMsgFlag(contentTpml, userPushInfo.getUserName(), channel.getChannelName());
		
		// 保存消息
		webUserMsgService.saveSysMsg(Admin.ZHITU_UID, receiverId, tip, Tag.USER_MSG_CHANNEL_WORLD, worldId,
				channel.getChannelName(), String.valueOf(channelId), worldDto.getTitleThumbPath(), 0);
		
		// 推送消息
		pushService.pushSysMessage(tip, Admin.ZHITU_UID, tip, userPushInfo, Tag.USER_MSG_CHANNEL_WORLD, new PushFailedCallback() {

			@Override
			public void onPushFailed(Exception e) {
				// XXX 处理织图选入频道通知的问题，应该要更新通知标志
			}
		});
	}

	/**
	 * 发送频道织图被选为精选的通知
	 * 
	 * @param receiverId
	 * @param channelId
	 * @author zhangbo 2015年9月2日
	 * @throws Exception
	 */
	private void sendChannelWorldToSuperbNotice(Integer receiverId, Integer channelId, Integer worldId) throws Exception {
		// 获取通知信息模板
		String contentTpml = getChannelNoticeTemplateByType(channelId, Admin.NOTICE_CHANNELWORLD_TO_SUPERB);
		if ( contentTpml == null || "".equals(contentTpml) ) {
			throw new HTSException("此频道未配置频道织图被选为精选的通知信息模板！请在<频道通知配置>功能中进行配置。");
		}

		// 根据织图id获取织图相关信息
		HTWorldDto worldDto = webWorldDao.queryHTWorldDtoById(worldId);
		
		// 获取频道对象信息
		OpChannel channel = channelMapper.queryChannelById(channelId);
		
		// 获取用户推送信息对象
		UserPushInfo userPushInfo = webUserInfoDao.queryUserPushInfoById(receiverId);
		
		// 要推送到客户端的最终信息 
		String tip = replaceMsgFlag(contentTpml, userPushInfo.getUserName(), channel.getChannelName());
		
		// 保存消息
		webUserMsgService.saveSysMsg(Admin.ZHITU_UID, receiverId, tip, Tag.USER_MSG_CHANNEL_SUPERB, worldId,
				channel.getChannelName(), String.valueOf(channelId), worldDto.getTitleThumbPath(), 0);
		
		// 推送消息
		pushService.pushSysMessage(tip, Admin.ZHITU_UID, tip, userPushInfo, Tag.USER_MSG_CHANNEL_SUPERB, new PushFailedCallback() {

			@Override
			public void onPushFailed(Exception e) {
				// XXX 处理织图选入频道通知的问题，应该要更新通知标志
			}
		});
	}

	/**
	 * 发送频道成员被选为红人的通知
	 * 
	 * @param receiverId
	 * @param channelId
	 * @author zhangbo 2015年9月2日
	 * @throws Exception
	 */
	private void sendChannelMemberToStarNotice(final Integer receiverId, final Integer channelId) throws Exception {
		// 获取通知信息模板
		String contentTpml = getChannelNoticeTemplateByType(channelId, Admin.NOTICE_CHANNELMEMBER_TO_STAR);
		if ( contentTpml == null || "".equals(contentTpml)  ) {
			throw new HTSException("此频道未配置频道成员被选为红人的通知信息模板！请在<频道通知配置>功能中进行配置。");
		}
		
		// 获取频道对象信息
		OpChannel channel = channelMapper.queryChannelById(channelId);
		
		// 获取用户推送信息对象
		UserPushInfo userPushInfo = webUserInfoDao.queryUserPushInfoById(receiverId);
		
		// 要推送到客户端的最终信息 
		String tip = replaceMsgFlag(contentTpml, userPushInfo.getUserName(), channel.getChannelName());
		
		// 保存消息，保存的内容为频道红人的通知，故objId与thumbPath可以传递为null
		webUserMsgService.saveSysMsg(Admin.ZHITU_UID, receiverId, tip, Tag.USER_MSG_CHANNEL_STAR, null, channel.getChannelName(), String.valueOf(channelId), null, 0);
		
		// 推送消息
		pushService.pushSysMessage(tip, Admin.ZHITU_UID, tip, userPushInfo, Tag.USER_MSG_CHANNEL_STAR, new PushFailedCallback() {

			@Override
			public void onPushFailed(Exception e) {
				// 若操作失败，则把频道红人的通知状态改未通知
				OpChannelMemberDTO channelStar = channelMemberService.getChannelStarByChannelIdAndUserId(channelId, receiverId);
				channelMemberService.updateChannelStarNotified(channelStar.getChannelMemberId(), Tag.FALSE);
			}
		});
	}
	
	/**
	 * 替换频道通知信息模板中接收人和频道名称，返回推送到APP端的通知信息 
	 * 
	 * @param msg			通知信息模板内容
	 * @param userName		要替换的接收人名称
	 * @param channelName	要替换的频道名称
	 * @return
	 * @throws Exception
	 * @author zhangbo	2015年9月6日
	 */
	private String replaceMsgFlag(String msg, String userName, String channelName) throws Exception {
		if ( msg.contains(notice_userName_flag) && msg.contains(notice_channelName_flag) ) {
			msg = msg.replace(notice_userName_flag, userName);
			msg = msg.replace(notice_channelName_flag, channelName);
		} else {
			throw new HTSException("发送的通知信息模板不正确，请检查！ 相关信息：username：" + userName + " 频道名称： " + channelName);
		}
		return msg;
	}

}

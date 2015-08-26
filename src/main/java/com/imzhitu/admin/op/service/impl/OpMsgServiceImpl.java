package com.imzhitu.admin.op.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.OpNotice;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.OpSysMsg;
import com.imzhitu.admin.constant.LoggerKeies;
import com.imzhitu.admin.op.dao.NoticeCacheDao;
import com.imzhitu.admin.op.mapper.SysMsgMapper;
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
	
	@Value("${push.appMsgSenderId}")
	private Integer appMsgSenderId = 2063;
	
	/**
	 * app推送限定条数
	 */
	private Integer appPushLimit = 300;
	
	private Integer appPushGroup = 300000;
	
	public Integer getAppMsgSenderId() {
		return appMsgSenderId;
	}

	public void setAppMsgSenderId(Integer appMsgSenderId) {
		this.appMsgSenderId = appMsgSenderId;
	}
	

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
		Integer id = webKeyGenService
				.generateId(KeyGenServiceImpl.OP_NOTICE_ID);
		noticeCacheDao.saveNotice(new OpNotice(id, path, link, phoneCode));
	}

	@Override
	public void pushAppMsg(final OpSysMsg msg, Boolean inApp, Boolean noticed, Integer uid)
			throws Exception {
		
		Integer pushAction = Tag.PUSH_ACTION_SYS;
		
		if(msg.getObjId() == null || msg.getObjId() == 0) {
			Integer objId = webKeyGenService.generateId(KeyGenServiceImpl.OP_SYS_MSG_ID);
			msg.setObjId(objId);
		}
		msg.setSenderId(appMsgSenderId);
		msg.setMsgDate(new Date());
		
		String sid = null;
		if(msg.getObjType().equals(Tag.USER_MSG_STAR_RECOMMEND)) {
			pushAction = Tag.PUSH_ACTION_USER_REC_MSG;
			sid = String.valueOf(msg.getObjId());
		}
		
		if(uid != null && uid != 0) {
			msg.setRecipientId(uid);
			sysMsgMapper.saveMsg(msg);
			List<Integer> ulist = new ArrayList<Integer>();
			ulist.add(uid);
			pushService.pushBulletin(pushAction, msg.getContent(), sid, ulist);
			return;
		}
		
		// 向数据库插入消息
		if(inApp) {
			sysMsgMapper.saveMsgByProcedure(msg);
		}

		// 向所有人推送消息
		if(noticed) {
			int factor = appPushGroup;
			int maxUID = userInfoMapper.selectMaxId();
			int parts = 0;
			if(maxUID % factor == 0) {
				parts = maxUID / factor;
			} else {
				parts = maxUID / factor + 1;
			}
			for(int i = 0; i < parts; i++) {
				int maxId = 0;
				int minId = 0;
				if(i == 0) {
					maxId = -1;
				} else {
					maxId = maxUID;
				}
				if(i == (parts - 1)) {
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
		if(maxId == -1) {
			uids = userInfoMapper.queryUID(minId, null,appPushLimit); // 第一次推送
		} else if(maxId != 0){
			uids = userInfoMapper.queryUID(minId, maxId, appPushLimit); // 递归推送
		}
		
		if(uids != null && uids.size() > 0) {
			int startId = uids.get(0);
			int endId = uids.get(uids.size() - 1);
			try { 
				pushService.pushBulletin(pushAction, msg, sid, uids);
			} catch(Exception e) {
				appPushLogger.warn("push app msg error : " + e.getMessage());
			}
			appPushLogger.info("push app msg from " + startId + "-" + endId);
			batchPushAppMsg(pushAction, msg, sid, minId, endId);
		}
	}

}

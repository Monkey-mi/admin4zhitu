package com.imzhitu.admin.op.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
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
	
	@Autowired
	private com.hts.web.operations.dao.StartPageCacheDao webStartPageCacheDao;
	
	@Value("${push.appMsgSenderId}")
	private Integer appMsgSenderId = 2063;
	
	/**
	 * app推送限定条数
	 */
	private Integer appPushLimit = 300;
	
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
	public void pushAppMsg(OpSysMsg msg, Boolean inApp, Boolean noticed)
			throws Exception {
		if(msg.getObjId() == null || msg.getObjId() == 0) {
			Integer objId = webKeyGenService.generateId(KeyGenServiceImpl.OP_SYS_MSG_ID);
			msg.setObjId(objId);
		}
		msg.setSenderId(appMsgSenderId);
		msg.setMsgDate(new Date());
		
		
		// 向数据库插入消息
		if(inApp) {
			sysMsgMapper.saveMsgByProcedure(msg);
		}

		long pushStartTime = System.currentTimeMillis();
		// 向所有人推送消息
		if(noticed) {
			batchPushAppMsg(msg.getContent(), -1);
		}
		long pushEndTime = System.currentTimeMillis();
		appPushLogger.info("finish push app msg, cost " + (pushEndTime - pushStartTime) / 1000 + " seconds"); 

	}
	
	public void batchPushAppMsg(String msg, Integer maxId) {
		List<Integer> uids = null;
		if(maxId == -1) {
			uids = userInfoMapper.queryUID(appPushLimit); // 第一次推送
		} else if(maxId != 0){
			uids = userInfoMapper.queryUIDByMaxId(maxId, appPushLimit); // 递归推送
		}
		
		if(uids != null && uids.size() > 0) {
			int startId = uids.get(0);
			int endId = uids.get(uids.size() - 1);
			try { 
				pushService.pushBulletin(msg, uids);
			} catch(Exception e) {
				appPushLogger.warn("push app msg error : " + e.getMessage());
			}
			appPushLogger.info("push app msg from " + startId + "-" + endId);
			batchPushAppMsg(msg, endId);
		}
	}

}

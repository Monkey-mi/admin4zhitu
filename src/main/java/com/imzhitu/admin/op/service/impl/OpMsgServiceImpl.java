package com.imzhitu.admin.op.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.pojo.OpNotice;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.OpSysMsg;
import com.imzhitu.admin.op.dao.NoticeCacheDao;
import com.imzhitu.admin.op.mapper.SysMsgMapper;
import com.imzhitu.admin.op.service.OpMsgService;

@Service
public class OpMsgServiceImpl extends BaseServiceImpl implements OpMsgService {

	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private NoticeCacheDao noticeCacheDao;
	
	@Autowired
	private SysMsgMapper sysMsgMapper;

	@Autowired
	private com.hts.web.push.service.PushService pushService;
	
	@Autowired
	private com.hts.web.operations.dao.StartPageCacheDao webStartPageCacheDao;
	
	@Value("${push.appMsgSenderId}")
	private Integer appMsgSenderId = 2063;
	
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
		Integer objId = webKeyGenService.generateId(KeyGenServiceImpl.OP_SYS_MSG_ID);
		msg.setSenderId(appMsgSenderId);
		msg.setMsgDate(new Date());
		msg.setObjId(objId);
//		if(noticed) {
//			pushService.pushAppMessage(msg.getContent());
//			if(msg.getObjMeta() == null) {
//				throw new HTSException("please input link");
//			}
//		}
		
		if(inApp) {
			sysMsgMapper.saveMsgByProcedure(msg);
		}
	}

	@Override
	public void updateStartPageCache(String linkPath, Integer linkType, String link,
			Date beginDate, Date endDate, Integer showCount) throws Exception {
		Integer id = webKeyGenService.generateId(KeyGenServiceImpl.OP_MSG_START_PAGE_ID);
		com.hts.web.common.pojo.OpMsgStartPage wpage =
				new com.hts.web.common.pojo.OpMsgStartPage();
		wpage.setId(id);
		wpage.setLinkPath(linkPath);
		wpage.setLinkType(linkType);
		wpage.setLink(link);
		wpage.setBeginDate(beginDate);
		wpage.setEndDate(endDate);
		wpage.setShowCount(showCount);
		webStartPageCacheDao.updateStartPage(wpage);
		
	}
}

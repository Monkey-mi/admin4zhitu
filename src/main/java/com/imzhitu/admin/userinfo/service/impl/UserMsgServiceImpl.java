package com.imzhitu.admin.userinfo.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.UserMsgDto;
import com.hts.web.common.pojo.UserMsgRecipientDto;
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.PushUtil;
import com.hts.web.common.util.StringUtil;
import com.hts.web.push.service.PushService;
import com.hts.web.push.service.impl.PushServiceImpl.PushFailedCallback;
import com.imzhitu.admin.op.service.impl.OpServiceImpl;
import com.imzhitu.admin.userinfo.dao.UserMsgDao;
import com.imzhitu.admin.userinfo.service.UserMsgService;

/**
 * <p>
 * 用户消息业务逻辑访问对象
 * </p>
 * 
 * 创建时间：2014-4-29
 * 
 * @author tianjie
 * 
 */
@Service
public class UserMsgServiceImpl extends BaseServiceImpl implements
		UserMsgService {

	public static final String MSG_TITLE = "亲爱的";
	
	@Autowired
	private PushService pushService;
	
	@Autowired
	private com.hts.web.userinfo.dao.UserInfoDao webUserInfoDao;
	
	@Autowired
	private com.hts.web.userinfo.service.UserMsgService webUserMsgService;
	
	@Autowired
	private com.hts.web.userinfo.dao.UserMsgDao webUserMsgDao;
	
	@Autowired
	private com.hts.web.userinfo.dao.UserMsgRecipientBoxDao webUserMsgRecipientBoxDao;
	
	@Autowired
	private UserMsgDao userMsgDao;
	
	

	@Override
	public void pushSysMsg(String idsStr, String content) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		List<UserPushInfo> infos = webUserInfoDao.queryUserPushInfoByIds(ids);
		for (UserPushInfo info : infos) {
			String userName = webUserInfoDao.queryUserNameById(info.getId());
			String fullContent = MSG_TITLE + userName + "，" + content;
			userName = PushUtil.getShortName(userName);
			String title = MSG_TITLE + userName + "，" + content;
			webUserMsgService.saveSysMsg(OpServiceImpl.ZHITU_UID, info.getId(), fullContent, Tag.USER_MSG_SYS, null, null, null, null, 0);
			pushService.pushSysMessage(title, OpServiceImpl.ZHITU_UID, title,
					info, Tag.USER_MSG_SYS, new PushFailedCallback() {

						@Override
						public void onPushFailed(Exception e) {
						}
					});
		}
	}

	@Override
	public void pushAppMsg(String content, Integer worldId,
			String activityName, String[] phoneTypes) throws Exception {
		List<String> phoneTypeList = new ArrayList<String>();
		for(String phoneType : phoneTypes) {
			phoneTypeList.add(phoneType);
		}
		Integer pushAction = Tag.PUSH_ACTION_SYS;
		String pushId = null;
		if(worldId != null) {
			pushAction = Tag.PUSH_ACTION_WORLD_MSG;
			pushId = worldId.toString();
		} else if(!StringUtil.checkIsNULL(activityName)) {
			pushAction = Tag.PUSH_ACTION_ACTIVITY_MSG;
			pushId = activityName;
		}
//		pushService.pushAppMessage(content, pushAction, pushId, phoneTypeList);
	}

	@Override
	public void pushListMsg(String content, Integer worldId,
			String activityName, String idsStr) throws Exception{
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		List<UserPushInfo> pushInfoList = webUserInfoDao.queryUserPushInfoByIds(ids);
		
		Integer pushAction = Tag.PUSH_ACTION_SYS;
		String pushId = null;
		if(worldId != null) {
			pushAction = Tag.PUSH_ACTION_WORLD_MSG;
			pushId = worldId.toString();
		} else if(!StringUtil.checkIsNULL(activityName)) {
			pushAction = Tag.PUSH_ACTION_ACTIVITY_MSG;
			pushId = activityName;
		}
		pushService.pushListMessage(content, pushAction, pushId, pushInfoList, new PushFailedCallback() {
			
			@Override
			public void onPushFailed(Exception e) {
			}
		});
	}

	@Override
	public void buildRecipientMsgBox(Integer maxId, final Integer recipientId, Integer phoneCode,
			int start, int limit, Map<String, Object> jsonMap) throws Exception {
		final LinkedHashMap<String, Object> attrMap = new LinkedHashMap<String, Object>();
		if(phoneCode != null) {
			attrMap.put("phone_code", phoneCode);
		}
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<UserMsgRecipientDto>() {

			@Override
			public List<UserMsgRecipientDto> getSerializables(RowSelection rowSelection) {
				return userMsgDao.queryRecipientMsgBox(recipientId, attrMap, rowSelection);
			}

			@Override
			public List<UserMsgRecipientDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return userMsgDao.queryRecipientMsgBox(maxId, recipientId, attrMap, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return userMsgDao.queryRecipientMsgBoxCount(maxId, recipientId, attrMap);
			}
			
		}, OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);
	}

	@Override
	public void buildUserMsg(final Integer userId, final Integer otherId, int maxId,
			int start, int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<UserMsgDto>() {

			@Override
			public List<UserMsgDto> getSerializables(RowSelection rowSelection) {
				List<UserMsgDto> list = webUserMsgDao.queryUserMsg(userId, otherId, rowSelection);
				return list;
			}

			@Override
			public List<UserMsgDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return webUserMsgDao.queryUserMsg(maxId, userId, otherId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return webUserMsgDao.queryUserMsgCount(maxId, userId, otherId);
			}
			
		}, OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);
	}

	@Override
	public void updateRecipientMsgBoxUnValid(String senderIdsStr,
			Integer recipientId) {
		Integer[] ids = StringUtil.convertStringToIds(senderIdsStr);
		webUserMsgRecipientBoxDao.updateRecipientUnValid(ids, recipientId);
	}

}

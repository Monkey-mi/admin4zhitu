package com.imzhitu.admin.userinfo.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.UserMsgConver;
import com.hts.web.common.pojo.UserMsgDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.NumberUtil;
import com.hts.web.common.util.StringUtil;
import com.hts.web.userinfo.dao.MsgUnreadDao.UnreadType;
import com.hts.web.userinfo.dao.UserMsgConversationDao;
import com.imzhitu.admin.common.pojo.UserMsgConversationDto;
import com.imzhitu.admin.common.pojo.UserMsgDanmu;
import com.imzhitu.admin.userinfo.dao.UserMsgDao;
import com.imzhitu.admin.userinfo.mapper.UserMsgConversationMapper;
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

	@Autowired
	private com.hts.web.userinfo.service.UserMsgService webUserMsgService;
	
	@Autowired
	private com.hts.web.userinfo.dao.UserMsgConversationDao webUserMsgConversationDao;

	@Autowired
	private com.hts.web.userinfo.dao.MsgUnreadDao webMsgUnreadDao;
	
	@Autowired
	private UserMsgConversationMapper conversationMapper;
	
	@Autowired
	private UserMsgDao userMsgDao;
	
	@Value("${push.customerServiceId}")
	private Integer customerServiceId;
	
	public Integer getCustomerServiceId() {
		return customerServiceId;
	}

	public void setCustomerServiceId(Integer customerServiceId) {
		this.customerServiceId = customerServiceId;
	}

	@Override
	public void buildConversation(UserMsgConversationDto conver, Integer start, Integer limit,
			Map<String, Object> jsonMap) throws Exception {
		conver.setUserId(customerServiceId);
		buildNumberDtos("getContentId", conver, start, limit, jsonMap, 
				new NumberDtoListAdapter<UserMsgConversationDto>() {

			@Override
			public List<? extends Serializable> queryList(UserMsgConversationDto dto) {
				if(dto.getOtherId() != null) {
					List<UserMsgConversationDto> list = conversationMapper.queryConverByOtherId(
							customerServiceId, dto.getOtherId());
					if(list.isEmpty()) {
						webUserMsgConversationDao.saveConver(
								new UserMsgConver(customerServiceId, dto.getOtherId(), 
										0, 0, UserMsgConversationDao.MSG_TYPE_SEND));
						list = conversationMapper.queryConverByOtherId(customerServiceId, dto.getOtherId());
					}
					return list;
					
				}
				return conversationMapper.queryConver(dto);
			}

			@Override
			public long queryTotal(UserMsgConversationDto dto) {
				if(dto.getOtherId() != null) {
					return 1l;
				}
				return conversationMapper.queryConverCount(dto);
			}
		});
	}
	

	@Override
	public void delConver(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		for(Integer id : ids) {
			webUserMsgConversationDao.sendMsg(customerServiceId, id, 0);
		}
	}

	@Override
	public void buildUserMsg(final Integer otherId, int maxId,
			int start, int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<UserMsgDto>() {

			@Override
			public List<UserMsgDto> getSerializables(RowSelection rowSelection) {
				List<UserMsgDto> list = webUserMsgService.queryUserMsg(customerServiceId, otherId, 0, rowSelection);
				webUserMsgConversationDao.clearUnreadCount(customerServiceId, otherId);
				if(!list.isEmpty()) {
					webMsgUnreadDao.clearCount(customerServiceId, list.get(0).getId(), UnreadType.UMSG);
				}
				return list;
			}

			@Override
			public List<UserMsgDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<UserMsgDto> list = webUserMsgService.queryUserMsg(customerServiceId, otherId,
						maxId, rowSelection);
				return list;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return webUserMsgService.queryUserMsgCount(customerServiceId, otherId, maxId); // 获取消息总数
			}
			
		}, OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);
	}
	
	@Override
	public void sendMsg(Integer userId, String content, Boolean keep) throws Exception {
		webUserMsgService.saveUserMsg(customerServiceId, userId, content);
		if(!keep) {
			webUserMsgConversationDao.sendMsg(customerServiceId, userId, 0);
		}
	}
	
	@Override
	public void sendMsgs(String idStrs, String content) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idStrs);
		if(ids != null && ids.length > 0) {
			for(Integer id : ids) {
				webUserMsgService.saveUserMsg(customerServiceId, id, content);
				webUserMsgConversationDao.sendMsg(customerServiceId, id, 0);
			}
		}
	}
	
	@Override
	public void sendMsgs(String[] idStrs, String content) throws Exception {
		Integer id;
		for(String str : idStrs) {
			id = Integer.parseInt(str);
			webUserMsgService.saveUserMsg(customerServiceId, id, content);
			webUserMsgConversationDao.sendMsg(customerServiceId, id, 0);
		}
	}



	@Override
	public void buildUserMsgDanmu(int maxId, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<UserMsgDanmu>() {

			@Override
			public List<UserMsgDanmu> getSerializables(RowSelection rowSelection) {
				final List<UserMsgDanmu> list = new ArrayList<UserMsgDanmu>();
				userMsgDao.queryMsgDanmu(customerServiceId, rowSelection, new RowCallback<UserMsgDanmu>() {

					@Override
					public void callback(UserMsgDanmu t) {
						setDanmuColor(t);
						setDanmuPos(t);
						list.add(t);
					}
				});
				return list;
			}

			@Override
			public List<UserMsgDanmu> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				final List<UserMsgDanmu> list = new ArrayList<UserMsgDanmu>();
				userMsgDao.queryMsgDanmu(maxId, customerServiceId, rowSelection, new RowCallback<UserMsgDanmu>() {

					@Override
					public void callback(UserMsgDanmu t) {
						setDanmuColor(t);
						setDanmuPos(t);
						list.add(t);
					}
				});
				return list;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return 0;
			}
			
		}, OptResult.JSON_KEY_MSG, OptResult.TOTAL);
	}

	/**
	 * 设置弹幕颜色
	 * @param dm
	 */
	private void setDanmuColor(UserMsgDanmu dm) {
		String color = "";
		int i = NumberUtil.getRandomIndex(4);
		switch(i) {
		case 2:
			color = "#d5a5a5";
			break;
		case 3:
			color = "#e6f1b3";
			break;
		default:
			color = "#f2f2f2";
			break;
		}
		dm.setColor(color);
	}
	
	/**
	 * 设置弹幕位置
	 * 
	 * @param dm
	 */
	private void setDanmuPos(UserMsgDanmu dm) {
		int pos = 0;
//		int i = NumberUtil.getRandomIndex(10);
//		switch(i) {
//		case 2:
//			pos = 1;
//			break;
//		case 3:
//			pos = 2;
//			break;
//		default:
//			pos = 0;
//			break;
//		}
		dm.setPosition(pos);
	}


}

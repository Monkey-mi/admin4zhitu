package com.imzhitu.admin.userinfo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.PlatFormCode;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.MD5Encrypt;
import com.imzhitu.admin.common.UserWithInteract;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.common.pojo.UserInfoDto;
import com.imzhitu.admin.interact.dao.InteractUserDao;
import com.imzhitu.admin.userinfo.dao.UserInfoDao;
import com.imzhitu.admin.userinfo.dao.UserTrustDao;
import com.imzhitu.admin.userinfo.mapper.UserInfoMapper;
import com.imzhitu.admin.userinfo.mapper.UserLoginPersistentMapper;
import com.imzhitu.admin.userinfo.mapper.UserSocialAccountMapper;
import com.imzhitu.admin.userinfo.service.UserInfoService;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl implements UserInfoService {
	
	private Logger log = Logger.getLogger(UserInfoService.class);
	
	public static final String PASSWORD = "123456";

	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private com.hts.web.userinfo.dao.UserInfoDao webUserInfoDao;
	
	@Autowired
	private com.hts.web.userinfo.service.UserInfoService webUserInfoService;
	
	@Autowired
	private com.hts.web.userinfo.service.UserMsgService webUserMsgService;
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Autowired
	private InteractUserDao interactUserDao;
	
	@Autowired
	private UserTrustDao userTrustDao;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private UserSocialAccountMapper socialAccountMapper;
	
	@Autowired
	private UserLoginPersistentMapper userLoginPersistentMapper;
	
	@Value("${push.customerServiceId}")
	private Integer customerServiceId;

	public Integer getCustomerServiceId() {
		return customerServiceId;
	}
	
	public void setCustomerServiceId(Integer customerServiceId) {
		this.customerServiceId = customerServiceId;
	}
	@Override
	public void buildUser(int maxId, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<UserInfoDto>(){

			@Override
			public List<UserInfoDto> getSerializables(
					RowSelection rowSelection) {
				List<UserInfoDto> userList = userInfoDao.queryUserInfo(rowSelection);
				extractInteractInfo(userList);
				webUserInfoService.extractVerify(userList);
				return userList;
			}

			@Override
			public List<UserInfoDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<UserInfoDto> userList = userInfoDao.queryUserInfoByMaxId(maxId, rowSelection);
				extractInteractInfo(userList);
				webUserInfoService.extractVerify(userList);
				return userList;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
//				return userInfoDao.queryUseInfoCountByMaxId(maxId);
				return 0L;
			}
			
		},OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_ID);
		
		// 临时语句，为了配合数据，强制查询第一页的最大用户id
		if(maxId == 0)
			maxId = userInfoMapper.selectMaxId();
		
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxId);
		jsonMap.put(OptResult.JSON_KEY_TOTAL, userInfoDao.queryUseInfoCountByMaxId(maxId));
	}
	
	@Override
	public void buildUser(int maxId, int start, int limit, final String userName, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<UserInfoDto>(){

			@Override
			public List<UserInfoDto> getSerializables(
					RowSelection rowSelection) {
				List<UserInfoDto> userList = userInfoDao.queryUserInfoByUserName(userName, rowSelection);
				extractInteractInfo(userList);
				webUserInfoService.extractVerify(userList);
				return userList;
			}

			@Override
			public List<UserInfoDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<UserInfoDto> userList = userInfoDao.queryUserInfoByMaxIdAndUserName(maxId, userName, rowSelection);
				extractInteractInfo(userList);
				webUserInfoService.extractVerify(userList);
				return userList;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return userInfoDao.queryUseInfoCountByMaxIdAndUserName(maxId, userName);
			}
			
		},OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_ID);
	}
	
	@Override
	public void buildUser(int maxId, int start, int limit, Integer userId,
			Map<String, Object> jsonMap) throws Exception {
		List<UserInfoDto> list = new ArrayList<UserInfoDto>();
		long total = 0l;
		UserInfoDto userInfo = userInfoDao.queryUserInfoDtoById(userId);
		if(userInfo != null) {
			list.add(userInfo);
			extractInteractInfo(list);
			total = 1;
			maxId = userId;
		} else {
			maxId = 0;
		}
		webUserInfoService.extractVerify(userInfo);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxId);
	}
	
	/**
	 * 查询 用户的信息，主要是查询用户的推荐状态
	 * @param userId
	 * @param jsonMap
	 * @throws Exception
	 */
	@Override
	public void queryUserInfoByUserId(Integer userId,Map<String,Object>jsonMap)throws Exception{
		UserInfoDto userInfo =  userInfoDao.queryUserInfoDtoById(userId);
		jsonMap.put("userInfo", userInfo);
	}
	
	@Override
	public void queryUserByUserIdAndCheckIsZombie(Integer userId,Map<String,Object>jsonMap)throws Exception {
		UserInfoDto userInfo = userInfoDao.queryUserInfoDtoById(userId);
		boolean r = userInfoDao.queryUserIsZombieByUserId(userId);
		long superbCount = userInfoDao.queryUserWorldCountByUserId(userId);
		jsonMap.put("superbCount", superbCount);
		if(r)jsonMap.put("zombie", 1);
		else jsonMap.put("zombie", 0);
		jsonMap.put("userInfo",	userInfo);
	}


	@Override
	public void shieldUser(Integer userId) throws Exception {
		userInfoDao.updateShield(userId, Tag.TRUE);
	}

	@Override
	public void unShieldUser(Integer userId)  throws Exception {
		userInfoDao.updateShield(userId, Tag.FALSE);
	}

	@Override
	public void saveUser(String userName, String loginCode, String userAvatar,
			String userAvatarL) throws Exception {
		Integer id = webKeyGenService.generateId(KeyGenServiceImpl.USER_ID);
		com.hts.web.common.pojo.UserInfo userInfo = new com.hts.web.common.pojo.UserInfo(id, PlatFormCode.ZHITU,
				null, null, null, Tag.VERIFY_NONE, null, loginCode,userName,userAvatar, userAvatarL, Tag.SEX_UNKNOWN, null,
				null,null, null, null, null, new Date(), 0, null, null, Tag.IOS, null, null, Tag.ONLINE, Tag.VERSION_2_8_2);
		byte[] passwordEncrypt = MD5Encrypt.encryptByMD5(PASSWORD);
		webUserInfoDao.saveUserInfo(userInfo, passwordEncrypt);
	}

	@Override
	public void updateTrust(Integer userId, Integer trust,Integer operatorId) throws Exception {
		userInfoDao.updateTrustById(userId, trust);
		Date now = new Date();
		if(userTrustDao.queryUserTrustByUid(userId) == null)
			userTrustDao.addUserTrust(userId,now , now, trust,operatorId);
		else
			userTrustDao.updateUserTrustByUid(userId, now, trust,operatorId);
	}
	
	@Override
	public void extractInteractInfo(final List<? extends UserWithInteract> userList) {
		int len = userList.size();
		if(len > 0) {
			final Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			Integer[] userIds = new Integer[len];
			for(int i = 0; i < len; i++) {
				UserWithInteract user = userList.get(i);
				userIds[i] = user.getUserId();
				indexMap.put(user.getUserId(), i);
			}
			interactUserDao.queryUserIdByUIDS(userIds, new RowCallback<Integer>() {
				
				@Override
				public void callback(Integer t) {
					Integer index = indexMap.get(t);
					if(index != null) 
						userList.get(index).setInteracted(Tag.TRUE);;
				}
			});
		}
	}
	
	/**
	 * 更新用户签名
	 */
	@Override
	public void updateSignature(String userInfoJSON)throws Exception{
		JSONArray jsna = JSONArray.fromObject(userInfoJSON);
		for(int i=0;i<jsna.size();i++){
			net.sf.json.JSONObject jsno = jsna.getJSONObject(i);
			Integer userId = jsno.getInt("userId");
			String signature = jsno.getString("signature");
			userInfoDao.updateSignature(userId, signature);
		}
		
	}
	@Override
	public void updateExchangeUsers(Integer id, Integer toId) throws Exception {
		Integer[] ids = new Integer[]{id, toId};
		List<UserInfo> users = userInfoMapper.selectByIds(ids);
		UserInfo u1 = users.get(0);
		UserInfo u2 = users.get(1);
		Integer tmpId = u1.getId();
		u1.setId(u2.getId());
		u2.setId(tmpId);
		
		// 调换信息
		userInfoMapper.updateLoginInfoById(u1);
		userInfoMapper.updateLoginInfoById(u2);

		// 删除绑定的账号信息
		socialAccountMapper.deleteByUID(id);
		socialAccountMapper.deleteByUID(toId);
		
		// 清空登录状态
		userLoginPersistentMapper.deleteByUserId(id);
		userLoginPersistentMapper.deleteByUserId(toId);
		
		// 发送通知
		webUserMsgService.saveUserMsg(customerServiceId, u1.getId(), "您的织图账号["+u1.getUserName()+"]和["+u2.getUserName()+"]的登录平台已经调换,请退出后重新登录", Tag.USER_MSG_NORMAL);
		webUserMsgService.saveUserMsg(customerServiceId, u2.getId(), "您的织图账号["+u2.getUserName()+"]和["+u1.getUserName()+"]的登录平台已经调换,请退出后重新登录", Tag.USER_MSG_NORMAL);
		
	}
	
}

package com.imzhitu.admin.op.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.PlatFormCode;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.OpSquare;
import com.hts.web.common.pojo.OpUserRecommend;
import com.hts.web.common.pojo.OpUserZombie;
import com.hts.web.common.pojo.UserInfo;
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.MD5Encrypt;
import com.hts.web.common.util.NumberUtil;
import com.hts.web.common.util.StringUtil;
import com.hts.web.operations.service.impl.UserOperationsServiceImpl;
import com.hts.web.push.service.PushService;
import com.hts.web.push.service.impl.PushServiceImpl.PushFailedCallback;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.OpSysMsg;
import com.imzhitu.admin.common.pojo.OpUserRecommendDto;
import com.imzhitu.admin.op.dao.UserRecommendDao;
import com.imzhitu.admin.op.dao.UserZombieDao;
import com.imzhitu.admin.op.service.OpMsgService;
import com.imzhitu.admin.op.service.OpUserService;
import com.imzhitu.admin.userinfo.mapper.UserInfoMapper;
import com.imzhitu.admin.userinfo.service.UserInfoService;
import com.imzhitu.admin.userinfo.service.impl.UserInfoServiceImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class OpUserServiceImpl extends BaseServiceImpl implements OpUserService {
	
	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private com.hts.web.userinfo.service.UserMsgService webUserMsgService;
	
	@Autowired
	private PushService pushService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private com.hts.web.userinfo.dao.UserInfoDao webUserInfoDao;
	
	@Autowired
	private com.hts.web.security.dao.UserLoginPersistentDao webUserLoginPersistentDao;
	
	@Autowired
	private com.hts.web.security.service.UserLoginPersistentService webUserLoginPersistentService;
	
	@Autowired
	private com.hts.web.operations.dao.UserRecommendDao webUserRecommendDao;
	
	@Autowired
	private com.hts.web.userinfo.service.UserInfoService webUserInfoService;
	
	@Autowired
	private UserZombieDao userZombieDao;
	
	@Autowired
	private UserRecommendDao userRecommendDao;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private com.hts.web.operations.dao.SysMsgDao webSysMsgDao;
	
	@Autowired
	private com.hts.web.aliyun.service.OsUserInfoService webOsUserInfoService;
	
	@Autowired
	private OpMsgService opMsgService;
	
	/**
	 * 达人邀请缩略图链接
	 */
	private static final String SYS_INVITE_THUMB = "http://imzhitu.qiniudn.com/op/notice/sysinvite.png";
	
	/**
	 * 达人邀请通知
	 */
	private static final String SYS_INVITE_MSG = "您收到一封神秘的信，点击查看>>";
	
	@Override
	public void buildRecommendUser(Integer maxId, int page, int rows,
			Integer userAccept, Integer sysAccept, Integer notified,Integer weight,
			Integer verifyId, String userName,Integer lastUsed, Date begin,Date end,Map<String, Object> jsonMap) throws Exception {
		
		final Map<String, Object> attrMap = new LinkedHashMap<String, Object>();
		
		if(userAccept != null)
			attrMap.put("user_accept", userAccept);
		
		if(sysAccept != null)
			attrMap.put("sys_accept", sysAccept);
		
		if(notified != null) 
			attrMap.put("notified", notified);
		
		if(weight != null && weight>=0)
			attrMap.put("weight", weight);
		
		if(verifyId != null && verifyId > 0) {
			attrMap.put("verify_id", verifyId);
		}
		
		if(lastUsed !=null && lastUsed== 1){
			Date now = new Date();
			long monthago = now.getTime() - 30l*24*60*60*1000;
			attrMap.put("last_login", monthago);
		}
		
		if(!StringUtil.checkIsNULL(userName)) {
			attrMap.clear();
			try {
				Integer uid = Integer.parseInt(userName);
				attrMap.put("user_id", uid);
			} catch(NumberFormatException e) {
				attrMap.put("user_name", "%"+userName+"%");
			}
			buildRecommendUser(maxId, page, rows, attrMap, jsonMap);
			return;
		}
		
		if ( begin != null ) {
			attrMap.put("begin", begin);
		}
		if ( end != null ) {
			attrMap.put("end", end);
		}
		
		buildSerializables(maxId, page, rows, jsonMap, new SerializableListAdapter<OpUserRecommendDto>() {

			@Override
			public List<OpUserRecommendDto> getSerializables(
					RowSelection rowSelection) {
				List<OpUserRecommendDto> userList = userRecommendDao.queryRecommendUser(attrMap, rowSelection);
				userInfoService.extractInteractInfo(userList);
				return userList;
			}

			@Override
			public List<OpUserRecommendDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<OpUserRecommendDto> userList = userRecommendDao.queryRecommendUser(maxId, attrMap, rowSelection);
				userInfoService.extractInteractInfo(userList);
				return userList;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return userRecommendDao.queryRecommendUserCount(maxId, attrMap);
			}
			
		},OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_ID);
	
	}
	
	public void buildRecommendUser(Integer maxId, int page, int rows, final Map<String, Object> attrMap,
			Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, page, rows, jsonMap, new SerializableListAdapter<OpUserRecommendDto>() {

			@Override
			public List<OpUserRecommendDto> getSerializables(
					RowSelection rowSelection) {
				List<OpUserRecommendDto> userList = userRecommendDao.queryRecommendUserByName(attrMap, rowSelection);
				userInfoService.extractInteractInfo(userList);
				return userList;
			}

			@Override
			public List<OpUserRecommendDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<OpUserRecommendDto> userList = userRecommendDao.queryRecommendUserByName(maxId, attrMap, rowSelection);
				userInfoService.extractInteractInfo(userList);
				return userList;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return userRecommendDao.queryRecommendUserCount(maxId, attrMap);
			}
			
		},OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_ID);
	}
	
	@Override
	public Integer saveRecommendUser(Integer userId, Integer verifyId, 
			String recommendDesc, Integer recommenderId) throws Exception {
		OpUserRecommend recommend = webUserRecommendDao.queryRecommendUserByUID(userId);
		com.imzhitu.admin.common.pojo.UserInfo userInfo = new com.imzhitu.admin.common.pojo.UserInfo();
		userInfo.setId(userId);
		userInfo.setTrust(Tag.TRUE);
		if(recommend == null) {
			Date date = new Date();
			Integer userAccept = UserOperationsServiceImpl.USER_RECOMMEND_ACCEPT;
			List<com.imzhitu.admin.common.pojo.UserInfo> userInfoList = userInfoMapper.queryUserInfo(userInfo);
			float ver = userInfoList.get(0).getVer();
			Integer id = webKeyGenService.generateId(KeyGenServiceImpl.OP_USER_REC_ID);
			if(ver >= Tag.VERSION_2_8_3) {
				userAccept = UserOperationsServiceImpl.USER_RECOMMEND_PENDING;
			} else {
				userInfo.setStar(verifyId);
			}
			webUserRecommendDao.saveRecommendUser(new OpUserRecommend(id, userId, verifyId, recommendDesc,
					recommenderId, date, date, userAccept, UserOperationsServiceImpl.USER_RECOMMEND_PENDING));
			userInfoMapper.updateByIdSelective(userInfo);
			webOsUserInfoService.updateUserWithoutNULL(userId, null, null, null, null, verifyId, null); // 更新opensearch明星标记
			return id;
		}
		return recommend.getId();
	}
	
	@Override
	public void deleteRecommendUserByUserId(Integer userId, Boolean deleteStar) {
		userRecommendDao.deleteRecommendUserByUserId(userId);
		if(deleteStar) {
			com.imzhitu.admin.common.pojo.UserInfo userInfo = new com.imzhitu.admin.common.pojo.UserInfo();
			userInfo.setId(userId);
			userInfo.setTrust(Tag.FALSE);
			userInfoMapper.updateByIdSelective(userInfo);
		}
	}

	@Override
	public void deleteRecommendUserByIds(String idsStr, 
			Boolean deleteStar,Boolean insertMessage) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		List<Integer> userIds = userRecommendDao.queryUserIdByIds(ids);
		for(Integer userId : userIds) {
			insertDelMessage(userId,insertMessage);
			deleteRecommendUserByUserId(userId, deleteStar);
		}
	}
	
	/**
	 * 根据id插入一条删除用户明星的消息
	 * @param id
	 * @param insertMessage
	 * @throws Exception
	 */
	@Override
	public void insertDelMessage(Integer userId,Boolean insertMessage) throws Exception{
		if(insertMessage){
			long r = userRecommendDao.queryUserAccpetAndSysAcceptResult(userId);
			if(r==0) return ;
			opMsgService.saveSysMsg(userId,
					"由于您已经一个月没有登录织图，达人已被取消，有任何意见，可联系织图小秘书",
					4, userId, null, null, null);
		}
	}
	
	@Override
	public void updateRecommendUserByJSON(String userJSON) throws Exception {
		JSONArray jsArray = JSONArray.fromObject(userJSON);
		for(int i = 0; i < jsArray.size(); i++) {
			JSONObject jsObj = jsArray.getJSONObject(i);
			Integer id = jsObj.getInt("id");
			String desc = jsObj.getString("recommendDesc");
			userRecommendDao.updateRecommendDesc(id, desc);
			Integer userId=jsObj.getInt("userId");
			Integer fixPos=jsObj.getInt("fixPos");
			if(fixPos>0)
				userRecommendDao.setFixPosByUserId(userId, fixPos);
		}
	}
	
	@Override
	public void updateRecommendSysAccept(String idsStr, Integer state) throws Exception {
		Integer star = Tag.FALSE;
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		if(state.equals(UserOperationsServiceImpl.USER_RECOMMEND_ACCEPT)) {
//			uidList = userRecommendDao.queryAcceptUserId(ids); // 只更新已经接受推荐的用户明星标记
//			star = Tag.TRUE;
			com.imzhitu.admin.common.pojo.UserInfo userInfo = new com.imzhitu.admin.common.pojo.UserInfo();
			List<OpUserRecommend> list = userRecommendDao.queryAcceptUserId(ids);
			for(OpUserRecommend rec : list) {
				userInfo.setId(rec.getUserId());
				userInfo.setStar(rec.getVerifyId());
				userInfoMapper.updateByIdSelective(userInfo);
			}
		} else {
			List<Integer> uidList = userRecommendDao.queryUserId(ids);
			// 批量更新用户明星标记
			if(uidList.size() > 0) {
				Integer[] uids = new Integer[uidList.size()];
				uidList.toArray(uids);
				userInfoMapper.updateStarByIds(uids, star);
			}
		}
		// 更新系统接受标记
		userRecommendDao.updateSysAccept(ids, state);
		
		
		
	}
	
	@Override
	public void updateRecommendUserIndex(String[] idStrs) throws Exception {
		for(int i = idStrs.length - 1; i >= 0; i--) {
			String idStr = idStrs[i];
			if(idStr != null && idStr != "") {
				int uid = Integer.parseInt(idStrs[i]);
				int id = webKeyGenService.generateId(KeyGenServiceImpl.OP_USER_REC_ID);
				userRecommendDao.updateRecommendId(uid, id);
			}
		}
	}
	
	@Override
	public void addRecommendUserMsg(final Integer id, Integer recipientId, 
			String recipientName, String msg, String recommendType, Integer userAccept, Boolean accepted) throws Exception {
		
		String fullMsg;
		UserPushInfo userPushInfo; 
		
		userPushInfo = webUserInfoDao.queryUserPushInfoById(recipientId);
		
		if(userPushInfo.getVer() >= Tag.VERSION_3_0) {
			fullMsg = recipientName + "，" + SYS_INVITE_MSG;
		} else {
			fullMsg = recipientName + "，" + msg;
		}
		
		Integer notified = userRecommendDao.queryNotified(id);
		if(notified == null || notified.equals(0)) {
			notified = 0;
			// +1为了避免null值客户端解析为0
			Integer objMeta = UserOperationsServiceImpl.USER_RECOMMEND_PENDING + 1;
			if(!accepted) {
				objMeta = UserOperationsServiceImpl.USER_RECOMMEND_REJECT + 1;
			} else if(userAccept.equals(UserOperationsServiceImpl.USER_RECOMMEND_ACCEPT)) {
				objMeta = UserOperationsServiceImpl.USER_RECOMMEND_ACCEPT + 1;
			}
			opMsgService.saveSysMsg(recipientId, fullMsg, Tag.USER_MSG_USER_RECOMMEND, recipientId,
					String.valueOf(objMeta), recommendType, SYS_INVITE_THUMB);
		}
		userRecommendDao.updateNotified(id, ++notified);
		
		pushService.pushSysMessage(fullMsg, Admin.ZHITU_UID, fullMsg, userPushInfo, Tag.USER_MSG_USER_RECOMMEND, new PushFailedCallback() {

			@Override
			public void onPushFailed(Exception e) {
			}
		});
	}
	
	@Override
	public void buildZombieUser(Integer min,Integer max,Integer userId,String userName,Date begin,Date end,int page, int rows, Map<String, Object> jsonMap) {
		long total = userZombieDao.queryZombieUserCount(min,max,userId,userName,begin,end);
		List<OpUserZombie> dtoList = userZombieDao.queryZombieUser(min,max,userId,userName,begin,end,new RowSelection(page, rows));
		webUserInfoService.extractVerify(dtoList);
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_ROWS, dtoList);
	}
	
	@Override
	public void saveZombieUser(String userName, String loginCode, String userAvatar,
			String userAvatarL, String recommender) throws Exception {
		Integer id = webKeyGenService.generateId(KeyGenServiceImpl.USER_ID);
		UserInfo userInfo = new UserInfo(id, PlatFormCode.ZHITU, null, null, null, Tag.VERIFY_NONE, null,
				loginCode, userName,userAvatar, userAvatarL, Tag.SEX_UNKNOWN, null, null, null, null, null, null,
				new Date(), 0, null, String.valueOf(Tag.FALSE),  Tag.IOS, null, null, Tag.ONLINE, Tag.VERSION_2_8_2);
		byte[] passwordEncrypt = MD5Encrypt.encryptByMD5(UserInfoServiceImpl.PASSWORD);
		webUserInfoDao.saveUserInfo(userInfo, passwordEncrypt);
		userZombieDao.saveZombieUser(new OpSquare(id, recommender, new Date(), null));
	}
	
	@Override
	public void saveZombieUsers(String idsStr, String recommender) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		for(Integer id : ids) {
			userZombieDao.saveZombieUser(new OpSquare(id, recommender, new Date(), null));
		}
	}
	
	@Override
	public void batchDeleteZombieUsers(String idsStr) {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		for(Integer id : ids) {
			userZombieDao.deleteZombieUserById(id);
		}
	}
	
	@Override
	public void shieldZombie(Integer userId) throws Exception {
		userZombieDao.updateShield(userId, Tag.TRUE);
	}

	@Override
	public void unShieldZombie(Integer userId) throws Exception {
		userZombieDao.updateShield(userId, Tag.FALSE);
	}
	

	@Override
	public void saveOrUpdateZombieLoginStatus() throws Exception {
		Date date = new Date();
		List<Integer> uidList = userZombieDao.queryUnShieldZombieUserId();
		for(Integer uid : uidList) {
			String token = webUserLoginPersistentService.generateTokenData();
			PersistentRememberMeToken rememberToken = webUserLoginPersistentDao.queryTokenByUserId(uid);
			if(rememberToken != null) {
				webUserLoginPersistentDao.updateToken(rememberToken.getSeries(), token, date);
			} else {
				String serial = webUserLoginPersistentService.generateSeriesData();
				webUserLoginPersistentDao.createNewToken(new PersistentRememberMeToken(String.valueOf(uid), serial, token, date));
			}
		}
	}

	@Override
	public List<Integer> getRandomUserZombieIds(Integer length) {
		Set<Integer> usedIndex = new HashSet<Integer>();
		Long total = userZombieDao.queryZombieCount(Tag.FALSE);
		if(total < length) {
			throw new IndexOutOfBoundsException("getRandomUserZombieIds:马甲数量不足,所需" + length + ",只有" + total + "个");
		}
		List<Integer> ids = new ArrayList<Integer>();
		for(int i = 0; i < length; i++) {
			int index = NumberUtil.getUnRepeatRandomIndex(total.intValue(), usedIndex);
			usedIndex.add(index);
			Integer uid = userZombieDao.queryUserIdByPageIndex(Tag.FALSE, index);
			ids.add(uid);
		}
		return ids;
	}

	@Override
	public List<Integer> getRandomUnFollowUserZombieIds(Integer userId,Integer worldId,	Integer length) {
//		Set<Integer> usedIndex = new HashSet<Integer>();
		long total = userZombieDao.queryUnFollowZombieCountForInteract(userId,worldId, Tag.FALSE);
		if(total < length.longValue()) {
			throw new IndexOutOfBoundsException("getRandomUnFollowUserZombieIds:马甲数量不足,所需" + length + ",只有" + total + "个");
		}
		return userZombieDao.queryRandomUnFollowUserId(userId, worldId, Tag.FALSE, length);
		
	}
	
	@Override
	public List<Integer> getRandomUnFollowUserZombieIds(Integer userId,	Integer length) {
		Set<Integer> usedIndex = new HashSet<Integer>();
		long total = userZombieDao.queryUnFollowZombieCount(userId, Tag.FALSE);
		if(total < length.longValue()) {
			throw new IndexOutOfBoundsException("getRandomUnFollowUserZombieIds:马甲数量不足,所需" + length + ",只有" + total + "个");
		}
		List<Integer> ids = new ArrayList<Integer>();
		for(int i = 0; i < length; i++) {
			int index = NumberUtil.getUnRepeatRandomIndex((int)total, usedIndex);
			usedIndex.add(index);
			Integer uid = userZombieDao.queryUnFollowUserIdByPageIndex(userId, Tag.FALSE, index);
			ids.add(uid);
		}
		return ids;
	}
	
	@Override
	public List<Integer> getRandomFollowUserZombieIds(Integer userId,Integer worldId, Integer start,Integer length){
		if(start==null||start<1){
			throw new  IndexOutOfBoundsException("getRandomFollowUserZombieIds:参数出错，start值小于1或为空，start=" +start);
		}
		if(length==null||length<1){
			throw new  IndexOutOfBoundsException("getRandomFollowUserZombieIds:参数出错，length值小于1或为空，length=" +length);
		}
		long total = userZombieDao.queryZombieFollowCountByUserIdForInteractForInteract(userId,worldId);
		if(length.longValue() > total){
			throw new  IndexOutOfBoundsException("getRandomFollowUserZombieIds:马甲数量不足,所需" + length + ",只有" + total + "个");
		}
		Set<Integer> usedIndex = new HashSet<Integer>();
		List<Integer> ids = new ArrayList<Integer>();
		for(int i = 0; i < length; i++) {
			int index = NumberUtil.getUnRepeatRandomIndex((int)total, usedIndex);
			usedIndex.add(index);
			Integer uid = userZombieDao.queryZombieFollowByUserIdForInteractForInteract(userId,worldId, index,1);
			ids.add(uid);
		}
		return ids;
	}

	@Override
	public void updateRecommendVerify(Integer id, Integer verifyId)
			throws Exception {
		OpUserRecommend recommend = userRecommendDao.queryRecommendById(id);
		if(recommend != null) {
			com.imzhitu.admin.common.pojo.UserInfo userInfo = new com.imzhitu.admin.common.pojo.UserInfo();
			userInfo.setId(recommend.getUserId());
			List<com.imzhitu.admin.common.pojo.UserInfo> userInfoList = userInfoMapper.queryUserInfo(userInfo);
			Integer star = userInfoList.get(0).getStar();
			if(star != Tag.FALSE) {
				userInfo.setStar(verifyId);
				userInfoMapper.updateByIdSelective(userInfo);
			}
			userRecommendDao.updateVerifyId(id, verifyId);
		}
	}

	@Override
	public void delStar(Integer userId)throws Exception{
		if(userId == null)return;
		userRecommendDao.deleteRecommendUserByUserId(userId);	
		com.imzhitu.admin.common.pojo.UserInfo userInfo = new com.imzhitu.admin.common.pojo.UserInfo();
		userInfo.setId(userId);
		userInfo.setStar(Tag.FALSE);
		userInfoMapper.updateByIdSelective(userInfo);
	}
	
	/**
	 * 直接让用户接受邀请
	 * @param userId
	 * @param verifyId
	 * @throws Exception
	 */
	@Override
	public void userAcceptRecommendDirect(Integer userId,Integer verifyId)throws Exception{
		Date date = new Date();
		webUserRecommendDao.updateUserAcceptByUID(userId, 1, date);
		webUserInfoDao.updateStartById(userId, verifyId);
//		webSysMsgDao.updateRecipientValid(userId, 3, Tag.FALSE);
		webSysMsgDao.deleteByObjType(userId, 3);
	}

	@Override
	public Integer updateRecommendWeight(Integer id, Boolean isAdd) throws Exception {
		Integer weight = 0;
		if(isAdd) {
			weight = webKeyGenService.generateId(KeyGenServiceImpl.OP_USER_REC_ID);
		}
		userRecommendDao.updateWeight(id, weight);
		return weight;
	}
	
	@Override
	public Integer updateRecommendWeightByUID(Integer userId, Boolean isAdd, Integer recommendUID) throws Exception {
		Integer id = userRecommendDao.queryIdByUID(userId);
		if(isAdd && id == null) {
			Integer verifyId = 2; // 默认是织图控
			id = saveRecommendUser(userId, verifyId, null, recommendUID);
		} 
		return updateRecommendWeight(id, isAdd);
	}
	
	
	/**
	 * 更新马甲的性别
	 * @param sex
	 * @param userId
	 */
	@Override
	public void updateZombieSex(Integer sex,Integer userId)throws Exception{
		if(sex>0 && sex<3)
			userZombieDao.updateZombieSex(sex, userId);
	}
	
	/**
	 *更新马甲的签名 
	 * @param signture
	 * @param userId
	 */
	@Override
	public void updateZombieSignText(String signture,Integer userId)throws Exception{
		if(signture != null && !signture.equals("")){
			userZombieDao.updateZombieSignText(signture, userId);
		}
	}
	
	/**
	 * 更新马甲的昵称
	 * @param userName
	 * @param userId
	 */
	@Override
	public void updateZombieUserName(String userName,Integer userId)throws Exception{
		if(userName != null && !userName.equals("")){
			userZombieDao.updateZombieUserName(userName, userId);
		}
	}
	
	/**
	 * 更新马甲的职业
	 * @param job
	 * @param userId
	 */
	@Override
	public void updateZombieJob(String job,Integer userId)throws Exception{
		if(job != null && !"".equals(job.trim())){
			userZombieDao.updateZombieJob(job, userId);
		}
	}
	
	/**
	 * 更新马甲的被赞总数
	 * @param likeMeCount
	 * @param userId
	 */
	@Override
	public void updateZombieLikeMeCount(Integer  likeMeCount,Integer userId)throws Exception{
		if(likeMeCount != null && likeMeCount >= 0){
			userZombieDao.updateZombieLikeMeCount(likeMeCount, userId);
		}
	}
	
	/**
	 * 更新马甲的地址
	 * @param address
	 * @param userId
	 */
	@Override
	public void updateZombieAddress(String address,Integer userId)throws Exception{
		if(address != null && !"".equals(address.trim())){
			userZombieDao.updateZombieAddress(address, userId);
		}
	}
	
	/**
	 * 更新省份和城市
	 * @param province
	 * @param city
	 * @param userId
	 */
	@Override
	public void updateZombieProvinceCity(String province,String city,Integer userId)throws Exception{
		if( (province != null && !"".equals(province.trim())) || 
			(city != null && !"".equals(city.trim()))	){
			userZombieDao.updateZombieProvinceCity(province, city, userId);
		}
	}
	
	/**
	 * 更新马甲信息
	 */
	@Override
	public void updateZombie(String rowJson)throws Exception{
		JSONArray jsArray = JSONArray.fromObject(rowJson);
		for(int i=0; i<jsArray.size(); i++){
			JSONObject jsObj = jsArray.getJSONObject(i);
			String userName = jsObj.optString("userName");
			String signture = jsObj.optString("signature");
			Integer userId  = jsObj.optInt("id");
			Integer sex 	= jsObj.optInt("sex");
			Integer likeMeCount = jsObj.optInt("likeMeCount");
			String job		= jsObj.optString("job");
			String province = jsObj.optString("province");
			String city		= jsObj.optString("city");
			if ( province == null){
				province = "";
			}
			if ( city == null ) {
				city = "";
			}
			String address	= province + " " + city;
			updateZombieSex(sex,userId);
			updateZombieUserName(userName,userId);
			updateZombieSignText(signture,userId);
			updateZombieAddress(address, userId);
			updateZombieLikeMeCount(likeMeCount, userId);
			updateZombieJob(job, userId);
			updateZombieProvinceCity(province, city, userId);
		}
	}

}

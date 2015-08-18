package com.imzhitu.admin.op.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hts.web.base.HTSException;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldLabel;
import com.hts.web.common.pojo.HTWorldLabelWorld;
import com.hts.web.common.pojo.OpActivity;
import com.hts.web.common.pojo.OpActivityAward;
import com.hts.web.common.pojo.OpActivityLogo;
import com.hts.web.common.pojo.OpActivitySponsor;
import com.hts.web.common.pojo.OpActivityWinner;
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.PushUtil;
import com.hts.web.common.util.StringUtil;
import com.hts.web.push.service.PushService;
import com.hts.web.push.service.impl.PushServiceImpl.PushFailedCallback;
import com.imzhitu.admin.common.pojo.OpActivityLogoDto;
import com.imzhitu.admin.common.pojo.OpActivityWorldCheckDto;
import com.imzhitu.admin.common.pojo.OpActivityWorldDto;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.interact.service.InteractActiveOperatedService;
import com.imzhitu.admin.op.dao.ActivityAwardDao;
import com.imzhitu.admin.op.dao.ActivityCacheDao;
import com.imzhitu.admin.op.dao.ActivityDao;
import com.imzhitu.admin.op.dao.ActivityLogoCacheDao;
import com.imzhitu.admin.op.dao.ActivityLogoDao;
import com.imzhitu.admin.op.dao.ActivitySponsorDao;
import com.imzhitu.admin.op.dao.ActivityStarCacheDao;
import com.imzhitu.admin.op.dao.ActivityWinnerDao;
import com.imzhitu.admin.op.dao.ActivityWorldDao;
import com.imzhitu.admin.op.dao.OpWorldTypeCacheDao;
import com.imzhitu.admin.op.dao.SquarePushTopicDao;
import com.imzhitu.admin.op.service.OpService;
import com.imzhitu.admin.ztworld.dao.HTWorldDao;
import com.imzhitu.admin.ztworld.dao.HTWorldLabelDao;
import com.imzhitu.admin.ztworld.dao.HTWorldLabelWorldDao;
import com.imzhitu.admin.ztworld.service.ZTWorldService;

@Service
public class OpServiceImpl extends BaseServiceImpl
		implements OpService {

	
	public static final String ACTIVITY_TIP_HEAD = "您的织图通过活动审核#";
	
	public static final String ACTIVITY_TIP_FOOT = "#";
	
	public static final String ACTIVITY_TIP_REJECT_HEAD = "您的织图不符合活动#";
	
	public static final String ACTIVITY_TIP_REJECT_FOOT = "#,很抱歉";
	
	/** 织图官方账号id */
	public static final Integer ZHITU_UID = 2063; // TODO 目前这个已经移植到ADMIN常量类中， 后续的调用要整改掉
	
	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private PushService pushService;
	
	@Autowired
	private com.hts.web.userinfo.service.UserMsgService webUserMsgService;
	
	@Autowired
	private com.hts.web.ztworld.dao.HTWorldDao webWorldDao;
	
	@Autowired
	private com.hts.web.userinfo.dao.UserInfoDao webUserInfoDao;
	
	@Autowired
	private com.hts.web.userinfo.service.UserInfoService webUserInfoService;
	
	@Autowired
	private SquarePushTopicDao squarePushTopicDao;
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private ActivityCacheDao activityCacheDao;
	
	@Autowired
	private ActivityWorldDao activityWorldDao;
	
	@Autowired
	private com.hts.web.operations.dao.ActivityCacheDao webActivityCacheDao;
	
	@Autowired
	private ZTWorldService worldService;
	
	@Autowired
	private HTWorldDao worldDao;
	
	@Autowired
	private ActivitySponsorDao activitySponsorDao;
	
	@Autowired
	private ActivityLogoDao activityLogoDao;
	
	@Autowired
	private ActivityLogoCacheDao activityLogoCacheDao;
	
	@Autowired
	private ActivityAwardDao activityAwardDao;
	
	@Autowired
	private OpWorldTypeCacheDao opWorldTypeCacheDao;
	
	@Autowired
	private HTWorldLabelDao worldLabelDao;
	
	@Autowired
	private HTWorldLabelWorldDao worldLabelWorldDao;
	
	@Autowired
	private com.hts.web.ztworld.dao.HTWorldLabelDao webWorldLabelDao;
	
	@Autowired
	private InteractActiveOperatedService interactActiveOperatedService;
	
	@Autowired
	private ActivityWinnerDao activityWinnerDao;
	
	@Autowired
	private ActivityStarCacheDao activityStarCacheDao;
	
	@Value("${admin.op.activityStarLimit}")
	private Integer activityStarLimit = 15;
	
	public Integer getActivityStarLimit() {
		return activityStarLimit;
	}

	public void setActivityStarLimit(Integer activityStarLimit) {
		this.activityStarLimit = activityStarLimit;
	}

	@Override
	public void buildActivity(int maxSerial, int start, int limit,
			Map<String, Object> jsonMap) throws Exception{
		buildSerializables("getSerial", maxSerial, start, limit, jsonMap, 
				new SerializableListAdapter<OpActivity>() {

					@Override
					public List<OpActivity> getSerializables(
							RowSelection rowSelection) {
						return activityDao.queryActivity(rowSelection);
					}

					@Override
					public List<OpActivity> getSerializableByMaxId(int maxId,
							RowSelection rowSelection) {
						return activityDao.queryActivity(maxId, rowSelection);
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return activityDao.queryActivityTotal(maxId);
					}
				}, OptResult.ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_SERIAL);
	}

	@Override
	public void saveActivity(Integer labelId, String titlePath, String titleThumbPath, String channelPath,
			String activityName, String activityTitle, String activityDesc, String activityLink,
			String activityLogo, Date activityDate, Date deadline, Integer commercial, 
			String shareTitle, String shareDesc, String sponsorIds, Integer valid) throws Exception {
		
		Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_SQUARE_ACTIVITY_ID);
		OpActivity activity = new OpActivity(labelId, titlePath,
				null, channelPath, activityName, activityTitle, activityDesc,activityLink, activityLogo, activityDate, deadline,
				Tag.SQUARE_ACTIVITY_NORMAL, labelId, commercial, shareTitle, shareDesc, valid, serial);
		
		activityDao.saveActivity(activity);
		if(!StringUtil.checkIsNULL(sponsorIds)) {
			Integer[] sids = StringUtil.convertStringToIds(sponsorIds);
			for(Integer sid : sids) {
				activitySponsorDao.saveSponsor(labelId, sid);
			}
		}
		worldLabelDao.updateLabelState(labelId, Tag.TRUE);
	}
	
	@Override
	public OpActivity queryActivityById(Integer id) throws Exception {
		OpActivity act = activityDao.queryActivityById(id);
		List<OpActivitySponsor> sponsors = activitySponsorDao.querySponsor(id);
		act.setSponsors(sponsors);
		return act;
	}

	@Override
	public void updateActivity(Integer id, String titlePath,
			String titleThumbPath, String channelPath, String activityName, String activityTitle,
			String activityDesc, String activityLink, 
			String activityLogo, Date activityDate, Date deadline, Integer commercial, 
			String shareTitle, String shareDesc, String sponsorIds, Integer valid) throws Exception {
		activityDao.updateActivity(new OpActivity(id, titlePath, titleThumbPath, channelPath,
				activityName, activityTitle, activityDesc, activityLink, activityLogo, 
				activityDate, deadline, Tag.SQUARE_ACTIVITY_NORMAL, id, commercial, shareTitle, shareDesc, valid));
		activitySponsorDao.deleteByActivityId(id);
		if(!StringUtil.checkIsNULL(sponsorIds)) {
			Integer[] sids = StringUtil.convertStringToIds(sponsorIds);
			for(Integer sid : sids) {
				activitySponsorDao.saveSponsor(id, sid);
			}
		}
	}
	
	@Override
	public void deleteActivityById(Integer id) throws Exception {
		activityDao.deleteById(id);
		activitySponsorDao.deleteByActivityId(id);
		worldLabelDao.updateLabelState(id, Tag.WORLD_LABEL_NORMAL);
	}


	@Override
	public void buildCacheActivity(Map<String, Object> jsonMap) {
		List<OpActivity> list = webActivityCacheDao.queryActivity();
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
		jsonMap.put(OptResult.TOTAL, list.size());
	}
	
	@Override
	public void buildActivityWorld(final Integer maxId, final Integer activityId, Integer valid,
			Integer weight, Integer worldId,Integer userId,String userName, final Integer isWinner, int page, int rows,
			final Map<String, Object> jsonMap) throws Exception {
		final LinkedHashMap<String, Object> attrMap = new LinkedHashMap<String, Object>();
		if((isWinner == null || isWinner.equals(Tag.FALSE)) && valid != null) {
			attrMap.put("valid", valid);
		} 
		if(weight != null) {
			attrMap.put("weight", weight);
		}
		if(worldId != null) {
			attrMap.put("world_id", worldId);
		}
		if(userId != null){
			attrMap.put("author_id", userId);
		}
		if(userName != null && !userName.trim().equals("")){
			attrMap.put("user_name","%"+userName+"%");
		}
		
		if(isWinner == null || isWinner.equals(Tag.FALSE)) {
			buildActivityWorld(maxId, activityId, page, rows, jsonMap, attrMap);
		} else {
			buildSerializables("getSerial", maxId, page, rows, jsonMap, new SerializableListAdapter<OpActivityWorldDto>() {

				@Override
				public List<OpActivityWorldDto> getSerializables(
						RowSelection rowSelection) {
					List<OpActivityWorldDto> list = activityWinnerDao.queryWinner(activityId, attrMap, rowSelection);
					webUserInfoService.extractVerify(list);
					return list;
				}

				@Override
				public List<OpActivityWorldDto> getSerializableByMaxId(
						int maxId, RowSelection rowSelection) {
					List<OpActivityWorldDto> list = activityWinnerDao.queryWinner(maxId, activityId, attrMap, rowSelection);
					webUserInfoService.extractVerify(list);
					return list;
				}

				@Override
				public long getTotalByMaxId(int maxId) {
					return activityWinnerDao.queryWinnerCount(maxId, activityId, attrMap);
				}
			
			}, OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);
		}
		
	}
	
	/**
	 * 构建活动织图列表
	 * 
	 * @param maxId
	 * @param activityId
	 * @param valid
	 * @param weight
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @param attrMap
	 * @throws Exception
	 */
	private void buildActivityWorld(final Integer maxId, final Integer activityId, int page, int rows,
			final Map<String, Object> jsonMap, final LinkedHashMap<String, Object> attrMap) throws Exception {
		
			buildSerializables("getSerial", maxId, page, rows, jsonMap, new SerializableListAdapter<OpActivityWorldDto>() {
				
				@Override
				public List<OpActivityWorldDto> getSerializables(
						RowSelection rowSelection) {
					List<OpActivityWorldDto> list = activityWorldDao.queryActivityWorldDto(activityId, attrMap, rowSelection);
					webUserInfoService.extractVerify(list);
					return list;
				}
				
				@Override
				public List<OpActivityWorldDto> getSerializableByMaxId(int maxId,
						RowSelection rowSelection) {
					List<OpActivityWorldDto> list = activityWorldDao.queryActivityWorldDto(maxId, activityId, attrMap, rowSelection);
					webUserInfoService.extractVerify(list);
					return list;
				}
				
				@Override
				public long getTotalByMaxId(int maxId) {
					return activityWorldDao.queryActivityWorldCount(maxId, activityId, attrMap);
				}
				
			}, OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);
		
	}
	
	@Override
	public void updateActivityWorldValid(Integer valid, Integer activityWorldId, Integer activityId,
			Integer worldId,Integer authorId, String authorName, String notifyTip)
			throws Exception {
		worldLabelWorldDao.updateLabelWorldValid(activityWorldId, valid);
		Integer count = webWorldLabelDao.queryWorldCount(activityId);
		if(valid.equals(Tag.TRUE)) {
			webWorldLabelDao.updateWorldCount(activityId, ++count);
			Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_WORLD_ID);
			worldLabelWorldDao.updateLabelWorldSerial(activityWorldId, serial);
		}
		if(StringUtil.checkIsNULL(notifyTip)) {
			throw new HTSException("提示不能为空");
		}
		activityStarCacheDao.updateStar(activityId, activityStarLimit);
//		addActivityWorldCheckMsg(activityId, worldId, authorId, authorName, notifyTip);
	}
	
	@Override
	public void updateActivityWorldValids(String idsStr, Integer valid)
			throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		Integer activityOperacted = valid == 1 ? Tag.TRUE : Tag.FALSE;
		if(ids.length > 0) {
			List<OpActivityWorldCheckDto> checkList = activityWorldDao.queryWorldCheck(ids);
			for(OpActivityWorldCheckDto check : checkList) {
				String tip = null;
				if(valid.equals(Tag.TRUE))
					tip = ACTIVITY_TIP_HEAD + check.getActivityName() + ACTIVITY_TIP_FOOT;
				else
					tip = ACTIVITY_TIP_REJECT_HEAD + check.getActivityName() + ACTIVITY_TIP_REJECT_FOOT;
				updateActivityWorldValid(valid,
						check.getId(),
						check.getActivityId(), 
						check.getWorldId(), 
						check.getUserId(),
						check.getUserName(),
						tip);
				activityStarCacheDao.updateStar(check.getActivityId(), activityStarLimit);
				interactActiveOperatedService.addOperated(check.getWorldId(), activityOperacted);//设置为被操作过。
				
			}
		}
	}

	
	@Override
	public void addActivityWorldCheckMsg(Integer activityId, Integer worldId, 
			Integer recipientId, String recipientName, String msg) throws Exception {
		Integer msgId = webUserMsgService.getValidMessageId(ZHITU_UID, recipientId, 
				Tag.USER_MSG_SQUARE_ACTIVITY_NOTIFY, worldId, String.valueOf(activityId));
		if(msgId == null) {
			String tip = recipientName + "," + msg;
			String shortTip = PushUtil.getShortName(recipientName) + "," + PushUtil.getShortTip(msg);
			HTWorld world = webWorldDao.queryWorldById(worldId);
			webUserMsgService.saveSysMsg(ZHITU_UID, recipientId, tip, 
					Tag.USER_MSG_SQUARE_ACTIVITY_NOTIFY, worldId, null,
					String.valueOf(activityId), world.getTitleThumbPath(), 0);
			UserPushInfo userPushInfo = webUserInfoDao.queryUserPushInfoById(recipientId);
			pushService.pushSysMessage(shortTip, ZHITU_UID, tip, userPushInfo, Tag.USER_MSG_SQUARE_ACTIVITY_NOTIFY,
					new PushFailedCallback() {

				@Override
				public void onPushFailed(Exception e) {}
			});
		}
	}

	@Override
	public void addActivityWorld(Integer worldId, String activityIdsStr) throws Exception {
		Integer[] activityIds = StringUtil.convertStringToIds(activityIdsStr);
		Map<Integer, HTWorldLabel> labelMap = worldLabelDao.queryLabelMap(activityIds);
		for(Integer activityId : activityIds) {
			HTWorldLabelWorld hw = worldLabelWorldDao.queryLabelWorld(worldId, activityId);
			if(hw != null) {
				if(hw.getValid().equals(Tag.FALSE)) { 
					String actName = labelMap.get(activityId).getLabelName();
					String notifyTip = ACTIVITY_TIP_HEAD + actName + ACTIVITY_TIP_FOOT;
					com.imzhitu.admin.common.pojo.UserInfo userInfo = worldDao.queryAuthorInfoByWorldId(worldId);
					updateActivityWorldValid(Tag.TRUE, hw.getId(), hw.getLabelId(), worldId, 
							userInfo.getId(), userInfo.getUserName(), notifyTip);
				}
			} else {
				// 直接推荐织图到活动
				Integer lwid = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_WORLD_ID);
				UserInfo userInfo = worldDao.queryAuthorInfoByWorldId(worldId);
				worldLabelWorldDao.saveLabelWorld(new HTWorldLabelWorld(lwid, worldId, userInfo.getId(), 
						activityId, Tag.TRUE, lwid, 0));
				Integer count = webWorldLabelDao.queryWorldCount(activityId);
				webWorldLabelDao.updateWorldCount(activityId, ++count);
			}
			activityStarCacheDao.updateStar(activityId, activityStarLimit);
		}
		interactActiveOperatedService.addOperated(worldId, Tag.TRUE);//设置为被操作过。
	}

	@Override
	public void buildActivityLogo(Integer maxSerial, int start,
			int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables("getSerial", maxSerial, start, limit, jsonMap, 
				new SerializableListAdapter<OpActivityLogoDto>() {

					@Override
					public List<OpActivityLogoDto> getSerializables(
							RowSelection rowSelection) {
						return activityLogoDao.queryLogoDto(rowSelection);
					}

					@Override
					public List<OpActivityLogoDto> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						return activityLogoDao.queryLogoDto(maxId, rowSelection);
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return activityLogoDao.queryLogoCount(maxId);
					}
					
		}, OptResult.JSON_KEY_ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_SERIAL);
		
	}
	
	@Override
	public void saveActivityLogo(Integer activityId, String logoPath)
			throws Exception {
		Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_ACTIVITY_LOGO_SERIAL);
		activityLogoDao.saveLogo(new OpActivityLogo(activityId, logoPath, serial, Tag.FALSE));
	}

	@Override
	public void deleteActivityLogo(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		activityLogoDao.deleteByIds(ids);
	}

	@Override
	public void updateActivityLogoValid(String idsStr, Integer valid)
			throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		activityLogoDao.updateValidByIds(ids, valid);
		activityLogoCacheDao.updateLogoCache();
	}

	@Override
	public void updateActivityLogoSerial(String[] idStrs) throws Exception {
		for(int i = idStrs.length - 1; i >= 0; i--) {
			String idStr = idStrs[i];
			if(idStr != null && idStr != "") {
				int id = Integer.parseInt(idStrs[i]);
				Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_ACTIVITY_LOGO_SERIAL);
				activityLogoDao.updateSeria(id, serial);
			}
		}
		activityLogoCacheDao.updateLogoCache();
	}
	
	@Override
	public void buildActivityAward(int maxSerial, int start, int limit, final Integer activityId,  
			Map<String, Object> jsonMap) throws Exception {
		buildSerializables("getSerial", maxSerial, start, limit, jsonMap, 
				new SerializableListAdapter<OpActivityAward>() {

					@Override
					public List<OpActivityAward> getSerializables(
							RowSelection rowSelection) {
						return activityAwardDao.queryAward(activityId, rowSelection);
					}

					@Override
					public List<OpActivityAward> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						return activityAwardDao.queryAward(maxId, activityId, rowSelection);
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return activityAwardDao.queryAwardCount(maxId, activityId);
					}
		}, OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_SERIAL);
	}
	
	@Override
	public void buildActivityAward(Integer activityId,  
			Map<String, Object> jsonMap) throws Exception {
		List<OpActivityAward> list = activityAwardDao.queryAward(activityId);
		jsonMap.put(OptResult.ROWS, list);
		jsonMap.put(OptResult.TOTAL, list.size());
		
	}

	@Override
	public void saveActivityAward(Integer activityId, String iconThumbPath, String iconPath,
			String awardName, String awardDesc, Double price, String awardLink, Integer total,
			Integer remain) throws Exception {
		if(remain == null) {
			remain = total;
		}
		Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_ACTIVITY_AWARD_SERIAL);
		activityAwardDao.saveAward(new OpActivityAward(activityId, iconThumbPath, 
				iconPath, awardName, awardDesc, price, awardLink, total, remain, serial));
	}
	
	@Override
	public void updateActivityAward(Integer id, Integer activityId, String iconThumbPath, 
			String iconPath, String awardName, String awardDesc, Double price,
			String awardLink, Integer total, Integer remain, Integer serial) throws Exception {
		activityAwardDao.updateAward(
				new OpActivityAward(id, activityId, iconThumbPath, iconPath, awardName, awardDesc,
						price,awardLink,
						total, remain, serial));
	}


	@Override
	public void deleteActivityAward(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		activityAwardDao.deleteAward(ids);
	}
	
	@Override
	public void updateActivityAwardSerial(String[] idStrs) throws Exception {
		for(int i = idStrs.length - 1; i >= 0; i--) {
			String idStr = idStrs[i];
			if(idStr != null && idStr != "") {
				int id = Integer.parseInt(idStrs[i]);
				Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_ACTIVITY_AWARD_SERIAL);
				activityAwardDao.updateSerial(id, serial);
			}
		}
	}
	
	@Override
	public OpActivityAward getActivityAwardById(Integer id) throws Exception {
		return activityAwardDao.queryAwardById(id);
	}
	
	@Override
	public void updateOpWorldType() throws Exception {
		opWorldTypeCacheDao.updateCacheType();
	}

	@Override
	public void saveActivityWinner(Integer activityId, Integer worldId, Integer userId,
			Integer awardId) throws Exception {
		Integer id = webKeyGenService.generateId(KeyGenServiceImpl.OP_ACTIVITY_WINNER_ID);
		activityWinnerDao.saveWinner(new OpActivityWinner(id, activityId, worldId, 
				userId, awardId, id, 0));
	}

	@Override
	public void deleteActivityWinner(Integer activityId, Integer worldId)
			throws Exception {
		activityWinnerDao.deleteWinner(activityId, worldId);
	}
	
	@Override
	public Integer queryActivityWinnerAwardId(Integer activityId,
			Integer worldId) throws Exception {
		return activityWinnerDao.queryAwardId(activityId, worldId);
	}
	
	@Override
	public void updateActivityWinnerAward(Integer activityId, Integer worldId,
			Integer awardId) throws Exception {
		activityWinnerDao.updateAwardId(activityId, worldId,awardId);
	}
}

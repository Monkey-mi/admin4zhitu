package com.imzhitu.admin.interact.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.hts.web.base.HTSException;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.Log;
import com.hts.web.common.util.NumberUtil;
import com.hts.web.common.util.StringUtil;
import com.hts.web.push.service.impl.PushServiceImpl.PushFailedCallback;
import com.hts.web.push.yunba.YunbaException;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.InteractTracker;
import com.imzhitu.admin.common.pojo.InteractUser;
import com.imzhitu.admin.common.pojo.InteractUserFollow;
import com.imzhitu.admin.common.pojo.InteractWorld;
import com.imzhitu.admin.common.pojo.InteractWorldClick;
import com.imzhitu.admin.common.pojo.InteractWorldComment;
import com.imzhitu.admin.common.pojo.InteractWorldCommentDto;
import com.imzhitu.admin.common.pojo.InteractWorldLiked;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.common.service.KeyGenService;
import com.imzhitu.admin.interact.dao.InteractCommentDao;
import com.imzhitu.admin.interact.dao.InteractTrackerDao;
import com.imzhitu.admin.interact.dao.InteractUserDao;
import com.imzhitu.admin.interact.dao.InteractUserFollowDao;
import com.imzhitu.admin.interact.dao.InteractWorldClickDao;
import com.imzhitu.admin.interact.dao.InteractWorldCommentDao;
import com.imzhitu.admin.interact.dao.InteractWorldDao;
import com.imzhitu.admin.interact.dao.InteractWorldLikedDao;
import com.imzhitu.admin.interact.service.InteractCommentService;
import com.imzhitu.admin.interact.service.InteractWorldService;
import com.imzhitu.admin.op.dao.UserZombieDao;
import com.imzhitu.admin.op.service.OpUserService;
import com.imzhitu.admin.op.service.impl.OpServiceImpl;
import com.imzhitu.admin.ztworld.dao.HTWorldDao;

//@Service
public class InteractWorldServiceImpl extends BaseServiceImpl implements
		InteractWorldService{
	
	private static Logger logger = Logger.getLogger(InteractWorldServiceImpl.class);
	
	/**
	 * 最早工作时间
	 */
	public static final int MIN_WORKING_HOUR = 0;
	
	/**
	 * 最晚工作时间
	 */
	public static final int MAX_WORKING_HOUR = 24;
	
	/**
	 * 工作时长
	 */
	public static final int WORKING_HOUR = (MAX_WORKING_HOUR>MIN_WORKING_HOUR?MAX_WORKING_HOUR:MAX_WORKING_HOUR+24) - MIN_WORKING_HOUR;
	
	/**
	 * 工作时长 分钟
	 */
	public static final int WORKING_MINUTE = WORKING_HOUR * 60;
	
	
	/**
	 * 工作时长，毫秒级
	 */
	public static final int WORKING_TIME = WORKING_HOUR*60*60*1000;
	
	/**
	 * 工作频率：分钟
	 */
	public static final int WORKING_INTERVAL = 5;
	/**
	 * 工作频率，毫秒级
	 */
	public static final int WORKING_INTERVAL_MILLISECOND = WORKING_INTERVAL*60*1000;
	/**
	 * 不工作时长，毫秒级
	 */
	public static final int UNWORKING_TIME_MILLISECOND = 60*60*1000*(MAX_WORKING_HOUR>MIN_WORKING_HOUR?MIN_WORKING_HOUR+24-MAX_WORKING_HOUR:MIN_WORKING_HOUR-MAX_WORKING_HOUR);
	
	public static final int CLICK_RATE = 30; // 播放速度
	
	public static final int INTERACT_TYPE_CLICK = 1;
	public static final int INTERACT_TYPE_COMMENT = 2;
	public static final int INTERACT_TYPE_LIKED = 3;
	public static final int INTERACT_TYPE_FOLLOW = 4;
	
	public Integer trackInterval = 20; // 跟踪频率
	
	@Value("${admin.adminId}")
	private Integer adminId = 485; // 管理员用户id
	
	@Value("${admin.interact.likedToFollowRate}")
	private Integer likeToFollowRate;//点赞的非粉丝僵尸用户转成粉丝的转化率

	@Value("${admin.interact.commentFromFollowRate}")
	private Integer commentFromFollowRate;//评论者来至于僵尸粉的比例

	@Value("${admin.interact.likedFromFollowRate}")
	private Integer likeFromFollowRate;//点赞者来至于僵尸粉的比例
	
	@Value("${admin.interact.minConcernCount}")
	private Integer minConcernCount;
	
	@Value("${admin.interact.maxConcernCount}")
	private Integer maxConcernCount;

	@Autowired
	private KeyGenService keyGenService;
	
	@Autowired
	private OpUserService opUserService;
	
	@Autowired
	private InteractWorldLikedDao interactWorldLikedDao;
	
	@Autowired
	private InteractWorldCommentDao interactWorldCommentDao;
	
	@Autowired
	private InteractWorldClickDao interactWorldClickDao;
	
	@Autowired
	private InteractWorldDao interactWorldDao;
	
	@Autowired
	private InteractCommentService interactCommentService;
	
	@Autowired
	private InteractCommentDao interactCommentDao;
	
	@Autowired
	private InteractUserDao interactUserDao;
	
	@Autowired
	private InteractUserFollowDao interactUserFollowDao;
	
	@Autowired
	private InteractTrackerDao interactTrackerDao;
	
	@Autowired
	private HTWorldDao worldDao;
	
	@Autowired
	private UserZombieDao userZombieDao;
	
	@Autowired
	private com.hts.web.ztworld.service.ZTWorldInteractService webWorldInteractService;
	
	@Autowired
	private com.hts.web.ztworld.service.ZTWorldService webWorldService;
	
	@Autowired
	private com.hts.web.userinfo.service.UserInteractService webUserInteractService;
	
	@Autowired
	private com.hts.web.userinfo.service.UserMsgService webUserMsgService;
	
	@Autowired
	private com.hts.web.userinfo.dao.UserInfoDao webUserInfoDao;
	
	@Autowired
	private com.hts.web.push.service.PushService pushService;
	
	
	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Integer getLikeToFollowRate() {
		return likeToFollowRate;
	}

	public void setLikeToFollowRate(Integer likeToFollowRate) {
		this.likeToFollowRate = likeToFollowRate;
	}

	public Integer getCommentFromFollowRate() {
		return commentFromFollowRate;
	}

	public void setCommentFromFollowRate(Integer commentFromFollowRate) {
		this.commentFromFollowRate = commentFromFollowRate;
	}

	public Integer getLikeFromFollowRate() {
		return likeFromFollowRate;
	}

	public void setLikeFromFollowRate(Integer likeFromFollowRate) {
		this.likeFromFollowRate = likeFromFollowRate;
	}
	
	public Integer getMinConcernCount() {
		return minConcernCount;
	}

	public void setMinConcernCount(Integer minConcernCount) {
		this.minConcernCount = minConcernCount;
	}

	public Integer getMaxConcernCount() {
		return maxConcernCount;
	}

	public void setMaxConcernCount(Integer maxConcernCount) {
		this.maxConcernCount = maxConcernCount;
	}

	@Override
	public void buildInteracts(Integer worldId, int maxId, int start, int limit, Map<String, Object> jsonMap) throws Exception{
		final Map<String, Object> attrMap = new LinkedHashMap<String, Object>();
		if(worldId != null && worldId != 0) {
			attrMap.put("world_id", worldId);
		}
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<InteractWorld>() {

			@Override
			public List<InteractWorld> getSerializables(
					RowSelection rowSelection) {
				return interactWorldDao.queryInteract(rowSelection, attrMap);
			}

			@Override
			public List<InteractWorld> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return interactWorldDao.queryInteract(maxId, rowSelection, attrMap);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return interactWorldDao.queryInteractTotal(maxId, attrMap);
			}

		}, OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);
	}
	
	@Override
	public void buildInteractByWorldIds(String idsStr, Map<String, Object> jsonMap) throws Exception {
		Integer[] worldIds = StringUtil.convertStringToIds(idsStr);
		for(Integer worldId : worldIds) {
			jsonMap.put(String.valueOf(worldId), Tag.FALSE);
		}
		List<Integer> worldIdList = interactWorldDao.queryWorldIdByWIDs(worldIds);
		for(Integer id : worldIdList) {
			jsonMap.put(String.valueOf(id), Tag.TRUE);
		}
	}

	@Override
	public void buildComments(final Integer interactId, int maxId, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<InteractWorldCommentDto>() {

			@Override
			public List<InteractWorldCommentDto> getSerializables(
					RowSelection rowSelection) {
				return interactWorldCommentDao.queryComment(interactId, rowSelection);
			}

			@Override
			public List<InteractWorldCommentDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return interactWorldCommentDao.queryComment(interactId, maxId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return interactWorldCommentDao.queryCommentTotal(interactId, maxId);
			}
			
		}, OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);
		
	}

	@Override
	public void buildLikeds(final Integer interactId, int maxId, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<InteractWorldLiked>() {

			@Override
			public List<InteractWorldLiked> getSerializables(
					RowSelection rowSelection) {
				return interactWorldLikedDao.queryLiked(interactId, rowSelection);
			}

			@Override
			public List<InteractWorldLiked> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return interactWorldLikedDao.queryLiked(interactId, maxId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return interactWorldLikedDao.queryLikedTotal(interactId, maxId);
			}
			
		}, OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);
	}

	@Override
	public void buildClicks(final Integer interactId, int maxId, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<InteractWorldClick>() {

			@Override
			public List<InteractWorldClick> getSerializables(
					RowSelection rowSelection) {
				return interactWorldClickDao.queryClick(interactId, rowSelection);
			}

			@Override
			public List<InteractWorldClick> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return interactWorldClickDao.queryClick(interactId, maxId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return interactWorldClickDao.queryClickTotal(interactId, maxId);
			}
			
		}, OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);
		
	}

	@Override
	public void saveInteract(Integer worldId, Integer clickCount,
			Integer likedCount, String[] commentIds, Integer duration) throws Exception {
		
		duration = duration == null ? 0 : duration; 
		clickCount = clickCount == null ? 0 : clickCount;
		likedCount = likedCount == null ? 0 : likedCount;
		int commentCount = commentIds == null ? 0 : commentIds.length;
		
		Integer interactId = 0;
		Date dateAdded = new Date();
		InteractWorld interact = interactWorldDao.queryInteractByWorldId(worldId);
		
		if(interact != null) {
			interactId = interact.getId();
			interactWorldDao.updateInteract(
					interactId, 
					interact.getClickCount()+clickCount,
					interact.getCommentCount()+commentCount,
					interact.getLikedCount()+likedCount,
					interact.getDuration()+duration);
		} else {
			interactId = keyGenService.generateId(Admin.KEYGEN_INTERACT_WORLD_ID);
			interactWorldDao.saveInteract(new InteractWorld(interactId, worldId, clickCount,
					commentCount, likedCount, duration,
					dateAdded, Tag.TRUE));
		}
		
		// 保存播放
		if(clickCount > 0) {
			List<Integer> countList = getScheduleCount(clickCount, duration);
			List<Date> scheduleDateList = getScheduleDates(dateAdded, duration, countList.size());
			for(int i = 0; i < countList.size(); i++) {
				saveClick(interactId, worldId, countList.get(i), dateAdded, scheduleDateList.get(i));
			}
		}
		
		// 保存评论
		if(commentCount > 0) {
			String idStr = Arrays.toString(commentIds);
			Integer[] cids = StringUtil.convertStringToIds(idStr.substring(1, idStr.length() - 1));
			List<Date> scheduleDateList = getScheduleDates(dateAdded, duration, cids.length);
			List<Integer> uids = opUserService.getRandomUserZombieIds(cids.length);
			for(int i = 0; i < cids.length; i++) {
				saveComment(interactId, worldId, uids.get(i), cids[i], dateAdded,scheduleDateList.get(i));
			}
		}
		
		// 保存喜欢
		if(likedCount > 0) {
			List<Integer> uids = opUserService.getRandomUserZombieIds(likedCount);
			List<Date> scheduleDateList = getScheduleDates(dateAdded, duration, likedCount);
			for(int i = 0; i < uids.size(); i++) {
				saveLiked(interactId, worldId, uids.get(i), dateAdded, scheduleDateList.get(i));
			}
		}
	
	}
	
	@Override
	public void saveInteractV2(Integer worldId, Integer clickCount,
			Integer likedCount, String[] commentIds, Integer minuteDuration) throws Exception {
		
		minuteDuration = minuteDuration == null ? 0 : minuteDuration; 
		clickCount = clickCount == null ? 0 : clickCount;
		likedCount = likedCount == null ? 0 : likedCount;
		int commentCount = commentIds == null ? 0 : commentIds.length;
		
		Integer interactId = 0;
		Date dateAdded = new Date();
		InteractWorld interact = interactWorldDao.queryInteractByWorldId(worldId);
		UserInfo userInfo = worldDao.queryAuthorInfoByWorldId(worldId);
		if(interact != null) {
			interactId = interact.getId();
			interactWorldDao.updateInteract(
					interactId, 
					interact.getClickCount()+clickCount,
					interact.getCommentCount()+commentCount,
					interact.getLikedCount()+likedCount,
					interact.getDuration());
		} else {
			interactId = keyGenService.generateId(Admin.KEYGEN_INTERACT_WORLD_ID);
			interactWorldDao.saveInteract(new InteractWorld(interactId, worldId, clickCount,
					commentCount, likedCount, minuteDuration/60>0?minuteDuration/60:1,//若不足一个钟，就按一个钟来计算
					dateAdded, Tag.TRUE));
		}
		
		// 保存播放
		if(clickCount > 0) {
			List<Integer> countList = getScheduleCountV2(clickCount, minuteDuration);
			List<Date> scheduleDateList = getScheduleDatesV2(dateAdded, minuteDuration, countList.size());
			for(int i = 0; i < countList.size(); i++) {
				saveClick(interactId, worldId, countList.get(i), dateAdded, scheduleDateList.get(i));
			}
		}
		
		//获取非粉僵尸用户、和用户僵尸粉所需总数
		//因为*Rate是整数，范围在0-100代表0%-100%
		//
		long hasFollowZombies = userZombieDao.queryZombieFollowCountByUserIdForInteractForInteract(userInfo.getId(),worldId);
		int followZombiesTotal = likedCount * likeFromFollowRate + likedCount * ( 100 -  likeFromFollowRate)* likeToFollowRate
							> commentCount * commentFromFollowRate ? likedCount * likeFromFollowRate : commentCount * commentFromFollowRate;
		followZombiesTotal = followZombiesTotal % 100 == 0 ? followZombiesTotal / 100 : followZombiesTotal / 100 +1;
		followZombiesTotal = (int)(followZombiesTotal> hasFollowZombies ? hasFollowZombies : followZombiesTotal);
		int unFollowZombiesTotal = likedCount > commentCount ? likedCount - followZombiesTotal : commentCount - followZombiesTotal;
		List<Integer> fzList =null;
		int fzListLength=0;
		try{
			if(followZombiesTotal >0 ){
				fzList = opUserService.getRandomFollowUserZombieIds(userInfo.getId(),worldId, 1, followZombiesTotal);
				fzListLength = fzList.size();
			}else{
				fzListLength = 0;
			}
		}catch(IndexOutOfBoundsException e){
			logger.info(e.getMessage());
		}
		List<Integer> unFzList = opUserService.getRandomUnFollowUserZombieIds(userInfo.getId(),worldId, followZombiesTotal+unFollowZombiesTotal-fzListLength);
		
		// 保存喜欢
		if(likedCount > 0) {
			
			List<Date> scheduleDateList = getScheduleDatesV2(dateAdded, minuteDuration, likedCount);
			int likeSize = likedCount * likeFromFollowRate % 100 == 0 ? likedCount * likeFromFollowRate / 100 : likedCount * likeFromFollowRate / 100 +1;
			int i,j;
			for(i = 0; i < likeSize && i < fzListLength; i++) {
				saveLiked(interactId, worldId, fzList.get(i), dateAdded, scheduleDateList.get(i));
			}
			for(j = 0; j < likedCount - i && j < unFzList.size(); j++){
				saveLiked(interactId, worldId, unFzList.get(j), dateAdded, scheduleDateList.get(i+j));
			}
		}
		
		// 保存评论
		if(commentCount > 0) {
			String idStr = Arrays.toString(commentIds);
			Integer[] cids = StringUtil.convertStringToIds(idStr.substring(1, idStr.length() - 1));
			List<Date> scheduleDateList = getScheduleDatesV2(dateAdded, minuteDuration, cids.length);
			int followCommentSize = commentCount * commentFromFollowRate % 100 == 0 ? commentCount * commentFromFollowRate / 100 : commentCount * commentFromFollowRate / 100 + 1;
			int i,j;
			for( i = 0; i < followCommentSize && i < fzListLength ; i++) {
				saveComment(interactId, worldId, fzList.get(i), cids[i], dateAdded,scheduleDateList.get(i));
			}
			for(j = 0; j < commentCount - i && j < unFzList.size(); j++){
				saveComment(interactId, worldId, unFzList.get(j), cids[i+j], dateAdded, scheduleDateList.get(i+j));
			}
		}
		
		//加粉
		if(likedCount > 0){
			int followSize = likedCount * likeToFollowRate % 100 == 0 ? likedCount * likeToFollowRate / 100 : likedCount * likeToFollowRate / 100 + 1;
			InteractUser userInteract = interactUserDao.queryUserInteractByUID(userInfo.getId());
			if(userInteract != null) {
				interactId = userInteract.getId();
				// 更新用户互动
				interactUserDao.updateInteract(interactId, followSize+userInteract.getFollowCount(),
						(minuteDuration/60>0?minuteDuration/60:1)+userInteract.getDuration());
			} else {
				interactId = keyGenService.generateId(Admin.KEYGEN_INTERACT_USER_ID);
				// 保存用户互动
				interactUserDao.saveInteract(new InteractUser(interactId, 
						userInfo.getId(), followSize, minuteDuration/60>0?minuteDuration/60:1, dateAdded, Tag.TRUE));
			}
			
			// 保存粉丝互动
			
			List<Date> scheduleDateList = getScheduleDatesV2(dateAdded, minuteDuration, followSize);
			for(int i = 0; i < followSize && i < unFzList.size(); i++) {
				interactUserFollowDao.saveFollow(new InteractUserFollow(interactId, userInfo.getId(), unFzList.get(i),
						dateAdded, scheduleDateList.get(i), Tag.TRUE, Tag.FALSE));
			}
		}
	}
	
	/**
	 * 第二版本的时间调度算法，主要是使用分钟来进行调度
	 * @param start
	 * @param minuteDuration
	 * @param size
	 * @return
	 * @author zxx
	 */
	@Override
	public List<Date> getScheduleDatesV2(Date start,int minuteDuration,int size  ){
		List<Date> list = new ArrayList<Date>();
		//计算正确的开始时间，若是当前时间为不工作时间，则开始时间为最早开始时间
//		Calendar ca = Calendar.getInstance(Locale.CHINA);
//		ca.setTime(start);
//		int hour = ca.get(Calendar.HOUR_OF_DAY);
//		if(MAX_WORKING_HOUR > MIN_WORKING_HOUR){
//			if(!(hour>=MIN_WORKING_HOUR && hour<=MAX_WORKING_HOUR)){
//				ca.set(Calendar.HOUR_OF_DAY, MIN_WORKING_HOUR);
//			}
//		}else{
//			if(hour < MIN_WORKING_HOUR && hour > MAX_WORKING_HOUR) {
//				ca.set(Calendar.HOUR_OF_DAY, MIN_WORKING_HOUR);
//			}
//		}
//		long interval = (long)((double)minuteDuration / (double)WORKING_MINUTE * WORKING_TIME / size);
//		long interval = (long)((double)minuteDuration / (double)WORKING_MINUTE * WORKING_TIME / size);
//		long startTime = ca.getTimeInMillis();
		long interval = (long)((double)minuteDuration *60*1000 / size);
		long startTime = start.getTime();
		for(int i = 0; i < size; i++) {
			long nextTime = (long)(startTime + Math.random() * interval);
			Date nextDate = getCorrectDate(new Date(nextTime));
			
			long tmpTime = (long)(startTime + interval);
			startTime = getCorrectDate(new Date(tmpTime)).getTime();
			list.add(nextDate);
		}
		return list;
	}
	
	@Override
	public void saveComment(Integer interactId, Integer worldId, Integer userId, Integer commentId,
			Date dateAdded, Date dateShedule) throws Exception {
		interactWorldCommentDao.saveComment(new InteractWorldComment(interactId, worldId,userId,commentId,dateAdded,dateShedule, Tag.TRUE, Tag.FALSE));
	}

	@Override
	public void saveLiked(Integer interactId, Integer worldId, Integer userId, Date dateAdded,
			Date dateShedule) throws Exception {
		interactWorldLikedDao.saveLiked(new InteractWorldLiked(interactId, worldId,userId,dateAdded,dateShedule, Tag.TRUE, Tag.FALSE));
	}

	@Override
	public void saveClick(Integer interactId, Integer worldId, Integer clickCount, Date dateAdded,
			Date dateShedule) throws Exception {
		interactWorldClickDao.saveClick(new InteractWorldClick(interactId, worldId,clickCount,dateAdded,dateShedule,Tag.TRUE, Tag.FALSE));
	}
	
	
	@Override
	public void updateInteractValid(Integer maxId) throws Exception {
		interactWorldDao.updateValid(maxId, Tag.TRUE);
		interactWorldClickDao.updateValid(maxId, Tag.TRUE);
		interactWorldCommentDao.updateValid(maxId, Tag.TRUE);
		interactWorldLikedDao.updateValid(maxId, Tag.TRUE);
	}
	
	@Override
	public void deleteInteract(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		interactWorldDao.deleteInteract(ids);
		interactWorldClickDao.deleteByInteractIds(ids);
		interactWorldCommentDao.deleteByInteractIds(ids);
		interactWorldLikedDao.deleteByInteractIds(ids);
	}
	
	@Override
	public List<Date> getScheduleDates(Date start, int duration, int size) {
		List<Date> list = new ArrayList<Date>();
		long interval = (long)((double)duration / (double)WORKING_HOUR * WORKING_TIME / size);
		//计算正确的开始时间，若是当前时间为不工作时间，则开始时间为最早开始时间
//		Calendar ca = Calendar.getInstance(Locale.CHINA);
//		ca.setTime(start);
//		int hour = ca.get(Calendar.HOUR_OF_DAY);
//		if(MAX_WORKING_HOUR > MIN_WORKING_HOUR){
//			if(!(hour>=MIN_WORKING_HOUR && hour<=MAX_WORKING_HOUR)){
//				ca.set(Calendar.HOUR_OF_DAY, MIN_WORKING_HOUR);
//			}
//		}else{
//			if(hour < MIN_WORKING_HOUR && hour > MAX_WORKING_HOUR) {
//				ca.set(Calendar.HOUR_OF_DAY, MIN_WORKING_HOUR);
//			}
//		}
//		long startTime = ca.getTimeInMillis();
		long startTime = start.getTime();
		for(int i = 0; i < size; i++) {
			long nextTime = (long)(startTime + Math.random() * interval);
			Date nextDate = getCorrectDate(new Date(nextTime));
			
			long tmpTime = (long)(startTime + interval);
			startTime = getCorrectDate(new Date(tmpTime)).getTime();
			list.add(nextDate);
		}
		return list;
	}
	
	
	
	@Override
	public List<Integer> getScheduleCount(int total, int duration) {
		List<Integer> countList = new ArrayList<Integer>();
		int workingMinutes = duration*60;
		int times = workingMinutes / WORKING_INTERVAL;
		if(times < total){//若总量大于分钟数，能够被均分
			int pre = total / times;
			int mod = total % times;
			for(int i = 0; i < times; i++) {
				if(i == times - 1 && mod > 0) {
					countList.add(mod);
				} else {
					countList.add(pre);
				}
			}
		}else{//不能均分，则每个都为1，不足补0
			for(int i = 0; i < times; i++){
				if(i < total){
					countList.add(1);
				}
			}
		}
		
		return countList;
	}
	
	/**
	 * 根据总数total，和分钟来分配一次互动要添加多少次点击数。
	 * 若少于100/次，则强制分配为100
	 */
	@Override
	public List<Integer> getScheduleCountV2(int total, int minutes) {
		List<Integer> countList = new ArrayList<Integer>();
		int workingMinutes = minutes;
		int times = workingMinutes / WORKING_INTERVAL;
		int pre = total / times;
		
		while(total > 0){
			if( total < 100){//若是低于一百次，则就一次分配完。若高于100次，就慢慢分
				pre = total;
				total = 0;
			}else if(pre < 100 && total >100){
				pre = 100;
				total -= pre;
			}else{
				total -= pre;
			}
			countList.add(pre);
		}
		
		return countList;
	}
	
	/**
	 * 获取工作时间
	 * 
	 * @param start
	 * @return
	 */
	private static Date getCorrectDate(Date start) {
//		Calendar ca = Calendar.getInstance(Locale.CHINA);
//		ca.setTime(start);
//		int hour = ca.get(Calendar.HOUR_OF_DAY);
//		
//		//该段代码是为了兼容max_working_hour为第二天的时间，也就是max_working_hour<min_working_hour
//		//
//		//请不要删掉该段代码
//		//
//		if(MAX_WORKING_HOUR > MIN_WORKING_HOUR){
//			if(!(hour>=MIN_WORKING_HOUR && hour<=MAX_WORKING_HOUR)){
//				int collectHour = hour-MAX_WORKING_HOUR;
//				collectHour = collectHour < 0 ? collectHour + 24 : collectHour;
//				ca.set(Calendar.HOUR_OF_DAY, MIN_WORKING_HOUR+collectHour-1);
//			}
//		}else{
//			if(hour < MIN_WORKING_HOUR && hour > MAX_WORKING_HOUR) {
//				int collectHour = hour-MAX_WORKING_HOUR;
//				collectHour = collectHour < 0 ? collectHour + 24 : collectHour;
//				ca.set(Calendar.HOUR_OF_DAY, MIN_WORKING_HOUR+collectHour-1);
//			}
//		}
//		return ca.getTime();
		return start;
	}

	@Override
	public void buildInteractSum(Integer worldId, Map<String, Object> jsonMap)
			throws Exception {
		Integer clickCount = 0;
		Integer likedCount = 0;
		Integer commentCount = 0;
		InteractWorld interact = interactWorldDao.queryInteractByWorldId(worldId);
		if(interact != null) {
			clickCount = interact.getClickCount();
			likedCount = interact.getLikedCount();
			commentCount = interact.getCommentCount();
		}
		
		jsonMap.put("clickCount", clickCount);
		jsonMap.put("likedCount", likedCount);
		jsonMap.put("commentCount", commentCount);
	}
	
	@Override
	public void buildUserInteract(int maxId, int start,
			int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<InteractUser>() {

			@Override
			public List<InteractUser> getSerializables(RowSelection rowSelection) {
				return interactUserDao.queryInteract(rowSelection);
			}

			@Override
			public List<InteractUser> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return interactUserDao.queryInteract(maxId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return interactUserDao.queryInteractCount(maxId);
			}
		}, OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);
	}

	@Override
	public InteractUser getUserInteractByUID(Integer userId) {
		InteractUser interact = interactUserDao.queryInteractByUID(userId);
		if(interact == null) 
			interact = new InteractUser();
		return interact;
	}
	
	@Override
	public void saveUserInteract(Integer userId, Integer followCount,
			Integer duration) throws Exception {
		Date dateAdded = new Date();
		Integer interactId = null;
		
		// 过滤null
		duration = duration == null ? 0 : duration; 
		followCount = followCount == null ? 0 : followCount;
		
		InteractUser interact = interactUserDao.queryUserInteractByUID(userId);
		if(interact != null) {
			interactId = interact.getId();
			// 更新用户互动
			interactUserDao.updateInteract(interactId, followCount+interact.getFollowCount(),
					duration+interact.getDuration());
		} else {
			interactId = keyGenService.generateId(Admin.KEYGEN_INTERACT_USER_ID);
			// 保存用户互动
			interactUserDao.saveInteract(new InteractUser(interactId, 
					userId, followCount, duration, dateAdded, Tag.TRUE));
		}
		
		// 保存粉丝互动
		if(followCount > 0) {
			List<Integer> uids = opUserService.getRandomUnFollowUserZombieIds(userId, followCount);
			List<Date> scheduleDateList = getScheduleDates(dateAdded, duration, followCount);
			for(int i = 0; i < uids.size(); i++) {
				interactUserFollowDao.saveFollow(new InteractUserFollow(interactId, userId, uids.get(i),
						dateAdded, scheduleDateList.get(i), Tag.TRUE, Tag.FALSE));
			}
		}
	}
	
	@Override
	public void deleteUserInteract(String idsStr) throws Exception {
		Integer[] interactIds = StringUtil.convertStringToIds(idsStr);
		interactUserDao.deleteByIds(interactIds);
		interactUserFollowDao.deleteByInteractId(interactIds);
	}
	
	@Override
	public void buildFollow(final Integer interactId, int maxId, int start,
			int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<InteractUserFollow>() {

			@Override
			public List<InteractUserFollow> getSerializables(RowSelection rowSelection) {
				return interactUserFollowDao.queryFollow(interactId, rowSelection);
			}

			@Override
			public List<InteractUserFollow> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return interactUserFollowDao.queryFollow(interactId, maxId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return interactUserFollowDao.queryFollowCount(interactId, maxId);
			}
		}, OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);
	}
	
	
	@Override
	public void commitClick() {
		Integer successfullyFinishCount = 0;
		Date currentDate = new Date();
		logger.info("click begin ：" + currentDate);
		Date startDate = new Date(currentDate.getTime() - WORKING_INTERVAL_MILLISECOND);
		
		// 提交播放互动队列
		List<InteractWorldClick> clickList = interactWorldClickDao.queryUnFinishedClick(startDate, currentDate);
		for(InteractWorldClick click : clickList) {
			try {
				successfullyFinishCount++;
				webWorldService.addClickCount(click.getWorldId(), click.getClick());
				interactWorldClickDao.updateFinished(click.getId(), Tag.TRUE);
			} catch (HTSException e) {
				interactWorldClickDao.updateFinished(click.getId(), Tag.TRUE);
			} catch (Exception e) {
			}
		}
		Date endDate = new Date();
		interactTrackerDao.updateLastInteractDate(INTERACT_TYPE_CLICK, endDate);
		logger.info("click finished：" + endDate + "," + "cost：" + (endDate.getTime() - currentDate.getTime()) + "ms.总共："+clickList.size() + ". 成功："+successfullyFinishCount);
	}
	
	@Override
	public void commitComment() {
		Integer successfullyFinishCount = 0;
		Date currentDate = new Date();
		logger.info("comment begin ：" + currentDate);
		Date startDate = new Date(currentDate.getTime() - WORKING_INTERVAL_MILLISECOND);
		
		// 提交评论互动队列
		List<InteractWorldCommentDto> commentList = interactWorldCommentDao.queryUnFinishedComment(startDate, currentDate);
		
		for(InteractWorldCommentDto comment : commentList) {
			try {
				successfullyFinishCount++;
				webWorldInteractService.saveComment(false, comment.getWorldId(), null,
						comment.getUserId(), " : " + comment.getContent());
				interactWorldCommentDao.updateFinished(comment.getId(), Tag.TRUE);
			} catch (HTSException e) {
				interactWorldCommentDao.updateFinished(comment.getId(), Tag.TRUE);
			} catch(YunbaException e2){
				interactWorldCommentDao.updateFinished(comment.getId(), Tag.TRUE);
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
			
		}
		Date endDate = new Date();
		interactTrackerDao.updateLastInteractDate(INTERACT_TYPE_COMMENT, endDate);
		logger.info("comment finished：" + endDate + "," + "cost：" + (endDate.getTime() - currentDate.getTime()) + "ms.总共："+commentList.size() + ". 成功："+successfullyFinishCount);
		
	}
	
	@Override
	public void commitLiked() {
		Date currentDate = new Date();
		logger.info("liked begin ：" + currentDate);
		Date startDate = new Date(currentDate.getTime() - WORKING_INTERVAL_MILLISECOND);
		
		// 提交喜欢互动队列
		List<InteractWorldLiked> likedList = interactWorldLikedDao.queryUnfinishedLiked(startDate, currentDate);
		for(InteractWorldLiked liked : likedList) {
			try {
				webWorldInteractService.saveLiked(false, liked.getUserId(), liked.getWorldId(), null);
				interactWorldLikedDao.updateFinished(liked.getId(), Tag.TRUE);
			} catch (HTSException e) {
				interactWorldLikedDao.updateFinished(liked.getId(), Tag.TRUE);
			} catch(YunbaException e2){
				interactWorldLikedDao.updateFinished(liked.getId(), Tag.TRUE);
			}catch (Exception e) {
			}
		}
		Date endDate = new Date();
		interactTrackerDao.updateLastInteractDate(INTERACT_TYPE_LIKED, endDate);
		logger.info("liked finished：" + endDate + "," + "cost：" + (endDate.getTime() - currentDate.getTime())/1000 + "seconds");
		
	}
	
	@Override
	public void commitFollow() {
		Date currentDate = new Date();
		logger.info("follow begin ：" + currentDate);
		Date startDate = new Date(currentDate.getTime() - WORKING_INTERVAL_MILLISECOND);
		
		// 提交粉丝互动队列
		List<InteractUserFollow> followList = interactUserFollowDao.queryUnFinishedFollow(startDate, currentDate);
		for(InteractUserFollow follow : followList) {
			try {
				int concernCount = NumberUtil.getRandomNum(minConcernCount, maxConcernCount);
				webUserInteractService.saveConcern(false, follow.getFollowId(), follow.getUserId(), concernCount);
				interactUserFollowDao.updateFinished(follow.getId(), Tag.TRUE);
			} catch (HTSException e) {
				interactUserFollowDao.updateFinished(follow.getId(), Tag.TRUE);
			} catch(YunbaException e2){
				interactUserFollowDao.updateFinished(follow.getId(), Tag.TRUE);
			} catch (Exception e) {
			}
		}
		Date endDate = new Date();
		interactTrackerDao.updateLastInteractDate(INTERACT_TYPE_FOLLOW, endDate);
		logger.info("follow finished：" + endDate + "," + "cost：" + (endDate.getTime() - currentDate.getTime())/1000 + "seconds");
		
	}

	@Override
	public void updateUnFinishedInteractSchedule() throws Exception {
		Date now = new Date();
		interactTrackerDao.updateTrackValid(Tag.FALSE);
		//取消掉评论、点赞、播放的延时
//		interactWorldClickDao.updateUnFinishedSchedule(now);
//		interactWorldCommentDao.updateUnFinishedSchedule(now);
//		interactWorldLikedDao.updateUnFinishedSchedule(now);
		interactUserFollowDao.updateUnFinishedSchedule(now);
	}

	@Override
	public void buildTracker(Map<String, Object> jsonMap) {
		List<InteractTracker> trackerList = interactTrackerDao.queryTracker();
		jsonMap.put(OptResult.JSON_KEY_TOTAL, trackerList.size());
		jsonMap.put(OptResult.JSON_KEY_ROWS, trackerList);
	}

	@Override
	public void trackInteract() {
		Calendar ca = Calendar.getInstance();
		long currTime = ca.getTimeInMillis();
		Date currDate = ca.getTime();
		List<InteractTracker> trackerList = interactTrackerDao.queryTracker();
		for(InteractTracker tracker : trackerList) {
			// 每天第一次，先更新所有跟踪器的有效状态
			if(tracker.getValid().equals(Tag.FALSE)) {
				interactTrackerDao.updateTrackValid(Tag.TRUE);
				break;
			}
			
			long interval = currTime - tracker.getLastInteractDate().getTime();
			if(interval >= (tracker.getInteractStep()+1)*60*1000) { // 互动超时
				notifyInteractTimeOut(tracker.getId());
				break;
				
			}
		}
		
		interactTrackerDao.updateLastTrackDate(currDate);
	}
	
	/**
	 * 互动超时通知
	 * 
	 * @param id
	 * @throws Exception 
	 */
	public void notifyInteractTimeOut(int id) {
		String content = "";
		switch (id) {
		case INTERACT_TYPE_CLICK:
			content = "播放互动超时";
			break;
		case INTERACT_TYPE_COMMENT:
			content = "评论互动超时";
			break;
		case INTERACT_TYPE_LIKED:
			content = "喜欢互动超时";
			break;
		case INTERACT_TYPE_FOLLOW:
			content = "粉丝互动超时";
			break;
		default:
			break;
		}
		try {
			webUserMsgService.saveSysMsg(OpServiceImpl.ZHITU_UID, adminId, content,
					Tag.USER_MSG_SYS, 0, null, null, null, 0);
			UserPushInfo userPushInfo = webUserInfoDao.queryUserPushInfoById(adminId);
			pushService.pushSysMessage(content, OpServiceImpl.ZHITU_UID, content, 
					userPushInfo, Tag.USER_MSG_SYS, new PushFailedCallback() {

				@Override
				public void onPushFailed(Exception e) {}
				
			});
		} catch (Exception e) {
		}
		Log.info(content);
	}
	
	/**
	 * 根据worldID列表来更新worldID对应的有效状态
	 * @param wids
	 * @param valid
	 */
	@Override
	public void updateInteractValidByWIDs(Integer[] wids,Integer valid) throws Exception{
		try{
			interactWorldDao.upInteractValidByWIDs(wids, valid);
		}catch(Exception e){
			throw e;
		}
	}
	/**
	 *  根据id更新互动播放表中的调度时间和有效性
	 * @param valid
	 * @param date_schedule
	 * @param id
	 * @throws Exception
	 */

	@Override
	public void updateClickValidAndSCHTimeById(Integer valid,Date date_schedule,Integer id) throws Exception{
		try{
			interactWorldClickDao.updateValidAndSCHTimeById(valid, date_schedule, id);
		}catch(Exception e){
			throw e;
		}
		
	}
	
	/**
	 *  根据id更新互动喜欢表中的调度时间和有效性
	 * @param valid
	 * @param date_schedule
	 * @param id
	 * @throws Exception
	 */
	@Override
	public void updateLikedValidAndSCHTimeById(Integer valid,Date date_schedule,Integer id) throws Exception{
		try{
			interactWorldLikedDao.updateValidAndSCHTimeById(valid, date_schedule, id);
		}catch(Exception e){
			throw e;
		}
		
	}
	
	/**
	 *  根据id更新互动评论表中的调度时间和有效性
	 * @param valid
	 * @param date_schedule
	 * @param id
	 * @throws Exception
	 */
	@Override
	public void updateCommentValidAndSCHTimeById(Integer valid,Date date_schedule,Integer id) throws Exception{
		try{
			interactWorldCommentDao.updateValidAndSCHTimeById(valid, date_schedule, id);
		}catch(Exception e){
			throw e;
		}
		
	}
	
	/**
	 * 更新播放互动计划的调度时间和有效性
	 * @param interactId
	 * @param date_add
	 * @throws Exception
	 */
	@Override
	public void updateClickValidAndSCHTimeByInteractId(Integer interactId,Date date_add)throws Exception{
		try{
			List<InteractWorldClick> interactWorldClickList = interactWorldClickDao.queryClickbyInteractId(interactId);
			if(interactWorldClickList != null){
				Date now = new Date();
				SimpleDateFormat dft = new SimpleDateFormat("yyMMdd");
				Calendar tomorrow = Calendar.getInstance();
				String nowStr = dft.format(now);
				tomorrow.setTime(dft.parse(nowStr));
				tomorrow.add(Calendar.DAY_OF_YEAR,1);
				long dist = now.getTime()-date_add.getTime();
				long tomorrowMinTime = tomorrow.getTime().getTime();
				long tomorrowMaxTime = tomorrowMinTime+UNWORKING_TIME_MILLISECOND;
				
				for(InteractWorldClick o:interactWorldClickList){
					long t = o.getDateSchedule().getTime()+dist;
					if(t>tomorrowMinTime && t<tomorrowMaxTime){
						t+=UNWORKING_TIME_MILLISECOND;
					}
					updateClickValidAndSCHTimeById(Tag.TRUE,new Date(t),o.getId());
				}
			}
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 更新喜欢互动计划的调度时间和有效性
	 * @param interactId
	 * @param date_add
	 * @throws Exception
	 */
	@Override
	public void updateLikedValidAndSCHTimeByInteractId(Integer interactId,Date date_add)throws Exception{
		try{
			List<InteractWorldLiked> interactWorldLikedList = interactWorldLikedDao.queryLikedByInteractId(interactId);		
			if(interactWorldLikedList != null){
				Date now = new Date();
				SimpleDateFormat dft = new SimpleDateFormat("yyMMdd");
				Calendar tomorrow = Calendar.getInstance();
				String nowStr = dft.format(now);
				tomorrow.setTime(dft.parse(nowStr));
				tomorrow.add(Calendar.DAY_OF_YEAR,1);
				long dist = now.getTime()-date_add.getTime();
				long tomorrowMinTime = tomorrow.getTime().getTime();
				long tomorrowMaxTime = tomorrowMinTime+UNWORKING_TIME_MILLISECOND;
				
				for(InteractWorldLiked o:interactWorldLikedList){
					long t = o.getDateSchedule().getTime()+dist;
					if(t>tomorrowMinTime && t<tomorrowMaxTime){
						t+=UNWORKING_TIME_MILLISECOND;
					}
					updateLikedValidAndSCHTimeById(Tag.TRUE,new Date(t),o.getId());
				}
			}
		}catch(Exception e){			
			throw e;
		}
	}
	
	/**
	 * 更新播放互动计划的调度时间和有效性
	 * @param interactId
	 * @param date_add
	 * @throws Exception
	 */
	@Override
	public void updateCommentValidAndSCHTimeByInteractId(Integer interactId,Date date_add)throws Exception{
		try{
			List<InteractWorldCommentDto> interactWorldCommentList = interactWorldCommentDao.queryCommentByInteractId(interactId);
			if(interactWorldCommentList != null){
				Date now = new Date();
				SimpleDateFormat dft = new SimpleDateFormat("yyMMdd");
				Calendar tomorrow = Calendar.getInstance();
				String nowStr = dft.format(now);
				tomorrow.setTime(dft.parse(nowStr));
				tomorrow.add(Calendar.DAY_OF_YEAR,1);
				long dist = now.getTime()-date_add.getTime();
				long tomorrowMinTime = tomorrow.getTime().getTime();
				long tomorrowMaxTime = tomorrowMinTime+UNWORKING_TIME_MILLISECOND;
				
				for(InteractWorldCommentDto o:interactWorldCommentList){
					long t = o.getDateSchedule().getTime()+dist;
					if(t>tomorrowMinTime && t<tomorrowMaxTime){
						t+=UNWORKING_TIME_MILLISECOND;
					}
					updateCommentValidAndSCHTimeById(Tag.TRUE,new Date(t),o.getId());
				}
			}
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 根据worldId查询互动表
	 * @param worldId
	 * @return
	 * @throws Exception
	 */
	@Override
	public InteractWorld queryInteractByWorldId(Integer worldId) throws Exception{
		return interactWorldDao.queryInteractByWorldId(worldId);
	}
	
	/**
	 * 更新互动播放喜欢评论计划
	 * @param worldId
	 * @throws Exception
	 */
	@Override
	public void updateInteractCommentLikedClickByWorldId(Integer[] wids)throws Exception{
		List<InteractWorld> interactWorldList = interactWorldDao.queryInteractByWIDs(wids);	
		if(interactWorldList !=null && !interactWorldList.isEmpty()){
			try{
				updateInteractValidByWIDs(wids, Tag.TRUE);
				for(InteractWorld o:interactWorldList){
					updateClickValidAndSCHTimeByInteractId(o.getId(), o.getDateAdded());
					updateCommentValidAndSCHTimeByInteractId(o.getId(), o.getDateAdded());
					updateLikedValidAndSCHTimeByInteractId(o.getId(), o.getDateAdded());
				}
			}catch(Exception e){
				throw e;
			}
		}	
	}
	
	
	/**
	 * 根据织图id查询互动id
	 */
	@Override
	public Integer queryIntegerIdByWorldId(Integer wId)throws Exception{
		return interactWorldDao.queryIntegerIdByWorldId(wId);
	}
	
	/**
	 * 删除计划评论by ids
	 */
	@Override
	
	public void deleteInteractCommentByids(String idsStr)throws Exception{
		if(idsStr == null || idsStr.equals(""))return ;
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		interactWorldCommentDao.deleteInteractCommentByids(ids);
	}
	
}
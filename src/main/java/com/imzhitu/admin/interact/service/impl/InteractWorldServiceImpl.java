package com.imzhitu.admin.interact.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.Log;
import com.hts.web.common.util.NumberUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.InteractComment;
import com.imzhitu.admin.common.pojo.InteractTracker;
import com.imzhitu.admin.common.pojo.InteractUser;
import com.imzhitu.admin.common.pojo.InteractUserFollow;
import com.imzhitu.admin.common.pojo.InteractWorld;
import com.imzhitu.admin.common.pojo.InteractWorldClick;
import com.imzhitu.admin.common.pojo.InteractWorldCommentDto;
import com.imzhitu.admin.common.pojo.InteractWorldLiked;
import com.imzhitu.admin.common.pojo.OpZombieDegreeUserLevel;
import com.imzhitu.admin.common.pojo.UserLevelListDto;
import com.imzhitu.admin.common.pojo.ZTWorldLevelDto;
import com.imzhitu.admin.common.service.KeyGenService;
import com.imzhitu.admin.interact.dao.InteractTrackerDao;
import com.imzhitu.admin.interact.dao.InteractUserDao;
import com.imzhitu.admin.interact.dao.InteractWorldDao;
import com.imzhitu.admin.interact.dao.InteractWorldlevelDao;
import com.imzhitu.admin.interact.mapper.InteractUserFollowMapper;
import com.imzhitu.admin.interact.mapper.InteractWorldClickMapper;
import com.imzhitu.admin.interact.mapper.InteractWorldCommentMapper;
import com.imzhitu.admin.interact.mapper.InteractWorldLikedMapper;
import com.imzhitu.admin.interact.service.CommentService;
import com.imzhitu.admin.interact.service.InteractUserlevelListService;
import com.imzhitu.admin.interact.service.InteractWorldService;
import com.imzhitu.admin.op.mapper.OpZombieChannelMapper;
import com.imzhitu.admin.op.mapper.OpZombieMapper;
import com.imzhitu.admin.op.service.OpZombieChannelService;
import com.imzhitu.admin.op.service.OpZombieDegreeUserLevelService;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

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
	 * 工作频率：分钟
	 */
	public static final int WORKING_INTERVAL = 5;
	/**
	 * 工作频率，毫秒级
	 */
	public static final int WORKING_INTERVAL_MILLISECOND = WORKING_INTERVAL*2*60*1000;
	/**
	 * 不工作时长，毫秒级
	 */
	public static final int UNWORKING_TIME_MILLISECOND = 60*60*1000*(MIN_WORKING_HOUR+24-MAX_WORKING_HOUR);
	
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
	
	@Value("${admin.interact.zombie.common.degree.id}")
	private Integer commonZombieDegreeId;
	
	
	@Autowired
	private KeyGenService keyGenService;
	
	@Autowired
	private InteractWorldDao interactWorldDao;
	
	@Autowired
	private CommentService interactCommentService;
	
	@Autowired
	private InteractUserDao interactUserDao;
	
	@Autowired
	private InteractTrackerDao interactTrackerDao;
	
	@Autowired
	private com.hts.web.ztworld.service.ZTWorldInteractService webWorldInteractService;
	
	@Autowired
	private com.hts.web.ztworld.service.ZTWorldService webWorldService;
	
	@Autowired
	private com.hts.web.userinfo.service.UserInteractService webUserInteractService;
	
	@Autowired
	private InteractWorldClickMapper worldClickMapper;
	
	@Autowired
	private OpZombieMapper zombieMapper;
	
	@Autowired
	private InteractWorldLikedMapper worldLikedMapper;
	
	@Autowired
	private InteractWorldCommentMapper worldCommentMapper;
	
	@Autowired
	private InteractUserFollowMapper userFollowMapper;
	
	@Autowired
	private InteractWorldlevelDao interactWorldlevelDao;
	
	@Autowired
	private InteractUserlevelListService userLevelListService;
	
	@Autowired
	private OpZombieDegreeUserLevelService zombieDegreeUserLevelService;
	
	@Autowired
	private OpZombieChannelService zombieChannelService;
	
	@Autowired
	private com.imzhitu.admin.interact.dao.InteractCommentDao interactCommentDao;
	
	@Autowired
	private OpZombieChannelMapper zombieChannelMapper;

	private Logger log = Logger.getLogger(InteractWorldServiceImpl.class);
	
	public Integer getCommonZombieDegreeId() {
		return commonZombieDegreeId;
	}

	public void setCommonZombieDegreeId(Integer commonZombieDegreeId) {
		this.commonZombieDegreeId = commonZombieDegreeId;
	}
	
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
		InteractWorldCommentDto dto = new InteractWorldCommentDto();
		dto.setFirstRow(limit * (start -1));
		dto.setLimit(limit);
		dto.setInteractId(interactId);
		dto.setMaxId(maxId);
		long total = 0;
		Integer reMaxId = 0;
		List<InteractWorldCommentDto> list = null;
		
		total = worldCommentMapper.queryWorldCommentTotalCount(dto);
		
		if (total > 0) {
			list = worldCommentMapper.queryWorldComment(dto);
			if ( list != null && list.size() > 0) {
				reMaxId = list.get(0).getId();
			}
		}
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, reMaxId);
		
	}

	@Override
	public void buildLikeds(final Integer interactId, int maxId, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		
		InteractWorldLiked dto = new InteractWorldLiked();
		dto.setFirstRow(limit * (start -1));
		dto.setLimit(limit);
		dto.setInteractId(interactId);
		dto.setMaxId(maxId);
		long total = 0;
		Integer reMaxId = 0;
		List<InteractWorldLiked> list = null;
		
		total = worldLikedMapper.queryWorldLikedTotalCount(dto);
		
		if (total > 0) {
			list = worldLikedMapper.queryWorldLiked(dto);
			if ( list != null && list.size() > 0) {
				reMaxId = list.get(0).getId();
			}
		}
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, reMaxId);
	}

	@Override
	public void buildClicks(final Integer interactId, int maxId, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		InteractWorldClick dto = new InteractWorldClick();
		dto.setFirstRow(limit * (start -1));
		dto.setLimit(limit);
		dto.setInteractId(interactId);
		dto.setMaxId(maxId);
		long total = 0;
		Integer reMaxId = 0;
		List<InteractWorldClick> list = null;
		
		total = worldClickMapper.queryWorldClickTotalCount(dto);
		
		if (total > 0) {
			list = worldClickMapper.queryWorldClick(dto);
			if ( list != null && list.size() > 0) {
				reMaxId = list.get(0).getId();
			}
		}
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, reMaxId);
	}
	
	
	/**
	 * 获取时间列表
	 * 特殊时间2点、，这两个时间段。
	 * 		算法：2点时间比例为每5分钟12%，11%，。。。，，并且让这些比例 乘以   （1-当前分钟/60） 。 未能完成的部分推迟到7点钟来完成。这样能有效避免2点59分发的图，3点钟还有一堆人点赞
	 * 特殊时间3点到7点：
	 *      算法：3点到6点没有人，不实施互动。7点钟开始，比例1%，2%。。。12%，11%。。。1%。
	 * 正常时间段8点到凌晨2点：
	 * 		算法：比例为12%，11%。。。1%,1%
	 */
	public List<Date> getScheduleV3(Date begin,Integer minuteDuration, Integer total){
		List<Date> dateList = new ArrayList<Date>();
		int tt = total;
		int hasBeenProcess = 0;
		long beginTime = begin.getTime();
		long interval = minuteDuration*2000L;
		Calendar ca = Calendar.getInstance(Locale.CHINA);
		ca.setTime(begin);
		int hourOfDay = ca.get(Calendar.HOUR_OF_DAY);
		int minuteOfHour = ca.get(Calendar.MINUTE);
		if(hourOfDay == 2){//凌晨2点钟,比例为12*(60-minuteOfHour)/60，11*(60-minuteOfHour)/60。。。
			for(int i=0;minuteOfHour < 60 && tt > hasBeenProcess;i++){
				int t = tt *((12-i)>0?(12-i):1)*(60-minuteOfHour)/6000;
				minuteOfHour += 5;
				hasBeenProcess += t;
				
				if(t>0){
					long span = interval / t;
					long tmpBeginTime = beginTime;
					for(int j=0; j < t; j++){						
						beginTime += (long)((Math.random()+1)*span);
						dateList.add(new Date(beginTime));
					}
					beginTime = tmpBeginTime + 300000L;
				}else{
					break;
				}
			}
			
			//剩下的7点钟来完成
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try{
				String str = df.format(begin);
				Date tmpDate = df.parse(str);
				beginTime = tmpDate.getTime() + 7*3600000L;
			}catch(Exception e){
				Log.warn("getScheduleV3: convert time failed!");
			}
			
			for(int k=1;tt > hasBeenProcess;k++){
				int tmpK = k>12?(24-k):k;
				int t = tt * tmpK/100;
				if(t == 0){
					if(Math.round(Math.random()) == 1){
						t = 1;
					}
					k--;
				}
				hasBeenProcess += t;
				long tmpBeginTime = beginTime;
				if(t>0){
					long span = interval / t;
					for(int j=0; j < t; j++){						
						beginTime += (long)((Math.random()+1)*span);
						dateList.add(new Date(beginTime));
					}
				}
				beginTime = tmpBeginTime + 300000L;
			}
		}else if(hourOfDay > 2 && hourOfDay < 8){//凌晨3点到早上八点基本很少人
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try{
				String str = df.format(begin);
				Date tmpDate = df.parse(str);
				if (hourOfDay != 7) {//如果为7点，则用及时的时间做开始时间，否则生效开始时间会早于计划时间
					beginTime = tmpDate.getTime() + 7*3600000L;
				} 
			}catch(Exception e){
				Log.warn("getScheduleV3: convert time failed!");
			}
			
			for(int k=1;tt > hasBeenProcess;k++){
				int tmpK = k>12?(24-k):k;
				int t = tt * tmpK/100;
				if(t == 0){
					if(Math.round(Math.random()) == 1){
						t = 1;
					}
					k--;
				}
				hasBeenProcess += t;
				long tmpBeginTime = beginTime;
				if(t>0){
					long span = interval / t;
					for(int j=0; j < t; j++){						
						beginTime += (long)((Math.random()+1)*span);
						dateList.add(new Date(beginTime));
					}
				}
				beginTime = tmpBeginTime + 300000L;
					
			}
		}else{//正常时间.
			//比例12、11、10、9、8、7、6、5、4、3、2、1、1。。。
			for(int i=0; tt > hasBeenProcess; i++){
				int tmpK = (12-i)>0?(12-i):1;
				int t = tt * tmpK / 100;
				if(t == 0){
					if(Math.round(Math.random()) == 1){
						t = 1;
					}
					i--;
				}
				hasBeenProcess += t;
				long tmpBeginTime = beginTime;
				if(t>0){
					long span = interval / t;
					for(int j=0; j < t; j++){						
						beginTime += (long)((Math.random()+1)*span);
						dateList.add(new Date(beginTime));
					}
				}
				beginTime = tmpBeginTime + 300000L;
			}
			
		}
		return dateList;
	}
	
	@Override
	public void saveInteractV3(Integer userId,Integer degreeId,Integer worldId,Integer clickCount,
			Integer likedCount,String[] commentIds,Integer minuteDuration)throws Exception {
		minuteDuration = minuteDuration == null ? 0 : minuteDuration; 
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
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration, countList.size());
			batchSaveClick(interactId, worldId, countList, dateAdded, scheduleDateList);
		}
		
		//
		//获取非粉僵尸用户、和用户僵尸粉所需总数
		//因为*Rate是整数，范围在0-100代表0%-100%
		//
		List<Integer> fzList = null;
		List<Integer> unFzList = null;
		int fzListLength=0;
		int unFzListLength = 0;
		
		//计算粉丝马甲数
		int followZombiesTotal = likedCount * likeFromFollowRate + likedCount * ( 100 -  likeFromFollowRate)* likeToFollowRate
				> commentCount * commentFromFollowRate ? likedCount * likeFromFollowRate : commentCount * commentFromFollowRate;
		followZombiesTotal = Math.round(followZombiesTotal / 100.00f);
		int unFollowZombiesTotal = likedCount > commentCount ? likedCount - followZombiesTotal : commentCount - followZombiesTotal;
		
		/**
		 * 对比需要数和总的数据库中的数据对比
			mishengliang
		 */
		Integer fzListTotalCount = 0;
		Integer unFzListTotalCount = 0;
		
		//查出库中的粉丝马季和非粉丝马甲
		fzListTotalCount = zombieMapper.queryNotInteractNRandomFollowZombieCount(userId, worldId,0);
		unFzListTotalCount = zombieMapper.queryNotInteractNRandomNotFollowZombieCount(userId, degreeId,worldId,0);
		int total = likedCount > commentCount ? likedCount : commentCount;
		/**
		 * if 库中粉丝马甲+库中非粉丝马甲 > 需要马甲总数{
		 * 		if（需要粉丝马甲< 库中粉丝马甲 && 需要非粉丝马甲> 库中非粉丝马甲）{
		 * 			需要粉丝马甲 = 总马甲 - 库中非粉丝马甲； 
		 * 		}else if（需要粉丝马甲> 库中粉丝马甲 && 需要非粉丝马甲< 库中非粉丝马甲）{
		 * 			需要非粉丝马甲 = 总马甲 -  库中粉丝马甲;
		 * 		}else{
		 * 
		 * 		}
		 * }
		 * 
		 */
		if(fzListTotalCount + unFzListTotalCount > total){
			if (followZombiesTotal < fzListTotalCount&&unFollowZombiesTotal >= unFzListTotalCount) {
				followZombiesTotal = total - unFzListTotalCount;
				unFollowZombiesTotal =  unFzListTotalCount;
			} else if(followZombiesTotal >= fzListTotalCount&&unFollowZombiesTotal < unFzListTotalCount){
				unFollowZombiesTotal = total - fzListTotalCount;
				followZombiesTotal = fzListTotalCount;
			}else if((followZombiesTotal < fzListTotalCount&&unFollowZombiesTotal < unFzListTotalCount)){
				
			}
		}else {
			throw new Exception("没有足够的马甲数");
		}
		
		
		//查询粉丝马甲
		if ( followZombiesTotal > 0){
			try{
				fzList = zombieMapper.queryNotInteractNRandomFollowZombie(userId, worldId,followZombiesTotal);
				if(fzList != null){
					fzListLength = fzList.size();
				}
			}catch(Exception e){
				logger.warn("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".worldId="+worldId+".\nnow set fzList is null.\ncause:"+e.getMessage());
				fzList = null;
				fzListLength = 0;
			}
		}
		
		//查询非粉丝马甲
		try{
			int unFollowZombieNeedTotal = followZombiesTotal + unFollowZombiesTotal - fzListLength;
			if(unFollowZombieNeedTotal > 0){
				unFzList = zombieMapper.queryNotInteractNRandomNotFollowZombie(userId, degreeId,worldId,unFollowZombieNeedTotal);
				if( null == unFzList){
					logger.warn("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".degreeId="+degreeId+",need:"+unFollowZombieNeedTotal+".\nnow set fzList is null.");
					throw new Exception("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".degreeId="+degreeId+",need:"+unFollowZombieNeedTotal+".\nnow set fzList is null.");
				}else{
					unFzListLength = unFzList.size();
				}
			}
			
		}catch(Exception e){
			logger.warn("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".degreeId="+degreeId+",need:"+(followZombiesTotal + unFollowZombiesTotal - fzListLength)+".\nnow set fzList is null.\ncause:"+e.getMessage());
		}
		//保存喜欢
		if(likedCount > 0) {
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration, likedCount);
			List<Integer> zombieIdList = new ArrayList<Integer>();
			int likeSize = Math.round(likedCount * likeFromFollowRate/100.00f);
			int unfollowlikeSize = likedCount - likeSize;
			likeSize =  (unFzListLength >= unfollowlikeSize ? likeSize : unfollowlikeSize - unFzListLength + likeSize);
			int i,j;
			for(i = 0; i < likeSize && i < fzListLength; i++) {
				zombieIdList.add(fzList.get(i));
			}
			for(j = 0; j < likedCount - i && j < unFzListLength; j++){
				zombieIdList.add(unFzList.get(j));
			}
			batchSaveLiked(interactId, worldId, zombieIdList, dateAdded, scheduleDateList);
		}
		
		// 保存评论
		if(commentCount > 0) {
			String idStr = Arrays.toString(commentIds);
			Integer[] cids = StringUtil.convertStringToIds(idStr.substring(1, idStr.length() - 1));
			List<Date> scheduleDateList = getCommentsScheduleDate(dateAdded, cids.length);
			List<Integer> zombieIdList = new ArrayList<Integer>();
			int followCommentSize = Math.round(commentCount * commentFromFollowRate);
			int i,j;
			for( i = 0; i < followCommentSize && i < fzListLength ; i++) {
				zombieIdList.add(fzList.get(i));
			}
			for(j = 0; j < commentCount - i && j < unFzListLength; j++){
				zombieIdList.add(unFzList.get(j));
			}
			batchSaveComment(interactId, worldId, zombieIdList, cids, dateAdded, scheduleDateList);
		}
		
		
		//加粉
		if(likedCount > 0){
			int followSize = Math.round(likedCount * likeToFollowRate / 100.0f);
			//如果非粉丝数量为0，则加粉的数量为0
			if (unFzListLength > 0 && unFzListLength <= Math.round(likedCount * likeToFollowRate / 100.0f)) {
				followSize = unFzList.size();
			}
			InteractUser userInteract = interactUserDao.queryUserInteractByUID(userId);
			if(userInteract != null) {
				interactId = userInteract.getId();
				// 更新用户互动
				interactUserDao.updateInteract(interactId, followSize+userInteract.getFollowCount(),
						(minuteDuration/60>0?minuteDuration/60:1)+userInteract.getDuration());
			} else {
				interactId = keyGenService.generateId(Admin.KEYGEN_INTERACT_USER_ID);
				// 保存用户互动
				interactUserDao.saveInteract(new InteractUser(interactId, 
						userId, followSize, minuteDuration/60>0?minuteDuration/60:1, dateAdded, Tag.TRUE));
			}
			
			// 保存粉丝互动
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration, followSize);
			List<InteractUserFollow> list = new ArrayList<InteractUserFollow>();
			for(int i = 0; i < followSize && i < unFzListLength; i++) {
				list.add(new InteractUserFollow(interactId, userId, unFzList.get(i),
						dateAdded, scheduleDateList.get(i), Tag.TRUE, Tag.FALSE));
			}
			
			try{
				if(list.size() > 0)
					userFollowMapper.batchSaveUserFollow(list);
			}catch(Exception e){
				logger.warn("saveInteractV3:userFollowMapper.batchSaveUserFollow failed.List<InteractUserFollow> list:"+list+"\ncause:"+e.getMessage());
				throw e;
			}
		}
		
		//更新op_zombie表中的lastmodify字段
		if(unFzList != null && unFzList.size() > 0){
			try{
				Integer[] unFzArray = new Integer[unFzList.size()];
				unFzList.toArray(unFzArray);
				zombieMapper.batchUpdateZombie(dateAdded.getTime(), 1, 1, unFzArray);
			}catch(Exception e){
				logger.warn("saveInteractV3:zombieMapper.batchUpdateZombie failed.List<InteractUserFollow> unFzList:"+unFzList+"\ncause:"+e.getMessage());
				throw e;
			}
		}
		
	}
	
	private void saveTypeInteract(Integer userId, Integer degreeId, Integer worldId, Integer clickCount, Integer likedCount, String[] commentIds, Integer minuteDuration) throws Exception {
		
		// TODO 这个方法可以重构，与saveInteractV3方法提取公共部分，这个方法只是计划评论的时间与V3方法不同，采用了原有的方法
		
		minuteDuration = minuteDuration == null ? 0 : minuteDuration; 
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
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration, countList.size());
			batchSaveClick(interactId, worldId, countList, dateAdded, scheduleDateList);
		}
		
		//
		//获取非粉僵尸用户、和用户僵尸粉所需总数
		//因为*Rate是整数，范围在0-100代表0%-100%
		//
		List<Integer> fzList = null;
		List<Integer> unFzList = null;
		int fzListLength=0;
		int unFzListLength = 0;
		
		//计算粉丝马甲数
		int followZombiesTotal = likedCount * likeFromFollowRate + likedCount * ( 100 -  likeFromFollowRate)* likeToFollowRate
				> commentCount * commentFromFollowRate ? likedCount * likeFromFollowRate : commentCount * commentFromFollowRate;
		followZombiesTotal = Math.round(followZombiesTotal / 100.00f);
		int unFollowZombiesTotal = likedCount > commentCount ? likedCount - followZombiesTotal : commentCount - followZombiesTotal;
		
		/**
		 * 对比需要数和总的数据库中的数据对比
			mishengliang
		 */
		Integer fzListTotalCount = 0;
		Integer unFzListTotalCount = 0;
		
		//查出库中的粉丝马季和非粉丝马甲
		fzListTotalCount = zombieMapper.queryNotInteractNRandomFollowZombieCount(userId, worldId,0);
		unFzListTotalCount = zombieMapper.queryNotInteractNRandomNotFollowZombieCount(userId, degreeId,worldId,0);
		int total = likedCount > commentCount ? likedCount : commentCount;
		/**
		 * if 库中粉丝马甲+库中非粉丝马甲 > 需要马甲总数{
		 * 		if（需要粉丝马甲< 库中粉丝马甲 && 需要非粉丝马甲> 库中非粉丝马甲）{
		 * 			需要粉丝马甲 = 总马甲 - 库中非粉丝马甲； 
		 * 		}else if（需要粉丝马甲> 库中粉丝马甲 && 需要非粉丝马甲< 库中非粉丝马甲）{
		 * 			需要非粉丝马甲 = 总马甲 -  库中粉丝马甲;
		 * 		}else{
		 * 
		 * 		}
		 * }
		 * 
		 */
		if(fzListTotalCount + unFzListTotalCount > total){
			if (followZombiesTotal < fzListTotalCount&&unFollowZombiesTotal >= unFzListTotalCount) {
				followZombiesTotal = total - unFzListTotalCount;
				unFollowZombiesTotal =  unFzListTotalCount;
			} else if(followZombiesTotal >= fzListTotalCount&&unFollowZombiesTotal < unFzListTotalCount){
				unFollowZombiesTotal = total - fzListTotalCount;
				followZombiesTotal = fzListTotalCount;
			}else if((followZombiesTotal < fzListTotalCount&&unFollowZombiesTotal < unFzListTotalCount)){
				
			}
		}else {
			throw new Exception("没有足够的马甲数");
		}
		
		
		//查询粉丝马甲
		if ( followZombiesTotal > 0){
			try{
				fzList = zombieMapper.queryNotInteractNRandomFollowZombie(userId, worldId,followZombiesTotal);
				if(fzList != null){
					fzListLength = fzList.size();
				}
			}catch(Exception e){
				logger.warn("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".worldId="+worldId+".\nnow set fzList is null.\ncause:"+e.getMessage());
				fzList = null;
				fzListLength = 0;
			}
		}
		
		//查询非粉丝马甲
		try{
			int unFollowZombieNeedTotal = followZombiesTotal + unFollowZombiesTotal - fzListLength;
			if(unFollowZombieNeedTotal > 0){
				unFzList = zombieMapper.queryNotInteractNRandomNotFollowZombie(userId, degreeId,worldId,unFollowZombieNeedTotal);
				if( null == unFzList){
					logger.warn("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".degreeId="+degreeId+",need:"+unFollowZombieNeedTotal+".\nnow set fzList is null.");
					throw new Exception("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".degreeId="+degreeId+",need:"+unFollowZombieNeedTotal+".\nnow set fzList is null.");
				}else{
					unFzListLength = unFzList.size();
				}
			}
			
		}catch(Exception e){
			logger.warn("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".degreeId="+degreeId+",need:"+(followZombiesTotal + unFollowZombiesTotal - fzListLength)+".\nnow set fzList is null.\ncause:"+e.getMessage());
		}
		//保存喜欢
		if(likedCount > 0) {
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration, likedCount);
			List<Integer> zombieIdList = new ArrayList<Integer>();
			int likeSize = Math.round(likedCount * likeFromFollowRate/100.00f);
			int unfollowlikeSize = likedCount - likeSize;
			likeSize =  (unFzListLength >= unfollowlikeSize ? likeSize : unfollowlikeSize - unFzListLength + likeSize);
			int i,j;
			for(i = 0; i < likeSize && i < fzListLength; i++) {
				zombieIdList.add(fzList.get(i));
			}
			for(j = 0; j < likedCount - i && j < unFzListLength; j++){
				zombieIdList.add(unFzList.get(j));
			}
			batchSaveLiked(interactId, worldId, zombieIdList, dateAdded, scheduleDateList);
		}
		
		// 保存评论
		if(commentCount > 0) {
			String idStr = Arrays.toString(commentIds);
			Integer[] cids = StringUtil.convertStringToIds(idStr.substring(1, idStr.length() - 1));
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration, cids.length);
			List<Integer> zombieIdList = new ArrayList<Integer>();
			int followCommentSize = Math.round(commentCount * commentFromFollowRate);
			int i,j;
			for( i = 0; i < followCommentSize && i < fzListLength ; i++) {
				zombieIdList.add(fzList.get(i));
			}
			for(j = 0; j < commentCount - i && j < unFzListLength; j++){
				zombieIdList.add(unFzList.get(j));
			}
			batchSaveComment(interactId, worldId, zombieIdList, cids, dateAdded, scheduleDateList);
		}
		
		
		//加粉
		if(likedCount > 0){
			int followSize = Math.round(likedCount * likeToFollowRate / 100.0f);
			//如果非粉丝数量为0，则加粉的数量为0
			if (unFzListLength > 0 && unFzListLength <= Math.round(likedCount * likeToFollowRate / 100.0f)) {
				followSize = unFzList.size();
			}
			InteractUser userInteract = interactUserDao.queryUserInteractByUID(userId);
			if(userInteract != null) {
				interactId = userInteract.getId();
				// 更新用户互动
				interactUserDao.updateInteract(interactId, followSize+userInteract.getFollowCount(),
						(minuteDuration/60>0?minuteDuration/60:1)+userInteract.getDuration());
			} else {
				interactId = keyGenService.generateId(Admin.KEYGEN_INTERACT_USER_ID);
				// 保存用户互动
				interactUserDao.saveInteract(new InteractUser(interactId, 
						userId, followSize, minuteDuration/60>0?minuteDuration/60:1, dateAdded, Tag.TRUE));
			}
			
			// 保存粉丝互动
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration, followSize);
			List<InteractUserFollow> list = new ArrayList<InteractUserFollow>();
			for(int i = 0; i < followSize && i < unFzListLength; i++) {
				list.add(new InteractUserFollow(interactId, userId, unFzList.get(i),
						dateAdded, scheduleDateList.get(i), Tag.TRUE, Tag.FALSE));
			}
			
			try{
				if(list.size() > 0)
					userFollowMapper.batchSaveUserFollow(list);
			}catch(Exception e){
				logger.warn("saveInteractV3:userFollowMapper.batchSaveUserFollow failed.List<InteractUserFollow> list:"+list+"\ncause:"+e.getMessage());
				throw e;
			}
		}
		
		//更新op_zombie表中的lastmodify字段
		if(unFzList != null && unFzList.size() > 0){
			try{
				Integer[] unFzArray = new Integer[unFzList.size()];
				unFzList.toArray(unFzArray);
				zombieMapper.batchUpdateZombie(dateAdded.getTime(), 1, 1, unFzArray);
			}catch(Exception e){
				logger.warn("saveInteractV3:zombieMapper.batchUpdateZombie failed.List<InteractUserFollow> unFzList:"+unFzList+"\ncause:"+e.getMessage());
				throw e;
			}
		}
	}
	
	@Override
	public void saveChannelInteractV3(Integer userId,Integer channelId,Integer worldId,Integer clickCount,
			Integer likedCount,String[] commentIds,Integer minuteDuration)throws Exception {
		minuteDuration = minuteDuration == null ? 0 : minuteDuration; 
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
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration, countList.size());
			batchSaveClick(interactId, worldId, countList, dateAdded, scheduleDateList);
		}
		
		//
		//获取非粉僵尸用户、和用户僵尸粉所需总数
		//因为*Rate是整数，范围在0-100代表0%-100%
		//
		List<Integer> fzList = null;
		List<Integer> unFzList = null;
		int fzListLength=0;
		
		//计算粉丝马甲数
		int followZombiesTotal = likedCount * likeFromFollowRate + likedCount * ( 100 -  likeFromFollowRate)* likeToFollowRate
				> commentCount * commentFromFollowRate ? likedCount * likeFromFollowRate : commentCount * commentFromFollowRate;
		followZombiesTotal = Math.round(followZombiesTotal / 100.00f);
		int unFollowZombiesTotal = likedCount > commentCount ? likedCount - followZombiesTotal : commentCount - followZombiesTotal;
		
		/*
		 * 对比需要数和总的数据库中的数据对比
		 * @modify by zhangbo 2015-09-28
		 */
		Integer fzListTotalCount = 0;	// 频道粉丝马甲总数
		Integer unFzListTotalCount = 0;	// 频道非粉丝马甲总数
		fzListTotalCount = zombieChannelMapper.queryNotInteractNRandomFollowZombieCount(userId, channelId, worldId);
		unFzListTotalCount = zombieChannelMapper.queryNotInteractNRandomNotFollowZombieCount(userId, channelId, worldId);
		int total = likedCount > commentCount ? likedCount : commentCount;
		if(fzListTotalCount + unFzListTotalCount > total){
			if (followZombiesTotal < fzListTotalCount&&unFollowZombiesTotal >= unFzListTotalCount) {
				followZombiesTotal = total - unFzListTotalCount;
			} else if(followZombiesTotal >= fzListTotalCount&&unFollowZombiesTotal < unFzListTotalCount){
				unFollowZombiesTotal = total - followZombiesTotal;
			}
		}else {
			throw new Exception("没有足够的马甲数");
		}
		
		//查询粉丝马甲
		if ( followZombiesTotal > 0){
			try{
				fzList = zombieChannelMapper.queryNotInteractNRandomFollowZombie(userId, channelId, worldId,followZombiesTotal);
				if(fzList != null){
					fzListLength = fzList.size();
				}
			}catch(Exception e){
				logger.warn("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".worldId="+worldId+".\nnow set fzList is null.\ncause:"+e.getMessage());
				fzList = null;
				fzListLength = 0;
			}
		}
		
		//非粉丝马甲
		try{
			int unFollowZombieNeedTotal = followZombiesTotal + unFollowZombiesTotal - fzListLength;
			if(unFollowZombieNeedTotal > 0){
				unFzList = zombieChannelService.queryNotInteractNRandomNotFollowZombie(userId, channelId, worldId, unFollowZombieNeedTotal);
				if( null == unFzList || unFzList.size() == 0){
					logger.warn("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".channelId="+channelId+",need:"+unFollowZombieNeedTotal+".\nnow set fzList is null.");
					throw new Exception("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".channelId="+channelId+",need:"+unFollowZombieNeedTotal+".\nnow set fzList is null.");
				}
			}
			
		}catch(Exception e){
			logger.warn("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".channelId="+channelId+",need:"+(followZombiesTotal + unFollowZombiesTotal - fzListLength)+".\nnow set fzList is null.\ncause:"+e.getMessage());
		}
		//保存喜欢
		if(likedCount > 0) {
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration, likedCount);
			List<Integer> zombieIdList = new ArrayList<Integer>();
			int likeSize = Math.round(likedCount * likeFromFollowRate/100.00f);
/*		int unfollowlikeSize = likedCount - likeSize;
			likeSize =  unFzList.size() >= unfollowlikeSize ? likeSize : unfollowlikeSize - unFzList.size() + likeSize;*/
			int i,j;
			for(i = 0; i < likeSize && i < fzListLength; i++) {
				zombieIdList.add(fzList.get(i));
			}
			for(j = 0; j < likedCount - i && j < unFzList.size(); j++){
				zombieIdList.add(unFzList.get(j));
			}
			batchSaveLiked(interactId, worldId, zombieIdList, dateAdded, scheduleDateList);
		}
		
		// 保存评论
		if(commentCount > 0) {
			String idStr = Arrays.toString(commentIds);
			Integer[] cids = StringUtil.convertStringToIds(idStr.substring(1, idStr.length() - 1));
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration, cids.length);
			List<Integer> zombieIdList = new ArrayList<Integer>();
			int followCommentSize = Math.round(commentCount * commentFromFollowRate);
			int i,j;
			for( i = 0; i < followCommentSize && i < fzListLength ; i++) {
				zombieIdList.add(fzList.get(i));
			}
			for(j = 0; j < commentCount - i && j < unFzList.size(); j++){
				zombieIdList.add(unFzList.get(j));
			}
			
			batchSaveComment(interactId, worldId, zombieIdList, cids, dateAdded, scheduleDateList);
		}
		
		
		//加粉
		if(likedCount > 0){
			int followSize = Math.round(likedCount * likeToFollowRate / 100.0f);
			InteractUser userInteract = interactUserDao.queryUserInteractByUID(userId);
			if(userInteract != null) {
				interactId = userInteract.getId();
				// 更新用户互动
				interactUserDao.updateInteract(interactId, followSize+userInteract.getFollowCount(),
						(minuteDuration/60>0?minuteDuration/60:1)+userInteract.getDuration());
			} else {
				interactId = keyGenService.generateId(Admin.KEYGEN_INTERACT_USER_ID);
				// 保存用户互动
				interactUserDao.saveInteract(new InteractUser(interactId, 
						userId, followSize, minuteDuration/60>0?minuteDuration/60:1, dateAdded, Tag.TRUE));
			}
			
			// 保存粉丝互动
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration, followSize);
			List<InteractUserFollow> list = new ArrayList<InteractUserFollow>();
			for(int i = 0; i < followSize && i < unFzList.size(); i++) {
				list.add(new InteractUserFollow(interactId, userId, unFzList.get(i),
						dateAdded, scheduleDateList.get(i), Tag.TRUE, Tag.FALSE));
			}
			
			try{
				if(list.size() > 0)
					userFollowMapper.batchSaveUserFollow(list);
			}catch(Exception e){
				logger.warn("saveInteractV3:userFollowMapper.batchSaveUserFollow failed.List<InteractUserFollow> list:"+list+"\ncause:"+e.getMessage());
				throw e;
			}
		}
		
		//更新op_zombie表中的lastmodify字段
		if(unFzList != null && unFzList.size() > 0){
			try{
				Integer[] unFzArray = new Integer[unFzList.size()];
				unFzList.toArray(unFzArray);
				zombieMapper.batchUpdateZombie(dateAdded.getTime(), 1, 1, unFzArray);
			}catch(Exception e){
				logger.warn("saveInteractV3:zombieMapper.batchUpdateZombie failed.List<InteractUserFollow> unFzList:"+unFzList+"\ncause:"+e.getMessage());
				throw e;
			}
		}
		
	}

	

	@Override
	public void batchSaveComment(Integer interactId, Integer worldId, List<Integer> zombieIdList, Integer[] commentIdList,
			Date dateAdded, List<Date>dateSheduleList) throws Exception {
		try{
			List<InteractWorldCommentDto> list = new ArrayList<InteractWorldCommentDto>();
			for(int i=0; i < commentIdList.length; i++){
				InteractWorldCommentDto dto = new InteractWorldCommentDto();
				dto.setInteractId(interactId);
				dto.setWorldId(worldId);
				dto.setUserId(zombieIdList.get(i));
				dto.setCommentId(commentIdList[i]);
				dto.setDateAdded(dateAdded);
				dto.setDateSchedule(dateSheduleList.get(i));
				dto.setValid(Tag.TRUE);
				dto.setFinished(Tag.FALSE);
				list.add(dto);
			}
			if (list.size() > 0){
				batchSaveComment(list);
			}			
		}catch(Exception e){
			logger.warn("batchSaveClick failed\ninteractId="+interactId+"\nworldId="+worldId+"\nzombieIdList="+zombieIdList+"\ncommentIdList="+commentIdList+"\ndateAdded="+dateAdded
					+"\ndateSheduleList="+dateSheduleList+"\nCause:"+e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 批量插入评论
	 */
	@Override
	public void batchSaveComment(List<InteractWorldCommentDto> list){
		worldCommentMapper.batchSaveWorldComment(list);
	}
	
	/**
	 * 插入评论
	 */
	public void insertWorldComment(InteractWorldCommentDto dto){
		worldCommentMapper.insertWorldComment(dto);
	}
	
	@Override
	public void batchSaveClick(Integer interactId,Integer worldId,List<Integer>clickCountList,Date dateAdded,List<Date>dateSheduleList)throws Exception{
		try{
			List<InteractWorldClick> list = new ArrayList<InteractWorldClick>();
			for(int i=0; i < clickCountList.size(); i++){
				list.add(new InteractWorldClick(interactId, worldId,clickCountList.get(i),dateAdded,dateSheduleList.get(i),Tag.TRUE, Tag.FALSE));
			}
			if (list.size() > 0){
				batchSaveClick(list);
			}
		}catch(Exception e){
			logger.warn("batchSaveClick failed\ninteractId="+interactId+"\nworldId="+worldId+"\nclickCountList="+clickCountList+"\ndateAdded="+dateAdded
					+"\ndateSheduleList="+dateSheduleList+"\nCause:"+e.getMessage());
			throw e;
		}
	}
	
	@Override
	public void batchSaveClick(List<InteractWorldClick> list){
		worldClickMapper.batchSaveWorldClick(list);
	}
	
	@Override
	public void batchSaveLiked(Integer interactId,Integer worldId,List<Integer>zombieIdList,Date dateAdded,List<Date>dateSheduleList)throws Exception{
		try{
			List<InteractWorldLiked> list = new ArrayList<InteractWorldLiked>();
			for(int i=0; i < zombieIdList.size(); i++){
				list.add(new InteractWorldLiked(interactId, worldId,zombieIdList.get(i),dateAdded,dateSheduleList.get(i),Tag.TRUE, Tag.FALSE));
			}
			if (list.size() > 0){
				batchSaveLiked(list);
			}
		}catch(Exception e ){
			logger.warn("batchSaveLike failed\ninteractId="+interactId+"\nworldId="+worldId+"\nzombieIdList="+zombieIdList+"\ndateAdded="+dateAdded
					+"\ndateSheduleList="+dateSheduleList+"\nCause:"+e.getMessage());
			throw e;
		}
	}
	
	@Override
	public void batchSaveLiked(List<InteractWorldLiked> list){
		worldLikedMapper.batchSaveWorldLiked(list);
	}
	
	@Override
	public void batchSaveFollow(Integer interactId,Integer userId,List<Integer>zombieIdList,Date dateAdded,List<Date>dateSheduleList)throws Exception{
		List<InteractUserFollow> list = new ArrayList<InteractUserFollow>();
		for(int i = 0; i < zombieIdList.size(); i++) {
			list.add(new InteractUserFollow(interactId, userId, zombieIdList.get(i),
					dateAdded, dateSheduleList.get(i), Tag.TRUE, Tag.FALSE));
		}
		//批量插入
		if(list.size() > 0){
			batchSaveFollow(list);
		}
	}
	
	@Override
	public void batchSaveFollow(List<InteractUserFollow> list){
		userFollowMapper.batchSaveUserFollow(list);
	}
	
	@Override
	public void updateInteractValid(Integer maxId) throws Exception {
		InteractWorldClick worldClickDto = new InteractWorldClick();
		worldClickDto.setInteractId(maxId);
		worldClickDto.setValid(Tag.TRUE);
		
		InteractWorldLiked worldLikeDto = new InteractWorldLiked();
		worldLikeDto.setInteractId(maxId);
		worldLikeDto.setValid(Tag.TRUE);
		
		InteractWorldCommentDto worldCommentDto = new InteractWorldCommentDto();
		worldCommentDto.setInteractId(maxId);
		worldCommentDto.setValid(Tag.TRUE);
		
		interactWorldDao.updateValid(maxId, Tag.TRUE);
		worldClickMapper.updateWorldClickValidByMaxInteractId(worldClickDto);
		worldLikedMapper.updateWorldLikedValidByMaxInteractId(worldLikeDto);
		worldCommentMapper.updateWorldCommentValidByMaxInteractId(worldCommentDto);
	}
	
	@Override
	public void deleteInteract(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		interactWorldDao.deleteInteract(ids);
		worldClickMapper.batchDeleteWorldClickByInteractId(ids);
		worldLikedMapper.batchDeleteWorldLikedByInteractId(ids);
		worldCommentMapper.batchDeleteWorldCommentByInteractId(ids);
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
	public void saveUserInteract(Integer userId, Integer degreeId,Integer followCount,
			Integer minuteDuration) throws Exception {
		Date dateAdded = new Date();
		Integer interactId = null;
		
		// 过滤null
		minuteDuration = minuteDuration == null ? 0 : minuteDuration; 
		followCount = followCount == null ? 0 : followCount;
		
		InteractUser interact = interactUserDao.queryUserInteractByUID(userId);
		if(interact != null) {
			interactId = interact.getId();
			// 更新用户互动
			interactUserDao.updateInteract(interactId, followCount+interact.getFollowCount(),
					Math.round(minuteDuration/60.0f)+interact.getDuration());
		} else {
			interactId = keyGenService.generateId(Admin.KEYGEN_INTERACT_USER_ID);
			// 保存用户互动
			interactUserDao.saveInteract(new InteractUser(interactId, 
					userId, followCount, Math.round(minuteDuration/60.0f), dateAdded, Tag.TRUE));
		}
		
		// 保存粉丝互动
		if(followCount > 0) {
			List<Integer> zombieIdList = zombieMapper.queryNRandomNotFollowZombie(userId, degreeId, followCount);
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration, followCount);
			batchSaveFollow(interactId, userId, zombieIdList, dateAdded, scheduleDateList);
		}
	}
	
	@Override
	public void deleteUserInteract(String idsStr) throws Exception {
		Integer[] interactIds = StringUtil.convertStringToIds(idsStr);
		interactUserDao.deleteByIds(interactIds);
		userFollowMapper.batchDeleteUserFollowByInteractId(interactIds);
	}
	
	@Override
	public void buildFollow(final Integer interactId, int maxId, int start,
			int limit, Map<String, Object> jsonMap) throws Exception {
		
		InteractUserFollow dto = new InteractUserFollow();
		dto.setFirstRow(limit * (start -1));
		dto.setLimit(limit);
		dto.setInteractId(interactId);
		dto.setMaxId(maxId);
		long total = 0;
		Integer reMaxId = 0;
		List<InteractUserFollow> list = null;
		
		total = userFollowMapper.queryUserFollowTotalCount(dto);
		
		if (total > 0) {
			list = userFollowMapper.queryUserFollow(dto);
			if ( list != null && list.size() > 0) {
				reMaxId = list.get(0).getId();
			}
		}
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, reMaxId);
	}
	
	
	@Override
	public void commitClick() {
		Integer successfullyFinishCount = 0;
		Date currentDate = new Date();
		logger.info("click begin ：" + currentDate);
		Date startDate = new Date(currentDate.getTime() - WORKING_INTERVAL_MILLISECOND);
		
		// 提交播放互动队列
		InteractWorldClick worldClickDto = new InteractWorldClick();
		worldClickDto.setDateSchedule(currentDate);
		worldClickDto.setDateAdded(startDate);
		worldClickDto.setValid(Tag.TRUE);
		worldClickDto.setFinished(Tag.FALSE);
		List<InteractWorldClick> clickList = null;
		try{
			clickList = worldClickMapper.queryWorldClick(worldClickDto);//查询未完成的播放计划
			if(null == clickList){
				logger.warn("commitClick:worldClickMapper.queryWorldClick is null");
				return;
			}
		}catch(Exception e){
			logger.warn("commitClick:worldClickMapper.queryWorldClick failed.cause:"+e.getMessage());
		}
				
		
		//更新htworld_htworld的播放数据
		List<Integer> successId = new ArrayList<Integer>();
		for(InteractWorldClick click : clickList) {
			try {
				successfullyFinishCount++;
				webWorldService.addClickCount(click.getWorldId(), click.getClick());
				successId.add(click.getId());
			} catch (Exception e) {
				logger.warn("commitClick failed. commitClick Id = "+click.getWorldId()+".click="+click.getClick()+".id="+click.getId());
			}
		}
		
		//统一更新hts_admin.interact_world_click里面的完成情况
		try{
			if(successId.size() > 0)
				worldClickMapper.batchUpdateWorldClickFinished(Tag.TRUE, successId);
		}catch(Exception e){
			logger.warn("commitClick:worldClickMapper.batchUpdateWorldClickValid failed. ids="+successId+"\n.cause:"+e.getMessage());
		}
		
		
		Date endDate = new Date();
		logger.info("click finished：" + endDate + "," + "cost：" + (endDate.getTime() - currentDate.getTime()) + "ms.total："+clickList.size() + ". success："+successfullyFinishCount);
	}
	
	@Override
	public void commitComment() {
		Integer successfullyFinishCount = 0;
		Date currentDate = new Date();
		logger.info("comment begin ：" + currentDate);
		Date startDate = new Date(currentDate.getTime() - WORKING_INTERVAL_MILLISECOND);
		
		InteractWorldCommentDto dto = new InteractWorldCommentDto();
		dto.setDateAdded(startDate);
		dto.setDateSchedule(currentDate);
		dto.setValid(Tag.TRUE);
		dto.setFinished(Tag.FALSE);
		List<InteractWorldCommentDto> commentList = null;
		try{
			commentList = worldCommentMapper.queryWorldComment(dto);
			if( null == commentList){
				logger.info("commitComment:worldCommentMapper.queryWorldComment is null.");
				return;
			}
		}catch(Exception e){
			logger.warn("commitComment:worldCommentMapper.queryWorldComment failed.cause:"+e.getMessage());
		}
		
		// 提交评论互动队列
		List<Integer> successId = new ArrayList<Integer>();
		for(InteractWorldCommentDto comment : commentList) {
			try {
				successId.add(comment.getId());
				webWorldInteractService.saveComment(false, comment.getWorldId(), null,
						comment.getUserId(), comment.getContent(), null, null, new HashMap<String, Object>());
				successfullyFinishCount++;
			} catch (Exception e3) {
				logger.info("commitComment:webWorldInteractService.saveComment\n"+e3.getMessage());
			}
			
		}
		
		//统一更新interact_world_comment这张表的finish
		try{
			if(successId.size() > 0)
				worldCommentMapper.batchUpdateWorldCommentFinished(Tag.TRUE, successId);
		}catch(Exception e){
			logger.warn("commitComment:worldCommentMapper.batchUpdateWorldClickValid failed. ids="+successId+"\n.cause:"+e.getMessage());
		}
		
		Date endDate = new Date();
		logger.info("comment finished：" + endDate + "," + "cost：" + (endDate.getTime() - currentDate.getTime()) + "ms.总共："+commentList.size() + ". 成功："+successfullyFinishCount);
		
	}
	
	@Override
	public void commitLiked() {
		Integer successfullyFinishCount = 0;
		Date currentDate = new Date();
		logger.info("liked begin ：" + currentDate);
		Date startDate = new Date(currentDate.getTime() - WORKING_INTERVAL_MILLISECOND);
		
		InteractWorldLiked dto = new InteractWorldLiked();
		dto.setDateAdded(startDate);
		dto.setDateSchedule(currentDate);
		dto.setValid(Tag.TRUE);
		dto.setFinished(Tag.FALSE);
		List<InteractWorldLiked> likedList = null;
		try{
			likedList = worldLikedMapper.queryWorldLiked(dto);
			if( null == likedList){
				logger.info("commitLiked:worldLikedMapper.queryWorldLiked is null.");
				return;
			}
		}catch(Exception e){
			logger.warn("commitLiked:worldLikedMapper.queryWorldLiked failed.cause:"+e.getMessage());
		}
		
		// 提交喜欢互动队列
		List<Integer> successId = new ArrayList<Integer>();
		for(InteractWorldLiked liked : likedList) {
			try {
				successId.add(liked.getId());
				webWorldInteractService.saveLiked(false, liked.getUserId(), liked.getWorldId(), null);
				successfullyFinishCount++;
			}catch (Exception e) {
				logger.warn("commitLiked:webWorldInteractService.saveLiked failed.\nuserId="+liked.getUserId()+"\nworldId="+liked.getWorldId()+"\ncause:"+e.getMessage());
			}
		}
		
		//批量更新完成情况
		try{
			if(successId.size() > 0)
				worldLikedMapper.batchUpdateWorldLikedFinished(Tag.TRUE, successId);
		}catch(Exception e){
			logger.warn("worldLikedMapper.batchUpdateWorldLikedFinished failed. ids="+successId+"\n.cause:"+e.getMessage());
		}
		Date endDate = new Date();
		
		logger.info("liked finished：" + endDate + "," + "cost：" + (endDate.getTime() - currentDate.getTime()) + "ms.success:"+successfullyFinishCount);
		
	}
	
	@Override
	public void commitFollow() {
		Integer successfullyFinishCount = 0;
		Date currentDate = new Date();
		logger.info("follow begin ：" + currentDate);
		Date startDate = new Date(currentDate.getTime() - WORKING_INTERVAL_MILLISECOND);
		
		InteractUserFollow dto = new InteractUserFollow();
		dto.setDateAdded(startDate);
		dto.setDateSchedule(currentDate);
		dto.setValid(Tag.TRUE);
		dto.setFinished(Tag.FALSE);
		List<InteractUserFollow> followList = null;
		try{
			followList = userFollowMapper.queryUserFollow(dto);
			if( null == followList){
				logger.info("commitFollow:userFollowMapper.queryUserFollow is null.");
				return;
			}
		}catch(Exception e){
			logger.warn("commitFollow:userFollowMapper.queryUserFollow failed.cause:"+e.getMessage());
		}
		
		// 提交粉丝互动队列
		List<Integer> successId = new ArrayList<Integer>();
		for(InteractUserFollow follow : followList) {
			try {
				successId.add(follow.getId());
				int concernCount = NumberUtil.getRandomNum(minConcernCount, maxConcernCount);
				webUserInteractService.saveConcern(false, follow.getFollowId(), follow.getUserId(), concernCount);
				successfullyFinishCount++;
			}  catch (Exception e) {
				logger.warn("commitFollow:webUserInteractService.saveConcern failed.\nuserId="+follow.getUserId()+"\nFollowId="+follow.getFollowId()+"\ncause:"+e.getMessage());
			}
		}
		
		//批量更新完成情况
		try{
			if(successId.size() > 0)
				userFollowMapper.batchUpdateUserFollowFinished(Tag.TRUE, successId);
		}catch(Exception e){
			logger.warn("commitFollow:userFollowMapper.batchUpdateWorldLikedFinished failed. ids="+successId+"\n.cause:"+e.getMessage());
		}
		
		Date endDate = new Date();
		logger.info("follow finished：" + endDate + "," + "cost：" + (endDate.getTime() - currentDate.getTime())/1000 + "seconds");
		
	}

	@Override
	public void updateUnFinishedInteractSchedule() throws Exception {
		interactTrackerDao.updateTrackValid(Tag.FALSE);
		//取消掉评论、点赞、播放的延时
//		interactWorldClickDao.updateUnFinishedSchedule(now);
//		interactWorldCommentDao.updateUnFinishedSchedule(now);
//		interactWorldLikedDao.updateUnFinishedSchedule(now);
//		interactUserFollowDao.updateUnFinishedSchedule(now);
	}

	@Override
	public void buildTracker(Map<String, Object> jsonMap) {
		List<InteractTracker> trackerList = interactTrackerDao.queryTracker();
		jsonMap.put(OptResult.JSON_KEY_TOTAL, trackerList.size());
		jsonMap.put(OptResult.JSON_KEY_ROWS, trackerList);
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
			if(null == id){
				return;
			}
			InteractWorldClick dto = new InteractWorldClick();
			dto.setValid(valid);
			dto.setDateSchedule(date_schedule);
			dto.setId(id);
			worldClickMapper.updateWorldClick(dto);
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
			if(null == id){
				return;
			}
			InteractWorldLiked dto = new InteractWorldLiked();
			dto.setValid(valid);
			dto.setDateSchedule(date_schedule);
			dto.setId(id);
			worldLikedMapper.updateWorldLiked(dto);
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
			if(null == id){
				return;
			}
			InteractWorldCommentDto dto = new InteractWorldCommentDto();
			dto.setValid(valid);
			dto.setDateSchedule(date_schedule);
			dto.setId(id);
			worldCommentMapper.updateWorldComment(dto);
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
			InteractWorldClick dto = new InteractWorldClick();
			dto.setInteractId(interactId);
			dto.setFinished(Tag.FALSE);
			List<InteractWorldClick> interactWorldClickList = worldClickMapper.queryWorldClick(dto);
			
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
			InteractWorldLiked dto = new InteractWorldLiked();
			dto.setInteractId(interactId);
			dto.setFinished(Tag.FALSE);
			List<InteractWorldLiked> interactWorldLikedList = worldLikedMapper.queryWorldLiked(dto);
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
			InteractWorldCommentDto dto = new InteractWorldCommentDto();
			dto.setInteractId(interactId);
			dto.setFinished(Tag.FALSE);
			List<InteractWorldCommentDto> interactWorldCommentList = worldCommentMapper.queryWorldComment(dto);
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
		worldCommentMapper.batchDeleteWorldCommentById(ids);
	}
	
	/**
	 * 若是只有userId没有degreeId，就调用这个接口，否则就调用另外一个接口
	 * @param userId
	 * @param followCount
	 * @param minuteDuration
	 * @throws Exception
	 */
	@Override
	public void saveUserInteract(Integer userId,Integer followCount,Integer minuteDuration)throws Exception{
		UserLevelListDto userLevelDto = userLevelListService.QueryUserlevelByUserId(userId);
		Integer needZombieDegreeId = null;
		if( null != userLevelDto){
			try{
				List<OpZombieDegreeUserLevel> zombieDegreeUserLevelList = zombieDegreeUserLevelService.queryZombieDegree(null, null, userLevelDto.getUser_level_id());
				if(zombieDegreeUserLevelList != null && zombieDegreeUserLevelList.size() == 1){
					needZombieDegreeId = zombieDegreeUserLevelList.get(0).getZombieDegreeId();
				}else{
					needZombieDegreeId = commonZombieDegreeId;
				}
			}catch(Exception e){
				needZombieDegreeId = commonZombieDegreeId;
			}
		}else{
			throw new Exception("saveUserInteract:userLevelListService.QueryUserlevelByUserId is null.\nuserId="+userId);
		}
		saveUserInteract(userId,needZombieDegreeId,followCount,minuteDuration);
	}
	
	/**
	 * mishengliang
	 * 通过文件增加评论
	 * @throws Exception 
	 */
	@Override
	public void addCommentsByFile(File commentsFile ,Integer worldId) throws Exception{
		//默认的评论加入的标签           5为其他旧的ID
		Integer labelId  = 5;
		String commentIds = "";
//		List<Integer> list = new ArrayList<Integer>();
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(new ParsingDetector(false)); 
		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance()); 
		detector.add(UnicodeDetector.getInstance()); 
		java.nio.charset.Charset set = null;
		
		if (commentsFile == null) throw new Exception("你没有选择文件。");
		set = detector.detectCodepage(commentsFile.toURI().toURL());
		String charsetName = set.name();
		
		// 除了GB开头的编码，其他一律用UTF-8
		String charset = charsetName != null && charsetName.startsWith("GB") ? charsetName : "UTF-8";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(commentsFile), charset));
			String line = null;
			while((line = reader.readLine()) != null) {
				try{
					String commentStr = line.trim();
					Integer commentId = keyGenService.generateId(Admin.INTERACT_COMMENT_KEYID);
					if (!"".equals(commentStr)) {
						interactCommentDao.saveComment(new InteractComment(commentId, commentStr, labelId));
						commentIds += commentId + ",";
					}
				}catch(Exception e){
					log.warn("batchInsertZombieChannel error. line:"+line+".zombieId:"+worldId+"\ncause:"+e.getMessage());
				}
			}

		}finally {
			reader.close();
		}
		
		commentIds = commentIds.substring(0, commentIds.length()-1);
		//将评论添加进计划评论中
		Integer userId = interactWorldlevelDao.QueryUIDByWID(worldId);
		if (!commentIds.equals("")) {
		saveInteractV3(userId,3,worldId,6,6,commentIds.split(","),120);
	}
	}
	
	@Override
	public void saveUserInteractByWorldId(Integer worldId,Integer followCount,Integer minuteDuration)throws Exception{
		Integer uid = interactWorldlevelDao.QueryUIDByWID(worldId);
		saveUserInteract(uid,followCount,minuteDuration);
	}
	
	@Override
	public void saveInteractV3(Integer worldId,Integer clickCount,Integer likeCount,String[]commentIds,Integer minuteDuration)throws Exception{
		//查询对应的马甲zombieDegreeId
		Integer uid = interactWorldlevelDao.QueryUIDByWID(worldId);
		UserLevelListDto userLevelDto = userLevelListService.QueryUserlevelByUserId(uid);
		Integer needZombieDegreeId = null;
		if( null != userLevelDto){
			try{
				//如果clickCount、likeCount为空，则采用userlevel里的值
				
				List<OpZombieDegreeUserLevel> zombieDegreeUserLevelList = zombieDegreeUserLevelService.queryZombieDegree(null, null, userLevelDto.getUser_level_id());
				if(zombieDegreeUserLevelList != null && zombieDegreeUserLevelList.size() == 1){
					needZombieDegreeId = zombieDegreeUserLevelList.get(0).getZombieDegreeId();
				}else{
					needZombieDegreeId = commonZombieDegreeId;
				}
			}catch(Exception e){
				needZombieDegreeId = commonZombieDegreeId;
			}
		}else{
			throw new Exception("saveInteractV3:userLevelListService.QueryUserlevelByUserId is null.\nuserId="+uid);
		}
		saveInteractV3(uid,needZombieDegreeId,worldId,clickCount,likeCount,commentIds,minuteDuration);
	}
	
	@Override
	public void saveTypeInteract(Integer worldId,Integer clickCount,Integer likeCount,String[]commentIds,Integer minuteDuration)throws Exception{
		// TODO 这块因为华哥要的急，所以就新建了方法，后续重构的时候，要与saveInteractV3整合起来
		//查询对应的马甲zombieDegreeId
		Integer uid = interactWorldlevelDao.QueryUIDByWID(worldId);
		UserLevelListDto userLevelDto = userLevelListService.QueryUserlevelByUserId(uid);
		Integer needZombieDegreeId = null;
		if( null != userLevelDto){
			try{
				//如果clickCount、likeCount为空，则采用userlevel里的值
				
				List<OpZombieDegreeUserLevel> zombieDegreeUserLevelList = zombieDegreeUserLevelService.queryZombieDegree(null, null, userLevelDto.getUser_level_id());
				if(zombieDegreeUserLevelList != null && zombieDegreeUserLevelList.size() == 1){
					needZombieDegreeId = zombieDegreeUserLevelList.get(0).getZombieDegreeId();
				}else{
					needZombieDegreeId = commonZombieDegreeId;
				}
			}catch(Exception e){
				needZombieDegreeId = commonZombieDegreeId;
			}
		}else{
			throw new Exception("saveInteractV3:userLevelListService.QueryUserlevelByUserId is null.\nuserId="+uid);
		}
		saveTypeInteract(uid,needZombieDegreeId,worldId,clickCount,likeCount,commentIds,minuteDuration);
	}
	
	/**
	 * 根据织图id、织图标签，进行用户等级的互动，
	 * @param worldId
	 * @param labelIdsStr
	 * @throws Exception
	 */
	@Override
	public void saveInteractV3(Integer worldId ,String labelIdsStr)throws Exception{
		Integer uid = interactWorldlevelDao.QueryUIDByWID(worldId);
		UserLevelListDto userLevelDto = userLevelListService.QueryUserlevelByUserId(uid);
		//所有的注释是为了现在没有绑定，所以硬编码来查询织图等级。等以后绑定了，再开放
//		InteractWorldLevelListDto worldLevelListDto = interactWorldlevelListDao.queryWorldLevelListByWid(worldId); 
		Integer needZombieDegreeId = null;
		Integer clickCount = 0;
		Integer likeCount = 0;
		Integer commentCount = 0;
		Integer minuteDuration = 0;
		String[] commentIds = null;
//		if( null != worldLevelListDto){
			try{
				//获取用户等级里面的值
				ZTWorldLevelDto worldLevelDto = interactWorldlevelDao.QueryWorldlevelById(64);//(worldLevelListDto.getWorld_level_id());先硬编码，后面再优化
				clickCount = worldLevelDto.getMin_play_times() + (int)(Math.round(Math.random()*(worldLevelDto.getMax_play_times()-worldLevelDto.getMin_play_times())));
				likeCount  = worldLevelDto.getMin_liked_count() + (int)(Math.round(Math.random()*(worldLevelDto.getMax_liked_count() - worldLevelDto.getMin_liked_count())));
				commentCount = worldLevelDto.getMin_comment_count() + (int)(Math.round(Math.random()*(worldLevelDto.getMax_comment_count() - worldLevelDto.getMin_comment_count())));
				minuteDuration = worldLevelDto.getTime();
				
				//从对应的标签中获取评论
				StringBuilder sb = new StringBuilder();
				Integer [] labelIds = StringUtil.convertStringToIds(labelIdsStr);
				Integer avetege = (int)(commentCount / labelIds.length);
				avetege = avetege > 0 ? avetege : 1;
				
				for( int i=0; i<labelIds.length; i++){
					if(commentCount >= avetege){
						commentCount -= avetege;
						List<Integer> commentList = interactCommentService.getRandomCommentIds(labelIds[i], avetege);
						if(sb.length() > 0){
							sb.append(',');
						}
						if (commentList != null && commentList.size() > 0) {
						    String commentStrs = commentList.toString();
						    sb.append(commentStrs.substring(1, commentStrs.length()-1)); 
						}
					}else if( commentCount > 0){
						List<Integer> commentList = interactCommentService.getRandomCommentIds(labelIds[i], (int)commentCount);
						if(sb.length() > 0){
							sb.append(',');
						}
						if (commentList != null && commentList.size() > 0) {
						    String commentStrs = commentList.toString();
						    sb.append(commentStrs.substring(1, commentStrs.length()-1)); 
						}
						break;
					}else{
						break;
					}
				}
				
				if(sb.length() > 0){
					String commentStr = sb.toString();
					commentIds = commentStr.split(",");
				}
				
				//获取用户对应的马甲等级
				List<OpZombieDegreeUserLevel> zombieDegreeUserLevelList = zombieDegreeUserLevelService.queryZombieDegree(null, null, userLevelDto.getUser_level_id());
				if(zombieDegreeUserLevelList != null && zombieDegreeUserLevelList.size() == 1){
					needZombieDegreeId = zombieDegreeUserLevelList.get(0).getZombieDegreeId();
				}else{
					needZombieDegreeId = commonZombieDegreeId;
				}
			}catch(Exception e){
				needZombieDegreeId = commonZombieDegreeId;
			}
//		}else{
//			throw new Exception("saveInteractV3:userLevelListService.QueryUserlevelByUserId is null.\nuserId="+uid);
//		}
		saveInteractV3(uid,needZombieDegreeId,worldId,clickCount,likeCount,commentIds,minuteDuration);
	}
	
	
	@Override
	public void saveChannelInteractV3(Integer channelId,Integer worldId,Integer clickCount,
			Integer likedCount,String[] commentIds,Integer minuteDuration)throws Exception{
		Integer uid = interactWorldlevelDao.QueryUIDByWID(worldId);
		saveChannelInteractV3(uid,channelId,worldId,clickCount,likedCount,commentIds,minuteDuration);
	}
	
	@Override
	public void saveInteract(Integer interactId,Integer worldId,Integer clickCount,Integer commentCount,Integer likedCount,Integer minuteDuration,Date dateAdded){
		interactWorldDao.saveInteract(new InteractWorld(interactId, worldId, clickCount,
				commentCount, likedCount, minuteDuration/60>0?minuteDuration/60:1,//若不足一个钟，就按一个钟来计算
				dateAdded, Tag.TRUE));
	}
	
	/**
	 * 评论计划时间列表获取方法
	 * 
	 * @param begin	要做计划的开始时间
	 * @param total	计划要加的评论总数
	 * @return	评论计划时间列表，根据total总数得到，按照规则随机获取计划时间
	 * @author zhangbo	2015年11月11日
	 */
	public List<Date> getCommentsScheduleDate(Date begin, Integer total){
		
		
		// 初始化每个时间段计数对象
		int _7to9 = 0;
		int _9to12 = 0;
		int _12to15 = 0;
		int _15to19 = 0;
		int _19to21 = 0;
		int _21to23 = 0;
		int _23to3 = 0;
		
		// 初始化计划时间，容量为total
		List<Date> dateList = new ArrayList<Date>(total);
		
		// 结束时间，与开始时间间隔一天 
		Date end = new Date(begin.getTime() + 24*60*60*1000);
		
		// 定义日历对象
		Calendar ca = Calendar.getInstance(Locale.CHINA);
		
		for (int i=0; i < total; i++) {
			// 得到区间内随机时间
			Date randomDate = getRandomDate(begin, end);
			ca.setTime(randomDate);
			// 得到随机时间的小时数0-24
			int hour = ca.get(Calendar.HOUR_OF_DAY);
			
			/*
			 * 7:00-9:00最大值1
			 * 9:00-12:00最大值2
			 * 12:00-15:00最大值2
			 * 15:00-19:00最大值1
			 * 19:00-21:00最大值2
			 * 21:00-23:00最大值3
			 * 23:00-3:00最大值2
			 */
			if ( hour >= 7 && hour < 9 && _7to9 < 1) {
				dateList.add(randomDate);
				_7to9++;
			} else if ( hour >= 9 && hour < 12 && _9to12 < 2) {
				dateList.add(randomDate);
				_9to12++;
			} else if ( hour >= 12 && hour < 15 && _12to15 < 2) {
				dateList.add(randomDate);
				_12to15++;
			} else if ( hour >= 15 && hour < 19 && _15to19 < 1) {
				dateList.add(randomDate);
				_15to19++;
			} else if ( hour >= 19 && hour < 21 && _19to21 < 2) {
				dateList.add(randomDate);
				_19to21++;
			} else if ( hour >= 21 && hour < 23 && _21to23 < 3) {
				dateList.add(randomDate);
				_21to23++;
			} else if ( (hour == 23 || hour == 24 || hour == 0 || hour == 1 || hour == 2) && _23to3 < 2) {
				dateList.add(randomDate);
				_23to3++;
			}
		}
		
		// 若随机数量小于总数，则执行递归
		if (dateList.size() < total) {
			List<Date> recursionDateList = getCommentsScheduleDate(begin, total-dateList.size());
			dateList.addAll(recursionDateList);
		}
		
		return dateList;
	}
	
	/**
	 * 根据起始结束区间获取区间内的随机时间
	 * 
	 * @param beginDate	起始时间
	 * @param endDate	结束时间
	 * @return randomDate	随机时间
	 * 
	 * @author zhangbo	2015年11月11日
	 */
	private Date getRandomDate(Date beginDate, Date endDate){
		long randomLong = beginDate.getTime() + (long)(Math.random() * (endDate.getTime() - beginDate.getTime()));
		Date randomDate = new Date(randomLong);
		
		// 如果返回的是开始时间和结束时间，则递归调用本函数查找随机时间
		if( randomDate.equals(beginDate) || randomDate.equals(endDate) ){
			return getRandomDate(randomDate, endDate);  
		}
		
		return randomDate;
	}

	@Override
	public void saveAutoInteract(Integer userId, Integer degreeId, Integer worldId, Integer clickCount, Integer likedCount, String[] commentIds, Integer minuteDuration) throws Exception {
		// TODO 这块要整体整改下，要抽取公共方法，与saveTypeInteract都是copy自saveInteractV3的，这里要分业务场景进行相应的底层代码抽取
		minuteDuration = minuteDuration == null ? 0 : minuteDuration; 
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
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration, countList.size());
			batchSaveClick(interactId, worldId, countList, dateAdded, scheduleDateList);
		}
		
		//
		//获取非粉僵尸用户、和用户僵尸粉所需总数
		//因为*Rate是整数，范围在0-100代表0%-100%
		//
		List<Integer> fzList = null;
		List<Integer> unFzList = null;
		int fzListLength=0;
		int unFzListLength = 0;
		
		//计算粉丝马甲数
		int followZombiesTotal = likedCount * likeFromFollowRate + likedCount * ( 100 -  likeFromFollowRate)* likeToFollowRate
				> commentCount * commentFromFollowRate ? likedCount * likeFromFollowRate : commentCount * commentFromFollowRate;
		followZombiesTotal = Math.round(followZombiesTotal / 100.00f);
		int unFollowZombiesTotal = likedCount > commentCount ? likedCount - followZombiesTotal : commentCount - followZombiesTotal;
		
		/**
		 * 对比需要数和总的数据库中的数据对比
			mishengliang
		 */
		Integer fzListTotalCount = 0;
		Integer unFzListTotalCount = 0;
		
		//查出库中的粉丝马季和非粉丝马甲
		fzListTotalCount = zombieMapper.queryNotInteractNRandomFollowZombieCount(userId, worldId,0);
		unFzListTotalCount = zombieMapper.queryNotInteractNRandomNotFollowZombieCount(userId, degreeId,worldId,0);
		int total = likedCount > commentCount ? likedCount : commentCount;
		/**
		 * if 库中粉丝马甲+库中非粉丝马甲 > 需要马甲总数{
		 * 		if（需要粉丝马甲< 库中粉丝马甲 && 需要非粉丝马甲> 库中非粉丝马甲）{
		 * 			需要粉丝马甲 = 总马甲 - 库中非粉丝马甲； 
		 * 		}else if（需要粉丝马甲> 库中粉丝马甲 && 需要非粉丝马甲< 库中非粉丝马甲）{
		 * 			需要非粉丝马甲 = 总马甲 -  库中粉丝马甲;
		 * 		}else{
		 * 
		 * 		}
		 * }
		 * 
		 */
		if(fzListTotalCount + unFzListTotalCount > total){
			if (followZombiesTotal < fzListTotalCount&&unFollowZombiesTotal >= unFzListTotalCount) {
				followZombiesTotal = total - unFzListTotalCount;
				unFollowZombiesTotal =  unFzListTotalCount;
			} else if(followZombiesTotal >= fzListTotalCount&&unFollowZombiesTotal < unFzListTotalCount){
				unFollowZombiesTotal = total - fzListTotalCount;
				followZombiesTotal = fzListTotalCount;
			}else if((followZombiesTotal < fzListTotalCount&&unFollowZombiesTotal < unFzListTotalCount)){
				
			}
		}else {
			throw new Exception("没有足够的马甲数");
		}
		
		
		//查询粉丝马甲
		if ( followZombiesTotal > 0){
			try{
				fzList = zombieMapper.queryNotInteractNRandomFollowZombie(userId, worldId,followZombiesTotal);
				if(fzList != null){
					fzListLength = fzList.size();
				}
			}catch(Exception e){
				logger.warn("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".worldId="+worldId+".\nnow set fzList is null.\ncause:"+e.getMessage());
				fzList = null;
				fzListLength = 0;
			}
		}
		
		//查询非粉丝马甲
		try{
			int unFollowZombieNeedTotal = followZombiesTotal + unFollowZombiesTotal - fzListLength;
			if(unFollowZombieNeedTotal > 0){
				unFzList = zombieMapper.queryNotInteractNRandomNotFollowZombie(userId, degreeId,worldId,unFollowZombieNeedTotal);
				if( null == unFzList){
					logger.warn("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".degreeId="+degreeId+",need:"+unFollowZombieNeedTotal+".\nnow set fzList is null.");
					throw new Exception("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".degreeId="+degreeId+",need:"+unFollowZombieNeedTotal+".\nnow set fzList is null.");
				}else{
					unFzListLength = unFzList.size();
				}
			}
			
		}catch(Exception e){
			logger.warn("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".degreeId="+degreeId+",need:"+(followZombiesTotal + unFollowZombiesTotal - fzListLength)+".\nnow set fzList is null.\ncause:"+e.getMessage());
		}
		//保存喜欢
		if(likedCount > 0) {
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration, likedCount);
			List<Integer> zombieIdList = new ArrayList<Integer>();
			int likeSize = Math.round(likedCount * likeFromFollowRate/100.00f);
			int unfollowlikeSize = likedCount - likeSize;
			likeSize =  (unFzListLength >= unfollowlikeSize ? likeSize : unfollowlikeSize - unFzListLength + likeSize);
			int i,j;
			for(i = 0; i < likeSize && i < fzListLength; i++) {
				zombieIdList.add(fzList.get(i));
			}
			for(j = 0; j < likedCount - i && j < unFzListLength; j++){
				zombieIdList.add(unFzList.get(j));
			}
			batchSaveLiked(interactId, worldId, zombieIdList, dateAdded, scheduleDateList);
		}
		
		// 保存评论
		if(commentCount > 0) {
			String idStr = Arrays.toString(commentIds);
			Integer[] cids = StringUtil.convertStringToIds(idStr.substring(1, idStr.length() - 1));
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration, cids.length);
			List<Integer> zombieIdList = new ArrayList<Integer>();
			int followCommentSize = Math.round(commentCount * commentFromFollowRate);
			int i,j;
			for( i = 0; i < followCommentSize && i < fzListLength ; i++) {
				zombieIdList.add(fzList.get(i));
			}
			for(j = 0; j < commentCount - i && j < unFzListLength; j++){
				zombieIdList.add(unFzList.get(j));
			}
			batchSaveComment(interactId, worldId, zombieIdList, cids, dateAdded, scheduleDateList);
		}
		
		
		//加粉
		if(likedCount > 0){
			int followSize = Math.round(likedCount * likeToFollowRate / 100.0f);
			//如果非粉丝数量为0，则加粉的数量为0
			if (unFzListLength > 0 && unFzListLength <= Math.round(likedCount * likeToFollowRate / 100.0f)) {
				followSize = unFzList.size();
			}
			InteractUser userInteract = interactUserDao.queryUserInteractByUID(userId);
			if(userInteract != null) {
				interactId = userInteract.getId();
				// 更新用户互动
				interactUserDao.updateInteract(interactId, followSize+userInteract.getFollowCount(),
						(minuteDuration/60>0?minuteDuration/60:1)+userInteract.getDuration());
			} else {
				interactId = keyGenService.generateId(Admin.KEYGEN_INTERACT_USER_ID);
				// 保存用户互动
				interactUserDao.saveInteract(new InteractUser(interactId, 
						userId, followSize, minuteDuration/60>0?minuteDuration/60:1, dateAdded, Tag.TRUE));
			}
			
			// 保存粉丝互动
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration, followSize);
			List<InteractUserFollow> list = new ArrayList<InteractUserFollow>();
			for(int i = 0; i < followSize && i < unFzListLength; i++) {
				list.add(new InteractUserFollow(interactId, userId, unFzList.get(i),
						dateAdded, scheduleDateList.get(i), Tag.TRUE, Tag.FALSE));
			}
			
			try{
				if(list.size() > 0)
					userFollowMapper.batchSaveUserFollow(list);
			}catch(Exception e){
				logger.warn("saveInteractV3:userFollowMapper.batchSaveUserFollow failed.List<InteractUserFollow> list:"+list+"\ncause:"+e.getMessage());
				throw e;
			}
		}
		
		//更新op_zombie表中的lastmodify字段
		if(unFzList != null && unFzList.size() > 0){
			try{
				Integer[] unFzArray = new Integer[unFzList.size()];
				unFzList.toArray(unFzArray);
				zombieMapper.batchUpdateZombie(dateAdded.getTime(), 1, 1, unFzArray);
			}catch(Exception e){
				logger.warn("saveInteractV3:zombieMapper.batchUpdateZombie failed.List<InteractUserFollow> unFzList:"+unFzList+"\ncause:"+e.getMessage());
				throw e;
			}
		}
		
	}

	@Override
	public void saveWorldListInteract(Integer worldId,Integer clickCount,Integer likeCount,String[]commentIds,Integer minuteDuration)throws Exception{
		// TODO 这块因为华哥要的急，所以就新建了方法，后续重构的时候，要与saveInteractV3整合起来
		//查询对应的马甲zombieDegreeId
		Integer uid = interactWorldlevelDao.QueryUIDByWID(worldId);
		UserLevelListDto userLevelDto = userLevelListService.QueryUserlevelByUserId(uid);
		Integer needZombieDegreeId = null;
		if( null != userLevelDto){
			try{
				//如果clickCount、likeCount为空，则采用userlevel里的值
				
				List<OpZombieDegreeUserLevel> zombieDegreeUserLevelList = zombieDegreeUserLevelService.queryZombieDegree(null, null, userLevelDto.getUser_level_id());
				if(zombieDegreeUserLevelList != null && zombieDegreeUserLevelList.size() == 1){
					needZombieDegreeId = zombieDegreeUserLevelList.get(0).getZombieDegreeId();
				}else{
					needZombieDegreeId = commonZombieDegreeId;
				}
			}catch(Exception e){
				needZombieDegreeId = commonZombieDegreeId;
			}
		}else{
			throw new Exception("saveInteractV3:userLevelListService.QueryUserlevelByUserId is null.\nuserId="+uid);
		}
		saveWorldListInteract(uid,needZombieDegreeId,worldId,clickCount,likeCount,commentIds,minuteDuration);
	}

	/**
	 * @param uid
	 * @param needZombieDegreeId
	 * @param worldId
	 * @param clickCount
	 * @param likeCount
	 * @param commentIds
	 * @param minuteDuration
	 * @author zhangbo	2015年11月18日
	 * @throws Exception 
	 */
	private void saveWorldListInteract(Integer userId, Integer degreeId, Integer worldId, Integer clickCount, Integer likedCount, String[] commentIds, Integer minuteDuration) throws Exception {
		minuteDuration = minuteDuration == null ? 0 : minuteDuration; 
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
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration, countList.size());
			batchSaveClick(interactId, worldId, countList, dateAdded, scheduleDateList);
		}
		
		//
		//获取非粉僵尸用户、和用户僵尸粉所需总数
		//因为*Rate是整数，范围在0-100代表0%-100%
		//
		List<Integer> fzList = null;
		List<Integer> unFzList = null;
		int fzListLength=0;
		int unFzListLength = 0;
		
		//计算粉丝马甲数
		int followZombiesTotal = likedCount * likeFromFollowRate + likedCount * ( 100 -  likeFromFollowRate)* likeToFollowRate
				> commentCount * commentFromFollowRate ? likedCount * likeFromFollowRate : commentCount * commentFromFollowRate;
		followZombiesTotal = Math.round(followZombiesTotal / 100.00f);
		int unFollowZombiesTotal = likedCount > commentCount ? likedCount - followZombiesTotal : commentCount - followZombiesTotal;
		
		/**
		 * 对比需要数和总的数据库中的数据对比
			mishengliang
		 */
		Integer fzListTotalCount = 0;
		Integer unFzListTotalCount = 0;
		
		//查出库中的粉丝马季和非粉丝马甲
		fzListTotalCount = zombieMapper.queryNotInteractNRandomFollowZombieCount(userId, worldId,0);
		unFzListTotalCount = zombieMapper.queryNotInteractNRandomNotFollowZombieCount(userId, degreeId,worldId,0);
		int total = likedCount > commentCount ? likedCount : commentCount;
		/**
		 * if 库中粉丝马甲+库中非粉丝马甲 > 需要马甲总数{
		 * 		if（需要粉丝马甲< 库中粉丝马甲 && 需要非粉丝马甲> 库中非粉丝马甲）{
		 * 			需要粉丝马甲 = 总马甲 - 库中非粉丝马甲； 
		 * 		}else if（需要粉丝马甲> 库中粉丝马甲 && 需要非粉丝马甲< 库中非粉丝马甲）{
		 * 			需要非粉丝马甲 = 总马甲 -  库中粉丝马甲;
		 * 		}else{
		 * 
		 * 		}
		 * }
		 * 
		 */
		if(fzListTotalCount + unFzListTotalCount > total){
			if (followZombiesTotal < fzListTotalCount&&unFollowZombiesTotal >= unFzListTotalCount) {
				followZombiesTotal = total - unFzListTotalCount;
				unFollowZombiesTotal =  unFzListTotalCount;
			} else if(followZombiesTotal >= fzListTotalCount&&unFollowZombiesTotal < unFzListTotalCount){
				unFollowZombiesTotal = total - fzListTotalCount;
				followZombiesTotal = fzListTotalCount;
			}else if((followZombiesTotal < fzListTotalCount&&unFollowZombiesTotal < unFzListTotalCount)){
				
			}
		}else {
			throw new Exception("没有足够的马甲数");
		}
		
		
		//查询粉丝马甲
		if ( followZombiesTotal > 0){
			try{
				fzList = zombieMapper.queryNotInteractNRandomFollowZombie(userId, worldId,followZombiesTotal);
				if(fzList != null){
					fzListLength = fzList.size();
				}
			}catch(Exception e){
				logger.warn("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".worldId="+worldId+".\nnow set fzList is null.\ncause:"+e.getMessage());
				fzList = null;
				fzListLength = 0;
			}
		}
		
		//查询非粉丝马甲
		try{
			int unFollowZombieNeedTotal = followZombiesTotal + unFollowZombiesTotal - fzListLength;
			if(unFollowZombieNeedTotal > 0){
				unFzList = zombieMapper.queryNotInteractNRandomNotFollowZombie(userId, degreeId,worldId,unFollowZombieNeedTotal);
				if( null == unFzList){
					logger.warn("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".degreeId="+degreeId+",need:"+unFollowZombieNeedTotal+".\nnow set fzList is null.");
					throw new Exception("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".degreeId="+degreeId+",need:"+unFollowZombieNeedTotal+".\nnow set fzList is null.");
				}else{
					unFzListLength = unFzList.size();
				}
			}
			
		}catch(Exception e){
			logger.warn("saveInteractV3:zombieMapper.queryNotInteractNRandomFollowZombie is null. userId="+userId+".degreeId="+degreeId+",need:"+(followZombiesTotal + unFollowZombiesTotal - fzListLength)+".\nnow set fzList is null.\ncause:"+e.getMessage());
		}
		//保存喜欢
		if(likedCount > 0) {
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration, likedCount);
			List<Integer> zombieIdList = new ArrayList<Integer>();
			int likeSize = Math.round(likedCount * likeFromFollowRate/100.00f);
			int unfollowlikeSize = likedCount - likeSize;
			likeSize =  (unFzListLength >= unfollowlikeSize ? likeSize : unfollowlikeSize - unFzListLength + likeSize);
			int i,j;
			for(i = 0; i < likeSize && i < fzListLength; i++) {
				zombieIdList.add(fzList.get(i));
			}
			for(j = 0; j < likedCount - i && j < unFzListLength; j++){
				zombieIdList.add(unFzList.get(j));
			}
			batchSaveLiked(interactId, worldId, zombieIdList, dateAdded, scheduleDateList);
		}
		
		// 保存评论
		if(commentCount > 0) {
			String idStr = Arrays.toString(commentIds);
			Integer[] cids = StringUtil.convertStringToIds(idStr.substring(1, idStr.length() - 1));
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration,cids.length);
			List<Integer> zombieIdList = new ArrayList<Integer>();
			int followCommentSize = Math.round(commentCount * commentFromFollowRate);
			int i,j;
			for( i = 0; i < followCommentSize && i < fzListLength ; i++) {
				zombieIdList.add(fzList.get(i));
			}
			for(j = 0; j < commentCount - i && j < unFzListLength; j++){
				zombieIdList.add(unFzList.get(j));
			}
			batchSaveComment(interactId, worldId, zombieIdList, cids, dateAdded, scheduleDateList);
		}
		
		
		//加粉
		if(likedCount > 0){
			int followSize = Math.round(likedCount * likeToFollowRate / 100.0f);
			//如果非粉丝数量为0，则加粉的数量为0
			if (unFzListLength > 0 && unFzListLength <= Math.round(likedCount * likeToFollowRate / 100.0f)) {
				followSize = unFzList.size();
			}
			InteractUser userInteract = interactUserDao.queryUserInteractByUID(userId);
			if(userInteract != null) {
				interactId = userInteract.getId();
				// 更新用户互动
				interactUserDao.updateInteract(interactId, followSize+userInteract.getFollowCount(),
						(minuteDuration/60>0?minuteDuration/60:1)+userInteract.getDuration());
			} else {
				interactId = keyGenService.generateId(Admin.KEYGEN_INTERACT_USER_ID);
				// 保存用户互动
				interactUserDao.saveInteract(new InteractUser(interactId, 
						userId, followSize, minuteDuration/60>0?minuteDuration/60:1, dateAdded, Tag.TRUE));
			}
			
			// 保存粉丝互动
			List<Date> scheduleDateList = getScheduleV3(dateAdded, minuteDuration, followSize);
			List<InteractUserFollow> list = new ArrayList<InteractUserFollow>();
			for(int i = 0; i < followSize && i < unFzListLength; i++) {
				list.add(new InteractUserFollow(interactId, userId, unFzList.get(i),
						dateAdded, scheduleDateList.get(i), Tag.TRUE, Tag.FALSE));
			}
			
			try{
				if(list.size() > 0)
					userFollowMapper.batchSaveUserFollow(list);
			}catch(Exception e){
				logger.warn("saveInteractV3:userFollowMapper.batchSaveUserFollow failed.List<InteractUserFollow> list:"+list+"\ncause:"+e.getMessage());
				throw e;
			}
		}
		
		//更新op_zombie表中的lastmodify字段
		if(unFzList != null && unFzList.size() > 0){
			try{
				Integer[] unFzArray = new Integer[unFzList.size()];
				unFzList.toArray(unFzArray);
				zombieMapper.batchUpdateZombie(dateAdded.getTime(), 1, 1, unFzArray);
			}catch(Exception e){
				logger.warn("saveInteractV3:zombieMapper.batchUpdateZombie failed.List<InteractUserFollow> unFzList:"+unFzList+"\ncause:"+e.getMessage());
				throw e;
			}
		}
		
	}

}
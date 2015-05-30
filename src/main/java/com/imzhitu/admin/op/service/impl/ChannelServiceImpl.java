package com.imzhitu.admin.op.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;

import com.hts.web.base.HTSException;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.pojo.HTWorldDto;
import com.hts.web.common.pojo.OpChannelTopOneTitle;
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.PushUtil;
import com.hts.web.common.util.StringUtil;
import com.hts.web.common.util.UserInfoUtil;
import com.hts.web.push.service.impl.PushServiceImpl.PushFailedCallback;
import com.imzhitu.admin.common.pojo.OpChannel;
import com.imzhitu.admin.common.pojo.OpChannelCover;
import com.imzhitu.admin.common.pojo.OpChannelStar;
import com.imzhitu.admin.common.pojo.OpChannelStarDto;
import com.imzhitu.admin.common.pojo.OpChannelTopOne;
import com.imzhitu.admin.common.pojo.OpChannelTopOneDto;
import com.imzhitu.admin.common.pojo.OpChannelTopOnePeriod;
import com.imzhitu.admin.common.pojo.OpChannelTopType;
import com.imzhitu.admin.common.pojo.OpChannelWorld;
import com.imzhitu.admin.common.pojo.OpChannelWorldDto;
import com.imzhitu.admin.op.dao.ChannelCacheDao;
import com.imzhitu.admin.op.dao.ChannelCoverCacheDao;
import com.imzhitu.admin.op.dao.ChannelStarCacheDao;
import com.imzhitu.admin.op.dao.ChannelTopOneCacheDao;
import com.imzhitu.admin.op.dao.ChannelTopOneTitleCacheDao;
import com.imzhitu.admin.op.dao.UserRecommendDao;
import com.imzhitu.admin.op.mapper.ChannelCoverMapper;
import com.imzhitu.admin.op.mapper.ChannelMapper;
import com.imzhitu.admin.op.mapper.ChannelStarMapper;
import com.imzhitu.admin.op.mapper.ChannelTopOneMapper;
import com.imzhitu.admin.op.mapper.ChannelTopTypeMapper;
import com.imzhitu.admin.op.mapper.ChannelWorldMapper;
import com.imzhitu.admin.op.service.ChannelService;
import com.imzhitu.admin.op.service.OpChannelUserService;

//@Service
public class ChannelServiceImpl extends BaseServiceImpl implements
		ChannelService {

	private static final String CHANNEL_STAR_MSG_HEAD = "，恭喜！你被推荐为";
	
	private static final String CHANNEL_STAR_MSG_FOOT =  "频道的红人啦！继续发光发亮哟，么么哒！";
	
	private static final String CHANNEL_WORLD_MSG_HEAD = "，恭喜！由于你的织图棒棒的，入选";
	
	private static final String CHANNEL_WORLD_MSG_FOOT = "啦！期待你的新作哦！";
	
	private static final String CHANNEL_TOP_ONE_MSG_HEAD = "，恭喜！由于";
	
	private static final String CHANNEL_TOP_ONE_MSG_FOOT = "，你被选入TOP红人榜啦！保持长红哟！";
	
	private static final String CHANNEL_TOP_ONE_TITLE_TEXT = "本期红人榜（每日9:30更新）";
	
	/**
	 * 更新top one 的时间间隔，单位ms
	 */
	private static final long CHANNEL_TOP_ONE_UPDATE_TIME_SPAN_MS = 3*24*60*60*1000;
	
	@Value("${admin.op.channelStarLimit}")
	private Integer channelStarLimit;
	
	@Autowired
	private ChannelCacheDao channelCacheDao;
	
	@Autowired
	private ChannelStarCacheDao channelStarCacheDao;
	
	@Autowired
	private ChannelTopOneCacheDao channelTopOneCacheDao;
	
	@Autowired
	private ChannelTopOneTitleCacheDao channelTopOneTitleCacheDao;
	
	@Autowired
	private ChannelMapper channelMapper;

	@Autowired
	private ChannelStarMapper channelStarMapper;
	
	@Autowired
	private ChannelTopOneMapper channelTopOneMapper;
	
	@Autowired
	private ChannelTopTypeMapper channelTopTypeMapper;
	
	@Autowired
	private ChannelWorldMapper channelWorldMapper;
	
	@Autowired
	private OpChannelUserService opChannelUserService;
	
	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private com.hts.web.userinfo.service.UserMsgService webUserMsgService;
	
	@Autowired
	private com.hts.web.push.service.PushService pushService;
	
	@Autowired
	private com.hts.web.userinfo.dao.UserInfoDao webUserInfoDao;
	
	@Autowired
	private com.hts.web.ztworld.dao.HTWorldDao webWorldDao;
	
	@Autowired
	private com.hts.web.userinfo.service.UserInfoService webUserInfoService;
	
	@Autowired
	private UserRecommendDao userRecommendDao;
	
	@Autowired
	private ChannelCoverMapper channelCoverMapper;
	
	@Autowired
	private ChannelCoverCacheDao channelCoverCacheDao;
	
	@Autowired
	private com.hts.web.operations.dao.ChannelThemeCacheDao webThemeCacheDao;
	
	@Autowired
	private com.hts.web.operations.service.ChannelService webChannelService;

	private static final int CHANNEL_CACHE_LIMIT_2_9_89 = 8;
	
	private Integer channeCachelLimit = 1000;
	
	private Integer channelCoverLimit = 5;

	public Logger logger = Logger.getLogger(ChannelServiceImpl.class);
	
	public Integer getChannelStarLimit() {
		return channelStarLimit;
	}

	public void setChannelStarLimit(Integer channelStarLimit) {
		this.channelStarLimit = channelStarLimit;
	}
	
	public Integer getChanneCachelLimit() {
		return channeCachelLimit;
	}

	public void setChanneCachelLimit(Integer channeCachelLimit) {
		this.channeCachelLimit = channeCachelLimit;
	}

	public Integer getChannelCoverLimit() {
		return channelCoverLimit;
	}

	public void setChannelCoverLimit(Integer channelCoverLimit) {
		this.channelCoverLimit = channelCoverLimit;
	}

	@Override
	public void updateChannelCache() throws Exception {
		channelCacheDao.updateChannel(channeCachelLimit);
		webThemeCacheDao.updateTheme();
		updateChannelCoverCache();
	}

	@Override
	public void updateStarCache(OpChannelStar star) throws Exception {
		star.setFirstRow(0);
		star.setLimit(channelStarLimit);
		channelStarCacheDao.updateChannelStar(star);
	}

	@Override
	public void updateTopOneCache() throws Exception {
		channelTopOneCacheDao.updateTopOne();
		Date endDate = new Date();
		Date beginDate = new Date(endDate.getTime() - CHANNEL_TOP_ONE_UPDATE_TIME_SPAN_MS);
		updateTopOneTitleCache(beginDate, endDate);
	}

	@Override
	public void updateTopOneTitleCache(Date beginDate, Date endDate) throws Exception {
//		SimpleDateFormat format = new SimpleDateFormat("MM.dd");
//		String begin = format.format(beginDate);
//		String end = format.format(endDate);
		
		OpChannelTopOneTitle title = new OpChannelTopOneTitle();
		title.setText(CHANNEL_TOP_ONE_TITLE_TEXT);
//		title.setDateInterval("（" + begin + "~" + end + "）");
		channelTopOneTitleCacheDao.updateTitle(title);
	}
	
	@Override
	public void updateChannelWorldCache(OpChannelWorld world, Integer childCountBase) throws Exception {
		updateChannelWorldCache(world.getChannelId(), childCountBase);
	}
	
	/**
	 * 更新频道织图缓存
	 * 
	 * @param channelId
	 * @param childCountBase
	 * @throws Exception
	 */
	private void updateChannelWorldCache(Integer channelId, Integer childCountBase) throws Exception {
		Integer count = 0;
		if(childCountBase == null || childCountBase.equals(0)) {
			childCountBase = channelMapper.queryChildCountBase(channelId);
		} else if(childCountBase.equals(-1)) {
			childCountBase = 0;
		}
		count = channelWorldMapper.querySumChildCountByChannelId(channelId);
		count += childCountBase;
		
		OpChannel channel = new OpChannel();
		channel.setId(channelId);
		channel.setChildCount(count);
		channel.setChildCountBase(childCountBase);
		channelMapper.update(channel);
		
		channelCacheDao.updateChannel(channeCachelLimit);
	}

	@Override
	public void buildChannel(final OpChannel channel, int page, int rows, 
			Map<String, Object> jsonMap) throws Exception {
		buildNumberDtos(OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_ID, 
				 "getSerial", channel, page, rows, jsonMap, new NumberDtoListAdapter<OpChannel>() {
			
			@Override
			public long queryTotal(OpChannel channel) {
				return channelMapper.queryChannelCount(channel);
			}
			
			@Override
			public List<? extends AbstractNumberDto> queryList(OpChannel dto) {
				return channelMapper.queryChannels(dto);
			}
		});
	}
	
	@Override
	public OpChannel queryChannelById(Integer id) throws Exception {
		return channelMapper.queryChannelById(id);
	}
	
	@Override
	public List<OpChannel> queryAllChannel()throws Exception{
		return channelMapper.queryAllChannel();
	}
	
	@Override
	public void saveChannel(OpChannel channel) throws Exception {
		Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_ID);
		channel.setId(serial);
		channel.setSerial(serial);
		channelMapper.save(channel);
	}

	@Override
	public void updateChannel(OpChannel channel) throws Exception {
		channelMapper.update(channel);
	}

	@Override
	public void deleteChannel(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		channelMapper.deleteByIds(ids);
	}
	
	@Override
	public void addChannelSerial(String[] idStrs) throws Exception {
		for(int i = idStrs.length - 1; i >= 0; i--) {
			if(StringUtil.checkIsNULL(idStrs[i]))
				continue;
			int id = Integer.parseInt(idStrs[i]);
			Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_ID);
			OpChannel channel = new OpChannel();
			channel.setId(id);
			channel.setSerial(serial);
			channelMapper.update(channel);
		}
	}

	@Override
	public void updateChannelValid(String idsStr, Integer valid)
			throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		channelMapper.updateValidByIds(ids, valid);
	}

	@Override
	public void buildStarDto(OpChannelStar star, int page, int rows,
			Map<String, Object> jsonMap) throws Exception {
		
		if(star.getChannelId() == null && star.getUserId() == null) {
			jsonMap.put(OptResult.JSON_KEY_TOTAL, 0);
			jsonMap.put(OptResult.JSON_KEY_ROWS, new ArrayList<OpChannelStarDto>());
			return;
		}
		
		
		buildNumberDtos(OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_ID, 
				 "getChannelStarId", star, page, rows, jsonMap, new NumberDtoListAdapter<OpChannelStar>() {
			
			@Override
			public long queryTotal(OpChannelStar star) {
				return channelStarMapper.queryStarCount(star);
			}
			
			@Override
			public List<? extends AbstractNumberDto> queryList(OpChannelStar star) {
				List<OpChannelStarDto> starList = channelStarMapper.queryStars(star);
				webUserInfoService.extractVerify(starList);
				
				//获取每个因素的具体值,动态的值，因为注册时间会随着时间的流逝增加，最后发图时间也是
				Integer superbScore;	//平均几天上精选*3 对应的分数
				Integer channelScore;	//平均几天上频道*3 对应的分数
				Integer registerScore;	//注册多少个周/2
				Integer lastWorldScore;	//最近发图时间*2
				for(OpChannelStarDto dto:starList){
					try{
						superbScore = opChannelUserService.querySuperbScore(dto.getUserId());
					}catch(Exception e){
						superbScore = -1;
					}
					try{
						channelScore = opChannelUserService.queryChannelScore(dto.getUserId());
					}catch(Exception e){
						channelScore = -1;
					}
					try{
						registerScore = opChannelUserService.queryRegisterScore(dto.getUserId());
					}catch(Exception e){
						registerScore = -1;
					}
					try{
						lastWorldScore = opChannelUserService.queryLastWorldScore(dto.getUserId());
					}catch(Exception e){
						lastWorldScore = -1;
					}
					dto.setSuperbScore(superbScore);
					dto.setChannelScore(channelScore);
					dto.setRegisterScore(registerScore);
					dto.setLastWorldScore(lastWorldScore);
				}
				
				return starList;
			}
		});
		
		if(page == 1) {
			Integer maxId = channelStarMapper.queryMaxId(star.getChannelId());
			jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxId);
		}
	}

	@Override
	public void updateStar(OpChannelStar star) throws Exception {
		channelStarMapper.update(star);
	}

	@Override
	public void saveStar(OpChannelStar star) throws Exception {
		OpChannelStar starExists = channelStarMapper.queryStarByChannelId(star);
		if(starExists != null) {
			addStarId(star.getChannelId(), star.getUserId());
		} else {
			Integer id = webKeyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_STAR_ID);
			star.setId(id);
			channelStarMapper.save(star);
		}
	}

	@Override
	public void deletelStars(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		channelStarMapper.deleteByIds(ids);
	}

	@Override
	public void updateStarValid(String idsStr, Integer valid) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		channelStarMapper.updateValidByIds(ids, valid);
	}
	
	@Override
	public void addStarId(Integer channelId, String[] uidStrs) throws Exception {
		for(int i = uidStrs.length - 1; i >= 0; i--) {
			if(StringUtil.checkIsNULL(uidStrs[i]))
				continue;
			int uid = Integer.parseInt(uidStrs[i]);
			addStarId(channelId, uid);
		}
	}
	
	@Override
	public void addStarId(Integer channelId, Integer uid) throws Exception {
		Integer newId = webKeyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_STAR_ID);
		OpChannelStar star = new OpChannelStar();
		star.setId(newId);
		star.setUserId(uid);
		star.setChannelId(channelId);
		channelStarMapper.updateId(star);
	}
	
	@Override
	public void updateStarWeight(Integer id, Boolean isAdd,
			Map<String, Object> jsonMap) throws Exception {
		OpChannelStar star = new OpChannelStar();
		Integer weight = 0;
		star.setId(id);
		
		if(isAdd)
			weight = webKeyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_STAR_ID);
		
		star.setWeight(weight);
		channelStarMapper.update(star);
		
		Integer userId = channelStarMapper.queryUserIdById(id);
		Integer recommendWeight = userRecommendDao.queryWeightByUID(userId);
		if(recommendWeight == null) recommendWeight = -1;
		
		jsonMap.put(OptResult.JSON_KEY_WEIGHT, weight);
		jsonMap.put(OptResult.JSON_KEY_RECOMMEND_WEIGHT, recommendWeight);
		jsonMap.put(OptResult.JSON_KEY_USER_ID, userId);
		
	}
	
	@Override
	public void buildTopOneDto(OpChannelTopOne topOne, int page, int rows, 
			Map<String, Object> jsonMap) throws Exception {
		buildNumberDtos(OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_ID, 
				 "getTopOneId", topOne, page, rows, jsonMap, new NumberDtoListAdapter<OpChannelTopOne>() {
			
			@Override
			public long queryTotal(OpChannelTopOne topOne) {
				return channelTopOneMapper.queryTopOneCount(topOne);
			}
			
			@Override
			public List<? extends AbstractNumberDto> queryList(OpChannelTopOne topOne) {
				List<OpChannelTopOneDto> topOneList = channelTopOneMapper.queryTopOnes(topOne);
				webUserInfoService.extractVerify(topOneList);
				return topOneList;
			}
		});
	}

	@Override
	public void updateTopOne(OpChannelTopOne topOne) throws Exception {
		channelTopOneMapper.update(topOne);
	}

	@Override
	public void saveTopOne(OpChannelTopOne topOne) throws Exception {
		Integer id = webKeyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_TOP_ONE_ID);
		Integer period = channelTopOneMapper.queryMaxPeriod();
		topOne.setPeriod(period);
		topOne.setId(id);
		channelTopOneMapper.save(topOne);
	}

	@Override
	public void deleteTopOne(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		channelTopOneMapper.deleteByIds(ids);
	}

	@Override
	public void updateTopOneValid(String idsStr, Integer valid)
			throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		channelTopOneMapper.updateValidByIds(ids, valid);
	}
	
	@Override
	public void addTopOneId(String[] idStrs) throws Exception {
		for(int i = idStrs.length - 1; i >= 0; i--) {
			if(StringUtil.checkIsNULL(idStrs[i]))
				continue;
			int id = Integer.parseInt(idStrs[i]);
			Integer newId = webKeyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_TOP_ONE_ID);
			channelTopOneMapper.updateId(id, newId);
		}
	}
	
	@Override
	public List<OpChannelTopOnePeriod> queryTopOnePeriodList(OpChannelTopOnePeriod period, int start, int limit,
			Boolean addAllTag) {
		period.setFirstRow((start-1) * limit);
		period.setLimit(limit);
		List<OpChannelTopOnePeriod> list = channelTopOneMapper.queryPeriodList(period);
		if(start == 1 && addAllTag) {
			OpChannelTopOnePeriod p = new OpChannelTopOnePeriod();
			p.setPeriod(0);
			p.setText("所有期数");
			list.add(0, p);
		}
		return list;
	}

	@Override
	public List<OpChannelTopType> queryTopType(Boolean addAllTag) throws Exception {
		List<OpChannelTopType> typeList = channelTopTypeMapper.queryTopTypes();
		if(addAllTag != null && addAllTag) {
			OpChannelTopType type = new OpChannelTopType();
			type.setId(0);
			type.setTopTitle("全部类型");
			typeList.add(0, type);
		}
		return typeList;
	}

	@Override
	public void updateTopType(OpChannelTopType topType) throws Exception {
		String title = topType.getTopTitle();
		String subTitle = topType.getTopSubTitle();
		String desc = title + "（" + subTitle + "）";
		topType.setTopDesc(desc);
		channelTopTypeMapper.update(topType);
	}

	@Override
	public void saveTopType(OpChannelTopType topType) throws Exception {
		String title = topType.getTopTitle();
		String subTitle = topType.getTopSubTitle();
		String desc = title + "（" + subTitle + "）";
		topType.setTopDesc(desc);
		channelTopTypeMapper.save(topType);
	}

	@Override
	public void deleteTopTypes(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		channelTopTypeMapper.deleteByIds(ids);
	}

	@Override
	public void buildChannelWorld(OpChannelWorld world, int page,
			int rows, Map<String, Object> jsonMap) throws Exception {
		
		if(world.getChannelId() == null && world.getWorldId() == null) {
			jsonMap.put(OptResult.JSON_KEY_TOTAL, 0);
			jsonMap.put(OptResult.JSON_KEY_ROWS, new ArrayList<OpChannelStarDto>());
			return;
		}
		
		buildNumberDtos(world, page, rows, jsonMap, new NumberDtoListAdapter<OpChannelWorld>() {
			
			@Override
			public long queryTotal(OpChannelWorld world) {
				return channelWorldMapper.queryChannelWorldCount(world);
			}
			
			@Override
			public List<? extends AbstractNumberDto> queryList(OpChannelWorld world) {
				List<OpChannelWorldDto> worldList = channelWorldMapper.queryChannelWorlds(world);
				webUserInfoService.extractVerify(worldList);
				return worldList;
			}
		});
	}

	@Override
	public void saveChannelWorld(OpChannelWorld world) throws Exception {
		OpChannelWorld worldExists = channelWorldMapper.queryWorldByChannelId(world);
		if(worldExists != null) {
			addChannelWorldId(world.getChannelId(), world.getWorldId());
			
		} else {
			world.setDateAdded(new Date());
			world.setSerial(world.getWorldId());
			world.setSuperb(Tag.FALSE);
			world.setWeight(0);
			Integer id = webKeyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_WORLD_ID);
			world.setId(id);
			if(world.getAuthorId() == null) {
				Integer authorId = webWorldDao.queryAuthorId(world.getWorldId());
				world.setAuthorId(authorId);
			}
			try {
				channelWorldMapper.save(world);
			} catch(DuplicateKeyException e) {
			}
		}
		updateChannelWorldCache(world.getChannelId(), 0);
	}

	@Override
	public void updateChannelWorld(OpChannelWorld world) throws Exception {
		channelWorldMapper.update(world);
	}

	@Override
	public void deleteChannelWorlds(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		if(ids != null && ids.length > 0) {
			OpChannelWorld world = channelWorldMapper.queryChannelWorldById(ids[0]);
			channelWorldMapper.deleteByIds(ids);
			if(world != null) {
				updateChannelWorldCache(world.getChannelId(), 0);
			}
		}
	}

	@Override
	public void addChannelWorldId(Integer channelId, String[] widsStr) throws Exception {
		for(int i = widsStr.length - 1; i >= 0; i--) {
			if(StringUtil.checkIsNULL(widsStr[i]))
				continue;
			int wid = Integer.parseInt(widsStr[i]);
			addChannelWorldId(channelId, wid);
		}
	}
	
	@Override
	public void addChannelWorldId(Integer channelId, Integer[] wids) throws Exception {
		for(int i = wids.length - 1; i >= 0; i--) {
			addChannelWorldId(channelId, wids[i]);
		}
	}
	
	@Override
	public void addChannelWorldId(Integer channelId, Integer wid) throws Exception {
		Integer newId = webKeyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_WORLD_ID);
		OpChannelWorld world = new OpChannelWorld();
		world.setId(newId);
		world.setWorldId(wid);
		world.setChannelId(channelId);
		channelWorldMapper.updateId(world);
	}
	
	@Override
	public void addChannelWorldId(Integer[] ids) throws Exception {
		for(int i = ids.length - 1; i >= 0; i--) {
			addChannelWorldId(ids[i]);
		}
	}
	
	@Override
	public void addChannelWorldId(Integer id) throws Exception {
		Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_WORLD_ID);
		channelWorldMapper.updateSerialById(id, serial);
	}

	@Override
	public void updateChannelWorldValid(String idsStr, Integer valid)
			throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		channelWorldMapper.updateValidByIds(ids, valid);
		if(ids != null && ids.length > 0) {
			if(valid != null && valid.equals(Tag.TRUE)) // 生效时同时重新排序 
				addChannelWorldId(ids);
			OpChannelWorld world = channelWorldMapper.queryChannelWorldById(ids[0]);
			if(world != null) {
//				updateChannelWorldCache(world.getChannelId(), 0);
				webChannelService.updateWorldAndChildCount(world.getChannelId());
			}
		}
	}
	
	/**
	 * 根据wid更新有效性
	 * 
	 * @param wids
	 * @param valid
	 */
	@Override
	public void updateChannelWorldValid(Integer[] wids,Integer channelId,Integer valid)throws Exception{
		if(wids.length > 0 && valid != null){
			channelWorldMapper.updateValidByWIds(wids, valid);
			if(valid != null && valid.equals(Tag.TRUE)) // 生效时同时重新排序 
				addChannelWorldId(channelId, wids);
			updateChannelWorldCache(channelId, 0);
		}
	}

	@Override
	public void addStarRecommendMsg(Integer id) throws Exception {
		OpChannelStar star = channelStarMapper.queryStarById(id);
		if(star == null) 
			throw new HTSException("记录已经被删除");
		
		Integer notified = star.getNotified();
		if(notified != null && notified.equals(Tag.FALSE)) {
			OpChannel channel = channelMapper.queryChannelById(star.getChannelId());
			String channelName = channel.getChannelName();
			Integer recipientId = star.getUserId();
			Integer channelId = star.getChannelId();
			String recipientName = webUserInfoDao.queryUserNameById(recipientId);
			UserPushInfo userPushInfo = webUserInfoDao.queryUserPushInfoById(recipientId);
			Integer msgCode = UserInfoUtil.getSysMsgCode(userPushInfo.getVer(), Tag.USER_MSG_CHANNEL_STAR);
			
			String msg = CHANNEL_STAR_MSG_HEAD + channelName + CHANNEL_STAR_MSG_FOOT;
			String tip = recipientName + msg;
			String shortTip = PushUtil.getShortName(recipientName) + PushUtil.getShortTip(msg);
			
			// 保存消息
			webUserMsgService.saveSysMsg(OpServiceImpl.ZHITU_UID, recipientId, 
					tip, msgCode, recipientId, channelName, String.valueOf(channelId), null, 0);
			
			// 更新推送标记
			star.setNotified(Tag.TRUE);
			channelStarMapper.update(star);
			
			// 推送消息
			pushService.pushSysMessage(shortTip, OpServiceImpl.ZHITU_UID, tip, userPushInfo, msgCode, new PushFailedCallback() {
	
				@Override
				public void onPushFailed(Exception e) {
				}
			});
		}
	}
	
	@Override
	public void addStarRecommendMsgs(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		for(Integer id : ids) {
			addStarRecommendMsg(id);
		}
	}
	
	@Override
	public void addTopOneRecommendMsg(Integer id) throws Exception {
		OpChannelTopOne topOne = channelTopOneMapper.queryTopOneById(id);
		if(topOne == null) 
			throw new HTSException("记录已经被删除");
		
		Integer notified = topOne.getNotified();
		if(notified != null && notified.equals(Tag.FALSE)) {
			Integer recipientId = topOne.getUserId();
			String recipientName = webUserInfoDao.queryUserNameById(recipientId);
			Integer topId = topOne.getTopId();
			UserPushInfo userPushInfo = webUserInfoDao.queryUserPushInfoById(recipientId);
			Integer msgCode = UserInfoUtil.getSysMsgCode(userPushInfo.getVer(), Tag.USER_MSG_CHANNEl_TOP_ONE);
			
			String topDesc = channelTopTypeMapper.queryTitleById(topId);
			String tipHead = CHANNEL_TOP_ONE_MSG_HEAD + topDesc + CHANNEL_TOP_ONE_MSG_FOOT;
			String tip = recipientName + tipHead;
			String shortTip = PushUtil.getShortName(recipientName) + PushUtil.getShortTip(tipHead);
			
			// 保存消息
			webUserMsgService.saveSysMsg(OpServiceImpl.ZHITU_UID, recipientId, 
					tip, msgCode, recipientId, null, null, null, 0);
			
			// 更新推送标记
			topOne.setNotified(Tag.TRUE);
			channelTopOneMapper.update(topOne);
			
			// 推送消息
			pushService.pushSysMessage(shortTip, OpServiceImpl.ZHITU_UID, tip, userPushInfo, msgCode, new PushFailedCallback() {
	
				@Override
				public void onPushFailed(Exception e) {
				}
			});
		}
	}
	
	@Override
	public void addTopOneRecommendMsgs(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		for(Integer id : ids) {
			addTopOneRecommendMsg(id);
		}
	}

	@Override
	public void addChannelWorldRecommendMsg(Integer id) throws Exception {
		OpChannelWorld world = channelWorldMapper.queryChannelWorldById(id);
		if(world == null) 
			throw new HTSException("记录已经被删除");
		Integer notified = world.getNotified();
		if(notified != null && notified.equals(Tag.FALSE)) {
			OpChannel channel = channelMapper.queryChannelById(world.getChannelId());
			HTWorldDto worldDto = webWorldDao.queryHTWorldDtoById(world.getWorldId());
			String thumbPath = worldDto.getTitleThumbPath();
			String channelName = channel.getChannelName();
			Integer recipientId = world.getAuthorId();
			Integer channelId = world.getChannelId();
			String recipientName = webUserInfoDao.queryUserNameById(recipientId);
			UserPushInfo userPushInfo = webUserInfoDao.queryUserPushInfoById(recipientId);
			Integer msgCode = UserInfoUtil.getSysMsgCode(userPushInfo.getVer(), Tag.USER_MSG_CHANNEL_WORLD);
			
			String msg = CHANNEL_WORLD_MSG_HEAD + channelName + CHANNEL_WORLD_MSG_FOOT;
			String tip = recipientName + msg;
			String shortTip = PushUtil.getShortName(recipientName) + PushUtil.getShortTip(msg);
			
			// 保存消息
			webUserMsgService.saveSysMsg(OpServiceImpl.ZHITU_UID, recipientId, 
					tip, msgCode, world.getWorldId(), channelName, String.valueOf(channelId), thumbPath, 0);
			
			// 更新推送标记
			world.setNotified(Tag.TRUE);
			channelWorldMapper.update(world);
			
			// 推送消息
			pushService.pushSysMessage(shortTip, OpServiceImpl.ZHITU_UID, tip, userPushInfo, msgCode, new PushFailedCallback() {
	
				@Override
				public void onPushFailed(Exception e) {
				}
			});
		}
	}
	
	
	@Override
	public void addChannelWorldRecommendMsgByWorldId(Integer worldId) throws Exception {
		OpChannelWorld world = channelWorldMapper.queryChannelWorldByWorldId(worldId);
		if(world == null) 
			throw new HTSException("记录已经被删除");
		Integer notified = world.getNotified();
		if(notified != null && notified.equals(Tag.FALSE)) {
			OpChannel channel = channelMapper.queryChannelById(world.getChannelId());
			HTWorldDto worldDto = webWorldDao.queryHTWorldDtoById(world.getWorldId());
			String thumbPath = worldDto.getTitleThumbPath();
			String channelName = channel.getChannelName();
			Integer recipientId = world.getAuthorId();
			Integer channelId = world.getChannelId();
			String recipientName = webUserInfoDao.queryUserNameById(recipientId);
			UserPushInfo userPushInfo = webUserInfoDao.queryUserPushInfoById(recipientId);
			Integer msgCode = UserInfoUtil.getSysMsgCode(userPushInfo.getVer(), Tag.USER_MSG_CHANNEL_WORLD);
			
			String msg = CHANNEL_WORLD_MSG_HEAD + channelName + CHANNEL_WORLD_MSG_FOOT;
			String tip = recipientName + msg;
			String shortTip = PushUtil.getShortName(recipientName) + PushUtil.getShortTip(msg);
			
			// 保存消息
			webUserMsgService.saveSysMsg(OpServiceImpl.ZHITU_UID, recipientId, 
					tip, msgCode, world.getWorldId(), channelName, String.valueOf(channelId), thumbPath, 0);
			
			// 更新推送标记
			world.setNotified(Tag.TRUE);
			channelWorldMapper.update(world);
			
			// 推送消息
			pushService.pushSysMessage(shortTip, OpServiceImpl.ZHITU_UID, tip, userPushInfo, msgCode, new PushFailedCallback() {
	
				@Override
				public void onPushFailed(Exception e) {
				}
			});
		}
	}
	
	@Override
	public void updateChannelStar() throws Exception{
		Date now = new Date();
		logger.info("======更新频道明星任务开始执行，开始时间："+now);
		//查询频道类型所有类型
		List<OpChannelTopType> channelTopTypeList = channelTopTypeMapper.queryTopTypes();
		for(OpChannelTopType o:channelTopTypeList){
			OpChannelStar star = new OpChannelStar();
			star.setChannelId(o.getId());
				
			//根据频道id查询频道排名前15个用户
			List<Integer> channelUserList = opChannelUserService.queryChannelUserRankTopN(o.getId());
			for(Integer u:channelUserList){
				star.setUserId(u);
				star.setValid(Tag.FALSE);
				star.setNotified(Tag.FALSE);
					//保存
					OpChannelStar starExists = channelStarMapper.queryStarByChannelId(star);
					Integer id = webKeyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_STAR_ID);
					star.setId(id);
					if(starExists != null) {
						channelStarMapper.updateId(star);
					} else {
						channelStarMapper.save(star);
					}
					
					//更新缓存
					//channelStarCacheDao.updateChannelStar(star);
					//通知
					//addStarRecommendMsg(star.getId());
					
			}
			//更新缓存
			//updateStarCache(star);
		}
		Date end = new Date();
		logger.info("=======更新频道明星完毕。结束时间："+end + ". 费时："+(now.getTime() - end.getTime()) + "ms");
	}
	
	
	@Override
	public List<Integer> querySuperbTopOne(OpChannelTopOne dto)throws Exception{
		return channelTopOneMapper.querySuperbTopOne(dto);
	}
	
	@Override
	public List<Integer> queryBeLikedTopOne(OpChannelTopOne dto)throws Exception{
		return channelTopOneMapper.queryBeLikedTopOne(dto);
	}
	
	@Override
	public List<Integer> querylikeTopOne(OpChannelTopOne dto)throws Exception{
		return channelTopOneMapper.querylikeTopOne(dto);
	}
	
	@Override
	public List<Integer> queryFollowTopOne(OpChannelTopOne dto)throws Exception{
		return channelTopOneMapper.queryFollowTopOne(dto);
	}
	
	
	/**
	 * 发图使用标签总数最多
	 * @param dto
	 * @return
	 */
	@Override
	public List<Integer> queryLabelTopOne(OpChannelTopOne dto)throws Exception{
		return channelTopOneMapper.queryLabelTopOne(dto);
	}
	
	/**
	 * 被浏览最多
	 * @param dto
	 * @return
	 */
	@Override
	public List<Integer> queryBeClickTopOne(OpChannelTopOne dto)throws Exception{
		return channelTopOneMapper.queryBeClickTopOne(dto);
	}
	
	/**
	 * 织图最多
	 * @param dto
	 * @return
	 */
	@Override
	public List<Integer> queryWorldTopOne(OpChannelTopOne dto)throws Exception{
		return channelTopOneMapper.queryWorldTopOne(dto);
	}
	
	/**
	 * 活动最多
	 * @param dto
	 * @return
	 */
	@Override
	public List<Integer> queryActivityTopOne(OpChannelTopOne dto)throws Exception{
		return channelTopOneMapper.queryActivityTopOne(dto);
	}
	
	
	@Override
	public List<Integer> queryFollowerIncreaseTopOne(OpChannelTopOne dto)throws Exception{
		return channelTopOneMapper.queryFollowerIncreaseTopOne(dto);
	}
	
	
	/**
	 * 评论他人最多
	 * @param dto
	 * @return
	 */
	@Override
	public List<Integer> queryCommentTopOne(OpChannelTopOne dto)throws Exception{
		return channelTopOneMapper.queryCommentTopOne(dto);
	}
	
	/**
	 * 图片最多
	 * @param dto
	 * @return
	 */
	@Override
	public List<Integer> queryPictureTopOne(OpChannelTopOne dto)throws Exception{
		return channelTopOneMapper.queryPictureTopOne(dto);
	}
	
	/**
	 * 更新排行榜
	 * @param dto
	 * @return
	 */
	@Override
	public void updateTopOne()throws Exception{
		OpChannelTopOne dto = new OpChannelTopOne();
		Date now = new Date();
		Date endDate = new Date();
		Date beginDate = new Date(endDate.getTime()-CHANNEL_TOP_ONE_UPDATE_TIME_SPAN_MS);
		
		dto.setBeginDate(beginDate);
		dto.setEndDate(endDate);
		logger.info("更新频道红人Top One 排行榜计划开始实行，开始时间为："+now);
		try{
			List<Integer> userId1List =  queryBeLikedTopOne(dto);
			if(userId1List.size() > 0){
				for(Integer userId1:userId1List){
					OpChannelTopOne dto1 = buildOpChannelTopOne(userId1,3,now);
					channelTopOneMapper.insertTopOne(dto1);
					channelTopOneMapper.updateTopOneValidByTopTypeIdAndPeriod(dto1);
				}
			}
		}catch(Exception e){
			
		}
		try{
			List<Integer> userId2List =  queryCommentTopOne(dto);
			if(userId2List.size() > 0){
				for(Integer userId2:userId2List){
					OpChannelTopOne dto2 = buildOpChannelTopOne(userId2,6,now);
					channelTopOneMapper.insertTopOne(dto2);
					channelTopOneMapper.updateTopOneValidByTopTypeIdAndPeriod(dto2);
				}
			}
		}catch(Exception e){
			
		}
		try{
			List<Integer> userId3List =  queryFollowerIncreaseTopOne(dto);
			if(userId3List.size() > 0){
				for(Integer userId3:userId3List){
					OpChannelTopOne dto3 = buildOpChannelTopOne(userId3,4,now);
					channelTopOneMapper.insertTopOne(dto3);
					channelTopOneMapper.updateTopOneValidByTopTypeIdAndPeriod(dto3);
				}
			}
		}catch(Exception e){
			
		}
		
		try{
			List<Integer> userId4List =  queryFollowTopOne(dto);
			if(userId4List.size() > 0){
				for(Integer userId4:userId4List){
					OpChannelTopOne dto4 = buildOpChannelTopOne(userId4,10,now);
					channelTopOneMapper.insertTopOne(dto4);
					channelTopOneMapper.updateTopOneValidByTopTypeIdAndPeriod(dto4);
				}
			}
		}catch(Exception e){
			
		}
		
		try{
			List<Integer> userId5List =  queryLabelTopOne(dto);
			if(userId5List.size() > 0){
				for(Integer userId5:userId5List){
					OpChannelTopOne dto5 = buildOpChannelTopOne(userId5,11,now);
					channelTopOneMapper.insertTopOne(dto5);
					channelTopOneMapper.updateTopOneValidByTopTypeIdAndPeriod(dto5);
				}
			}
		}catch(Exception e){
			
		}
		try{
			List<Integer> userId6List =  querylikeTopOne(dto);
			if(userId6List.size() > 0){
				for(Integer userId6:userId6List){
					OpChannelTopOne dto6 = buildOpChannelTopOne(userId6,5,now);
					channelTopOneMapper.insertTopOne(dto6);
					channelTopOneMapper.updateTopOneValidByTopTypeIdAndPeriod(dto6);
				}
			}
		}catch(Exception e){
			
		}
		try{
			List<Integer> userId7List =  querySuperbTopOne(dto);
			if(userId7List.size() > 0){
				for(Integer userId7:userId7List){
					OpChannelTopOne dto7 = buildOpChannelTopOne(userId7,1,now);
					channelTopOneMapper.insertTopOne(dto7);
					channelTopOneMapper.updateTopOneValidByTopTypeIdAndPeriod(dto7);
				}
			}
		}catch(Exception e){
			
		}		
		
		try{
			List<Integer> userId8List =  queryBeClickTopOne(dto);
			if(userId8List.size() > 0){
				for(Integer userId8:userId8List){
					OpChannelTopOne dto8 = buildOpChannelTopOne(userId8,2,now);
					channelTopOneMapper.insertTopOne(dto8);
					channelTopOneMapper.updateTopOneValidByTopTypeIdAndPeriod(dto8);
				}
			}
		}catch(Exception e){
			
		}
		
		try{
			List<Integer> userId9List =  queryWorldTopOne(dto);
			if(userId9List.size() > 0){
				for(Integer userId9:userId9List){
					OpChannelTopOne dto9 = buildOpChannelTopOne(userId9,8,now);
					channelTopOneMapper.insertTopOne(dto9);
					channelTopOneMapper.updateTopOneValidByTopTypeIdAndPeriod(dto9);
				}
			}
		}catch(Exception e){
			
		}
		try{
			List<Integer> userId10List =  queryPictureTopOne(dto);
			if(userId10List.size() > 0){
				for(Integer userId10:userId10List){
					OpChannelTopOne dto10 = buildOpChannelTopOne(userId10,7,now);
					channelTopOneMapper.insertTopOne(dto10);
					channelTopOneMapper.updateTopOneValidByTopTypeIdAndPeriod(dto10);
				}
			}
		}catch(Exception e){
			
		}
		try{
			List<Integer> userId11List =  queryActivityTopOne(dto);
			if(userId11List.size() > 0){
				for(Integer userId11:userId11List){
					OpChannelTopOne dto11 = buildOpChannelTopOne(userId11,9,now);
					channelTopOneMapper.insertTopOne(dto11);
					channelTopOneMapper.updateTopOneValidByTopTypeIdAndPeriod(dto11);
				}
			}
		}catch(Exception e){
			
		}
		
		//更新缓存
		//updateTopOneCache();
		
		Date end = new Date();
		logger.info("更新频道红人Top One 排行榜计划实行完毕，结束时间为："+end+". 花费时间："+(end.getTime() - now.getTime()) + "ms");
	}

	@Override
	public void addChannelWorldRecommendMsgs(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		for(Integer id : ids) {
			addChannelWorldRecommendMsg(id);
		}
	}
	
	/**
	 * 构建插入top one榜所需的dto
	 * @param userId
	 * @param topId
	 * @param now
	 * @return
	 */
	private OpChannelTopOne buildOpChannelTopOne(Integer userId,Integer topId,Date now){
		OpChannelTopOne dto = new OpChannelTopOne();
		Integer id = webKeyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_TOP_ONE_ID);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String datestr = df.format(now);
		Integer intDate = Integer.parseInt(datestr);
		dto.setId(id);
		dto.setPeriod(intDate);
		dto.setTopId(topId);
		dto.setUserId(userId);
		dto.setValid(Tag.FALSE);
		dto.setNotified(Tag.FALSE);
		return dto;
	}

	@Override
	public OpChannelTopOne queryTopOneById(Integer id) {
		return channelTopOneMapper.queryTopOneById(id);
	}

	@Override
	public void saveChannelCover(OpChannelCover cover) throws Exception {
		channelCoverMapper.save(cover);
	}

	@Override
	public void updateChannelCover(OpChannelCover cover) throws Exception {
		channelCoverMapper.update(cover);
	}

	@Override
	public void deleteChannelCover(OpChannelCover cover) throws Exception {
		channelCoverMapper.deleteByChannelIdAndWID(cover);
	}

	@Override
	public void updateChannelCoverCache() throws Exception {
		List<com.hts.web.common.pojo.OpChannel> clist 
			= channelCacheDao.queryChannel(CHANNEL_CACHE_LIMIT_2_9_89);
		if(clist.size() > 0) {
			Integer[] cids = new Integer[clist.size()];
			for(int i = 0; i < clist.size(); i++) {
				cids[i] = clist.get(i).getId();
			}
			channelCoverCacheDao.updateCoverCache(cids, channelCoverLimit);
		}
	}

}

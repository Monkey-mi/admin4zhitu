package com.imzhitu.admin.op.service.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.OpChannel;
import com.imzhitu.admin.common.pojo.OpChannelNameDto;
import com.imzhitu.admin.common.pojo.OpChannelTopOne;
import com.imzhitu.admin.common.pojo.OpChannelTopOneDto;
import com.imzhitu.admin.common.pojo.OpChannelTopOnePeriod;
import com.imzhitu.admin.common.pojo.OpChannelTopType;
import com.imzhitu.admin.common.pojo.OpChannelWorld;
import com.imzhitu.admin.common.pojo.OpChannelWorldDto;
import com.imzhitu.admin.op.dao.ChannelTopOneCacheDao;
import com.imzhitu.admin.op.dao.ChannelTopOneTitleCacheDao;
import com.imzhitu.admin.op.mapper.ChannelMapper;
import com.imzhitu.admin.op.mapper.ChannelTopOneMapper;
import com.imzhitu.admin.op.mapper.ChannelTopTypeMapper;
import com.imzhitu.admin.op.mapper.ChannelWorldMapper;
import com.imzhitu.admin.op.service.ChannelService;

//@Service
public class ChannelServiceImpl extends BaseServiceImpl implements
		ChannelService {

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
	private ChannelTopOneCacheDao channelTopOneCacheDao;
	
	@Autowired
	private ChannelTopOneTitleCacheDao channelTopOneTitleCacheDao;
	
	@Autowired
	private ChannelMapper channelMapper;

	@Autowired
	private ChannelTopOneMapper channelTopOneMapper;
	
	@Autowired
	private ChannelTopTypeMapper channelTopTypeMapper;
	
	@Autowired
	private ChannelWorldMapper channelWorldMapper;
	
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
	private com.hts.web.operations.service.ChannelService webChannelService;

	private Integer channelCoverLimit = 5;

	public Logger logger = Logger.getLogger(ChannelServiceImpl.class);
	
	public Integer getChannelStarLimit() {
		return channelStarLimit;
	}

	public void setChannelStarLimit(Integer channelStarLimit) {
		this.channelStarLimit = channelStarLimit;
	}
	
	public Integer getChannelCoverLimit() {
		return channelCoverLimit;
	}

	public void setChannelCoverLimit(Integer channelCoverLimit) {
		this.channelCoverLimit = channelCoverLimit;
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
		OpChannelTopOneTitle title = new OpChannelTopOneTitle();
		title.setText(CHANNEL_TOP_ONE_TITLE_TEXT);
		channelTopOneTitleCacheDao.updateTitle(title);
	}
	
	@Override
	public void updateChannelWorldCache(OpChannelWorld world, Integer childCountBase) throws Exception {
		webChannelService.updateWorldAndChildCount(world.getChannelId());
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
			jsonMap.put(OptResult.JSON_KEY_ROWS, new ArrayList<OpChannelWorld>());
			return;
		}
		
		buildNumberDtos(world, page, rows, jsonMap, new NumberDtoListAdapter<OpChannelWorld>() {
			
			@Override
			public long queryTotal(OpChannelWorld world) {
				return channelWorldMapper.queryChannelWorldCount(world);
			}
			
			@Override
			public List<? extends AbstractNumberDto> queryList(OpChannelWorld world) {
				final List<OpChannelWorldDto> worldList = channelWorldMapper.queryChannelWorlds(world);
				if(worldList.size() > 0) {
					Map<Integer, Integer> idxMap = new HashMap<Integer, Integer>();
					Integer[] wids = new Integer[worldList.size()];
					for(int i = 0; i < worldList.size(); i++) {
						Integer wid = worldList.get(i).getWorldId();
						wids[i] = wid;
						idxMap.put(wid, i);
					}
					List<OpChannelNameDto> nameList = channelWorldMapper.queryChannelNameByWIDs(wids);
					for(int i = 0; i < nameList.size(); i++) {
						Integer wid = nameList.get(i).getWorldId();
						String name = nameList.get(i).getChannelName();
						Integer idx = idxMap.get(wid);
						worldList.get(idx).getMultiple().add(name);
					}
				}
				webUserInfoService.extractVerify(worldList);
				return worldList;
			}
		}, new NumberDtoListMaxIdAdapter(){

		    @Override
		    public Serializable getMaxId(
			    List<? extends Serializable> list) throws Exception {
			return channelWorldMapper.queryChannelWorldMaxId();
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
			world.setSuperb(Tag.FALSE);
			world.setWeight(0);
			Integer id = webKeyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_WORLD_ID);
			world.setSerial(id);
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
//		updateChannelWorldCache(world.getChannelId(), 0);
		webChannelService.updateWorldAndChildCount(world.getChannelId());
	}

	@Override
	public void updateChannelWorld(OpChannelWorld world) throws Exception {
	    if ( world.getId() == null || world.getId().equals("") ) {
		channelWorldMapper.updateChannelWorldByWorldIdAndChannelId(world);
	    } else {
		channelWorldMapper.update(world);
	    }
	}

	@Override
	public void deleteChannelWorlds(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		if(ids != null && ids.length > 0) {
			OpChannelWorld world = channelWorldMapper.queryChannelWorldById(ids[0]);
			channelWorldMapper.deleteByIds(ids);
			if(world != null) {
//				updateChannelWorldCache(world.getChannelId(), 0);
				webChannelService.updateWorldAndChildCount(world.getChannelId());
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
		world.setSerial(newId);
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
//			updateChannelWorldCache(channelId, 0);
			webChannelService.updateWorldAndChildCount(channelId);
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
			webUserMsgService.saveSysMsg(Admin.ZHITU_UID, recipientId, 
					tip, msgCode, recipientId, null, null, null, 0);
			
			// 更新推送标记
			topOne.setNotified(Tag.TRUE);
			channelTopOneMapper.update(topOne);
			
			// 推送消息
			pushService.pushSysMessage(shortTip, Admin.ZHITU_UID, tip, userPushInfo, msgCode, new PushFailedCallback() {
	
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
			webUserMsgService.saveSysMsg(Admin.ZHITU_UID, recipientId, 
					tip, msgCode, world.getWorldId(), channelName, String.valueOf(channelId), thumbPath, 0);
			
			// 更新推送标记
			world.setNotified(Tag.TRUE);
			channelWorldMapper.update(world);
			
			// 推送消息
			pushService.pushSysMessage(shortTip, Admin.ZHITU_UID, tip, userPushInfo, msgCode, new PushFailedCallback() {
	
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
			webUserMsgService.saveSysMsg(Admin.ZHITU_UID, recipientId, 
					tip, msgCode, world.getWorldId(), channelName, String.valueOf(channelId), thumbPath, 0);
			
			// 更新推送标记
			world.setNotified(Tag.TRUE);
			channelWorldMapper.update(world);
			
			// 推送消息
			pushService.pushSysMessage(shortTip, Admin.ZHITU_UID, tip, userPushInfo, msgCode, new PushFailedCallback() {
	
				@Override
				public void onPushFailed(Exception e) {
				}
			});
		}
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
	public void searchChannel(String query, Integer maxId, Integer start, Integer limit,
			Map<String, Object> jsonMap) throws Exception {
		final OpChannel channel = new OpChannel();
		channel.setMaxId(maxId);
		channel.setValid(Tag.TRUE); // 只查询有效频道
		if(!StringUtil.checkIsNULL(query)) {
			try {
				Integer id = Integer.parseInt(query);
				channel.setId(id);
			} catch(NumberFormatException e) {
				channel.setChannelName("%" + query + "%");
			}
		}
		buildNumberDtos(OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL, OptResult.JSON_KEY_MAX_ID, 
				 "getSerial", channel, start, limit, jsonMap, new NumberDtoListAdapter<OpChannel>() {
			
			@Override
			public long queryTotal(OpChannel topOne) {
				return channelMapper.searchChannelCount(channel);
			}
			
			@Override
			public List<? extends AbstractNumberDto> queryList(OpChannel topOne) {
				return channelMapper.searchChannel(channel);
			}
		});
	}

	@Override
	public void updateChannelWorldValid(Integer channelId, Integer worldId,
			Integer valid) throws Exception {
		Integer serial = webKeyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_WORLD_ID);
		channelWorldMapper.updateValidAndSerialByWID(channelId, worldId, valid, serial);
		webChannelService.updateWorldAndChildCount(channelId);
	}

	@Override
	public void updateChannelWorldSuperb(Integer channelId, Integer worldId,
			Integer superb) throws Exception {
		channelWorldMapper.updateSuperbByWID(channelId, worldId, superb);
		webChannelService.updateSuperbCount(channelId);
	}

}

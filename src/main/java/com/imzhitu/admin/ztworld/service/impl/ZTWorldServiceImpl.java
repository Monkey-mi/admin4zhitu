package com.imzhitu.admin.ztworld.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowCallback;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldFilterLogo;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.Log;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.aliyun.service.OpenSearchService;
import com.imzhitu.admin.common.WorldWithInteract;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.OpActivityWorldValidDto;
import com.imzhitu.admin.common.pojo.OpChannelWorld;
import com.imzhitu.admin.common.pojo.OpNearLabelWorldDto;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.common.pojo.UserLevelDto;
import com.imzhitu.admin.common.pojo.UserMsgAtWorldDto;
import com.imzhitu.admin.common.pojo.UserTrust;
import com.imzhitu.admin.common.pojo.ZTWorldDto;
import com.imzhitu.admin.common.util.AdminUtil;
import com.imzhitu.admin.interact.dao.InteractWorldDao;
import com.imzhitu.admin.interact.service.InteractTypeOptionWorldService;
import com.imzhitu.admin.interact.service.InteractWorldlevelListService;
import com.imzhitu.admin.op.mapper.ChannelWorldMapper;
import com.imzhitu.admin.op.mapper.OpNearLabelWorldMapper;
import com.imzhitu.admin.userinfo.dao.UserTrustDao;
import com.imzhitu.admin.userinfo.service.UserInfoService;
import com.imzhitu.admin.userinfo.service.UserInteractService;
import com.imzhitu.admin.ztworld.dao.HTWorldCacheDao;
import com.imzhitu.admin.ztworld.dao.HTWorldChildWorldDao;
import com.imzhitu.admin.ztworld.dao.HTWorldFilterLogoCacheDao;
import com.imzhitu.admin.ztworld.dao.HTWorldLabelWorldDao;
import com.imzhitu.admin.ztworld.mapper.UserMsgAtWorldMapper;
import com.imzhitu.admin.ztworld.mapper.ZTWorldMapper;
import com.imzhitu.admin.ztworld.pojo.ZTWorld;
import com.imzhitu.admin.ztworld.service.ZTWorldService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * <p>
 * 世界管理访问业务逻辑对象
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
//@Service
public class ZTWorldServiceImpl extends BaseServiceImpl implements ZTWorldService{

	private Integer cacheLatestSize = 10; // 最新织图缓存队列大小
	Logger log = Logger.getLogger(ZTWorldServiceImpl.class);

	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private com.hts.web.ztworld.dao.HTWorldDao webWorldDao;
	
	@Autowired
	private com.hts.web.ztworld.dao.HTWorldCacheDao webWorldCacheDao;
	
	@Autowired
	private com.hts.web.userinfo.service.UserInfoService webUserInfoService;
	
	@Autowired
	private ZTWorldMapper ztWorldMapper;
	
	@Autowired
	private HTWorldCacheDao worldCacheDao;
	
	@Autowired
	private HTWorldChildWorldDao worldChildWorldDao;
	
	@Autowired
	private InteractWorldDao interactWorldDao;
	
	@Autowired
	private HTWorldLabelWorldDao worldLabelWorldDao;
	
	@Autowired
	private InteractWorldlevelListService interactWorldlevelListService;
	
	@Autowired
	private HTWorldFilterLogoCacheDao worldFilterLogoCacheDao;
	
	@Autowired
	private UserTrustDao userTrustDao;
	
	@Autowired
	private ChannelWorldMapper channelWorldMapper;
	
	@Autowired
	private OpenSearchService openSearchService;
	
	@Autowired
	private UserMsgAtWorldMapper userMsgAtWorldMapper;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private UserInteractService userInteractService;
	
	@Autowired
	private InteractTypeOptionWorldService worldSuperReserveService;
	
	@Autowired
	private OpNearLabelWorldMapper nearLabelWorldMapper;
	
	public Integer getCacheLatestSize() {
		return cacheLatestSize;
	}

	public void setCacheLatestSize(Integer cacheLatestSize) {
		this.cacheLatestSize = cacheLatestSize;
	}

	@Override
	public void buildWorld(int maxId, int start, int limit, String startDateStr, String endDateStr, String shortLink, Integer phoneCode, String label, String authorName, Integer valid, Integer shield, String worldDesc, String worldLocation,
			Integer user_level_id, String orderKey, String orderBy, Integer isZombie,Map<String, Object> jsonMap) throws Exception {
		ZTWorldDto dto = new ZTWorldDto();
		dto.setMaxId(maxId);
		dto.setLimit(limit);
		dto.setFirstRow((start - 1) * limit);
		Date now = new Date();

		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(startDateStr == null || endDateStr == null) { // 默认查询今天的所有织图
			dto.setDateAdded(now);
			dto.setDateModified(new Date(now.getTime()+24*60*60*1000));
		}else {
			if(startDateStr != null){
				dto.setDateAdded(date.parse(startDateStr));
			}
			if(endDateStr != null){
				dto.setDateModified(date.parse(endDateStr));
			}
		}
		
		if(orderKey == null || orderBy == null) {
			orderKey = "dateModified";
			orderBy = "desc";
		}
		orderKey = convertOrderKey(orderKey);
		dto.setOrderKey(orderKey);
		
		//等级查询条件
		if(user_level_id != null){
			dto.setUser_level_id(user_level_id);
		}
		// 装载短链查询条件
		if(!StringUtil.checkIsNULL(shortLink)) {
			try {
				Integer worldId = Integer.parseInt(shortLink);
				dto.setWorldId(worldId);
			}catch(NumberFormatException e) {
				dto.setShortLink(shortLink);
			}
			dto.setDateAdded(null);
			dto.setDateModified(null);
		}
		
		// 装载用户信息查询条件
		if(!StringUtil.checkIsNULL(authorName)) {
			// TODO 这里判断是否为数字不建议使用捕获异常方式，因为会消耗性能	zhangbo 2015-12-17 19:44
			try {
				Integer uid = Integer.parseInt(authorName);
				dto.setAuthorId(uid);
			} catch(NumberFormatException e) {
				dto.setAuthorName(authorName);
			}
			dto.setDateAdded(null);
			dto.setDateModified(null);
		}

		// 装载织图查询条件
		if(!StringUtil.checkIsNULL(label)) {
			dto.setWorldLabel(label);
		}
		
		// 装载客户端查询条件
		if(phoneCode != null) {
			dto.setPhoneCode(phoneCode);
		}
		
		//织图按有效性查找
		if(valid != null) {
			dto.setValid(valid);
		}
		
		if(shield != null) {
			dto.setShield(shield);
		}
		
		if(worldDesc != null) {
			dto.setWorldDesc("%" + worldDesc + "%");
			dto.setDateAdded(null);
			dto.setDateModified(null);
		}
		
		//是否为马甲织图
		if (isZombie != null) {
			dto.setIsZombie(isZombie);
		}
		
		// 定义根据OpenSearch查询回来的Map
		Map<String, Object> openSearchMap = null;
		// 当地理位置信息不为空时，则查询该地理位置对应的world_id集合
		if ( worldLocation != null ) {
			// 根据前台传递过来的起始页start，来构造OpenSearch查询分页需要的起始位置startHit，limit与前台传递过来的分页每页数量保持一致
			int startHit = (start - 1) * limit;
			
			openSearchMap = getWorldIdsByLocationWithOpenSearch(worldLocation, startHit, limit);
			Integer[] worldIds = (Integer[]) openSearchMap.get("worldIds");
			Log.info("worldLocation query result worldIds' length : " + worldIds.length);
			if ( worldIds.length != 0 ) {
				dto.setWorld_Ids(worldIds);
				dto.setDateAdded(null);
				dto.setDateModified(null);
				// 因为在OpenSearch时已经分页过了，所以在查询时不使用limit，设置为null
				dto.setLimit(null);
				dto.setFirstRow(null);
			}
		}
		
		List<ZTWorldDto> dtoList = null;
		long totalCount = 0;
		dto.setMaxId(maxId);
		// 根据用户等级查询织图
		if(dto.getUser_level_id() != null || dto.getAuthorName() != null){
			dtoList = ztWorldMapper.queryHTWorldByAttrMap(dto);
			totalCount = ztWorldMapper.queryHTWorldCountByAttrMap(dto);
		}else{
			dtoList = ztWorldMapper.queryHTWorld(dto);
			totalCount = ztWorldMapper.queryHTWorldTotalCount(dto);
		}
		
		
		// TODO 这个地方的逻辑是暂时的，等这个方法重构的时候，可以把这块逻辑重新整理，不用这样再去获取total了
		if ( worldLocation != null ) {
			totalCount = (Long) openSearchMap.get("worldTotal");
		}
		
		if(maxId <= 0) {
			if(dtoList.size() > 0) {
//				maxId  = ztWorldMapper.queryMaxId(dto);
				maxId = dtoList.get(0).getWorldId();
			}
			
		}
		extractInteractInfo(dtoList);
		extractActivityInfo(dtoList);
		webUserInfoService.extractVerify(dtoList);
		
		for(ZTWorldDto o:dtoList){
			//链接构造
//			o.setShortLink(prefix + o.getShortLink());
			
			//查询是谁添加信任的 TODO 查询信任列表可以去除，放入用户详细信息页面
			UserTrust userTrust = userTrustDao.queryUserTrustByUid(o.getAuthorId());
			if(userTrust != null){
				o.setTrustModifyDate(userTrust.getModifyDate());
				o.setTrustOperatorId(userTrust.getOperatorId());
				o.setTrustOperatorName(userTrust.getOperatorName());
			}else{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date trustModifyDate = df.parse("2011-08-20 00:00:00");
				o.setTrustModifyDate(trustModifyDate);
				o.setTrustOperatorId(0);
				o.setTrustOperatorName("暂无");
			}
			
			//查询频道
			OpChannelWorld world = new OpChannelWorld();
			world.setWorldId(o.getWorldId());
//			world.setValid(Tag.TRUE);
			long r = channelWorldMapper.queryChannelWorldCount(world);
			if(r == 0)
				o.setChannelName("NO_EXIST");
			else {
				List<String>strList = channelWorldMapper.queryChannelNameByWorldId(o.getWorldId());
				// TODO 频道名称也不用再去查询计算， 直接获取织图上面存的频道名称
				
				o.setChannelName(strList.toString());
			}
			
			//查询是否被添加分类互动
			try{
				if(interactWorldlevelListService.chechWorldLevelListIsExistByWId(o.getWorldId())){
					o.setTypeInteracted(1);
				}else{
					o.setTypeInteracted(0);
				}
//				if(interactActiveOperatedService.checkIsOperatedByWId(dto.getWorldId())){
//					dto.setActiveOperated(1);
//				}else{
//					dto.setActiveOperated(0);
//				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxId);
		jsonMap.put(OptResult.JSON_KEY_ROWS, dtoList);
		jsonMap.put(OptResult.TOTAL, totalCount);

	}
	
	/**
	 * 查询织图描述中被@人的信息
	 * @param worldId 
	 * @param jsonMap
	 * @throws Exception 
		*	2015年11月10日
		*	mishengliang
	 */
	@Override
	public void  queryCommentAt(int worldId,Map<String, Object> jsonMap){
		List<UserMsgAtWorldDto> list = userMsgAtWorldMapper.queryAtWorldByWorldId(worldId); 
		jsonMap.put(OptResult.JSON_KEY_OBJ,list);
	}
	
	/**
	 * 根据查询的地理位置信息，得到相关的织图主键id集合，使用OpenSearch进行查询 
	 * 
	 * @param worldLocation	地理位置信息
	 * @param startHit		OpenSearch开始查询位置 
	 * @param limit 		OpenSearch本次查询多少条
	 * 
	 * @return map	存储了从OpenSearch根据条件查询到的分页结果，及worldIds：织图主键id集合，还包括根据条件查询的总数，及worldTotal
	 * 
	 * @author zhangbo	2015年8月27日
	 * @throws Exception 
	 */
	private Map<String, Object> getWorldIdsByLocationWithOpenSearch(String worldLocation, int startHit, int limit) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Integer> list = new ArrayList<Integer>();
		
		JSONObject resultJson = openSearchService.queryHTWolrdLocationInfo(worldLocation, startHit, limit);
		JSONArray resultJsonArray = resultJson.getJSONArray("items");
		for (int i = 0; i < resultJsonArray .size(); i++) {
			JSONObject jObject = resultJsonArray.getJSONObject(i);
			list.add(jObject.getInt("id"));
		}
		Integer[] result = new Integer[1];
		result = list.toArray(result);
		Log.info("OpenSearch result worldIds' length : " + result.length);
		resultMap.put("worldIds", result);
		resultMap.put("worldTotal", resultJson.getLong("total"));
		return resultMap;
	}

	/**
	 * 转换排序键
	 * @param orderKey
	 * @return
	 */
	private String convertOrderKey(String orderKey) {
		String covertKey = "id";
		if(StringUtil.checkIsNULL(orderKey)) {
			covertKey = "id";
		} else if("likeCount".equals(orderKey)) {
			covertKey = "like_count";
		} else if("commentCount".equals(orderKey)) {
			covertKey = "comment_count";
		} else if("keepCount".equals(orderKey)) {
			covertKey = "keep_count";
		} else if("clickCount".equals(orderKey)) {
			covertKey = "click_count";
		} else if("dateAdded".equals(orderKey)) {
			covertKey = "date_added";
		} else if("dateModified".equals(orderKey)) {
			covertKey = "date_modified";
		}
		return covertKey;
	}
	

	@Override
	public void shieldWorld(Integer worldId) throws Exception{
		ZTWorldDto dto = new ZTWorldDto();
		dto.setWorldId(worldId);
		dto.setShield(Tag.TRUE);
		ztWorldMapper.updateWorldShield(dto);
//		worldDao.updateWorldShield(worldId, Tag.TRUE);
	}

	@Override
	public void unShieldWorld(Integer worldId) throws Exception{
		ZTWorldDto dto = new ZTWorldDto();
		dto.setWorldId(worldId);
		dto.setShield(Tag.FALSE);
		ztWorldMapper.updateWorldShield(dto);
//		worldDao.updateWorldShield(worldId, Tag.FALSE);
	}

	@Override
	public void updateWorldByJSON(String idKey, String worldJSON) throws Exception {
		
		JSONArray jsArray = JSONArray.fromObject(worldJSON);
		for(int i = 0; i < jsArray.size(); i++) {
			ZTWorldDto dto = new ZTWorldDto();
			JSONObject jsObj = jsArray.getJSONObject(i);
			int id = jsObj.optInt(idKey);
//			Map<String, Object> attrMap = new LinkedHashMap<String, Object>();
//			attrMap.put("click_count", jsObj.optInt("clickCount"));
//			attrMap.put("like_count", jsObj.optInt("likeCount"));
			dto.setClickCount(jsObj.optInt("clickCount"));
			dto.setLikeCount(jsObj.optInt("likeCount"));
			dto.setWorldId(id);
			Integer authorId = jsObj.optInt("authorId");
			if(authorId != null && authorId != 0) {
				HTWorld htworld = webWorldDao.queryWorldById(id);
				Integer oriAuthorId = htworld.getAuthorId();
				if(oriAuthorId != 0 && !oriAuthorId.equals(authorId)) {
//					int oriWorldCount = webUserInfoDao.queryWorldCountForUpdate(oriAuthorId);
//					webUserInfoDao.updateWorldCount(oriAuthorId, ++oriWorldCount);
//					int worldCount = webUserInfoDao.queryWorldCountForUpdate(authorId);
//					webUserInfoDao.updateWorldCount(authorId, ++worldCount);
				}
//				attrMap.put("author_id", authorId);
				dto.setAuthorId(authorId);
			}
//			worldDao.updateWorld(id, attrMap);
			ztWorldMapper.updateWorld(dto);
		}
	}

	@Override
	public void deleteWorld(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
//		worldDao.deleteByIds(ids);
		ztWorldMapper.deleteByIds(ids);
		worldChildWorldDao.deleteByWorldIds(ids);
	}

	@Override
	public void updateLatestValid(Integer id, Integer valid) throws Exception {
		HTWorld world = webWorldDao.queryWorldById(id);
		Integer latestValid = Tag.FALSE;
		if(valid.equals(Tag.TRUE)) {
			webWorldCacheDao.saveLatestCache(world);
			latestValid = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_HTWORLD_ID);
		} else {
			worldCacheDao.deleteLatestCache(id);
		}
		ZTWorldDto dto = new ZTWorldDto();
		dto.setWorldId(id);
		dto.setValid(latestValid);
		dto.setDateAdded(new Date());
		ztWorldMapper.updateLatestValid(dto);
//		worldDao.updateLatestValid(id, latestValid, new Date());
	}
	
	@Override
	public void deleteOverFlowLatestCache() throws Exception {
		worldCacheDao.deleteOverFlowLatestCache(cacheLatestSize);
	}
	
	private void extractActivityInfo(final List<ZTWorldDto> worldList) {
		int len = worldList.size();
		if(len > 0) {
			final Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			Integer[] wids = new Integer[len];
			for(int i = 0; i < len; i++) {
				WorldWithInteract w = worldList.get(i);
				wids[i] = w.getWorldId();
				indexMap.put(w.getWorldId(), i);
			}
			
			worldLabelWorldDao.queryWorldIds(wids, Tag.WORLD_LABEL_ACTIVITY, 
					new RowCallback<OpActivityWorldValidDto>() {
				
				@Override
				public void callback(OpActivityWorldValidDto t) {
					Integer index = indexMap.get(t.getWorldId());
					if(index != null) {
						ZTWorldDto dto = worldList.get(index);
						if(dto.getActiveOperated() == null || !dto.getActiveOperated().equals(Tag.TRUE))
							dto.setActiveOperated(t.getValid());
					}
				}
			});
		}
	}
	
	@Override
	public void extractInteractInfo(final List<? extends WorldWithInteract> worldList) {
		int len = worldList.size();
		if(len > 0) {
			final Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			Integer[] wids = new Integer[len];
			for(int i = 0; i < len; i++) {
				WorldWithInteract w = worldList.get(i);
				wids[i] = w.getWorldId();
				indexMap.put(w.getWorldId(), i);
			}
			interactWorldDao.queryWorldIdByWIDs(wids, new RowCallback<Integer>() {
				
				@Override
				public void callback(Integer t) {
					Integer index = indexMap.get(t);
					if(index != null) 
						worldList.get(index).setInteracted(Tag.TRUE);;
				}
			});
		}
	}
	@Override
	public void updateFilterLogo(Float ver, String logoPath,
			String logoDesc, Integer valid) {
		worldFilterLogoCacheDao.updateLogo(new HTWorldFilterLogo(ver, logoPath,
				logoDesc, valid));
	}
	
	@Override
	public void buildFilterLogo(Map<String, Object> jsonMap) throws Exception {
		List<HTWorldFilterLogo> list = new ArrayList<HTWorldFilterLogo>();
		HTWorldFilterLogo logo = worldFilterLogoCacheDao.queryLogo();
		list.add(logo);
		jsonMap.put(OptResult.ROWS, list);
		jsonMap.put(OptResult.TOTAL, list.size());
	}
	
	@Override
	public HTWorldFilterLogo getFilterLogo() throws Exception {
		return worldFilterLogoCacheDao.queryLogo();
	}

	@Override
	public void updateLatestInvalid(Integer authorId) throws Exception {
		ztWorldMapper.updateLatestInvalid(authorId);
	}

	@Override
	public void refreshChannelNamesAndChannelIds(Integer worldId) throws Exception {
		
		//查询频道
		OpChannelWorld world = new OpChannelWorld();
		world.setWorldId(worldId);
		long r = channelWorldMapper.queryChannelWorldCount(world);
		if(r != 0) {
			List<String> channelNameList = channelWorldMapper.queryChannelNameByWorldId(worldId);
			List<Integer> channelIdList = channelWorldMapper.queryChannelIdsByWorldId(worldId);
			
			String channelNames = "";
			String channelIds = "";
			
			for (int i = 0; i < channelNameList.size(); i++) {
				if ( i == 0 ) {
					channelNames += channelNameList.get(i);
				} else {
					channelNames += "," + channelNameList.get(i);
				}
			}
			
			for (int i = 0; i < channelIdList.size(); i++) {
				if ( i == 0 ) {
					channelIds += channelIdList.get(i);
				} else {
					channelIds += "," + channelIdList.get(i);
				}
			}
			
			ZTWorldDto dto = new ZTWorldDto();
			dto.setWorldId(worldId);
			dto.setChannelName(channelNames);
			dto.setChannelId(channelIds);
			
			ztWorldMapper.updateWorld(dto);
		} 
		// 若织图不在任何频道中，则把织图中相关频道名称与频道id字段刷成空串
		else {
			ZTWorldDto dto = new ZTWorldDto();
			dto.setWorldId(worldId);
			dto.setChannelName("");
			dto.setChannelId("");
			
			ztWorldMapper.updateWorld(dto);
		}
	}
	
	@Override
	public ZTWorldDto getZTWorldByWorldId(Integer worldId) {
		return ztWorldMapper.getZTWorldByWorldId(worldId);
	}
	

	@Override
	public void buildWorldMasonry(Integer maxId, Integer page, Integer rows, String startTime, String endTime, Integer phoneCode, Integer valid, Map<String, Object> jsonMap) throws Exception {
		List<ZTWorld> worldList = new ArrayList<ZTWorld>();
		
		// 定义返回的条件查询织图总数
		long totalCount = 0;
		
		// 分页起始行
		Integer firstRow = (page - 1) * rows;
		
		// 定义日期格式
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// 数据来源就应该是从真实用户所发织图
		// 设置查询条件
		if ( valid == null || valid == 1 ) {
			worldList = ztWorldMapper.getWorldListValid(maxId, date.parse(startTime), date.parse(endTime), phoneCode, firstRow, rows);
			totalCount = ztWorldMapper.getWorldValidTotal(maxId, date.parse(startTime), date.parse(endTime), phoneCode);
		} else {
			worldList = ztWorldMapper.getWorldListInvalid(maxId, date.parse(startTime), date.parse(endTime), phoneCode, firstRow, rows);
			totalCount = ztWorldMapper.getWorldInvalidTotal(maxId, date.parse(startTime), date.parse(endTime), phoneCode);
		}
		
		List<ZTWorldDto> rtnList = worldListToWorldDTOList(worldList);
		
		// 若传递的maxId为0，则取当前列表第一个织图的id作为maxId返回前台
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxId == 0 && rtnList.size() != 0 ? rtnList.get(0).getId() : maxId);
		jsonMap.put(OptResult.JSON_KEY_ROWS, rtnList);
		jsonMap.put(OptResult.TOTAL, totalCount);
		
	}
	
	/**
	 * 将织图信息转化成展示在前台需要的字段信息
	 * 
	 * @param worldList
	 * @return
	 * @throws Exception 
	 * @author zhangbo	2015年11月25日
	 */
	private List<ZTWorldDto> worldListToWorldDTOList(List<ZTWorld> worldList) throws Exception {
		List<ZTWorldDto> rtnList = new ArrayList<ZTWorldDto>();
		
		if ( worldList ==null || worldList.size() == 0) {
			return rtnList;
		}
		
		for (ZTWorld world : worldList) {
			// 定义织图DTO对象
			ZTWorldDto dto = new ZTWorldDto();
			
			// 由织图对象转换成为展示用的织图DTO对象
			dto.setId(world.getId());
			dto.setWorldId(world.getId());
			dto.setWorldDesc(world.getDescription());
			dto.setChannelId(world.getChannelIds());
			dto.setChannelName(world.getChannelNames());
			dto.setAuthorId(world.getAuthorId());
			dto.setWorldLabel(world.getWorldLabel());
			dto.setDateAdded(world.getCreateTime());
			dto.setClickCount(world.getClickCount());
			dto.setLikeCount(world.getLikeCount());
			dto.setCommentCount(world.getCommentCount());
			dto.setKeepCount(world.getKeepCount());
			dto.setLongitude(world.getLongitude());
			dto.setLatitude(world.getLatitude());
			dto.setLocationAddr(world.getAddress());
			dto.setProvince(world.getProvince());
			dto.setCity(world.getCity());
			dto.setWorldURL(Admin.worldURLPrefix + world.getShortLink());
			dto.setDateAdded(world.getCreateTime());
			
			// 获取用户信息，丰富展示用织图DTO对象
			UserInfo userInfo = userInfoService.getUserInfo(world.getAuthorId());
			
			dto.setAuthorName(userInfo.getUserName());			// 用户名
			dto.setAuthorAvatar(userInfo.getUserAvatar());		// 用户头像
			dto.setTrust(userInfo.getTrust());					// 信任标记
			dto.setStar(userInfo.getStar());					// 是否明星
			dto.setPhoneCode(userInfo.getPhoneCode());			// 手机辨别代号
			dto.setPhoneSys(userInfo.getPhoneSys());			// 手机系统
			dto.setPhoneVer(String.valueOf(userInfo.getVer()));	// 手机版本
			
			// 获取用户等级信息，丰富展示织图DTO对象
			UserLevelDto userLevel = userInteractService.getUserLevel(world.getAuthorId());
			if ( userLevel != null ) {
				dto.setLevel_description(userLevel.getLevel_description());
			}
			
			// 根据织图id，查询是否为精选备选，然后拼装织图DTO信息
			boolean exist = worldSuperReserveService.isExist(world.getId());
			dto.setSquarerecd(exist? 1 : 0);
			
			//根据织图ID查询对应的织图附近标签
			OpNearLabelWorldDto labelWorlDdto = new OpNearLabelWorldDto();
			labelWorlDdto.setWorldId(world.getId());
			List<OpNearLabelWorldDto> list  = nearLabelWorldMapper.queryNearLabelWorld(labelWorlDdto);
			String nearLabelName = "";
			
			for(int i=0;i<list.size();i++) {
				if (i==0) {
					nearLabelName = list.get(i).getNearLabelName();
				} else {
					nearLabelName = nearLabelName + "," + list.get(i).getNearLabelName();
				}
			}
			
			dto.setNearLabelNames(nearLabelName);
			
			rtnList.add(dto);
		}
		
		extractInteractInfo(rtnList);
		extractActivityInfo(rtnList);
		webUserInfoService.extractVerify(rtnList);
		
		return rtnList;
	}
	
	@Override
	public void buildWorldMasonryByUserLevel(Integer maxId, Integer page, Integer rows, String startTime, String endTime, Integer user_level_id, Integer valid, Map<String, Object> jsonMap) throws Exception {
		List<ZTWorld> worldList = new ArrayList<ZTWorld>();
		
		// 定义返回的条件查询织图总数
		long totalCount = 0;
		
		// 分页起始行
		Integer firstRow = (page - 1) * rows;
		
		// 定义日期格式
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// 数据来源就应该是从真实用户所发织图
		// 设置查询条件
		if ( valid == null || valid == 1 ) {
			worldList = ztWorldMapper.queryWorldByUserLevelValid(user_level_id, maxId, date.parse(startTime), date.parse(endTime), firstRow, rows);
			totalCount = ztWorldMapper.getWorldByUserLevelValidTotal(user_level_id, maxId, date.parse(startTime), date.parse(endTime));
		} else {
			worldList = ztWorldMapper.queryWorldByUserLevelInvalid(user_level_id, maxId, date.parse(startTime), date.parse(endTime), firstRow, rows);
			totalCount = ztWorldMapper.getWorldByUserLevelInvalidTotal(user_level_id, maxId, date.parse(startTime), date.parse(endTime));
		}
		
		List<ZTWorldDto> rtnList = worldListToWorldDTOList(worldList);
		
		// 若传递的maxId为0，则取当前列表第一个织图的id作为maxId返回前台
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxId == 0 && rtnList.size() != 0 ? rtnList.get(0).getId() : maxId);
		jsonMap.put(OptResult.JSON_KEY_ROWS, rtnList);
		jsonMap.put(OptResult.TOTAL, totalCount);
		
	}

	@Override
	public void buildWorldMasonryByZombie(Integer maxId, Integer page, Integer rows, String startTime, String endTime, Map<String, Object> jsonMap) throws Exception {
		List<ZTWorld> worldList = new ArrayList<ZTWorld>();
		
		// 定义返回的条件查询织图总数
		long totalCount = 0;
		
		// 分页起始行
		Integer firstRow = (page - 1) * rows;
		
		// 定义日期格式
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// 数据来源就应该是从真实用户所发织图
		// 设置查询条件
		worldList = ztWorldMapper.queryZombieWorld(firstRow, rows, maxId, date.parse(startTime),  date.parse(endTime));
		totalCount = ztWorldMapper.queryZombieWorldTotal(maxId, date.parse(startTime),  date.parse(endTime));
		
		List<ZTWorldDto> rtnList = worldListToWorldDTOList(worldList);
		
		// 若传递的maxId为0，则取当前列表第一个织图的id作为maxId返回前台
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxId == 0 && rtnList.size() != 0 ? rtnList.get(0).getId() : maxId);
		jsonMap.put(OptResult.JSON_KEY_ROWS, rtnList);
		jsonMap.put(OptResult.TOTAL, totalCount);
		
	}

	@Override
	public void buildWorldMasonryByWorldDesc(Integer maxId, Integer page, Integer rows, String worldDesc,Integer valid, Map<String, Object> jsonMap) throws Exception {
		// 根据织图描述（关键字）查询匹配的织图集合
		JSONObject resultJson = openSearchService.queryWolrdDescInfo(worldDesc, (page - 1) * rows, rows);
		
		// 获取主要opensearch返回值对象items，这里主要封装的为worldId的集合
		JSONArray resultJsonArray = resultJson.getJSONArray("items");
		
		// 定义opensearch返回的织图id集合
		Integer[] worldIds = new Integer[resultJsonArray .size()];
		for (int i = 0; i < resultJsonArray .size(); i++) {
			JSONObject jObject = resultJsonArray.getJSONObject(i);
			worldIds[i] = jObject.getInt("id");
		}
		
		List<ZTWorld> worldList =	null;
		if (valid == 1) {
			 worldList = ztWorldMapper.getWorldListByIdsForValid(worldIds);
		} else {
			 worldList = ztWorldMapper.getWorldListByIdsForInvalid(worldIds);
		}
		List<ZTWorldDto> rtnList = worldListToWorldDTOList(worldList);
		
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxId == 0 && rtnList.size() != 0 ? rtnList.get(0).getId() : maxId);
		jsonMap.put(OptResult.JSON_KEY_ROWS, rtnList);
		jsonMap.put(OptResult.TOTAL, resultJson.getLong("total"));
	}

	@Override
	public void buildWorldMasonryByWorldLocation(Integer maxId, Integer page, Integer rows, String worldLocation,Integer valid, Map<String, Object> jsonMap) throws Exception {
		// 根据织图地理位置信息（关键字）查询匹配的织图集合
		JSONObject resultJson = openSearchService.queryHTWolrdLocationInfo(worldLocation, (page - 1) * rows, rows);
		
		// 获取主要opensearch返回值对象items，这里主要封装的为worldId的集合
		JSONArray resultJsonArray = resultJson.getJSONArray("items");
		
		if ( resultJsonArray.size() == 0 ) {
			return;
		}
		
		// 定义opensearch返回的织图id集合
		Integer[] worldIds = new Integer[resultJsonArray.size()];
		for (int i = 0; i < resultJsonArray.size(); i++) {
			JSONObject jObject = resultJsonArray.getJSONObject(i);
			worldIds[i] = jObject.getInt("id");
		}
		
		List<ZTWorld> worldList =	null;
		if (valid == 1) {
			 worldList = ztWorldMapper.getWorldListByIdsForValid(worldIds);
		} else {
			 worldList = ztWorldMapper.getWorldListByIdsForInvalid(worldIds);
		}
		List<ZTWorldDto> rtnList = worldListToWorldDTOList(worldList);
		
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxId == 0 && rtnList.size() != 0 ? rtnList.get(0).getId() : maxId);
		jsonMap.put(OptResult.JSON_KEY_ROWS, rtnList);
		jsonMap.put(OptResult.TOTAL, resultJson.getLong("total"));
	}

	
	@Override
	public void buildWorldMasonryByWorldId(Integer maxId, Integer page, Integer rows, Integer worldId,Integer valid, Map<String, Object> jsonMap) throws Exception{
		Integer[] worldIds = new Integer[]{worldId};
		List<ZTWorld> worldList =	null;
		if (valid == 1) {
			 worldList = ztWorldMapper.getWorldListByIdsForValid(worldIds);
		} else {
			 worldList = ztWorldMapper.getWorldListByIdsForInvalid(worldIds);
		}
		List<ZTWorldDto> rtnList = worldListToWorldDTOList(worldList);
		
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxId == 0 && rtnList.size() != 0 ? rtnList.get(0).getId() : maxId);
		jsonMap.put(OptResult.JSON_KEY_ROWS, rtnList);
		jsonMap.put(OptResult.TOTAL, worldList.size());
	}

	@Override
	public void buildWorldMasonryByAuthorNameOrId(Integer maxId, Integer page, Integer rows, String authorNameOrId,Integer valid, Map<String, Object> jsonMap) throws Exception {
		
		// 定义查询条件：用户id集合
		Integer[] authorIds = new  Integer[1];
		
		// 分页起始行
		Integer firstRow = (page - 1) * rows;
		
		// 若为数字型，直接转换成用户id直接使用，否则根据用户名模糊匹配相关的人员，然后查询用户id集合
		if ( AdminUtil.isNumeric(authorNameOrId) ) {
			authorIds[0] = Integer.parseInt(authorNameOrId);
		} else {
			List<Integer> userIdList = userInfoService.getUserIdsByName(authorNameOrId);
			authorIds = userIdList.toArray(authorIds);
		}
		
		//根据有效性区分查找
		List<ZTWorld> worldList = null;
		long totalCount = 0;
		if(valid == 1){
			 worldList = ztWorldMapper.getWorldListByAuthorIdForValid(maxId, authorIds, firstRow, rows);
			 totalCount = ztWorldMapper.getWorldTotalByAuthorIdForValid(maxId, authorIds);
		}else{
			 worldList = ztWorldMapper.getWorldListByAuthorIdForInValid(maxId, authorIds, firstRow, rows);
			 totalCount = ztWorldMapper.getWorldTotalByAuthorIdForInValid(maxId, authorIds);
		}
		
		
		List<ZTWorldDto> rtnList = worldListToWorldDTOList(worldList);
		
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxId == 0 && rtnList.size() != 0 ? rtnList.get(0).getId() : maxId);
		jsonMap.put(OptResult.JSON_KEY_ROWS, rtnList);
		jsonMap.put(OptResult.TOTAL, totalCount);
	}
	
}

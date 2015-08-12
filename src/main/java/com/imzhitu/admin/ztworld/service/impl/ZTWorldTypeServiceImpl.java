package com.imzhitu.admin.ztworld.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.HTWorldLabel;
import com.hts.web.common.pojo.HTWorldType;
import com.hts.web.common.pojo.HTWorldTypeWorld;
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.PushUtil;
import com.hts.web.common.util.StringUtil;
import com.hts.web.push.service.PushService;
import com.hts.web.push.service.impl.PushServiceImpl.PushFailedCallback;
import com.imzhitu.admin.common.pojo.InteractWorldLevelListDto;
import com.imzhitu.admin.common.pojo.InteractWorldlevelWorldComment;
import com.imzhitu.admin.common.pojo.InteractWorldlevelWorldLabel;
import com.imzhitu.admin.common.pojo.OpChannelWorld;
import com.imzhitu.admin.common.pojo.UserTrust;
import com.imzhitu.admin.common.pojo.ZTWorldType;
import com.imzhitu.admin.common.pojo.ZTWorldTypeWorldDto;
import com.imzhitu.admin.interact.dao.InteractUserlevelListDao;
import com.imzhitu.admin.interact.service.InteractWorldService;
import com.imzhitu.admin.interact.service.InteractWorldlevelListService;
import com.imzhitu.admin.interact.service.InteractWorldlevelService;
import com.imzhitu.admin.op.dao.OpWorldTypeCacheDao;
import com.imzhitu.admin.op.dao.OpWorldTypeDto2CacheDao;
import com.imzhitu.admin.op.mapper.ChannelWorldMapper;
import com.imzhitu.admin.op.service.impl.OpServiceImpl;
import com.imzhitu.admin.userinfo.dao.UserTrustDao;
import com.imzhitu.admin.ztworld.dao.HTWorldDao;
import com.imzhitu.admin.ztworld.dao.HTWorldLabelDao;
import com.imzhitu.admin.ztworld.dao.HTWorldLabelWorldDao;
import com.imzhitu.admin.ztworld.dao.HTWorldTypeCacheDao;
import com.imzhitu.admin.ztworld.dao.HTWorldTypeDao;
import com.imzhitu.admin.ztworld.dao.HTWorldTypeWorldDao;
import com.imzhitu.admin.ztworld.service.ZTWorldService;
import com.imzhitu.admin.ztworld.service.ZTWorldTypeService;
import com.imzhitu.admin.ztworld.service.ZTWorldTypeWorldSchedulaService;


//@Service
public class ZTWorldTypeServiceImpl extends BaseServiceImpl implements
		ZTWorldTypeService {

	
//	public static final String UPDATE_TYPE_WORLD_NOTIFY_TIP_HEAD = "恭喜！您的织图被推荐上#";
//	public static final String UPDATE_TYPE_WORLD_NOTIFY_TIP_FOOT = "#分类啦";
	public static final String UPDATE_TYPE_WORLD_NOTIFY_TIP = "恭喜!您的织图被推荐上精选啦!";
	private static final long SPAN_TIME = 10*60*1000;//扫描更新排序计划时间间隔
	private static Logger logger = Logger.getLogger(ZTWorldTypeServiceImpl.class);		
	
	
	@Value("${admin.op.superbLimit}")
	private Integer superbLimit = 36; // 精品数量
	
	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private com.hts.web.userinfo.service.UserMsgService webUserMsgService;
	
	@Autowired
	private com.hts.web.userinfo.dao.UserInfoDao webUserInfoDao;
	
	@Autowired
	private com.hts.web.userinfo.service.UserInfoService webUserInfoService;
	
	@Autowired
	private PushService pushService;
	
	@Autowired
	private HTWorldTypeDao worldTypeDao;
	
	@Autowired
	private HTWorldTypeWorldDao worldTypeWorldDao;
	
	@Autowired
	private HTWorldLabelWorldDao worldLabelWorldDao;
	
	@Autowired
	private HTWorldLabelDao worldLabelDao;
	
	@Autowired
	private HTWorldDao worldDao;
	
	@Autowired
	private OpWorldTypeCacheDao opWorldTypeCacheDao;
	
	@Autowired
	private ZTWorldService worldService;

	@Autowired
	private InteractWorldService interactWorldService;

	@Autowired
	private OpWorldTypeDto2CacheDao opWorldTypeDto2CacheDao;
	
	@Autowired
	private InteractWorldlevelListService interactWorldlevelListService;
	
	@Autowired
	private InteractWorldlevelService interactWorldlevelService;
	
	@Autowired
	private InteractUserlevelListDao interactUserlevelListDao;
	
	@Autowired
	private ZTWorldTypeWorldSchedulaService typeWorldSchedulaService;
	
	@Autowired
	private UserTrustDao userTrustDao;
	
	@Autowired
	private ChannelWorldMapper channelWorldMapper;
	
	@Autowired
	private HTWorldTypeCacheDao typeCacheDao;
	
	public Integer getSuperbLimit() {
		return superbLimit;
	}

	public void setSuperbLimit(Integer superbLimit) {
		this.superbLimit = superbLimit;
	}

	@Override
	public void saveTypeWorld(Integer worldId, Integer typeId, String worldType,
			Integer recommenderId) throws Exception {
		worldTypeWorldDao.deleteByWorldId(worldId);
		worldDao.updateWorldTypeLabel(worldId, typeId, worldType);
		Integer id = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_TYPE_WORLD_ID);	
		worldTypeWorldDao.saveTypeWorld(new HTWorldTypeWorld(id, worldId, typeId, Tag.TRUE, Tag.FALSE,
				id, 0, recommenderId));
				
	}
	
	@Override
	public void updateTypeWorld(Integer worldId, Integer typeId, String worldType,
			Integer recommenderId) throws Exception{
		worldTypeWorldDao.updateTypeId(worldId, typeId);
		worldDao.updateWorldTypeLabel(worldId, typeId, worldType);
		opWorldTypeDto2CacheDao.updateWorldTypeDto2(superbLimit);
	}
	
	@Override
	public void buildTypeWorld(String beginDate,String endDate,int maxSerial, Integer typeId, Integer valid, Integer superb, Integer weight,Integer recommenderId,
			final String sort, final String order, Integer isSorted,int page, int rows, Map<String, Object> jsonMap) throws Exception{
		final Map<String, Object> attrMap = new LinkedHashMap<String, Object>();
		if(typeId != null) attrMap.put("typeId", typeId);
		if(valid != null) attrMap.put("valid", valid);
		if(superb != null) attrMap.put("superb", superb);
		if(weight != null) attrMap.put("weight", weight);
//		if(recommenderId != null) attrMap.put("recommenderId", recommenderId);
		if(isSorted !=null )attrMap.put("isSorted", isSorted);
		if(beginDate !=null && endDate !=null && !(beginDate.trim().equals("")) && !(endDate.trim().equals(""))){
			try{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date begin = df.parse(beginDate);
				Date end = df.parse(endDate);
				attrMap.put("beginDate", begin);
				attrMap.put("endDate", end);
			}catch(Exception e){
				e.printStackTrace();
			}			
		}
		
		buildSerializables("getSerial", maxSerial, page, rows,jsonMap, 
				new SerializableListAdapter<ZTWorldTypeWorldDto>() {

			@Override
			public List<ZTWorldTypeWorldDto> getSerializables(
					RowSelection rowSelection) {
				List<ZTWorldTypeWorldDto> typeList = 
						worldTypeWorldDao.queryTypeWorld(sort, order, attrMap, rowSelection);
				worldService.extractInteractInfo(typeList);
				webUserInfoService.extractVerify(typeList);
				for(ZTWorldTypeWorldDto dto: typeList){
					//查询每个用户的权限
					dto.setUserlevel(interactUserlevelListDao.QueryUserlevelByUserId(dto.getAuthorId()));
					try{
						if(interactWorldlevelListService.chechWorldLevelListIsExistByWId(dto.getWorldId())){
							dto.setTypeInteracted(1);
						}else{
							dto.setTypeInteracted(0);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					
					//查询信任操作人
					UserTrust userTrust = userTrustDao.queryUserTrustByUid(dto.getAuthorId());
					if(userTrust != null){
						dto.setTrustModifyDate(userTrust.getModifyDate());
						dto.setTrustOperatorId(userTrust.getOperatorId());
						dto.setTrustOperatorName(userTrust.getOperatorName());
					}else{
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						try{
							Date trustModifyDate = df.parse("2011-08-20 00:00:00");
							dto.setTrustModifyDate(trustModifyDate);
							dto.setTrustOperatorId(0);
							dto.setTrustOperatorName("暂无");
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					
					//查询频道信息
					OpChannelWorld world = new OpChannelWorld();
					world.setWorldId(dto.getWorldId());
//					world.setValid(Tag.TRUE);
					long r = channelWorldMapper.queryChannelWorldCount(world);
					if(r == 0)
						dto.setChannelName("NO_EXIST");
					else {
						List<String>strList = channelWorldMapper.queryChannelNameByWorldId(dto.getWorldId());
						dto.setChannelName(strList.toString());
					}
					
				}
				return typeList;
			}
	
			@Override
			public List<ZTWorldTypeWorldDto> getSerializableByMaxId(
					int maxId, RowSelection rowSelection) {
				List<ZTWorldTypeWorldDto> typeList = 
						worldTypeWorldDao.queryTypeWorld(maxId, sort, order, attrMap, rowSelection);
				worldService.extractInteractInfo(typeList);
				webUserInfoService.extractVerify(typeList);
				for(ZTWorldTypeWorldDto dto: typeList){
					//查询每个用户的权限
					dto.setUserlevel(interactUserlevelListDao.QueryUserlevelByUserId(dto.getAuthorId()));
					try{
						if(interactWorldlevelListService.chechWorldLevelListIsExistByWId(dto.getWorldId())){
							dto.setTypeInteracted(1);
						}else{
							dto.setTypeInteracted(0);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					
					//查询信任操作人
					UserTrust userTrust = userTrustDao.queryUserTrustByUid(dto.getAuthorId());
					if(userTrust != null){
						dto.setTrustModifyDate(userTrust.getModifyDate());
						dto.setTrustOperatorId(userTrust.getOperatorId());
						dto.setTrustOperatorName(userTrust.getOperatorName());
					}else{
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						try{
							Date trustModifyDate = df.parse("2011-08-20 00:00:00");
							dto.setTrustModifyDate(trustModifyDate);
							dto.setTrustOperatorId(0);
							dto.setTrustOperatorName("暂无");
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					
					//查询频道信息
					OpChannelWorld world = new OpChannelWorld();
					world.setWorldId(dto.getWorldId());
//					world.setValid(Tag.TRUE);
					long r = channelWorldMapper.queryChannelWorldCount(world);
					if(r == 0)dto.setChannelName("NO_EXIST");
					else {
//						OpChannelWorld channelWorld = channelWorldMapper.queryChannelWorldByWorldId(dto.getWorldId());
//						dto.setChannelName(channelWorld.getChannelName());
						List<String>strList = channelWorldMapper.queryChannelNameByWorldId(dto.getWorldId());
						dto.setChannelName(strList.toString());
					}
				}
				return typeList;
			}
	
			@Override
			public long getTotalByMaxId(int maxId) {
				return worldTypeWorldDao.queryTypeWorldCountByMaxId(maxId, attrMap);
			}
			
		},OptResult.JSON_KEY_ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_SERIAL);
	}
	
	@Override
	public void deleteTypeWorldByWorldId(Integer worldId,Integer operatorId) throws Exception {
		Date now = new Date();
		worldTypeWorldDao.updateRecommenderId(worldId, operatorId);
		worldTypeWorldDao.updateSuperbByWId(worldId, Tag.FALSE);
		worldTypeWorldDao.updateValidByWorldId(worldId, Tag.FALSE);
		worldTypeWorldDao.updateModifyDateByWid(worldId, now);
		opWorldTypeDto2CacheDao.updateWorldTypeDto2(superbLimit);
	}
	
	@Override
	public void batchDeleteTypeWorld(String idsStr,Integer operatorId) throws Exception{
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		List<Integer> worldList = worldTypeWorldDao.queryRecommendWorldIdsByTypeWorldIds(ids);
		for(Integer worldId : worldList) {
			deleteTypeWorldByWorldId(worldId,operatorId);
		}
		opWorldTypeDto2CacheDao.updateWorldTypeDto2(superbLimit);
		
	}
	
	@Override
	public void updateTypeWorldSuperb(Integer id, Integer superb) {
		worldTypeWorldDao.updateSuperb(id, superb);
	//	opWorldTypeDto2CacheDao.updateWorldTypeDto2(superbLimit);
	}
	
	@Override
	public void updateTypeWorldWeight(Integer id, Integer weight)
			throws Exception {
		worldTypeWorldDao.updateWeight(id, weight);
	//	opWorldTypeDto2CacheDao.updateWorldTypeDto2(superbLimit);
	}

	
	@Override
	public void batchUpdateTypeWorldSerial(String[] idStrs) {
		for(int i = idStrs.length - 1; i >= 0; i--) {
			String idStr = idStrs[i];
			if(idStr != null && idStr != "") {
				int id = Integer.parseInt(idStrs[i]);
				int serial = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_TYPE_WORLD_ID);
				worldTypeWorldDao.updateSerial(id, serial);
			}
		}
		opWorldTypeDto2CacheDao.updateWorldTypeDto2(superbLimit);
	}
	
	/**
	 * 增加织图排序计划
	 * @param idStrs
	 * @param schedula
	 */
	@Override
	public void addUpdateTypeWorldSerialSchedula(String[] idStrs,Date schedula,Integer operatorId){
		for(int i= idStrs.length -1;i >= 0; i--){
			String idStr = idStrs[i];
			if(idStr !=null && idStr != ""){
				int id = Integer.parseInt(idStrs[i]);
				long t = schedula.getTime() - i*1000;//用以排序
				try{
					typeWorldSchedulaService.addTypeWorldSchedula(id, new Date(t),operatorId, 0);
					worldTypeWorldDao.updateIsSorted(new Integer[]{id}, 1);
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		}
	}
	
	/**
	 * 定时任务--执行排序计划，并更新有效性
	 */
	@Override
	public void performTypeWorldSchedula(){
		Date begin = new Date();
		logger.info("begin performTypeWorldSchedula =====>>>"+begin);
		try{
			List<Integer> list = typeWorldSchedulaService.getWorldIdBySchedula(new Date(begin.getTime()-SPAN_TIME),begin);//获取在该时间段内的所有计划的worldid
			if(!list.isEmpty()){//worldId不为空
				for(Integer id:list){//遍历所有的worldid ，用以更新它对应的序列号
					try{//更新序列号
						int serial = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_TYPE_WORLD_ID);
						worldTypeWorldDao.updateSerial(id, serial);
						InteractWorldLevelListDto woldlevellistDto = interactWorldlevelListService.queryWorldLevelListByWid(id);//查询对应的织图等级
						if(woldlevellistDto !=null){
							//增加等级织图，将等级对应的数据转成互动计划，
//							List<InteractWorldlevelWorldLabel> worldLabelList = woldlevellistDto.getWorldLabelList();
							List<InteractWorldlevelWorldComment> worldCommentList = woldlevellistDto.getWorldCommentList();
//							String worldLabelStr=null;
							String worldCommentStr = "";
							
//							for(int i=0;i<worldLabelList.size();i++){
//								worldLabelStr += worldLabelList.get(i).getLabelId().toString();
//								if(i != worldLabelList.size() -1)
//									worldLabelStr +=",";
//							}
							for(int j=0;j<worldCommentList.size();j++){
								worldCommentStr += worldCommentList.get(j).getCommentId().toString();
								if(j != worldCommentList.size() -1)
									worldCommentStr +=",";
							}
							
							//普通发
//							interactWorldlevelService.AddLevelWorld(id, 
//									woldlevellistDto.getWorld_level_id(), 
//									worldLabelStr, 
//									worldCommentStr);
							//一小时内发完80%，剩下的按普通发,用来替代上面的interactWorldlevelService.AddLevelWorld
							interactWorldlevelService.AddTypeWorldInteract(id, woldlevellistDto.getWorld_level_id(), worldCommentStr);
							
							//更新等级织图的有效性
							interactWorldlevelListService.updateWorldlevelListValidity(id, woldlevellistDto.getWorld_level_id(), Tag.TRUE,woldlevellistDto.getOperatorId());
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				Integer[] ids = list.toArray(new Integer[list.size()]);
				worldTypeWorldDao.updateTypeWorldValidByWorldIds(ids);//更新有效性
				typeWorldSchedulaService.updateCompleteByIds(ids, 1);//更新计划表中的完成性
				opWorldTypeDto2CacheDao.updateWorldTypeDto2(superbLimit);//更新缓存
				Integer[] listworldId = new Integer[list.size()];
				list.toArray(listworldId);
				List<ZTWorldTypeWorldDto> listworldDto =  worldTypeWorldDao.queryTypeWorldByWids(listworldId);//查询typeworld
				for(ZTWorldTypeWorldDto dto : listworldDto) {
					autoCommitUpdateTypeWorldValid(dto);//推送上广场的消息
				}		
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		Date end = new Date();
		logger.info("end performTypeWorldSchedula =====>>>"+end);
	}
	
	@Override
	public void batchUpdateRecommendTypeWorldValid() throws Exception {
		List<ZTWorldTypeWorldDto> worldList = worldTypeWorldDao.queryRecommendTypeWorldByValid(Tag.FALSE);
		worldTypeWorldDao.updateAllRecommendTypeWorldValid(Tag.TRUE);
		for(ZTWorldTypeWorldDto dto : worldList) {
			autoCommitUpdateTypeWorldValid(dto);
		}
		opWorldTypeDto2CacheDao.updateWorldTypeDto2(superbLimit);
	}
	
	@Override
	public void batchUpdateTypeWorldValid(String idsStr,String widsStr,Integer operatorId) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		worldTypeWorldDao.updateTypeWorldValid(ids);
		List<ZTWorldTypeWorldDto> worldList = worldTypeWorldDao.queryTypeWorldByIds(ids);
		for(ZTWorldTypeWorldDto dto : worldList) {
			autoCommitUpdateTypeWorldValid(dto);
		}
		
		//更新织图等级的有效性
		Integer[]wids = StringUtil.convertStringToIds(widsStr);
		for(Integer wid:wids){
			InteractWorldLevelListDto woldlevellistDto = interactWorldlevelListService.queryWorldLevelListByWid(wid);//查询对应的织图等级
			if(woldlevellistDto !=null){
				//增加等级织图，将等级对应的数据转成互动计划，
				List<InteractWorldlevelWorldLabel> worldLabelList = woldlevellistDto.getWorldLabelList();
				List<InteractWorldlevelWorldComment> worldCommentList = woldlevellistDto.getWorldCommentList();
				String worldLabelStr="";
				String worldCommentStr = "";
				for(int i=0;i<worldLabelList.size();i++){
					worldLabelStr += worldLabelList.get(i).getLabelId().toString();
					if(i != worldLabelList.size() -1)
						worldLabelStr +=",";
				}
				for(int j=0;j<worldCommentList.size();j++){
					worldCommentStr += worldCommentList.get(j).getCommentId().toString();
					if(j != worldCommentList.size() -1)
						worldCommentStr +=",";
				}
				interactWorldlevelService.AddLevelWorld(wid, 
						woldlevellistDto.getWorld_level_id(), 
						worldLabelStr, 
						worldCommentStr);
				//更新等级织图的有效性
				interactWorldlevelListService.updateWorldlevelListValidity(wid, woldlevellistDto.getWorld_level_id(), Tag.TRUE,operatorId);
			}
		}
		
		
		opWorldTypeDto2CacheDao.updateWorldTypeDto2(superbLimit);
	}
	
	public void autoCommitUpdateTypeWorldValid(ZTWorldTypeWorldDto dto) throws Exception {
//		String notifyTip = UPDATE_TYPE_WORLD_NOTIFY_TIP_HEAD + dto.getWorldType() + UPDATE_TYPE_WORLD_NOTIFY_TIP_FOOT;
		String notifyTip = UPDATE_TYPE_WORLD_NOTIFY_TIP;
		Integer msgId = null;
		Integer userId = dto.getAuthorId();
		Integer worldId = dto.getWorldId();
		String userName = dto.getUserInfo().getUserName();
		String tip = userName + "," + notifyTip;
		String shortTip = PushUtil.getShortName(userName) + "," + PushUtil.getShortTip(notifyTip);
		msgId = webUserMsgService.getValidMessageId(OpServiceImpl.ZHITU_UID, userId, Tag.USER_MSG_SQUARE_NOTIFY, worldId);
		if(msgId == null) {
			UserPushInfo userPushInfo = webUserInfoDao.queryUserPushInfoById(userId);
			webUserMsgService.saveSysMsg(OpServiceImpl.ZHITU_UID, userId, tip, 
					Tag.USER_MSG_SQUARE_NOTIFY, worldId, dto.getWorldType(), String.valueOf(dto.getTypeId()),
					dto.getTitleThumbPath(), 0);
			pushService.pushSysMessage(shortTip, OpServiceImpl.ZHITU_UID, shortTip, userPushInfo,
					Tag.USER_MSG_SQUARE_NOTIFY,
					new PushFailedCallback() {
	
				@Override
				public void onPushFailed(Exception e) {}
				
			});
		}
	}

	@Override
	public void buildLabel(int typeId,Map<String, Object> jsonMap)
			throws Exception {
		List<HTWorldLabel> list = worldLabelDao.queryAllLabelByTypeId(typeId);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
	}
	
	@Override
	public void saveType(String typeName, String typeDesc) throws Exception {
		Integer id = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_TYPE_ID);
		String typePinyin = StringUtil.getPinYin(typeName);
		worldTypeDao.saveType(new ZTWorldType(id, typeName, typePinyin, typeDesc, Tag.TRUE, id));
	}

	@Override
	public void buildType(int maxSerial, int start, int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables("getSerial", maxSerial, start, limit, jsonMap, new SerializableListAdapter<ZTWorldType>() {

			@Override
			public List<ZTWorldType> getSerializables(RowSelection rowSelection) {
				return worldTypeDao.queryType(rowSelection);
			}

			@Override
			public List<ZTWorldType> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return worldTypeDao.queryType(maxId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return worldTypeDao.queryTypeCount(maxId);
			}
		}, OptResult.JSON_KEY_ROWS, OptResult.JSON_KEY_TOTAL);
	}
	

	@Override
	public void batchUpdateTypeValid(String idsStr, Integer valid) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		worldTypeDao.updateTypeValid(ids, valid);
		opWorldTypeCacheDao.updateCacheType();
	}
	
	@Override
	public void saveLabel(Integer typeId, File labelFile) throws Exception {
		BufferedReader reader = null;
		try {
			FileInputStream fis = new FileInputStream(labelFile); 
			InputStreamReader isr = new InputStreamReader(fis,"GBK"); //指定以GBK编码读入 
			reader = new BufferedReader(isr);
			String line = null;
			while((line = reader.readLine()) != null) {
				String labelName = line.trim();
				if(!labelName.equals("")) {
					Integer id = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_ID);
					String labelPinyin = StringUtil.getPinYin(labelName);
					worldLabelDao.saveLabel(new HTWorldLabel(id, labelName, labelPinyin, 0, new Date(), Tag.TRUE, Tag.TRUE, id, 0));
				}
			}
		} finally {
			reader.close();
		}
	}

	@Override
	public void buildLabelIds(Integer worldId,
			Map<String, Object> jsonMap) throws Exception {
		List<Integer> list = worldLabelWorldDao.queryLabelIds(worldId);
		jsonMap.put(OptResult.JSON_KEY_LABEL_INFO, list);
	}
	
	
	/**
	 * 修改精选织图点评
	 */
	@Override
	public void updateTypeWorldReview(Integer worldId,String review) throws Exception{
		worldTypeWorldDao.updateTypeWorldReview(worldId, review);
	}
	
	/**
	 * 更新缓存
	 */
	public void updateTypeWorldCache()throws Exception{
		opWorldTypeDto2CacheDao.updateWorldTypeDto2(superbLimit);
	}
	
	
	@Override
	public void updateTypeCache()throws Exception {
		typeCacheDao.updateTypeCache();
	}

}

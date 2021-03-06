package com.imzhitu.admin.ztworld.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.hts.web.push.service.PushService;
import com.hts.web.push.service.impl.PushServiceImpl.PushFailedCallback;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.InteractWorldLevelListDto;
import com.imzhitu.admin.common.pojo.InteractWorldlevelWorldComment;
import com.imzhitu.admin.common.pojo.InteractWorldlevelWorldLabel;
import com.imzhitu.admin.common.pojo.OpChannelWorld;
import com.imzhitu.admin.common.pojo.UserTrust;
import com.imzhitu.admin.common.pojo.ZTWorldType;
import com.imzhitu.admin.common.pojo.ZTWorldTypeLabelDto;
import com.imzhitu.admin.common.pojo.ZTWorldTypeWorldDto;
import com.imzhitu.admin.common.pojo.ZTWorldTypeWorldWeightUpdateDto;
import com.imzhitu.admin.common.service.KeyGenService;
import com.imzhitu.admin.interact.dao.InteractUserlevelListDao;
import com.imzhitu.admin.interact.service.InteractWorldlevelListService;
import com.imzhitu.admin.interact.service.InteractWorldlevelService;
import com.imzhitu.admin.op.dao.OpWorldTypeCacheDao;
import com.imzhitu.admin.op.dao.OpWorldTypeDto2CacheDao;
import com.imzhitu.admin.op.mapper.ChannelWorldMapper;
import com.imzhitu.admin.op.service.OpMsgService;
import com.imzhitu.admin.userinfo.dao.UserTrustDao;
import com.imzhitu.admin.ztworld.dao.HTWorldDao;
import com.imzhitu.admin.ztworld.dao.HTWorldLabelDao;
import com.imzhitu.admin.ztworld.dao.HTWorldLabelWorldDao;
import com.imzhitu.admin.ztworld.dao.HTWorldTypeCacheDao;
import com.imzhitu.admin.ztworld.dao.HTWorldTypeDao;
import com.imzhitu.admin.ztworld.mapper.ZTWorldTypeWorldMapper;
import com.imzhitu.admin.ztworld.mapper.ZTWorldTypeWorldWeightUpdateMapper;
import com.imzhitu.admin.ztworld.service.ZTWorldService;
import com.imzhitu.admin.ztworld.service.ZTWorldTypeService;
import com.imzhitu.admin.ztworld.service.ZTWorldTypeWorldSchedulaService;

public class ZTWorldTypeServiceImpl extends BaseServiceImpl implements
		ZTWorldTypeService {
	
	public static final String UPDATE_TYPE_WORLD_NOTIFY_TIP = "恭喜！您的织图被推荐上精选啦！";
	private static final long SPAN_TIME = 10*60*1000;//扫描更新排序计划时间间隔
	private static Logger logger = Logger.getLogger(ZTWorldTypeServiceImpl.class);		
	
	@Value("${admin.op.superbLimit}")
	private Integer superbLimit = 36; // 精品数量
	
	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private com.hts.web.userinfo.dao.UserInfoDao webUserInfoDao;
	
	@Autowired
	private com.hts.web.userinfo.service.UserInfoService webUserInfoService;
	
	@Autowired
	private PushService pushService;
	
	@Autowired
	private HTWorldTypeDao worldTypeDao;
	
	@Autowired
	private ZTWorldTypeWorldMapper typeWorldMapper;
	
	@Autowired
	private ZTWorldTypeWorldWeightUpdateMapper typeWorldWeightUpdateMapper;
	
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
	
	@Autowired
	private KeyGenService keyGenService;
	
	@Autowired
	private OpMsgService opMsgService;
	
	public Integer getSuperbLimit() {
		return superbLimit;
	}

	public void setSuperbLimit(Integer superbLimit) {
		this.superbLimit = superbLimit;
	}

	@Override
	public void saveTypeWorld(Integer worldId, Integer typeId, String worldType,
			Integer recommenderId) throws Exception {
		if ( worldId == null){
			throw new Exception("worldId is null");
		}
		ZTWorldTypeWorldDto dto = new ZTWorldTypeWorldDto();
		dto.setWorldId(worldId);
		long r = typeWorldMapper.selectTypeWorldTotalCount(dto);
		
		dto.setTypeId(typeId);
		dto.setSuperb(Tag.TRUE);
		dto.setValid(Tag.FALSE);
		dto.setWeight(0);
		dto.setRecommenderId(recommenderId);
		
		if(r == 0 ){
			//若不存在 则新增
			Integer id = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_TYPE_WORLD_ID);	
			Integer serial = keyGenService.generateId(Admin.KEYGEN_ZT_TYPEWORLD_SERIAL);
			dto.setId(id);
			dto.setSerial(serial);
			typeWorldMapper.saveTypeWorld(dto);	
		}else{
			//若存在则修改
			typeWorldMapper.updateTypeWorld(dto);
		}
		
		//更新，并添加记录
		worldDao.updateWorldTypeLabel(worldId, typeId, worldType);
			
	}
	
	@Override
	public void updateTypeWorld(Integer worldId, Integer typeId, String worldType,
			Integer recommenderId) throws Exception{
		if(worldId == null){
			throw new Exception("worldId is null");
		}
		ZTWorldTypeWorldDto dto = new ZTWorldTypeWorldDto();
		dto.setWorldId(worldId);
		dto.setTypeId(typeId);
		typeWorldMapper.updateTypeWorld(dto);
		worldDao.updateWorldTypeLabel(worldId, typeId, worldType);
		opWorldTypeDto2CacheDao.updateWorldTypeDto2(superbLimit);
	}
	
	@Override
	public void buildTypeWorld(Integer worldId,Date beginDate,Date endDate,int maxSerial, Integer typeId, Integer valid, Integer superb, Integer weight,Integer recommenderId,
			final String sort, final String order, Integer isSorted,int page, int rows, Map<String, Object> jsonMap) throws Exception{
		
		ZTWorldTypeWorldDto typeWorlddto = new ZTWorldTypeWorldDto();
		typeWorlddto.setMaxId(maxSerial);
		typeWorlddto.setFirstRow(rows*(page-1));
		typeWorlddto.setLimit(rows);
		typeWorlddto.setTypeId(typeId);
		typeWorlddto.setValid(valid);
		typeWorlddto.setSuperb(superb);
		typeWorlddto.setWeight(weight);
		typeWorlddto.setAddDate(beginDate);
		typeWorlddto.setModifyDate(endDate);
		typeWorlddto.setIsSorted(isSorted);
		typeWorlddto.setRecommenderId(recommenderId);
		typeWorlddto.setOrder(order);
		typeWorlddto.setSort(sort);
		typeWorlddto.setWorldId(worldId);
		
		long total = typeWorldMapper.queryTypeWorldTotalCount(typeWorlddto);
		List<ZTWorldTypeWorldDto> list = null;
		if(total > 0 ){
			list = typeWorldMapper.queryTypeWorld(typeWorlddto);
			if (list != null && list.size() > 0 ) {
				maxSerial = list.get(0).getSerial();
				
				worldService.extractInteractInfo(list);
				webUserInfoService.extractVerify(list);
				for(ZTWorldTypeWorldDto dto: list){
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
			}
		}
		
		
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
		jsonMap.put(OptResult.JSON_KEY_MAX_SERIAL, maxSerial);
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
	}
	
	@Override
	public void deleteTypeWorld(Integer id,Integer worldId,Integer operatorId)throws Exception{
		if(id == null && worldId == null){
			throw new Exception("id and worldId is null");
		}
		ZTWorldTypeWorldDto dto = new ZTWorldTypeWorldDto();
		dto.setId(id);
		dto.setWorldId(worldId);
		dto.setSuperb(Tag.FALSE);
		dto.setValid(Tag.FALSE);
		dto.setRecommenderId(operatorId);
		typeWorldMapper.updateTypeWorld(dto);
	}
	
	@Override
	public void batchDeleteTypeWorld(String idsStr,Integer operatorId) throws Exception{
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		for(Integer id:ids){
			deleteTypeWorld(id,null,operatorId);
		}
		opWorldTypeDto2CacheDao.updateWorldTypeDto2(superbLimit);
		
	}
	
	@Override
	public void updateTypeWorldSuperb(Integer id, Integer superb) {
		ZTWorldTypeWorldDto dto = new ZTWorldTypeWorldDto();
		dto.setId(id);
		dto.setSuperb(superb);
		typeWorldMapper.updateTypeWorld(dto);
	//	opWorldTypeDto2CacheDao.updateWorldTypeDto2(superbLimit);
	}
	
	@Override
	public void updateTypeWorldWeight(Integer id, Integer weight,Integer timeUpdate)
			throws Exception {
		int flag = weight;
		if(weight > 0) {
			weight = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_TYPE_WORLD_ID);
		}
		ZTWorldTypeWorldDto dto = new ZTWorldTypeWorldDto();
		dto.setId(id);
		dto.setWeight(weight);
		typeWorldMapper.updateTypeWorld(dto);
		//更新缓存使置顶生效
		opWorldTypeDto2CacheDao.updateWorldTypeDto2(superbLimit);
		
		/**
		 * mishengliang
		 * 将weight=1的记录到定时表中
		 */
		if(flag == 1){
			long now  = new Date().getTime();
			long endTime = now + timeUpdate * 60 * 60 * 1000;
			ZTWorldTypeWorldWeightUpdateDto weightUpdateDto = new ZTWorldTypeWorldWeightUpdateDto();
			weightUpdateDto.setTypeWorldId(id);
			weightUpdateDto.setEndTime(endTime);
			typeWorldWeightUpdateMapper.saveTypeWorldWeight(weightUpdateDto);
		}
	}

	
	@Override
	public void batchUpdateTypeWorldSerial(String[] idStrs) {
		ZTWorldTypeWorldDto dto = new ZTWorldTypeWorldDto();
		
		for(int i = idStrs.length - 1; i >= 0; i--) {
			String idStr = idStrs[i];
			if(idStr != null && idStr != "") {
				int id = Integer.parseInt(idStrs[i]);
				int serial = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_TYPE_WORLD_ID);
				dto.setId(id);
				dto.setSerial(serial);
				typeWorldMapper.updateTypeWorld(dto);
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
	public void addUpdateTypeWorldSerialSchedula(String[] worldIdStrs,Date schedula,Integer operatorId){
		ZTWorldTypeWorldDto dto = new ZTWorldTypeWorldDto();
		dto.setIsSorted(Tag.TRUE);
		for(int i= worldIdStrs.length -1;i >= 0; i--){
			String worldIdStr = worldIdStrs[i];
			if(worldIdStr !=null && worldIdStr != ""){
				int worldId = Integer.parseInt(worldIdStr);
				long t = schedula.getTime() - i*1000;//用以排序
				try{
					typeWorldSchedulaService.addTypeWorldSchedula(worldId, new Date(t),operatorId, 0);
					dto.setWorldId(worldId);
					typeWorldMapper.updateTypeWorld(dto);
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
		ZTWorldTypeWorldDto dto = new ZTWorldTypeWorldDto();
		Date begin = new Date();
		logger.info("begin performTypeWorldSchedula =====>>>"+begin);
		try{
			List<Integer> list = typeWorldSchedulaService.getWorldIdBySchedula(new Date(begin.getTime()-SPAN_TIME),begin);//获取在该时间段内的所有计划的worldid
			if(!list.isEmpty()){//worldId不为空
				for(Integer worldId:list){//遍历所有的worldid ，用以更新它对应的序列号
					try{//更新序列号
						int serial = keyGenService.generateId(Admin.KEYGEN_ZT_TYPEWORLD_SERIAL);
						dto.setWorldId(worldId);
						dto.setSerial(serial);
						typeWorldMapper.updateTypeWorld(dto);
						InteractWorldLevelListDto woldlevellistDto = interactWorldlevelListService.queryWorldLevelListByWid(worldId);//查询对应的织图等级
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
							interactWorldlevelService.AddTypeWorldInteract(worldId, woldlevellistDto.getWorld_level_id(), worldCommentStr);
							
							//更新等级织图的有效性
							interactWorldlevelListService.updateWorldlevelListValidity(worldId, woldlevellistDto.getWorld_level_id(), Tag.TRUE,woldlevellistDto.getOperatorId());
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				Integer[] worldIds = list.toArray(new Integer[list.size()]);
				typeWorldMapper.updateTypeWorldValidByWorldIds(worldIds, Tag.TRUE);
				typeWorldSchedulaService.updateCompleteByWorldIds(worldIds, Tag.TRUE);//更新计划表中的完成性
				opWorldTypeDto2CacheDao.updateWorldTypeDto2(superbLimit);//更新缓存
				List<ZTWorldTypeWorldDto> listworldDto =  typeWorldMapper.queryTypeWorldByWorldIds(worldIds);//查询typeworld
				for(ZTWorldTypeWorldDto worldDto : listworldDto) {
					autoCommitUpdateTypeWorldValid(worldDto);//推送上广场的消息
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
		ZTWorldTypeWorldDto dto = new ZTWorldTypeWorldDto();
		dto.setValid(Tag.FALSE);
		dto.setRecommenderId(1);//非0的查询，即已经推荐的用户
		List<ZTWorldTypeWorldDto> worldList = typeWorldMapper.queryTypeWorld(dto);
		typeWorldMapper.updateAllRecommendTypeWorldValid(Tag.FALSE, Tag.TRUE);
		for(ZTWorldTypeWorldDto worldDto : worldList) {
			autoCommitUpdateTypeWorldValid(worldDto);
		}
		opWorldTypeDto2CacheDao.updateWorldTypeDto2(superbLimit);
	}
	
	@Override
	public void batchUpdateTypeWorldValid(String idsStr,String widsStr,Integer operatorId) throws Exception {
		Integer[]wids = StringUtil.convertStringToIds(widsStr);
		typeWorldMapper.updateTypeWorldValidByWorldIds(wids, Tag.TRUE);
		List<ZTWorldTypeWorldDto> worldList = typeWorldMapper.queryTypeWorldByWorldIds(wids);
		for(ZTWorldTypeWorldDto dto : worldList) {
			autoCommitUpdateTypeWorldValid(dto);
		}
		
		//更新织图等级的有效性
		
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
		String notifyTip = UPDATE_TYPE_WORLD_NOTIFY_TIP;
		Integer userId = dto.getAuthorId();
		Integer worldId = dto.getWorldId();
		String userName = dto.getUserInfo().getUserName();
		String tip = userName + "," + notifyTip;
		UserPushInfo userPushInfo = webUserInfoDao.queryUserPushInfoById(userId);
		opMsgService.saveSysMsg(userId, tip, Tag.USER_MSG_SQUARE_NOTIFY, 
				worldId, dto.getWorldType(), String.valueOf(dto.getTypeId()),
						dto.getTitleThumbPath());
		pushService.pushSysMessage(tip, Admin.ZHITU_UID, tip, userPushInfo,
				Tag.USER_MSG_SQUARE_NOTIFY,
				new PushFailedCallback() {

			@Override
			public void onPushFailed(Exception e) {}
			
		});
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
					worldLabelDao.saveLabel(new HTWorldLabel(id, labelName, labelPinyin, 0, 0,
							new Date(), Tag.TRUE, Tag.TRUE, id, 0));
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
		ZTWorldTypeWorldDto dto = new ZTWorldTypeWorldDto();
		dto.setWorldId(worldId);
		dto.setReView(review);
		typeWorldMapper.updateTypeWorld(dto);
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
	
	/**
	 * 查询所有的分类，用以做下拉列表
	 */
	@Override
	public List<ZTWorldTypeLabelDto> queryAllType()throws Exception{
		List<ZTWorldTypeLabelDto> list = worldTypeDao.queryAllType();
		return list;
	}

	/**
	 * 每10分钟扫描一次时间表，截止时间 小于 当前时间的将其删除，且将广场分类表中的weight改为0
	 *  
		*	2015年11月4日
		*	mishengliang
	 */
	public void doTypeWorldWeightUpdateSchedule(){
		long now = new Date().getTime();
		List<ZTWorldTypeWorldWeightUpdateDto> lists  = typeWorldWeightUpdateMapper.selectTypeWorldWeightByEndTime(now);
		if(lists.size() > 0){
			for(ZTWorldTypeWorldWeightUpdateDto list : lists){
				int typeWorldId = list.getTypeWorldId();
				typeWorldMapper.updateTypeWorldWeightByTypeWorldId(typeWorldId, 0);
				typeWorldWeightUpdateMapper.deleteTypeWorldWeightBytypeWorldId(typeWorldId);
			}
		}
	}
	
}

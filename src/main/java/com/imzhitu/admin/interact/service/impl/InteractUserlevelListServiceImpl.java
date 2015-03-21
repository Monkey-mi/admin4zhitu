package com.imzhitu.admin.interact.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.InteractPlanComment;
import com.imzhitu.admin.common.pojo.InteractPlanCommentLabel;
import com.imzhitu.admin.common.pojo.InteractWorldLabelDto;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.common.pojo.UserLevelDto;
import com.imzhitu.admin.common.pojo.UserLevelListDto;
import com.imzhitu.admin.interact.dao.InteractUserlevelDao;
import com.imzhitu.admin.interact.dao.InteractUserlevelListDao;
import com.imzhitu.admin.interact.dao.InteractWorldLabelCommentLabelDao;
import com.imzhitu.admin.interact.service.InteractPlanCommentLabelService;
import com.imzhitu.admin.interact.service.InteractPlanCommentService;
import com.imzhitu.admin.interact.service.InteractWorldService;
import com.imzhitu.admin.interact.service.InteractUserlevelListService;
import com.imzhitu.admin.interact.service.InteractCommentService;
import com.imzhitu.admin.userinfo.mapper.UserInfoMapper;
import com.imzhitu.admin.ztworld.dao.HTWorldLabelWorldDao;
import com.imzhitu.admin.ztworld.mapper.ZTWorldMapper;

//@Service
public class InteractUserlevelListServiceImpl extends BaseServiceImpl implements InteractUserlevelListService{
	
	private static Logger logger = Logger.getLogger(InteractUserlevelListServiceImpl.class);
	
	private static final long WORK_TIME = 10*60*1000;//毫秒级工作时间，当前时间-work_time 至 当前时间 范围内的新发的织图10*60*1000
	
	@Value("${plan.comment.common.userlevel.id}")
	private Integer commonUserLevelId;
	
	@Value("${plan.comment.trust.userlevel.id}")
	private Integer trustUserLevelId;
	
	@Value("${plan.comment.star.userlevel.id}")
	private Integer starUserLevelId;
	
	@Value("${plan.comment.super.star.userlevel.id}")
	private Integer superStarUserLevelId;
	
	@Autowired
	private InteractUserlevelListDao interactUserlevelListDao;
	
	@Autowired
	private HTWorldLabelWorldDao worldLabelWorldDao;
	
	@Autowired
	private InteractWorldService interactWorldService;
	
	@Autowired
	private InteractUserlevelDao  interactUserlevelDao;
	
	@Autowired
	private InteractWorldLabelCommentLabelDao interactWorldLabelCommentLabelDao;
	
	@Autowired
	private InteractCommentService interactCommentService;
	
	@Autowired
	private InteractPlanCommentLabelService interactPlanCommentLabelService;
	
	@Autowired
	private InteractPlanCommentService  interactPlanCommentService;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private ZTWorldMapper ztworldMapper;
	
	
	public Integer getStarUserLevelId() {
		return starUserLevelId;
	}

	public void setStarUserLevelId(Integer starUserLevelId) {
		this.starUserLevelId = starUserLevelId;
	}

	public Integer getTrustUserLevelId() {
		return trustUserLevelId;
	}

	public void setTrustUserLevelId(Integer trustUserLevelId) {
		this.trustUserLevelId = trustUserLevelId;
	}

	public Integer getCommonUserLevelId() {
		return commonUserLevelId;
	}
	
	public void setCommonUserLevelId(Integer commonUserLevelId) {
		this.commonUserLevelId = commonUserLevelId;
	}

	/**
	 * 扫描新发的织图。然后根据用户等级为该织图添加互动	
	 */
	@Override
	public void ScanNewWorldAndJoinIntoInteract(){
		Integer successTotalCount = 0;
		Date currentDate = new Date();
		Map<Integer,Integer> userLevelMap = new HashMap<Integer,Integer>();
		logger.info("织图添加互动 begin ：" + currentDate);
		Date startTime = new Date(currentDate.getTime() - WORK_TIME);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
		List<UserLevelDto> userlevelDtoList=null;
		List<InteractWorldLabelDto> interactWorldLabel=null;
		try{
			userlevelDtoList = interactUserlevelDao.QueryUserlevelList();
			//已经没有查询userlevelId
			interactWorldLabel = interactUserlevelListDao.QueryNewWorldUserlevelListByTime(startTime, currentDate);
		}catch(Exception e){
			e.printStackTrace();
		}
		for(InteractWorldLabelDto o:interactWorldLabel){//新发织图遍历
			
			/**
			 * 确定用户等级
			 * 规则：	首先查询该用户是否已经被添加等级
			 * 		只要是用户明星标志的，就默认是明星用户.。
			 * 		新用户，进来就默认是普通用户，如果普通用户，发图超过10个，并且人工也点了信任用户，那这个用户的等级，就升级为信任用户。
			 * 		系统设定的等级，系统不用在去审核他，只有人工去改变他们的等级
			 */
			Integer userlevelId = userLevelMap.get(o.getUser_id());
			if(null == userlevelId){//用户等级站里没有记录到该用户，则说明该用户没有被操作过。
				//查询该用户的等级id，若没有等级，则为null
				o.setUser_level_id( interactUserlevelListDao.QueryUserlevelIdByUserId(o.getUser_id()));
			}
			
			if(null == userlevelId){//用户等级站里没有记录到该用户，则说明该用户没有被操作过。
				if(o.getUser_level_id() == null || o.getUser_level_id() == 0){//该用户没有等级，则默认添加为普通用户
						interactUserlevelListDao.AddUserlevel(
								new UserLevelListDto(
										null,
										o.getUser_id(),
										commonUserLevelId,//普通用户等级id
										Tag.TRUE,
										null,
										null,
										currentDate,
										currentDate,
										0,//操作者：暂无
										null
										));
					o.setUser_level_id(commonUserLevelId);
				}else{//该用户有等级
					//查看该用户是否为明星用户
					UserInfo userInfo = userInfoMapper.selectById(o.getUser_id());
					if(userInfo.getStar() > 0){//明星用户
						if(o.getUser_level_id() != superStarUserLevelId && o.getUser_level_id() != starUserLevelId){//改用户当前等级不为超级明星，也不是明星,则设置其为明星等级
							UserLevelListDto userLevelListDto = new UserLevelListDto();
							userLevelListDto.setUser_id(o.getUser_id());
							userLevelListDto.setUser_level_id(starUserLevelId);
							userLevelListDto.setValidity(Tag.TRUE);
							userLevelListDto.setModifyDate(currentDate);
							userLevelListDto.setOperatorId(0);
							interactUserlevelListDao.UpdateUserlevelByUserId(userLevelListDto);
							o.setUser_level_id(starUserLevelId);
						}
						
					} else {//非明星用户
						//普通用户，发图超过10个，并且人工也点了信任用户
						if(o.getUser_level_id() == commonUserLevelId && userInfo.getTrust() == Tag.TRUE){
							long worldCount = ztworldMapper.queryWorldCountByUserId(o.getUser_id());
							if(worldCount > 10){
								UserLevelListDto userLevelListDto = new UserLevelListDto();
								userLevelListDto.setUser_id(o.getUser_id());
								userLevelListDto.setUser_level_id(trustUserLevelId);
								userLevelListDto.setValidity(Tag.TRUE);
								userLevelListDto.setModifyDate(currentDate);
								userLevelListDto.setOperatorId(0);
								interactUserlevelListDao.UpdateUserlevelByUserId(userLevelListDto);
								o.setUser_level_id(trustUserLevelId);
							}
						}
					}
				}
				userLevelMap.put(o.getUser_id(), o.getUser_level_id());
			} else {//若用户等级站里有记录到该用户,则将改用户的等级设置为所记录的。原因是：可能同一个用户发了多张织图，该用户等级为C，根据规则，该用户的等级可能升级为B，此时，处理该用户的第一张织图时，其等级已经升级，并已经记录到userLevelMap
				o.setUser_level_id(userlevelId);
			}
			successTotalCount++;
			if(successTotalCount == 99){
				successTotalCount =99;
			}
			/**
			 * 广告用户,织图不能出现在最新
			 */
			if (o.getUser_level_id() == 27){
				ztworldMapper.updateLatestInvalid(o.getUser_id());
				continue;
			}
			
			/**
			 * 按用户等级来添加互动	
			 */
			for(UserLevelDto userlevel:userlevelDtoList){//等级遍历，匹配新发织图的用户对于的用户等级
				if(userlevel.getId().equals(o.getUser_level_id())){
					try{
						interactWorldService.saveUserInteract(o.getUser_id(),GetLongRandamNum(userlevel.getMin_fans_count(),userlevel.getMax_fans_count()),userlevel.getTime());//添加粉丝
						
						StringBuilder strb = new StringBuilder();
						List<InteractPlanCommentLabel> list = interactPlanCommentLabelService.queryInteractPlanCommentLabelByDateAndTime(df.parse(df.format(currentDate)), df2.parse(df2.format(currentDate)));//查询当前有效标签
						if(list != null && list.size() > 0){
							int commentsize = GetLongRandamNum(userlevel.getMin_comment_count(),userlevel.getMax_comment_count());
							int average = 0;
							int length = list.size();
							if(commentsize < list.size()){
								average = 1;
								length = commentsize;
							} else {
								average = commentsize / list.size();
							}
							for(int i=0;i<length;i++){
								List<InteractPlanComment> planCommentList = interactPlanCommentService.queryNRandomPlanCommentByGroupId(average, list.get(i).getId());
								for(int j=0;j<planCommentList.size();j++){
									if(strb.length() > 0){
										strb.append(",");
									}
									strb.append(planCommentList.get(j).getInteractCommentId());
								}
							}
							
						}
						
						String[] commentsArray = null;
						if(strb.length()>0){
							commentsArray = strb.toString().split(",");
						}
						
						interactWorldService.saveInteractV2(o.getWorldId(), GetLongRandamNum(userlevel.getMin_play_times(),userlevel.getMax_play_times()), 
								GetLongRandamNum(userlevel.getMin_liked_count(),userlevel.getMax_liked_count()), commentsArray, userlevel.getTime());//添加互动
						/*
						interactWorldService.saveInteractV2(o.getWorldId(), GetLongRandamNum(userlevel.getMin_play_times(),userlevel.getMax_play_times()), 
									GetLongRandamNum(userlevel.getMin_liked_count(),userlevel.getMax_liked_count()), null, userlevel.getTime());//添加互动
						List<InteractPlanCommentLabel> list = interactPlanCommentLabelService.queryInteractPlanCommentLabelByDateAndTime(df.parse(df.format(currentDate)), df2.parse(df2.format(currentDate)));//查询当前有效标签
						if(list != null && list.size() > 0){
							int commentsize = GetLongRandamNum(userlevel.getMin_comment_count(),userlevel.getMax_comment_count());
							int average = 0;
							int length = list.size();
							if(commentsize < list.size()){
								average = 1;
								length = commentsize;
							} else {
								average = commentsize / list.size();
							}
							
							for(int i=0;i<length;i++){
								String commentStr = "";
								List<InteractPlanComment> planCommentList = interactPlanCommentService.queryNRandomPlanCommentByGroupId(average, list.get(i).getId());
								for(int j=0;j<planCommentList.size();j++){
									commentStr += planCommentList.get(j).getInteractCommentId();
									if(j != planCommentList.size()-1)
										commentStr += ",";
								}
								interactWorldService.saveInteractV2(o.getWorldId(), 0, 
									0, commentStr.split(","), userlevel.getTime());//添加互动
							}
						}
						*/
						
					}catch(Exception e){
						e.printStackTrace();
					}
					break;
				}
			}
		}
		Date end = new Date();
		logger.info("织图添加互动 end , 共花费：" + (end.getTime() - currentDate.getTime())+" . total:"+interactWorldLabel.size()+". success:"+successTotalCount);
	}
	
	/**
	 * 删除用户等级by ids
	 */
	@Override
	public void DeleteUserlevelByIds(String idsStr)throws Exception{
		Integer[]ids = StringUtil.convertStringToIds(idsStr);
		interactUserlevelListDao.DeleteUserlevelByIds(ids);
	}
	
	/**
	 * 查询用户等级列表
	 */
	@Override
	public void QueryUserlevelList(int maxId,Integer timeType,Date beginTime,Date endTime,Integer userId,int start,int limit,Map<String ,Object> jsonMap)throws Exception{	
		final Map<String,Object> attr = new HashMap<String,Object>();
		if(null != userId)
			attr.put("userId", userId);
		if(null != timeType)
			attr.put("timeType", timeType);
		if(null != beginTime)
			attr.put("beginTime", beginTime);
		if(null != endTime)
			attr.put("endTime", endTime);
		
		buildSerializables(maxId,start,limit,jsonMap,new SerializableListAdapter<UserLevelListDto>(){
			@Override
			public List<UserLevelListDto> getSerializables(RowSelection rowSelection){
				return interactUserlevelListDao.QueryUserlevelList(attr,rowSelection);
			}
			@Override 
			public List<UserLevelListDto> getSerializableByMaxId(int maxId,RowSelection rowSelection){
				return interactUserlevelListDao.QueryUserlevelListByMaxId(maxId,attr,rowSelection);
			}
			@Override
			public long getTotalByMaxId(int maxId){
				return interactUserlevelListDao.GetUserlevelListCount(maxId,attr);
			}
		},OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);
	}
	
	/**
	 * 增加等级用户，若该用户已存在等级，则更新其等级
	 */
	public void AddUserlevel(UserLevelListDto userLevelDto)throws Exception{
		if(!CheckUserlevelExistByUserId(userLevelDto.getUser_id())){//若不存在，则新增
			interactUserlevelListDao.AddUserlevel(userLevelDto);
		}else{//若存在，则更新
			interactUserlevelListDao.UpdateUserlevelByUserId(userLevelDto);
		}
		//广告用户
		if(userLevelDto.getUser_level_id() == 27){
			userInfoMapper.updateTrust(userLevelDto.getUser_id(), Tag.FALSE);
		}
		
	}
	
	
	/**
	 * 随机函数生成介于min，max之间的数,最大是10000000
	 */
	private Integer GetLongRandamNum(Integer min,Integer max){
		int ma = max.intValue();
		int mi = min.intValue();
		if(mi>ma||mi<0)return 1;
		if(mi==ma)return max;
		int l=0;
		l = (int)(Math.random()*10000007)%(ma-mi+1);
		l = mi+l;
		return new Integer(l);
		
	}
	
	/**
	 * 根据用户id查询其用户等级
	 */
	@Override
	public UserLevelListDto QueryUserlevelByUserId(Integer userId)throws Exception{
		if(CheckUserlevelExistByUserId(userId)){
			return interactUserlevelListDao.QueryUserlevelByUserId(userId);
		}else{
			return null;
		}
		
	}
	
	/**
	 * 检查userId是否被评为等级
	 */
	@Override
	public boolean CheckUserlevelExistByUserId(Integer userId)throws Exception{
		return interactUserlevelListDao.CheckUserlevelExistByUserId(userId);
	}
	
	/**
	 * 更新用户等级
	 */
	@Override
	public void UpdateUserlevelByUserId(UserLevelListDto userLevelListDto)throws Exception{
		interactUserlevelListDao.UpdateUserlevelByUserId(userLevelListDto);
	}
}

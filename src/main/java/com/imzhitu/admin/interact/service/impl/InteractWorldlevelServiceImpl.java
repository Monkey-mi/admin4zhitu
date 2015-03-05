package com.imzhitu.admin.interact.service.impl;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.ZTWorldLevelDto;
import com.imzhitu.admin.interact.dao.InteractWorldlevelDao;
import com.imzhitu.admin.interact.service.InteractCommentService;
import com.imzhitu.admin.interact.service.InteractWorldService;
import com.imzhitu.admin.interact.service.InteractWorldlevelService;

@Service
public class InteractWorldlevelServiceImpl extends BaseServiceImpl implements InteractWorldlevelService {
	
	private Integer ONE_HOUR_COMPLETE_RATE = 70;
	
	@Autowired
	private InteractWorldlevelDao interactWorldlevelDao;
	
	@Autowired
	private InteractWorldService interactWorldService;
	
	@Autowired
	private InteractCommentService interactCommentService;

	@Override
	public void QueryWorldlevelList(int maxId, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		// TODO Auto-generated method stub
		buildSerializables(maxId, start, limit, jsonMap,new SerializableListAdapter<ZTWorldLevelDto>(){
			@Override
			public List<ZTWorldLevelDto> getSerializables(RowSelection rowSelection){
				return interactWorldlevelDao.QueryWorldlevelList(rowSelection);
			}
			
			@Override
			public List<ZTWorldLevelDto> getSerializableByMaxId(int maxId,RowSelection rowSelection){
				return interactWorldlevelDao.QueryWorldlevelListByMaxId(maxId, rowSelection);
			}
			
			@Override
			public long getTotalByMaxId(int maxId){
				return interactWorldlevelDao.GetWorldlevelCountByMaxId(maxId);
			}
		},OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);

	}

	@Override
	public void DeleteWorldlevelByIds(String idsStr) throws Exception {
		// TODO Auto-generated method stub
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		interactWorldlevelDao.DeleteWorldLevelByIds(ids);
	}

	@Override
	public ZTWorldLevelDto QueryWorldlevelById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return interactWorldlevelDao.QueryWorldlevelById(id);
	}

	@Override
	public void AddWorldlevel(ZTWorldLevelDto worldlevelDto) throws Exception {
		// TODO Auto-generated method stub
		interactWorldlevelDao.AddWorldlevel(worldlevelDto);
	}

	@Override
	public void UpdateWorldlevel(ZTWorldLevelDto worldlevelDto)
			throws Exception {
		// TODO Auto-generated method stub
		interactWorldlevelDao.UpdateWorldlevel(worldlevelDto);
	}
	
	/**
	 * 查找织图等级列表
	 */
	@Override
	public List<ZTWorldLevelDto> QueryWorldLevel()throws Exception{
		return interactWorldlevelDao.QueryWorldLevel();
	}
	
	/**
	 * 更新织图等级
	 */
	public void updateWorldLevelByRowJson(String rowJson)throws Exception{
		JSONArray jsArray = JSONArray.fromObject(rowJson);
		for(int i=0;i<jsArray.size();i++){
			JSONObject jsObj = jsArray.getJSONObject(i);
			int id = jsObj.optInt("id");
			int minFansCount = jsObj.optInt("min_fans_count");
			int maxFansCount = jsObj.optInt("max_fans_count");
			int minlikedCount = jsObj.optInt("min_liked_count");
			int maxlikedCount = jsObj.optInt("max_liked_count");
			int mincommentCount = jsObj.optInt("min_comment_count");
			int maxcommentCount = jsObj.optInt("max_comment_count");
			int minPlayTimes = jsObj.optInt("min_play_times");
			int maxPlayTimes = jsObj.optInt("max_play_times");
			int time = jsObj.optInt("time");
			int weight = jsObj.optInt("weight");
			String level_description = jsObj.getString("level_description");
			if(id < 0 || minFansCount < 0 || maxFansCount < minFansCount || minlikedCount < 0||
					maxlikedCount < minlikedCount || mincommentCount < 0 || maxcommentCount < mincommentCount ||
					minPlayTimes < 0 || maxPlayTimes < minPlayTimes || time <=0 && time > 72) continue;
			interactWorldlevelDao.UpdateWorldlevel(new ZTWorldLevelDto(id,
					minFansCount,maxFansCount,
					minlikedCount,maxlikedCount,
					mincommentCount,maxcommentCount,
					minPlayTimes,maxPlayTimes,
					time,level_description,weight));
		}
		
	}
	
	/**
	 * 增加等级织图
	 */
	@Override
	public void AddLevelWorld(Integer world_id,Integer id,String labelsStr,String comments)throws Exception{
		ZTWorldLevelDto worldLevel=null;
		if(world_id == null || world_id == 0){//若织图id为空，则什么都不做
			throw new Exception("AddLevelWorld:world_id is null");
		}
		
		if(id != null){
			worldLevel = interactWorldlevelDao.QueryWorldlevelById(id);
			if(worldLevel != null){
				Integer uid = interactWorldlevelDao.QueryUIDByWID(world_id);
				if(uid != null){
					try{
						interactWorldService.saveUserInteract(uid,
								GetLongRandamNum(worldLevel.getMin_fans_count(),worldLevel.getMax_fans_count()),1);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
			}
			
		}
		
		if((labelsStr==null || labelsStr.equals("")) && (id == null || id == 0)){//若等级id为空，且标签为空
			if(comments != null && !comments.equals("")){//若评论不为空，则只评论，且平时时长为1小时
				interactWorldService.saveInteractV2(world_id,0,0,comments.split(","),60);				
			}
			return ;
		}
		
		if((labelsStr==null || labelsStr.equals("")) && (id != null)){//若标签为空，等级id 不为空
			if(worldLevel == null){
				worldLevel = new ZTWorldLevelDto(0,0,0,0,0,0,0,0,2,0,null,0);
//				throw new Exception("interactWorldLevelServiceImpl:AddLevelWorld: worldLevel is NULL");
			}
			if(comments != null && !comments.equals("")){//若评论不为空，则只评论，且平时时长为10小时
				interactWorldService.saveInteractV2(world_id,GetLongRandamNum(worldLevel.getMin_play_times(),worldLevel.getMax_play_times()),
						GetLongRandamNum(worldLevel.getMin_liked_count(),worldLevel.getMax_liked_count()),comments.split(","),worldLevel.getTime());				
			}else{//若评论为空，则不评论
				interactWorldService.saveInteractV2(world_id,GetLongRandamNum(worldLevel.getMin_play_times(),worldLevel.getMax_play_times()),
						GetLongRandamNum(worldLevel.getMin_liked_count(),worldLevel.getMax_liked_count()),null,worldLevel.getTime());
			}
			return ;
		}
		
		/*
		if(labelsStr!=null ){//标签不为空的时候,则分情况来讨论，若等级id为空和不为空的两种情况
			Integer commentCountInteger;
			Integer playTimes;
			Integer likedCount;
			Integer time;
			String[] labels=labelsStr.split(",");
			int commentCount;
			if(id != null){
				if(worldLevel == null){
					commentCount = 0;
					playTimes = 0;
					likedCount = 0;
					time = 20;
//					throw new Exception("interactWorldLevelServiceImpl:AddLevelWorld: worldLevel is NULL");
				}else{
					commentCountInteger = GetLongRandamNum(worldLevel.getMin_comment_count(),worldLevel.getMax_comment_count());//获取每个标签对应的评论数
					commentCount = commentCountInteger/labels.length < 1 ? 1 : commentCountInteger/labels.length;
					playTimes = GetLongRandamNum(worldLevel.getMin_play_times(),worldLevel.getMax_play_times());
					likedCount = GetLongRandamNum(worldLevel.getMin_liked_count(),worldLevel.getMax_liked_count());
					time = worldLevel.getTime();
				}
			}else{
				commentCount = 1;
				playTimes = 1;
				likedCount = 1;
				time = 20;
			}
			
			
			//标签不为空时，根据标签来添加评论
			List<Integer> commentIds = new java.util.ArrayList<Integer>();
			
			//根据标签来获取评论
			for(String label:labels){
				List<Integer> commentsList;
				try{
					commentsList = interactCommentService.getRandomCommentIds(Integer.parseInt(label.trim()),commentCount);
				}catch(Exception e){
					e.printStackTrace();
					commentsList = null;
				}
				
				if(commentsList == null || commentsList.size()<1)continue;//没有评论，则continue
				commentIds.addAll(commentsList);
			}
			
			if(commentIds.size() == 0){//若是没有获得评论，则不评论
				if(comments==null || comments.equals("")){
					interactWorldService.saveInteractV2(world_id,playTimes,likedCount,null,time);
				}else{
					interactWorldService.saveInteractV2(world_id,playTimes,likedCount,comments.split(","),time);
				}				
				return ;
			}else{//若有评论,
				String s = commentIds.toString();
				String s1;
				if(comments==null || comments.equals("")){
					s1 = s.substring(1, s.length()-1);
				}else{
					s1 = s.substring(1, s.length()-1) + ","+comments;
				}
				String[] commentIdsStrArray = s1.split(",");//List toString 的过程中的数据格式是[]这样的
				interactWorldService.saveInteractV2(world_id,playTimes,likedCount,commentIdsStrArray,time);
			}
		}*/			
	}
	
	/**
	 * 增加等级织图互动计划
	 */
	@Override
	public void AddTypeWorldInteract(Integer worldId,Integer worldLevelId,String commentStr)throws Exception {
		if(worldId == null || worldId == 0){//若织图id为空，则什么都不做
			throw new Exception("AddLevelWorld:world_id is null");
		}
		if(worldLevelId == null || worldLevelId == 0){//若织图id为空，则什么都不做
			throw new Exception("AddLevelWorld:worldLevelId is null");
		}
		
		ZTWorldLevelDto worldLevel = interactWorldlevelDao.QueryWorldlevelById(worldLevelId);
		if(worldLevel != null){
			Integer uid = interactWorldlevelDao.QueryUIDByWID(worldId);
			if(uid != null){
				try{
					
					//一小时内发完80%
					Integer likeCountOneHour = 0;
					Integer playCountOneHour = 0;
					Integer commentCountOneHour = 0;
					Integer likeCount = worldLevel.getMax_liked_count() + worldLevel.getMin_liked_count() / 2;
					Integer playCount = worldLevel.getMax_play_times() + worldLevel.getMin_play_times() / 2;
					likeCountOneHour = likeCount  * ONE_HOUR_COMPLETE_RATE / 100;
					playCountOneHour = playCount  * ONE_HOUR_COMPLETE_RATE / 100;
					if(commentStr != null && !"".equals(commentStr.trim())){//有评论
						String[] commentIds = commentStr.split(",");
						commentCountOneHour = commentIds.length*ONE_HOUR_COMPLETE_RATE / 100;
						
						//一小时内发完80%
						if(commentCountOneHour > 0) {
							String[] comentIdsOneHour = new String[commentCountOneHour];
							for(int i=0; i<commentCountOneHour && i<commentIds.length; i++){
								comentIdsOneHour[i] = commentIds[i];
							}
							interactWorldService.saveInteractV2(worldId,playCountOneHour,likeCountOneHour,comentIdsOneHour,60);	
						}
						
						//剩下的照常发
						if(commentIds.length - commentCountOneHour > 0){
							String [] commentIdsOther = new String[commentIds.length - commentCountOneHour];
							for( int j=0; j<commentIds.length-commentCountOneHour; j++){
								commentIdsOther[j] = commentIds[j+commentCountOneHour];
							}
							interactWorldService.saveInteractV2(worldId,playCount - playCountOneHour,likeCount - likeCountOneHour,commentIdsOther,worldLevel.getTime());	
						}
					} else {//没有评论
						interactWorldService.saveInteractV2(worldId,playCountOneHour,likeCountOneHour,null,60);	
						interactWorldService.saveInteractV2(worldId,playCount - playCountOneHour,likeCount - likeCountOneHour,null,worldLevel.getTime());
					}
					 
					interactWorldService.saveUserInteract(uid,
							GetLongRandamNum(worldLevel.getMin_fans_count(),worldLevel.getMax_fans_count()),1);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
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
		l = (int)(Math.random()*100007)%(ma-mi+1);
		l = mi+l;
		return new Integer(l);
		
	}

}

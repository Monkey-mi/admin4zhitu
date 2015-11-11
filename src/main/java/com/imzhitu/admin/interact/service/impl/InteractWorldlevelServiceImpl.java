package com.imzhitu.admin.interact.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.ZTWorldLevelDto;
import com.imzhitu.admin.common.util.AdminUtil;
import com.imzhitu.admin.interact.dao.InteractWorldlevelDao;
import com.imzhitu.admin.interact.service.InteractWorldService;
import com.imzhitu.admin.interact.service.InteractWorldlevelService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class InteractWorldlevelServiceImpl extends BaseServiceImpl implements InteractWorldlevelService {
	
	@Autowired
	private InteractWorldlevelDao interactWorldlevelDao;
	
	@Autowired
	private InteractWorldService interactWorldService;
	
	@Override
	public void QueryWorldlevelList(int maxId, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
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
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		interactWorldlevelDao.DeleteWorldLevelByIds(ids);
	}

	@Override
	public ZTWorldLevelDto QueryWorldlevelById(Integer id) throws Exception {
		return interactWorldlevelDao.QueryWorldlevelById(id);
	}

	@Override
	public void AddWorldlevel(ZTWorldLevelDto worldlevelDto) throws Exception {
		interactWorldlevelDao.AddWorldlevel(worldlevelDto);
	}

	@Override
	public void UpdateWorldlevel(ZTWorldLevelDto worldlevelDto)
			throws Exception {
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
	 * world_id 织图ID
	 * id 织图等级
	 * comments 评论
	 */
	@Override
	public void AddLevelWorld(Integer world_id,Integer id,String labelsStr,String comments)throws Exception{
		ZTWorldLevelDto worldLevel=null;
		if(world_id == null || world_id == 0){//若织图id为空，则什么都不做
			throw new Exception("AddLevelWorld:world_id is null");
		}
		
		//查询对应的马甲zombieDegreeId -- 作者ID
		Integer uid = interactWorldlevelDao.QueryUIDByWID(world_id);
		
		//添加互动
		if(id != null){
			worldLevel = interactWorldlevelDao.QueryWorldlevelById(id);
			if(worldLevel != null){
				try{
					interactWorldService.saveUserInteract(uid,
							AdminUtil.GetRandamNum(worldLevel.getMin_fans_count(),worldLevel.getMax_fans_count()),worldLevel.getTime());
					if(comments != null && !comments.equals("")){//若评论不为空，则只评论，且平时时长为10小时
						interactWorldService.saveInteractV3(world_id,AdminUtil.GetRandamNum(worldLevel.getMin_play_times(),worldLevel.getMax_play_times()),
								AdminUtil.GetRandamNum(worldLevel.getMin_liked_count(),worldLevel.getMax_liked_count()),comments.split(","),worldLevel.getTime());				
					}else{
						interactWorldService.saveInteractV3(world_id,AdminUtil.GetRandamNum(worldLevel.getMin_play_times(),worldLevel.getMax_play_times()),
								AdminUtil.GetRandamNum(worldLevel.getMin_liked_count(),worldLevel.getMax_liked_count()),null,worldLevel.getTime());
					}
				}catch(Exception e){
					throw e;
				}
				
			}
			
		}
			
	}
	
	/**
	 * 增加等级织图互动计划
	 */
	@Override
	public void AddTypeWorldInteract(Integer worldId,Integer worldLevelId,String commentStr)throws Exception {
		if(worldId == null || worldId == 0){//若织图id为空，则什么都不做
			throw new Exception("AddLevelWorld:world_id is null");
		}
		if(worldLevelId == null || worldLevelId == 0){//若织图等级id为空，则什么都不做
			throw new Exception("AddLevelWorld:worldLevelId is null");
		}
		
		ZTWorldLevelDto worldLevel = interactWorldlevelDao.QueryWorldlevelById(worldLevelId);
		if(worldLevel != null){
//			if(commentStr != null && !"".equals(commentStr.trim())){//有评论
				String[] commentIds = commentStr.split(",");
				Integer likeCount = worldLevel.getMax_liked_count() + worldLevel.getMin_liked_count() / 2;
				Integer playCount = worldLevel.getMax_play_times() + worldLevel.getMin_play_times() / 2;
//				interactWorldService.saveInteractV3(worldId, playCount, likeCount, commentIds, worldLevel.getTime());
				// TODO 目前广场互动的逻辑与老版本的saveInteractV3一致，这块以后肯定要整体整改
				interactWorldService.saveTypeInteract(worldId, playCount, likeCount, commentIds, worldLevel.getTime());
//			}
			
			Integer uid = interactWorldlevelDao.QueryUIDByWID(worldId);
			interactWorldService.saveUserInteract(uid, AdminUtil.GetRandamNum(worldLevel.getMin_fans_count(),worldLevel.getMax_fans_count()),worldLevel.getTime());			
		}
		
	}
	
}

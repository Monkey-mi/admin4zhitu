package com.imzhitu.admin.interact.service.impl;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.UserLevelDto;
import com.imzhitu.admin.interact.service.InteractUserlevelService;
import com.imzhitu.admin.interact.dao.InteractUserlevelDao;

@Service
public class InteractUserlevelServiceImpl extends BaseServiceImpl implements InteractUserlevelService{
	
	@Autowired
	private InteractUserlevelDao  interactUserlevelDao;
	
	/**
	 * 查询用户等级列表
	 */
	@Override
	public void QueryUserlevelList(int maxId,int start,int limit,Map<String ,Object> jsonMap) throws Exception{
		buildSerializables(maxId,start,limit,jsonMap,new SerializableListAdapter<UserLevelDto>(){
			
			@Override
			public List<UserLevelDto> getSerializables(RowSelection rowSelection){
				return interactUserlevelDao.QueryUserlevelList(rowSelection);
			}
			
			@Override
			public List<UserLevelDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return interactUserlevelDao.QueryUserlevelListByMaxId(maxId,rowSelection);
			}
			
			@Override
			public long getTotalByMaxId(int maxId){
				return interactUserlevelDao.GetUserLevelCountByMaxId(maxId);
			}
		},OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);
	}
	
	/**
	 * 根据ids删除用户等级
	 */
	@Override
	public void DeleteUserlevelByIds(String idsStr)throws Exception{
		try{
			Integer[] ids = StringUtil.convertStringToIds(idsStr);		
			interactUserlevelDao.DeleteUserlevelByIds(ids);
		}catch(Exception e){
			throw e;
		}
		
	}
	
	/**
	 * 更新用户等级
	 */
	@Override
	public void UpdateUserlevelById(UserLevelDto userlevelDto)throws Exception{
		try{
			interactUserlevelDao.UpdateUserlevelById(userlevelDto);
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 增加用户等级
	 */
	@Override
	public void AddUserlevel(UserLevelDto userlevelDto)throws Exception{
		try{
			interactUserlevelDao.AddUserlevel(userlevelDto);
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 根据id查询用户等级
	 */
	@Override
	public UserLevelDto QueryUserlevelById(Integer id)throws Exception{
		try{
			return interactUserlevelDao.QueryUserlevelById(id);
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 查询用户等级列表
	 */
	@Override
	public List<UserLevelDto> QueryUserLevel() throws Exception{
		return interactUserlevelDao.QueryUserLevel();
	}
	/**
	 * 根据rowJson来更新用户等级
	 */
	@Override
	public void updateUserLevelByRowJson(String rowJson)throws Exception{
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
					minPlayTimes < 0 || maxPlayTimes < minPlayTimes || time <=0 && time > 72 || weight < 0) continue;
			interactUserlevelDao.UpdateUserlevelById(new UserLevelDto(id,
					minFansCount,maxFansCount,
					minlikedCount,maxlikedCount,
					mincommentCount,maxcommentCount,
					minPlayTimes,maxPlayTimes,
					time,level_description,weight));
		}
	}

}

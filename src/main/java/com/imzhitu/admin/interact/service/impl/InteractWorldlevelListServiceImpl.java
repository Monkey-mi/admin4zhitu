package com.imzhitu.admin.interact.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.Log;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.interact.dao.InteractWorldlevelListDao;
import com.imzhitu.admin.interact.dao.InteractWorldlevelDao;
import com.imzhitu.admin.interact.service.InteractWorldlevelListService;
import com.imzhitu.admin.common.pojo.InteractWorldLevelListDto;
import com.imzhitu.admin.common.pojo.ZTWorldLevelDto;
import com.imzhitu.admin.interact.dao.InteractWorldlevelWorldCommentDao;
import com.imzhitu.admin.interact.dao.InteractWorldlevelWorldLabelDao;
import com.imzhitu.admin.common.pojo.InteractWorldlevelWorldComment;
import com.imzhitu.admin.common.pojo.InteractWorldlevelWorldLabel;

@Service
public class InteractWorldlevelListServiceImpl extends BaseServiceImpl implements InteractWorldlevelListService{
	
	@Autowired
	private InteractWorldlevelListDao interactWorldlevelListDao ;
	
	@Autowired
	private InteractWorldlevelDao interactWorldlevelDao;
	
	@Autowired
	private InteractWorldlevelWorldCommentDao interactWorldlevelWorldCommentDao;
	
	@Autowired
	private InteractWorldlevelWorldLabelDao interactWorldlevelWorldLabelDao;
	
	@Override
	public void queryWorldlevelList(int maxId,Integer wid,Integer timeType,Date beginTime,Date endTime,int start,int limit,Map<String ,Object> jsonMap)throws Exception{
		final Map<String,Object> attr = new HashMap<String,Object>();
		if(null != wid)
			attr.put("worldId", wid);
		if(null != timeType)
			attr.put("timeType", timeType);
		if(null != beginTime)
			attr.put("beginTime", beginTime);
		if(null != endTime)
			attr.put("endTime", endTime);
		
		buildSerializables(maxId,start,limit,jsonMap,new SerializableListAdapter<InteractWorldLevelListDto>(){
			@Override
			public List<InteractWorldLevelListDto>getSerializables(RowSelection rowSelection){
				return interactWorldlevelListDao.queryWorldLevelList(attr,rowSelection);
			}
			@Override
			public List<InteractWorldLevelListDto>getSerializableByMaxId(int maxId,RowSelection rowSelection){
				return interactWorldlevelListDao.queryWorldLevelList(maxId, attr,rowSelection);
			}
			@Override
			public long getTotalByMaxId(int maxId){
				return interactWorldlevelListDao.getWorldLevelListCountByMaxId(maxId,attr);
			}
		},OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);
	}
	
	@Override
	public void addWorldlevelList(Integer world_id,Integer world_level_id,Integer validity,String comment_ids,String label_ids,Integer operatorId)throws Exception{
		if(world_id==null)return ;
		if(validity==null)validity = new Integer(Tag.FALSE);
		Date now = new Date();
		InteractWorldLevelListDto dto = interactWorldlevelListDao.queryWorldLevelListByWid(world_id);
		if( dto == null )
			interactWorldlevelListDao.addWorldLevelList(world_id, world_level_id,Tag.FALSE,now,now,operatorId);//增加织图等级
		else{
			if(world_level_id != null)
				interactWorldlevelListDao.updateWorldLevelList(world_id, world_level_id, Tag.FALSE,now,operatorId);//更新织图等级
		}
		if(comment_ids != null){//增加评论
			try{
				Integer[] commentIds = StringUtil.convertStringToIds(comment_ids);
				for(Integer cid:commentIds){
					try{
						interactWorldlevelWorldCommentDao.addWorldlevelWorldComment(world_id, cid, Tag.FALSE, now);
					}catch(Exception e1){
						Log.info("织图等级添加模块：插入织图等级中织图评论失败。原因是重复评论！wid="+world_id+".commentId="+cid);
					}
				}
			}catch(Exception e){
				Log.info("织图等级添加模块：评论字符串转成整型数组失败。wid="+world_id);
			}
		}	
		if(label_ids != null){//增加标签
			try{
				Integer[] labelIds = StringUtil.convertStringToIds(label_ids);
				for(Integer lid:labelIds){
					try{
						interactWorldlevelWorldLabelDao.addWorldlevelWorldLabel(world_id, lid, Tag.FALSE, now);
					}catch(Exception e1){
						Log.info("织图等级添加模块：插入织图等级中织图标签失败。wid="+world_id+". labelId="+lid);
					}
				}
			}catch(Exception e){
				Log.info("织图等级添加模块：标签字符串转成整型数组失败。wid="+world_id);
			}
		}
	}
	
		
	
	@Override
	public void delWorldlevelListByWIds(String widsStr)throws Exception{
		if(widsStr == null || widsStr == "")return ;
		Integer[]wIds = StringUtil.convertStringToIds(widsStr);
		interactWorldlevelListDao.delWorldLevelListByWIds(wIds);
	}
	
	@Override
	public void updateWorldlevelListValidity(Integer world_id,Integer world_level_id,Integer validity,Integer operatorId)throws Exception{
		if(world_id==null||world_level_id==null)return ;
		if(validity==null)validity = new Integer(Tag.FALSE);
		boolean r = interactWorldlevelListDao.checkWorldLevelListIsExistByWId(world_id);
		if( r == true ){
			interactWorldlevelListDao.updateWorldLevelList(world_id, world_level_id,validity,new Date(),operatorId);
			interactWorldlevelWorldCommentDao.updateWorldlevelWorldCommentComplete(world_id, validity);
//			interactWorldlevelWorldCommentDao.updateWorldlevelWorldCommentComplete(world_id, validity);
		}
//		else
//			interactWorldlevelListDao.addWorldLevelList(world_id, world_level_id,validity,null,null);
	}
	
	@Override
	public void updateWorldLevelList(Integer worldId,Integer world_level_id,Integer validity,String label_ids,String comment_ids,Integer operatorId)throws Exception{
		if(worldId==null||world_level_id==null)return ;
		if(validity==null)validity = new Integer(Tag.FALSE);
		Date now = new Date();
		boolean r = interactWorldlevelListDao.checkWorldLevelListIsExistByWId(worldId);
		if( r == true ){
			interactWorldlevelListDao.updateWorldLevelList(worldId, world_level_id, validity,new Date(),operatorId);
			if(comment_ids != null){//增加评论
				try{
					Integer[] commentIds = StringUtil.convertStringToIds(comment_ids);
					for(Integer cid:commentIds){
						try{
							interactWorldlevelWorldCommentDao.addWorldlevelWorldComment(worldId, cid, Tag.FALSE, now);
						}catch(Exception e1){
							Log.info("织图等级添加模块：插入织图等级中织图评论失败。原因是重复评论！wid="+worldId+".commentId="+cid);
						}
					}
				}catch(Exception e){
					Log.info("织图等级添加模块：评论字符串转成整型数组失败。wid="+worldId);
				}
			}
			if(label_ids != null){//增加标签
				try{
					Integer[] labelIds = StringUtil.convertStringToIds(label_ids);
					for(Integer lid:labelIds){
						try{
							interactWorldlevelWorldLabelDao.addWorldlevelWorldLabel(worldId, lid, Tag.FALSE, now);
						}catch(Exception e1){
							Log.info("织图等级添加模块：插入织图等级中织图标签失败。原因是重复!wid="+worldId+". labelId="+lid);
						}
					}
				}catch(Exception e){
					Log.info("织图等级添加模块：标签字符串转成整型数组失败。wid="+worldId);
				}
			}
		}
	}
	
	@Override
	public InteractWorldLevelListDto queryWorldLevelListByWid(Integer wid)throws Exception{
		InteractWorldLevelListDto dto = interactWorldlevelListDao.queryWorldLevelListByWid(wid);
		if(null == dto)return null;
		List<InteractWorldlevelWorldLabel> labelList = interactWorldlevelWorldLabelDao.queryWorldlevelWorldLabelByWid(wid);
		dto.setWorldLabelList(labelList);
		List<InteractWorldlevelWorldComment> commentList = interactWorldlevelWorldCommentDao.queryWorldlevelWorldCommentByWid(wid);
		dto.setWorldCommentList(commentList);
		return dto;
		
	}
	
	@Override
	public boolean chechWorldLevelListIsExistByWId(Integer wid)throws Exception{
		return interactWorldlevelListDao.checkWorldLevelListIsExistByWId(wid);
	}
	
	@Override
	public void queryWorldUNInteractCount(Integer wid,Map<String,Object> jsonMap)throws Exception{
		InteractWorldLevelListDto dto = interactWorldlevelListDao.queryWorldLevelListByWid(wid);
		List<InteractWorldlevelWorldComment> commentlist = interactWorldlevelWorldCommentDao.queryWorldlevelWorldCommentByWid(wid);
		if(dto != null){
			ZTWorldLevelDto levelDto = interactWorldlevelDao.QueryWorldlevelById(dto.getWorld_level_id());
			if(levelDto != null){
				jsonMap.put("clickCount", levelDto.getMin_play_times());
				jsonMap.put("commentCount", levelDto.getMin_comment_count()+commentlist.size());
				jsonMap.put("likedCount", levelDto.getMin_liked_count());
				return;
			}else{
				jsonMap.put("clickCount", 0);
				jsonMap.put("commentCount", 0+commentlist.size());
				jsonMap.put("likedCount", 0);
				return;
			}
		}else{
			jsonMap.put("clickCount", 0);
			jsonMap.put("commentCount", 0);
			jsonMap.put("likedCount", 0);
			return;
		}
	}
}

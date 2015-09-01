package com.imzhitu.admin.interact.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.InteractLikeFollowCommentLabel;
import com.imzhitu.admin.common.pojo.InteractLikeFollowRecord;
import com.imzhitu.admin.common.pojo.InteractUserFollow;
import com.imzhitu.admin.common.pojo.InteractWorldCommentDto;
import com.imzhitu.admin.common.pojo.InteractWorldLiked;
import com.imzhitu.admin.common.service.KeyGenService;
import com.imzhitu.admin.interact.mapper.InteractLikeFollowRecordMapper;
import com.imzhitu.admin.interact.service.InteractCommentService;
import com.imzhitu.admin.interact.service.InteractLikeFollowCommentLabelService;
import com.imzhitu.admin.interact.service.InteractLikeFollowRecordService;
import com.imzhitu.admin.interact.service.InteractLikeFollowZombieService;
import com.imzhitu.admin.interact.service.InteractWorldService;

//@Service
public class InteractLikeFollowRecordServiceImpl extends BaseServiceImpl implements InteractLikeFollowRecordService{

	@Autowired
	private InteractLikeFollowRecordMapper likeFollowRecordMapper;
	
	@Autowired
	private InteractWorldService worldService;
	
	@Autowired
	private InteractLikeFollowZombieService likeFollowZombieService;
	
	@Autowired
	private InteractLikeFollowCommentLabelService likeFollowCommentLabelService;
	
	@Autowired
	private InteractCommentService commentService;
	
	@Autowired
	private KeyGenService keyGenService;
	
	private Logger log = Logger.getLogger(InteractLikeFollowRecordServiceImpl.class);
	
	/**
	 * 插入互赞互粉，
	 * @param type 类型，用以区分互粉还是互赞。0表示互赞，1表示互粉
	 * @return
	 */
	@Override
	public void insertLikeFollowRecord(Integer zombieId, Integer worldId,
			Integer userId, Integer type, Integer complete,Integer interactWorldCommentId) throws Exception {
		// TODO Auto-generated method stub
		InteractLikeFollowRecord dto = new InteractLikeFollowRecord();
		dto.setZombieId(zombieId);
		dto.setWorldId(worldId);
		dto.setUserId(userId);
		dto.setComplete(complete);
		dto.setType(type);
		dto.setInteractWorldCommentId(interactWorldCommentId);
		likeFollowRecordMapper.insertLikeFollowRecord(dto);
	}

	@Override
	public void batchDeleteLikeFollowRecord(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		likeFollowRecordMapper.batchDeleteLikeFollowRecord(ids);
	}

	@Override
	public void batchUpdateLikeFollowRecord(Integer complete, Integer[] ids)
			throws Exception {
		likeFollowRecordMapper.batchUpdateLikeFollowRecord(complete, ids);
	}

	@Override
	public void queryLikeFollowRecord(Integer id, Integer zombieId,
			Integer worldId, Integer userId, Integer type, Integer complete,Integer interactWorldCommentId,
			Integer maxId, int page, int rows, Map<String, Object> jsonMap)
			throws Exception {
		InteractLikeFollowRecord dto = new InteractLikeFollowRecord();
		dto.setId(id);
		dto.setZombieId(zombieId);
		dto.setWorldId(worldId);
		dto.setUserId(userId);
		dto.setComplete(complete);
		dto.setType(type);
		dto.setFirstRow(rows*(page-1));
		dto.setMaxId(maxId);
		dto.setLimit(rows);
		dto.setInteractWorldCommentId(interactWorldCommentId);
		
		List<InteractLikeFollowRecord> list = null;
		Integer reMaxId						= 0;
		long total							= 0;
		
		total = likeFollowRecordMapper.queryLikeFollowRecordCount(dto);
		
		if( total > 0){
			list = likeFollowRecordMapper.queryLikeFollowRecord(dto);
			if(list != null && list.size() > 0){
				reMaxId = list.get(0).getId();
			}
		}
		
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, reMaxId);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
	}
	
	/**
	 * 查询未完成的互粉互赞
	 * @param type 类型，用以区分互粉还是互赞。0表示互赞，1表示互粉
	 * @return
	 */
	@Override
	public List<InteractLikeFollowRecord> queryUnCompleteLikeFollowRecordByType(Integer type)throws Exception{
		return likeFollowRecordMapper.queryUnCompleteLikeFollowRecordByType(type);
	}
	
	
	@Override
	public void doLikeFollowJob()throws Exception{
		Date now = new Date();
		log.info("doLikeFollowJob begin. "+now);
		
		//互赞
		try{
			List<InteractWorldLiked> userLikeList = new ArrayList<InteractWorldLiked>();
			List<InteractLikeFollowRecord> likeRecordList = queryUnCompleteLikeFollowRecordByType(0);
			
			if(likeRecordList != null && likeRecordList.size() > 0 ){
				Integer[] ids = new Integer[likeRecordList.size()];
				
				//生成计划
				for(int i=0; i < likeRecordList.size(); i++){
					InteractLikeFollowRecord likeDto = likeRecordList.get(i);
					ids[i] = likeDto.getId();
					Integer interactId = worldService.queryIntegerIdByWorldId(likeDto.getWorldId());
					if ( interactId != null ) {
						List<Date> dateList = worldService.getScheduleV3(now, 120, 1);
						userLikeList.add(new InteractWorldLiked(interactId, likeDto.getWorldId(),likeDto.getZombieId(),now,dateList.get(0),Tag.TRUE, Tag.FALSE));
					}
				}
				
				if (userLikeList.size() > 0 ) {
					worldService.batchSaveLiked(userLikeList);
				}
				
				//更新完成标记
				likeFollowRecordMapper.batchUpdateLikeFollowRecord(Tag.TRUE, ids);
			}
		}catch(Exception e){
			log.warn("doLikeFollowJob : like. "+e.getMessage());
		}
		
		//互粉
		try{
			List<InteractUserFollow> userFollowList = new ArrayList<InteractUserFollow>();
			List<InteractLikeFollowRecord> followRecordList = queryUnCompleteLikeFollowRecordByType(1);
			
			if(followRecordList != null && followRecordList.size() > 0 ){
				Integer[] ids = new Integer[followRecordList.size()];
				
				//生成计划
				for(int i=0; i < followRecordList.size(); i++){
					InteractLikeFollowRecord followDto = followRecordList.get(i);
					ids[i] = followDto.getId();
					Integer interactId = worldService.queryIntegerIdByWorldId(followDto.getWorldId());
					if ( interactId != null ) {
						List<Date> dateList = worldService.getScheduleV3(now, 120, 1);
						userFollowList.add(new InteractUserFollow(interactId, followDto.getUserId(), followDto.getZombieId(),
								now, dateList.get(0), Tag.TRUE, Tag.FALSE));
					}
				}
				
				if (userFollowList.size() > 0 ) {
					worldService.batchSaveFollow(userFollowList);
				}
				
				//更新完成标记
				likeFollowRecordMapper.batchUpdateLikeFollowRecord(Tag.TRUE, ids);
			}
		}catch(Exception e){
			log.warn("doLikeFollowJob : follow. "+e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param userId
	 * @param worldId
	 * @param type 0赞。
	 */
	@Override
	public void addLikeFollowInteract(Integer userId,Integer worldId,Integer type){
		Date now = new Date();
		
		//查询对应的标签
		List<InteractLikeFollowCommentLabel> labelList = null;
		try{
			labelList = likeFollowCommentLabelService.queryLikeFollowCommentLabelByType(type);
		}catch(Exception e){
			log.warn("queryLikeFollowCommentLabelByType failed!\ncase:"+e.getMessage());
			return ;
		}
		
		
		if (labelList != null && labelList.size() > 0){
			//查询标签对应的评论
			List<Integer> commentList = null;
			try{
				commentList = commentService.getRandomCommentIds(labelList.get(0).getLabelId(), 1);
			}catch(Exception e){
				log.warn("getRandomCommentIds failed.\ncase:"+e.getMessage());
				return;
			}
			if(commentList != null && commentList.size() > 0){
				
				//查询马甲id
				List<Integer> zombieList = null;
				try{
					zombieList = likeFollowZombieService.queryNRandomNotCommentNotFollowZombieId(userId, worldId,1);
				}catch(Exception e){
					log.warn("queryNRandomNotCommentNotFollowZombieId failed.\ncase:"+e.getMessage());
					return;
				}
				
				if(zombieList != null && zombieList.size() > 0){
					
					//生成评论
					try{
						List<Date> dateList = worldService.getScheduleV3(now, 120, 1);
						Integer interactId = worldService.queryIntegerIdByWorldId(worldId);
						if(interactId == null){
							interactId = keyGenService.generateId(Admin.KEYGEN_INTERACT_WORLD_ID);
							worldService.saveInteract(interactId, worldId, 0, 1, 0, 60, now);
						}
						InteractWorldCommentDto dto = new InteractWorldCommentDto();
						dto.setInteractId(interactId);
						dto.setWorldId(worldId);
						dto.setUserId(zombieList.get(0));
						dto.setCommentId(commentList.get(0));
						dto.setDateAdded(now);
						dto.setDateSchedule(dateList.get(0));
						dto.setValid(Tag.TRUE);
						dto.setFinished(Tag.FALSE);
						worldService.insertWorldComment(dto);
						//生成互动记录
						insertLikeFollowRecord(zombieList.get(0),worldId,userId,type,Tag.FALSE,dto.getId());
					}catch(Exception e){
						log.warn("userId:"+userId+".worldId:"+worldId+"type:"+type+"labelList"+labelList+"zombieList"+zombieList+e.getMessage());
						return;
					}
				}
			}else {
				log.warn("commentList is null."+"userId:"+userId+".worldId:"+worldId+"type:"+type+"labelList"+labelList);
				return;
			}
		}else{
			log.warn("labelList is null."+"userId:"+userId+".worldId:"+worldId+"type:"+type);
			return;
		}
		
	}

}

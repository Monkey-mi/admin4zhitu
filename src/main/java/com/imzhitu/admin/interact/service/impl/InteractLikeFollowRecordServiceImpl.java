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
import com.imzhitu.admin.common.pojo.InteractLikeFollowRecord;
import com.imzhitu.admin.interact.mapper.InteractLikeFollowRecordMapper;
import com.imzhitu.admin.interact.service.InteractLikeFollowRecordService;
import com.imzhitu.admin.interact.service.InteractWorldService;

@Service
public class InteractLikeFollowRecordServiceImpl extends BaseServiceImpl implements InteractLikeFollowRecordService{

	@Autowired
	private InteractLikeFollowRecordMapper likeFollowRecordMapper;
	
	@Autowired
	private InteractWorldService worldService;
	
	private Logger log = Logger.getLogger(InteractLikeFollowRecordServiceImpl.class);
	
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
		// TODO Auto-generated method stub
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		likeFollowRecordMapper.batchDeleteLikeFollowRecord(ids);
	}

	@Override
	public void batchUpdateLikeFollowRecord(Integer complete, Integer[] ids)
			throws Exception {
		// TODO Auto-generated method stub
		likeFollowRecordMapper.batchUpdateLikeFollowRecord(complete, ids);
	}

	@Override
	public void queryLikeFollowRecord(Integer id, Integer zombieId,
			Integer worldId, Integer userId, Integer type, Integer complete,Integer interactWorldCommentId,
			Integer maxId, int page, int rows, Map<String, Object> jsonMap)
			throws Exception {
		// TODO Auto-generated method stub
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
			List<InteractLikeFollowRecord> likeRecordList = queryUnCompleteLikeFollowRecordByType(0);
			List<Integer> zombieIdList = new ArrayList<Integer>();
			
			if(likeRecordList != null && likeRecordList.size() > 0 ){
				Integer[] ids = new Integer[likeRecordList.size()];
				
				//生成计划
				for(int i=0; i < likeRecordList.size(); i++){
					InteractLikeFollowRecord likeDto = likeRecordList.get(i);
					ids[i] = likeDto.getId();
					Integer interactId = worldService.queryIntegerIdByWorldId(likeDto.getWorldId());
					if ( interactId != null ) {
						zombieIdList.clear();
						zombieIdList.add(likeDto.getZombieId());
						List<Date> dateList = worldService.getScheduleV3(now, 120, 1);
						worldService.batchSaveLiked(interactId, likeDto.getWorldId(), zombieIdList, now, dateList);
					}
				}
				
				//更新完成标记
				likeFollowRecordMapper.batchUpdateLikeFollowRecord(Tag.TRUE, ids);
			}
		}catch(Exception e){
			log.warn("doLikeFollowJob : like. "+e.getMessage());
		}
		
		//互粉
		try{
			List<InteractLikeFollowRecord> followRecordList = queryUnCompleteLikeFollowRecordByType(1);
			List<Integer> zombieIdList = new ArrayList<Integer>();
			
			if(followRecordList != null && followRecordList.size() > 0 ){
				Integer[] ids = new Integer[followRecordList.size()];
				
				//生成计划
				for(int i=0; i < followRecordList.size(); i++){
					InteractLikeFollowRecord followDto = followRecordList.get(i);
					ids[i] = followDto.getId();
					Integer interactId = worldService.queryIntegerIdByWorldId(followDto.getWorldId());
					if ( interactId != null ) {
						zombieIdList.clear();
						zombieIdList.add(followDto.getZombieId());
						List<Date> dateList = worldService.getScheduleV3(now, 120, 1);
						worldService.batchSaveLiked(interactId, followDto.getWorldId(), zombieIdList, now, dateList);
					}
				}
				
				//更新完成标记
				likeFollowRecordMapper.batchUpdateLikeFollowRecord(Tag.TRUE, ids);
			}
		}catch(Exception e){
			log.warn("doLikeFollowJob : follow. "+e.getMessage());
		}
	}

}

package com.imzhitu.admin.interact.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.InteractLikeFollowRecord;
import com.imzhitu.admin.interact.mapper.InteractLikeFollowRecordMapper;
import com.imzhitu.admin.interact.service.InteractLikeFollowRecordService;

public class InteractLikeFollowRecordServiceImpl extends BaseServiceImpl implements InteractLikeFollowRecordService{

	@Autowired
	private InteractLikeFollowRecordMapper likeFollowRecordMapper;
	
	@Override
	public void insertLikeFollowRecord(Integer zombieId, Integer worldId,
			Integer userId, Integer type, Integer complete) throws Exception {
		// TODO Auto-generated method stub
		InteractLikeFollowRecord dto = new InteractLikeFollowRecord();
		dto.setZombieId(zombieId);
		dto.setWorldId(worldId);
		dto.setUserId(userId);
		dto.setComplete(complete);
		dto.setType(type);
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
			Integer worldId, Integer userId, Integer type, Integer complete,
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

}

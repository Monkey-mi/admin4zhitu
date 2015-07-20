package com.imzhitu.admin.interact.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.InteractChannelLevel;
import com.imzhitu.admin.interact.mapper.InteractChannelLevelMapper;
import com.imzhitu.admin.interact.service.InteractChannelLevelService;

@Service
public class InteractChannelLevelServiceImpl extends BaseServiceImpl implements InteractChannelLevelService{

	@Autowired
	private InteractChannelLevelMapper channelLevelMapper;
	
	@Override
	public void insertChannelLevel(Integer channelId,
			Integer unSuperMinCommentCount, Integer unSuperMaxCommentCount,
			Integer superMinCommentCount, Integer superMaxCommentCount,Integer minutetime)
			throws Exception {
		// TODO Auto-generated method stub
		InteractChannelLevel dto = new InteractChannelLevel();
		dto.setChannelId(channelId);
		dto.setUnSuperMinCommentCount(unSuperMinCommentCount);
		dto.setUnSuperMmaxCommentCount(unSuperMaxCommentCount);
		dto.setSuperMinCommentCount(superMinCommentCount);
		dto.setSuperMaxCommentCount(superMaxCommentCount);
		dto.setMinuteTime(minutetime);
		channelLevelMapper.insertChannelLevel(dto);
	}

	@Override
	public void batchDeleteChannelLevel(String idsStr) throws Exception {
		// TODO Auto-generated method stub
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		channelLevelMapper.batchDeleteChannelLevel(ids);
	}

	@Override
	public void updateChannelLevel(Integer id,Integer unSuperMinCommentCount,
			Integer unSuperMaxCommentCount, Integer superMinCommentCount,
			Integer superMaxCommentCount,Integer minutetime) throws Exception {
		// TODO Auto-generated method stub
		InteractChannelLevel dto = new InteractChannelLevel();
		dto.setId(id);
		dto.setUnSuperMinCommentCount(unSuperMinCommentCount);
		dto.setUnSuperMmaxCommentCount(unSuperMaxCommentCount);
		dto.setSuperMinCommentCount(superMinCommentCount);
		dto.setSuperMaxCommentCount(superMaxCommentCount);
		dto.setMinuteTime(minutetime);
		channelLevelMapper.updateChannelLevel(dto);
	}

	@Override
	public List<InteractChannelLevel> queryChannelLevel(Integer channelId,
			Integer id) throws Exception {
		// TODO Auto-generated method stub
		InteractChannelLevel dto = new InteractChannelLevel();
		dto.setId(id);
		dto.setChannelId(channelId);
		return channelLevelMapper.queryChannelLevel(dto);
	}

	@Override
	public void queryChannelLevel(Integer id, Integer channelId, Integer maxId,
			int page, int rows, Map<String, Object> jsonMap) throws Exception {
		// TODO Auto-generated method stub
		InteractChannelLevel dto = new InteractChannelLevel();
		dto.setId(id);
		dto.setChannelId(channelId);
		dto.setMaxId(maxId);
		dto.setFirstRow(rows * (page -1));
		dto.setLimit(rows);
		
		List<InteractChannelLevel> list = null;
		Integer reMaxId					= 0;
		long total						= 0;
		
		total = channelLevelMapper.queryChannelLevelTotalCount(dto);
		
		if(total > 0){
			list = channelLevelMapper.queryChannelLevel(dto);
			if(list != null && list.size()>0){
				reMaxId = list.get(0).getId();
			}
		}
		
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, reMaxId);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
	}

}

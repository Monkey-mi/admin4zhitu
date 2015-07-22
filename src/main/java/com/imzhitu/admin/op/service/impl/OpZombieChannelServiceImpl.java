package com.imzhitu.admin.op.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.OpZombieChannel;
import com.imzhitu.admin.op.mapper.OpZombieChannelMapper;
import com.imzhitu.admin.op.service.OpZombieChannelService;

@Service
public class OpZombieChannelServiceImpl extends BaseServiceImpl implements OpZombieChannelService{
	
	@Autowired
	private OpZombieChannelMapper zombieChannelMapper;

	@Override
	public void insertZombieChannel(Integer userId, Integer channelId)
			throws Exception {
		// TODO Auto-generated method stub
		OpZombieChannel dto = new OpZombieChannel();
		dto.setChannelId(channelId);
		dto.setUserId(userId);
		zombieChannelMapper.insertZombieChannel(dto);
	}

	@Override
	public void batchDeleteZombieChannel(String idsStr) throws Exception {
		// TODO Auto-generated method stub
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		zombieChannelMapper.batchDeleteZombieChannel(ids);
	}

	@Override
	public void queryZombieChannel(Integer id, Integer userId,
			Integer channelId, Integer maxId, int page, int rows,
			Map<String, Object> jsonMap) throws Exception {
		// TODO Auto-generated method stub
		OpZombieChannel dto = new OpZombieChannel();
		dto.setChannelId(channelId);
		dto.setUserId(userId);
		dto.setId(id);
		dto.setMaxId(maxId);
		dto.setFirstRow(rows*(page-1));
		dto.setLimit(rows);
		long total 			= 0;
		Integer reMaxId 	= 0;
		List<OpZombieChannel> list = null;
		
		total = zombieChannelMapper.queryZombieChannelTotalCount(dto);
		if(total > 0){
			list = zombieChannelMapper.queryZombieChannel(dto);
			if(list  != null && list.size() > 0 ){
				reMaxId = list.get(0).getId();
			}
		}
		
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID,reMaxId);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
	}

	@Override
	public long queryZombieChannelTotalCount(Integer id, Integer userId,
			Integer channelId, Integer maxId) throws Exception {
		// TODO Auto-generated method stub
		OpZombieChannel dto = new OpZombieChannel();
		dto.setChannelId(channelId);
		dto.setUserId(userId);
		dto.setId(id);
		dto.setMaxId(maxId);
		return zombieChannelMapper.queryZombieChannelTotalCount(dto);
	}

	@Override
	public List<Integer> queryNotInteractNRandomNotFollowZombie(Integer userId,
			Integer channelId, Integer worldId, Integer limit) throws Exception {
		// TODO Auto-generated method stub
		return zombieChannelMapper.queryNotInteractNRandomNotFollowZombie(userId, channelId, worldId, limit);
	}

}

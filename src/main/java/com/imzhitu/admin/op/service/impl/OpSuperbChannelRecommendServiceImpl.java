package com.imzhitu.admin.op.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.OpChannel;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.hts.web.operations.dao.ChannelCacheDao;
import com.hts.web.operations.dao.ChannelDao;
import com.hts.web.operations.dao.ChannelThemeCacheDao;
import com.imzhitu.admin.common.pojo.OpSuperbChannelRecommend;
import com.imzhitu.admin.op.mapper.OpSuperbChannelRecommendMapper;
import com.imzhitu.admin.op.service.OpSuperbChannelRecommendService;

@Service
public class OpSuperbChannelRecommendServiceImpl extends BaseServiceImpl implements OpSuperbChannelRecommendService{
	
	@Autowired
	private OpSuperbChannelRecommendMapper superbChannelRecommendMapper;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private ChannelCacheDao channelCacheDao;
	
	@Autowired
	private ChannelThemeCacheDao channelThemeCacheDao;

	@Override
	public void insertSuperbChannelRecommend(Integer channelId, Integer valid,
			Integer operator)throws Exception {
		// TODO Auto-generated method stub
		OpSuperbChannelRecommend dto = new OpSuperbChannelRecommend();
		dto.setChannelId(channelId);
		long totalCount = superbChannelRecommendMapper.querySuperbChannelRecommendCount(dto);
		if(totalCount != 0)
			return;
		dto.setValid(valid);
		dto.setOperatorId(operator);
		Date now = new Date();
		dto.setAddDate(now);
		dto.setModifyDate(now);
		superbChannelRecommendMapper.insertSuperbChannelRecommend(dto);
	}

	@Override
	public void batchDeleteSuperbChannelRecommend(String idsStr) throws Exception{
		// TODO Auto-generated method stub
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		if(ids != null && ids.length > 0)
			superbChannelRecommendMapper.batchDeleteSuperbChannelRecommend(ids);
	}

	@Override
	public void batchUpdateSuperbChannelRecommendValid(Integer valid,String idsStr) throws Exception{
		// TODO Auto-generated method stub
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		if(ids != null && ids.length > 0)
			superbChannelRecommendMapper.batchUpdateSuperbChannelRecommendValid(valid,ids);
	}

	@Override
	public void qeurySuperbChannelRecommend(Integer id, Integer channelId,
			Integer valid, Integer maxId, int page, int rows,
			Map<String, Object> jsonMap) throws Exception{
		// TODO Auto-generated method stub
		OpSuperbChannelRecommend dto = new OpSuperbChannelRecommend();
		dto.setChannelId(channelId);
		dto.setValid(valid);
		dto.setMaxId(maxId);
		dto.setId(id);
		dto.setFirstRow((page-1)*rows);
		dto.setLimit(rows);
		List<OpSuperbChannelRecommend> list = null;
		Integer reMaxId = 0;
		long totalCount = superbChannelRecommendMapper.querySuperbChannelRecommendCount(dto);
		if(totalCount > 0){
			list = superbChannelRecommendMapper.qeurySuperbChannelRecommend(dto);
		}
		if(list != null && list.size() > 0){
			reMaxId = list.get(0).getId();
		}
		
		jsonMap.put(OptResult.JSON_KEY_TOTAL, list.size());
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, reMaxId);
	}

	@Override
	public long querySuperbChannelRecommendCount(Integer id,Integer channelId,
			Integer valid,Integer maxId) throws Exception{
		// TODO Auto-generated method stub
		OpSuperbChannelRecommend dto = new OpSuperbChannelRecommend();
		dto.setChannelId(channelId);
		dto.setValid(valid);
		dto.setMaxId(maxId);
		dto.setId(id);
		return superbChannelRecommendMapper.querySuperbChannelRecommendCount(dto);
	}
	
	
	@Override
	public void updateSuperbChannelRecommendCache()throws Exception{
		//查询所有精选频道
		List<OpChannel> list = channelDao.querySuperbChannel(2000);
		if(list == null || list.size() == 0){
			throw new Exception("没有精选频道");
		}
		
		//查询所有精选频道推荐
		OpSuperbChannelRecommend dto = new OpSuperbChannelRecommend();
		dto.setValid(Tag.TRUE);
		List<OpSuperbChannelRecommend> rList = superbChannelRecommendMapper.qeurySuperbChannelRecommend(dto);
		int rListSize = 0;
		if(rList != null){
			rListSize = rList.size();
		}
		
		List<OpChannel> topList = new ArrayList<OpChannel>();
		List<OpChannel> superbList = new ArrayList<OpChannel>();
		boolean flag = false;
		for(int i=0;i<rListSize;i++){
			for(int j=0;j<rList.size();j++){
				if(list.get(i).getId() == rList.get(j).getChannelId()){
					flag = true;
					break;
				}
			}
			if(true == flag){
				topList.add(list.get(i));
				flag = false;
			}else{
				superbList.add(list.get(i));
			}
		}
		channelCacheDao.updateChannel(topList, superbList);
		channelThemeCacheDao.updateTheme();
	}

}

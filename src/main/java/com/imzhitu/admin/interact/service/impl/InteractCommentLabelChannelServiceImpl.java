package com.imzhitu.admin.interact.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.InteractCommentLabelChannel;
import com.imzhitu.admin.interact.mapper.InteractCommentLabelChannelMapper;
import com.imzhitu.admin.interact.service.InteractCommentLabelChannelService;

@Service
public class InteractCommentLabelChannelServiceImpl extends BaseServiceImpl implements InteractCommentLabelChannelService{
	
	@Autowired
	private InteractCommentLabelChannelMapper commentLabelChannelMapper;

	@Override
	public void insertCommentLabelChannel(Integer channelId,
			Integer commentLabelId, Integer operator) throws Exception {
		// TODO Auto-generated method stub
		InteractCommentLabelChannel dto = new InteractCommentLabelChannel();
		dto.setChannelId(channelId);
		dto.setCommentLabelId(commentLabelId);
		dto.setOperator(operator);
		dto.setAddDate(new Date());
		
		commentLabelChannelMapper.insertCommentLabelChannel(dto);
	}

	@Override
	public void batchDeleteCommentLabelChannel(String idsStr) throws Exception {
		// TODO Auto-generated method stub
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		commentLabelChannelMapper.batchDeleteCommentLabelChannel(ids);
	}

	@Override
	public void queryCommentLabelChannel(Integer id, Integer channelId,
			Integer commentLabelId, Integer maxId, int page, int rows,
			Map<String, Object> jsonMap) throws Exception {
		// TODO Auto-generated method stub
		InteractCommentLabelChannel dto = new InteractCommentLabelChannel();
		dto.setChannelId(channelId);
		dto.setCommentLabelId(commentLabelId);
		dto.setId(id);
		dto.setMaxId(maxId);
		dto.setFirstRow(rows * (page-1));
		dto.setLimit(rows);
		
		Integer reMaxId = 0;
		long totalCount = 0;
		List<InteractCommentLabelChannel> list = null;
		
		totalCount = commentLabelChannelMapper.queryComemntLabelChannelTotalCount(dto);
		if(totalCount > 0){
			list = commentLabelChannelMapper.queryCommentLabelChannel(dto);
			if(null != list && list.size() > 0){
				reMaxId = list.get(0).getId();
			}
		}
		
		jsonMap.put(OptResult.JSON_KEY_TOTAL, totalCount);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, reMaxId);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
	}

	@Override
	public long queryComemntLabelChannelTotalCount(Integer id,
			Integer channelId, Integer commentLabelId,Integer maxId) throws Exception {
		// TODO Auto-generated method stub
		InteractCommentLabelChannel dto = new InteractCommentLabelChannel();
		dto.setChannelId(channelId);
		dto.setCommentLabelId(commentLabelId);
		dto.setId(id);
		dto.setMaxId(maxId);
		return commentLabelChannelMapper.queryComemntLabelChannelTotalCount(dto);
	}

	@Override
	public List<InteractCommentLabelChannel> queryCommentLabelByChannelId(Integer channelId)
			throws Exception {
		// TODO Auto-generated method stub
		
		return commentLabelChannelMapper.queryCommentLabelByChannelId(channelId);
	}

	@Override
	public List<InteractCommentLabelChannel> queryCommentLabelNameByLabelIds(
			String label_ids) throws Exception {
		// TODO Auto-generated method stub
		Integer[]ids = StringUtil.convertStringToIds(label_ids);
		return commentLabelChannelMapper.queryCommentLabelNameByLabelIds(ids);
	}
	


}

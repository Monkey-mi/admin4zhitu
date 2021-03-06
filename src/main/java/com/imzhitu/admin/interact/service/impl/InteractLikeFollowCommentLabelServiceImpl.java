package com.imzhitu.admin.interact.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.InteractLikeFollowCommentLabel;
import com.imzhitu.admin.interact.mapper.InteractLikeFollowCommentLabelMapper;
import com.imzhitu.admin.interact.service.InteractLikeFollowCommentLabelService;

@Service
public class InteractLikeFollowCommentLabelServiceImpl extends BaseServiceImpl implements InteractLikeFollowCommentLabelService{
	
	@Autowired
	private InteractLikeFollowCommentLabelMapper likeFollowCommentLabelMapper;

	@Override
	public void insertLikeFollowCommentLabel(Integer labelId,Integer type) throws Exception {
		// TODO Auto-generated method stub
		InteractLikeFollowCommentLabel dto = new InteractLikeFollowCommentLabel();
		dto.setLabelId(labelId);
		dto.setType(type);
		likeFollowCommentLabelMapper.insertLikeFollowCommentLabel(dto);
	}

	@Override
	public void batchDeleteLikeFollowCommentLabel(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		likeFollowCommentLabelMapper.batchDeleteLikeFollowCommentLabel(ids);
	}

	@Override
	public List<InteractLikeFollowCommentLabel> queryCommentLabelNameByLabelIds(String labelIdsStr) throws Exception {
		Integer[] labelIds = StringUtil.convertStringToIds(labelIdsStr);
		return likeFollowCommentLabelMapper.queryCommentLabelNameByLabelIds(labelIds);
	}
	
	@Override
	public void queryLikeFollowCommentLabel(Integer id, Integer labelId,Integer type, Integer maxId, int page, int rows,
			Map<String, Object> jsonMap) throws Exception {
		InteractLikeFollowCommentLabel dto = new InteractLikeFollowCommentLabel();
		dto.setId(id);
		dto.setMaxId(maxId);
		dto.setFirstRow(rows*(page-1));
		dto.setLimit(rows);
		dto.setLabelId(labelId);
		dto.setType(type);
		
		List<InteractLikeFollowCommentLabel>  list = null;
		Integer reMaxId = 0;
		long total = likeFollowCommentLabelMapper.queryLikeFollowCommentLabelTotalCount(dto);
		
		if ( total > 0 ) {
			list = likeFollowCommentLabelMapper.queryLikeFollowCommentLabel(dto);
			if ( list != null){
				reMaxId = list.get(0).getId();
			}
		}
		
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, reMaxId);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
	}

	@Override
	public List<InteractLikeFollowCommentLabel> queryLikeFollowCommentLabelByType(
			Integer type) throws Exception {
		return likeFollowCommentLabelMapper.queryLikeFollowCommentLabelByType(type);
	}

}

package com.imzhitu.admin.op.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.OpStarRecommendDto;
import com.imzhitu.admin.op.mapper.OpStarRecommendMapper;
import com.imzhitu.admin.op.service.OpStarRecommendService;

@Service
public class OpStarRecommendServiceImpl  extends BaseServiceImpl implements OpStarRecommendService{

	@Autowired
	private OpStarRecommendMapper starRecommendMapper;
	@Override
	public void insertStarRecommend(Integer userId, Integer top, Integer valid)
			throws Exception {
		// TODO Auto-generated method stub
		OpStarRecommendDto dto = new OpStarRecommendDto();
		dto.setUserId(userId);
		long total = starRecommendMapper.queryStarRecommendTotalCount(dto);
		if(total != 0)
			return;
		dto.setTop(top);
		dto.setValid(valid);
		starRecommendMapper.insertStarRecommend(dto);
	}

	@Override
	public void deleteStarRecommend(Integer id, Integer userId, Integer top,
			Integer valid) throws Exception {
		// TODO Auto-generated method stub
		if(id == null && userId == null)
			return;
		OpStarRecommendDto dto = new OpStarRecommendDto();
		dto.setId(id);
		dto.setUserId(userId);
		dto.setTop(top);
		dto.setValid(valid);
		starRecommendMapper.deleteStarRecommend(dto);
	}

	@Override
	public void updateStarRecommend(Integer id, Integer userId, Integer top,
			Integer valid) throws Exception {
		// TODO Auto-generated method stub
		if(id == null && userId == null)
			return;
		OpStarRecommendDto dto = new OpStarRecommendDto();
		dto.setId(id);
		dto.setUserId(userId);
		dto.setTop(top);
		dto.setValid(valid);
		starRecommendMapper.updateStarRecommend(dto);
	}

	@Override
	public void queryStarRecommend(int maxId,int page, int rows, Map<String, Object> jsonMap,
			Integer id, Integer userId, Integer top, Integer valid)
			throws Exception {
		// TODO Auto-generated method stub
		OpStarRecommendDto dto = new OpStarRecommendDto();
		dto.setId(id);
		dto.setUserId(userId);
		dto.setTop(top);
		dto.setValid(valid);
		dto.setMaxId(maxId);
		dto.setFirstRow((page-1)*rows);
		dto.setLimit(rows);
		long totalCount = starRecommendMapper.queryStarRecommendTotalCount(dto);
		List<OpStarRecommendDto> list = new ArrayList<OpStarRecommendDto>();
		
		if(totalCount > 0){
			//若不是查询置顶的
			if(top == null){
				OpStarRecommendDto tempDto = new OpStarRecommendDto();
				tempDto.setId(id);
				tempDto.setUserId(userId);
				tempDto.setTop(Tag.TRUE);
				tempDto.setValid(valid);
				tempDto.setMaxId(maxId);
				long topTotalCount = starRecommendMapper.queryStarRecommendTotalCount(tempDto);
				
				if(topTotalCount > (page-1)*rows){//置顶的够(page-1)*rows个,结果集里面的置顶+非置顶的
					List<OpStarRecommendDto> ol = starRecommendMapper.queryStarRecommend(tempDto);
					if(ol.size() > 0){
						list.addAll(ol);
					}
					if(rows - ol.size() > 0){//若是该页置顶的不够rows个，剩下的由非置顶的补充
						dto.setFirstRow(0);
						dto.setLimit(rows - ol.size());
						dto.setTop(Tag.FALSE);
						List<OpStarRecommendDto> l = starRecommendMapper.queryStarRecommend(dto);
						if(l.size()>0){
							list.addAll(l);
						}
					}
				}else{//置顶的不过够(page-1)*rows个,结果集里面的必然是非置顶的
					dto.setFirstRow(0);
					int limit = rows - (int)(topTotalCount%rows);
					dto.setLimit(limit);
					dto.setTop(Tag.FALSE);
					List<OpStarRecommendDto> l = starRecommendMapper.queryStarRecommend(dto);
					if(l.size()>0){
						list.addAll(l);
					}
				}
			}else{
				List<OpStarRecommendDto> li = starRecommendMapper.queryStarRecommend(dto);
				if(li.size()>0){
					list.addAll(li);
				}
			}
			
			int reMaxId = 0;
			for(int i=0; i< list.size(); i++){
				reMaxId = reMaxId < list.get(i).getId() ? list.get(i).getId() : reMaxId;
			}
			
			jsonMap.put(OptResult.JSON_KEY_TOTAL, totalCount);
			jsonMap.put(OptResult.JSON_KEY_ROWS, list);
			jsonMap.put(OptResult.JSON_KEY_MAX_ID, reMaxId);
			
		}
	}

	@Override
	public long queryStarRecommendTotalCount(int maxId, Integer id,
			Integer userId, Integer top, Integer valid) throws Exception {
		// TODO Auto-generated method stub
		OpStarRecommendDto dto = new OpStarRecommendDto();
		dto.setId(id);
		dto.setUserId(userId);
		dto.setTop(top);
		dto.setValid(valid);
		return starRecommendMapper.queryStarRecommendTotalCount(dto);
	}

}

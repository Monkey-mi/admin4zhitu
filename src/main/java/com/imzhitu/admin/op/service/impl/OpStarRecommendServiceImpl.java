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
import com.imzhitu.admin.op.service.OpStarRecommendCacheService;
import com.imzhitu.admin.op.service.OpStarRecommendService;

@Service
public class OpStarRecommendServiceImpl  extends BaseServiceImpl implements OpStarRecommendService{

	@Autowired
	private OpStarRecommendMapper starRecommendMapper;
	
	@Autowired
	private com.hts.web.userinfo.service.UserInfoService webUserInfoService;
	
	@Autowired
	private OpStarRecommendCacheService opStarRecommendCacheService;
	
	@Override
	public void insertStarRecommend(Integer userId, Integer top, Integer valid)
			throws Exception {
		OpStarRecommendDto dto = new OpStarRecommendDto();
		dto.setUserId(userId);
		long total = starRecommendMapper.queryStarRecommendTotalCount(dto);
		if(total != 0)
			return;
		dto.setTop(top);
		dto.setValid(valid);
		dto.setActivity(0);
		starRecommendMapper.insertStarRecommend(dto);
	}

	@Override
	public void deleteStarRecommend(Integer id, Integer userId, Integer top,
			Integer valid) throws Exception {
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
			Integer valid,Integer activity) throws Exception {
		if(id == null && userId == null)
			return;
		OpStarRecommendDto dto = new OpStarRecommendDto();
		dto.setId(id);
		dto.setUserId(userId);
		dto.setTop(top);
		dto.setValid(valid);
		dto.setActivity(activity);
		starRecommendMapper.updateStarRecommend(dto);
	}

	@Override
	public void queryStarRecommend(int maxId,int page, int rows, Map<String, Object> jsonMap,
			Integer id, Integer userId, Integer top, Integer valid,Integer orderBy)
			throws Exception {
		OpStarRecommendDto dto = new OpStarRecommendDto();
		dto.setId(id);
		dto.setUserId(userId);
		dto.setTop(top);
		dto.setValid(valid);
		dto.setMaxId(maxId);
		dto.setFirstRow((page-1)*rows);
		dto.setLimit(rows);
		dto.setOrderBy(orderBy);
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
					int  firstRow = (int)((page-1)*rows - topTotalCount );
					dto.setFirstRow(firstRow);
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
			
			webUserInfoService.extractVerify(list);
			
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
		OpStarRecommendDto dto = new OpStarRecommendDto();
		dto.setId(id);
		dto.setUserId(userId);
		dto.setTop(top);
		dto.setValid(valid);
		return starRecommendMapper.queryStarRecommendTotalCount(dto);
	}
	
	
	/**
	 * 从缓存中读取达人推荐数据。这些数据经过加工之后，在后台显示
	 * @param jsonMap
	 * @throws Exception
	 */
	@Override
	public void queryStarRecommendFromCache(Map<String,Object>jsonMap)throws Exception{
		//从缓存中读取达人推荐。但是这些信息在后台显示中是偏少的，需要再组装一些数据。
		List<com.hts.web.common.pojo.OpStarRecommendDto> htsStarRecommendList = opStarRecommendCacheService.queryStarRecommendCache();
		
		//遍历从缓存中读取的数据，为其添加数据
		if(htsStarRecommendList != null && htsStarRecommendList.size() > 0 ){
			OpStarRecommendDto tempDto = new OpStarRecommendDto();
			List<OpStarRecommendDto> list = new ArrayList<OpStarRecommendDto>(htsStarRecommendList.size());
			for(com.hts.web.common.pojo.OpStarRecommendDto htsStarRecommend : htsStarRecommendList){
				tempDto.setUserId(htsStarRecommend.getId());
				List<OpStarRecommendDto> ol = starRecommendMapper.queryStarRecommend(tempDto);
				if(ol != null && ol.size() == 1){
					OpStarRecommendDto dto = ol.get(0);
					dto.setVerifyIcon(htsStarRecommend.getVerifyIcon());
					dto.setVerifyName(htsStarRecommend.getVerifyName());
					list.add(dto);
				}else{
					throw new Exception("query starRecommend by userId failed. it is null or size > 1 which excepted 1. ");
				}
			}
			
			jsonMap.put(OptResult.JSON_KEY_TOTAL, list.size());
			jsonMap.put(OptResult.JSON_KEY_ROWS, list);
			jsonMap.put(OptResult.JSON_KEY_MAX_ID, 0);
		}
		
	}
	
	/**
	 * 更新达人推荐的缓存
	 * @throws Exception
	 */
	@Override
	public void updateStarRecommendCache()throws Exception{
		opStarRecommendCacheService.updateStarRecommendCache();
	}
	

}

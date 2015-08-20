package com.imzhitu.admin.op.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.OpZombieDegreeUserLevel;
import com.imzhitu.admin.op.mapper.OpZombieDegreeUserLevelMapper;
import com.imzhitu.admin.op.service.OpZombieDegreeUserLevelService;

@Service
public class OpZombieDegreeUserLevelServiceImpl extends BaseServiceImpl implements OpZombieDegreeUserLevelService{
	
	@Autowired
	private OpZombieDegreeUserLevelMapper zombieDegreeUserLevelMapper;

	@Override
	public void insertZombieDegreeUserLevel(Integer zombieDegreeId,
			Integer userLevelId) throws Exception {
		OpZombieDegreeUserLevel dto = new OpZombieDegreeUserLevel();
		dto.setZombieDegreeId(zombieDegreeId);
		dto.setUserLevelId(userLevelId);
		zombieDegreeUserLevelMapper.insertZombieDegreeUserLevel(dto);
	}

	@Override
	public void batchDeleteZombieDegreeUserLevel(String idsStr)
			throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		zombieDegreeUserLevelMapper.batchDeleteZombieDegreeUserLevel(ids);
	}

	@Override
	public void queryZombieDegreeUserLevel(Integer id, Integer zombieDegreeId,
			Integer userLevelId, Integer maxId, int page, int rows,
			Map<String, Object> jsonMap) throws Exception {
		OpZombieDegreeUserLevel dto = new OpZombieDegreeUserLevel();
		dto.setZombieDegreeId(zombieDegreeId);
		dto.setUserLevelId(userLevelId);
		dto.setId(id);
		dto.setMaxId(maxId);
		dto.setFirstRow(rows*(page-1));
		dto.setLimit(rows);
		
		long total 							= 0;
		Integer reMaxId						= 0;
		List<OpZombieDegreeUserLevel> list = null;
		
		total = zombieDegreeUserLevelMapper.queryZombieDegreeUserLevelTotalCount(dto);
		if(total > 0 ){
			list = zombieDegreeUserLevelMapper.queryZombieDegreeUserLevel(dto);
			if(list != null && list.size() > 0){
				reMaxId = list.get(0).getId();
			}
		}
		
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID,reMaxId);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
	}

	@Override
	public long queryZombieDegreeUserLevelTotalCount(Integer id,
			Integer zombieDegreeId, Integer userLevelId, Integer maxId)
			throws Exception {
		OpZombieDegreeUserLevel dto = new OpZombieDegreeUserLevel();
		dto.setZombieDegreeId(zombieDegreeId);
		dto.setUserLevelId(userLevelId);
		dto.setId(id);
		dto.setMaxId(maxId);
		return zombieDegreeUserLevelMapper.queryZombieDegreeUserLevelTotalCount(dto);
	}
	
	@Override
	public List<OpZombieDegreeUserLevel> queryZombieDegree(Integer id, Integer zombieDegreeId,
			Integer userLevelId)throws Exception{
		OpZombieDegreeUserLevel dto = new OpZombieDegreeUserLevel();
		dto.setZombieDegreeId(zombieDegreeId);
		dto.setUserLevelId(userLevelId);
		dto.setId(id);
		return zombieDegreeUserLevelMapper.queryZombieDegreeUserLevel(dto);
	}

}

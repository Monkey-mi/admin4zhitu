package com.imzhitu.admin.op.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.OpZombieDegree;
import com.imzhitu.admin.op.mapper.OpZombieDegreeMapper;
import com.imzhitu.admin.op.service.OpZombieDegreeService;

@Service
public class OpZombieDegreeServiceImpl extends BaseServiceImpl implements OpZombieDegreeService{

	@Autowired
	private OpZombieDegreeMapper zombieDegreeMapper;
	
	@Override
	public void insertZombieDegree(String degreeName, Integer weight)
			throws Exception {
		OpZombieDegree dto = new OpZombieDegree();
		dto.setDegreeName(degreeName);
		dto.setWeight(weight);
		zombieDegreeMapper.insertZombieDegree(dto);
	}

	@Override
	public void batchDeleteZombieDegree(String idsStr) {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		zombieDegreeMapper.batchDeleteZombieDegree(ids);
	}

	@Override
	public void updateZombieDegree(Integer id, String degreeName, Integer weight)
			throws Exception {
		OpZombieDegree dto = new OpZombieDegree();
		dto.setId(id);
		dto.setDegreeName(degreeName);
		dto.setWeight(weight);
		zombieDegreeMapper.updateZombieDegree(dto);
	}

	@Override
	public void queryZombieDegree(Integer id, Integer weight, Integer maxId,
			int page, int rows, Map<String, Object> jsonMap) throws Exception {
		OpZombieDegree dto = new OpZombieDegree();
		dto.setId(id);
		dto.setWeight(weight);
		dto.setMaxId(maxId);
		dto.setFirstRow(rows*(page-1));
		dto.setLimit(rows);
		
		long total = 0;
		Integer reMaxId = 0;
		List<OpZombieDegree> list = null;
		
		total = zombieDegreeMapper.queryZombieDegreeTotalCount(dto);
		if(total > 0){
			list = zombieDegreeMapper.queryZombieDegree(dto);
			if(list != null && list.size()>0){
				reMaxId = list.get(0).getId();
			}
		}
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID,reMaxId);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
	}

	@Override
	public long queryZombieDegreeTotalCount(Integer id, Integer weight,
			Integer maxId) throws Exception {
		OpZombieDegree dto = new OpZombieDegree();
		dto.setId(id);
		dto.setWeight(weight);
		dto.setMaxId(maxId);
		return zombieDegreeMapper.queryZombieDegreeTotalCount(dto);
	}

	@Override
	public List<OpZombieDegree> queryAllZombieDegree() throws Exception {
		return zombieDegreeMapper.queryAllZombieDegree();
	}

}

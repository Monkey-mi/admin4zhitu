package com.imzhitu.admin.op.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.OpZombie;
import com.imzhitu.admin.op.mapper.OpZombieMapper;
import com.imzhitu.admin.op.service.OpZombieService;

import net.sf.json.JSONArray;

@Service
public class OpZombieServiceImpl extends BaseServiceImpl implements OpZombieService{
	
	@Autowired
	private OpZombieMapper zombieMapper;

	@Override
	public void insertZombie(Integer userId, Integer degreeId,
			Integer commentCount, Integer concernCount) throws Exception {
		// TODO Auto-generated method stub
		OpZombie dto = new OpZombie();
		Date now = new Date();
		dto.setUserId(userId);
		dto.setDegreeId(degreeId);
		if(commentCount == null){
			commentCount = 0;
		}
		if(concernCount == null){
			concernCount = 0;
		}
		dto.setCommentCount(commentCount);
		dto.setConcernCount(concernCount);
		dto.setLastModify(now.getTime());
		zombieMapper.insertZombie(dto);
	}

	@Override
	public void batchDeleteZombie(String idsStr) throws Exception {
		// TODO Auto-generated method stub
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		zombieMapper.batchDeleteZombie(ids);
	}

	@Override
	public void batchUpdateZombie(Integer commentCount, Integer concernCount,
			Integer[] ids) throws Exception {
		// TODO Auto-generated method stub
		Date now = new Date(); 
		zombieMapper.batchUpdateZombie(now.getTime(), concernCount, commentCount, ids);
	}

	@Override
	public void queryZombie(Integer userId, Integer degreeId, Integer maxId,
			int page, int rows, Map<String, Object> jsonMap) throws Exception {
		// TODO Auto-generated method stub
		OpZombie dto = new OpZombie();
		dto.setUserId(userId);
		dto.setDegreeId(degreeId);
		dto.setMaxId(maxId);
		dto.setFirstRow(rows*(page-1));
		dto.setLimit(rows);
		long total = 0;
		Integer reMaxId = 0;
		List<OpZombie> list = null;
		total = zombieMapper.queryZombieTotalCount(dto);
		if(total > 0){
			list = zombieMapper.queryZombie(dto);
			if(list != null && list.size() > 0){
				reMaxId = list.get(0).getId();
			}
		}
		
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID,reMaxId);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
	}

	@Override
	public long queryZombieTotalCount(Integer userId, Integer degreeId,
			Integer maxId) throws Exception {
		// TODO Auto-generated method stub
		OpZombie dto = new OpZombie();
		dto.setUserId(userId);
		dto.setDegreeId(degreeId);
		dto.setMaxId(maxId);
		return zombieMapper.queryZombieTotalCount(dto);
	}

	@Override
	public List<OpZombie> queryNZombie(Integer degreeId, Integer n)
			throws Exception {
		// TODO Auto-generated method stub
		return zombieMapper.queryZombieByLastModifyASC(degreeId, n);
	}

	@Override
	public void updateSexAndSignature(String zombieInfoJSON) {
		JSONArray jsna = JSONArray.fromObject(zombieInfoJSON);
		for (int i = 0; i < jsna.size(); i++) {
			net.sf.json.JSONObject jsno = jsna.getJSONObject(i);
			Integer userId = jsno.getInt("userId");
			Integer sex = jsno.getInt("sex");
			String signature = jsno.getString("signature");
			zombieMapper.updateSexAndSignature(userId,sex,signature);
		}
	}


}

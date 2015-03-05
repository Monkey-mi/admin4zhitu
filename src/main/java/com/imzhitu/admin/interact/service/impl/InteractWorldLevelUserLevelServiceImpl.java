package com.imzhitu.admin.interact.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.InteractWorldLevelUserLevel;
import com.imzhitu.admin.interact.mapper.InteractWorldLevelUserLevelMapper;
import com.imzhitu.admin.interact.service.InteractWorldLevelUserLevelService;

@Service
public class InteractWorldLevelUserLevelServiceImpl extends BaseServiceImpl implements InteractWorldLevelUserLevelService{
	@Autowired
	InteractWorldLevelUserLevelMapper interactWorldLevelUserLevelMapper;
	
	@Override
	public void queryWorldLevelUserLevel(int maxId,int start,int limit,Map<String ,Object> jsonMap)throws Exception{
		InteractWorldLevelUserLevel dto = new InteractWorldLevelUserLevel();
		if(maxId > 0)dto.setMaxId(maxId);
		buildNumberDtos(dto,start,limit,jsonMap,new NumberDtoListAdapter<InteractWorldLevelUserLevel>(){
			@Override
			public long queryTotal(InteractWorldLevelUserLevel d){
				return interactWorldLevelUserLevelMapper.queryWorldLevelUserLevelTotal(d);
			}
			
			@Override
			public List< ? extends AbstractNumberDto> queryList(InteractWorldLevelUserLevel d){
				return interactWorldLevelUserLevelMapper.queryWorldLevelUserLevel(d);
			}
		});
	}
	
	@Override
	public InteractWorldLevelUserLevel queryWorldLevelUserLevelByUid(Integer uid)throws Exception{
		try{
			InteractWorldLevelUserLevel dto = new InteractWorldLevelUserLevel();
			dto.setUserId(uid);
			return interactWorldLevelUserLevelMapper.queryWorldLevelUserLevelByUid(dto);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public void addWorldLevelUserLevel(Integer worldLevelId,Integer userLevelId,Integer operatorId)throws Exception {
		InteractWorldLevelUserLevel dto = new InteractWorldLevelUserLevel();
		dto.setUserLevelId(userLevelId);
		dto.setWorldLevelId(worldLevelId);
		dto.setOperatorId(operatorId);
		Date now = new Date();
		dto.setAddDate(now);
		dto.setModifyDate(now);
		dto.setValid(Tag.TRUE);
		interactWorldLevelUserLevelMapper.addWorldLevelUserLevel(dto);
	}
	
	@Override
	public void delWorldLevelUserLevel(String idsStr)throws Exception{
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		for(Integer id:ids){
			interactWorldLevelUserLevelMapper.delWorldLevelUserLevelById(id);
		}
	}
	
}

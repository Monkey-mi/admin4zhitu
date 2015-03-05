package com.imzhitu.admin.interact.service.impl;

import java.text.SimpleDateFormat;
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
import com.imzhitu.admin.common.pojo.InteractPlanCommentLabel;
import com.imzhitu.admin.interact.mapper.InteractPlanCommentLabelMapper;
import com.imzhitu.admin.interact.service.InteractPlanCommentLabelService;

@Service
public class InteractPlanCommentLabelServiceImpl extends BaseServiceImpl implements InteractPlanCommentLabelService{
	@Autowired
	private InteractPlanCommentLabelMapper interactPlanCommentLabelMapper;
	
	@Override
	public void addPlanCommentLabel(Integer groupId,String description,Date startTime,Date deadline,String workStartTime,String workEndTime,Integer operatorId,Integer valid)throws Exception{
		if(null==groupId)throw new Exception("组id不能为空");
		if(description==null || description.trim().equals(""))throw new Exception("描述不能为空");
		if(null == startTime || null == deadline || startTime.after(deadline))throw new Exception("有效开始日期和结束日期参数出错");
		if(null == workStartTime || null== workEndTime )throw new Exception("工作时间出错");
		Date now = new Date();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		InteractPlanCommentLabel dto = new InteractPlanCommentLabel();
		dto.setAddDate(now);
		dto.setModifyDate(now);
		dto.setDescription(description);
		dto.setDeadline(deadline);
		dto.setWorkStartTime(df.parse(workStartTime));
		dto.setWorkEndTime(df.parse(workEndTime));
		dto.setValid(valid);
		dto.setOperatorId(operatorId);
		dto.setGroupId(groupId);
		dto.setStartTime(startTime);
		interactPlanCommentLabelMapper.addPlanCommentLabel(dto);
	}
	
	@Override
	public void queryPlanCommentLabelForTable(Integer  maxId, Integer page, Integer rows,Map<String , Object> jsonMap)throws Exception{
		InteractPlanCommentLabel dto = new InteractPlanCommentLabel();
		if(maxId != null && maxId != 0){
			dto.setMaxId(maxId);
		}
		buildNumberDtos(dto,page,rows,jsonMap,new NumberDtoListAdapter<InteractPlanCommentLabel>(){
			@Override
			public long queryTotal(InteractPlanCommentLabel d){
				return interactPlanCommentLabelMapper.queryInteractPlanCommentLabelCount(d);
			}
			
			@Override
			public List< ? extends AbstractNumberDto> queryList(InteractPlanCommentLabel d){
				return interactPlanCommentLabelMapper.queryInteractPlanCommentLabel(d);
			}
		});
	}
	
	@Override
	public void delPlanCommentLabelByIds(String idsStr,Integer operatorId)throws Exception{
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		Date now = new Date();
		for(Integer id: ids){
			InteractPlanCommentLabel dto = new InteractPlanCommentLabel();
			dto.setValid(Tag.FALSE);
			dto.setOperatorId(operatorId);
			dto.setModifyDate(now);
			dto.setId(id);
			interactPlanCommentLabelMapper.updatePlanCommentValidById(dto);
		}
	}
	
	@Override
	public List<InteractPlanCommentLabel> queryInteractPlanCommentLabel()throws Exception{
		try{
			return interactPlanCommentLabelMapper.queryInteractPlanCommentLabelList();
		}catch(EmptyResultDataAccessException e ){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<InteractPlanCommentLabel> queryInteractPlanCommentLabelByDateAndTime(Date date,Date time)throws Exception{
		try{
			InteractPlanCommentLabel dto = new InteractPlanCommentLabel();
			dto.setStartTime(time);
			dto.setDeadline(date);
			return interactPlanCommentLabelMapper.queryInteractPlanCommentLabelByDateAndTime(dto);
		}catch(EmptyResultDataAccessException e ){
			e.printStackTrace();
			return null;
		}
	}
}

package com.imzhitu.admin.interact.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.InteractAutoResponseSchedula;
import com.imzhitu.admin.interact.mapper.InteractAutoResponseMapper;
import com.imzhitu.admin.interact.mapper.InteractAutoResponseSchedulaMapper;
import com.imzhitu.admin.interact.service.InteractAutoResponseSchedulaService;
import com.imzhitu.admin.interact.service.InteractAutoResponseService;

public class InteractAutoResponseSchedulaServiceImpl extends BaseServiceImpl implements InteractAutoResponseSchedulaService{
	
	@Autowired
	private InteractAutoResponseSchedulaMapper autoResponseSchedulaMapper;
	
	@Autowired
	private InteractAutoResponseMapper autoResponseMapper;
	
	@Autowired
	private InteractAutoResponseService autoResponseService;
	
	private Logger log = Logger.getLogger(InteractAutoResponseSchedulaServiceImpl.class);
	
	/**
	 * 扫描为完成的
	 */
	private long timeSpan = 2*60*60*1000;
	
	public void queryAutoResponseSchedulaForTable(Date addDate,Date modifyDate,Date schedula,Integer autoResponseId,Integer valid,Integer complete,Integer page,Integer rows,Map<String,Object>jsonMap)throws Exception{
		InteractAutoResponseSchedula dto = new InteractAutoResponseSchedula();
		dto.setAddDate(addDate);
		dto.setModifyDate(modifyDate);
		dto.setSchedula(schedula);
		dto.setValid(valid);
		dto.setComplete(complete);
		dto.setAutoResponseId(autoResponseId);
		buildNumberDtos(dto,page,rows,jsonMap,new NumberDtoListAdapter<InteractAutoResponseSchedula>(){
			@Override
			public long queryTotal(InteractAutoResponseSchedula dto){
				return autoResponseSchedulaMapper.queryAutoResponseSchedulaCount(dto);
			}
			
			@Override
			public List<? extends AbstractNumberDto>queryList(InteractAutoResponseSchedula dto){
				return autoResponseSchedulaMapper.queryAutoResponseSchedula(dto);
			}
		});
	}
	
	
	/**
	 * 增加自动评论计划
	 * @param autoResponseId
	 * @param valid
	 * @param complete
	 * @param operatorId
	 * @throws Exception
	 */
	@Override
	public void addAutoResponseSchedula(Integer autoResponseId,Integer valid,Integer complete,Date schedula,Integer operatorId)throws Exception{
		InteractAutoResponseSchedula dto = new InteractAutoResponseSchedula();
		Date now = new Date();
		dto.setAutoResponseId(autoResponseId);
		dto.setAddDate(now);
		dto.setModifyDate(now);
		dto.setComplete(complete);
		dto.setValid(valid);
		dto.setOperatorId(operatorId);
		dto.setSchedula(getRandomDate());
		autoResponseSchedulaMapper.addAutoResponseSchedula(dto);
	}
	
	/**
	 * 批量删除自动评论计划
	 * @param idsStr
	 * @throws Exception
	 */
	@Override
	public void delInteractAutoResponseSchedula(String idsStr)throws Exception{
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		autoResponseSchedulaMapper.delInteractAutoResponseSchedula(ids);
	}
	
	/**
	 * 跟新自动评论计划
	 * @param id
	 * @param valid
	 * @param complete
	 * @param operatorId
	 * @throws Exception
	 */
	@Override
	public void updateAutoResponseSchedula(Integer id,Integer valid,Integer complete,Integer operatorId )throws Exception{
		InteractAutoResponseSchedula dto = new InteractAutoResponseSchedula();
		Date now = new Date();
		dto.setId(id);
		dto.setModifyDate(now);
		dto.setComplete(complete);
		dto.setValid(valid);
		dto.setOperatorId(operatorId);
		autoResponseSchedulaMapper.updateAutoResponseSchedula(dto);
	}
	
	/**
	 * 查询未完成的计划
	 * @param complete
	 * @param valid
	 * @param schedulaBegin
	 * @param schedulaEnd
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<InteractAutoResponseSchedula> queryUnCompleteSchedula(Integer complete,Integer valid,Date schedulaBegin,Date schedulaEnd)throws Exception{
		InteractAutoResponseSchedula dto = new InteractAutoResponseSchedula();
		dto.setAddDate(schedulaBegin);
		dto.setModifyDate(schedulaEnd);
		dto.setComplete(complete);
		dto.setValid(valid);
		return autoResponseSchedulaMapper.queryUnCompleteSchedula(dto);
	}
	
	/**
	 * job
	 */
	@Override
	public void doAutoResponseSchedulaJob(){
		Date now = new Date();
		int successCount = 0;
		log.info("自会回复计划开始。" + now);
		List<InteractAutoResponseSchedula> list = null;
		try{ 
			list = queryUnCompleteSchedula(0,1,new Date(now.getTime() - timeSpan),now);
		}catch(Exception e){
			log.info("自动回复计划失败。queryUnCompleteSchedula出错。");
			return ;
		}
		Integer[] ids = new Integer[1];
		if(list != null && list.size() > 0){
			for(InteractAutoResponseSchedula obj : list ){
				//更新hts.htworld_comment 和 hts_admin.interact_auto_response
				autoResponseService.updateResponseCompleteById(obj.getAutoResponseId());
				
				//更新hts_admin.interact_auto_repsonse_schedula
				ids[0] = obj.getId();
				autoResponseSchedulaMapper.updateAutoResponseSchedulaComplete(ids);
				successCount++;
			}
		}
		Date end = new Date();
		log.info("自动回复计划结束。成功："+successCount + ". 花费时间："+(end.getTime()-now.getTime()) + "ms.");
	}
	
	/**
	 * 随机一个未来1.5小时内的数据
	 * @return
	 */
	private Date getRandomDate(){
		Date now = new Date();
		double randomNumber = Math.random();
		long t = (long)(randomNumber*90*60*1000);
		return new Date(now.getTime() + t);
	}
	
	
	
	
	
	
}

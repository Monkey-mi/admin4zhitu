package com.imzhitu.admin.interact.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.imzhitu.admin.common.pojo.InteractAutoResponseSchedula;

public interface InteractAutoResponseSchedulaService {
	/**
	 * 分页查询
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryAutoResponseSchedulaForTable(Date addDate,Date modifyDate,Date schedula,Integer id,Integer valid,Integer complete,Integer page,Integer rows,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 增加自动评论计划
	 * @param autoResponseId
	 * @param valid
	 * @param complete
	 * @param operatorId
	 * @throws Exception
	 */
	public void addAutoResponseSchedula(Integer autoResponseId,Integer valid,Integer complete,Date schedula,Integer operatorId)throws Exception;
	
	/**
	 * 批量删除自动评论计划
	 * @param idsStr
	 * @throws Exception
	 */
	public void delInteractAutoResponseSchedula(String idsStr)throws Exception;
	
	/**
	 * 跟新自动评论计划
	 * @param id
	 * @param valid
	 * @param complete
	 * @param operatorId
	 * @throws Exception
	 */
	public void updateAutoResponseSchedula(Integer id,Integer valid,Integer complete,Integer operatorId )throws Exception;
	
	/**
	 * job,扫描2小时内的未完成的计划，并实施
	 */
	public void doAutoResponseSchedulaJob();
	
	/**
	 * 查询2小时内未完成的计划
	 * @param complete
	 * @param valid
	 * @param schedulaBegin
	 * @param schedulaEnd
	 * @return
	 * @throws Exception
	 */
	public List<InteractAutoResponseSchedula> queryUnCompleteSchedula(Integer complete,Integer valid,Date schedulaBegin,Date schedulaEnd)throws Exception;
}

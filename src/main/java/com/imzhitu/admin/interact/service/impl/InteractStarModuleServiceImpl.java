package com.imzhitu.admin.interact.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.StarModule;
import com.imzhitu.admin.interact.mapper.InteractStarModuleMapper;
import com.imzhitu.admin.interact.service.InteractStarModuleService;

@Service
public class  InteractStarModuleServiceImpl extends BaseServiceImpl implements InteractStarModuleService {

	@Autowired
	private InteractStarModuleMapper mapper;

	/**
	 * 
	 * @param title1  小标题
	 * @param title2 小副标题	 
	 * @param userId  用户ID
	 * @param pics  图片名
	 * @param Intro 图片介绍
	 * @throws Exception 
		*	2015年9月21日
		*	mishengliang
	 */
	@Override
	public void add(String title1,String title2,Integer userId,String pics,String intro,Integer topicId)  throws Exception{
		StarModule dto  = new StarModule();
		dto.setTitle1(title1);
		dto.setTitle2(title2);
		dto.setUserId(userId);
		dto.setPics(pics);
		dto.setIntro(intro);
		dto.setTopicId(topicId);
		mapper.addStarModule(dto);
	}

	@Override
	public List<StarModule> get(Integer topicId) throws Exception {
		List<StarModule> list  =  mapper.getStarModule(topicId);
		return list;
	}

	
	@Override
	public void update(Integer id,String title1,String title2,Integer userId,String pics,String intro)  throws Exception{
		StarModule dto  = new StarModule();
		dto.setId(id);
		dto.setTitle1(title1);
		dto.setTitle2(title2);
		dto.setUserId(userId);
		dto.setPics(pics);
		dto.setIntro(intro);
		mapper.updateStarModule(dto);
	}
	
	@Override
	public void destory(Integer id)  throws Exception{
		mapper.destory(id);
	}
}
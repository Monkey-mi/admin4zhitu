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
	 * @param title  小标题
	 * @param subtitle 小副标题	 
	 * @param userId  用户ID
	 * @param pics  图片名
	 * @param Intro 图片介绍
	 * @throws Exception 
		*	2015年9月21日
		*	mishengliang
	 */
	@Override
	public void add(String title,String subtitle,Integer userId,String pics,String pic02,String pic03,String pic04,String intro,Integer topicId)  throws Exception{
		intro = intro.replaceAll("   ", "</br>");
		StarModule dto  = new StarModule();
		dto.setTitle(title);
		dto.setSubtitle(subtitle);
		dto.setUserId(userId);
		dto.setPics(pics);
		dto.setPic02(pic02);
		dto.setPic03(pic03);
		dto.setPic04(pic04);
		dto.setIntro(intro);
		dto.setTopicId(topicId);
		mapper.addStarModule(dto);
	}

	@Override
	public  void get(Integer page,Integer rows,Integer maxId,Integer topicId,Map<String, Object> jsonMap) throws Exception {
		Integer start = (page - 1) * rows;
		Integer limites = rows; 
		Integer total  = mapper.getStarModuleCount(topicId);
		List<StarModule> list  =  mapper.getStarModule(start,limites,maxId,topicId);
		if(page <= 1){
			 maxId = list.get(0).getId();
		}
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxId);
		
	}

	
	@Override
	public void update(Integer id,String title,String subtitle,Integer userId,String pics,String pic02,String pic03,String pic04,String intro)  throws Exception{
		intro = intro.replaceAll("   ", "</br>");
		StarModule dto  = new StarModule();
		dto.setId(id);
		dto.setTitle(title);
		dto.setSubtitle(subtitle);
		dto.setUserId(userId);
		dto.setPics(pics);
		dto.setPic02(pic02);
		dto.setPic03(pic03);
		dto.setPic04(pic04);
		dto.setIntro(intro);
		mapper.updateStarModule(dto);
	}
	
	@Override
	public void destory(Integer id)  throws Exception{
		mapper.destory(id);
	}
	
	/**
	 * 利用传输过来id顺序进行排序
	 * @param ids
	 * @throws Exception 
		*	2015年11月13日
		*	mishengliang
	 */
	@Override
	public void reOrderIndex(String[] ids){
		for(int i = 0; i < ids.length; i++){
			if(ids[i] !=null && ids[i] != ""){
				int id = Integer.parseInt(ids[i]);
				mapper.reOrderIndex(id,i+1);
			}
		}
	}
}

package com.imzhitu.admin.interact.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.StarModule;
import com.imzhitu.admin.interact.mapper.InteractStarModuleMapper;
import com.imzhitu.admin.interact.mapper.InteractStarWorldModuleMapper;
import com.imzhitu.admin.interact.service.InteractStarWorldModuleService;

@Service
public class  InteractStarWorldModuleServiceImpl extends BaseServiceImpl implements InteractStarWorldModuleService {

	@Autowired
	private InteractStarWorldModuleMapper mapper;
	
	@Autowired
	private InteractStarModuleMapper moduleMapper;

	@Override
	public void addWorldModule(String title,String subtitle,Integer userId,Integer worldId,String intro,Integer topicId)  throws Exception{
		intro = intro.replaceAll("   ", "</br>");
		StarModule dto  = new StarModule();
		dto.setTitle(title);
		dto.setSubtitle(subtitle);
		dto.setUserId(userId);
		dto.setWorldId(worldId);
		dto.setIntro(intro);
		dto.setTopicId(topicId);
		mapper.addStarWorldModule(dto);
	}

	@Override
	public  void getWorldModule(Integer page,Integer rows,Integer maxId,Integer topicId,Map<String, Object> jsonMap) throws Exception {
		Integer start = (page - 1) * rows;
		Integer limites = rows; 
		Integer total  = mapper.getStarWorldModuleCount(topicId);
		List<StarModule> list  =  mapper.getStarWorldModule(start,limites,maxId,topicId);
		if(page <= 1){
			 maxId = list.get(0).getId();
		}
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxId);
	}

	
	@Override
	public void updateWorldModule(Integer id,String title,String subtitle,Integer userId,Integer worldId,String intro)  throws Exception{
		intro = intro.replaceAll("   ", "</br>");
		StarModule dto  = new StarModule();
		dto.setId(id);
		dto.setTitle(title);
		dto.setSubtitle(subtitle);
		dto.setUserId(userId);
		dto.setWorldId(worldId);
		dto.setIntro(intro);
		mapper.updateStarWorldModule(dto);
	}
	
	@Override
	public void destroyWorldModule(String ids)  throws Exception{
		String[] idsArr = ids.split(",");
		for(int i = 0; i < idsArr.length; i++){
			mapper.destroyStarWorldModule(Integer.parseInt(idsArr[i]));
		}
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
			if(ids[i] != null && ids[i] != ""){
				int id = Integer.parseInt(ids[i]);
				mapper.reOrderIndex(id,i+1);
			}
		}
	}
	
}

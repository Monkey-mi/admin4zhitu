package com.imzhitu.admin.interact.service.impl;

import java.util.List;
import java.util.Map;

import javax.management.loading.PrivateClassLoader;

import org.apache.log4j.lf5.viewer.TrackingAdjustmentListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.StarRecommendTopic;
import com.imzhitu.admin.interact.mapper.InteractStarModuleMapper;
import com.imzhitu.admin.interact.mapper.InteractStarRocommendTopicMapper;
import com.imzhitu.admin.interact.mapper.InteractStarWorldModuleMapper;
import com.imzhitu.admin.interact.service.InteractStarRecommendTopicService;

@Service
public class  InteractStarRecommendTopicServiceImpl extends BaseServiceImpl implements InteractStarRecommendTopicService {

	@Autowired
	private InteractStarRocommendTopicMapper mapper;
	
	@Autowired
	private InteractStarWorldModuleMapper worldModuleMapper;
	
	@Autowired
	private InteractStarModuleMapper moduleMapper;

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
	public void addTopic(String backgroundColor,Integer topicType,Integer isWorld,String shareBanner,String bannerPic,String title,String introduceHead,String introduceFoot,String stickerButton,String shareButton)  throws Exception{
		introduceHead = introduceHead.replaceAll("   ", "</br>");
		introduceFoot = introduceFoot.replaceAll("   ", "</br>");
		StarRecommendTopic dto  = new StarRecommendTopic();
		dto.setBackgroundColor(backgroundColor);
		dto.setTopicType(topicType);
		dto.setIsWorld(isWorld);
		dto.setShareBanner(shareBanner);
		dto.setBannerPic(bannerPic);
		dto.setTitle(title);
		dto.setIntroduceHead(introduceHead);
		dto.setIntroduceFoot(introduceFoot);
		dto.setStickerButton(stickerButton);
		dto.setShareButton(shareButton);
		mapper.addStarRecommendTopic(dto);
	}

	/**
	 * isWorld 区别是哪个模块要获取其中的信息  在topic展示中没有数值传入
	 */
	@Override
	public void getTopic(Integer page,Integer rows,Integer maxId,Integer isWorld,Map<String, Object> jsonMap) throws Exception {
		Integer start = (page - 1) * rows;
		Integer limites = rows; 
		Integer total  = mapper.getTopicModuleCount();
		List<StarRecommendTopic> list  =  mapper.getStarRecommendTopic(start,limites,maxId,isWorld);
		String link = "";
		String http = "http://imzhitu.com/operations/";
		for(int i = 0 ;i < list.size() ; i++){
			if (list.get(i).getTopicType() == 1 && list.get(i).getIsWorld() == 0) {
				link = "star/";
			} else if(list.get(i).getTopicType() == 2 && list.get(i).getIsWorld() == 0){
				link = "read/";
			}else if(list.get(i).getTopicType() == 1 && list.get(i).getIsWorld() == 1){
				link = "starWorld/";
			}else if(list.get(i).getTopicType() == 2 && list.get(i).getIsWorld() == 1){
				link = "readWorld/";
			}
			list.get(i).setLink(http + link + list.get(i).getId());
		}
		if(page <= 1){
			 maxId = list.get(0).getId();
		}
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxId);
	}

	public List<Integer> getTopicId()  throws Exception{
		List<Integer> list  =  mapper.getStarRecommendTopicId();
		return list;
	}
	
	@Override
	public void updateTopic(Integer id,Integer topicType,Integer isWorld,String backgroundColor,String shareBanner,String bannerPic,String title,String introduceHead,String introduceFoot,String stickerButton,String shareButton)  throws Exception{
		introduceHead = introduceHead.replaceAll("   ", "</br>");
		introduceFoot = introduceFoot.replaceAll("   ", "</br>");
		StarRecommendTopic dto  = new StarRecommendTopic();
		dto.setId(id);
		dto.setBackgroundColor(backgroundColor);
		dto.setTopicType(topicType);
		dto.setIsWorld(isWorld);
		dto.setShareBanner(shareBanner);
		dto.setBannerPic(bannerPic);
		dto.setTitle(title);
		dto.setIntroduceHead(introduceHead);
		dto.setIntroduceFoot(introduceFoot);
		dto.setStickerButton(stickerButton);
		dto.setShareButton(shareButton);
		mapper.updateStarRecommendTopic(dto);
	}
	
	@Override
	public void destoryTopic(Integer id,Integer isWorld)  throws Exception{
		mapper.destoryStarRecommendTopic(id);
		if (isWorld ==  0) {
			moduleMapper.destoryByTopicId(id);
		} else {
			worldModuleMapper.destoryByTopicId(id);
		}
	}
}

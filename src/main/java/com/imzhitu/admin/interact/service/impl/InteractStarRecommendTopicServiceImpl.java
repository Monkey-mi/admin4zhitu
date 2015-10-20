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
import com.imzhitu.admin.interact.service.InteractStarRecommendTopicService;

@Service
public class  InteractStarRecommendTopicServiceImpl extends BaseServiceImpl implements InteractStarRecommendTopicService {

	@Autowired
	private InteractStarRocommendTopicMapper mapper;
	
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
	public void addTopic(String backgroundColor,Integer topicType,String shareBanner,String bannerPic,String title,String introduceHead,String introduceFoot,String stickerButton,String shareButton)  throws Exception{
		introduceHead = introduceHead.replaceAll("   ", "</br>");
		introduceFoot = introduceFoot.replaceAll("   ", "</br>");
		StarRecommendTopic dto  = new StarRecommendTopic();
		dto.setBackgroundColor(backgroundColor);
		dto.setTopicType(topicType);
		dto.setShareBanner(shareBanner);
		dto.setBannerPic(bannerPic);
		dto.setTitle(title);
		dto.setIntroduceHead(introduceHead);
		dto.setIntroduceFoot(introduceFoot);
		dto.setStickerButton(stickerButton);
		dto.setShareButton(shareButton);
		mapper.addStarRecommendTopic(dto);
	}

	@Override
	public List<StarRecommendTopic> getTopic() throws Exception {
		List<StarRecommendTopic> list  =  mapper.getStarRecommendTopic();
		String link = "";
		String http = "http://imzhitu.com/operations/";
		for(int i = 0 ;i < list.size() ; i++){
			if (list.get(i).getTopicType() == 1) {
				link = "star/";
			} else if(list.get(i).getTopicType() == 2){
				link = "read/";
			}
			list.get(i).setLink(http + link + list.get(i).getId());
		}
		return list;
	}

	public List<Integer> getTopicId()  throws Exception{
		List<Integer> list  =  mapper.getStarRecommendTopicId();
		return list;
	}
	
	@Override
	public void updateTopic(Integer id,Integer topicType,String backgroundColor,String shareBanner,String bannerPic,String title,String introduceHead,String introduceFoot,String stickerButton,String shareButton)  throws Exception{
		introduceHead = introduceHead.replaceAll("   ", "</br>");
		introduceFoot = introduceFoot.replaceAll("   ", "</br>");
		StarRecommendTopic dto  = new StarRecommendTopic();
		dto.setId(id);
		dto.setBackgroundColor(backgroundColor);
		dto.setTopicType(topicType);
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
	public void destoryTopic(Integer id)  throws Exception{
		mapper.destoryStarRecommendTopic(id);
		moduleMapper.destoryByTopicId(id);
	}
}

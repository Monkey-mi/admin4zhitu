package com.imzhitu.admin.op.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.op.service.OpStarRecommendCacheService;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.OpStarRecommendDto;

public class OpStarRecommendCacheServiceImpl implements OpStarRecommendCacheService{
	
	@Autowired
	private com.hts.web.operations.service.OpStarRecommendService htsOpStarRecommendService;
	
	@Autowired
	private com.hts.web.userinfo.service.UserActivityService userActivityService;
	
	private Logger log = Logger.getLogger(OpStarRecommendCacheServiceImpl.class);
	
	/**
	 * 更新缓存
	 */
	@Override
	public void updateStarRecommendCache()throws Exception{
		htsOpStarRecommendService.updateStarRecommendCache();		
	}
	
	/**
	 * 更新缓存计划
	 */
	@Override
	public void doUpdateStarRecommendCacheSchedula(){
		Date now = new Date();
		log.info("doUpdateStarRecommendCacheSchedula begin:"+now);
		try{
			List<OpStarRecommendDto> list = htsOpStarRecommendService.queryStarRecommend();
			for(OpStarRecommendDto dto:list){
				userActivityService.addActivityScore(Tag.ACT_TYPE_CHANGE_SUB, dto.getId());
			}
			updateStarRecommendCache();
		}catch(Exception e){
			log.info("doUpdateStarRecommendCacheSchedula exception:"+e.getMessage());
		}
		Date end = new Date();
		log.info("doUpdateStarRecommendCacheSchedula end. cost:"+(end.getTime()-now.getTime()));
	}
}

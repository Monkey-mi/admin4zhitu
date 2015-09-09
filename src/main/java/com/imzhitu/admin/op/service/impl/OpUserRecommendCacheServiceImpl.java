package com.imzhitu.admin.op.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpUser;
import com.hts.web.common.pojo.OpUserVerifyDto;
import com.hts.web.operations.dao.OpUserVerifyDtoCacheDao;
import com.hts.web.operations.service.UserOperationsService;
import com.imzhitu.admin.op.dao.UserRecommendDao;
import com.imzhitu.admin.op.service.OpUserRecommendCacheService;

public class OpUserRecommendCacheServiceImpl extends BaseCacheDaoImpl<OpUser> implements
		OpUserRecommendCacheService {

	
	@Autowired
	private OpUserVerifyDtoCacheDao opUserVerifyDtoCacheDao;
	
	@Autowired
	private UserRecommendDao userRecommendDao;
	
	@Autowired
	private UserOperationsService userOperationsService;
	
	private Logger log = Logger.getLogger(OpUserRecommendCacheServiceImpl.class);
	
	@Override
	public void updateUserRecommendCache() throws Exception {
		//更新置顶的
		if(getRedisTemplate().hasKey(com.hts.web.base.constant.CacheKeies.OP_USER_VERIFY_REC_TOP)){
			getRedisTemplate().delete(com.hts.web.base.constant.CacheKeies.OP_USER_VERIFY_REC_TOP);
		}
		List<OpUser> userRecommendTopList = userRecommendDao.queryWeightUserRecommend(50);
		int size = 0;
		if( userRecommendTopList != null && userRecommendTopList.size() > 0){
			//补充织图数据
			userOperationsService.extractHTWorldThumbUser(2, userRecommendTopList);
			size = userRecommendTopList.size();
			OpUser[] userInfoArray = new OpUser[size];
			userRecommendTopList.toArray(userInfoArray);
			getRedisTemplate().opsForList().rightPushAll(com.hts.web.base.constant.CacheKeies.OP_USER_VERIFY_REC_TOP,userInfoArray);
		}
		
		//非置顶的
		List<OpUserVerifyDto> userVerifyList = opUserVerifyDtoCacheDao.queryVerify();
		/*
		 * 并不直接查询＂所有分类＂缓存列表，而是取每种分类的前5个，加上明星分类中的前50个汇总成一个列表
		 * allVerifyCollector用于收集
		 */
		List<OpUser> allVerifyCollector = new ArrayList<OpUser>();
		for(OpUserVerifyDto userVerify : userVerifyList){
			updateUserRecommendCache(userVerify.getId(), allVerifyCollector);
		}
		updateAllTypeVerify(allVerifyCollector);
	}

//	@Override
	public void updateUserRecommendCache(Integer verifyId, List<OpUser> allVerifyCollector) throws Exception {
		String key = CacheKeies.OP_USER_VERIFY_REC + verifyId;
		if(getRedisTemplate().hasKey(key)){
			getRedisTemplate().delete(key);
		}
		
		//查询推荐用户
		List<OpUser> userRecommendNotTopList = userRecommendDao.queryNotWeightUserRecommendByVerifyId(verifyId, 50);
		
		//查询织图
		userOperationsService.extractHTWorldThumbUser(2, userRecommendNotTopList);
				
		int size = 0;
		if( userRecommendNotTopList != null && userRecommendNotTopList.size() > 0){
			size = userRecommendNotTopList.size();
			OpUser[] userInfoArray = new OpUser[size];
			getRedisTemplate().opsForList().rightPushAll(key,userRecommendNotTopList.toArray(userInfoArray));
		}
		
		// 用collector收集该分类下的前5个人
		for(int i = 0; i < 5; i++) {
			allVerifyCollector.add(userRecommendNotTopList.get(i));
		}
	}
	
	/**
	 * 更新＂全部＂分类下的达人推荐，此分类用0标记
	 * 
	 * @throws Exception
	 */
	private void updateAllTypeVerify(List<OpUser> allVerifyCollector) throws Exception {
		String key = CacheKeies.OP_USER_VERIFY_REC + 0;
		if(getRedisTemplate().hasKey(key)){
			getRedisTemplate().delete(key);
		}
		
		//　对收集回来的数据排序
		Collections.sort(allVerifyCollector, new Comparator<OpUser>() {

			@Override
			public int compare(OpUser o1, OpUser o2) {
				if(o1.getRecommendId() > o2.getRecommendId()) {
					return 1;
				} else if(o1.getRecommendId() < o2.getRecommendId()){
					return -1;
				}
				return 0;
			}
		});
		
		//查询明星用户
		List<OpUser> starList = userRecommendDao.queryNotWeightUserRecommendByVerifyId(Tag.VERIFY_SUPER_STAR_ID, 50);
		starList.addAll(allVerifyCollector);
		
		if(starList.size() > 0) {
			OpUser[] userInfoArray = new OpUser[starList.size()];
			getRedisTemplate().opsForList().rightPushAll(key,starList.toArray(userInfoArray));
		}
	}
	
	/**
	 * 
	 */
	@Override
	public void doUpdateUserRecommendCacheJob(){
		try{
			Date begin = new Date();
			log.info("begin update user recommend cache."+begin);
			updateUserRecommendCache();
			Date end = new Date();
			log.info("end update user recommend cache. cost:"+(end.getTime() - begin.getTime()) +"ms.");
		}catch(Exception e){
			log.warn(e.getMessage());
		}
	}

}

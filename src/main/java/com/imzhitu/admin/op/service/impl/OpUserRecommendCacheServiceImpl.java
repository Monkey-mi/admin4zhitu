package com.imzhitu.admin.op.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.CacheKeies;
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
		for(OpUserVerifyDto userVerify : userVerifyList ){
			updateUserRecommendCache(userVerify.getId());
		}
	}

	@Override
	public void updateUserRecommendCache(Integer verifyId) throws Exception {
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

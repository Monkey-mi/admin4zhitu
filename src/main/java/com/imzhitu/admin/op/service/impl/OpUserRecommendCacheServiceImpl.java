package com.imzhitu.admin.op.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpUserVerifyDto;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.operations.dao.OpUserVerifyDtoCacheDao;
import com.imzhitu.admin.op.dao.UserRecommendDao;
import com.imzhitu.admin.op.service.OpUserRecommendCacheService;

@Service
public class OpUserRecommendCacheServiceImpl extends BaseCacheDaoImpl<UserInfoDto> implements
		OpUserRecommendCacheService {

	
	@Autowired
	private OpUserVerifyDtoCacheDao opUserVerifyDtoCacheDao;
	
	@Autowired
	private UserRecommendDao userRecommendDao;
	
	@Override
	public void updateUserRecommendCache() throws Exception {
		// TODO Auto-generated method stub
		List<OpUserVerifyDto> userVerifyList = opUserVerifyDtoCacheDao.queryVerify();
		for(OpUserVerifyDto userVerify : userVerifyList ){
			updateUserRecommendCache(userVerify.getId());
		}
	}

	@Override
	public void updateUserRecommendCache(Integer verifyId) throws Exception {
		// TODO Auto-generated method stub
		String key = CacheKeies.OP_USER_VERIFY_REC + verifyId;
		if(getRedisTemplate().hasKey(key)){
			getRedisTemplate().delete(key);
		}
		List<UserInfoDto> list = new ArrayList<UserInfoDto>();
		List<UserInfoDto> userRecommendTopList = userRecommendDao.queryWeightUserRecommend(50);
		List<UserInfoDto> userRecommendNotTopList = userRecommendDao.queryNotWeightUserRecommendByVerifyId(verifyId, 50);
		list.addAll(userRecommendTopList);
		list.addAll(userRecommendNotTopList);
		int size = 0;
		if( userRecommendTopList != null){
			size += userRecommendTopList.size();
		}
		if( userRecommendNotTopList != null){
			size += userRecommendNotTopList.size();
		}
		if( size > 0 ){
			UserInfoDto[] userInfoArray = new UserInfoDto[size];
			getRedisTemplate().opsForList().rightPushAll(key,list.toArray(userInfoArray));
		}else {
			throw new Exception("update userRecommendCache failed! result is null");
		}
		
	}

}

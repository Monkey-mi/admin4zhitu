package com.imzhitu.admin.op.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.pojo.UserVerify;
import com.hts.web.operations.dao.UserVerifyRecCacheDao;
import com.imzhitu.admin.op.service.OpUserRecommendCacheService;
import com.imzhitu.admin.userinfo.dao.UserVerifyCacheDao;

@Service
public class OpUserRecommendCacheServiceImpl implements
		OpUserRecommendCacheService {

	@Autowired
	private UserVerifyRecCacheDao userVerifyReCacheDao;
	
	@Autowired
	private UserVerifyCacheDao userVerifyCacheDao;
	
	@Override
	public void updateUserRecommendCache() throws Exception {
		// TODO Auto-generated method stub
		List<UserVerify> userVerifyList = userVerifyCacheDao.queryAllVerify();
		for(UserVerify userVerify : userVerifyList ){
			userVerifyReCacheDao.update(userVerify.getId());
		}
	}

}

package com.imzhitu.admin.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.dao.KeyGenDao;
import com.imzhitu.admin.common.service.KeyGenService;

@Service
public class KeyGenServiceImpl extends BaseServiceImpl implements KeyGenService {

	private static final String KEY_PREFIX = "admin:keygen:";
	
	@Autowired
//	private KeyGenDao keyGenDao;
	private com.hts.web.common.dao.KeyGenDao keyGenDao;
	
	
	
	@Override
	public Integer generateId(Integer keyId) {
//		Integer[] meta = keyGenDao.queryMaxIdAndStepForUpdate(keyId);
//		int maxId = meta[0];
//		maxId += meta[1];
//		keyGenDao.updateMaxId(keyId, maxId);
//		return maxId;
//		return 
		return keyGenDao.nextId(KEY_PREFIX + keyId);
	}
	
}

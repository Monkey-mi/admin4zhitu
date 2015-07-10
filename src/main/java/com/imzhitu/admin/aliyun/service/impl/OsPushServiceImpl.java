package com.imzhitu.admin.aliyun.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.aliyun.dao.OsUserInfoCacheDao;
import com.hts.web.aliyun.dao.OsUserLoginCacheDao;
import com.imzhitu.admin.aliyun.service.OsPushService;

public class OsPushServiceImpl implements OsPushService {
	
	private static Logger log = Logger.getLogger(OsPushServiceImpl.class);
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private OsUserInfoCacheDao osUserInfoCacheDao;
	
	@Autowired
	private OsUserLoginCacheDao osUserLoginCacheDao;
	
	private Integer optCommitLimit = 200;
	
	@Override
	public void pushUpdate() {
		Date begin = new Date();
		try {
			osUserInfoCacheDao.popOpts(optCommitLimit);
			osUserLoginCacheDao.popOpts(optCommitLimit);
//			Date end = new Date();
//			log.info("push opensearch update, begin date=" + format.format(begin) 
//					+ " , end date=" + format.format(end) + ", cost " 
//					+ (end.getTime() - begin.getTime()) / 1000 + " second");
		} catch(Exception e) {
			log.warn("push opensearch update error, begin date=" 
					+ format.format(begin), e);
		}
	}

}

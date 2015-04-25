package com.imzhitu.admin.plat.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.pojo.PlatConcern;
import com.imzhitu.admin.plat.service.PlatService;

public class PlatServiceImpl implements PlatService {

	private static Logger log = Logger.getLogger(PlatServiceImpl.class);
	
	@Autowired
	private com.hts.web.plat.dao.PlatConcernCacheDao concernCacheDao;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Integer concernCommitLimit = 10;

	@Override
	public void saveBeConcern(String cid, String cname, Integer pid)
			throws Exception {
		concernCacheDao.saveBeConcern(new PlatConcern(null, cid, cname, pid));
	}

	@Override
	public void deleteBeConcern(int index) throws Exception {
		concernCacheDao.deleteBeConcern(index);
	}

	@Override
	public void buildBeConcern(Map<String, Object> jsonMap) throws Exception {
		List<PlatConcern> belist = concernCacheDao.queryAllBeConcern();
		jsonMap.put(OptResult.JSON_KEY_ROWS, belist);
		jsonMap.put(OptResult.JSON_KEY_TOTAL, belist.size());
	}

	@Override
	public void pushConcern() {
		
		Date begin = new Date();
		try {
			concernCacheDao.popConcern(concernCommitLimit);
//			Date end = new Date();
//			log.info("push plat, concern date=" + format.format(begin) 
//					+ " , end date=" + format.format(end) + ", cost " 
//					+ (end.getTime() - begin.getTime()) / 1000 + " second");
		} catch(Exception e) {
			log.warn("push plat concern, begin date=" 
					+ format.format(begin), e);
		}
	}
	
	
}

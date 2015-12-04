package com.imzhitu.admin.op.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.op.service.OpNearService;

@Service
public class OpNearServiceImpl extends BaseServiceImpl implements OpNearService{

	@Override
	public void queryNearLabel(Integer id, Integer cityId, int maxSerial,
			int start, int limit, Map<String, Object> jsonMap) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNearLabel(Integer id, Integer cityId, String labelName,
			Double longitude, Double latitude, String description,
			String bannerUrl, Integer serial) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertNearLabel(Integer id, Integer cityId, String labelName,
			Double longitude, Double latitude, String description,
			String bannerUrl, Integer serial) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void batchDeleteNearLabel(String idsStr) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

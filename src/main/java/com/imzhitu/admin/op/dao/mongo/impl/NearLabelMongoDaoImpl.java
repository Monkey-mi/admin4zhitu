package com.imzhitu.admin.op.dao.mongo.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.common.dao.impl.BaseMongoDaoImpl;
import com.hts.web.common.pojo.OpNearLabelDto;
import com.imzhitu.admin.op.dao.mongo.NearLabelMongoDao;

@Repository
public class NearLabelMongoDaoImpl extends BaseMongoDaoImpl implements NearLabelMongoDao {

	@Override
	public void saveLabel(OpNearLabelDto label) {
		if(label.getLoc() == null || (label.getLoc()[0] == 0 && label.getLoc()[1] == 0))
			throw new IllegalArgumentException("please set loc");
		
	}

	@Override
	public void deleteById(Integer id) {
		
	}
	
	@Override
	public void deleteByIds(Integer[] ids) {
		
	}

	@Override
	public void updateLabel(OpNearLabelDto label) {
		
	}

	@Override
	public void updateSerial(Integer id, Integer serial) {
		
	}



}

package com.imzhitu.admin.op.dao.mongo.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.MongoHTS;
import com.hts.web.common.dao.impl.BaseMongoDaoImpl;
import com.hts.web.common.pojo.OpNearLabelDto;
import com.imzhitu.admin.op.dao.mongo.NearLabelMongoDao;

@Repository
public class NearLabelMongoDaoImpl extends BaseMongoDaoImpl implements NearLabelMongoDao {
	
	private static String coll = MongoHTS.NEAR_LABEL;
	
	@Override
	public void saveLabel(OpNearLabelDto label) {
		if(label.getLoc() == null || (label.getLoc()[0] == 0 && label.getLoc()[1] == 0))
			throw new IllegalArgumentException("please set loc");
		getMongoTemplate().save(label, coll);
	}

	@Override
	public void deleteById(Integer id) {
		getMongoTemplate().remove(new Query(where("_id").is(id)), coll);
	}
	
	@Override
	public void deleteByIds(Integer[] ids) {
		getMongoTemplate().remove(new Query(where("_id").in((Object[])ids)), coll);
	}

	@Override
	public void updateLabel(OpNearLabelDto label) {
		Update update = new Update();
		if(label.getBannerUrl() != null)
			update.set("bannerUrl", label.getBannerUrl());
		
		if(label.getLabelName() != null)
			update.set("labelName", label.getLabelName());
		
		if(label.getLoc() != null)
			update.set("loc", label.getLoc());
		
		if(label.getDescription() != null)
			update.set("description", label.getDescription());
		
		if(label.getSerial() != null)
			update.set("serial", label.getSerial());
		
		getMongoTemplate().updateFirst(new Query(where("_id").is(label.getId())), update, coll);
	}

	@Override
	public void updateSerial(Integer id, Integer serial) {
		Update update = new Update();
		update.set("serial", serial);
		getMongoTemplate().updateFirst(new Query(where("_id").is(id)), update, coll);
	}

}

package com.imzhitu.admin.op.dao.mongo.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.MongoHTS;
import com.hts.web.common.dao.impl.BaseMongoDaoImpl;
import com.hts.web.common.pojo.OpNearWorldDto;
import com.imzhitu.admin.op.dao.mongo.NearWorldMongoDao;

/**
 * 
 * @author zxx 2015-12-9 18:16:12
 *
 */
@Repository("NearWorldMongoDao")
public class NearWorldMongoDaoImpl extends BaseMongoDaoImpl implements NearWorldMongoDao{
	private static String collection = MongoHTS.NEAR_WORLD;
	@Override
	public List<OpNearWorldDto> queryNear(int maxId,int cityId,int start, int limit) {
		Criteria criteria = Criteria.where("cityId").is(cityId).and("id").gt(2971557);
		if(maxId > 0) {
			criteria = criteria.and("recommendId").lte(maxId);
		}
		return getMongoTemplate()
				.find(new Query(criteria)
				.with(new Sort(Direction.DESC, "recommendId"))
				.skip(start)
				.limit(limit),
				OpNearWorldDto.class, collection);
	}

	@Override
	public long queryNearTotalCount(int cityId) {
		Criteria criteria = Criteria.where("cityId").is(cityId).and("id").gt(2971557);
		return getMongoTemplate().count(new Query(criteria), OpNearWorldDto.class,collection);
	}
	
	@Override
	public void saveWorld(OpNearWorldDto world) {
		getMongoTemplate().insert(world, collection);
	}
}

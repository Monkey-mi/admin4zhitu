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
import com.imzhitu.admin.op.dao.mongo.NearWorldLastMongoDao;

@Repository("NearWorldLastMongoDao")
public class NearWorldLastMongoDaoImpl extends BaseMongoDaoImpl implements NearWorldLastMongoDao{

	private static String collection = MongoHTS.NEAR_WORLD_LAST;
	
	@Override
	public List<OpNearWorldDto> queryNear(int maxId, Integer cityId, int start,
			int limit) {
		if (cityId != null){
			Criteria criteria = Criteria.where("cityId").is(cityId);
			if(maxId > 0) {
				criteria = criteria.and("recommendId").lte(maxId);
			}
			return getMongoTemplate()
					.find(new Query(criteria)
					.with(new Sort(Direction.DESC, "recommendId"))
					.skip(start)
					.limit(limit),
					OpNearWorldDto.class, collection);
		} else {
			if(maxId > 0) {
				Criteria criteria = Criteria.where("recommendId").lte(maxId);
				return getMongoTemplate()
						.find(new Query(criteria)
						.with(new Sort(Direction.DESC, "recommendId"))
						.skip(start)
						.limit(limit),
						OpNearWorldDto.class, collection);
			}else{
				return getMongoTemplate()
						.find(new Query()
						.with(new Sort(Direction.DESC, "recommendId"))
						.skip(start)
						.limit(limit),
						OpNearWorldDto.class, collection);
			}
		}
		
	}

	@Override
	public long queryNearTotalCount(Integer cityId) {
		if ( cityId != null ){
			Criteria criteria = Criteria.where("cityId").is(cityId);
			return getMongoTemplate().count(new Query(criteria), OpNearWorldDto.class,collection);
		} else {
			return getMongoTemplate().count(new Query(), OpNearWorldDto.class,collection);
		}
		
	}

	
	@Override
	public void saveNearWorldLast(OpNearWorldDto world) {
		getMongoTemplate().insert(world, collection);
	}

	@Override
	public void deleteNearWorldLast(Integer id) {
		getMongoTemplate().remove(new Query(Criteria.where("_id").is(id)), collection);
	}

	@Override
	public OpNearWorldDto queryNearWorldLastById(Integer id) {
		return getMongoTemplate().findOne(new Query(Criteria.where("_id").is(id)), OpNearWorldDto.class,collection);
	}

}

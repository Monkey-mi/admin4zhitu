package com.imzhitu.admin.op.dao.mongo.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.MongoHTS;
import com.hts.web.common.dao.impl.BaseMongoDaoImpl;
import com.imzhitu.admin.op.dao.mongo.NearBulletinMongoDao;
import com.imzhitu.admin.op.pojo.NearBulletinCityDto;
import com.imzhitu.admin.op.pojo.NearBulletinDto;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

@Repository
public class NearBulletinMongoDaoImpl extends BaseMongoDaoImpl implements NearBulletinMongoDao {

	private static String collection = MongoHTS.NEAR_BULLETIN;
	
	@Override
	public void save(NearBulletinDto bulletin) {
		com.hts.web.operations.pojo.NearBulletin webnb = 
				new com.hts.web.operations.pojo.NearBulletin();
		BeanUtils.copyProperties(bulletin, webnb);
		getMongoTemplate().save(webnb, collection);
	}

	@Override
	public List<NearBulletinDto> queryBulletin(NearBulletinDto bulletin) {
		
		List<NearBulletinDto> resList = new ArrayList<NearBulletinDto>();
		
		Criteria criteria = Criteria.where("cityId").is(bulletin.getCityId());
		
		if(bulletin.getMaxId() > 0) {
			criteria = criteria.and("serial").lte(bulletin.getMaxId());
		}
		
		if(bulletin.getBulletinName() != null) {
			criteria = criteria.and("bulletinName").is("/" + bulletin.getBulletinName() + "/");
		}
		
		List<com.hts.web.operations.pojo.NearBulletin> list = 
				getMongoTemplate()
				.find(new Query(criteria)
				.with(new Sort(Direction.DESC, "serial"))
				.skip(bulletin.getFirstRow())
				.limit(bulletin.getLimit()),
				com.hts.web.operations.pojo.NearBulletin.class, collection);
		
		if(list != null && !list.isEmpty()) {
			for(com.hts.web.operations.pojo.NearBulletin webnb : list) {
				NearBulletinDto dto = new NearBulletinDto();
				BeanUtils.copyProperties(webnb, dto);
				resList.add(dto);
			}
		}
		return resList;
	}
	

	@Override
	public long queryCount(NearBulletinDto bulletin) {
		Criteria criteria = Criteria.where("cityId").is(bulletin.getCityId());
		
		if(bulletin.getMaxId() > 0) {
			criteria = criteria.and("serial").lte(bulletin.getMaxId());
		}
		
		if(bulletin.getBulletinName() != null) {
			criteria = criteria.and("bulletinName").is("/" + bulletin.getBulletinName() + "/");
		}
		
		return getMongoTemplate().count(new Query(criteria), collection);
	}

	@Override
	public void delByIds(Integer[] ids) {
		getMongoTemplate().remove(new Query(
				Criteria.where("_id").in((Object[])ids)), 
				collection);
	}

	@Override
	public void updateSerial(Integer id, Integer serial) {
		Update update = new Update();
		update.set("serial", serial);
		getMongoTemplate().updateFirst(
				new Query(Criteria.where("_id").is(id)),
				update, collection);
	}

	@Override
	public void update(NearBulletinDto bulletin) {
		
		if(bulletin.getBulletinId() == null)
			throw new IllegalArgumentException("bulletin id can not be null");
		
		Update update = new Update();
		if(bulletin.getBulletinPath() != null)
			update.set("path", bulletin.getBulletinPath());
		
		if(bulletin.getBulletinType() != null)
			update.set("type", bulletin.getBulletinType());
		
		if(bulletin.getLink() != null)
			update.set("link", bulletin.getLink());
		
		if(bulletin.getBulletinName() != null)
			update.set("name", bulletin.getBulletinName());

		if(bulletin.getBulletinThumb() != null) 
			update.set("thumb", bulletin.getBulletinThumb());
		
		
		getMongoTemplate().updateFirst(
				new Query(Criteria.where("bulletinId").is(bulletin.getBulletinId())),
				update, collection);
	}

	@Override
	public void delByBulletinId(Integer bulletinId) {
		getMongoTemplate().remove(
				new Query(Criteria.where("bulletinId").is(bulletinId)), collection);
	}

	@Override
	public List<NearBulletinCityDto> queryCityByBulletinIds(Integer[] bulletinIds) {
		final List<NearBulletinCityDto> list = new ArrayList<NearBulletinCityDto>();
		getMongoTemplate().executeQuery(
				new Query(Criteria.where("bulletinId").in((Object[])bulletinIds))
				.with(new Sort(Direction.DESC, "serial")),
				collection, new DocumentCallbackHandler() {
					
					@Override
					public void processDocument(DBObject dbObject) throws MongoException, DataAccessException {
						NearBulletinCityDto dto = new NearBulletinCityDto();
						dto.setBulletinId((Integer)dbObject.get("bulletinId"));
						dto.setCityId((Integer)dbObject.get("cityId"));
						list.add(dto);
					}
				});
		return list;
	}

}

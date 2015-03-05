package com.imzhitu.admin.op.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpWorldType;
import com.imzhitu.admin.op.dao.OpWorldTypeCacheDao;
import com.imzhitu.admin.op.dao.OpWorldTypeDao;


@Repository
public class OpWorldTypeCacheDaoImpl extends BaseCacheDaoImpl<OpWorldType> implements OpWorldTypeCacheDao {
	
	@Autowired
	private OpWorldTypeDao opWorldTypeDao;

	@Override
	public void updateCacheType() {
		List<OpWorldType> typeList = opWorldTypeDao.queryAllValidType();
		if(getRedisTemplate().hasKey(CacheKeies.OP_TYPE)) {
			getRedisTemplate().delete(CacheKeies.OP_TYPE);
		}
		OpWorldType[] types = new OpWorldType[typeList.size()];
		getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_TYPE, typeList.toArray(types));
	}
	
	/**
	 * 构建分类
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public OpWorldType buildType(ResultSet rs) throws SQLException {
		return new OpWorldType(
				rs.getInt("id"),
				rs.getString("type_name"),
				rs.getString("type_desc"));
	}


}

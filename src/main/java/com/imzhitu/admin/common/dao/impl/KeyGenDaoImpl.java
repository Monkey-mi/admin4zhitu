package com.imzhitu.admin.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.dao.KeyGenDao;
import com.imzhitu.admin.common.database.Admin;

@Repository
public class KeyGenDaoImpl extends BaseDaoImpl implements KeyGenDao{

	public static String table = Admin.KEYGEN;
	
	/**
	 * 更新最大id
	 */
	private static final String UPDATE_MAX_ID = "UPDATE " + table + " set max_id=? where id=?";
	
	/**
	 * 查询最大id和步长
	 */
	private static final String QUERY_MAX_ID = "select max_id,step from " + table 
			+ " where id=? for update";
	
	@Override
	public Integer[] queryMaxIdAndStepForUpdate(Integer keyId) {
		Integer[] meta = getJdbcTemplate().queryForObject(QUERY_MAX_ID, new Object[]{keyId}, new RowMapper<Integer[]>() {

			@Override
			public Integer[] mapRow(ResultSet rs, int num)
					throws SQLException {
				return new Integer[]{rs.getInt("max_id"),
						rs.getInt("step")};
			}
		});
		return meta;
	}
	
	@Override
	public void updateMaxId(int keyId, int maxId) {
		getJdbcTemplate().update(UPDATE_MAX_ID, new Object[]{maxId, keyId}); //更新最大id
	}
	
}

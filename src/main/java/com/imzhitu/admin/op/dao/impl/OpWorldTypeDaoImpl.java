package com.imzhitu.admin.op.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpWorldType;
import com.imzhitu.admin.op.dao.OpWorldTypeDao;

@Repository
public class OpWorldTypeDaoImpl extends BaseDaoImpl implements OpWorldTypeDao {
	
	private static String table = HTS.HTWORLD_TYPE;
	
	/**
	 * 查询所有有效分类
	 */
	private static final String QUERY_ALL_VALID_TYPE = "select * from " + table 
			+ " where valid=1" +ORDER_BY_SERIAL_DESC;
	
	@Override
	public List<OpWorldType> queryAllValidType() {
		return getJdbcTemplate().query(QUERY_ALL_VALID_TYPE, new RowMapper<OpWorldType>(){

			@Override
			public OpWorldType mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildType(rs);
			}
		});
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

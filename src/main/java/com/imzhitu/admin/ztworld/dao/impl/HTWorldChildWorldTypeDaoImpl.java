package com.imzhitu.admin.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldChildWorldType;
import com.hts.web.common.pojo.HTWorldChildWorldTypeDto;
import com.imzhitu.admin.ztworld.dao.HTWorldChildWorldTypeDao;

@Repository
public class HTWorldChildWorldTypeDaoImpl extends BaseDaoImpl implements
		HTWorldChildWorldTypeDao {

	private static String table = HTS.HTWORLD_CHILD_WORLD_TYPE;
	
	private static final String SAVE_TYPE = "insert into " + table 
			+ " (id,type_path,total,use_count,type_desc,desc_path,label_name,serial) values (?,?,?,?,?,?,?,?)";
	
	/**
	 * 查询类型
	 */
	private static final String QUERY_TYPE = "select * from " + table
			+ ORDER_BY_SERIAL_DESC;
	
	/**
	 * 根据最大序号查询类型列表
	 */
	private static final String QUERY_TYPE_BY_MAX_SERIAL = "select * from " + table
			+ " where serial<=?" + ORDER_BY_SERIAL_DESC;
	
	/**
	 * 根据最大序号查询类型总数
	 */
	private static final String QUERY_TYPE_COUNT_BY_MAX_SERIAL = "select count(*) from " + table
			+ " where serial<=?";
	
	/**
	 * 删除类型
	 */
	private static final String DELETE_TYPE = "delete from " + table
			+ " where id in ";
	
	/**
	 * 更新序号
	 */
	private static final String UPDATE_TYPE_SERIAL = "update " + table
			+ " set serial=? where id=?";
	
	/**
	 * 根据id查询类型
	 */
	private static final String QUERY_TYPE_BY_ID = "select * from " + table
			+ " where id=?";
	
	/**
	 * 更新类型
	 */
	private static final String UPDATE_TYPE = "update " + table 
			+ " set type_path=?,total=?,use_count=?,type_desc=?,desc_path=?,label_name=?,serial=? where id=?";
	@Override
	public void saveType(HTWorldChildWorldType type) {
		getMasterJdbcTemplate().update(SAVE_TYPE, new Object[]{
			type.getId(),
			type.getTypePath(),
			type.getTotal(),
			type.getUseCount(),
			type.getTypeDesc(),
			type.getDescPath(),
			type.getLabelName(),
			type.getSerial()
		});
	}
	
	@Override
	public List<HTWorldChildWorldType> queryType(RowSelection rowSelection) {
		return queryForPage(QUERY_TYPE, new RowMapper<HTWorldChildWorldType>(){

			@Override
			public HTWorldChildWorldType mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildType(rs);
			}
			
		},rowSelection);
	}

	@Override
	public List<HTWorldChildWorldTypeDto> queryTypeDto(RowSelection rowSelection) {
		return queryForPage(QUERY_TYPE, new RowMapper<HTWorldChildWorldTypeDto>(){

			@Override
			public HTWorldChildWorldTypeDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildTypeDto(rs);
			}
			
		},rowSelection);
	}

	@Override
	public List<HTWorldChildWorldTypeDto> queryTypeDto(int maxSerial,
			RowSelection rowSelection) {
		return queryForPage(QUERY_TYPE_BY_MAX_SERIAL, new Object[]{maxSerial}, 
				new RowMapper<HTWorldChildWorldTypeDto>() {

					@Override
					public HTWorldChildWorldTypeDto mapRow(ResultSet rs,
							int rowNum) throws SQLException {
						return buildTypeDto(rs);
					}
		}, rowSelection);
	}

	@Override
	public long queryTypeCount(int maxSerial) {
		return getJdbcTemplate().queryForLong(QUERY_TYPE_COUNT_BY_MAX_SERIAL, 
				new Object[]{maxSerial});
	}
	
	@Override
	public void deleteType(Integer[] ids) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = DELETE_TYPE + inSelection;
		getMasterJdbcTemplate().update(sql, (Object[])ids);
	}

	@Override
	public void updateSerial(Integer id, Integer serial) {
		getMasterJdbcTemplate().update(UPDATE_TYPE_SERIAL, new Object[]{serial, id});
	}
	
	@Override
	public HTWorldChildWorldTypeDto queryTypeById(Integer id) {
		return queryForObjectWithNULL(QUERY_TYPE_BY_ID, new Object[]{id}, 
				new RowMapper<HTWorldChildWorldTypeDto>() {

					@Override
					public HTWorldChildWorldTypeDto mapRow(ResultSet rs,
							int rowNum) throws SQLException {
						return buildTypeDto(rs);
					}
		});
	}

	
	public HTWorldChildWorldType buildType(ResultSet rs) throws SQLException {
		return new HTWorldChildWorldType(
				rs.getInt("id"), 
				rs.getString("type_path"),
				rs.getInt("total"),
				rs.getInt("use_count"),
				rs.getString("type_desc"),
				rs.getString("desc_path"),
				rs.getString("label_name"),
				rs.getInt("serial"));
	}
	
	
	public HTWorldChildWorldTypeDto buildTypeDto(ResultSet rs) throws SQLException {
		String typePath = rs.getString("type_path");
		int index = typePath.lastIndexOf(".");
		String prefix = typePath.substring(0, index);
		String suffix = typePath.substring(index);
		
		return new HTWorldChildWorldTypeDto(
				rs.getInt("id"), 
				typePath,
				prefix + 1 + suffix,
				prefix + 2 + suffix,
				prefix + 3 + suffix,
				prefix + 4 + suffix,
				prefix + 5 + suffix,
				prefix + 6 + suffix,
				prefix + 7 + suffix,
				prefix + 8 + suffix,
				prefix + 9 + suffix,
				prefix + 10 + suffix,
				rs.getInt("total"),
				rs.getInt("use_count"),
				rs.getString("type_desc"),
				rs.getString("desc_path"),
				rs.getString("label_name"),
				rs.getInt("serial"));
	}

	@Override
	public void updateType(HTWorldChildWorldType type) {
		getMasterJdbcTemplate().update(UPDATE_TYPE, new Object[]{
			type.getTypePath(),
			type.getTotal(),
			type.getUseCount(),
			type.getTypeDesc(),
			type.getDescPath(),
			type.getLabelName(),
			type.getSerial(),
			type.getId()
		});
	}

}

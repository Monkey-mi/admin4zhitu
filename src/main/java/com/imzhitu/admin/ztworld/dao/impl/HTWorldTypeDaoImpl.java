package com.imzhitu.admin.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldType;
import com.imzhitu.admin.common.pojo.ZTWorldType;
import com.imzhitu.admin.common.pojo.ZTWorldTypeLabelDto;
import com.imzhitu.admin.ztworld.dao.HTWorldTypeDao;

/**
 * <p>
 * 广场推送数据访问对象
 * </p>
 * 
 * 创建时间：2013-1-26
 * 
 * @author ztj
 * 
 */
@Repository
public class HTWorldTypeDaoImpl extends BaseDaoImpl implements HTWorldTypeDao {

	private static String table = HTS.HTWORLD_TYPE;
	
	
	private static final String ORDER_BY_SERIAL_DESC = " order by serial desc";
	
	/**
	 * 保存分类
	 */
	private static final String SAVE_TYPE = "insert into " + table
			+ "(id,type_name,type_pinyin,type_desc,valid,serial) values (?,?,?,?,?,?)";
	
	/**
	 * 查询分类公用SQL头部
	 */
	private static final String QUERY_TYPE_HEAD = "select * from " + table 
			+ " where valid=?";
	
	/**
	 * 查询分类
	 */
	private static final String QUERY_TYPE = QUERY_TYPE_HEAD + ORDER_BY_SERIAL_DESC;
	
	/**
	 * 根据最大序号查询分类
	 */
	private static final String QUERY_TYPE_BY_MAX_SERIAL = QUERY_TYPE_HEAD 
			+ " and serial<=?" + ORDER_BY_SERIAL_DESC;
	
	/**
	 * 根据最大序号查询分类总数
	 */
	private static final String QUERY_TYPE_COUNT_BY_MAX_SERIAL = "select count(*) from " + table 
			+ " where valid=? and serial<=?";
	
	/**
	 * 批量更新分类有效性
	 */
	private static final String UPDATE_TYPE_VALID_BY_IDS = "update " + table 
			+ " set valid=? where id in";
	
	/**
	 * 查询所有分类
	 */
	private static final String QUERY_ALL_TYPE = "select * from " + table 
			+ " where valid=1" + ORDER_BY_SERIAL_DESC;
	
	@Override
	public List<ZTWorldTypeLabelDto> queryAllType() {
		return getJdbcTemplate().query(QUERY_ALL_TYPE, new RowMapper<ZTWorldTypeLabelDto>() {

			@Override
			public ZTWorldTypeLabelDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return new ZTWorldTypeLabelDto(rs.getInt("id"), rs.getString("type_name"), "closed");
			}
		});
	}
	
	@Override
	public void saveType(ZTWorldType type) {
		getMasterJdbcTemplate().update(SAVE_TYPE, new Object[]{
			type.getId(),
			type.getTypeName(),
			type.getTypePinyin(),
			type.getTypeDesc(),
			type.getValid(),
			type.getSerial()
		});
	}

	@Override
	public List<ZTWorldType> queryType(RowSelection rowSelection) {
		return queryForPage(QUERY_TYPE, new Object[]{Tag.TRUE}, new RowMapper<ZTWorldType>() {

			@Override
			public ZTWorldType mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildType(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public List<ZTWorldType> queryType(int maxSerial, RowSelection rowSelection) {
		return queryForPage(QUERY_TYPE_BY_MAX_SERIAL, new Object[]{Tag.TRUE, maxSerial}, new RowMapper<ZTWorldType>() {

			@Override
			public ZTWorldType mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildType(rs);
			}
			
		}, rowSelection);
	}


	@Override
	public long queryTypeCount(int maxSerial) {
		return getJdbcTemplate().queryForLong(QUERY_TYPE_COUNT_BY_MAX_SERIAL, new Object[]{Tag.TRUE, maxSerial});
	}
	
	@Override
	public void updateTypeValid(Integer[] ids, Integer valid) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = UPDATE_TYPE_VALID_BY_IDS + inSelection;
		Object[] args = SQLUtil.getArgsByInCondition(ids, new Object[]{valid}, true);
		getMasterJdbcTemplate().update(sql, args);
	}
	
	/**
	 * 构建HTWorldType
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private ZTWorldType buildType(ResultSet rs) throws SQLException {
		return new ZTWorldType(
				rs.getInt("id"), 
				rs.getString("type_name"), 
				rs.getString("type_pinyin"),
				rs.getString("type_desc"), 
				rs.getInt("valid"), 
				rs.getInt("serial"));
	}


}


package com.imzhitu.admin.op.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpActivityLogo;
import com.imzhitu.admin.common.pojo.OpActivityLogoDto;
import com.imzhitu.admin.op.dao.ActivityLogoDao;

@Repository
public class ActivityLogoDaoImpl extends BaseDaoImpl implements ActivityLogoDao {
	
	
	private static final String ORDER_BY_LO_SERIAL_DESC = " order by lo.serial desc";
	
	private static String table = HTS.OPERATIONS_ACTIVITY_LOGO;
	
	/**
	 * 保存logo
	 */
	private static final String SAVE_LOGO = "insert into " + table 
			+ " (activity_id, logo_path, serial, valid) values (?,?,?,?)";
	
	/**
	 * 查询活动LOGO公用SQL头部
	 */
	private static final String QUERY_LOGO_HEAD = "select * from " + table 
			+ " as lo, " + HTS.OPERATIONS_SQUARE_PUSH_ACTIVITY + " as ac "
			+ " where lo.activity_id=ac.id and ac.valid=1";
	
	/**
	 * 查询活动LOGO
	 */
	private static final String QUERY_LOGO = QUERY_LOGO_HEAD + ORDER_BY_LO_SERIAL_DESC;
	
	/**
	 * 根据最大序号查询活动LOGO
	 */
	private static final String QUERY_LOGO_BY_MAX_SERIAL = QUERY_LOGO_HEAD + " and lo.serial<=?" 
			+ ORDER_BY_LO_SERIAL_DESC;
	
	/**
	 * 根据最大序号查询LOGO总数
	 */
	private static final String QUERY_LOGO_COUNT_BY_MAX_SERIAL = "select count(lo.id) from " + table 
			+ " as lo, " + HTS.OPERATIONS_SQUARE_PUSH_ACTIVITY + " as ac "
			+ " where lo.activity_id=ac.id and ac.valid=1 and lo.serial<=?";
	
	/**
	 * 根据id删除logo
	 */
	private static final String DELETE_BY_IDS = "delete from " + table + " where id in";
	
	/**
	 * 查询有效logo
	 */
	private static final String QUERY_VALID_LOGO = QUERY_LOGO_HEAD + " and lo.valid=1"
			+ ORDER_BY_LO_SERIAL_DESC;;
	
	/**
	 * 根据ids更新有效性
	 */
	private static final String UPDATE_VALID_BY_IDS = "update " + table 
			+ " set valid=? where id in";
	
	/**
	 * 根据活动id更新有效性
	 */
	private static final String UPDATE_VALID_BY_ACTIVITY_IDS = "update " + table 
			+ " set valid=? where activity_id in";
	
	/**
	 * 更新logo排序
	 */
	private static final String UPDATE_SERIAL_BY_ID = "update " + table 
			+ " set serial=? where id=?";
	
	@Override
	public void saveLogo(OpActivityLogo logo) {
		getJdbcTemplate().update(SAVE_LOGO, new Object[]{
				logo.getActivityId(),
				logo.getLogoPath(),
				logo.getSerial(),
				logo.getValid()
		});
	}

	@Override
	public List<OpActivityLogoDto> queryLogoDto(RowSelection rowSelection) {
		return queryForPage(QUERY_LOGO, new RowMapper<OpActivityLogoDto>() {

					@Override
					public OpActivityLogoDto mapRow(ResultSet rs, int num)
							throws SQLException {
						return buildLogoDto(rs);
					}
					
			}, rowSelection);
	}

	@Override
	public List<OpActivityLogoDto> queryLogoDto(Integer maxSerial, RowSelection rowSelection) {
		return queryForPage(QUERY_LOGO_BY_MAX_SERIAL, 
				new Object[]{maxSerial}, new RowMapper<OpActivityLogoDto>() {

					@Override
					public OpActivityLogoDto mapRow(ResultSet rs, int num)
							throws SQLException {
						return buildLogoDto(rs);
					}
				}, rowSelection);
	}

	@Override
	public long queryLogoCount(Integer maxSerial) {
		return getJdbcTemplate().queryForLong(QUERY_LOGO_COUNT_BY_MAX_SERIAL,
				new Object[]{maxSerial});
	}
	

	@Override
	public void deleteByIds(Integer[] ids) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = DELETE_BY_IDS + inSelection;
		getJdbcTemplate().update(sql, (Object[])ids);
	}
	
	@Override
	public List<OpActivityLogo> queryValidLogo() {
		return getJdbcTemplate().query(QUERY_VALID_LOGO, new RowMapper<OpActivityLogo>(){

			@Override
			public OpActivityLogo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildLogo(rs);
			}
			
		});
	}
	
	@Override
	public void updateValidByIds(Integer[] ids, Integer valid) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = UPDATE_VALID_BY_IDS + inSelection;
		Object[] args = SQLUtil.getArgsByInCondition(ids, new Object[]{valid}, true);
		getJdbcTemplate().update(sql, args);
	}
	
	@Override
	public void updateValidByActivityIds(Integer[] activityIds, Integer valid) {
		String inSelection = SQLUtil.buildInSelection(activityIds);
		String sql = UPDATE_VALID_BY_ACTIVITY_IDS + inSelection;
		Object[] args = SQLUtil.getArgsByInCondition(activityIds, new Object[]{valid}, true);
		getJdbcTemplate().update(sql, args);
	}
	
	public OpActivityLogo buildLogo(ResultSet rs) throws SQLException {
		return new OpActivityLogo(
			rs.getInt("id"), 
			rs.getInt("activity_id"),
			rs.getString("logo_path"),
			rs.getInt("serial"),
			rs.getInt("valid"));
	}
	
	public OpActivityLogoDto buildLogoDto(ResultSet rs) throws SQLException {
		return new OpActivityLogoDto(
				rs.getInt("id"),
				rs.getString("logo_path"),
				rs.getInt("activity_id"),
				rs.getString("title_path"),
				rs.getString("title_thumb_path"),
				rs.getString("activity_name"),
				rs.getString("activity_title"),
				rs.getString("activity_desc"),
				rs.getString("activity_link"),
				rs.getString("activity_logo"),
				(Date)rs.getObject("activity_date"),
				rs.getInt("obj_type"),
				rs.getInt("obj_id"),
				rs.getInt("commercial"),
				rs.getInt("serial"), 
				rs.getInt("valid"));
	}

	@Override
	public void updateSeria(Integer id, Integer serial) {
		getJdbcTemplate().update(UPDATE_SERIAL_BY_ID, new Object[]{serial, id});
	}

}

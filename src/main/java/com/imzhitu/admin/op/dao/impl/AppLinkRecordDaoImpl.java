package com.imzhitu.admin.op.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpAdAppLinkRecord;
import com.imzhitu.admin.op.dao.AppLinkRecordDao;

@Repository
public class AppLinkRecordDaoImpl extends BaseDaoImpl implements
		AppLinkRecordDao {
	
	private static String table = HTS.OPERATIONS_AD_APPLINK_RECORD;
	
	public static final String SAVE_RECORD = "insert into " + table
			+ " (record_ip,record_date,app_id) values (?,?,?)";
	
	private static final String QUERY_RECORD_HEAD = "select * from " + table + " where app_id=?";
	
	private static final String QUERY_RECORD = QUERY_RECORD_HEAD + ORDER_BY_ID_DESC;
	
	private static final String QUERY_RECORD_BY_MAX_ID = QUERY_RECORD_HEAD + " and id<=?" + ORDER_BY_ID_DESC;
	
	private static final String QUERY_RECORD_COUNT_BY_MAX_ID = "select count(*) from " + table + " where app_id=? and id<=?";
	
	@Override
	public void saveRecord(OpAdAppLinkRecord record) {
		getMasterJdbcTemplate().update(SAVE_RECORD, new Object[]{
			record.getRecordip(),
			record.getRecordDate(),
			record.getAppId()
		});
	}

	@Override
	public List<OpAdAppLinkRecord> queryRecord(Integer appId, RowSelection rowSelection) {
		return queryForPage(QUERY_RECORD, new Object[]{appId}, new RowMapper<OpAdAppLinkRecord>() {

			@Override
			public OpAdAppLinkRecord mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildRecordByRs(rs);
			}
			
		},rowSelection);
	}

	@Override
	public List<OpAdAppLinkRecord> queryRecord(Integer maxId, Integer appId, RowSelection rowSelection) {
		return queryForPage(QUERY_RECORD_BY_MAX_ID, new Object[]{appId, maxId}, new RowMapper<OpAdAppLinkRecord>() {

			@Override
			public OpAdAppLinkRecord mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildRecordByRs(rs);
			}
			
		},rowSelection);
	}

	@Override
	public long queryRecordCount(Integer maxId, Integer appId) {
		return getJdbcTemplate().queryForLong(QUERY_RECORD_COUNT_BY_MAX_ID,
				new Object[]{appId, maxId});
	}
	
	public OpAdAppLinkRecord buildRecordByRs(ResultSet rs) throws SQLException {
		return new OpAdAppLinkRecord(
				rs.getInt("id"), 
				rs.getString("record_ip"), 
				(Date)rs.getObject("record_date"), 
				rs.getInt("app_id"));
	}

}

package com.imzhitu.admin.ztworld.dao.impl;

import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.ztworld.dao.HTWorldReportDao;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.imzhitu.admin.common.pojo.ZTWorldReport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class HTWorldReportDaoImpl extends BaseDaoImpl implements HTWorldReportDao{
	public static String reportTable = HTS.HTWORLD_REPORT;
	public static String worldTable = HTS.HTWORLD_HTWORLD;
	public static String userTable = HTS.USER_INFO;
	private static final String QUERY_REPORT = "select hr.id as commentId,hr.user_id,hr.world_id,hr.report_content,hr.report_date,hr.valid as reportValid,ut.user_name as author_name,ut.user_avatar as author_avatar,hw.* from "+
												reportTable + " as hr, "+worldTable+" as hw, "+userTable+
												" as ut where hr.valid= ? and hr.world_id = hw.id and hr.user_id = ut.id order by hr.id desc";
	private static final String QUERY_REPORT_BY_MAX_ID = "select hr.id as commentId,hr.user_id,hr.world_id,hr.report_content,hr.report_date,hr.valid as reportValid,ut.user_name as author_name,ut.user_avatar as author_avatar,hw.* from "+
												reportTable + " as hr, "+worldTable+" as hw, "+userTable+
												" as ut where hr.valid= ? and hr.id<=? and hr.world_id = hw.id and hr.user_id = ut.id order by hr.id desc";
	private static final String QUERY_REPORT_COUNT = "select count(1) from " + reportTable + " as hr"; 
	private static final String DELETE_REPORT_BY_ID ="delete from " + reportTable + "  where id in ";
	private ZTWorldReport worldReport;
	public ZTWorldReport getWorldReport(){
		return this.worldReport;
	}
	public void setWorldReport(ZTWorldReport worldReport){
		this.worldReport = worldReport;
	}
	
	@Override
	public List<ZTWorldReport> queryReport(
			RowSelection rowSelection){
		return queryForPage(QUERY_REPORT, new Object[]{Tag.TRUE},new RowMapper<ZTWorldReport>() {

			@Override
			public ZTWorldReport mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildZTWorldReport(rs);
			}
		}, rowSelection);
	}
	
	
	@Override
	public List<ZTWorldReport> queryReport(Integer maxId,
			 RowSelection rowSelection){
		return queryForPage(QUERY_REPORT_BY_MAX_ID, new Object[]{Tag.TRUE,maxId}, new RowMapper<ZTWorldReport>() {

			@Override
			public ZTWorldReport mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildZTWorldReport(rs);
			}
		}, rowSelection);
		
	}
	
	@Override
	public long queryReportCount(){
		return getJdbcTemplate().queryForLong(QUERY_REPORT_COUNT);
	}
	
	@Override
	public void deleteReportById(Integer[] ids){
		String inSection = SQLUtil.buildInSelection(ids);
		String sql = DELETE_REPORT_BY_ID + inSection;
		getMasterJdbcTemplate().update(sql,(Object[])ids);
		
	}
	
	public ZTWorldReport buildZTWorldReport(ResultSet rs)throws SQLException{
		return new ZTWorldReport(rs.getInt("commentId"),
								 rs.getInt("user_id"),
								 rs.getInt("world_id"),
								 rs.getString("report_content"),
								 rs.getDate("report_date"),
								 rs.getInt("reportValid"),
								 rs.getInt("phone_code"),
								 rs.getString("author_name"),
								 rs.getString("author_avatar"),
								 rs.getInt("click_count"),
								 rs.getInt("like_count"),
								 getUrlPrefix()+rs.getString("short_link"),
								 rs.getString("world_desc"),
								 rs.getString("title_thumb_path"),
								 rs.getString("world_label"),
								 rs.getString("world_type"),
								 rs.getInt("comment_count")
								 );
	}
	

}

package com.imzhitu.admin.op.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpAdAppLink;
import com.hts.web.common.pojo.OpAdAppLinkDto;
import com.imzhitu.admin.op.dao.AppLinkDao;

@Repository
public class AppLinkDaoImpl extends BaseDaoImpl implements AppLinkDao {

	private static String table = HTS.OPERATIONS_AD_APPLINK;

	private static final String ORDER_BY_SERIAL_DESC = " order by serial desc";
	
	/** 
	 * 保存App链接 
	 * */
	private static final String SAVE_LINK = "insert into " + table
			+ " (id,app_name,app_icon,app_desc,app_link,short_link,phone_code,serial,open) values (?,?,?,?,?,?,?,?,?)";
	
	private static final String QUERY_APP_LINK_DTO_HEAD = "select app_name, app_icon,app_desc,short_link,serial from " + table 
			+ " where open=? and phone_code=?";
	
	private static final String QUERY_APP_LINK_DTO = QUERY_APP_LINK_DTO_HEAD + ORDER_BY_SERIAL_DESC;
	
	private static final String QUERY_APP_LINK_DTO_BY_MAX_SERIAL = QUERY_APP_LINK_DTO_HEAD + " and serial<=?" + ORDER_BY_SERIAL_DESC;
	
	private static final String QUERY_APP_LINK_HEAD = "select * from " + table + " where open=? and phone_code=?";
	
	private static final String QUERY_APP_LINK = QUERY_APP_LINK_HEAD + ORDER_BY_SERIAL_DESC;
	
	private static final String QUERY_APP_LINK_BY_MAX_SERIAL = QUERY_APP_LINK_HEAD + " and serial<=?" + ORDER_BY_SERIAL_DESC;
	
	private static final String QUERY_APP_LINK_COUNT_BY_MAX_SERIAL = "select count(*) from " + table 
			+ " where open=? and phone_code=? and serial<=?";
	
	private static final String QUERY_APP_LINK_BY_SHORT_LINK = "select * from " + table + " where short_link=?";
	
	private static final String ADD_CLICK_COUNT = "update " + table + " set click_count=click_count+1 where id=?";
	
	private static final String UPDATE_SERIAL = "update " + table + " set serial=? where id=?";
	
	private static final String UPDATE_APP_LINK = "update " + table 
			+ " set app_name=?,app_icon=?,app_desc=?,app_link=?,phone_code=? where id=?";
	
	/**
	 * 更新app开放标记
	 */
	private static final String UPDATE_APP_OPEN = "update " + table
			+ " set open=? where id in ";
	
	@Value("${appUrlPrefix}")
	private String appUrlPrefix = "http://www.imzhitu.com/APP";
	
	public String getAppUrlPrefix() {
		return appUrlPrefix;
	}

	public void setAppUrlPrefix(String appUrlPrefix) {
		this.appUrlPrefix = appUrlPrefix;
	}

	@Override
	public void saveAppLink(OpAdAppLink link) {
		getMasterJdbcTemplate().update(SAVE_LINK, new Object[]{
			link.getId(),
			link.getAppName(),
			link.getAppIcon(),
			link.getAppDesc(),
			link.getAppLink(),
			link.getShortLink(),
			link.getPhoneCode(),
			link.getSerial(),
			link.getOpen()
		});
	}

	@Override
	public List<OpAdAppLinkDto> queryAppLinkDto(Integer open, Integer phoneCode, RowSelection rowSelection) {
		return queryForPage(QUERY_APP_LINK_DTO, new Object[]{open, phoneCode}, new RowMapper<OpAdAppLinkDto>(){

			@Override
			public OpAdAppLinkDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildAppLinkDtoByRs(rs);
			}
			
		},rowSelection);
	}

	@Override
	public List<OpAdAppLinkDto> queryAppLinkDto(Integer maxSerial, Integer open, Integer phoneCode,
			RowSelection rowSelection) {
		return queryForPage(QUERY_APP_LINK_DTO_BY_MAX_SERIAL, new Object[]{open, phoneCode, maxSerial}, new RowMapper<OpAdAppLinkDto>(){

			@Override
			public OpAdAppLinkDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildAppLinkDtoByRs(rs);
			}
			
		},rowSelection);
	}
	
	@Override
	public List<OpAdAppLink> queryAppLink(Integer open, Integer phoneCode, RowSelection rowSelection) {
		return queryForPage(QUERY_APP_LINK, new Object[]{open,phoneCode}, new RowMapper<OpAdAppLink>(){

			@Override
			public OpAdAppLink mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildAppLinkByRs(rs);
			}
			
		},rowSelection);
	}

	@Override
	public List<OpAdAppLink> queryAppLink(Integer maxSerial, Integer open, Integer phoneCode,
			RowSelection rowSelection) {
		return queryForPage(QUERY_APP_LINK_BY_MAX_SERIAL, new Object[]{open, phoneCode, maxSerial},
				new RowMapper<OpAdAppLink>(){

			@Override
			public OpAdAppLink mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildAppLinkByRs(rs);
			}
			
		},rowSelection);
	}

	@Override
	public long queryAppLinkCount(Integer maxSerial, Integer open, Integer phoneCode) {
		return getJdbcTemplate().queryForLong(QUERY_APP_LINK_COUNT_BY_MAX_SERIAL, 
				new Object[]{open, phoneCode,maxSerial});
	}
	
	@Override
	public OpAdAppLink queryIdByShortLink(String shortLink) {
		return queryForObjectWithNULL(QUERY_APP_LINK_BY_SHORT_LINK, new Object[]{shortLink}, 
				new RowMapper<OpAdAppLink>() {

			@Override
			public OpAdAppLink mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildAppLinkByRs(rs);
			}
		});
	}
	

	@Override
	public void addClickCount(Integer id) {
		getMasterJdbcTemplate().update(ADD_CLICK_COUNT, new Object[]{id});
	}
	
	@Override
	public void updateSerial(Integer id, Integer serial) {
		getMasterJdbcTemplate().update(UPDATE_SERIAL, new Object[]{serial, id});
	}
	
	@Override
	public void updateOpenByIds(Integer[] ids, Integer open) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = UPDATE_APP_OPEN + inSelection;
		Object[] args = SQLUtil.getArgsByInCondition(ids, new Object[]{open}, true);
		getMasterJdbcTemplate().update(sql, args);
	}
	
	/**
	 * 根据结果集构建AppLinkDto
	 * 
	 * @param rs
	 * @throws SQLException 
	 */
	public OpAdAppLinkDto buildAppLinkDtoByRs(ResultSet rs) throws SQLException {
		return new OpAdAppLinkDto(
			rs.getInt("serial"),
			rs.getString("app_name"),
			rs.getString("app_icon"),
			rs.getString("app_desc"),
			urlPrefix + rs.getString("short_link")
		);
	}
	
	/**
	 * 根据结果集构建AppLink
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public OpAdAppLink buildAppLinkByRs(ResultSet rs) throws SQLException {
		OpAdAppLink link = new OpAdAppLink(
				rs.getInt("id"),
				rs.getString("app_name"),
				rs.getString("app_icon"),
				rs.getString("app_desc"),
				rs.getString("app_link"),
				rs.getString("short_link"),
				rs.getInt("phone_code"),
				rs.getInt("click_count"),
				rs.getInt("serial"),
				rs.getInt("open"));
		link.setUrl(urlPrefix + rs.getString("short_link"));
		return link;
	}

	@Override
	public void updateAppLink(OpAdAppLink link) {
		getMasterJdbcTemplate().update(UPDATE_APP_LINK, new Object[]{
			link.getAppName(),
			link.getAppIcon(),
			link.getAppDesc(),
			link.getAppLink(),
			link.getPhoneCode(),
			link.getId()
		});
	}

}

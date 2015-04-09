package com.imzhitu.admin.op.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpActivitySponsor;
import com.imzhitu.admin.op.dao.ActivitySponsorDao;

@Repository
public class ActivitySponsorDaoImpl extends BaseDaoImpl implements
		ActivitySponsorDao {

	private static String table = HTS.OPERATIONS_ACTIVITY_SPONSOR;
	
	/**
	 * 发起人信息
	 */
	private static final String SPONSOR_INFO = "u.id,u.user_name,u.user_avatar,u.user_avatar_l,u.star,u.platform_verify";
	
	/**
	 * 保存发起人
	 */
	private static final String SAVE_SPONSOR = "insert into " + table
			+ " (activity_id, user_id) values (?,?)";
	
	/**
	 * 根据活动id删除发起人
	 */
	private static final String DELETE_BY_ACTIVITY_ID = "delete from " + table
			+ " where activity_id=?";
	
	/**
	 * 根据活动ids查询发起人
	 */
	private static final String QUERY_SPONSOR_BY_ACTIVITY_IDS = "select s.activity_id," + SPONSOR_INFO
			+ " from " + table + " as s," + HTS.USER_INFO + " as u"
			+ " where s.user_id=u.id and u.shield=0 and s.activity_id in ";
	
	/**
	 * 根据活动id查询发起人
	 */
	private static final String QUERY_SPONSOR_BY_ID = "select s.activity_id," + SPONSOR_INFO
			+ " from " + table + " as s," + HTS.USER_INFO + " as u"
			+ " where s.user_id=u.id and s.activity_id=?";
	
	
	@Override
	public void querySponsor(Integer[] activityIds,
			final RowCallback<OpActivitySponsor> callback) {
		String inSelection = SQLUtil.buildInSelection(activityIds);
		String sql = QUERY_SPONSOR_BY_ACTIVITY_IDS + inSelection;
		getJdbcTemplate().query(sql, (Object[])activityIds, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(buildSponsor(rs));
			}
		});
	}
	
	/**
	 * 构建OpActivitySponsor
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public OpActivitySponsor buildSponsor(ResultSet rs) throws SQLException {
		return new OpActivitySponsor(
				rs.getInt("activity_id"),
				rs.getInt("id"), 
				rs.getString("user_name"), 
				rs.getString("user_avatar"), 
				rs.getString("user_avatar_l"), 
				rs.getInt("star"),
				rs.getInt("platform_verify"));
	}

	
	@Override
	public void saveSponsor(Integer activityId, Integer userId) {
		getMasterJdbcTemplate().update(SAVE_SPONSOR, new Object[]{activityId, userId});
	}

	@Override
	public void deleteByActivityId(Integer activityId) {
		getMasterJdbcTemplate().update(DELETE_BY_ACTIVITY_ID, new Object[]{activityId});
	}

	@Override
	public List<OpActivitySponsor> querySponsor(Integer activityId) {
		return getJdbcTemplate().query(QUERY_SPONSOR_BY_ID, new Object[]{activityId},
				new RowMapper<OpActivitySponsor>() {

					@Override
					public OpActivitySponsor mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildSponsor(rs);
					}
			
		});
	}

}

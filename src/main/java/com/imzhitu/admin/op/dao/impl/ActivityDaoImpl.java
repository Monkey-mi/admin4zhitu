package com.imzhitu.admin.op.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpActivity;
import com.imzhitu.admin.op.dao.ActivityDao;

/**
 * <p>
 * 活动数据访问对象
 * </p>
 * 
 * 创建时间：2013-11-8
 * @author ztj
 *
 */
@Repository
public class ActivityDaoImpl extends BaseDaoImpl implements
		ActivityDao {
	
	private static String table = HTS.OPERATIONS_SQUARE_PUSH_ACTIVITY;
	
	/**
	 * 
	 */
	private static String ORDER_BY_LB_SERIAL_DESC = " order by lb.serial desc";
	
	/** 
	 * 保存活动
	 */
	private static final String SAVE_ACTIVITY = "insert into " + table 
			+ " (id,title_path,title_thumb_path,channel_path,activity_name,activity_title,"
			+ "activity_desc,activity_link,activity_logo,activity_date,deadline,obj_type,"
			+ "obj_id,commercial,share_title,share_desc,valid,serial) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * 查询活动公用头部 
	 */
	private static final String QUERY_ACTIVITY_HEAD = "select lb.*,ac.* from " + table
			+ " as ac, " + HTS.HTWORLD_LABEL + " as lb where ac.id=lb.id ";
	
	/** 
	 * 查询活动 
	 */
	private static final String QUERY_ACTIVITY = QUERY_ACTIVITY_HEAD + ORDER_BY_LB_SERIAL_DESC;
	
	/** 
	 * 根据最大序号查询活动
	 */
	private static final String QUERY_ACTIVITY_BY_MAX_SERIAL = QUERY_ACTIVITY_HEAD 
			+ " and lb.serial<=?" + ORDER_BY_LB_SERIAL_DESC;
	
	/** 
	 * 根据最大序号查询活动总数
	 */
	private static final String QUERY_ACTIVITY_TOTAL_BY_MAX_SERIAL = "select count(*) from " + table 
			+ " as ac, " + HTS.HTWORLD_LABEL + " as lb where ac.id=lb.id and lb.serial<=?";
	
	/**
	 * 更新活动
	 */
	private static final String UPDATE_ACTIVITY = "update " + table 
			+ " set title_path=?,title_thumb_path=?,channel_path=?,activity_name=?,activity_title=?," 
			+ "activity_desc=?,activity_link=?,activity_logo=?,activity_date=?,deadline=?,"
			+ "obj_type=?,obj_id=?,commercial=?,share_title=?,share_desc=?,valid=? where id=?";
	
	
	/**
	 * 根据ids查询活动信息
	 */
	private static final String QUERY_ACTIVITY_BY_IDS = "select * from " + table + " where id in";
	
	/**
	 * 根据ids删除活动
	 */
	private static final String DELETE_BY_IDS = "delete from " + table
			+ " where id in ";
	
	/**
	 * 根据id查询活动
	 */
	private static final String QUERY_ACTIVITY_BY_ID = "select * from " + table + " where id=?";
	
	/**
	 * 根据id删除活动
	 */
	private static final String DElETE_BY_ID = "delete from " + table
			+ " where id=?";
	
	@Override
	public void saveActivity(OpActivity activity) {
		getJdbcTemplate().update(SAVE_ACTIVITY, new Object[]{
			activity.getId(),
			activity.getTitlePath(),
			activity.getTitleThumbPath(),
			activity.getChannelPath(),
			activity.getActivityName(),
			activity.getActivityTitle(),
			activity.getActivityDesc(),
			activity.getActivityLink(),
			activity.getActivityLogo(),
			activity.getActivityDate(),
			activity.getDeadline(),
			activity.getObjType(),
			activity.getObjId(),
			activity.getCommercial(),
			activity.getShareTitle(),
			activity.getShareDesc(),
			activity.getValid(),
			activity.getSerial()
		});
	}
	
	@Override
	public void updateActivity(OpActivity activity) {
		getJdbcTemplate().update(UPDATE_ACTIVITY, new Object[]{
			activity.getTitlePath(),
			activity.getTitleThumbPath(),
			activity.getChannelPath(),
			activity.getActivityName(),
			activity.getActivityTitle(),
			activity.getActivityDesc(),
			activity.getActivityLink(),
			activity.getActivityLogo(),
			activity.getActivityDate(),
			activity.getDeadline(),
			activity.getObjType(),
			activity.getObjId(),
			activity.getCommercial(),
			activity.getShareTitle(),
			activity.getShareDesc(),
			activity.getValid(),
			activity.getId()
		});
	}

	@Override
	public List<OpActivity> queryActivity(RowSelection rowSelection) {
		return queryForPage(QUERY_ACTIVITY, new RowMapper<OpActivity>() {

			@Override
			public OpActivity mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OpActivity act = buildActivity(rs);
				act.setActivityName(rs.getString("label_name"));
				return act;
			}
			
		}, rowSelection);
	}

	@Override
	public List<OpActivity> queryActivity(int maxId, RowSelection rowSelection) {
		return queryForPage(QUERY_ACTIVITY_BY_MAX_SERIAL, new Object[]{maxId}, new RowMapper<OpActivity>() {

			@Override
			public OpActivity mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OpActivity act = buildActivity(rs);
				act.setActivityName(rs.getString("label_name"));
				return act;
			}
		}, rowSelection);
	}
	
	@Override
	public long queryActivityTotal(int maxId) {
		return getJdbcTemplate().queryForLong(QUERY_ACTIVITY_TOTAL_BY_MAX_SERIAL, new Object[]{maxId});
	}

	@Override
	public OpActivity queryActivityById(Integer id) {
		return queryForObjectWithNULL(QUERY_ACTIVITY_BY_ID, new Object[]{id}, new RowMapper<OpActivity>() {

			@Override
			public OpActivity mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildActivity(rs);
			}
		});
	}
	
	@Override
	public Map<Integer, OpActivity> queryActivityByIds(Integer[] ids) {
		final Map<Integer, OpActivity> map = new HashMap<Integer, OpActivity>();
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = QUERY_ACTIVITY_BY_IDS + inSelection;
		getJdbcTemplate().query(sql, ids, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				OpActivity act = buildActivity(rs);
				map.put(act.getId(), act);
			}
		});
		return map;
	}
	
	@Override
	public void deleteByIds(Integer[] ids) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = DELETE_BY_IDS + inSelection;
		getJdbcTemplate().update(sql, (Object[])ids);
	}
	
	@Override
	public void deleteById(Integer id) {
		getJdbcTemplate().update(DElETE_BY_ID, new Object[]{id});
	}
	
	public OpActivity buildActivity(ResultSet rs) throws SQLException {
		Date deadline = null;
		Object deadlineObj = rs.getObject("deadline");
		if(deadlineObj != null) {
			deadline = (Date) deadlineObj;
		}
		return new OpActivity(
				rs.getInt("id"), 
				rs.getString("title_path"),
				rs.getString("title_thumb_path"),
				rs.getString("channel_path"),
				rs.getString("activity_name"),
				rs.getString("activity_title"),
				rs.getString("activity_desc"),
				rs.getString("activity_link"),
				rs.getString("activity_logo"),
				(Date)rs.getObject("activity_date"),
				deadline,
				rs.getInt("obj_type"),
				rs.getInt("obj_id"),
				rs.getInt("commercial"),
				rs.getString("share_title"),
				rs.getString("share_desc"),
				rs.getInt("valid"),
				rs.getInt("serial"));
	}

}

package com.imzhitu.admin.op.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpActivityWinner;
import com.hts.web.common.util.CollectionUtil;
import com.imzhitu.admin.common.pojo.OpActivityWorldDto;
import com.imzhitu.admin.op.dao.ActivityWinnerDao;
import com.imzhitu.admin.op.dao.ActivityWorldDao;

@Repository
public class ActivityWinnerDaoImpl extends BaseDaoImpl implements
		ActivityWinnerDao {

	/**
	 * 查询活动获胜织图公用头部
	 */
	private static final String QUERY_ACTIVITY_WINNER_HEAD = "select aw0.activity_id,aw0.world_id,aw0.award_id,aw0.serial,aw0.weight,awa0.award_name,awa0.icon_thumb_path,'1' as lw_valid,lw0.id as activity_world_id,h0.*," + U0_INFO
			+ " from " + HTS.HTWORLD_HTWORLD + " as h0," + HTS.USER_INFO + " as u0," + HTS.OPERATIONS_ACTIVITY_WINNER + " as aw0,"
			+ HTS.OPERATIONS_ACTIVITY_AWARD + " as awa0,"+ HTS.HTWORLD_LABEL_WORLD + " as lw0"
			+ " where aw0.world_id=h0.id and h0.author_id=u0.id and lw0.label_id=aw0.activity_id and awa0.id=aw0.award_id"
			+ " and aw0.world_id=lw0.world_id and lw0.valid=1 and aw0.activity_id=? and h0.valid=1 and h0.shield=0";
	
	/**
	 * 查询活动获胜织图公用尾部
	 */
	private static final String QUERY_ACTIVITY_WINNER_FOOT = " order by aw0.serial desc limit ?,?";
	
	/**
	 * 根据最大id查询活动获胜织图总数
	 */
	private static final String QUERY_ACTIVITY_WINNER_COUNT_BY_MAX_ID = "select count(*)"
			+ " from " + HTS.HTWORLD_HTWORLD + " as h0," + HTS.OPERATIONS_ACTIVITY_WINNER + " as aw0,"
			+ HTS.HTWORLD_LABEL_WORLD + " as lw0"
			+ " where aw0.world_id=h0.id and h0.valid=1 and lw0.label_id=aw0.activity_id "
			+ " and aw0.world_id=lw0.world_id and lw0.valid=1 and h0.shield=0 and aw0.activity_id=? and aw0.serial<=?";
	
	/**
	 * 保存获胜者
	 */
	private static final String SAVE_WINNER = "insert into " + HTS.OPERATIONS_ACTIVITY_WINNER 
			+ " (id,activity_id,world_id,user_id,award_id,serial,weight) values (?,?,?,?,?,?,?)";
	
	/**
	 * 删除获胜者
	 */
	private static final String DELETE_WINNER = "delete from " + HTS.OPERATIONS_ACTIVITY_WINNER
			+ " where activity_id=? and world_id=?";
	
	/**
	 * 更新奖品id
	 */
	private static final String UPDATE_WINNER_AWARD = "update " + HTS.OPERATIONS_ACTIVITY_WINNER
			+ " set award_id=? where activity_id=? and world_id=?";
	
	/**
	 * 查询奖品id
	 */
	private static final String QUERY_WINNER_AWARD_ID = "select award_id from " + HTS.OPERATIONS_ACTIVITY_WINNER
			+ " where activity_id=? and world_id=?";
	

	@Autowired
	private ActivityWorldDao activityWorldDao;
	

	@Override
	public List<OpActivityWorldDto> queryWinner(Integer activityId,
			LinkedHashMap<String, Object> attrMap, RowSelection rowSelection) {
		String sql = QUERY_ACTIVITY_WINNER_HEAD + buildQueryActivityWorldSelection(attrMap) 
				+ QUERY_ACTIVITY_WINNER_FOOT;
		List<Object> args = new ArrayList<Object>();
		args.add(activityId);
		CollectionUtil.collectMapValues(args, attrMap);
		args.add(rowSelection.getFirstRow());
		args.add(rowSelection.getLimit());
		return getJdbcTemplate().query(sql, args.toArray(), new RowMapper<OpActivityWorldDto>() {

			@Override
			public OpActivityWorldDto mapRow(ResultSet rs,
					int rowNum) throws SQLException {
				OpActivityWorldDto dto = activityWorldDao.buildActivityWorldDto(rs);
				dto.setAwardId(rs.getInt("award_id"));
				dto.setAwardName(rs.getString("award_name"));
				dto.setAwardThumbPath(rs.getString("icon_thumb_path"));
				dto.setIsWinner(Tag.TRUE);
				return dto;
			}
		});
	}

	@Override
	public List<OpActivityWorldDto> queryWinner(Integer maxId,
			Integer activityId, LinkedHashMap<String, Object> attrMap,
			RowSelection rowSelection) {
		String sql = QUERY_ACTIVITY_WINNER_HEAD + " and aw0.serial<=? " + buildQueryActivityWorldSelection(attrMap) 
				+ QUERY_ACTIVITY_WINNER_FOOT;
		List<Object> args = new ArrayList<Object>();
		args.add(activityId);
		args.add(maxId);
		CollectionUtil.collectMapValues(args, attrMap);
		args.add(rowSelection.getFirstRow());
		args.add(rowSelection.getLimit());
		return getJdbcTemplate().query(sql, args.toArray(), new RowMapper<OpActivityWorldDto>() {

			@Override
			public OpActivityWorldDto mapRow(ResultSet rs,
					int rowNum) throws SQLException {
				OpActivityWorldDto dto = activityWorldDao.buildActivityWorldDto(rs);
				dto.setAwardId(rs.getInt("award_id"));
				dto.setAwardName(rs.getString("award_name"));
				dto.setAwardThumbPath(rs.getString("icon_thumb_path"));
				dto.setIsWinner(Tag.TRUE);
				return dto;
			}
		});
	}

	@Override
	public long queryWinnerCount(Integer maxId, Integer activityId,
			LinkedHashMap<String, Object> attrMap) {
		String sql = QUERY_ACTIVITY_WINNER_COUNT_BY_MAX_ID + buildQueryActivityWorldSelection(attrMap);
		List<Object> args = new ArrayList<Object>();
		args.add(activityId);
		args.add(maxId);
		CollectionUtil.collectMapValues(args, attrMap);
		return getJdbcTemplate().queryForLong(sql, args.toArray());
	}
	
	/**
	 * 构建活动织图查询条件
	 * 
	 * @param attrMap
	 * @return
	 */
	private String buildQueryActivityWorldSelection(Map<String, Object> attrMap) {
		StringBuilder builder = new StringBuilder();
		Object weight = attrMap.get("weight");
		if(weight != null) {
			if(weight.equals(Tag.FALSE)) {
				builder.append(" and aw0.weight=? ");
			} else {
				builder.append(" and aw0.weight>? ");
			}
		}
		return builder.toString();
	}

	@Override
	public void saveWinner(OpActivityWinner winner) {
		getMasterJdbcTemplate().update(SAVE_WINNER, new Object[]{
			winner.getId(),
			winner.getActivityId(),
			winner.getWorldId(),
			winner.getUserId(),
			winner.getAwardId(),
			winner.getSerial(),
			winner.getWeight()
		});
	}

	@Override
	public void deleteWinner(Integer activityId, Integer worldId) {
		getMasterJdbcTemplate().update(DELETE_WINNER, new Object[]{activityId, worldId});
	}

	@Override
	public Integer queryAwardId(Integer activityId, Integer worldId) {
		return getJdbcTemplate().queryForInt(QUERY_WINNER_AWARD_ID, 
				new Object[]{activityId, worldId});
	}

	@Override
	public void updateAwardId(Integer activityId, Integer worldId, Integer awardId) {
		getMasterJdbcTemplate().update(UPDATE_WINNER_AWARD, new Object[]{awardId, activityId, worldId});
	}
	

}

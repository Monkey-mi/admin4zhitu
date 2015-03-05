package com.imzhitu.admin.op.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.util.CollectionUtil;
import com.imzhitu.admin.common.pojo.OpActivityWorldCheckDto;
import com.imzhitu.admin.common.pojo.OpActivityWorldDto;
import com.imzhitu.admin.op.dao.ActivityWorldDao;

@Repository
public class ActivityWorldDaoImpl extends BaseDaoImpl implements
		ActivityWorldDao {
	
	/**
	 * 查询活动织图公用头部
	 */
	private static final String QUERY_ACTIVITY_WORLD_HEAD = "select h.*, 1-ISNULL(aw.world_id) as iswinner, awa.id as award_id,awa.award_name,awa.icon_thumb_path from "
			+ "(select lw0.label_id as activity_id,lw0.id as activity_world_id,lw0.world_id,lw0.valid as lw_valid,lw0.serial,lw0.weight,h0.*," + U0_INFO
			+ " from " + HTS.HTWORLD_HTWORLD + " as h0," + HTS.USER_INFO + " as u0," + HTS.HTWORLD_LABEL_WORLD + " as lw0"
			+ " where lw0.world_id=h0.id and h0.author_id=u0.id and lw0.label_id=? and h0.valid=1 and h0.shield=0";
	
	/**
	 * 查询活动织图公用尾部
	 */
	private static final String QUERY_ACTIVITY_WORLD_FOOT = " order by lw0.serial desc limit ?,?) as h left join " 
			+ HTS.OPERATIONS_ACTIVITY_WINNER + " as aw" 
			+ " on h.world_id = aw.world_id and h.activity_id=aw.activity_id"
			+ " left join " + HTS.OPERATIONS_ACTIVITY_AWARD + " as awa"
			+ " on aw.award_id=awa.id";
	
	/**
	 * 根据最大id查询活动织图总数
	 */
	private static final String QUERY_ACTIVITY_WORLD_COUNT_BY_MAX_ID = "select count(*)"
			+ " from " + HTS.HTWORLD_HTWORLD + " as h0," + HTS.USER_INFO + " as u0," + HTS.HTWORLD_LABEL_WORLD + " as lw0"
			+ " where lw0.world_id=h0.id and h0.author_id=u0.id and h0.valid=1 and h0.shield=0 and lw0.label_id=? and lw0.id<=?";
	
	
	private static final String QUERY_ACTIVITY_WORLD_CHECK_HEAD = "select aw.id,aw.world_id,aw.label_id,aw.user_id,a.label_name,u.user_name from " 
			+ "(select * from " + HTS.HTWORLD_LABEL_WORLD + " where id in" ;
	
	private static final String QUERY_ACTIVITY_WORLD_CHECK_FOOT = " ) as aw," + HTS.HTWORLD_LABEL 
			+ " as a," + HTS.USER_INFO + " as u where aw.label_id=a.id and aw.user_id=u.id";
	
	
	@Override
	public List<OpActivityWorldDto> queryActivityWorldDto(
			Integer activityId, LinkedHashMap<String, Object> attrMap, 
			RowSelection rowSelection) {
		String sql = QUERY_ACTIVITY_WORLD_HEAD + buildQueryActivityWorldSelection(attrMap) 
				+ QUERY_ACTIVITY_WORLD_FOOT;
		List<Object> args = new ArrayList<Object>();
		args.add(activityId);
		CollectionUtil.collectMapValues(args, attrMap);
		args.add(rowSelection.getFirstRow());
		args.add(rowSelection.getLimit());
		return getJdbcTemplate().query(sql, args.toArray(), new RowMapper<OpActivityWorldDto>() {

			@Override
			public OpActivityWorldDto mapRow(ResultSet rs,
					int rowNum) throws SQLException {
				OpActivityWorldDto dto = buildActivityWorldDto(rs);
				dto.setAwardId(rs.getInt("award_id"));
				dto.setAwardName(rs.getString("award_name"));
				dto.setAwardThumbPath(rs.getString("icon_thumb_path"));
				dto.setIsWinner(rs.getObject("iswinner"));
				return dto;
			}
			
		});
	}
	@Override
	public List<OpActivityWorldDto> queryActivityWorldDto(
			Integer maxId, Integer activityId, LinkedHashMap<String, Object> attrMap, 
			RowSelection rowSelection) {
		String sql = QUERY_ACTIVITY_WORLD_HEAD + " and lw0.id<=? " + buildQueryActivityWorldSelection(attrMap) 
				+ QUERY_ACTIVITY_WORLD_FOOT;
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
				OpActivityWorldDto dto = buildActivityWorldDto(rs);
				dto.setAwardId(rs.getInt("award_id"));
				dto.setAwardName(rs.getString("award_name"));
				dto.setAwardThumbPath(rs.getString("icon_thumb_path"));
				dto.setIsWinner(rs.getObject("iswinner"));
				return dto;
			}
		});
	}
	
	@Override
	public long queryActivityWorldCount(Integer maxId, Integer activityId, 
			LinkedHashMap<String, Object> attrMap) {
		String sql = QUERY_ACTIVITY_WORLD_COUNT_BY_MAX_ID + buildQueryActivityWorldSelection(attrMap);
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
		if(attrMap.get("valid") != null) {
			builder.append(" and lw0.valid=? ");
		}
		Object weight = attrMap.get("weight");
		if(weight != null) {
			if(weight.equals(Tag.FALSE)) {
				builder.append(" and lw0.weight=? ");
			} else {
				builder.append(" and lw0.weight>? ");
			}
		}
		if(attrMap.get("world_id") != null) {
			builder.append(" and h0.id=? ");
		}
		if(attrMap.get("author_id") != null){
			builder.append(" and h0.author_id=? ");
		}
		if(attrMap.get("user_name") != null){
			builder.append(" and u0.user_name like ? ");
		}
		return builder.toString();
	}
	
	@Override
	public List<OpActivityWorldCheckDto> queryWorldCheck(Integer[] ids) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = QUERY_ACTIVITY_WORLD_CHECK_HEAD + inSelection + QUERY_ACTIVITY_WORLD_CHECK_FOOT;
		return getJdbcTemplate().query(sql, ids, new RowMapper<OpActivityWorldCheckDto>() {

			@Override
			public OpActivityWorldCheckDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return new OpActivityWorldCheckDto(rs.getInt("id"), 
						rs.getInt("label_id"),
						rs.getString("label_name"),
						rs.getInt("world_id"), 
						rs.getInt("user_id"), 
						rs.getString("user_name"));
			}
			
		});
	}
	
	public OpActivityWorldDto buildActivityWorldDto(ResultSet rs) throws SQLException {
		OpActivityWorldDto dto = new OpActivityWorldDto(
				rs.getInt("world_id"),
				rs.getString("short_link"),
				rs.getString("world_name"),
				rs.getString("world_desc"),
				rs.getString("world_label"),
				rs.getString("world_type"),
				rs.getInt("type_id"),
				(Date)rs.getObject("date_added"),
				(Date)rs.getObject("date_modified"),
				rs.getInt("author_id"),
				rs.getString("user_name"),
				rs.getString("user_avatar"),
				rs.getInt("star"),
				rs.getInt("trust"),
				rs.getInt("click_count"),
				rs.getInt("like_count"),
				rs.getInt("comment_count"),
				rs.getInt("keep_count"),
				rs.getString("cover_path"),
				rs.getString("title_path"),
				rs.getString("title_thumb_path"),
				rs.getDouble("longitude"),
				rs.getDouble("latitude"),
				rs.getString("location_desc"),
				rs.getString("location_addr"),
				rs.getInt("phone_code"),
				rs.getString("province"),
				rs.getString("city"),
				rs.getInt("size"),
				rs.getInt("child_count"),
				rs.getInt("activity_id"),
				rs.getInt("activity_world_id"),
				rs.getInt("lw_valid"),
				rs.getInt("serial"),
				rs.getInt("weight"));
		dto.setWorldURL(urlPrefix + dto.getShortLink());
		return dto;
		
	}
}

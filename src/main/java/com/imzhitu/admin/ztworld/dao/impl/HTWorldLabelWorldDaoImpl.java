package com.imzhitu.admin.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldLabelWorld;
import com.hts.web.common.util.CollectionUtil;
import com.imzhitu.admin.common.pojo.OpActivityWorldValidDto;
import com.imzhitu.admin.common.pojo.ZTWorldDto;
import com.imzhitu.admin.common.pojo.ZTWorldLabelWorldDto;
import com.imzhitu.admin.ztworld.dao.HTWorldLabelWorldDao;

@Repository
public class HTWorldLabelWorldDaoImpl extends BaseDaoImpl implements HTWorldLabelWorldDao {

	private static String table = HTS.HTWORLD_LABEL_WORLD;
	
	private static final String ORDER_BY_H0ID_LW0WEIGHT_DESC = " order by h0.id desc, lw0.weight desc";
	
	/**
	 * 保存标签织图
	 */
	private static final String SAVE_LABEL_WORLD = "insert into " 
			+ table + " (id,world_id, user_id,label_id, valid,serial,weight) values (?,?,?,?,?,?,?)";
	
	/**
	 * 删除标签织图
	 */
	private static final String DELETE_LABEL_WORLD_BY_WID = "delete from " 
			+ table + " where world_id=?";
	
	/**
	 * 根据织图id查询标签
	 */
	private static final String QUERY_LABEL_BY_WID = "select label_id from " 
			+ table + " where world_id=?";
	
	/**
	 * 根据织图id查询未被拒绝的标签id
	 */
	private static final String QUERY_LABEL_ID_BY_WID_WITHOUT_REJECT = "select label_id from " 
			+ table + " where world_id=? and valid<2";
	
	/**
	 * 根据标签ids删除标签织图
	 */
	private static final String DELETE_LABEL_WORLD_BY_LIDS = "delete from " 
			+ table + " where label_id in ";
	
	
	/**
	 * 根据标签查询织图公用SQL
	 */
	private static final String QUERY_WORLD_BY_LABEL_ID_HEAD = "select lw0.*,h0.*," + U0_INFO + " from "
			+ table + " as lw0, " + HTS.HTWORLD_HTWORLD + " as h0, " + HTS.USER_INFO + " as u0 "
			+ " where lw0.world_id=h0.id and h0.author_id=u0.id and h0.valid=1 and h0.shield=0"
			+ " and lw0.label_id=?";
	
	/**
	 * 根据标签查询织图总数SQL
	 */
	private static final String QUERY_WORLD_COUNT_BY_LABEL_ID_HEAD = "select count(*) from "
			+ table + " as lw0, " + HTS.HTWORLD_HTWORLD + " as h0"
			+ " where lw0.world_id=h0.id and h0.valid=1 and h0.shield=0"
			+ " and lw0.label_id=?";
	
	/**
	 * 根据标签查询最大织图id
	 */
	private static final String QUERY_MAX_ID = "select MAX(id) from " + table;;
	
	/**
	 * 根据织图ids更新有效性
	 */
	private static final String UPDATE_VALID_BY_WIDS = "update " + table 
			+ " set valid=? where world_id in ";
	
	
	/**
	 * 根据id查询标签织图信息
	 */
	private static final String QUERY_WIDS_BY_WIDS = "select lw.world_id,lw.valid from "
			+ table + " as lw," + HTS.HTWORLD_LABEL + " as lb"
			+ " where lb.id=lw.label_id and lb.label_state=? and lw.world_id in ";
	
	/**
	 * 查询标签织图
	 */
	private static final String QUERY_LABEL_WORLD_BY_LABEL_AND_WORLD_ID = "select DISTINCT * from " + table
			+ " where label_id=? and world_id=?";
	
	/**
	 * 更新标签织图有效性
	 */
	private static final String UPDATE_LABEL_WORLD_VALID_BY_ID = "update " + table
			+ " set valid=? where id=?";
	
	/**
	 * 更新标签织图加精
	 */
	private static final String UPDATE_LABEL_WORLD_SUPERB_BY_ID = "update " + table
			+ " set superb=? where id=?";
	
	/**
	 * 更新标签织图序号
	 */
	private static final String UPDATE_LABEL_WORLD_SERIAL = "update " + table
			+ " set serial=? where id=?";
	
	/**
	 * 更新htworld_htworld里的labelName
	 */
	private static final String UPDATE_LABEL_NAME_ON_HTWORLD_HTWORLD = " update " + HTS.HTWORLD_HTWORLD
			+ " set world_label=? where id=?";
	
	@Override
	public void deleteByWorldId(Integer worldId) {
		getMasterJdbcTemplate().update(DELETE_LABEL_WORLD_BY_WID, new Object[]{worldId});
	}

	@Override
	public void saveLabelWorld(HTWorldLabelWorld labelWorld) {
		getMasterJdbcTemplate().update(SAVE_LABEL_WORLD, new Object[]{
			labelWorld.getId(),
			labelWorld.getWorldId(),
			labelWorld.getUserId(),
			labelWorld.getLabelId(),
			labelWorld.getValid(),
			labelWorld.getSerial(),
			labelWorld.getWeight()
		});
	}

	@Override
	public List<Integer> queryLabelIds(Integer worldId) {
		try{
			return getJdbcTemplate().queryForList(QUERY_LABEL_BY_WID, new Object[]{worldId}, Integer.class);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public void deleteByLabelIds(Integer[] labelIds) {
		String inSelection = SQLUtil.buildInSelection(labelIds);
		String sql = DELETE_LABEL_WORLD_BY_LIDS + inSelection;
		getMasterJdbcTemplate().update(sql, (Object[])labelIds);
	}

	@Override
	public List<ZTWorldLabelWorldDto> queryLabelWorld(Integer labelId, LinkedHashMap<String, Object> attrMap,
			RowSelection rowSelection) {
		String selection = buildQueryLabelWorldSelection(attrMap);
		String sql = QUERY_WORLD_BY_LABEL_ID_HEAD + selection + ORDER_BY_H0ID_LW0WEIGHT_DESC;
		List<Object> args = new ArrayList<Object>();
		args.add(labelId);
		CollectionUtil.collectMapValues(args, attrMap);
		
		return queryForPage(sql, args.toArray(), new RowMapper<ZTWorldLabelWorldDto>() {

					@Override
					public ZTWorldLabelWorldDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildLabelWorldDto(rs);
					}
		}, rowSelection);
	}

	@Override
	public List<ZTWorldLabelWorldDto> queryLabelWorld(Integer maxId, Integer labelId,
			LinkedHashMap<String, Object> attrMap, RowSelection rowSelection) {
		String selection = buildQueryLabelWorldSelection(attrMap);
		String sql = QUERY_WORLD_BY_LABEL_ID_HEAD + " and lw0.id<=? " + selection + ORDER_BY_H0ID_LW0WEIGHT_DESC;
		List<Object> args = new ArrayList<Object>();
		args.add(labelId);
		args.add(maxId);
		CollectionUtil.collectMapValues(args, attrMap);
		return queryForPage(sql, args.toArray(), new RowMapper<ZTWorldLabelWorldDto>() {

					@Override
					public ZTWorldLabelWorldDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildLabelWorldDto(rs);
					}
		}, rowSelection);
	}

	@Override
	public long queryLabelWorldCount(Integer maxId, Integer labelId, LinkedHashMap<String, Object> attrMap) {
		String selection = buildQueryLabelWorldSelection(attrMap);
		String sql = QUERY_WORLD_COUNT_BY_LABEL_ID_HEAD + " and lw0.id<=? " + selection;
		List<Object> args = new ArrayList<Object>();
		args.add(labelId);
		args.add(maxId);
		CollectionUtil.collectMapValues(args, attrMap);
		return getJdbcTemplate().queryForLong(sql, args.toArray());
	}
	
	@Override
	public long queryLabelWorldCount(Integer labelId, LinkedHashMap<String, Object> attrMap) {
		String selection = buildQueryLabelWorldSelection(attrMap);
		String sql = QUERY_WORLD_COUNT_BY_LABEL_ID_HEAD + selection;
		List<Object> args = new ArrayList<Object>();
		args.add(labelId);
		CollectionUtil.collectMapValues(args, attrMap);
		return getJdbcTemplate().queryForLong(sql, args.toArray());
	}
	
	
	/**
	 * 构建标签织图查询条件
	 * 
	 * @param attrMap
	 * @return
	 */
	private String buildQueryLabelWorldSelection(Map<String, Object> attrMap) {
		StringBuilder builder = new StringBuilder();
		if(attrMap.get("valid") != null) {
			builder.append(" and lw0.valid=? ");
		}
		return builder.toString();
	}
	
	@Override
	public void updateLabelWorldValid(Integer[] worldIds, Integer valid) {
		String selection = SQLUtil.buildInSelection(worldIds);
		String sql = UPDATE_VALID_BY_WIDS + selection;
		Object[] args = SQLUtil.getArgsByInCondition(worldIds, new Object[]{valid}, true);
		getMasterJdbcTemplate().update(sql, args);
	}
	
	@Override
	public Integer queryMaxId() {
		return getJdbcTemplate().queryForInt(QUERY_MAX_ID);
	}
	
	@Override
	public void queryWorldIds(Integer[] worldIds, Integer labelState,
			final RowCallback<OpActivityWorldValidDto> callback) {
		String inSelection = SQLUtil.buildInSelection(worldIds);
		String sql = QUERY_WIDS_BY_WIDS + inSelection;
		Object[] args = SQLUtil.getArgsByInCondition(worldIds, new Object[]{labelState}, true);
		getJdbcTemplate().query(sql, args, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(new OpActivityWorldValidDto(
						rs.getInt("world_id"),
						rs.getInt("valid")));
			}
		});
	}
	
	@Override
	public List<Integer> queryLabelIdsWithoutReject(Integer worldId) {
		return getJdbcTemplate().queryForList(QUERY_LABEL_ID_BY_WID_WITHOUT_REJECT,
				new Object[]{worldId}, Integer.class);
	}
	
	@Override
	public HTWorldLabelWorld queryLabelWorld(Integer worldId, Integer labelId) {
		return queryForObjectWithNULL(QUERY_LABEL_WORLD_BY_LABEL_AND_WORLD_ID,
				new Object[]{labelId, worldId}, new RowMapper<HTWorldLabelWorld>() {

					@Override
					public HTWorldLabelWorld mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildLabelWorld(rs);
					}
				});
	}
	
	@Override
	public void updateLabelNameInHtworldHtworld(Integer worldId,String labelStr){
		getMasterJdbcTemplate().update(UPDATE_LABEL_NAME_ON_HTWORLD_HTWORLD, labelStr,worldId);
	}
	
	@Override
	public void updateLabelWorldValid(Integer id, Integer valid) {
		getMasterJdbcTemplate().update(UPDATE_LABEL_WORLD_VALID_BY_ID, new Object[]{valid,id});
	}
	
	@Override
	public void updateLabelWorldSuperb(Integer id, Integer superb) {
		getMasterJdbcTemplate().update(UPDATE_LABEL_WORLD_SUPERB_BY_ID, new Object[]{superb,id});
	}
	
	@Override
	public void updateLabelWorldSerial(Integer id, Integer serial) {
		getMasterJdbcTemplate().update(UPDATE_LABEL_WORLD_SERIAL, new Object[]{serial, id});
	}

	public HTWorldLabelWorld buildLabelWorld(ResultSet rs) throws SQLException {
		return new HTWorldLabelWorld(
				rs.getInt("id"),
				rs.getInt("world_id"),
				rs.getInt("user_id"),
				rs.getInt("label_id"),
				rs.getInt("valid"),
				rs.getInt("serial"),
				rs.getInt("weight"));
	}
	
	/**
	 * 构建{@link ZTWorldDto}
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public ZTWorldLabelWorldDto buildLabelWorldDto(ResultSet rs) throws SQLException {
		ZTWorldLabelWorldDto dto = new ZTWorldLabelWorldDto(
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
				rs.getInt("latest_valid"),
				rs.getInt("id"),
				rs.getInt("label_id"),
				rs.getInt("valid"),
				rs.getInt("weight"));
		
		if(dto.getShortLink() != null) {
			dto.setWorldURL(urlPrefix + dto.getShortLink()); 
		} else {
			dto.setWorldURL(urlPrefix + dto.getId());
		}
		return dto;
	}


}

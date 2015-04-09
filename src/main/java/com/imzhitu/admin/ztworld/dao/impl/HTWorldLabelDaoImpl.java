package com.imzhitu.admin.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldLabel;
import com.hts.web.common.util.CollectionUtil;
import com.imzhitu.admin.ztworld.dao.HTWorldLabelDao;

@Repository
public class HTWorldLabelDaoImpl extends BaseDaoImpl implements HTWorldLabelDao {

	private static final String table = HTS.HTWORLD_LABEL;
	
	/**
	 * 查询标签公用SQL
	 */
	private static final String QUERY_LABEL_HEAD = "select * from "
			+ table + " where 1=1";
	
	/**
	 * 根据状态查询标签
	 */
	private static final String QUERY_LABEL_BY_STATE = QUERY_LABEL_HEAD + " and label_state=?"
			+ ORDER_BY_SERIAL_DESC;
	
	/**
	 * 查询标签总数公用SQL
	 */
	private static final String QUERY_LABEL_COUNT_HEAD = "select count(*) from "
			+ table + " where 1=1";
	
	/**
	 * 查询指定分类下的所有标签
	 */
	private static final String QUERY_ALL_LABEL_BY_TYPE_ID = "select * from " 
			+ table + " where type_id=? and valid=? order by serial desc";
	
	/**
	 * 更新序号
	 */
	private static final String UPDATE_SERIAL = "update " + table
			+ " set serial=? where id=?";
	
	/**
	 * 更新织图总数
	 */
	private static final String UPDATE_WORLD_COUNT = "update " + table 
			+ " set world_count=? where id=?";
	
	/**
	 * 更新标签属性
	 */
	private static final String UPDATE_LABEL_STATE = "update " + table
			+ " set label_state=? where id=?";
	
	/**
	 * 根据ids查询标签
	 */
	private static final String QUERY_LABEL_BY_IDS = "select * from " + table
			+ " where id in ";

	@Autowired
	private com.hts.web.ztworld.dao.HTWorldLabelDao webWorldLabelDao;
	

	@Override
	public void saveLabel(HTWorldLabel label) {
		webWorldLabelDao.saveLabel(label);
	}

	@Override
	public List<HTWorldLabel> queryLabel(LinkedHashMap<String, Object> attrMap, String orderKey,
			String order, RowSelection rowSelection) {
		String selection = buildQueryLabelSelection(attrMap);
		String sql = QUERY_LABEL_HEAD + selection + " order by " + orderKey + " " + order;
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		
		return queryForPage(sql, argsList.toArray(), new RowMapper<HTWorldLabel>() {

			@Override
			public HTWorldLabel mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return webWorldLabelDao.buildLabel(rs);
			}
		}, rowSelection);
	}

	@Override
	public List<HTWorldLabel> queryLabel(Integer maxSerial, LinkedHashMap<String, Object> attrMap,
			String orderKey, String order, RowSelection rowSelection) {
		String selection = buildQueryLabelSelection(attrMap);
		String sql = QUERY_LABEL_HEAD + selection + " and serial<=?" + " order by " + orderKey + " " + order;
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		argsList.add(maxSerial);
		
		return queryForPage(sql, argsList.toArray(), new RowMapper<HTWorldLabel>() {

			@Override
			public HTWorldLabel mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return webWorldLabelDao.buildLabel(rs);
			}
		}, rowSelection);
	}

	@Override
	public long queryLabelCount(Integer maxSerial, LinkedHashMap<String, Object> attrMap) {
		String selection = buildQueryLabelSelection(attrMap);
		String sql = QUERY_LABEL_COUNT_HEAD + selection + " and serial<=?";
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		argsList.add(maxSerial);
		
		return getJdbcTemplate().queryForLong(sql, argsList.toArray());
	}
	
	/**
	 * 构建标签查询条件
	 * 
	 * @param attrMap
	 * @return
	 */
	private String buildQueryLabelSelection(LinkedHashMap<String, Object> attrMap) {
		StringBuilder build = new StringBuilder();
		if(attrMap.get("label_state") != null) {
			build.append(" and label_state=? ");
		}
		if(attrMap.get("label_name") != null) {
			build.append(" and label_name like ? ");
		}
		return build.toString();
	}
	
	@Override
	public List<HTWorldLabel> queryAllLabelByTypeId(Integer typeId) {
		return getJdbcTemplate().query(QUERY_ALL_LABEL_BY_TYPE_ID, new Object[]{typeId, Tag.TRUE}, 
				new RowMapper<HTWorldLabel>() {

					@Override
					public HTWorldLabel mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return webWorldLabelDao.buildLabel(rs);
					}
		});
	}

	@Override
	public void deleteByIds(Integer[] ids) {
		deleteByIds(table, ids);
	}

	@Override
	public void updateSerial(Integer id, Integer serial) {
		getMasterJdbcTemplate().update(UPDATE_SERIAL, new Object[]{serial, id});
	}

	@Override
	public List<HTWorldLabel> queryLabel(Integer labelState, RowSelection rowSelection) {
		return queryForPage(QUERY_LABEL_BY_STATE, new Object[]{labelState},
				new RowMapper<HTWorldLabel>() {

			@Override
			public HTWorldLabel mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return webWorldLabelDao.buildLabel(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public void updateWorldCount(Integer id, Integer worldCount) {
		getMasterJdbcTemplate().update(UPDATE_WORLD_COUNT, new Object[]{worldCount, id});
	}

	@Override
	public void updateLabelState(Integer id, Integer state) {
		getMasterJdbcTemplate().update(UPDATE_LABEL_STATE, new Object[]{state, id});
	}

	@Override
	public HTWorldLabel queryLabelByName(String name) {
		return webWorldLabelDao.queryLabelByName(name);
	}

	@Override
	public Map<Integer, HTWorldLabel> queryLabelMap(Integer[] ids) {
		final Map<Integer, HTWorldLabel> map = new HashMap<Integer, HTWorldLabel>();
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = QUERY_LABEL_BY_IDS + inSelection;
		getJdbcTemplate().query(sql, (Object[])ids, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				HTWorldLabel label = webWorldLabelDao.buildLabel(rs);
				map.put(label.getId(), label);
			}
		});
		return map;
	}

}

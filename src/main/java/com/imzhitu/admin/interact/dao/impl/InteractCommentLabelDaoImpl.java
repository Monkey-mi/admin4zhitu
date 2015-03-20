package com.imzhitu.admin.interact.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.InteractCommentLabel;
import com.imzhitu.admin.common.pojo.InteractCommentLabelTree;
import com.imzhitu.admin.interact.dao.InteractCommentLabelDao;

@Repository
public class InteractCommentLabelDaoImpl extends BaseDaoImpl implements InteractCommentLabelDao {

	private static String table = Admin.INTERACT_COMMENT_LABEL;
	
	private static final String SAVE_LABEL = "insert into " + table + " (label_name, group_id) values (?,?)";
	
	private static final String QUERY_LABEL_HEAD = "select * from " + table;
	
	/**
	 * 查询标签
	 */
	private static final String QUERY_LABEL = QUERY_LABEL_HEAD + " where group_id=?" + ORDER_BY_ID_DESC;
	
	/**
	 * 根据最大id查询标签
	 */
	private static final String QUERY_LABEL_BY_MAX_ID = QUERY_LABEL_HEAD + " where group_id=? and id<=?" + ORDER_BY_ID_DESC;
	
	/**
	 * 查询标签总数
	 */
	private static final String QUERY_LABEL_COUNT = "select count(*) from " + table;
	
	/**
	 * 根据最大id查询标签总数
	 */
	private static final String QUERY_LABEL_COUNT_BY_MAX_ID = QUERY_LABEL_COUNT + " where group_id=? and id<=?";
	
	private static final String QUERY_ALL_LABEL_GROUP = QUERY_LABEL_HEAD + " where group_id=0";
	
	/**
	 * 查询标签分组
	 */
	private static final String QUERY_LABEL_GROUP = QUERY_LABEL_HEAD + " where group_id=0" + ORDER_BY_ID_DESC;
	
	/**
	 * 根据最大id查询标签分组
	 */
	private static final String QUERY_LABEL_GROUP_BY_MAX_ID = QUERY_LABEL_HEAD + " where group_id=0 and id<=?" + ORDER_BY_ID_DESC;
	
	/**
	 * 查询标签分组总数
	 */
	private static final String QUERY_LABEL_GROUP_COUNT = "select count(*) from " + table + " where group_id=0";
	
	/**
	 * 根据最大id查询标签分组总数
	 */
	private static final String QUERY_LABEL_GROUP_COUNT_BY_MAX_ID = QUERY_LABEL_GROUP_COUNT + " and id<=?";
	
	/**
	 * 更新标签
	 */
	private static final String UPDATE_LABEL_BY_ID = "update " + table + " set label_name=?, group_id=? where id=?";
	
	/**
	 * 根据分组id删除标签
	 */
	private static final String DELETE_BY_GROUP_ID = "delete from " + table + " where group_id=?";
	
	/**
	 * 查询是否存在该名字的标签
	 */
	private static final String CHECK_EXSIST_BY_LABEL_NAME = "select count(*) from " + table + " where label_name=?";
	
	/**
	 * 查询是否存在该名字的标签
	 * true 存在
	 * false 不存在
	 */
	public boolean checkLabelExsistByLabelName(String labelName){
		long r = getJdbcTemplate().queryForLong(CHECK_EXSIST_BY_LABEL_NAME, labelName);
		return r>0;
	}
	
	
	
	@Override
	public void saveLabel(InteractCommentLabel label) {
		getJdbcTemplate().update(SAVE_LABEL, new Object[]{
			label.getLabelName(),
			label.getGroupId()
		});
	}
	
	@Override
	public List<InteractCommentLabel> queryLabel() {
		return getJdbcTemplate().query(QUERY_LABEL_HEAD, new RowMapper<InteractCommentLabel>(){

			@Override
			public InteractCommentLabel mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildLabelByRs(rs);
			}
		});
	}
	
	@Override
	public List<InteractCommentLabel> queryLabel(Integer groupId, RowSelection rowSelection) {
		return queryForPage(QUERY_LABEL, new Object[]{groupId}, new RowMapper<InteractCommentLabel>() {

			@Override
			public InteractCommentLabel mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildLabelByRs(rs);
			}
			
		},rowSelection);
	}

	@Override
	public List<InteractCommentLabel> queryLabel(Integer maxId, Integer groupId, 
			RowSelection rowSelection) {
		return queryForPage(QUERY_LABEL_BY_MAX_ID, new Object[]{groupId, maxId}, new RowMapper<InteractCommentLabel>() {

			@Override
			public InteractCommentLabel mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildLabelByRs(rs);
			}
			
		},rowSelection);
	}

	@Override
	public long queryLabelCount(Integer maxId, Integer groupId) {
		return getJdbcTemplate().queryForLong(QUERY_LABEL_COUNT_BY_MAX_ID, new Object[]{groupId, maxId});
	}
	

	@Override
	public List<InteractCommentLabel> queryLabelGroup() {
		return getJdbcTemplate().query(QUERY_ALL_LABEL_GROUP, new RowMapper<InteractCommentLabel>(){

			@Override
			public InteractCommentLabel mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				InteractCommentLabel label = buildLabelByRs(rs);
				if(label.getId().equals(1)) {
					label.setSelected(true);
				}
				return label;
			}
		});
	}
	
	@Override
	public List<InteractCommentLabel> queryLabelGroup(RowSelection rowSelection) {
		return queryForPage(QUERY_LABEL_GROUP, new RowMapper<InteractCommentLabel>(){

			@Override
			public InteractCommentLabel mapRow(ResultSet rs, int num)
					throws SQLException {
				return buildLabelByRs(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public List<InteractCommentLabel> queryLabelGroup(Integer maxId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_LABEL_GROUP_BY_MAX_ID, new Object[]{maxId},
				new RowMapper<InteractCommentLabel>(){

			@Override
			public InteractCommentLabel mapRow(ResultSet rs, int num)
					throws SQLException {
				return buildLabelByRs(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public long queryLabelGroupCount(Integer maxId) {
		return getJdbcTemplate().queryForLong(QUERY_LABEL_GROUP_COUNT_BY_MAX_ID, new Object[]{maxId});
	}
	
	@Override
	public void updateLabel(Integer id, String labelName, Integer groupId) {
		getJdbcTemplate().update(UPDATE_LABEL_BY_ID, new Object[]{labelName, groupId, id});
	}
	
	@Override
	public void deleteByGroupId(Integer groupId) {
		getJdbcTemplate().update(DELETE_BY_GROUP_ID, new Object[]{groupId});
	}
	

	@Override
	public List<InteractCommentLabelTree> queryLabelGroupTree(final Integer selectId) {
		return getJdbcTemplate().query(QUERY_ALL_LABEL_GROUP, new RowMapper<InteractCommentLabelTree>(){

			@Override
			public InteractCommentLabelTree mapRow(ResultSet rs, int num)
					throws SQLException {
				InteractCommentLabelTree label = buildLabelTree(false, rs);
				if(label.getId().equals(selectId)) {
					label.setChecked(true);
				}
				return label;
			}
			
		});
	}

	@Override
	public List<InteractCommentLabelTree> queryLabelTree(Integer groupId, final Integer selectId) {
		return getJdbcTemplate().query(QUERY_LABEL, new Object[]{groupId},
				new RowMapper<InteractCommentLabelTree>(){

			@Override
			public InteractCommentLabelTree mapRow(ResultSet rs, int num)
					throws SQLException {
				InteractCommentLabelTree label = buildLabelTree(true, rs);
				if(label.getId().equals(selectId)) {
					label.setChecked(true);
				}
				return label;
			}
		});
	}
	
	/**
	 * 根据结果集构建标签
	 * 
	 * @param rs
	 * @throws SQLException 
	 */
	public InteractCommentLabel buildLabelByRs(ResultSet rs) throws SQLException {
		return new InteractCommentLabel(
				rs.getInt("id"),
				rs.getString("label_name"),
				rs.getInt("group_id"));
	}
	
	/**
	 * 构建标签书树
	 * @param open
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public InteractCommentLabelTree buildLabelTree(boolean open, ResultSet rs) throws SQLException {
		String state = (open ? "opend" : "closed");
		InteractCommentLabelTree label = new InteractCommentLabelTree(
				rs.getInt("id"),
				rs.getString("label_name"),
				state);
		return label;
	}


}

package com.imzhitu.admin.interact.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.InteractComment;
import com.imzhitu.admin.interact.dao.InteractCommentDao;

@Repository
public class InteractCommentDaoImpl extends BaseDaoImpl implements InteractCommentDao {

	public static String table = Admin.INTERACT_COMMENT;
	
	private static final String SAVE_COMMENT = "insert into " + table + " (id,content,label_id) values (?,?,?)";

	private static final String QUERY_ID_BY_PAGE_INDEX = "select DISTINCT id from " + table + " where label_id=? and valid=? LIMIT ?,1";
	
	private static final String QUERY_COMMENT = "select * from " + table + " where valid=?";
	
	private static final String QUERY_COMMENT_BY_LABEL = QUERY_COMMENT + " and label_id=?";
	
	private static final String QUERY_COMMENT_TOTAL = "select count(*) from " + table + " where valid=?";
	
	private static final String QUERY_COMMENT_TOTAL_BY_LABEL = QUERY_COMMENT_TOTAL + " and label_id=?";
	
	private static final String UPDATE_COMMENT_VALID_IDS = "update " + table + " set valid=0 where id in ";
	
	private static final String ADD_COUNT = "update " + table + " set count=count+? where id=?";
	
	private static final String QUERY_COMMENT_BY_ID = "select * from " + table + " where id=?";
	
	private static final String UPDATE_COMMENT_BY_ID = "update " + table + " set content=?, label_id=? where id=?";
	
	private static final String UPDATE_COMMENT_CONTENT_BY_ID = "update " + table + " set content=? where id=?";
	
	@Override
	public void saveComment(InteractComment comment) {
		getMasterJdbcTemplate().update(SAVE_COMMENT, new Object[]{
			comment.getId(),
			comment.getContent(),
			comment.getLabelId()
		});
	}

	@Override
	public List<InteractComment> queryComment(Integer labelId, String comment, RowSelection rowSelection) {
		String sql = null;
		Object[] args = null;
		boolean hasLabel = (labelId != null && !labelId.equals(0));
		if(!hasLabel)
			sql = QUERY_COMMENT;
		else 
			sql = QUERY_COMMENT_BY_LABEL;
		if(comment != null && !comment.equals("")) {
			sql = sql + " and content like ?" + ORDER_BY_ID_DESC;
			if(!hasLabel) {
				args = new Object[]{Tag.TRUE, "%"+comment+"%"};
			} else {
				args = new Object[]{Tag.TRUE, labelId, "%"+comment+"%"};
			}
		} else {
			sql = sql + ORDER_BY_ID_DESC;
			if(!hasLabel) {
				args = new Object[]{Tag.TRUE};
			} else {
				args = new Object[]{Tag.TRUE, labelId};
			}
		}
		return queryForPage(sql, args, new RowMapper<InteractComment>(){

			@Override
			public InteractComment mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildWorldCommentByResultSet(rs);
			}
			
		},rowSelection);
	}

	@Override
	public List<InteractComment> queryComment(Integer labelId, String comment, Integer maxId,
			RowSelection rowSelection) {
		String sql = null;
		Object[] args = null;
		boolean hasLabel = (labelId != null && !labelId.equals(0));
		if(!hasLabel)
			sql = QUERY_COMMENT;
		else 
			sql = QUERY_COMMENT_BY_LABEL;
		if(comment != null && !comment.equals("")) {
			sql = sql + " and id<=? and content like ?" + ORDER_BY_ID_DESC;
			if(!hasLabel) {
				args = new Object[]{Tag.TRUE, maxId, "%"+comment+"%"};
			} else {
				args = new Object[]{Tag.TRUE, labelId, maxId, "%"+comment+"%"};
			}
		} else {
			sql = sql + " and id<=?" + ORDER_BY_ID_DESC;
			if(!hasLabel) {
				args = new Object[]{Tag.TRUE, maxId};
			} else {
				args = new Object[]{Tag.TRUE, labelId, maxId};
			}
		}
		return queryForPage(sql, args, new RowMapper<InteractComment>(){

			@Override
			public InteractComment mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildWorldCommentByResultSet(rs);
			}
			
		},rowSelection);
	}

	@Override
	public long queryCommentTotal(Integer labelId) {
		String sql = null;
		Object[] args = null;
		boolean hasLabel = (labelId != null && !labelId.equals(0));
		if(!hasLabel)
			sql = QUERY_COMMENT_TOTAL;
		else 
			sql = QUERY_COMMENT_TOTAL_BY_LABEL;
		if(!hasLabel) {
			args = new Object[]{Tag.TRUE};
		} else {
			args = new Object[]{Tag.TRUE, labelId};
		}
		return getJdbcTemplate().queryForLong(sql, args);
	}
	
	@Override
	public long queryCommentTotal(Integer labelId, String comment, Integer maxId) {
		String sql = null;
		Object[] args = null;
		boolean hasLabel = (labelId != null && !labelId.equals(0));
		if(!hasLabel)
			sql = QUERY_COMMENT_TOTAL;
		else 
			sql = QUERY_COMMENT_TOTAL_BY_LABEL;
		if(comment != null && !comment.equals("")) {
			sql = sql + " and id<=? and content like ?";
			if(!hasLabel) {
				args = new Object[]{Tag.TRUE, maxId, "%"+comment+"%"};
			} else {
				args = new Object[]{Tag.TRUE, labelId, maxId, "%"+comment+"%"};
			}
		} else {
			sql = sql + " and id<=?";
			if(!hasLabel) {
				args = new Object[]{Tag.TRUE, maxId};
			} else {
				args = new Object[]{Tag.TRUE, labelId, maxId};
			}
		}
		return getJdbcTemplate().queryForLong(sql, args);
	}
	
	@Override
	public Integer queryIdByPageIndex(int labelId, int page) {
		return getJdbcTemplate().queryForObject(QUERY_ID_BY_PAGE_INDEX, new Object[]{labelId, Tag.TRUE, page}, Integer.class);
	}
	
	/**
	 * 从结果集构建WorldComment
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public InteractComment buildWorldCommentByResultSet(ResultSet rs) throws SQLException {
		return new InteractComment(
				rs.getInt("id"),
				rs.getString("content"),
				rs.getInt("count"),
				rs.getInt("label_id"));
	}

	@Override
	public void deleteByIds(Integer[] ids) {
		String sql = UPDATE_COMMENT_VALID_IDS + SQLUtil.buildInSelection(ids);
		getMasterJdbcTemplate().update(sql, (Object[])ids);
	}
	
	@Override
	public void addCount(Integer id, Integer count) {
		getMasterJdbcTemplate().update(ADD_COUNT, new Object[]{count, id});
	}

	@Override
	public InteractComment queryCommentById(Integer id) {
		return queryForObjectWithNULL(QUERY_COMMENT_BY_ID, new Object[]{id}, new RowMapper<InteractComment>() {

			@Override
			public InteractComment mapRow(ResultSet rs, int arg1)
					throws SQLException {
				return buildWorldCommentByResultSet(rs);
			}
		});
	}

	@Override
	public void updateComment(Integer id, String content, Integer labelId) {
		getMasterJdbcTemplate().update(UPDATE_COMMENT_BY_ID, new Object[]{content, labelId, id});
	}

	@Override
	public void updateCommentContentById(String content,Integer id){
		getMasterJdbcTemplate().update(UPDATE_COMMENT_CONTENT_BY_ID,content,id);
	}
}

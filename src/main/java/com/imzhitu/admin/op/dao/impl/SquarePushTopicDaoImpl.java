package com.imzhitu.admin.op.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpSquareTopic;
import com.imzhitu.admin.op.dao.SquarePushTopicDao;

/**
 * <p>
 * 广场推送数据访问对象
 * </p>
 * 
 * 创建时间：2013-9-9
 * @author ztj
 *
 */
@Repository
public class SquarePushTopicDaoImpl extends BaseDaoImpl implements
		SquarePushTopicDao {
	
	private static String table = HTS.OPERATIONS_SQUARE_PUSH_TOPIC;
	
	private static final String QUERY_TOPIC = "select * from " + table + ORDER_BY_ID_DESC;
	
	private static final String QUERY_TOPIC_COUNT = "select count(*) from " + table;
	
	private static final String DELETE_TOPIC_BY_ID = "delete from " + table + " where id=?";
	
	private static final String SAVE_TOPIC = "insert into " + table + " (topic,topic_path,topic_path_hd,date_added) values (?,?,?,?)";

	@Override
	public List<OpSquareTopic> queryTopic(RowSelection rowSelection) {
		return queryForPage(QUERY_TOPIC, new RowMapper<OpSquareTopic>(){

			@Override
			public OpSquareTopic mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildSquarePushTopicByResultSet(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public long queryTopicCount() {
		return getJdbcTemplate().queryForLong(QUERY_TOPIC_COUNT);
	}
	
	public OpSquareTopic buildSquarePushTopicByResultSet(ResultSet rs) throws SQLException {
		return new OpSquareTopic(
				rs.getInt("id"),
				rs.getString("topic"),
				rs.getString("topic_path"),
				rs.getString("topic_path_hd"),
				(Date)rs.getObject("date_added"));
	}

	@Override
	public void saveTopic(OpSquareTopic topic) {
		getJdbcTemplate().update(SAVE_TOPIC, new Object[]{
			topic.getTopic(),
			topic.getTopicPath(),
			topic.getTopicPathHd(),
			topic.getDateAdded()
		});
	}

	@Override
	public void deleteTopicById(Integer id) {
		getJdbcTemplate().update(DELETE_TOPIC_BY_ID, id);
	}

}

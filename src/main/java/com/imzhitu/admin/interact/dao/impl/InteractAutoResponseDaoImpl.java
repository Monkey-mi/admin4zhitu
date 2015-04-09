package com.imzhitu.admin.interact.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.InteractAutoResponseDto;
import com.imzhitu.admin.interact.dao.InteractAutoResponseDao;

@Repository
public class InteractAutoResponseDaoImpl extends BaseDaoImpl implements InteractAutoResponseDao{
	private static final String table = Admin.INTERACT_AUTO_RESPONSE;
	private static final String zoombie_table = Admin.HTS_OPERATIONS_USER_ZOMBIE;
	private static final String CHECK_IS_ZOOMBIE = "select count(1) from " + zoombie_table + " where user_id=?";
	private static final String ADD_RESPONSE = "insert into " + table + " (response_id,comment_id,complete,world_id,author,re_author) values (?,?,?,?,?,?)";
	private static final String QUERY_UNCOMPLETE_RESPONSE = " SELECT ar.*, hh.short_link,"
															+"	  hc2.content as context,"
															+"	  hc1.content as reContext,"
															+"	  ui.user_name as reAuthorName,"
															+"	  uz.user_name as authorName,"
															+"	  hc1.comment_date as commentDate"
														+"	from  hts_admin.interact_auto_response as ar,"
														+"	      hts.user_info as uz,"
														+"		  hts.user_info as ui,"
														+"	      hts.htworld_comment as hc1,"
														+"	      hts.htworld_comment as hc2,"
														+"		  hts.htworld_htworld as hh"		
														+"	where hc1.id = ar.comment_id"
														+"	  and hc2.id = ar.response_id"
														+"	  and uz.id = ar.author"
														+"	  and ui.id = ar.re_author"
														+"	  and ar.complete = 0"
														+"    and hh.id = ar.world_id"
														+"    and hh.valid = 1 "
														+"	ORDER BY ar.id desc";
	private static final String QUERY_UNCOMPLETE_RESPONSE_BY_MAXID = " SELECT ar.*,hh.short_link,"
															+"	  hc2.content as context,"
															+"	  hc1.content as reContext,"
															+"	  ui.user_name as reAuthorName,"
															+"	  uz.user_name as authorName,"
															+"	  hc1.comment_date as commentDate"
														+"	from  hts_admin.interact_auto_response as ar,"
														+"	      hts.user_info as uz,"
														+"		  hts.user_info as ui,"
														+"	      hts.htworld_comment as hc1,"
														+"	      hts.htworld_comment as hc2,"
														+"		  hts.htworld_htworld as hh"
														+"	where hc1.id = ar.comment_id"
														+"	  and hc2.id = ar.response_id"
														+"	  and uz.id = ar.author"
														+"	  and ui.id = ar.re_author"
														+"	  and ar.complete = 0"
														+"    and hh.id = ar.world_id"
														+"    and hh.valid = 1 "
														+"    and ar.id<=?"
														+"	ORDER BY ar.id desc";
	private static final String GET_UNCOMPLETE_RESPONSE_COUNT = " select count(1) from (select * from hts_admin.interact_auto_response where complete=0 and id<=?) as ar left join hts.htworld_htworld hh on ar.world_id=hh.id  where hh.valid=1";
	private static final String QUERY_RESPONSE_BY_ID = " SELECT ar.id,hc.id as response_id,ui.user_name as authorName,hc.content as context,hc.comment_date as commentDate "
			+ " FROM hts_admin.interact_auto_response ar,hts.htworld_comment as hc , hts.user_info as ui "
			+ " WHERE ar.id = ? and hc.world_id=ar.world_id and hc.author_id=ui.id "
			+ " AND (((hc.author_id=ar.author or hc.author_id=ar.re_author) AND (hc.re_author_id =ar.author or hc.re_author_id=ar.re_author)) or (hc.author_id=ar.author AND hc.re_author_id=0))"
			+ " order by hc.comment_date desc";
	private static final String UPDATE_COMPLETE_BY_IDS = " update " + table + " set complete=1 where id in ";
	private static final String QUERY_UNCK_RESPONSE = "SELECT "
			+ "			hc.*, ui.user_name"
			+ "			FROM"
			+ "				("
			+ "					SELECT"
			+ "						*"
			+ "					FROM"
			+ "						hts.htworld_comment"
			+ "					WHERE"
			+ "						ck = 0"
			+ "					AND valid = 1"
			+ "			    AND comment_date > ?"
					+ "				) hc"
					+ "			INNER JOIN hts.operations_user_zombie uz ON hc.re_author_id = uz.user_id"
					+ "			LEFT JOIN hts.user_info ui ON hc.author_id = ui.id";
//	private static final String QUERY_UNCK_RESPONSE = "SELECT "
//			+ "			hc.*, ui.user_name"
//			+ "			FROM"
//			+ "					hts.htworld_comment hc left join hts.operations_user_zombie uz on hc.re_author_id = uz.user_id LEFT JOIN hts.user_info ui ON hc.author_id = ui.id"
//			+ "					WHERE"
//			+ "						hc.ck = 0 "
//			+ "					AND hc.valid = 1"
//			+ "			    AND hc.comment_date > ?";
	private static final String DELETE_BY_IDS = " delete from " + table + " where id in ";
	
	/**
	 * 删除自动回复记录
	 * @param ids
	 */
	@Override
	public void delAutoResponseByIds(Integer[] ids){
		String sql = DELETE_BY_IDS + SQLUtil.buildInSelection(ids);
		getMasterJdbcTemplate().update(sql, (Object[])ids);
	}
	
	/**
	 * 查看是否是马甲
	 */
	@Override
	public boolean checkIsZoombie(Integer uid){
		long r = getJdbcTemplate().queryForLong(CHECK_IS_ZOOMBIE, uid);
		return r == 0 ? false : true;
	}
	
	/**
	 * 增加自动回复记录
	 */
	@Override
	public void addResponse(Integer responseId,Integer commentId,Integer complete,Integer world_id,Integer author, Integer re_author){
		getMasterJdbcTemplate().update(ADD_RESPONSE, responseId,commentId,complete,world_id,author,re_author);
	}
	
	/**
	 * 查询未被回复的评论列表，展示所用
	 */
	@Override
	public List<InteractAutoResponseDto> queryUncompleteResponse(RowSelection rowSelection){
		return queryForPage(QUERY_UNCOMPLETE_RESPONSE,new RowMapper<InteractAutoResponseDto>(){
			@Override
			public InteractAutoResponseDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildInteractAutoResponseDto(rs);
			}
		},rowSelection);
	}
	
	/**
	 * 查询未被回复的评论列表，展示所用
	 */
	@Override
	public List<InteractAutoResponseDto> queryUncompleteResponse(Integer maxId,RowSelection rowSelection){
		return queryForPage(QUERY_UNCOMPLETE_RESPONSE_BY_MAXID,new Object[]{maxId},new RowMapper<InteractAutoResponseDto>(){
			@Override
			public InteractAutoResponseDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildInteractAutoResponseDto(rs);
			}
		},rowSelection);
	}
	
	/**
	 * 根据id查询回复组
	 */
	@Override
	public List<InteractAutoResponseDto> queryResponseById(Integer id){
		try{
			return getJdbcTemplate().query(QUERY_RESPONSE_BY_ID, new Object[]{id},new RowMapper<InteractAutoResponseDto>(){
				@Override
				public InteractAutoResponseDto mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return buildDialog(rs);
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
		
	}
	
	/**
	 * 更新完成情况 by id
	 */
	@Override
	public void updateResponseCompleteByIds(Integer[] ids){
		String sql = UPDATE_COMPLETE_BY_IDS + SQLUtil.buildInSelection(ids);
		getMasterJdbcTemplate().update(sql, (Object[])ids);
	}
	
	/**
	 * 查询未被回复的总数
	 */
	@Override
	public long getUnCompleteResponseCountByMaxId(Integer maxId){
		return getJdbcTemplate().queryForLong(GET_UNCOMPLETE_RESPONSE_COUNT, maxId);
	}
	
	/**
	 * 查询马甲的未回复的被用户回复
	 */
	@Override
	public List<InteractAutoResponseDto> queryUnCkResponse(Date beginDate){
		try{
			return getJdbcTemplate().query(QUERY_UNCK_RESPONSE, new Object[]{beginDate},new RowMapper<InteractAutoResponseDto>(){
				@Override
				public InteractAutoResponseDto mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return buildUnCkResponse(rs);
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
		
	}
	/**
	 * 构建自动回复数据列表
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private InteractAutoResponseDto buildInteractAutoResponseDto(ResultSet rs)throws SQLException{
		return new InteractAutoResponseDto(rs.getInt("id"),
				rs.getInt("world_id"),
				rs.getInt("response_id"),
				rs.getInt("comment_id"),
				rs.getInt("complete"),
				urlPrefix + rs.getString("short_link"),
				rs.getInt("author"),
				rs.getString("authorName"),
				rs.getInt("re_author"),
				rs.getString("reAuthorName"),
				rs.getString("context"),
				rs.getString("reContext"),
				(Date)rs.getObject("commentDate"));
	}
	
	/**
	 * 构建回复对话内容
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private InteractAutoResponseDto buildDialog(ResultSet rs) throws SQLException{
		return new InteractAutoResponseDto(
				rs.getInt("id"),
				rs.getInt("response_id"),
				rs.getString("authorName"),
				rs.getString("context"),
				(Date)rs.getObject("commentDate"));
	}
	
	/**
	 * 构建马甲的未回复的被用户回复的回复
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private InteractAutoResponseDto buildUnCkResponse(ResultSet rs)throws SQLException{
		return new InteractAutoResponseDto(
				rs.getInt("id"),
				rs.getInt("author_id"),
				rs.getString("content"),
				rs.getInt("world_id"),
				rs.getInt("world_author_id"),
				rs.getInt("re_author_id"),
				rs.getString("user_name")
				);
	}
}

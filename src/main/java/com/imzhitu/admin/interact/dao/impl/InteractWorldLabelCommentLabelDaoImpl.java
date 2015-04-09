package com.imzhitu.admin.interact.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.WorldLabelCommentLabelDto;
import com.imzhitu.admin.common.pojo.UserLabelDto;
import com.imzhitu.admin.common.pojo.InteractWorldLabelTreeDto;
import com.imzhitu.admin.interact.dao.InteractWorldLabelCommentLabelDao;

@Repository
public class InteractWorldLabelCommentLabelDaoImpl extends BaseDaoImpl implements InteractWorldLabelCommentLabelDao{
	private static final String table = Admin.INTERACT_WORLD_LABEL_COMMENT_LABEL;//用户标签--评论标签关联表，表名
	private static final String ulclTable = Admin.ADMIN_INTERACT_WORLD_LABEL_COMMENT_LABEL;//用户标签--评论标签关联表，数据库名+表名
//	private static final String userLabelTable = HTS.USER_LABEL; //用户标签表
//	private static final String userLabelTable = Admin.HTS_USER_LABEL; //用户标签表
	private static final String commentLabelTable = Admin.INTERACT_COMMENT_LABEL;//评论标签表
	private static final String worldLabelTable = Admin.HTS_WORLD_LABEL;
	private static final String QUERY_WORLD_LABEL_COMMENT_LABEL_LIST_BY_MAXID = "select ulcl.* ,ul.label_name as label_name,cl.label_name as commentLabelName from " 
			+ ulclTable + " ulcl left join " 
			+ worldLabelTable + " ul on ulcl.world_label_id=ul.id left join " 
			+ commentLabelTable + " cl on ulcl.comment_label_id=cl.id where ulcl.id<=? order by ulcl.id desc";
	private static final String QUERY_WORLD_LABEL_COMMENT_LABEL_LIST = "select ulcl.* ,ul.label_name as label_name,cl.label_name as commentLabelName from " 
			+ ulclTable + " ulcl left join " 
			+ worldLabelTable + " ul on ulcl.world_label_id=ul.id left join " 
			+ commentLabelTable + " cl on ulcl.comment_label_id=cl.id  order by ulcl.id desc";
	private static final String QUERY_WORLD_LABEL_COMMENT_COUNT = "select count(1) from " + table + " where id<=?";
	
	
	private static final String DELETE_WORLD_LABEL_COMMENT_LABEL_BY_IDS = "delete from " + table + " where id in ";
	private static final String QUERY_WORLD_LABEL_COMMENT_LABEL_BY_UID = "select ulcl.* ,ul.label_name as label_name,cl.label_name as commentLabelName from " 
			+ ulclTable + " ulcl left join " 
			+ worldLabelTable + " ul on ulcl.world_label_id=ul.id left join " 
			+ commentLabelTable + " cl on ulcl.comment_label_id=cl.id where ulcl.world_label_id=?";
	private static final String ADD_WORLD_LABEL_COMMENT_LABEL = "insert into " + table + " (world_label_id,comment_label_id) values(?,?)";
	
	/**
	 * 查询织图类型
	 */
	private static final String QUERY_ALL_WORLD_TYPE = " select * from " + Admin.HTS_WORLD_TYPE;
	
	/**
	 * 查询织图标签
	 */
	private static final String QUERY_WORLD_LABEL_BY_TYPE_ID = " select * from " + Admin.HTS_WORLD_LABEL + " where type_id=?";
	
	/**
	 * 查询用户标签--评论标签关联列表
	 */
	@Override
	public List<WorldLabelCommentLabelDto> QueryWorldLabelCommentLabel(Integer maxId, RowSelection rowSelection){
		return queryForPage(QUERY_WORLD_LABEL_COMMENT_LABEL_LIST_BY_MAXID, new Object[]{maxId},new RowMapper<WorldLabelCommentLabelDto>(){
			@Override
			public WorldLabelCommentLabelDto mapRow(ResultSet rs, int num)throws SQLException{
				return buildULCLDto(rs);
			}
		},rowSelection);
	}
	/**
	 *  查询用户标签--评论标签关联总数
	 */
	@Override
	public long GetWorldLabelCommentLabelCount(Integer maxId){
		return getJdbcTemplate().queryForLong(QUERY_WORLD_LABEL_COMMENT_COUNT, maxId);
	}
	/**
	 * 查询用户标签--评论标签关联列表
	 */
	@Override
	public List<WorldLabelCommentLabelDto> QueryWorldLabelCommentLabel(RowSelection rowSelection){
		return queryForPage(QUERY_WORLD_LABEL_COMMENT_LABEL_LIST, new RowMapper<WorldLabelCommentLabelDto>(){
			@Override
			public WorldLabelCommentLabelDto mapRow(ResultSet rs, int num)throws SQLException{
				return buildULCLDto(rs);
			}
		},rowSelection);
	}
	

	/**
	 * 查询织图类型列表to Tree
	 */
	@Override
	public List<InteractWorldLabelTreeDto> QueryAllWorldTypeToTree(){
		return getJdbcTemplate().query(QUERY_ALL_WORLD_TYPE,new RowMapper<InteractWorldLabelTreeDto>(){
			@Override
			public InteractWorldLabelTreeDto mapRow(ResultSet rs,int num)throws SQLException{
				InteractWorldLabelTreeDto worldLabelTree =  buildWorldTypeToTree(rs);
				return worldLabelTree;
			}
		});
	}
	
	/**
	 * 查询织图标签列表to Tree
	 */
	@Override
	public List<InteractWorldLabelTreeDto> QueryWorldLabelToTreeByTypeId(Integer typeId){
		return getJdbcTemplate().query(QUERY_WORLD_LABEL_BY_TYPE_ID,new Object[]{typeId},new RowMapper<InteractWorldLabelTreeDto>(){
			@Override
			public InteractWorldLabelTreeDto mapRow(ResultSet rs,int num)throws SQLException{
				InteractWorldLabelTreeDto worldLabelTree =  buildWorldLabelToTree(rs);
				return worldLabelTree;
			}
		});
	}
	
	@Override
	public void DeleteWorldLabelCommentLabelByIds(Integer[] ids){
		String sql = DELETE_WORLD_LABEL_COMMENT_LABEL_BY_IDS + SQLUtil.buildInSelection(ids);
		getMasterJdbcTemplate().update(sql, (Object[])ids);
	}
	
	/**
	 * 根据标签id查询用户标签--评论标签关联
	 */
	@Override
	public WorldLabelCommentLabelDto QueryWorldLabelCommentLabelByUId(Integer uId){
		try{
			return getJdbcTemplate().queryForObject(QUERY_WORLD_LABEL_COMMENT_LABEL_BY_UID, new Object[]{uId}, new RowMapper<WorldLabelCommentLabelDto>(){
				@Override
				public WorldLabelCommentLabelDto mapRow(ResultSet rs,int num)throws SQLException{
					return buildULCLDto(rs);
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
		
	}
	
	/**
	 * 增加用户标签--评论标签关联
	 */
	@Override
	public void AddWorldLabelCommentLabelCount(WorldLabelCommentLabelDto worldLabelCommentLabelDto){
		getMasterJdbcTemplate().update(ADD_WORLD_LABEL_COMMENT_LABEL, worldLabelCommentLabelDto.getWorld_label_id(),worldLabelCommentLabelDto.getComment_label_id());
	}

	/**
	 * 构建用户标签--评论标签关联的dto
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private WorldLabelCommentLabelDto buildULCLDto(ResultSet rs) throws SQLException{
		return new WorldLabelCommentLabelDto(rs.getInt("id"),
				rs.getInt("world_label_id"),
				rs.getInt("comment_label_id"),
				rs.getString("label_name"),
				rs.getString("commentLabelName"));
	}
	
	
	
	/**
	 * 构建织图类型Tree
	 */
	private InteractWorldLabelTreeDto buildWorldTypeToTree(ResultSet rs)throws SQLException{
		return new InteractWorldLabelTreeDto(
				rs.getInt("id"),
				rs.getString("type_name"));
	}
	/**
	 * 构建织图标签Tree
	 */
	private InteractWorldLabelTreeDto buildWorldLabelToTree(ResultSet rs)throws SQLException{
		return new InteractWorldLabelTreeDto(
				rs.getInt("id"),
				rs.getString("label_name"));
	}
	
	
	
}

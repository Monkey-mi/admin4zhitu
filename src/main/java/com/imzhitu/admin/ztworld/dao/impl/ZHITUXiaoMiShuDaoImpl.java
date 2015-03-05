package com.imzhitu.admin.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.pojo.ZTWorldDto;
import com.imzhitu.admin.ztworld.dao.ZHITUXiaoMiShuDao;

@Repository
public class ZHITUXiaoMiShuDaoImpl extends BaseDaoImpl implements ZHITUXiaoMiShuDao{
	private static final String table = HTS.HTWORLD_HTWORLD;
	private static final String QUERY_ZTWORLD_BY_UID = " select * from " + table + " where author_id=? order by id desc limit 0,?";
	/**
	 * 保存世界
	 */
	private static final String SAVE_WORLD = "insert into " + table 
			+ " (id, short_link, world_name, world_desc, world_label, world_type, type_id, date_added, date_modified," 
			+ "author_id, cover_path, title_path, title_thumb_path, thumbs," 
			+ "longitude,latitude,location_desc,location_addr, phone_code, province," 
			+ "city, size, child_count,ver, valid, latest_valid, shield)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * 删除世界
	 */
	private static final String DEL_WORLD_BY_IDS = " delete from " + table + " where id in "; 
	
	private static final String QUERY_WORLD_BY_WID =  " select * from " + table + " where id=?";
	
	private static final String UPDATE_WORLD_DESC_BY_WID = " update " + table + " set world_desc=? where id=?";
	
	private static final String UPDATE_WORLD_LABEL_BY_WID = " update " + table + " set world_label=? where id=?";
	
	private static final String QUERY_WORLD = " select * from " + table + " where author_id= ? order by id desc";
	
	private static final String QUERY_WORLD_BY_MAXID = " select * from " + table + " where author_id=? and id<? order by id desc";
	
	private static final String GET_WORLD_COUNT  = " select count(1) from " + table + " where author_id=? and id<?";
	
	@Override
	public void addZTWorld(ZTWorldDto htworld){
		if(htworld.getLatestValid()==null)htworld.setLatestValid(0);
		getJdbcTemplate().update(SAVE_WORLD, new Object[]{
				htworld.getId(),
				htworld.getShortLink(),
				htworld.getWorldName(),
				htworld.getWorldDesc(),
				htworld.getWorldLabel(),
				htworld.getWorldType(),
				htworld.getTypeId(),
				htworld.getDateAdded(),
				htworld.getDateModified(),
				htworld.getAuthorId(),
				htworld.getCoverPath(),
				htworld.getTitlePath(),
				htworld.getTitleThumbPath(),
				htworld.getThumbs(),
				htworld.getLongitude(),
				htworld.getLatitude(),
				htworld.getLocationDesc(),
				htworld.getLocationAddr(),
				htworld.getPhoneCode(),
				htworld.getProvince(),
				htworld.getCity(),
				htworld.getSize(),
				htworld.getChildCount(),
				htworld.getVer(),
				htworld.getValid(),
				htworld.getLatestValid(),
				htworld.getShield()
		});
	}
	
	@Override
	public List<ZTWorldDto> queryZTWorldByUId(Integer uid,Integer limit){
		try{
			return getJdbcTemplate().query(QUERY_ZTWORLD_BY_UID, new Object[]{uid,limit},new RowMapper<ZTWorldDto>(){
				@Override
				public ZTWorldDto mapRow(ResultSet rs, int num)throws SQLException{
					return buildZTWorldDto(rs);
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public void delZTWorldByIds(Integer[] ids){
		String sql = DEL_WORLD_BY_IDS+SQLUtil.buildInSelection(ids);
		getJdbcTemplate().update(sql, (Object[])ids);
	}
	
	
	@Override
	public ZTWorldDto queryWorldByWid(Integer wid){
		try{
			return getJdbcTemplate().queryForObject(QUERY_WORLD_BY_WID, new Object[]{wid}, new RowMapper<ZTWorldDto>(){
				@Override
				public ZTWorldDto mapRow(ResultSet rs, int num)throws SQLException{
					return buildZTWorldDto(rs);
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	
	@Override
	public void updateZTWorldDesc(Integer wid,String worldDesc){
		getJdbcTemplate().update(UPDATE_WORLD_DESC_BY_WID, worldDesc,wid);
	}
	
	@Override
	public void updateWorldLabel(Integer wid,String worldLabel){
		getJdbcTemplate().update(UPDATE_WORLD_LABEL_BY_WID,worldLabel,wid);
	}
	
	@Override
	public List<ZTWorldDto> queryWorld(Integer authorId,RowSelection rowSelection){
		try{
			return queryForPage(QUERY_WORLD,new Object[]{authorId},new RowMapper<ZTWorldDto>(){
				@Override
				public ZTWorldDto mapRow(ResultSet rs, int num)throws SQLException{
					return buildZTWorldDto(rs);
				}
			},rowSelection);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public List<ZTWorldDto> queryWorld(Integer authorId,Integer maxId,RowSelection rowSelection){
		try{
			return queryForPage(QUERY_WORLD_BY_MAXID,new Object[]{authorId,maxId},new RowMapper<ZTWorldDto>(){
				@Override
				public ZTWorldDto mapRow(ResultSet rs, int num)throws SQLException{
					return buildZTWorldDto(rs);
				}
			},rowSelection);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	
	@Override
	public long getWorldCount(Integer authorId,Integer maxId){
		return getJdbcTemplate().queryForLong(GET_WORLD_COUNT, authorId,maxId);
	}
	
	/**
	 * 根据结果集构建WorldMaintainDto
	 * 
	 * @param rs
	 * @param urlPrefix
	 * @return
	 * @throws SQLException 
	 */
	public ZTWorldDto buildZTWorldDto(ResultSet rs) throws SQLException {
		ZTWorldDto dto = new ZTWorldDto(
				rs.getInt("id"),
				rs.getString("short_link"),
				rs.getInt("author_id"),
				rs.getString("world_name"), 
				rs.getString("world_desc"), 
				rs.getString("world_label"),
				rs.getString("world_type"),
				rs.getInt("type_id"),
				(Date)rs.getObject("date_added"), 
				(Date)rs.getObject("date_modified"), 
				rs.getInt("click_count"),
				rs.getInt("like_count"),
				rs.getInt("comment_count"), 
				rs.getInt("keep_count"), 
				rs.getString("cover_path"), 
				rs.getString("title_path"),
				rs.getString("title_thumb_path"),
				rs.getString("thumbs"),
				rs.getDouble("longitude"),
				rs.getDouble("latitude"), 
				rs.getString("location_desc"),
				rs.getString("location_addr"), 
				rs.getInt("phone_code"), 
				rs.getString("province"), 
				rs.getString("city"),
				rs.getInt("size"),
				rs.getInt("child_count"),
				rs.getInt("ver"),
				rs.getInt("valid"),
				rs.getInt("shield"));
		dto.setWorldId(dto.getId());
		
		if(dto.getShortLink() != null) {
			dto.setWorldURL(urlPrefix + dto.getShortLink()); 
		} else {
			dto.setWorldURL(urlPrefix + dto.getId());
		}
		return dto;
	}
}

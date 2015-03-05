package com.imzhitu.admin.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldTypeWorld;
import com.hts.web.common.util.CollectionUtil;
import com.hts.web.userinfo.dao.impl.UserInfoDaoImpl;
import com.imzhitu.admin.common.pojo.ZTWorldTypeWorldDto;
import com.imzhitu.admin.ztworld.dao.HTWorldTypeWorldDao;

@Repository
public class HTWorldTypeWorldDaoImpl extends BaseDaoImpl implements
		HTWorldTypeWorldDao {

	private static String tableTypeWorld = HTS.HTWORLD_TYPE_WORLD;
	
	/**
	 * 分类信息
	 */
	private static final String HW_INFO = "hw.id,hw.world_id,hw.type_id,hw.superb,"
			+ "hw.valid as type_valid,hw.serial,hw.weight,hw.recommender_id,hw.date_add,hw.date_modify,hw.review ";
	
	/**
	 * 保存分类织图
	 */
	private static final String SAVE_TYPE_WORLD = "insert into " + tableTypeWorld
			+ " (id,world_id, type_id, superb, valid, serial, weight, recommender_id,date_add,date_modify) values (?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * 根据id删除
	 */
	private static final String DELETE_TYPE_WORLD_BY_ID = "delete from " 
			+ tableTypeWorld+ " where id=?";
	
	/**
	 * 根据织图id删除
	 */
	private static final String DELETE_TYPE_WORLD_BY_WORLD_ID = "delete from " + tableTypeWorld 
			+ " where world_id=?";
	
	/**
	 * 查询分类织图SQL头部
	 */
	private static final String QUERY_TYPE_WORLD_HEAD = "select  " + HW_INFO + ", h.*,aui.user_name as recommenderName," + U0_INFO + " from " 
			+ HTS.HTWORLD_HTWORLD +" as h, " + tableTypeWorld+ "  hw left join  "
			+ " hts_admin.interact_type_schedula its on hw.world_id=its.world_id, "
			+ HTS.USER_INFO + " as u0," + HTS.HTWORLD_TYPE + " as ht,"
			+ "hts_admin.admin_user_info as aui "
			+ " where h.id=hw.world_id and h.author_id=u0.id and ht.id=hw.type_id and ht.valid=1 and aui.id=hw.recommender_id";
	
	/**
	 * 查询分类织图总数
	 */
	private static final String QUERY_TYPE_WORLD_COUNT = "select count(*) from " 
			+ HTS.HTWORLD_HTWORLD +" as h, " + tableTypeWorld+ " as hw," + HTS.HTWORLD_TYPE + " as ht"
			+ " where h.id=hw.world_id and ht.id=hw.type_id and ht.valid=1";
	
	/**
	 * 更新分类
	 */
	private static final String UPDATE_TYPE = "update " + tableTypeWorld 
			+ " set type_id=? where id=?";
	
	/**
	 * 更新精品标记
	 */
	private static final String UPDATE_SUPERB = "update " + tableTypeWorld 
			+ " set superb=? where id=?";
	
	/**
	 * 更新精品标记
	 */
	private static final String UPDATE_SUPERB_BY_WID = "update " + tableTypeWorld
			+ " set superb=? where world_id=? ";
	
	/**
	 * 更新权重
	 */
	private static final String UPDATE_WEIGHT = "update " + tableTypeWorld 
			+ " set weight=? where id=?";
	
	/**
	 * 更新有效性
	 */
	private static final String UPDATE_VALID_BY_WORLD_ID ="update " + tableTypeWorld 
			+ " set valid=? where world_id=? and valid=?";
	
	/**
	 * 更新序号
	 */
	private static final String UPDATE_SERIAL = "update " + tableTypeWorld 
			+ " set serial=? where world_id=?";
	
	/**
	 * 更新分类id
	 */
	private static final String UPDATE_TYPE_ID = "update " + tableTypeWorld 
			+ " set type_id=? where world_id=?";
	
	/**
	 * 更新分类织图有效性
	 */
	private static final String UPDATE_TYPE_WORLD_VALID_BY_IDS = "update " + tableTypeWorld 
			+ " set valid=? where id in ";
	
	/**
	 * 更新分类织图有效性
	 */
	private static final String UPDATE_TYPE_WORLD_VALID_BY_WORLD_IDS = " update " + tableTypeWorld 
			+ " set valid=? where world_id in ";
	
	/**
	 * 更新所有推荐分类织图有效性
	 */
	private static final String UPDATE_ALL_RECOMMEND_TYPE_WORLD_VALID_BY_IDS = "update " + tableTypeWorld 
			+ " set valid=? where recommender_id != 0 and valid=?";
	
	/**
	 * 根据有效性查询分类织图
	 */
	private static final String QUERY_ALL_RECOMMEND_TYPE_WORLD_BY_VALID = "select " + HW_INFO 
			+ ",h.*,aui.user_name as recommenderName," + U0_INFO + " from " + HTS.HTWORLD_HTWORLD 
			+ " h," + HTS.USER_INFO + " u0," + HTS.HTWORLD_TYPE_WORLD + " hw,"
			+ " hts_admin.admin_user_info as aui "
			+ " where h.author_id=u0.id and h.id=hw.world_id and hw.valid=? and hw.recommender_id != 0 and h.valid=1 and h.shield=0  and aui.id=hw.recommender_id";
	
	/**
	 * 根据ids查询分类织图
	 */
	private static final String QUERY_TYPE_WORLD_BY_IDS = "select " + HW_INFO+ ",h.*,aui.user_name as recommenderName," + U0_INFO + " from " + HTS.HTWORLD_HTWORLD 
			+ " h," + HTS.USER_INFO + " u0," + HTS.HTWORLD_TYPE_WORLD + " hw,"
			+ " hts_admin.admin_user_info as aui "
			+ " where h.author_id=u0.id and h.id=hw.world_id and h.valid=1 and h.shield=0 and aui.id=hw.recommender_id and hw.id in ";
	
	/**
	 * 根据ids查询推荐织图id
	 */
	private static final String QUERY_RECOMMEND_WORLD_IDS_BY_IDS = "select world_id from " + tableTypeWorld 
			+ " where recommender_id != 0 and id in ";
	
	/**
	 * 更新推荐id
	 */
	private static final String UPDATE_RECOMMEND_ID_BY_WORLD_ID = "update " + tableTypeWorld + " set recommender_id=?"
			+ " where world_id=?";
	/**
	 * 更新最后更改时间
	 */
	private static final String UPDATE_MODIFY_DATE_BY_WORLD_ID = "update " + tableTypeWorld + " set date_modify=? where world_id=?";
	
	/**
	 * 更新排序情况
	 */
	private static final String UPDATE_IS_SORTED_BY_IDS = "update " + tableTypeWorld + " set is_sorted=? where world_id in ";
	
	/**
	 * 根据wids查询分类织图
	 */
	private static final String QUERY_TYPE_WORLD_BY_WIDS = "select " + HW_INFO + ",h.*,aui.user_name as recommenderName,"+U0_INFO + " from " + HTS.HTWORLD_HTWORLD
			+ " h,"+ HTS.USER_INFO + " u0," + HTS.HTWORLD_TYPE_WORLD + " hw,"
			+ " hts_admin.admin_user_info as aui "
			+ " where h.author_id=u0.id and h.id=hw.world_id and h.valid=1 and h.shield=0 and aui.id=hw.recommender_id and hw.world_id in ";
	
	/**
	 * 修改精选织图点评
	 */
	private static final String UPDATE_TYPE_WORLD_REVIEW = " update " + tableTypeWorld + " set review=? where world_id=?";
	
	@Override
	public void saveTypeWorld(HTWorldTypeWorld typeWorld) {
		Date now = new Date();
		getJdbcTemplate().update(SAVE_TYPE_WORLD, new Object[]{
			typeWorld.getId(),
			typeWorld.getWorldId(),
			typeWorld.getTypeId(),
			typeWorld.getSuperb(),
			typeWorld.getValid(),
			typeWorld.getSerial(),
			typeWorld.getWeight(),
			typeWorld.getRecommenderId(),
			now,
			now
		});
	}
	
	
	@Override
	public List<ZTWorldTypeWorldDto> queryTypeWorld(String sort,
			String order, Map<String, Object> attrMap, RowSelection rowSelection) {
		String sql = QUERY_TYPE_WORLD_HEAD + buildSelection(attrMap) + buildOrder(sort, order);
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		return queryForPage(sql, argsList.toArray(), new RowMapper<ZTWorldTypeWorldDto>() {

			@Override
			public ZTWorldTypeWorldDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ZTWorldTypeWorldDto dto = buildTypeWorldDto(rs);
				if(dto.getAuthorId() != 0) {
					dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
				}
				return dto;
			}
		},rowSelection);
	}

	@Override
	public List<ZTWorldTypeWorldDto> queryTypeWorld(int maxId, String sort,
			String order, Map<String, Object> attrMap, RowSelection rowSelection) {
		String sql = QUERY_TYPE_WORLD_HEAD + buildSelection(attrMap) + " and hw.serial<=? " + buildOrder(sort, order);
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		argsList.add(maxId);
		return queryForPage(sql, argsList.toArray(), new RowMapper<ZTWorldTypeWorldDto>() {

			@Override
			public ZTWorldTypeWorldDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ZTWorldTypeWorldDto dto = buildTypeWorldDto(rs);
				if(dto.getAuthorId() != 0) {
					dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
				}
				return dto;
			}
		},rowSelection);
	}
	
	@Override
	public long queryTypeWorldCount() {
		return getJdbcTemplate().queryForLong(QUERY_TYPE_WORLD_COUNT);
	}
	
	@Override
	public long queryTypeWorldCountByMaxId(int maxId,
			Map<String, Object> attrMap) {
		String sql = QUERY_TYPE_WORLD_COUNT + buildSelection(attrMap) + " and hw.serial<=?";
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		argsList.add(maxId);
		return getJdbcTemplate().queryForLong(sql, argsList.toArray());
	}

	/**
	 * 构建分类查询条件
	 * 
	 * @param attrMap
	 * @return
	 */
	private String buildSelection(Map<String, Object> attrMap) {
		StringBuilder builder = new StringBuilder();
		Object typeId = attrMap.get("typeId");
		if(typeId != null && typeId.toString() != "") {
			builder.append(" and hw.type_id=?");
		}
		Object valid = attrMap.get("valid");
		if(valid != null && valid.toString() != "") {
			builder.append(" and hw.valid=?");
		}
		Object superb = attrMap.get("superb");
		if(superb != null && superb.toString() != "") {
			builder.append(" and hw.superb=?");
		}
		Object weight = attrMap.get("weight");
		if(weight != null && weight.toString() != "") {
			builder.append(" and hw.weight=?");
		}
		Object recommenerId = attrMap.get("recommenderId");
		if(recommenerId != null && recommenerId.toString() != "") {
			builder.append(" and hw.recommender_id=?");
		}
		Object isSorted = attrMap.get("isSorted");
		if(isSorted != null && isSorted.toString()!=""){
			builder.append(" and hw.is_sorted=?");
		}
		Object beginDate = attrMap.get("beginDate");
		Object endDate = attrMap.get("endDate");
		if(beginDate !=null && endDate !=null && beginDate.toString() !="" && endDate.toString()!=""){
			builder.append(" and h.date_added between ? and ?");
		}
		
		return builder.toString();
	}
	
	/**
	 * 构建分类查询排序
	 * @param sort
	 * @param order
	 * @return
	 */
	private String buildOrder(String sort, String order) {
		StringBuilder builder = new StringBuilder(" order by ");
		if(sort != null	&& sort != "") {
			if(sort.equals("clickCount")) {
				builder.append("h.click_count ");
				
			} else if(sort.equals("likeCount")) {
				builder.append("h.like_count ");
				
			} else if(sort.equals("commentCount")) {
				builder.append("h.comment_count ");
				
			} else if(sort.equals("keepCount")) {
				builder.append("h.keep_count ");
			} else if(sort.equals("schedulaDate")){
				builder.append("its.schedula ");
			}else {
				builder.append("hw.serial ");
			}
			builder.append(order);
		} else {
			builder.append(" hw.serial desc");
		}
		return builder.toString();
	}
	
	@Override
	public void deleteTypeWorld(Integer id) {
		getJdbcTemplate().update(DELETE_TYPE_WORLD_BY_ID, id);
	}
	
	@Override
	public void deleteTypeWorld(Integer[] ids) {
		deleteByIds(tableTypeWorld, ids);
	}
	
	@Override
	public void deleteByWorldId(Integer worldId) {
		getJdbcTemplate().update(DELETE_TYPE_WORLD_BY_WORLD_ID, worldId);
	}

	@Override
	public void updateType(int id, int typeId) {
		getJdbcTemplate().update(UPDATE_TYPE, new Object[]{typeId, id});
	}

	@Override
	public void updateSuperb(int id, int superb) {
		getJdbcTemplate().update(UPDATE_SUPERB, new Object[]{superb, id});
	}
	
	@Override
	public void updateSuperbByWId(Integer worldId,int superb){
		getJdbcTemplate().update(UPDATE_SUPERB_BY_WID, new Object[]{superb,worldId});
	}
	
	@Override
	public void updateWeight(int id, int weight) {
		getJdbcTemplate().update(UPDATE_WEIGHT, new Object[]{weight, id});
	}

	@Override
	public int updateValidByWorldId(Integer worldId, Integer valid) {
		return getJdbcTemplate().update(UPDATE_VALID_BY_WORLD_ID, new Object[]{valid, worldId, 1-valid});
	}
	
	@Override
	public void updateSerial(Integer worldId, Integer serial) {
		getJdbcTemplate().update(UPDATE_SERIAL, new Object[]{serial, worldId});
	}
	

	@Override
	public void updateTypeId(Integer worldId, Integer typeId) {
		getJdbcTemplate().update(UPDATE_TYPE_ID, new Object[]{typeId, worldId});
	}
	
	
	@Override
	public void updateTypeWorldValid(Integer[] ids) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = UPDATE_TYPE_WORLD_VALID_BY_IDS + inSelection;
		Object[] args = SQLUtil.getArgsByInCondition(ids, new Object[]{Tag.TRUE}, true);
		getJdbcTemplate().update(sql, args);
	}
	
	@Override
	public void updateTypeWorldValidByWorldIds(Integer[] worldIds){
		String sql = UPDATE_TYPE_WORLD_VALID_BY_WORLD_IDS + SQLUtil.buildInSelection(worldIds);
		Object[] args = SQLUtil.getArgsByInCondition(worldIds, new Object[]{Tag.TRUE}, true);
		getJdbcTemplate().update(sql,args);
	}
	
	@Override
	public void updateAllRecommendTypeWorldValid(Integer valid) {
		getJdbcTemplate().update(UPDATE_ALL_RECOMMEND_TYPE_WORLD_VALID_BY_IDS, valid, 1-valid);
	}


	@Override
	public List<ZTWorldTypeWorldDto> queryRecommendTypeWorldByValid(Integer valid) {
		return getJdbcTemplate().query(QUERY_ALL_RECOMMEND_TYPE_WORLD_BY_VALID, new Object[]{valid}, new RowMapper<ZTWorldTypeWorldDto>() {

			@Override
			public ZTWorldTypeWorldDto mapRow(ResultSet rs, int num)
					throws SQLException {
				ZTWorldTypeWorldDto dto = buildTypeWorldDto(rs);
				if(dto.getAuthorId() != 0) {
					dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
				}
				return dto;
			}
		});
	}
	
	@Override
	public List<ZTWorldTypeWorldDto> queryTypeWorldByIds(Integer[] ids) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = QUERY_TYPE_WORLD_BY_IDS + inSelection;
		return getJdbcTemplate().query(sql, (Object[])ids, new RowMapper<ZTWorldTypeWorldDto>() {

			@Override
			public ZTWorldTypeWorldDto mapRow(ResultSet rs, int num)
					throws SQLException {
				ZTWorldTypeWorldDto dto = buildTypeWorldDto(rs);
				if(dto.getAuthorId() != 0) {
					dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
				}
				return dto;
			}
		});
	}
	
	@Override
	public List<ZTWorldTypeWorldDto> queryTypeWorldByWids(Integer[] wids) {
		String inSelection = SQLUtil.buildInSelection(wids);
		String sql = QUERY_TYPE_WORLD_BY_WIDS + inSelection;
		try{
			return getJdbcTemplate().query(sql, (Object[])wids, new RowMapper<ZTWorldTypeWorldDto>() {
	
				@Override
				public ZTWorldTypeWorldDto mapRow(ResultSet rs, int num)
						throws SQLException {
					ZTWorldTypeWorldDto dto = buildTypeWorldDto(rs);
					if(dto.getAuthorId() != 0) {
						dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
					}
					return dto;
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public List<Integer> queryRecommendWorldIdsByTypeWorldIds(Integer[] ids) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = QUERY_RECOMMEND_WORLD_IDS_BY_IDS + inSelection;
		return getJdbcTemplate().queryForList(sql, ids, Integer.class);
	}
	
	@Override
	public void updateRecommenderId(Integer worldId, Integer recommenderId) {
		getJdbcTemplate().update(UPDATE_RECOMMEND_ID_BY_WORLD_ID, recommenderId, worldId);
	}
	
	@Override
	public void updateIsSorted(Integer[] ids,Integer isSorted){
		String sql = UPDATE_IS_SORTED_BY_IDS + SQLUtil.buildInSelection(ids);
		Object[] args = SQLUtil.getArgsByInCondition(ids, new Object[]{isSorted}, true);
		getJdbcTemplate().update(sql,args);
	}
	
	@Override
	public void updateModifyDateByWid(Integer wid,Date modifyDate){
		getJdbcTemplate().update(UPDATE_MODIFY_DATE_BY_WORLD_ID, modifyDate,wid);
	}
	
	/**
	 * 修改精选织图点评
	 */
	@Override
	public void updateTypeWorldReview(Integer worldId,String review){
		getJdbcTemplate().update(UPDATE_TYPE_WORLD_REVIEW, review,worldId);
	}
	
	/**
	 * 构建ZTWorldTypeWorldDto
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public ZTWorldTypeWorldDto buildTypeWorldDto(ResultSet rs) throws SQLException{
		String worldURL = null;
		if(rs.getString("short_link") != null) {
			worldURL = urlPrefix + rs.getString("short_link");
		} else {
			worldURL = urlPrefix + rs.getInt("world_id");
		}
		
		ZTWorldTypeWorldDto dto = new ZTWorldTypeWorldDto(
				rs.getInt("id"),
				rs.getInt("world_id"),
				rs.getInt("type_id"),
				rs.getInt("superb"),
				rs.getInt("type_valid"),
				rs.getInt("serial"),
				rs.getInt("weight"),
				rs.getInt("recommender_id"),
				rs.getString("short_link"),
				rs.getString("world_name"),
				rs.getString("world_desc"),
				rs.getString("world_label"),
				rs.getString("world_type"),
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
				rs.getInt("valid"),
				rs.getInt("shield"),
				worldURL,
				rs.getString("recommenderName"),
				(Date)rs.getObject("date_add"),
				(Date)rs.getObject("date_modify"));
		dto.setReView(rs.getString("review"));
		return dto;
	}
	
}

package com.imzhitu.admin.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.common.pojo.UserMsgRecipientDto;
import com.hts.web.common.util.CollectionUtil;
import com.imzhitu.admin.userinfo.dao.UserMsgDao;

/**
 * <p>
 * 私信数据访问对象
 * </p>
 * 
 * 创建时间：2014-5-8
 * @author tianjie
 *
 */
@Repository
public class UserMsgDaoImpl extends BaseDaoImpl implements UserMsgDao {
	
	private static final String tableRecipientBox = HTS.USER_MSG_RECIPIENT_BOX;
	
	/**
	 * 查询收件箱信息SQL头部
	 */
	private static final String QUERY_RECIPIENT_BOX_HEAD = "select m.*," + U0_INFO + ",u0.phone_code,u0.phone_sys,u0.phone_ver,u0.ver"
			+ " from (select mr0.sender_id, mr0.recipient_id,mr0.ck, m0.* from " 
			+ tableRecipientBox + " as mr0, " + HTS.USER_MSG 
			+ " as m0 where mr0.content_id=m0.id and mr0.valid=1 and mr0.recipient_id=?";
	
	/**
	 * 查询收件箱信息SQL中部
	 */
	private static final String QUERY_RECIPIENT_BOX_MAIN = " ORDER BY m0.id desc) as m,"
			+ HTS.USER_INFO + " as u0 where m.sender_id=u0.id";
	
	
	/**
	 * 查询收件箱信息SQL尾部
	 */
	private static final String QUERY_RECIPIENT_BOX_FOOT = " GROUP BY m.sender_id ORDER BY m.id desc";
	
	/**
	 * 查询收件箱信息总数
	 */
	/*
	private static final String QUERY_SENDER_INDEX_COUNT_BY_MAX_ID = "select count(*)"
			+ " from (select mr0.sender_id, mr0.recipient_id, m0.* from " 
			+ tableRecipientBox + " as mr0, " + HTS.USER_MSG + " as m0"
			+ " where mr0.content_id=m0.id and mr0.valid=1 and mr0.recipient_id=? and m0.id<=? GROUP BY mr0.sender_id ORDER BY m0.id desc) as m,"
			+ HTS.USER_INFO + " as u0 where m.sender_id=u0.id";*/
	private static final String QUERY_SENDER_INDEX_COUNT_BY_MAX_ID_HEAD = "select count(*)"
			+ " from (select mr0.sender_id, mr0.recipient_id, m0.* from " 
			+ tableRecipientBox + " as mr0, " + HTS.USER_MSG + " as m0"
			+ " where mr0.content_id=m0.id and mr0.valid=1 and mr0.recipient_id=? and m0.id<=? ";
	private static final String QUERY_SENDER_INDEX_COUNT_BY_MAX_ID_MAIN = " GROUP BY mr0.sender_id ORDER BY m0.id desc) as m,"
			+ HTS.USER_INFO + " as u0 where m.sender_id=u0.id";
	
	
	@Override
	public List<UserMsgRecipientDto> queryRecipientMsgBox(Integer recipientId,
			LinkedHashMap<String, Object> attrMap, RowSelection rowSelection) {
		String selection = buildQueryRecipientMsgBoxSelection(attrMap);
		StringBuilder builder = new StringBuilder();
		if(attrMap.get("senderId") != null) {
			builder.append(" and mr0.sender_id=? ");
		}else{
			builder.append(" ");
		}
		String sql = QUERY_RECIPIENT_BOX_HEAD + builder.toString() + QUERY_RECIPIENT_BOX_MAIN + selection + QUERY_RECIPIENT_BOX_FOOT;
		List<Object> argsList = new ArrayList<Object>();
		argsList.add(recipientId);
		CollectionUtil.collectMapValues(argsList, attrMap);
		return queryForPage(sql, argsList.toArray(), new RowMapper<UserMsgRecipientDto>() {

			@Override
			public UserMsgRecipientDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				UserMsgRecipientDto dto = buildSenderIndexDto(rs);
				UserInfoDto user = dto.getSenderInfo();
				user.setVer(rs.getFloat("ver"));
				user.setPhoneSys(rs.getString("phone_sys"));
				user.setPhoneVer(rs.getString("phone_ver"));
				return dto;
			}
		}, rowSelection);
	}

	@Override
	public List<UserMsgRecipientDto> queryRecipientMsgBox(Integer maxId, Integer recipientId, 
			LinkedHashMap<String, Object> attrMap, RowSelection rowSelection) {
		String selection = buildQueryRecipientMsgBoxSelection(attrMap);
		StringBuilder builder = new StringBuilder();
		if(attrMap.get("senderId") != null) {
			builder.append(" and mr0.sender_id=? ");
		}else{
			builder.append(" ");
		}
		String sql = QUERY_RECIPIENT_BOX_HEAD + " and m0.id<=?" + builder.toString() +QUERY_RECIPIENT_BOX_MAIN + selection + QUERY_RECIPIENT_BOX_FOOT;
		List<Object> argsList = new ArrayList<Object>();
		argsList.add(recipientId);
		argsList.add(maxId);
		CollectionUtil.collectMapValues(argsList, attrMap);
		return queryForPage(sql, argsList.toArray(), new RowMapper<UserMsgRecipientDto>() {

			@Override
			public UserMsgRecipientDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				UserMsgRecipientDto dto = buildSenderIndexDto(rs);
				UserInfoDto user = dto.getSenderInfo();
				user.setVer(rs.getFloat("ver"));
				user.setPhoneSys(rs.getString("phone_sys"));
				user.setPhoneVer(rs.getString("phone_ver"));
				return dto;
			}
		}, rowSelection);
	}

	@Override
	public long queryRecipientMsgBoxCount(Integer maxId, Integer recipientId,
			LinkedHashMap<String, Object> attrMap) {
		String selection = buildQueryRecipientMsgBoxSelection(attrMap);
//		String sql = QUERY_SENDER_INDEX_COUNT_BY_MAX_ID + selection;
		StringBuilder builder = new StringBuilder();
		if(attrMap.get("senderId") != null) {
			builder.append(" and mr0.sender_id=? ");
		}else{
			builder.append(" ");
		}
		String sql = QUERY_SENDER_INDEX_COUNT_BY_MAX_ID_HEAD + builder.toString()+QUERY_SENDER_INDEX_COUNT_BY_MAX_ID_MAIN + selection;
		List<Object> argsList = new ArrayList<Object>();
		argsList.add(recipientId);
		argsList.add(maxId);
		CollectionUtil.collectMapValues(argsList, attrMap);
		return getJdbcTemplate().queryForLong(sql, argsList.toArray());
	}

	/**
	 * 根据属性构建收件箱查询条件
	 * 
	 * @param attrMap
	 * @return
	 */
	private String buildQueryRecipientMsgBoxSelection(LinkedHashMap<String, Object> attrMap) {
		StringBuilder builder = new StringBuilder();
		if(attrMap.get("phone_code") != null) {
			builder.append(" and u0.phone_code=? ");
		}
		return builder.toString();
	}
	
	
	public UserMsgRecipientDto buildSenderIndexDto(ResultSet rs) throws SQLException {
		Integer senderId = rs.getInt("sender_id");
		
		UserInfoDto senderInfo = new UserInfoDto(
				senderId,
				rs.getString("user_name"), 
				rs.getString("user_avatar"), 
				rs.getString("user_avatar_l"), 
				rs.getInt("sex"), 
				rs.getString("email"),
				rs.getString("address"),
				rs.getString("u_province"),
				rs.getString("u_city"),
				null,
				rs.getString("signature"),
				(Date)rs.getObject("register_date"), 
				rs.getString("user_label"),
				rs.getInt("star"),
				rs.getInt("u_phone_code"),
				rs.getInt("online"),
				rs.getInt("platform_verify"));
		
		UserMsgRecipientDto dto =  new UserMsgRecipientDto(
				rs.getInt("id"),
				senderId,
				rs.getInt("recipient_id"),
				(Date)rs.getObject("msg_date"), 
				rs.getString("content"),
				rs.getInt("obj_type"),
				rs.getString("obj_meta"),
				rs.getInt("obj_id"), 
				rs.getString("thumb_path"),
				rs.getInt("ck"),
				senderInfo);
		return dto;
	}

}

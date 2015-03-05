package com.imzhitu.admin.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.UserTrust;
import com.imzhitu.admin.userinfo.dao.UserTrustDao;

@Repository
public class UserTrustDaoImpl extends BaseDaoImpl implements UserTrustDao{
	private static final String table = Admin.USER_TRUST;
	private static final String ADD_USER_TRUST = " insert into " + table + " (user_id,date_add,date_modify,trust,operator_id) values (?,?,?,?,?)";
	private static final String DEL_USET_TRUST =  " delete from " + table + " where user_id in ";
	private static final String UPDATE_USER_TRUST_BY_UID = " update " + table + " set date_modify=?,trust=?,operator_id=? where user_id=?";
	private static final String UPDATE_USER_TRUST_BY_ID = " update " + table + " set date_modify=?,trust=? ,operator_id=? where id=?";
	private static final String QUERY_USER_TRUST_BY_UID = " select ut.*,aui.user_name from " + table + " ut left join " + Admin.ADMIN_USER_INFO+" aui on ut.operator_id=aui.id where ut.user_id=?";
	private static final String QUERY_USER_TRUST_BY_ID =  " select ut.*,aui.user_name from " + table + " ut left join " + Admin.ADMIN_USER_INFO+" aui on ut.operator_id=aui.id where ut.id=?";
	
	public void addUserTrust(Integer userId,Date addDate,Date modifyDate,Integer trust,Integer operatorId){
		getJdbcTemplate().update(ADD_USER_TRUST, userId,addDate,modifyDate,trust,operatorId);
	}
	
	public void delUserTrust(Integer[] ids){
		String sql = DEL_USET_TRUST + SQLUtil.buildInSelection(ids);
		getJdbcTemplate().update(sql, (Object[])ids);
	}
	
	
	public void updateUserTrustByUid(Integer userId,Date modifyDate,Integer trust,Integer operatorId){
		getJdbcTemplate().update(UPDATE_USER_TRUST_BY_UID, modifyDate,trust,operatorId,userId);
	}
	
	public void updateUserTrustById(Integer id,Date modifyDate,Integer trust,Integer operatorId){
		getJdbcTemplate().update(UPDATE_USER_TRUST_BY_ID,modifyDate,trust,operatorId,id);
	}
	
	public UserTrust queryUserTrustByUid(Integer userId){
		try{
			return getJdbcTemplate().queryForObject(QUERY_USER_TRUST_BY_UID, new Object[]{userId},new RowMapper<UserTrust>(){
				@Override
				public UserTrust mapRow(ResultSet rs ,int rowNum)throws SQLException{
					return buildUserTrust(rs);
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	public UserTrust queryUserTrustById(Integer id){
		try{
			return getJdbcTemplate().queryForObject(QUERY_USER_TRUST_BY_ID, new Object[]{id},new RowMapper<UserTrust>(){
				@Override
				public UserTrust mapRow(ResultSet rs ,int rowNum)throws SQLException{
					return buildUserTrust(rs);
				}
			});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	private UserTrust buildUserTrust(ResultSet rs)throws SQLException{
		return new UserTrust(rs.getInt("id"),
				rs.getInt("user_id"),
				rs.getString("user_name"),
				(Date)rs.getObject("date_add"),
				(Date)rs.getObject("date_modify"),
				rs.getInt("trust"),
				rs.getInt("operator_id"));
	}
}

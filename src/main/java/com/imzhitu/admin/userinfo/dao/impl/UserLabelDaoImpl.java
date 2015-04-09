package com.imzhitu.admin.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserLabel;
import com.imzhitu.admin.common.pojo.UserLabelDto;
import com.imzhitu.admin.userinfo.dao.UserLabelDao;

/**
 * <p>
 * 用户标签数据访问对象
 * </p>
 * 
 * 创建时间：2014-2-5
 * @author tianjie
 *
 */
@Repository
public class UserLabelDaoImpl extends BaseDaoImpl implements UserLabelDao {

	private static String table = HTS.USER_LABEL;
	
	private static String tableUserLabel = HTS.USER_INFO_LABEL;

	/**
	 * 保存标签
	 */
	private static final String SAVE_LABEL = "insert into " + table 
			+ " (id,label_name,label_pinyin,label_sex,valid,serial,weight) "
			+ " values (?,?,?,?,?,?,?,?)";
	
	/**
	 * 查询所有
	 */
	private static final String QUERY_ALL_LABEL = "select * from " + table 
			+ " where valid=?" + ORDER_BY_SERIAL_DESC;
	
	/**
	 * 根据用户id查询标签id
	 */
	private static final String QUERY_LABEL_ID_BY_UID = "select label_id from " + tableUserLabel
			+ " where user_id=?";
	
	/**
	 * 根据用户id删除标签用户关联
	 */
	private static final String DELETE_LABEL_USER_BY_UID = "delete from " + tableUserLabel 
			+ " where user_id=?";
	
	/**
	 * 保存标签用户关联
	 */
	private static final String SAVE_LABEL_USER = "insert into " + tableUserLabel
			+ " (user_id,label_id) values (?,?)";
			
	@Override
	public void saveLabel(UserLabel label) {
		getMasterJdbcTemplate().update(SAVE_LABEL, new Object[]{
			label.getId(),
			label.getLabelName(),
			label.getLabelPinyin(),
			label.getLabelState(),
			label.getValid(),
			label.getSerial(),
			label.getWeight()
		});
	}

	@Override
	public void queryAllLabel(final RowCallback<UserLabelDto> callback) {
		getJdbcTemplate().query(QUERY_ALL_LABEL, new Object[]{Tag.TRUE}, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				UserLabelDto label = new UserLabelDto(
						rs.getInt("id"),
						rs.getString("label_name"), 
						rs.getInt("label_sex"));
				callback.callback(label);
			}
		});
	}

	@Override
	public List<Integer> queryLabelIdByUserId(Integer userId) {
		return getJdbcTemplate().queryForList(QUERY_LABEL_ID_BY_UID, new Object[]{userId}, Integer.class);
	}

	@Override
	public void deleteLabelByUserId(Integer userId) {
		getMasterJdbcTemplate().update(DELETE_LABEL_USER_BY_UID, new Object[]{userId});
	}

	@Override
	public void saveLabelUser(Integer labelId, Integer userId) {
		getMasterJdbcTemplate().update(SAVE_LABEL_USER, new Object[]{userId, labelId});
	}
}

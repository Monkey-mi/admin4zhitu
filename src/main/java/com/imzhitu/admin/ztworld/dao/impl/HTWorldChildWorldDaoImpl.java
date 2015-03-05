package com.imzhitu.admin.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldChildWorld;
import com.imzhitu.admin.ztworld.dao.HTWorldChildWorldDao;

/**
 * <p>
 * 子世界数据访问对象
 * </p>
 * 
 * 创建时间：2012-11-01
 * 
 * @author ztj
 * 
 */
@Repository
public class HTWorldChildWorldDaoImpl extends BaseDaoImpl implements HTWorldChildWorldDao{

	
	/**
	 * 子世界表
	 */
	public static String table =  HTS.HTWORLD_CHILD_WORLD;

	/**
	 * 根据wids删除子世界
	 */
	private static final String DELETE_BY_WORLD_IDS = "delete from " + table + " where world_id in ";
	
	/**
	 * 查询所有子世界信息
	 */
	private static final String QUERY_ALL_CHILD_WORLD = "select * from " 
			+ table + " where world_id=?";
	
	@Autowired
	private com.hts.web.ztworld.dao.HTWorldChildWorldDao webWorldChildWorldDao;
	
	@Override
	public void deleteByWorldIds(Integer[] worldIds) {
		String inSelection = SQLUtil.buildInSelection(worldIds);
		String sql = DELETE_BY_WORLD_IDS + inSelection;
		getJdbcTemplate().update(sql, (Object[])worldIds);
	}

	@Override
	public List<HTWorldChildWorld> queryChildWorld(Integer worldId) {
		return getJdbcTemplate().query(QUERY_ALL_CHILD_WORLD, new Object[]{worldId}, 
				new RowMapper<HTWorldChildWorld>() {

					@Override
					public HTWorldChildWorld mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return webWorldChildWorldDao.buildChildWorldByResultSet(rs);
					}
			
		});
	}


}

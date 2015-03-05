package com.imzhitu.admin.ztworld.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.common.pojo.ZTWorldDto;

/**
 * <p>
 * 汇图世界数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 * 
 */
public interface HTWorldDao extends BaseDao {

	/**
	 * 添加播放次数
	 * @param worldId
	 * @param count
	 */
	public int addClickCount(Integer worldId, Integer count);
	
	/**
	 * 减少播放次数
	 * @param worldId
	 * @param count
	 */
	public int reduceClickCount(Integer worldId, Integer count);
	
	/**
	 * 添加喜欢次数
	 * @param worldId
	 * @param count
	 */
	public int addLikedCount(Integer worldId, Integer count);
	
	/**
	 * 减少喜欢次数
	 * @param worldId
	 * @param count
	 */
	public int reduceLikedCount(Integer worldId, Integer count);
	
	/**
	 * 添加收藏次数
	 * @param worldId
	 * @param count
	 */
	public int addKeepCount(Integer worldId, Integer count);
	
	/**
	 * 减少收藏次数
	 * @param worldId
	 * @param count
	 */
	public int reduceKeepCount(Integer worldId, Integer count);
	
	/**
	 * 添加评论次数
	 * @param worldId
	 * @param count
	 */
	public int addCommentCount(Integer worldId, Integer count);
	
	/**
	 * 减少喜欢次数
	 * @param worldId
	 * @param count
	 */
	public int reduceCommentCount(Integer worldId, Integer count);
	
	/**
	 * 更新评论次数
	 * 
	 * @param worldId
	 * @param count
	 * @return
	 */
	public int updateCommentCount(Integer worldId, Integer count);
	
	/**
	 * 更新收藏次数
	 * 
	 * @param worldId
	 * @param count
	 * @return
	 */
	public int updateKeepCount(Integer worldId, Integer count);
	
	
	/**
	 * 根据条件查询织图
	 * 
	 * @param startDateStr 起始时间
	 * @param endDateStr 结束时间
	 * @param attrMap 织图条件
	 * @param userAttrMap 用户条件
	 * @param orderKey
	 * @param orderBy
	 * @param rowSelection
	 * @return
	 */
	public List<ZTWorldDto> queryHTWorldByAttrMap(Date currentDate, String startDateStr,
			String endDateStr, Map<String, Object> attrMap, Map<String, Object> userAttrMap, String orderKey, 
			String orderBy, RowSelection rowSelection);
	
	/**
	 * 根据最大id查询织图
	 * 
	 * @param maxId 最大id
	 * @param startDateStr 起始时间
	 * @param endDateStr 结束时间
	 * @param attrMap 织图条件
	 * @param userAttrMap 用户条件
	 * @param orderKey
	 * @param orderBy
	 * @param rowSelection
	 * @return
	 */
	public List<ZTWorldDto> queryHTWorldByAttrMapByMaxId(Date currentDate, Integer maxId, String startDateStr,
			String endDateStr, Map<String, Object> attrMap, Map<String, Object> userAttrMap, String orderKey, 
			String orderBy, RowSelection rowSelection);
	
	/**
	 * 根据最大id查询织图总数
	 * 
	 * @param maxId
	 * @param startDateStr
	 * @param endDateStr
	 * @param attrMap
	 * @param userAttrMap
	 * @return
	 */
	public long queryHTWorldCountByMaxId(Date currentDate, Integer maxId, String startDateStr, String endDateStr, Map<String, Object> attrMap, Map<String, Object> userAttrMap);
	
	
	/**
	 * 更新织图屏蔽标志
	 * 
	 * @param worldId
	 * @param shield
	 */
	public void updateWorldShield(Integer worldId, Integer shield);

	/**
	 * 更新织图
	 * 
	 * @param worldId
	 * @param attrMap
	 */
	public void updateWorld(Integer worldId, Map<String, Object> attrMap);
	
	/**
	 * 根据结果集构建WorldMaintainDto
	 * 
	 * @param rs
	 * @param urlPrefix
	 * @return
	 * @throws SQLException 
	 */
//	public ZTWorldDto buildZTWorldDto(ResultSet rs) throws SQLException;

	/**
	 * 根据id批量删除织图
	 * 
	 * @param ids
	 */
	public void deleteByIds(Integer[] ids);
	
	/**
	 * 更新织图分类
	 * 
	 * @param worldId
	 * @param typeId
	 * @param worldType
	 */
	public void updateWorldTypeLabel(Integer worldId, Integer typeId, String worldType);
	
	/**
	 * 根据织图id查询作者信息
	 * 
	 * @param worldId
	 * @return
	 */
	public UserInfo queryAuthorInfoByWorldId(Integer worldId);
	
	/**
	 * 更新最新有效性
	 * 
	 * @param id
	 * @param valid
	 * @param dateModified
	 */
	public void updateLatestValid(Integer id, Integer valid, Date dateAdded);
	
	public Integer queryMaxId(String startDateStr, String endDateStr);
	
}





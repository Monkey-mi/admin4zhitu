package com.imzhitu.admin.ztworld.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.common.pojo.ZTWorldDto;

public interface ZTWorldMapper {
	/**
	 * 添加播放次数
	 * @param worldId
	 * @param count
	 */
	@DataSource("master")
	public int addClickCount(ZTWorldDto dto);
	
	/**
	 * 减少播放次数
	 * @param worldId
	 * @param count
	 */
	@DataSource("master")
	public int reduceClickCount(ZTWorldDto dto);
	
	/**
	 * 添加喜欢次数
	 * @param worldId
	 * @param count
	 */
	@DataSource("master")
	public int addLikedCount(ZTWorldDto dto);
	
	/**
	 * 减少喜欢次数
	 * @param worldId
	 * @param count
	 */
	@DataSource("master")
	public int reduceLikedCount(ZTWorldDto dto);
	
	/**
	 * 添加收藏次数
	 * @param worldId
	 * @param count
	 */
	@DataSource("master")
	public int addKeepCount(ZTWorldDto dto);
	
	/**
	 * 减少收藏次数
	 * @param worldId
	 * @param count
	 */
	@DataSource("master")
	public int reduceKeepCount(ZTWorldDto dto);
	
	/**
	 * 添加评论次数
	 * @param worldId
	 * @param count
	 */
	@DataSource("master")
	public int addCommentCount(ZTWorldDto dto);
	
	/**
	 * 减少喜欢次数
	 * @param worldId
	 * @param count
	 */
	@DataSource("master")
	public int reduceCommentCount(ZTWorldDto dto);
	
	/**
	 * 更新评论次数
	 * 
	 * @param worldId
	 * @param count
	 * @return
	 */
	@DataSource("master")
	public int updateCommentCount(ZTWorldDto dto);
	
	/**
	 * 更新收藏次数
	 * 
	 * @param worldId
	 * @param count
	 * @return
	 */
	@DataSource("master")
	public int updateKeepCount(ZTWorldDto dto);
	
	
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
//	public List<ZTWorldDto> queryHTWorldByAttrMap(Date currentDate, String startDateStr,
//			String endDateStr, Map<String, Object> attrMap, Map<String, Object> userAttrMap, String orderKey, 
//			String orderBy, RowSelection rowSelection);
	
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
	@DataSource("slave")
	public List<ZTWorldDto> queryHTWorldByAttrMap(ZTWorldDto dto);
	
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
	@DataSource("slave")
	public long queryHTWorldCountByAttrMap(ZTWorldDto dto);
	
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
	@DataSource("slave")
	public List<ZTWorldDto> queryHTWorld(ZTWorldDto dto);
	
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
	@DataSource("slave")
	public long queryHTWorldTotalCount(ZTWorldDto dto);
	
	/**
	 * 查询最大id
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public Integer queryMaxId(ZTWorldDto dto);
	
	
	/**
	 * 更新织图屏蔽标志
	 * 
	 * @param worldId
	 * @param shield
	 */
	@DataSource("master")
	public void updateWorldShield(ZTWorldDto dto);

	/**
	 * 更新织图
	 * 
	 * @param worldId
	 * @param attrMap
	 */
	@DataSource("master")
	public void updateWorld(ZTWorldDto dto);
	
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
	@DataSource("master")
	public void deleteByIds(Integer[] ids);
	
	/**
	 * 更新织图分类
	 * 
	 * @param worldId
	 * @param typeId
	 * @param worldType
	 */
	@DataSource("master")
	public void updateWorldTypeLabel(Integer worldId, Integer typeId, String worldType);
	
	/**
	 * 根据织图id查询作者信息
	 * 
	 * @param worldId
	 * @return
	 */
	@DataSource("slave")
	public UserInfo queryAuthorInfoByWorldId(Integer worldId);
	
	/**
	 * 更新最新有效性
	 * 
	 * @param id
	 * @param valid
	 * @param dateModified
	 */
	@DataSource("master")
	public void updateLatestValid(ZTWorldDto dto);
	
	/**
	 * 查询某用户发图总数
	 * @param userId
	 * @return
	 */
	@DataSource("slave")
	public long queryWorldCountByUserId(Integer userId);
	
	/**
	 * 更新该用户织图的最新标记
	 * 
	 * @param authorId
	 */
	@DataSource("master")
	public void updateLatestInvalid(@Param("authorId")Integer authorId);
	
	/**
	 * 将织图归档到周表
	 * @param worldWeekMaxId htworld_htworld_week表中的最大Id，(查询结果中不包含该Id对应的值)
	 * @author zxx 2015年11月12日 19:31:22
	 */
	@DataSource("master")
	public void fileWorldToWeek(@Param("worldWeekMaxId")Integer worldWeekMaxId);
	
	/**
	 * 将织图周表中最大ID
	 * @author zxx 2015年11月12日 19:31:22
	 */
	@DataSource("master")
	public Integer queryWorldWeekMaxId();
	
	/**
	 * 查询指定时间内htworld_htworld表中的最小的id
	 * @param date
	 * @return
	 * @author zxx 2015年11月12日 19:58:26
	 */
	public Integer queryWorldMinIdByDate(@Param("date")Date date);
	
}

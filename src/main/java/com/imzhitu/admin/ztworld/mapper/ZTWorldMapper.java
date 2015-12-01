package com.imzhitu.admin.ztworld.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.ZTWorldDto;
import com.imzhitu.admin.ztworld.pojo.ZTWorld;

/**
 * 织图数据操作类
 * 
 * @author zhangbo	2015年11月2日
 *
 */
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
	 * 查询马甲织图
	 * @param firstRow
	 * @param limite
	 * @param maxId
	 * @param startTime 起始时间
	 * @param endTime 	截止时间，用于查询设定的截止时间
	 * @return 
		*	2015年11月26日
		*	mishengliang
	 */
	List<ZTWorld> queryZombieWorld(@Param("firstRow")Integer firstRow,@Param("limite")Integer limite,@Param("maxId")Integer maxId,@Param("startTime")Date startTime,@Param("endTime")Date endTime);
	
	/**
	 * 查询马甲织图总数
	 * 
	 * @param firstRow
	 * @param limite
	 * @param maxId
	 * @param startTime 起始时间
	 * @param endTime 	截止时间，用于查询设定的截止时间
	 * @return
	 * @author zhangbo	2015年11月30日
	 */
	Integer queryZombieWorldTotal(@Param("firstRow")Integer firstRow,@Param("limite")Integer limite,@Param("maxId")Integer maxId,@Param("startTime")Date startTime,@Param("endTime")Date endTime);
	
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
	 * @param worldId	织图id
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
	 * 根据织图id获取织图信息
	 * 
	 * @author zhangbo	2015年11月2日
	 */
	@DataSource("slave")
	ZTWorldDto getZTWorldByWorldId(@Param("id")Integer worldId);

	/**
	 * 根据时间段查询有效织图集合
	 * 
	 * @param maxId		织图查询最大id，用于分页显示的
	 * @param startTime	查询起始时间
	 * @param endTime	查询结束时间
	 * @param phoneCode	客户端代号，0：IOS，1：android
	 * @param firstRow	分页起始行
	 * @param rows		查询每页的数量
	 * @return
	 * @author zhangbo	2015年11月27日
	 */
	List<ZTWorld> getWorldListValid(@Param("maxId")Integer maxId, @Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("phoneCode")Integer phoneCode, @Param("firstRow")Integer firstRow, @Param("limit")Integer rows);
	
	/**
	 * 根据时间段查询有效织图总数
	 * 
	 * @param maxId		织图查询最大id，用于分页显示的
	 * @param startTime	查询起始时间
	 * @param endTime	查询结束时间
	 * @param phoneCode	客户端代号，0：IOS，1：android
	 * @param firstRow	分页起始行
	 * @param rows		查询每页的数量
	 * @return
	 * @author zhangbo	2015年11月30日
	 */
	Integer getWorldListValidTotal(@Param("maxId")Integer maxId, @Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("phoneCode")Integer phoneCode, @Param("firstRow")Integer firstRow, @Param("limit")Integer rows);

	/**
	 * 根据时间段查询无效织图集合
	 * TODO 此处在以后织图表结构分拆后，可以删除，因为删除表肯定是另外一张，就不在这里进行操作
	 * 
	 * @param maxId		织图查询最大id，用于分页显示的
	 * @param startTime	查询起始时间
	 * @param endTime	查询结束时间
	 * @param phoneCode	客户端代号，0：IOS，1：android
	 * @param firstRow	分页起始行
	 * @param rows		查询每页的数量
	 * @return
	 * @author zhangbo	2015年11月27日
	 */
	List<ZTWorld> getWorldListInvalid(@Param("maxId")Integer maxId, @Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("phoneCode")Integer phoneCode, @Param("firstRow")Integer firstRow, @Param("limit")Integer rows);
	
	/**
	 * 根据时间段查询无效织图总数
	 * TODO 此处在以后织图表结构分拆后，可以删除，因为删除表肯定是另外一张，就不在这里进行操作
	 * 
	 * @param maxId		织图查询最大id，用于分页显示的
	 * @param startTime	查询起始时间
	 * @param endTime	查询结束时间
	 * @param phoneCode	客户端代号，0：IOS，1：android
	 * @param firstRow	分页起始行
	 * @param rows		查询每页的数量
	 * @return
	 * @author zhangbo	2015年11月30日
	 */
	Integer getWorldListInvalidTotal(@Param("maxId")Integer maxId, @Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("phoneCode")Integer phoneCode, @Param("firstRow")Integer firstRow, @Param("limit")Integer rows);

}
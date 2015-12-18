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
	 * 根据最大id查询织图
	 * TODO 这个方法在优化后要删除
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
	 * TODO 这个方法在优化后要删除
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
	 * @param firstRow		分页起始行，传值为null，则查询全部
	 * @param rows			查询每页的数量，传值为null，则查询全部
	 * @param maxId
	 * @param startTime 起始时间
	 * @param endTime 	截止时间，用于查询设定的截止时间
	 * @return 
		*	2015年11月26日
		*	mishengliang
	 */
	List<ZTWorld> queryZombieWorld(@Param("firstRow")Integer firstRow,@Param("limit")Integer limit,@Param("maxId")Integer maxId,@Param("startTime")Date startTime,@Param("endTime")Date endTime);
	
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
	long queryZombieWorldTotal(@Param("maxId")Integer maxId,@Param("startTime")Date startTime,@Param("endTime")Date endTime);
	
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
	 * @param firstRow		分页起始行，传值为null，则查询全部
	 * @param rows			查询每页的数量，传值为null，则查询全部
	 * 
	 * @return List<ZTWorld>	织图对象集合
	 * 
	 * @author zhangbo	2015年11月27日
	 */
	@DataSource("slave")
	List<ZTWorld> getWorldListValid(@Param("maxId")Integer maxId, @Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("phoneCode")Integer phoneCode, @Param("firstRow")Integer firstRow, @Param("limit")Integer rows);
	
	/**
	 * 根据时间段查询有效织图总数
	 * 
	 * @param maxId		织图查询最大id，用于分页显示的
	 * @param startTime	查询起始时间
	 * @param endTime	查询结束时间
	 * @param phoneCode	客户端代号，0：IOS，1：android
	 * 
	 * @return total	在此时间段下有效织图总数
	 * 
	 * @author zhangbo	2015年11月30日
	 */
	@DataSource("slave")
	long getWorldValidTotal(@Param("maxId")Integer maxId, @Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("phoneCode")Integer phoneCode);

	/**
	 * 根据时间段查询无效织图集合
	 * TODO 此处在以后织图表结构分拆后，可以删除，因为删除表肯定是另外一张，就不在这里进行操作
	 * 
	 * @param maxId		织图查询最大id，用于分页显示的
	 * @param startTime	查询起始时间
	 * @param endTime	查询结束时间
	 * @param phoneCode	客户端代号，0：IOS，1：android
	 * @param firstRow		分页起始行，传值为null，则查询全部
	 * @param rows			查询每页的数量，传值为null，则查询全部
	 * 
	 * @return List<ZTWorld>	织图对象集合
	 * 
	 * @author zhangbo	2015年11月27日
	 */
	@DataSource("slave")
	List<ZTWorld> getWorldListInvalid(@Param("maxId")Integer maxId, @Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("phoneCode")Integer phoneCode, @Param("firstRow")Integer firstRow, @Param("limit")Integer rows);
	
	/**
	 * 根据时间段查询无效织图总数
	 * TODO 此处在以后织图表结构分拆后，可以删除，因为删除表肯定是另外一张，就不在这里进行操作
	 * 
	 * @param maxId		织图查询最大id，用于分页显示的
	 * @param startTime	查询起始时间
	 * @param endTime	查询结束时间
	 * @param phoneCode	客户端代号，0：IOS，1：android
	 * 
	 * @return total	在此时间段下无效织图总数
	 * 
	 * @author zhangbo	2015年11月30日
	 */
	@DataSource("slave")
	long getWorldInvalidTotal(@Param("maxId")Integer maxId, @Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("phoneCode")Integer phoneCode);
	
	
	/**
	 * 根据织图id集合查询织图
	 * 
	 * @param ids	织图id集合
	 * 
	 * @return List<ZTWorld>	织图对象集合
	 * 
	 * @author zhangbo	2015年12月3日
	 */
	@DataSource("slave")
	List<ZTWorld> getWorldListByIds(@Param("ids")Integer[] ids);
	
	
	/**
	 * 根据织图id集合查询织图
	 * 
	 * @param ids	织图id集合
	 * 
	 * @return List<ZTWorld>	织图对象集合
	 * 
	 * @author zhangbo	2015年12月3日
	 */
	@DataSource("slave")
	List<ZTWorld> getWorldListByIdsForValid(@Param("ids")Integer[] ids);
	
	/**
	 * 根据织图id集合查询织图
	 * 
	 * @param ids	织图id集合
	 * 
	 * @return List<ZTWorld>	织图对象集合
	 * 
	 * @author zhangbo	2015年12月3日
	 */
	@DataSource("slave")
	List<ZTWorld> getWorldListByIdsForInvalid(@Param("ids")Integer[] ids);
	
	
	/**
	 * 根据时间段与用户等级查询有效织图集合
	 * 
	 * @param userLevelId	用户等级id
	 * @param maxId			织图查询最大id，用于分页显示的
	 * @param startTime		查询起始时间
	 * @param endTime		查询结束时间
	 * @param firstRow		分页起始行，传值为null，则查询全部
	 * @param rows			查询每页的数量，传值为null，则查询全部
	 * 
	 * @return List<ZTWorld>	织图对象集合
	 * 
	 * @author zhangbo	2015年12月16日
	 */
	@DataSource("slave")
	List<ZTWorld> queryWorldByUserLevelValid(@Param("userLevelId")Integer userLevelId, @Param("maxId")Integer maxId, @Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("firstRow")Integer firstRow, @Param("limit")Integer rows);

	/**
	 * 根据时间段与用户等级查询有效织图总数
	 * 
	 * @param userLevelId	用户等级id
	 * @param maxId			织图查询最大id，用于分页显示的
	 * @param startTime		查询起始时间
	 * @param endTime		查询结束时间
	 * 
	 * @return total	在此时间段下有效织图总数
	 * 
	 * @author zhangbo	2015年12月16日
	 */
	@DataSource("slave")
	long getWorldByUserLevelValidTotal(@Param("userLevelId")Integer userLevelId, @Param("maxId")Integer maxId, @Param("startTime")Date startTime, @Param("endTime")Date endTime);

	/**
	 * 根据时间段与用户等级查询无效织图集合
	 * TODO 此处在以后织图表结构分拆后，可以删除，因为删除表肯定是另外一张，就不在这里进行操作
	 * 
	 * @param userLevelId	用户等级id
	 * @param maxId			织图查询最大id，用于分页显示的
	 * @param startTime		查询起始时间
	 * @param endTime		查询结束时间
	 * @param firstRow		分页起始行
	 * @param rows			查询每页的数量
	 * 
	 * @return List<ZTWorld>	织图对象集合
	 * 
	 * @author zhangbo	2015年12月16日
	 */
	@DataSource("slave")
	List<ZTWorld> queryWorldByUserLevelInvalid(@Param("userLevelId")Integer userLevelId, @Param("maxId")Integer maxId, @Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("firstRow")Integer firstRow, @Param("limit")Integer rows);

	/**
	 * 根据时间段与用户等级查询无效织图总数
	 * TODO 此处在以后织图表结构分拆后，可以删除，因为删除表肯定是另外一张，就不在这里进行操作
	 * 
	 * @param userLevelId	用户等级id
	 * @param maxId			织图查询最大id，用于分页显示的
	 * @param startTime		查询起始时间
	 * @param endTime		查询结束时间
	 * 
	 * @return total	在此时间段下无效织图总数
	 * 
	 * @author zhangbo	2015年12月16日
	 */
	@DataSource("slave")
	long getWorldByUserLevelInvalidTotal(@Param("userLevelId")Integer userLevelId, @Param("maxId")Integer maxId, @Param("startTime")Date startTime, @Param("endTime")Date endTime);

	/**
	 * 根据用户id集合分页查询织图集合
	 * 
	 * @param maxId			织图查询最大id，用于分页显示的
	 * @param authorIds	用户id集合
	 * @param firstRow		分页起始行
	 * @param rows			查询每页的数量
	 * 
	 * @return List<ZTWorld>	织图对象集合
	 * 
	 * @author zhangbo	2015年12月17日
	 */
	@DataSource("slave")
	List<ZTWorld> getWorldListByAuthorId(@Param("maxId")Integer maxId, @Param("authorIds")Integer[] authorIds, @Param("firstRow")Integer firstRow, @Param("limit")Integer rows);

	@DataSource("slave")
	List<ZTWorld> getWorldListByAuthorIdForValid(@Param("maxId")Integer maxId, @Param("authorIds")Integer[] authorIds, @Param("firstRow")Integer firstRow, @Param("limit")Integer rows);
	
	@DataSource("slave")
	List<ZTWorld> getWorldListByAuthorIdForInValid(@Param("maxId")Integer maxId, @Param("authorIds")Integer[] authorIds, @Param("firstRow")Integer firstRow, @Param("limit")Integer rows);
	
	/**
	 * 根据用户id集合查询织图总数
	 * 
	 * @param maxId			织图查询最大id，用于分页显示的
	 * @param authorIds	用户id集合
	 * 
	 * @return total	用户id对应的用户所发图总数
	 * 
	 * @author zhangbo	2015年12月17日
	 */
	long getWorldTotalByAuthorId(@Param("maxId")Integer maxId, @Param("authorIds")Integer[] authorIds);
	
	long getWorldTotalByAuthorIdForValid(@Param("maxId")Integer maxId, @Param("authorIds")Integer[] authorIds);
	
	long getWorldTotalByAuthorIdForInValid(@Param("maxId")Integer maxId, @Param("authorIds")Integer[] authorIds);

}
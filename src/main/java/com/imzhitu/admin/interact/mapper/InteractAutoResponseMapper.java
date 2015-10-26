package com.imzhitu.admin.interact.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.InteractAutoResponseDto;

public interface InteractAutoResponseMapper {
	/**
	 * 查询是否为马甲
	 * @param userId
	 * @return
	 */
	@DataSource("slave")
	boolean checkIsZoombie(Integer userId);
	
	/**
	 * 增加回复
	 * @param dto
	 */
	@DataSource("master")
	public void addResponse(InteractAutoResponseDto dto);
	
	/**
	 * 修改回复内容
	 * @param dto
	 */
	@DataSource("master")
	public void updateResponseContext(InteractAutoResponseDto dto);
	
	/**
	 * 查询为完成的回复
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<InteractAutoResponseDto> queryUncompleteResponse(InteractAutoResponseDto dto);
	
	/**
	 * 根据id来查询回复
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public List<InteractAutoResponseDto> queryResponseById(Integer id);
//	InteractAutoResponseDto queryUncompleteResponseByCommentId(Integer commentId);
	
	/**
	 * 根据id来查询回复组
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public List<InteractAutoResponseDto> queryResponseGroupById(InteractAutoResponseDto dto);
	
	/**
	 * 查询未查看的回复
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public List<InteractAutoResponseDto> queryUnCkResponse(InteractAutoResponseDto dto);
	
	/**
	 * 更新回复的完成性
	 * @param ids
	 */
	@DataSource("master")
	public void updateResponseCompleteByIds(Integer[] ids);
	
	/**
	 * 查询为完成回复的总数
	 * @param dto
	 * @return
	 */
	@DataSource("slave")
	public long getUnCompleteResponseCountByMaxId(InteractAutoResponseDto dto);
	
	/**
	 * 删除自动回复 by ids
	 * @param ids
	 */
	@DataSource("master")
	public void delAutoResponseByIds(Integer[] ids);
	
	/**
	 * 查询前一条评论
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public String queryPreComment(Integer id);
	
	/**
	 * 查询自动回复 by id
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public InteractAutoResponseDto queryRespnse(Integer id);
	
	/**
	 * 插入评论ID和马甲ID的关系表
	 * @param zombieId
	 * @param commentId 
		*	2015年9月6日
		*	mishengliang
	 */
	@DataSource("master")
	public void insertZombieAutoResponse(@Param("zombieWorldId")Integer zombieWorldId,@Param("commentId")Integer commentId);
	
	/**
	 *查询马甲织图对应对应的自动评论ID
	 * @param zombieId
	 * @return 
		*	2015年9月7日
		*	mishengliang
	 */
	public List<Integer> queryZombieCommentId(@Param("zombieWorldId")Integer zombieWorldId);
}

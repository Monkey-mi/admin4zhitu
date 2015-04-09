package com.imzhitu.admin.interact.mapper;

import java.util.List;

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
	public void addResponse(InteractAutoResponseDto dto);
	
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
}

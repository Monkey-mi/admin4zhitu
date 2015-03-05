package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.InteractAutoResponseDto;

public interface InteractAutoResponseMapper {
	boolean checkIsZoombie(Integer userId);
	public void addResponse(InteractAutoResponseDto dto);
	public List<InteractAutoResponseDto> queryUncompleteResponse(InteractAutoResponseDto dto);
	public List<InteractAutoResponseDto> queryResponseById(Integer id);
//	InteractAutoResponseDto queryUncompleteResponseByCommentId(Integer commentId);
	public List<InteractAutoResponseDto> queryUnCkResponse(InteractAutoResponseDto dto);
	public void updateResponseCompleteByIds(Integer[] ids);
	public long getUnCompleteResponseCountByMaxId(InteractAutoResponseDto dto);
	public void delAutoResponseByIds(Integer[] ids);
	public String queryPreComment(Integer id);
	public InteractAutoResponseDto queryRespnse(Integer id);
}

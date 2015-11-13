package com.imzhitu.admin.interact.dao;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.imzhitu.admin.common.pojo.InteractAutoResponseDto;

import java.util.Date;
import java.util.List;

@Deprecated
public interface InteractAutoResponseDao extends BaseDao{
	boolean checkIsZoombie(Integer uid);
	public void addResponse(Integer responseId,Integer commentId,Integer complete,Integer world_id,Integer author, Integer re_author);
	public List<InteractAutoResponseDto> queryUncompleteResponse(RowSelection rowSelection);
	public List<InteractAutoResponseDto> queryUncompleteResponse(Integer maxId,RowSelection rowSelection);
	public List<InteractAutoResponseDto> queryResponseById(Integer id);
//	InteractAutoResponseDto queryUncompleteResponseByCommentId(Integer commentId);
	public List<InteractAutoResponseDto> queryUnCkResponse(Date beginDate);
	public void updateResponseCompleteByIds(Integer[] ids);
	public long getUnCompleteResponseCountByMaxId(Integer maxId);
	public void delAutoResponseByIds(Integer[] ids);
}

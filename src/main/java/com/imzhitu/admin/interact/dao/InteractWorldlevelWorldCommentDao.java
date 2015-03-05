package com.imzhitu.admin.interact.dao;

import com.hts.web.common.dao.BaseDao;
import java.util.List;
import java.util.Date;
import com.imzhitu.admin.common.pojo.InteractWorldlevelWorldComment;

public interface InteractWorldlevelWorldCommentDao extends BaseDao{
	/**
	 * 查询织图等级中对应的织图评论
	 * @param worldId
	 * @return
	 */
	public List<InteractWorldlevelWorldComment> queryWorldlevelWorldCommentByWid(Integer worldId);
	
	/**
	 * 增加织图等级中对应的织图评论
	 * @param wid
	 * @param cid
	 * @param complete
	 * @param dateAdd
	 */
	public void addWorldlevelWorldComment(Integer wid,Integer cid,Integer complete,Date dateAdd);
	
	/**
	 * 更新完成性
	 * @param wid
	 * @param complete
	 */
	public void updateWorldlevelWorldCommentComplete(Integer wid,Integer complete);

}

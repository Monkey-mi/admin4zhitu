package com.imzhitu.admin.interact.dao;

import com.hts.web.common.dao.BaseDao;
import java.util.List;
import java.util.Date;
import com.imzhitu.admin.common.pojo.InteractWorldlevelWorldLabel;

public interface InteractWorldlevelWorldLabelDao extends BaseDao{
	/**
	 * 织图等级对应的织图标签
	 * @param worldId
	 * @return
	 */
	public List<InteractWorldlevelWorldLabel> queryWorldlevelWorldLabelByWid(Integer worldId);
	
	/**
	 * 增加织图等级对应的标签 
	 * @param wid
	 * @param lid
	 * @param complete
	 * @param dateAdd
	 */
	public void addWorldlevelWorldLabel(Integer wid,Integer lid,Integer complete,Date dateAdd);
}

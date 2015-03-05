package com.imzhitu.admin.interact.dao;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;

import java.util.List;

import com.imzhitu.admin.common.pojo.ZTWorldLevelDto;

public interface InteractWorldlevelDao extends BaseDao {
	/**
	 * 查询织图等级列表
	 * @param rowSelection
	 * @return
	 */
	public List<ZTWorldLevelDto> QueryWorldlevelList(RowSelection rowSelection);
	
	/**
	 * 根据id查询织图等级
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ZTWorldLevelDto QueryWorldlevelById(Integer id)throws Exception;
	
	/**
	 * 根据ids删除织图等级
	 * @param ids
	 * @throws Exception
	 */
	public void DeleteWorldLevelByIds(Integer[] ids) throws Exception;
	
	/**
	 * 增加织图等级
	 * @param worldlevelDto
	 * @throws Exception
	 */
	public void AddWorldlevel(ZTWorldLevelDto worldlevelDto)throws Exception;
	
	/**
	 * 更新织图等级
	 * @param worldlevelDto
	 * @throws Exception
	 */
	public void UpdateWorldlevel(ZTWorldLevelDto worldlevelDto)throws Exception;
	
	/**
	 * 获取织图等级总数
	 * @param maxId
	 * @return
	 */
	public long GetWorldlevelCountByMaxId(Integer maxId);
	
	/**
	 * 根据maxId查询
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<ZTWorldLevelDto>QueryWorldlevelListByMaxId(Integer maxId,RowSelection rowSelection);
	
	/**
	 * 查询织图等级列表
	 * @return
	 */
	public List<ZTWorldLevelDto> QueryWorldLevel();
	
	/**
	 * 根据worldId查询用户id
	 * @param wid
	 * @return
	 */
	public Integer QueryUIDByWID(Integer wid);
}

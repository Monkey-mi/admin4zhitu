package com.imzhitu.admin.interact.service;

import java.util.Map;
import java.util.List;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.ZTWorldLevelDto;

public interface InteractWorldlevelService extends BaseService {
	/**
	 * 查询织图等级列表
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void QueryWorldlevelList(int maxId,int start,int limit,Map<String ,Object> jsonMap) throws Exception;
	
	/**
	 * 根据ids删除织图等级
	 * @param idsStr
	 * @throws Exception
	 */
	public void DeleteWorldlevelByIds(String idsStr)throws Exception;
	
	/**
	 * 根据id查询织图等级
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ZTWorldLevelDto QueryWorldlevelById(Integer id)throws Exception;
	
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
	 * 增加织图等级
	 * @param world_id
	 * @param id   织图等级（前台指定的数据）
	 * @param labelsStr
	 * @param comments
	 * @throws Exception
	 */
	public void AddLevelWorld(Integer world_id,Integer id,String labelsStr,String comments)throws Exception;
	
	/**
	 * 增加等级织图互动
	 */
	public void AddTypeWorldInteract(Integer worldId,Integer worldLevelId,String commentStr)throws Exception;
	
	/**
	 * 查询织图等级
	 * @return
	 * @throws Exception
	 */
	public List<ZTWorldLevelDto> QueryWorldLevel()throws Exception;
	
	/**
	 * 跟新
	 */
	public void updateWorldLevelByRowJson(String rowJson)throws Exception;
	
}

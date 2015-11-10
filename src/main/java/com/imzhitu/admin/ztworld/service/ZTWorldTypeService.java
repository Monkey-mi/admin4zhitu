package com.imzhitu.admin.ztworld.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.ZTWorldTypeLabelDto;

/**
 * <p>
 * 织图分类管理业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2013-1-15
 * @author ztj
 *
 */
public interface ZTWorldTypeService extends BaseService {

	/**
	 * 保存分类织图
	 * 
	 * @param worldId
	 * @param typeId
	 * @param worldType
	 * @param recommenderId
	 * @throws Exception
	 */
	public void saveTypeWorld(Integer worldId, Integer typeId, String worldType,
			Integer recommenderId) throws Exception;
	
	
	/**
	 * 更新分类织图
	 * 
	 * @param worldId
	 * @param typeId
	 * @param worldType
	 * @param recommenderId
	 * @throws Exception
	 */
	public void updateTypeWorld(Integer worldId, Integer typeId, String worldType,
			Integer recommenderId) throws Exception;
	
	/**
	 * 构建分类织图列表
	 * 
	 * @param maxId
	 * @param typeId
	 * @param valid
	 * @param superb
	 * @param weight
	 * @param recommenderId
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildTypeWorld(Integer worldId,Date beginDate,Date endDate,int maxId, Integer typeId, Integer valid, Integer superb, Integer weight,Integer recommenderId,
			String sort, String order,Integer isSorted, int page, int rows, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建织图标签ids
	 * 
	 * @param worldId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLabelIds(Integer worldId, Map<String, Object> jsonMap) throws Exception;

	/**
	 * 更新精品标记
	 * 
	 * @param id
	 * @param superb
	 */
	public void updateTypeWorldSuperb(Integer id, Integer superb) throws Exception;
	
	/**
	 * 更新权重
	 * 
	 * @param id
	 * @param weight
	 */
	public void updateTypeWorldWeight(Integer id, Integer weight,Integer timeUpdate) throws Exception;
	
	/**
	 * 根据织图id删除分类织图
	 * 
	 * @param worldId
	 * @throws EXception
	 */
	public void deleteTypeWorldByWorldId(Integer worldId,Integer operatorId) throws Exception;
	
	/**
	 * 根据ids批量分类织图
	 * 
	 * @param idsStr
	 */
	public void batchDeleteTypeWorld(String idsStr,Integer operatorId) throws Exception;
	
	/**
	 * 根据织图id或者worldId删除分类织图
	 * @param id
	 * @param worldId
	 * @param operatorId
	 * @throws Exception
	 */
	public void deleteTypeWorld(Integer id,Integer worldId,Integer operatorId)throws Exception;
	
	/**
	 * 更新排序
	 * 
	 * @param idStrs
	 */
	public void batchUpdateTypeWorldSerial(String[] idStrs);
	
	/**
	 * 增加织图排序计划
	 * @param idStrs
	 * @param schedula
	 */
	public void addUpdateTypeWorldSerialSchedula(String[] idStrs,Date schedula,Integer operatorId);
	
	/**
	 * 批量更新所有推荐分类织图有效性
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void batchUpdateRecommendTypeWorldValid() throws Exception;
	
	/**
	 * 批量更新所有分类织图有效性
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void batchUpdateTypeWorldValid(String idsStr,String widsStr,Integer operatorId) throws Exception;
	
	/**
	 * 查询分类标签列表
	 * 
	 * @param typeId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLabel(int typeId, Map<String, Object> jsonMap) throws Exception;

	/**
	 * 保存分类
	 * 
	 * @param typeName
	 * @param typeDesc
	 */
	public void saveType(String typeName, String typeDesc) throws Exception;
	
	/**
	 * 构建分类列表
	 * 
	 * @param maxSerial
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildType(int maxSerial, int start, int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 批量更新分类有效性
	 * 
	 * @param idsStr
	 * @throws Exception
	 */
	public void batchUpdateTypeValid(String idsStr, Integer valid) throws Exception;
	
	/**
	 * 保存标签
	 * 
	 * @param labelFile
	 * @throws Exception
	 */
	public void saveLabel(Integer typeId, File labelFile) throws Exception;
	
	/**
	 * 执行分类织图计划
	 */
	public void performTypeWorldSchedula();
	
	/**
	 * 修改精选织图点评
	 */
	public void updateTypeWorldReview(Integer worldId,String review) throws Exception;
	
	/**
	 * 更新精选的缓存
	 * @throws Exception
	 */
	public void updateTypeWorldCache()throws Exception;
	/**
	 * 更新分类缓存
	 * 
	 * @throws Exception
	 */
	public void updateTypeCache() throws Exception;
	
	/**
	 * 查询所有的分类，用以做下拉列表
	 */
	public List<ZTWorldTypeLabelDto> queryAllType()throws Exception;

}

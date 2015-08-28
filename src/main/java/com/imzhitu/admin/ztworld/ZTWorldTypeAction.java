package com.imzhitu.admin.ztworld;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.common.pojo.ZTWorldTypeLabelDto;
import com.imzhitu.admin.ztworld.service.ZTWorldTypeService;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * <p>
 * 织图分类管理模块控制器
 * </p>
 * 
 * 创建时间：2014-1-16
 * @author ztj
 *
 */
public class ZTWorldTypeAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8783496535887835225L;
	
	private static Logger logger = Logger.getLogger(ZTWorldTypeAction.class);
	
	private Integer id;
	private String ids;
	private String wids;
	private Integer typeId;
	private Integer valid;
	private Integer superb;
	private Integer weight;
	private Integer recommenderId;
	private Integer worldId;
	private String worldIds;
	private String labelIds;
	private String worldType;
	private String worldLabel;
	private Boolean isAdd;
	private Boolean isRecommend;
	
	private String typeName;
	private String typeDesc;
	
	private Integer isSorted;//是否被排序
	private String schedula;
	
	private Date beginDate;
	private Date endDate;
	
	private String myOrder;//定义自己的排序
	private String mySort;//定义自己的排序
	
	private String review;//精选点评
	
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}

	@Autowired
	private ZTWorldTypeService worldTypeService;
	
	
	public String getMyOrder() {
		return myOrder;
	}
	public void setMyOrder(String myOrder) {
		this.myOrder = myOrder;
	}
	public String getMySort() {
		return mySort;
	}
	public void setMySort(String mySort) {
		this.mySort = mySort;
	}


	
	public void setBeginDate(Date beginDate){
		this.beginDate = beginDate;
	}
	public Date getBeginDate(){
		return this.beginDate;
	}
	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}
	public Date getEndDate(){
		return this.endDate;
	}
	
	public void setSchedula(String schedula){
		this.schedula = schedula;
	}
	
	public String getSchedula(){
		return this.schedula;
	}
	
	public void setIsSorted(Integer isSorted){
		this.isSorted = isSorted;
	}
	public Integer getIsSorted(){
		return this.isSorted;
	}
	public ZTWorldTypeService getWorldTypeService() {
		return worldTypeService;
	}

	public void setWorldTypeService(ZTWorldTypeService worldTypeService) {
		this.worldTypeService = worldTypeService;
	}
	
	/**
	 * 查询分类
	 */
	public String queryAllType(){
		PrintWriter out = null;
		try{
			out = response.getWriter();
			List<ZTWorldTypeLabelDto> list = worldTypeService.queryAllType();
			JSONArray jsArray = JSONArray.fromObject(list);
			out.print(jsArray.toString());
			out.flush();
		}catch(Exception e) {
			jsonMap.clear();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(json.toString());
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}
	
	
	/**
	 * 查询分类
	 * 
	 * @return
	 */
	public String queryType() {
		try {
			worldTypeService.buildType(maxSerial, page, rows, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存分类
	 * 
	 * @return
	 */
	public String saveType() {
		try {
			worldTypeService.saveType(typeName, typeDesc);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新分类有效性
	 * 
	 * @param idsStr
	 * @param valid
	 * @return
	 */
	public String updateTypeValid() {
		try {
			worldTypeService.batchUpdateTypeValid(ids, valid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询分类织图
	 * 
	 * @return
	 */
	public String queryTypeWorld() {
		try {
			if(mySort == null){
				setMySort(sort);
			}
			if(myOrder == null){
				setMyOrder(order);
			}
				
			worldTypeService.buildTypeWorld(beginDate,endDate,maxSerial, typeId, valid, superb, weight,recommenderId, mySort, myOrder, isSorted,page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据织图id查询分类信息
	 * @return
	 */
	public String queryLabelIdByWorldId() {
		try {
			worldTypeService.buildLabelIds(worldId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存分类织图
	 * 
	 * @return
	 */
	public String saveTypeWorld() {
		try {
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			worldTypeService.saveTypeWorld(worldId, typeId, worldType,user.getId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 根据织图id删除分类织图
	 * 
	 * @return
	 */
	public String deleteTypeWorldByWorldId() {
		try {
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			worldTypeService.deleteTypeWorldByWorldId(worldId,user.getId());
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 批量删除分类织图
	 * 
	 * @return
	 */
	public String deleteTypeWorlds() {
		try {
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			worldTypeService.batchDeleteTypeWorld(ids,user.getId());
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新精品标记
	 * 
	 * @return
	 */
	public String updateTypeWorldSuperb() {
		try {
			worldTypeService.updateTypeWorldSuperb(id, superb);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新权重
	 * @return
	 */
	public String updateTypeWorldWeight() {
		try {
			worldTypeService.updateTypeWorldWeight(id, weight);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 更新分类织图序号
	 * 
	 * @return
	 */
	public String updateTypeWorldSerial() {
		String[] ids = request.getParameterValues("reIndexId");
		try {
			worldTypeService.batchUpdateTypeWorldSerial(ids);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新分类织图有效性
	 * 
	 * @return
	 */
	public String updateTypeWorldValid() {
		try {
			AdminUserDetails user = (AdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			worldTypeService.batchUpdateTypeWorldValid(ids,wids,user.getId());
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch(Exception e) {
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询分类标签
	 * 
	 * @return
	 */
	public String queryAllLabelByTypeId() {
		try {
			worldTypeService.buildLabel(typeId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 
	 * @return
	 */
	public String addUpdateTypeWorldSerialSchedula(){
		String[] ids = request.getParameterValues("reIndexId");
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(schedula==null||schedula.equals("")){
				worldTypeService.batchUpdateTypeWorldSerial(ids);
				JSONUtil.optSuccess(jsonMap);
			}else{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				worldTypeService.addUpdateTypeWorldSerialSchedula(ids, df.parse(schedula),user.getId());
				JSONUtil.optSuccess(jsonMap);
			}
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新精选点评
	 * @return
	 */
	public String updateTypeWorldReview(){
		try{
			worldTypeService.updateTypeWorldReview(worldId, review);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 更新精选缓存
	 * @return
	 */
	public String updateTypeWorldCache(){
		try{
			worldTypeService.updateTypeWorldCache();
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 更新分类缓存
	 */
	public String updateTypeCache() {
		try{
			worldTypeService.updateTypeCache();
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setWids(String wids){
		this.wids = wids;
	}
	public String getWids(){
		return this.wids;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getSuperb() {
		return superb;
	}

	public void setSuperb(Integer superb) {
		this.superb = superb;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getWorldIds() {
		return worldIds;
	}

	public void setWorldIds(String worldIds) {
		this.worldIds = worldIds;
	}
	
	public String getLabelIds() {
		return labelIds;
	}

	public void setLabelIds(String labelIds) {
		this.labelIds = labelIds;
	}

	public String getWorldType() {
		return worldType;
	}

	public void setWorldType(String worldType) {
		this.worldType = worldType;
	}

	public String getWorldLabel() {
		return worldLabel;
	}

	public void setWorldLabel(String worldLabel) {
		this.worldLabel = worldLabel;
	}
	
	public Boolean getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(Boolean isAdd) {
		this.isAdd = isAdd;
	}
	
	public Boolean getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
	}
	

	public Integer getRecommenderId() {
		return recommenderId;
	}

	public void setRecommenderId(Integer recommenderId) {
		this.recommenderId = recommenderId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	
}

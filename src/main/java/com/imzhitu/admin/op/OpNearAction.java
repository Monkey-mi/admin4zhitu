package com.imzhitu.admin.op;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.op.service.OpNearService;

/**
 * 附近模块 控制层
 * @author zxx 2015-12-4 15:35:35
 *
 */
public class OpNearAction extends BaseCRUDAction{

	private static final long serialVersionUID = -4388190459643752462L;
	private Integer id;
	private Integer cityId;
	private String labelName;
	private Double longitude;
	private Double latitude;
	private String description;
	private String bannerUrl;
	private Integer serial;
	private Integer cityGroupId;
	
	private String idsStr;
	
	@Autowired 
	private OpNearService nearService;
	
	
	/**
	 * 查询附近标签
	 * @return
	 * @author zxx 2015-12-4 16:59:28
	 */
	public String queryNearLabel(){
		try{
			nearService.queryNearLabel(id, cityId, maxSerial, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 增加附近标签
	 * @return
	 * @author zxx 2015-12-4 16:59:28
	 */
	public String insertNearLabel(){
		try{
			nearService.insertNearLabel(id, cityId, labelName, longitude, latitude, description, bannerUrl, serial);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 批量删除附近标签
	 * @return
	 * @author zxx 2015-12-4 16:59:28
	 */
	public String batchDeleteNearLabel(){
		try{
			nearService.batchDeleteNearLabel(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新附近标签
	 * @return
	 * @author zxx 2015-12-4 16:59:28
	 */
	public String updateNearLabel(){
		try{
			nearService.updateNearLabel(id, cityId, labelName, longitude, latitude, description, bannerUrl, serial);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 增加附近城市分组
	 * @return
	 * @author zxx 2015-12-4 16:59:28
	 */
	public String addCityGroup(){
		try{
			nearService.insertNearCityGroup(description);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 批量删除附近城市分组
	 * @return
	 * @author zxx 2015-12-4 16:59:28
	 */
	public String batchDeleteCityGroup(){
		try{
			nearService.batchDeleteNearCityGroup(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询附近城市分组
	 * @return
	 * @author zxx 2015-12-4 16:59:28
	 */
	public String queryCityGroupList(){
		try{
			nearService.queryNearCityGroup(id, maxSerial, page,	rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询附近推荐城市
	 * @return
	 * @author zxx 2015-12-4 16:59:28
	 */
	public String queryRecommendCity(){
		try{
			nearService.queryNearRecommendCity(id, cityId, cityGroupId, maxSerial, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 增加附近推荐城市
	 * @return
	 * @author zxx 2015-12-4 16:59:28
	 */
	public String addRecommendCity(){
		try{
			nearService.insertNearRecommendCity(cityId, cityGroupId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 批量删除附近推荐城市
	 * @return
	 * @author zxx 2015-12-4 16:59:28
	 */
	public String batchDeleteRecommendCity(){
		try{
			nearService.batchDeleteNearRecommendCity(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS,jsonMap);
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
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBannerUrl() {
		return bannerUrl;
	}
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public String getIdsStr() {
		return idsStr;
	}

	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}
	
	public Integer getCityGroupId() {
		return cityGroupId;
	}

	public void setCityGroupId(Integer cityGroupId) {
		this.cityGroupId = cityGroupId;
	}

}
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
	
	private String idsStr;
	
	@Autowired 
	private OpNearService nearService;
	
	public String queryNearLabel(){
		try{
			nearService.queryNearLabel(id, cityId, maxSerial, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String insertNearLabel(){
		try{
			nearService.insertNearLabel(id, cityId, labelName, longitude, latitude, description, bannerUrl, serial);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchDeleteNearLabel(){
		try{
			nearService.batchDeleteNearLabel(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS,jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String updateNearLabel(){
		try{
			nearService.updateNearLabel(id, cityId, labelName, longitude, latitude, description, bannerUrl, serial);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS,jsonMap);
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

}

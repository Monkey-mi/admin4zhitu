package com.imzhitu.admin.interact;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.interact.service.InteractZombieService;

public class InteractZombieWorldAction extends BaseCRUDAction{
	private static final long serialVersionUID = -2234348047212019979L;
	private Integer id;	
	private String worldName;
	private String worldDesc;
	private String worldLabel;
	private String worldLabelIds;
	private Integer authorId;
	private String coverPath;
	private String titlePath;
	private String thumbTitlePath;
	private Double longitude;
	private Double latitude;
	private String locationAddr;
	private Integer size;
	private Integer complete;
	private String childs;
	private String imgPath;
	private Integer titleId;
	private Date addDate;
	private Date modifyDate;
	

	private String ids;//批量发织图
	private Date begin;//开始执行计划的时间
	private Integer timeSpan;//
	
	

	@Autowired
	private InteractZombieService service;
	
	/**
	 * 分页查询
	 */
	public String queryZombieWorldForTable(){
		try{
			service.queryZombieWorldForTable(maxId, addDate, modifyDate, page, rows, complete, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 检查图片是否被下载过
	 * 
	 * @return
	 */
	public String beenDownload(){
		
		try{
			Integer result = service.beenDownload(imgPath);
			JSONUtil.optResult(result, "success", jsonMap);
		}catch(Exception e){
			JSONUtil.optResult(-1, "fail", jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存编辑完的数据格式
	 * @return
	 */
	public String saveZombieWorld(){
		try{
			service.saveZombieWorld(childs,titleId,authorId,worldName,worldDesc,worldLabel,null,
					coverPath,titlePath,thumbTitlePath,longitude,latitude,locationAddr,size);
			JSONUtil.optResult(0, "success", jsonMap);
		}catch(Exception e){
			JSONUtil.optResult(-1, "fail", jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 下载编辑过的数据，用以上传
	 * @return
	 */
	public String queryZombieWorldInfo(){
		try{
			service.queryZombieWorld(jsonMap,20);
			JSONUtil.optResult(0, "success", jsonMap);
		}catch(Exception e){
			JSONUtil.optResult(-1, "fail", jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 发布织图
	 * @return
	 */
	public String saveZombieWorldToHtWorld(){
		try{
			service.saveZombieWorldToHtWorld(id);
			JSONUtil.optResult(0, "success", jsonMap);
		}catch(Exception e){
			JSONUtil.optResult(-1, "fail", jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 批量发布马甲织图
	 * @return
	 */
	public String batchSaveZombieWorldToHTWorld(){
		try{
			service.batchSaveZombieWorldToHTWorld(ids, begin, timeSpan);
			JSONUtil.optResult(0, "success", jsonMap);
		}catch(Exception e){
			JSONUtil.optResult(-1, "fail", jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getChilds() {
		return childs;
	}
	public void setChilds(String childs) {
		this.childs = childs;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWorldName() {
		return worldName;
	}
	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}
	public String getWorldDesc() {
		return worldDesc;
	}
	public void setWorldDesc(String worldDesc) {
		this.worldDesc = worldDesc;
	}
	public String getWorldLabel() {
		return worldLabel;
	}
	public void setWorldLabel(String worldLabel) {
		this.worldLabel = worldLabel;
	}
	public String getWorldLabelIds() {
		return worldLabelIds;
	}
	public void setWorldLabelIds(String worldLabelIds) {
		this.worldLabelIds = worldLabelIds;
	}
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	public String getCoverPath() {
		return coverPath;
	}
	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}
	public String getTitlePath() {
		return titlePath;
	}
	public void setTitlePath(String titlePath) {
		this.titlePath = titlePath;
	}
	public String getThumbTitlePath() {
		return thumbTitlePath;
	}
	public void setThumbTitlePath(String thumbTitlePath) {
		this.thumbTitlePath = thumbTitlePath;
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
	public String getLocationAddr() {
		return locationAddr;
	}
	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getComplete() {
		return complete;
	}
	public void setComplete(Integer complete) {
		this.complete = complete;
	}
	public Integer getTitleId() {
		return titleId;
	}

	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
	}
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	


	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Integer getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(Integer timeSpan) {
		this.timeSpan = timeSpan;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
}

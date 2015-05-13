package com.imzhitu.admin.op;

import java.io.PrintWriter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.OpChannelV2Dto;
import com.imzhitu.admin.op.service.OpChannelV2Service;

public class OpChannelV2Action extends BaseCRUDAction{

	private static final long serialVersionUID = -8394229282973325347L;
	
	@Autowired
	private OpChannelV2Service opChannelV2Service;
	
	public String insertOpChannel(){
		try{
			opChannelV2Service.insertOpChannel(ownerId, channelName, channelTitle, subtitle, channelDesc, channelIcon, subIcon, channelTypeId,
					channelLabelNames, channelLabelIds, 0, 0, 0, 0, 0, Tag.TRUE, Tag.FALSE, 0, Tag.FALSE, Tag.FALSE, Tag.TRUE);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String deleteOpChannel(){
		try{
			opChannelV2Service.deleteOpChannel(channelId, ownerId);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String updateOpChannel(){
		try{
			opChannelV2Service.updateOpChannel(channelId, ownerId, channelName, channelTitle, subtitle, channelDesc, channelIcon, subIcon, channelTypeId,
					channelLabelNames, channelLabelIds, worldCount, worldPictureCount, memberCount, superbCount, childCountBase, superb, valid, serial, danmu, moodFlag, worldFlag);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	public String queryOpChannel(){
		try{
			opChannelV2Service.queryOpChannel(channelId, ownerId, Tag.TRUE, valid, serial, danmu, moodFlag, worldFlag, start, rows, channelId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询频道标签
	 * @return
	 */
	public String queryOpChannelLabel(){
		PrintWriter out = null;
		try{
			out = response.getWriter();
			JSONArray jsonArray = opChannelV2Service.queryOpChannelLabel(channelLabelNames);
			out.print(jsonArray.toString());
			out.flush();
			//JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			jsonMap.clear();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(json.toString());
			out.flush();
		}finally{
			out.close();
		}
		return null;
	}
	
	
	public String queryOpChannelById(){
		try{
			OpChannelV2Dto dto = opChannelV2Service.queryOpChannelById(channelId);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, dto, OptResult.JSON_KEY_OBJ, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchUpdateChannelValid(){
		try{
			opChannelV2Service.batchUpdateValid(channelIdsStr,valid);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	private Integer channelId;			//id
	private Integer ownerId;			//拥有者ID
	private String ownerName;			//拥有者名称
	private String channelName;			//频道名称
	private String channelTitle;		//频道标题
	private String subtitle;			//频道副标题
	private String channelDesc;			//频道描述
	private String channelIcon;			//频道icon
	private String subIcon;				//副Icon
	private Integer channelTypeId;		//频道类型ID
	private String channelTypeName;		//频道类型名称
	private String channelLabelNames;	//频道标签名称，eg。label_A,label_B,...
	private String channelLabelIds;		//频道标签ids，eg: label_A_id,label_B_id....
	private Integer worldCount;			//织图总数
	private Integer worldPictureCount;	//图片总数
	private Integer memberCount;		//频道成员总数
	private Integer  superbCount;		//精选总数
	private Integer childCountBase;		//图片基数
	private Long createTime;			//创建时间
	private Long lastModifiedTime;		//最后修改时间
	private Integer superb;				//精选标记。0非精选。1精选
	private Integer valid;				//有效性	0无效。1有效
	private Integer serial;				//序号
	private Integer danmu;				//弹幕标记。0非弹幕，1弹幕
	private Integer moodFlag;			//发心情标记
	private Integer worldFlag;			//织图标记
	
	private Integer orderBy;			//排序
	private String channelIdsStr;		//channel id array string
	
	
	public Integer getMoodFlag() {
		return moodFlag;
	}
	public void setMoodFlag(Integer moodFlag) {
		this.moodFlag = moodFlag;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Integer getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelTitle() {
		return channelTitle;
	}
	public void setChannelTitle(String channelTitle) {
		this.channelTitle = channelTitle;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getChannelDesc() {
		return channelDesc;
	}
	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
	}
	public String getChannelIcon() {
		return channelIcon;
	}
	public void setChannelIcon(String channelIcon) {
		this.channelIcon = channelIcon;
	}
	public String getSubIcon() {
		return subIcon;
	}
	public void setSubIcon(String subIcon) {
		this.subIcon = subIcon;
	}
	public Integer getChannelTypeId() {
		return channelTypeId;
	}
	public void setChannelTypeId(Integer channelTypeId) {
		this.channelTypeId = channelTypeId;
	}
	public String getChannelTypeName() {
		return channelTypeName;
	}
	public void setChannelTypeName(String channelTypeName) {
		this.channelTypeName = channelTypeName;
	}
	public String getChannelLabelNames() {
		return channelLabelNames;
	}
	public void setChannelLabelNames(String channelLabelNames) {
		this.channelLabelNames = channelLabelNames;
	}
	public String getChannelLabelIds() {
		return channelLabelIds;
	}
	public void setChannelLabelIds(String channelLabelIds) {
		this.channelLabelIds = channelLabelIds;
	}
	public Integer getWorldCount() {
		return worldCount;
	}
	public void setWorldCount(Integer worldCount) {
		this.worldCount = worldCount;
	}
	public Integer getWorldPictureCount() {
		return worldPictureCount;
	}
	public void setWorldPictureCount(Integer worldPictureCount) {
		this.worldPictureCount = worldPictureCount;
	}
	public Integer getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}
	public Integer getSuperbCount() {
		return superbCount;
	}
	public void setSuperbCount(Integer superbCount) {
		this.superbCount = superbCount;
	}
	public Integer getChildCountBase() {
		return childCountBase;
	}
	public void setChildCountBase(Integer childCountBase) {
		this.childCountBase = childCountBase;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(Long lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public Integer getSuperb() {
		return superb;
	}
	public void setSuperb(Integer superb) {
		this.superb = superb;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	public Integer getDanmu() {
		return danmu;
	}
	public void setDanmu(Integer danmu) {
		this.danmu = danmu;
	}
	public Integer getWorldFlag() {
		return worldFlag;
	}
	public void setWorldFlag(Integer worldFlag) {
		this.worldFlag = worldFlag;
	}
	public Integer getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	public String getChannelIdsStr() {
		return channelIdsStr;
	}

	public void setChannelIdsStr(String channelIdsStr) {
		this.channelIdsStr = channelIdsStr;
	}
	

}

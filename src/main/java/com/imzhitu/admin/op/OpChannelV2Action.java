package com.imzhitu.admin.op;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.OpChannelLink;
import com.hts.web.common.pojo.OpChannelTheme;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.OpChannelV2Dto;
import com.imzhitu.admin.constant.LoggerKeies;
import com.imzhitu.admin.op.service.OpChannelV2Service;
import com.imzhitu.admin.privileges.dao.RoleDao;

public class OpChannelV2Action extends BaseCRUDAction{

	private static final long serialVersionUID = -8394229282973325347L;
	
	private static Logger log = Logger.getLogger(LoggerKeies.CHANNEL_V2);
	
	@Autowired
	private OpChannelV2Service opChannelV2Service;
	
	@Autowired
	private RoleDao roleDao;
	
	/**
	 * 插入频道
	 * @return
	 */
	public String insertOpChannel(){
		try{
			opChannelV2Service.insertOpChannel(ownerId, channelName, channelTitle, subtitle, channelDesc, channelIcon, channelTypeId,
					channelLabelNames, channelLabelIds, 0, 0, 0, 0, 0, Tag.FALSE, Tag.FALSE, 0, Tag.FALSE, Tag.FALSE, Tag.TRUE,themeId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(),e);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除频道
	 * @return
	 */
	public String deleteOpChannel(){
		try{
			opChannelV2Service.deleteOpChannel(channelId, ownerId);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(),e);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新频道
	 * @return
	 */
	public String updateOpChannel(){
		try{
			opChannelV2Service.updateOpChannel(channelId, ownerId, channelName, channelTitle, subtitle, channelDesc, channelIcon, channelTypeId,
					channelLabelNames, channelLabelIds, worldCount, worldPictureCount, memberCount, superbCount, childCountBase, superb, valid, serial, danmu, moodFlag, worldFlag,themeId);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(),e);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 分页查询频道
	 * @return
	 */
    public String queryOpChannel() {
	try {
	    // 判断是否置顶，置顶传递1，不置顶传递null，因为是查询，若不置顶，则设置为null，表示不查询top字段
	    Integer top = isTopFlag() ? 1 : null;
	    opChannelV2Service.queryOpChannel(channelId, channelName,
		    channelTypeId, ownerId, superb, valid, top, serial, danmu,
		    moodFlag, worldFlag, themeId, page, rows, maxId, jsonMap);
	    JSONUtil.optSuccess(jsonMap);
	} catch (Exception e) {
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	    log.error(e.getMessage(),e);
	}
	return StrutsKey.JSON;
    }
	
	/**
	 * 查询频道标签 ，根据名称模糊查询，查询出匹配的所有标签
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
			log.error(e.getMessage(),e);
		}finally{
			out.close();
		}
		return null;
	}
	
	/**
	 * 根据频道查询与该频道关联的所有标签
	 * @return
	 */
	public String queryOpChannelLabelList(){
	    try{
		List<Map<String, Object>> queryOpChannelLabelList = opChannelV2Service.queryOpChannelLabelList(getChannelId());
		JSONUtil.optResult(OptResult.OPT_SUCCESS, queryOpChannelLabelList, OptResult.ROWS, jsonMap);
	    }catch(Exception e){
		JSONUtil.optFailed(e.getMessage(), jsonMap);
		log.error(e.getMessage(),e);
	    }
	    return StrutsKey.JSON;
	}
	
	/**
	 * 根据ID查询频道
	 * @return
	 */
	public String queryOpChannelByIdOrName(){
		try{
			OpChannelV2Dto dto = opChannelV2Service.queryOpChannelByIdOrName(channelId,channelName);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, dto, OptResult.JSON_KEY_OBJ, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(),e);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 批量更新有效性
	 * @return
	 */
	public String batchUpdateChannelValid(){
		try{
			opChannelV2Service.batchUpdateValid(channelIdsStr,valid);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(),e);
		}
		return StrutsKey.JSON;
	}
	
	
    public String queryOpChannelByAdminUserId() {
	try {
	    if (roleDao.isSuperOrOpAdminCurrentLogin()) {
		opChannelV2Service.queryOpChannel(channelId, channelName,
			channelTypeId, ownerId, superb, valid, null, serial,
			danmu, moodFlag, worldFlag, themeId, page, rows, maxId,
			jsonMap);
	    } else {
		opChannelV2Service.queryOpChannelByAdminUserId(channelId,
			channelName, channelTypeId, getCurrentLoginUserId(),
			jsonMap);
	    }
	} catch (Exception e) {
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	    log.error(e.getMessage(),e);
	}
	return StrutsKey.JSON;
    }
	
	public String batchInsertWorldToChannel(){
		try{
			opChannelV2Service.batchInsertWorldToChannel(channelId, worldAndAuthorIdsStr);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(),e);
		}
		return StrutsKey.JSON;
	}
	
    /**
     * 刷新频道缓存
     *
     * @return
     * @author zhangbo 2015年6月10日
     */
    public String refreshCache() {
	try {
	    opChannelV2Service.updateChannelCache();
	    JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
	} catch (Exception e) {
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	    log.error(e.getMessage(),e);
	}
	return StrutsKey.JSON;
    }
    
    /**
     * 查询关联频道返回集合
     *
     * @return
     * @author zhangbo 2015年6月10日
     */
    public String queryRelatedChannel() {
	try {
	    List<OpChannelLink> queryRelatedChannelList = opChannelV2Service.queryRelatedChannelList(getChannelId());
	    JSONUtil.optResult(OptResult.OPT_SUCCESS, queryRelatedChannelList, OptResult.ROWS, jsonMap);
	} catch (Exception e) {
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	    log.error(e.getMessage(),e);
	}
	return StrutsKey.JSON;
    }
    
    /**
     * 添加关联频道
     *
     * @return
     * @author zhangbo 2015年6月11日
     */
    public String addRelatedChannel(){
	try {
	    opChannelV2Service.addRelatedChannel(getChannelId(),getLinkChannelId());
	    JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
	} catch (Exception e) {
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	    log.error(e.getMessage(),e);
	}
	return StrutsKey.JSON;
    }
    
    /**
     * 批量删除关联频道
     *
     * @return
     * @author zhangbo 2015年6月11日
     */
    public String deleteRelatedChannels(){
	try {
	    Integer[] deleteIds = StringUtil.convertStringToIds(getDeleteIds());
	    opChannelV2Service.deleteRelatedChannels(getChannelId(),deleteIds);
	    JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
	} catch (Exception e) {
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	    log.error(e.getMessage(),e);
	}
	return StrutsKey.JSON;
    }
    
    /**
     * 批量删除关联频道
     *
     * @return
     * @author zhangbo 2015年6月11日
     */
    public String updateRelatedChannelSerial(){
	String channelId = request.getParameter("reIndexChannelId");
	String[] linkChannelIds = request.getParameterValues("reIndexlinkId");
	try {
	    opChannelV2Service.updateRelatedChannelSerial(Integer.valueOf(channelId),linkChannelIds);
	    JSONUtil.optSuccess("排序成功", jsonMap);
	} catch (Exception e) {
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	    log.error(e.getMessage(),e);
	}
	return StrutsKey.JSON;
    }
    
    /**
     * 保存频道置顶
     *
     * @return
     * @author zhangbo 2015年6月12日
     */
    public String saveChannelTop(){
	try {
	    opChannelV2Service.saveChannelTop(getChannelId());
	    JSONUtil.optSuccess("修改成功", jsonMap);
	} catch (Exception e) {
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	    log.error(e.getMessage(),e);
	}
	return StrutsKey.JSON;
    }
    
    /**
     * 删除频道置顶
     *
     * @return
     * @author zhangbo 2015年6月12日
     */
    public String deleteChannelTop(){
	try {
	    opChannelV2Service.deleteChannelTop(getChannelId());
	    JSONUtil.optSuccess("修改成功", jsonMap);
	} catch (Exception e) {
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	    log.error(e.getMessage(),e);
	}
	return StrutsKey.JSON;
    }
    
    /**
     * 修改频道标签
     *
     * @return
     * @author zhangbo 2015年6月12日
     */
	public String updateOpChannelLabel() {
		try {
			String labelIds = getChannelLabelIds().equals("") ? null : getChannelLabelIds();
			String labelNames = getChannelLabelNames().equals("") ? null : getChannelLabelNames();
			opChannelV2Service.updateOpChannelLabel(getChannelId(), labelIds, labelNames);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(),e);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询频道专题
	 *
	 * @return
	 * @author zhangbo 2015年6月12日
	 */
	public void queryChannelThemeList() {
		PrintWriter out = null;
		try{
			out = response.getWriter();
			
			List<OpChannelTheme> list = opChannelV2Service.queryChannelThemeList();
			
			JSONArray array = new JSONArray();
			
			for (OpChannelTheme opChannelTheme : list) {
				JSONObject obj = new JSONObject();
				obj.put("themeId", opChannelTheme.getId());
				obj.put("themeName", opChannelTheme.getThemeName());
				array.add(obj);
			}
			
			out.print(array.toString());
			out.flush();
		}catch(Exception e){
			jsonMap.clear();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(json.toString());
			out.flush();
			log.error(e.getMessage(),e);
		}finally{
			out.close();
		}
	}
	
	
	private Integer channelId;			//id
	private Integer ownerId;			//拥有者ID
	private String ownerName;			//拥有者名称
	private String channelName;			//频道名称
	private String channelTitle;		//频道标题
	private String subtitle;			//频道副标题
	private String channelDesc;			//频道描述
	private String channelIcon;			//频道icon
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
	
	private String worldAndAuthorIdsStr;//worldId and authorId，eg：123-114,124-114
	private Integer themeId;			//主题ID
	
	private Integer linkChannelId;	// 关联频道id
	private String deleteIds;	// 执行删除操作的id集合
	private boolean topFlag;			//是否置顶	true置顶，false不置顶
	
	
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

	public String getWorldAndAuthorIdsStr() {
		return worldAndAuthorIdsStr;
	}

	public void setWorldAndAuthorIdsStr(String worldAndAuthorIdsStr) {
		this.worldAndAuthorIdsStr = worldAndAuthorIdsStr;
	}

	public Integer getThemeId() {
		return themeId;
	}

	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}

	/**
	 * @return the linkChannelId
	 */
	public Integer getLinkChannelId() {
	    return linkChannelId;
	}

	/**
	 * @param linkChannelId the linkChannelId to set
	 */
	public void setLinkChannelId(Integer linkChannelId) {
	    this.linkChannelId = linkChannelId;
	}

	/**
	 * @return the deleteIds
	 */
	public String getDeleteIds() {
	    return deleteIds;
	}

	/**
	 * @param deleteIds the deleteIds to set
	 */
	public void setDeleteIds(String deleteIds) {
	    this.deleteIds = deleteIds;
	}

	/**
	 * @return the topFlag
	 */
	public boolean isTopFlag() {
	    return topFlag;
	}

	/**
	 * @param topFlag the topFlag to set
	 */
	public void setTopFlag(boolean topFlag) {
	    this.topFlag = topFlag;
	}

}

package com.imzhitu.admin.ztworld;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldFilterLogo;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.hts.web.ztworld.service.impl.ZTWorldServiceImpl;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.ztworld.service.ZTWorldService;

import net.sf.json.JSONObject;

/**
 * 世界管理控制器
 * 
 * @author ztj
 * 
 */
public class ZTWorldAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5952046745371192904L;

	private Integer id;
	private String ids;
	private String worldIdKey;
	private Integer worldId;
	private String worldIds;
	private String shortLink;
	private String startTime;
	private String endTime;
	private String worldLabel;
	private String worldLabe;
	private Integer phoneCode;
	private String platform;
	private String authorName;

	private String groupName;
	private Integer groupId;

	private Integer showLocationTag;
	private String province;
	private String city;
	private String location;

	private String worldJSON;

	private Integer activityId; // 活动id
	
	private String labelIds;
	private Integer typeId;
	private Integer valid;
	private Integer shield;
	private String worldDesc;
	
	private Float ver;
	private String logoPath;
	private String logoDesc;
	private Integer user_level_id;	//用户登记
	
	private Integer childId;
	private Boolean  isNotAddClick = false; // 不添加播放次数标记位
	
	private String worldLocation;	// 织图所标记的地理位置信息 

	@Autowired
	private ZTWorldService worldService;
	
	@Autowired
	private com.hts.web.ztworld.service.ZTWorldService webWorldService;

	
	/**
	 * 查询今日更新的世界
	 */
	public String queryHTWorldList() {
		try {
			worldService.buildWorld(maxId, page, rows, startTime, endTime, shortLink, 
					phoneCode, worldLabel, authorName, valid, shield,worldDesc, worldLocation,user_level_id, sort, order, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询封面子世界所在分页的子世界信息
	 * 
	 * @return
	 */
	public String queryTitleChildWorldPage() {
		try {
			boolean isAdmin = false;
			String admin = request.getParameter("adminKey");
			if(!StringUtil.checkIsNULL(admin) && admin.equals(ZTWorldServiceImpl.ADMIN_PASS)) {
				isAdmin = true;
			}
			JSONObject json = webWorldService.getTitleChildPageInfo(worldId, limit, isNotAddClick, isAdmin);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, json, OptResult.JSON_KEY_HTWORLD, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询指定子世界所在分页的子世界信息
	 * @return
	 */
	public String queryChildWorldPage() {
		try {
			JSONObject json = webWorldService.getChildWorldPageInfoById(worldId, childId, limit);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, json, OptResult.JSON_KEY_HTWORLD, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 屏蔽织图
	 * 
	 * @return
	 * @throws
	 */
	public String shieldWorld() {
		try {
			worldService.shieldWorld(worldId);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 解除屏蔽
	 * 
	 * @return
	 */
	public String unShieldWorld() {
		try {
			worldService.unShieldWorld(worldId);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 根据json字符串批量更新织图
	 * 
	 * @return
	 */
	public String updateWorldByJSON() {
		try {
			worldService.updateWorldByJSON(worldIdKey, worldJSON);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除织图
	 * 
	 * @return
	 */
	public String deleteWorld() {
		try {
			worldService.deleteWorld(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 查询所有分类
	 * 
	 * @return
	 */
	public String queryAllType() {
		try {
			worldService.deleteWorld(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新最新有效性
	 * 
	 * @return
	 */
	public String updateLatestValid() {
		try {
			worldService.updateLatestValid(id, valid);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询滤镜logo列表
	 * @return
	 * 
	 */
	public String queryFilterLogoList() {
		try {
			worldService.buildFilterLogo(jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询滤镜logo
	 * 
	 * @return
	 */
	public String queryFilterLogo() {
		try {
			HTWorldFilterLogo logo = worldService.getFilterLogo();
			JSONUtil.optResult(OptResult.OPT_SUCCESS, logo, OptResult.JSON_KEY_LOGO, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新滤镜logo 
	 * 
	 * @return
	 */
	public String updateFilterLogo() {
		try {
			worldService.updateFilterLogo(ver, logoPath, logoDesc, valid);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询织图信息
	 * 
	 * @return
	 * @author zhangbo	2015年10月13日
	 */
	public String queryWorld() {
		try {
			HTWorld world = webWorldService.getWorldById(worldId, false);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, world, OptResult.JSON_KEY_HTWORLD, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWorldLabel() {
		return worldLabel;
	}

	public void setWorldLabel(String worldLabel) {
		this.worldLabel = worldLabel;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Integer getShowLocationTag() {
		return showLocationTag;
	}

	public void setShowLocationTag(Integer showLocationTag) {
		this.showLocationTag = showLocationTag;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public ZTWorldService getWorldService() {
		return worldService;
	}

	public void setWorldService(ZTWorldService worldService) {
		this.worldService = worldService;
	}

	public String getWorldIds() {
		return worldIds;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public void setWorldIds(String worldIds) {
		this.worldIds = worldIds;
	}

	public String getWorldJSON() {
		return worldJSON;
	}

	public void setWorldJSON(String worldJSON) {
		this.worldJSON = worldJSON;
	}

	public String getShortLink() {
		return shortLink;
	}

	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getWorldIdKey() {
		return worldIdKey;
	}

	public void setWorldIdKey(String worldIdKey) {
		this.worldIdKey = worldIdKey;
	}

	public String getWorldLabe() {
		return worldLabe;
	}

	public void setWorldLabe(String worldLabe) {
		this.worldLabe = worldLabe;
	}

	public String getLabelIds() {
		return labelIds;
	}

	public void setLabelIds(String labelIds) {
		this.labelIds = labelIds;
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

	public Integer getShield() {
		return shield;
	}

	public void setShield(Integer shield) {
		this.shield = shield;
	}

	public Float getVer() {
		return ver;
	}

	public void setVer(Float ver) {
		this.ver = ver;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getLogoDesc() {
		return logoDesc;
	}

	public void setLogoDesc(String logoDesc) {
		this.logoDesc = logoDesc;
	}
	
	public Integer getUser_level_id() {
		return user_level_id;
	}

	public void setUser_level_id(Integer user_level_id) {
		this.user_level_id = user_level_id;
	}

	public Integer getChildId() {
		return childId;
	}

	public void setChildId(Integer childId) {
		this.childId = childId;
	}

	public Boolean getIsNotAddClick() {
		return isNotAddClick;
	}

	public void setIsNotAddClick(Boolean isNotAddClick) {
		this.isNotAddClick = isNotAddClick;
	}

	public String getWorldDesc() {
		return worldDesc;
	}

	public void setWorldDesc(String worldDesc) {
		this.worldDesc = worldDesc;
	}

	public void setWorldLocation(String worldLocation) {
		this.worldLocation = worldLocation;
	}
	
	
}

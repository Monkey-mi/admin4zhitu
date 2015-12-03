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
	private String shortLink;
	private String startTime;
	private String endTime;
	private String worldLabel;
	private Integer phoneCode;
	private String authorName;

	private String worldJSON;

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
	
	private Integer isZombie;

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
					phoneCode, worldLabel, authorName, valid, shield,worldDesc, worldLocation,user_level_id, sort, order,isZombie, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据条件查询瀑布流织图
	 * 
	 * @return
	 * @author zhangbo	2015年11月25日
	 */
	public String queryWorldMasonry() {
		try {
			// 若存在织图等级，则根据织图等级查询织图瀑布流数据
			if ( user_level_id != null ) {
				// TODO 根据用户查询现在还有待商榷，先用原来的老接口
				worldService.buildWorld(maxId, page, rows, startTime, endTime, shortLink, 
						phoneCode, worldLabel, authorName, valid, shield,worldDesc, worldLocation,user_level_id, sort, order,isZombie, jsonMap);
			}
			// 若马甲条件为1，则查询马甲织图
			else if ( isZombie != null && isZombie == 1 ) {
				worldService.buildWorldMasonryByZombie(maxId, page, rows, startTime, endTime, phoneCode, jsonMap);
			}
			// 若存在织图描述，则为按照织图描述查询
			else if ( worldDesc != null && !worldDesc.trim().equals("") ) {
				worldService.buildWorldMasonryByWorldDesc(maxId, page, rows, worldDesc, jsonMap);
			}
			// 若存在织图地理位置信息，则为按照织图地理位置信息查询
			else if ( worldLocation != null && !worldLocation.trim().equals("") ) {
				worldService.buildWorldMasonryByWorldLocation(maxId, page, rows, worldLocation, jsonMap);
			}
			// 默认查询瀑布流织图信息
			else {
				worldService.buildWorldMasonry(maxId, page, rows, startTime, endTime, phoneCode, valid, jsonMap);
			}
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询织图描述中被@人的信息
	 * @return 
		*	2015年11月10日
		*	mishengliang
	 */
	public String  queryCommentAt(){
		try {
			worldService.queryCommentAt(worldId,jsonMap);
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

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setWorldIdKey(String worldIdKey) {
		this.worldIdKey = worldIdKey;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setWorldLabel(String worldLabel) {
		this.worldLabel = worldLabel;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public void setWorldJSON(String worldJSON) {
		this.worldJSON = worldJSON;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public void setShield(Integer shield) {
		this.shield = shield;
	}

	public void setWorldDesc(String worldDesc) {
		this.worldDesc = worldDesc;
	}

	public void setVer(Float ver) {
		this.ver = ver;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public void setLogoDesc(String logoDesc) {
		this.logoDesc = logoDesc;
	}

	public void setUser_level_id(Integer user_level_id) {
		this.user_level_id = user_level_id;
	}

	public void setChildId(Integer childId) {
		this.childId = childId;
	}

	public void setIsNotAddClick(Boolean isNotAddClick) {
		this.isNotAddClick = isNotAddClick;
	}

	public void setWorldLocation(String worldLocation) {
		this.worldLocation = worldLocation;
	}

	public void setIsZombie(Integer isZombie) {
		this.isZombie = isZombie;
	}
	
}

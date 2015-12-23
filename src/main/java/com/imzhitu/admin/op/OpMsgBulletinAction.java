package com.imzhitu.admin.op;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.IllegalClassException;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.op.pojo.NearBulletinDto;
import com.imzhitu.admin.op.service.OpMsgBulletinService;

/**
 * 公告请求控制类
 * 
 * @author zhutianjie
 * @modify zhangbo	2015年12月18日
 *
 */
public class OpMsgBulletinAction extends BaseCRUDAction {

	private static final long serialVersionUID = 312348216405081783L;

	/**
	 * 公告主键id
	 * @author zhangbo	2015年12月18日
	 */
	private Integer id; 
	
	/**
	 * 公告名称，即公告描述
	 * @author zhangbo	2015年12月18日
	 */
	private String bulletinName;
	
	/**
	 * 公告分类	1：有奖活动，2：无奖活动，3：达人专题，4：内容专题
	 * @author zhangbo	2015年12月18日
	 */
	private Integer category;
	
	/**
	 * 公告图片路径
	 * @author zhangbo	2015年12月18日
	 */
	private String path;
	
	/**
	 * 公告缩略图路径
	 * @author zhangbo	2015年12月18日
	 */
	private String thumb;
	
	/**
	 * 公告链接类型	1：网页链接，2：频道id，3：用户id，4：活动标签（名称）
	 * @author zhangbo	2015年12月18日
	 */
	private Integer type;
	
	/**
	 * 公告链接内容
	 * @author zhangbo	2015年12月18日
	 */
	private String link;
	
	/**
	 * 公告id集合，以逗号“,”分隔
	 * @author zhangbo	2015年12月18日
	 */
	private String idsStr;
	
	/**
	 * 刷新缓存的标记位，1：刷新活动专题，2：刷新达人专题，3：刷新内容专题
	 * @author zhangbo	2015年12月19日
	 */
	private Integer refreshFlag;
	
	/**
	 * 是否为查询缓存中数据
	 * @author zhangbo	2015年12月18日
	 */
	private Integer isCache;

	private NearBulletinDto nearBulletin = new NearBulletinDto();

	@Autowired
	private OpMsgBulletinService msgBulletinService;

	public String queryMsgBulletin() {
		try {
			msgBulletinService.queryMsgBulletin(id, category, type, isCache, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存公告
	 * 
	 * @return
	 * @author zhangbo	2015年12月18日
	 */
	public String saveMsgBulletin() {
		try {
			if ( id == null ) {
				msgBulletinService.insertMsgBulletin(path, category, type, link, getCurrentLoginUserId(), bulletinName, thumb);
			} else {
				msgBulletinService.updateMsgBulletin(id, path, category, type, link, getCurrentLoginUserId(), bulletinName, thumb);
			}
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchDeleteMsgBulletin() {
		try {
			msgBulletinService.batchDeleteMsgBulletin(idsStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 这个方法是旧接口，刷新旧的缓存时候使用
	 * 
	 * @return
	 */
	public String updateMsgBulletinCache() {
		try {
			msgBulletinService.updateMsgBulletinCacheOld(idsStr, getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 重新排序公告
	 * 
	 * @return
	 * @author zhangbo	2015年12月19日
	 */
	public String reorderBulletin() {
		try {
			Integer[] ids = StringUtil.convertStringToIds(idsStr);
			msgBulletinService.reorder(ids, getCurrentLoginUserId());
			JSONUtil.optSuccess("排序成功", jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 刷新公告缓存，根据刷新标记位，来确定刷新特定缓存，刷新内容为在表格中勾选的记录
	 * 
	 * @return
	 * @author zhangbo	2015年12月19日
	 */
	public String refreshBulletinCache() {
		try {
			if ( refreshFlag == 1 ) {
				msgBulletinService.refreshActivityThemeCache();
			} else if ( refreshFlag == 2 ) {
				msgBulletinService.refreshUserThemeCache();
			} else if ( refreshFlag == 3 ) {
				msgBulletinService.refreshContentThemeCache();
			}
			JSONUtil.optSuccess("刷新缓存成功", jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 查询附近公告
	 * 
	 * @return
	 */
	public String queryNearBulletin() {
		try {
			msgBulletinService.buildNearBulletin(nearBulletin, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 根据id删除附近公告
	 * 
	 * @return
	 */
	public String deleteNearBulletinById() {
		try {
			msgBulletinService.delNearBulletinById(id);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 保存附近公告
	 * 
	 * @return
	 */
	public String saveNearBulletin() {
		try {
			String[] cityIdStrs = request.getParameterValues("cityId");
			if (cityIdStrs != null && cityIdStrs.length > 0) {
				List<Integer> list = new ArrayList<Integer>();
				for (String idStr : cityIdStrs) {
					if (!StringUtil.checkIsNULL(idStr)) {
						list.add(Integer.parseInt(idStr));
					}
				}
				nearBulletin.setCityIds(list);
			} else {
				throw new IllegalClassException("please select cityid");
			}
			msgBulletinService.saveNearBulletin(nearBulletin);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 根据id查询附近公告
	 * 
	 * @return
	 */
	public String queryNearBulletinById() {
		try {
			NearBulletinDto dto = msgBulletinService.queryNearBulletinById(id);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, dto, OptResult.JSON_KEY_OBJ, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 根据ids删除置顶城市的公告
	 * 
	 * @return
	 */
	public String deleteCityBulletinByIds() {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		try {
			msgBulletinService.delCityBulletinByIds(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 更新附近公告
	 * 
	 * @return
	 */
	public String updateNearBulletin() {
		try {
			String[] cityIdStrs = request.getParameterValues("cityId");
			if (cityIdStrs != null && cityIdStrs.length > 0) {
				List<Integer> list = new ArrayList<Integer>();
				for (String idStr : cityIdStrs) {
					if (!StringUtil.checkIsNULL(idStr)) {
						list.add(Integer.parseInt(idStr));
					}
				}
				nearBulletin.setCityIds(list);
			}
			msgBulletinService.updateNearBulletin(nearBulletin);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 更新城市公告序号
	 * 
	 * @return
	 */
	public String updateCityBulletinSerial() {
		try {
			String[] idStrs = request.getParameterValues("reIndexId");
			msgBulletinService.updateCityBulletinSerial(idStrs);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;

	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setBulletinName(String bulletinName) {
		this.bulletinName = bulletinName;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}

	public void setIsCache(Integer isCache) {
		this.isCache = isCache;
	}
	
	public NearBulletinDto getNearBulletin() {
		return nearBulletin;
	}

	public void setNearBulletin(NearBulletinDto nearBulletin) {
		this.nearBulletin = nearBulletin;
	}

	public void setRefreshFlag(Integer refreshFlag) {
		this.refreshFlag = refreshFlag;
	}

	public OpMsgBulletinService getMsgBulletinService() {
		return msgBulletinService;
	}

	public void setMsgBulletinService(OpMsgBulletinService msgBulletinService) {
		this.msgBulletinService = msgBulletinService;
	}

	public Integer getId() {
		return id;
	}

	public String getBulletinName() {
		return bulletinName;
	}

	public Integer getCategory() {
		return category;
	}

	public String getPath() {
		return path;
	}

	public String getThumb() {
		return thumb;
	}

	public Integer getType() {
		return type;
	}

	public String getLink() {
		return link;
	}

	public String getIdsStr() {
		return idsStr;
	}

	public Integer getRefreshFlag() {
		return refreshFlag;
	}

	public Integer getIsCache() {
		return isCache;
	}

}

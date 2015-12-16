package com.imzhitu.admin.op;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.op.pojo.NearBulletinDto;
import com.imzhitu.admin.op.service.OpMsgBulletinService;


public class OpMsgBulletinAction extends BaseCRUDAction {

	private static final long serialVersionUID = 312348216405081783L;

	private Integer id;
	private Integer valid;
	private Integer type;
	private String path;
	private String link;
	private String idsStr;
	private Integer isCache;
	private String bulletinName;
	private String bulletinThumb;

	private NearBulletinDto nearBulletin = new NearBulletinDto();

	@Autowired
	private OpMsgBulletinService msgBulletinService;

	public String queryMsgBulletin() {
		try {
			msgBulletinService.queryMsgBulletin(id, type, valid, isCache, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	public String insertMsgBulletin() {
		try {
			msgBulletinService.insertMsgBulletin(path, type, link, getCurrentLoginUserId(), bulletinName,
					bulletinThumb);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
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

	public String updateMsgBulletin() {
		try {
			msgBulletinService.updateMsgBulletin(id, path, type, link, valid, getCurrentLoginUserId(), bulletinName,
					bulletinThumb);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	public String batchUpdateMsgBulletinValid() {
		try {
			msgBulletinService.batchUpdateMsgBulletinValid(idsStr, valid, getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	public String updateMsgBulletinCache() {
		try {
			msgBulletinService.updateMsgBulletinCache(idsStr, getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
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
			if(cityIdStrs != null && cityIdStrs.length > 0) {
				List<Integer> list = new ArrayList<Integer>();
				for(String idStr : cityIdStrs) {
					if(!StringUtil.checkIsNULL(idStr)) {
						list.add(Integer.parseInt(idStr));
					}
				}
				nearBulletin.setCityIds(list);
			}
			msgBulletinService.saveNearBulletin(nearBulletin);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
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

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getIdsStr() {
		return idsStr;
	}

	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}

	public Integer getIsCache() {
		return isCache;
	}

	public void setIsCache(Integer isCache) {
		this.isCache = isCache;
	}

	public String getBulletinName() {
		return bulletinName;
	}

	public void setBulletinName(String bulletinName) {
		this.bulletinName = bulletinName;
	}

	public String getBulletinThumb() {
		return bulletinThumb;
	}

	public void setBulletinThumb(String bulletinThumb) {
		this.bulletinThumb = bulletinThumb;
	}

	public NearBulletinDto getNearBulletin() {
		return nearBulletin;
	}

	public void setNearBulletin(NearBulletinDto nearBulletin) {
		this.nearBulletin = nearBulletin;
	}

}
